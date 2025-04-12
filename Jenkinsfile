pipeline {
    agent any
    tools {
        maven 'M2_HOME' // ← Remplace par le nom exact dans Jenkins
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
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
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
