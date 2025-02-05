pipeline {
    agent any
    environment {
        GOOGLE_CREDENTIALS = credentials('gcp-credentials')
    }
    stages {
        stage('Terraform Init') {
            steps {
                script {
                    sh 'terraform init'
                }
            }
        }
        stage('Terraform Apply') {
            steps {
                script {
                    sh 'terraform apply -auto-approve'
                }
            }
        }
    }
    post {
        success {
            echo "GKE Cluster created successfully!"
        }
        failure {
            echo "Failed to create GKE Cluster."
        }
    }
}
