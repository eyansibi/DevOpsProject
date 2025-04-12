pipeline {
    agent any
    tools {
        maven 'M2_HOME' // Remplace par le nom exact configuré dans Jenkins (ex. 'maven' si corrigé)
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', 
                    url: 'https://github.com/eyansibi/DevOpsProject.git'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('MVN SONARQUBE') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                        sh "mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dmaven.test.skip=true"
                    }
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Deploy') {
            steps {
                sh 'cp target/*.jar /path/to/deploy' // Ajuste le chemin selon ton environnement (ex. serveur Tomcat)
            }
        }
    }
    post {
        success {
            echo 'Build, tests, analyse SonarQube et déploiement réussis !'
        }
        failure {
            echo 'Échec du build, des tests, de l\'analyse SonarQube ou du déploiement !'
        }
        always {
            archiveArtifacts artifacts: 'target/surefire-reports/*.xml, target/*.jar', allowEmptyArchive: true
        }
    }
}
