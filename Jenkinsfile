pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn -B clean verify'
            }
        }
    }

    post {
        always {
            junit(
                testResults: 'course-registration-app/target/surefire-reports/TEST-*.xml',
                allowEmptyResults: true
            )
        }

        success {
            archiveArtifacts(
                artifacts: 'course-registration-app/target/*.jar',
                fingerprint: true
            )
        }
    }
}