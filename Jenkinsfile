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
    }
}