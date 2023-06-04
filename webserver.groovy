pipeline {
    agent any
    
    stages {
        stage('Compile') {
            steps {
                sh 'go build'
            }
        }
        
        stage('Stop Process') {
            steps {
                script {
                    sh 'pkill -f main'
                }
            }
        }
        
        stage('Execute') {
            steps {
                sh './main'
            }
        }
    }
}

