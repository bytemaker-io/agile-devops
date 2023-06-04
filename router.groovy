/*
*
Author:kalean
Time:2023/05/03
*
*/
pipeline {
    agent any
    
    stages {
        stage('Compile') {
            steps {
                sh 'GOOS=linux GOARCH=mipsle GOMIPS=softfloat CGO_ENABLED=0 go build'
            }
        }
        
        stage('Transfer') {
            steps {
                script {
                    sshPublisher(
                        publishers: [sshPublisherDesc(
                            configName: 'SSH Server',
                            transfers: [sshTransfer(
                                sourceFiles: 'path/to/compiled/file',
                                removePrefix: 'path/to/compiled/',
                                remoteDirectory: '/path/on/ssh/server'
                            )]
                        )]
                    )
                }
            }
        }
        
        stage('Stop Application') {
            steps {
                script {
                    sshCommand remote: '192.168.1.1', user: 'root', password: 'wf981230', command: 'killall main'
                }
            }
        }
        
        stage('Execute') {
            steps {
                script {
                    sshCommand remote: '192.168.1.1', user: 'root', password: 'wf981230', command: './main'
                }
            }
        }
    }
}

