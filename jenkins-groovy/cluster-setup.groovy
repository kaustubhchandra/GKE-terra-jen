pipeline {
    agent any
    environment {
        // Google Cloud Service Account Credentials
        GOOGLE_APPLICATION_CREDENTIALS = credentials('gke-gcr-json')  // The ID of your Google Cloud credentials
        GIT_CREDENTIALS = 'git-tok'  // The ID of your Git credentials
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clone the repository containing your Terraform configuration
                git credentialsId: "${GIT_CREDENTIALS}", url: 'https://github.com/kaustubhchandra/GKE-terra-jen.git', branch: 'main'
            }
        }

        stage('Authenticate with Google Cloud') {
            steps {
                script {
                    // Authenticate using gcloud CLI with the service account JSON key from credentials
                    sh '''
                    echo "${GOOGLE_APPLICATION_CREDENTIALS}" > /tmp/google-credentials.json
                    gcloud auth activate-service-account --key-file=/tmp/google-credentials.json
                    gcloud config set project asysops  // Replace with your actual project ID
                    '''
                }
            }
        }

        stage('Terraform Init') {
            steps {
                script {
                    // Initialize Terraform in the specified directory
                    sh 'cd tf-modules/gke-cluster && terraform init'
                }
            }
        }

        stage('Terraform Plan') {
            steps {
                script {
                    // Run terraform plan in the specified directory
                    sh 'cd tf-modules/gke-cluster && terraform plan'
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    // Automatically approve the Terraform apply
                    sh 'cd tf-modules/gke-cluster && terraform apply -auto-approve'
                }
            }
        }
    }

    post {
        always {
            // Clean up after job is finished
            cleanWs()
        }
    }
}
