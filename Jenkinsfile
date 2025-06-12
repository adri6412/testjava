pipeline {
    agent any
    
    tools {
        // Utilizza la versione di Maven disponibile nel tuo ambiente Jenkins
        // Se non hai un'installazione di Maven configurata, puoi rimuovere questa riga
        // e utilizzare il Maven wrapper del progetto
        
        // Utilizza la JDK 11 disponibile nel tuo ambiente Jenkins
        jdk 'OJDK11'
        maven 'Maven'  // Deve corrispondere al nome del tool configurato
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
        }
        
        stage('Code Quality') {
            when {
                // Esegui questa fase solo se SonarQube è configurato
                expression { return false } // Disabilitato per ora, cambia in 'true' quando SonarQube è configurato
            }
            steps {
                // Analisi del codice con SonarQube
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage('Build Docker Image') {
            when {
                // Esegui questa fase solo se Docker è installato
                expression {
                    try {
                        sh(script: 'docker --version', returnStatus: true) == 0
                    } catch (Exception e) {
                        return false
                    }
                }
            }
            steps {
                // Costruisci l'immagine Docker
                sh 'docker build -t anagrafica-service:${BUILD_NUMBER} .'
                sh 'docker tag anagrafica-service:${BUILD_NUMBER} anagrafica-service:latest'
            }
        }
        
        stage('Deploy to Development') {
            when {
                // Esegui questa fase solo se Docker è installato e l'immagine è stata creata
                expression {
                    try {
                        sh(script: 'docker --version', returnStatus: true) == 0
                    } catch (Exception e) {
                        return false
                    }
                }
            }
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
