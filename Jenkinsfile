pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'JAVA_HOME', type: 'jdk'
        M2_HOME = tool name: 'M2_HOME', type: 'maven'
        PATH = "${JAVA_HOME}/bin:${M2_HOME}/bin:${PATH}"
    }

    stages {
        stage('GIT') {
            steps {
                git branch: 'main', url: 'https://github.com/eyansibi/DevOpsProject.git'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test Stage') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Maven Install') {
            steps {
                sh 'mvn install'
            }
        }

// stage('MVN SONARQUBE') {
//     steps {
//         withSonarQubeEnv('sq') {
//             sh '''
//                 mvn clean compile
//                 mvn sonar:sonar -DskipTests -Dsonar.java.binaries=target/classes
//             '''
//         }
//     }
// }


        // stage('Deploy to Nexus') {
        //     steps {
        //         echo 'Deploying to Nexus Repository'
        //         sh 'mvn deploy -Dmaven.test.skip=true'
        //     }
        // }

        // stage('Nexus Deployment') {
        //     steps {
        //         script {
        //             def artifactExists = sh(
        //                 script: '''
        //                     curl -s -o /dev/null -w "%{http_code}" -u admin:admin "http://192.167.33.10:8081/repository/maven-public/tn/esprit/spring/kaddem/0.0.1-SNAPSHOT/kaddem-0.0.1-20250413.001931-1.jar"
        //                 ''',
        //                 returnStdout: true
        //             ).trim()

        //             if (artifactExists != '200') {
        //                 echo 'Artifact not found. Deploying to Nexus...'
        //                 sh 'mvn deploy -Dmaven.test.skip=true'
        //             } else {
        //                 echo 'Artifact already exists on Nexus; skipping deployment.'
        //             }
        //         }
        //     }
        // }

        // stage('docker image Stage') {
        //     steps {
        //         sh 'docker build -t timesheet:1.0.0 .'
        //     }
        // }
    }
}
