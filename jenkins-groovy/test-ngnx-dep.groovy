pipeline {
    agent any
    environment {
        GOOGLE_CREDENTIALS = credentials('gcp-credentials')
    }
    stages {
        stage('Deploy NGINX') {
            steps {
                script {
                    sh 'kubectl apply -f workload-manifest/test-app-manifest/deployment.yaml'
                    sh 'kubectl apply -f workload-manifest/test-app-manifest/svc.yaml'
                }
            }
        }
    }
    post {
        success {
            echo "NGINX application deployed successfully."
        }
        failure {
            echo "NGINX application deployment failed."
        }
    }
}
