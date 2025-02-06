pipeline {
    agent any
    environment {
        GOOGLE_CREDENTIALS = credentials('gcp-credentials')
        GCP_PROJECT = 'your-gcp-project-id'
        GCR_REPO = "gcr.io/${GCP_PROJECT}/highway-app"
        K8S_CLUSTER_NAME = "highway-cluster"
        K8S_CLUSTER_ZONE = "us-central1-a"
    }
    stages {
        stage('Authenticate with Google Cloud') {
            steps {
                script {
                    // Authenticate with GCP using the provided service account
                    sh 'gcloud auth activate-service-account --key-file=$GOOGLE_CREDENTIALS'
                    sh 'gcloud config set project $GCP_PROJECT'
                }
            }
        }
        stage('Set Up Kubernetes Credentials') {
            steps {
                script {
                    // Get credentials for the GKE cluster and configure kubectl
                    sh 'gcloud container clusters get-credentials $K8S_CLUSTER_NAME --zone $K8S_CLUSTER_ZONE --project $GCP_PROJECT'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image from the Dockerfile in highway-app directory
                    sh 'docker build -t $GCR_REPO:latest highway-app/'
                }
            }
        }
        stage('Push Docker Image to GCR') {
            steps {
                script {
                    // Push the built Docker image to Google Container Registry (GCR)
                    sh 'docker push $GCR_REPO:latest'
                }
            }
        }
        stage('Deploy Blue') {
            steps {
                script {
                    // Deploy the blue version to Kubernetes
                    sh 'kubectl apply -f workload-manifest/blue-green-manifest/deploy_blue.yaml'
                    sh 'kubectl apply -f workload-manifest/blue-green-manifest/ingress.yaml'
                }
            }
        }
        stage('Deploy Green') {
            steps {
                script {
                    // Deploy the green version to Kubernetes
                    sh 'kubectl apply -f workload-manifest/blue-green-manifest/deploy_green.yaml'
                }
            }
        }
        stage('Switch Traffic') {
            steps {
                script {
                    // Switch traffic to the green deployment (can be done via ingress update)
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
