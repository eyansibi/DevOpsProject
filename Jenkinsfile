pipeline {
    agent any
   environment {
        JAVA_HOME = tool name: 'JAVA_HOME', type: 'jdk'
        M2_HOME = tool name: 'M2_HOME', type: 'maven'
        PATH = "${JAVA_HOME}/bin:${M2_HOME}/bin:${PATH}"
       NEXUS_REPO_URL = "http://127.0.0.1:8081/repository/maven-releases/"
        MAVEN_SETTINGS = "/usr/share/maven/conf/settings.xml"
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
        stage('MVN SONARQUBE') {
            steps {
                withSonarQubeEnv('sq') {
                    sh '''
                        mvn clean compile
                        mvn sonar:sonar -DskipTests -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }
        stage('Deploy to Nexus') {
    steps {
        script {
            withCredentials([usernamePassword(credentialsId: 'nexus-credentials-id', 
                                              usernameVariable: 'NEXUS_USERNAME', 
                                              passwordVariable: 'NEXUS_PASSWORD')]) {
                try {
                    sh """
                        mvn deploy \
                            --settings ${MAVEN_SETTINGS} \
                            -DskipTests \
                            -Dnexus.username=$NEXUS_USERNAME \
                            -Dnexus.password=$NEXUS_PASSWORD
                    """
                } catch (Exception e) {
                    echo "Deployment to Nexus failed: ${e.message}"
                    throw e
                }
            }
        }
    }
}
    }
}
