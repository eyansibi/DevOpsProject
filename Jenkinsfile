pipeline {
    agent any
    environment {
        JAVA_HOME = tool name: 'JAVA_HOME', type: 'jdk'
        M2_HOME = tool name: 'M2_HOME', type: 'maven'
        PATH = "${JAVA_HOME}/bin:${M2_HOME}/bin:${PATH}"
       NEXUS_REPO_URL = "http://192.167.33.10:8081/repository/maven-snapshots/"
        MAVEN_SETTINGS = "/usr/share/maven/conf/settings.xml" // settings.xml contenant user/password
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/eyansibi/DevOpsProject.git'
            }
        }
        stage('Java Version') {
            steps {
                sh 'java -version'
            }
        }
        stage('MAVEN') {
            steps {
                sh 'mvn --version'
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Deploy to Nexus') {
            steps {
                script {
                    try {
                        echo "Déploiement vers Nexus en utilisant ${MAVEN_SETTINGS}"

                        sh """
                            mvn deploy \
                                --settings ${MAVEN_SETTINGS} \
                                -DskipTests \
                                -DaltDeploymentRepository=nexus-snapshots::default::${NEXUS_REPO_URL}
                        """
                    } catch (Exception e) {
                        echo "Échec du déploiement vers Nexus: ${e.message}"
                        error "Le déploiement a échoué. Vérifiez le fichier settings.xml et la configuration Nexus."
                    }
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Install') {
            steps {
                sh 'mvn install'
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
    }
    post {
        failure {
            echo "Le pipeline a échoué. Vérifiez les logs pour plus de détails."
        }
        success {
            echo "Le pipeline a réussi !"
        }
    }
}
