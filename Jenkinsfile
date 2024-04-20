pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') { 
            steps {
                bat 'mvn -B -DskipTests clean package' 
            }
        }
	stage('Deliver') { 
            steps {
                bat 'jenkins/scripts/deliver.sh' 
            }
        }
    }
}