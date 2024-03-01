pipeline {
    agent any


    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Zushaner/SimbirSoftTask1.git'
                withMaven {
                bat "mvn clear test"
                }

                // To run Maven on a Windows agent, use
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}