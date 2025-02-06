pipeline {
    agent any
    environment {
        GOOGLE_APPLICATION_CREDENTIALS = credentials('gke-gcr-json') // The ID of your Google Cloud Service Account credentials
    }
    
    stages {
        stage('Clone Repository') {
            steps {
                // Clone the repository containing your Terraform configuration (optional if you use SCM)
                git 'https://github.com/kaustubhchandra/GKE-terra-jen.git'
            }
        }
        stage('Terraform Init') {
            steps {
                script {
                    sh 'cd jenkins-groovy'
                    sh 'terraform init'
                }
            }
        }
        stage('Terraform Plan') {
            steps {
                script {
                    sh 'terraform plan'
                }
            }
        }
        stage('Terraform Apply') {
            steps {
                script {
                    // Automatically approve the Terraform apply
                    sh 'terraform apply -auto-approve'
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
