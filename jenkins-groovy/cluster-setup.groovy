pipeline {
    agent any

    environment {
        // This is the ID of the Git token you created in Jenkins
        GIT_CREDENTIALS = 'git-tok'
    }

    stages {
        stage('Checkout') {
            steps {
                // Using the Git credentials to clone the repo
                git credentialsId: "${GIT_CREDENTIALS}", url: 'https://github.com/kaustubhchandra/GKE-terra-jen.git'
            }
        }

        stage('Build') {
            steps {
                // Your build steps go here
                echo 'Building the project...'
            }
        }

        // Add more stages as needed
    }
}
