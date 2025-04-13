pipeline {
    agent any

    environment {
        JAVA_HOME = tool name: 'JAVA_HOME', type: 'jdk'
        M2_HOME = tool name: 'M2_HOME', type: 'maven'
        PATH = "${JAVA_HOME}/bin:${M2_HOME}/bin:${PATH}"
    }

    stages {
        stage('Start MySQL') {
            steps {
                echo '🔧 Starting MySQL Docker container...'
                sh '''
                    docker run -d --name mysql-test \
                      -e MYSQL_ROOT_PASSWORD=root \
                      -e MYSQL_DATABASE=kaddem \
                      -p 3306:3306 \
                      mysql:8

                    echo "⏳ Waiting for MySQL to be ready..."
                    for i in {1..20}; do
                      docker exec mysql-test mysqladmin ping -h localhost -uroot -proot --silent && break
                      echo "⌛ Still waiting..."
                      sleep 2
                    done
                '''
            }
        }

        stage('Clone Repository') {
            steps {
                echo '📥 Cloning repository...'
                git branch: 'main', url: 'https://github.com/eyansibi/DevOpsProject.git'
            }
        }

        stage('Maven Compile') {
            steps {
                echo '⚙️ Compiling project...'
                sh 'mvn clean compile'
            }
        }

        stage('Maven Test') {
            steps {
                echo '🧪 Running unit tests...'
                sh 'mvn test'
            }
        }

        stage('Maven Install') {
            steps {
                echo '📦 Installing project...'
                sh 'mvn install'
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning up Docker container...'
            sh 'docker rm -f mysql-test || true'
        }
        success {
            echo '✅ Build succeeded!'
        }
        failure {
            echo '❌ Build failed.'
        }
    }
}
