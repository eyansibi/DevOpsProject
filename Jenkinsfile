pipeline {
    agent any
   environment {
        JAVA_HOME = tool name: 'JAVA_HOME', type: 'jdk'
        M2_HOME = tool name: 'M2_HOME', type: 'maven'
        PATH = "${JAVA_HOME}/bin:${M2_HOME}/bin:${PATH}"
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/eyansibi/DevOpsProject.git' // Replace with your repository URL and branch
            }
        }
        stage('Java Version') {
            steps {
                sh 'java -version' // Verify Java setup
            }
        }
        stage('MAVEN') {
            steps {
                sh 'mvn --version' // Verify Maven setup
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn clean compile' // Compile the project
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test' // Run tests
            }
        }
        stage('Install') {
            steps {
                sh 'mvn install' // Install the project
            }
        }
    }
}
