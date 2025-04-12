pipeline {
    agent any
    tools {
        maven 'M2_HOME' // Ensure Maven is configured in Jenkins Global Tool Configuration
    }
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', 
                    url: 'https://github.com/eyansibi/DevOpsProject.git'
            }
        }
       
       
    }
   
}
