pipeline {
    agent any
    
    tools {
        // Specifica la versione di Maven da utilizzare
        maven 'Maven 3.8.6'
        // Specifica la versione di JDK da utilizzare (deve essere Java 17 o superiore)
        jdk 'JDK 17'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout del codice dal repository Git
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                // Compila il progetto con Maven
                sh 'mvn clean package -DskipTests'
            }
        }
        
        stage('Test') {
            steps {
                // Esegui i test
                sh 'mvn test'
            }
            post {
                always {
                    // Pubblica i risultati dei test
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Code Quality') {
            steps {
                // Analisi del codice con SonarQube
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                // Costruisci l'immagine Docker
                sh 'docker build -t anagrafica-service:${BUILD_NUMBER} .'
                sh 'docker tag anagrafica-service:${BUILD_NUMBER} anagrafica-service:latest'
            }
        }
        
        stage('Deploy to Development') {
            steps {
                // Deploy in ambiente di sviluppo
                sh 'docker stop anagrafica-service-dev || true'
                sh 'docker rm anagrafica-service-dev || true'
                sh 'docker run -d --name anagrafica-service-dev -p 8080:8080 --network app-network -e SPRING_PROFILES_ACTIVE=dev anagrafica-service:latest'
            }
        }
    }
    
    post {
        success {
            // Azioni da eseguire in caso di successo
            echo 'Build completata con successo!'
            // Notifica via email o Slack
            emailext (
                subject: "Build Successful: ${currentBuild.fullDisplayName}",
                body: "La build è stata completata con successo: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
        failure {
            // Azioni da eseguire in caso di fallimento
            echo 'Build fallita!'
            // Notifica via email o Slack
            emailext (
                subject: "Build Failed: ${currentBuild.fullDisplayName}",
                body: "La build è fallita: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
        always {
            // Pulizia workspace
            cleanWs()
        }
    }
}