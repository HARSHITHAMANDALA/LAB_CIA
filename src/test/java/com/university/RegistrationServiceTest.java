package com.university;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Test
    void shouldRegisterStudentForValidCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectInactiveCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                false,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }

    @Test
    void shouldRejectMissingPrerequisite() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS401"));
    }

    @Test
    void shouldAllowRegistrationWhenPrerequisiteIsCompleted() {

        Student student = new Student("S101", "Anita");
        student.addCompletedPrerequisite("CS301");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS401"));
    }

    @Test
    void shouldRejectDuplicateRegistration() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));

        boolean secondAttempt = service.register(student, course);

        assertFalse(secondAttempt);
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCreditLimitIsExceeded() {

        Student student = new Student("S101", "Anita");

        // Simulate 20 existing credits
        student.addRegistration("CS101", 4);
        student.addRegistration("CS102", 4);
        student.addRegistration("CS201", 4);
        student.addRegistration("CS202", 4);
        student.addRegistration("CS203", 4);

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }
    
    @Test
    void shouldRejectRegistrationWhenCourseIsAtCapacity() {
        Student student = new Student("S102", "Ravi");

        Course course = new Course(
                "CS301", "Data Structures",
                4, 1, true, null
        );

        // The course already has one student and capacity is one.
        course.incrementEnrollment();

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
        assertEquals(0, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldAllowRegistrationWhenExactlyOneSeatRemains() {
        Student student = new Student("S103", "Meera");

        Course course = new Course(
                "CS301", "Data Structures",
                4, 2, true, null
        );

        // One of the two seats is already occupied.
        course.incrementEnrollment();

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(2, course.getCurrentEnrollment());
    }

   
@Test
void shouldRejectDuplicateRegistrationWithDifferentCase() {
    Student student = new Student("S104", "Arun");

    Course firstCourse = new Course(
        "CS101", "Programming", 4, 10, true, null
    );

    Course duplicateCourse = new Course(
        "cs101", "Programming", 4, 10, true, null
    );

    RegistrationService service = new RegistrationService();

    assertTrue(service.register(student, firstCourse));

    // Same course code with different capitalization must be rejected.
    boolean result = service.register(student, duplicateCourse);

    assertFalse(result);
    assertEquals(4, student.getTotalCredits());
    assertEquals(1, firstCourse.getCurrentEnrollment());
    assertEquals(0, duplicateCourse.getCurrentEnrollment());
}

@Test
void shouldRejectDuplicateRegistrationWithMixedCase() {
    Student student = new Student("S105", "Priya");

    Course firstCourse = new Course(
        "CS102", "Data Structures", 3, 10, true, null
    );

    Course duplicateCourse = new Course(
        "Cs102", "Data Structures", 3, 10, true, null
    );

    RegistrationService service = new RegistrationService();

    assertTrue(service.register(student, firstCourse));

    // "CS102" and "Cs102" represent the same course.
    boolean result = service.register(student, duplicateCourse);

    assertFalse(result);
    assertEquals(3, student.getTotalCredits());
    assertEquals(1, firstCourse.getCurrentEnrollment());
    assertEquals(0, duplicateCourse.getCurrentEnrollment());
}


}