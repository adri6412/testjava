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
        
        stage('Fortify Scan') {
            steps {
                // Esegui la scansione di sicurezza con Fortify
                // Pulizia dell'ambiente Fortify
                fortifyClean buildID: 'anagrafica-service'
                
                // Traduzione del codice sorgente
                fortifyTranslate buildID: 'anagrafica-service', 
                                 projectScanType: fortifyJava(javaVersion: '1.11', 
                                                             javaSrcFiles: 'src/main/java/**/*.java',
                                                             javaClasspath: 'target/classes:target/dependency/*')
                
                // Scansione del codice
                fortifyScan buildID: 'anagrafica-service', resultsFile: 'results.fpr'
                
                // Caricamento su Fortify Software Security Center (SSC)
                fortifyUpload appName: 'Anagrafica Service', 
                              appVersion: '1.0', 
                              resultsFile: 'results.fpr'
                
                // Archivia i risultati della scansione
                archiveArtifacts artifacts: 'results.fpr', allowEmptyArchive: true
            }
        }
        
        // Le fasi Docker sono state rimosse per semplificare la pipeline
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
