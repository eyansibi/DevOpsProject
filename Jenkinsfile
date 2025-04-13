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
    }
}
