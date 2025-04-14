pipeline {
    agent any
    environment {
        JAVA_HOME = tool name: 'JAVA_HOME', type: 'jdk'
        M2_HOME = tool name: 'M2_HOME', type: 'maven'
        PATH = "${JAVA_HOME}/bin:${M2_HOME}/bin:${PATH}"
       NEXUS_REPO_URL = "http://192.167.33.10:8081/repository/maven-snapshots/"
        MAVEN_SETTINGS = "/usr/share/maven/conf/settings.xml" // settings.xml contenant user/password

        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')

        DOCKER_IMAGE_NAME = 'eyansibi/eyansibidevops4twin2'
        DOCKER_IMAGE_TAG = "${BUILD_NUMBER}"
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
        // stage('Deploy to Nexus') {
        //     steps {
        //         script {
        //             try {
        //                 echo "Déploiement vers Nexus en utilisant ${MAVEN_SETTINGS}"

        //                 sh """
        //                     mvn deploy \
        //                         --settings ${MAVEN_SETTINGS} \
        //                         -DskipTests \
        //                         -DaltDeploymentRepository=nexus-snapshots::default::${NEXUS_REPO_URL}
        //                 """
        //             } catch (Exception e) {
        //                 echo "Échec du déploiement vers Nexus: ${e.message}"
        //                 error "Le déploiement a échoué. Vérifiez le fichier settings.xml et la configuration Nexus."
        //             }
        //         }
        //     }
        // }
        /*stage('Docker Login') {
            steps {
                sh 'echo "$DOCKERHUB_CREDENTIALS_PSW" | docker login -u "$DOCKERHUB_CREDENTIALS_USR" --password-stdin'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
                sh "docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${DOCKER_IMAGE_NAME}:latest"
            }
        }
        stage('Push Docker Image to DockerHub') {
            steps {
                sh "docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                sh "docker push ${DOCKER_IMAGE_NAME}:latest"
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh """
                    sed -i 's|image: eyansibi/eyansibidevops4twin2 :.*|image: ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}|' docker-compose.yml
                    """

                    sh 'docker-compose down'
                    sh 'docker-compose up -d'
                }
            }
        }

        stage('Cleanup') {
            steps {
                sh 'docker-compose down'
                sh 'docker system prune -f'
            }
        }
        */
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
