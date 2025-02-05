pipeline {
    agent any
    environment {
        GOOGLE_CREDENTIALS = credentials('gcp-credentials')
    }
    stages {
        stage('Deploy Blue') {
            steps {
                script {
                    sh 'kubectl apply -f workload-manifest/blue-green-manifest/deploy_blue.yaml'
                    sh 'kubectl apply -f workload-manifest/blue-green-manifest/ingress.yaml'
                }
            }
        }
        stage('Deploy Green') {
            steps {
                script {
                    sh 'kubectl apply -f workload-manifest/blue-green-manifest/deploy_green.yaml'
                }
            }
        }
        stage('Switch Traffic') {
            steps {
                script {
                    sh 'kubectl apply -f workload-manifest/blue-green-manifest/ingress.yaml'
                }
            }
        }
    }
    post {
        success {
            echo "Blue-Green Deployment completed successfully."
        }
        failure {
            echo "Blue-Green Deployment failed."
        }
    }
}
