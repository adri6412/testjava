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
                withCredentials([fortifyToken(credentialsId: 'fortifyjenkins', variable: 'FORTIFY_TOKEN')]) {
                    sh '''
                        # Traduzione del codice sorgente
                        sourceanalyzer -b anagrafica-service -clean
                        sourceanalyzer -b anagrafica-service -source 1.11 -cp "target/classes:target/dependency/*" src/main/java
                        
                        # Scansione del codice
                        sourceanalyzer -b anagrafica-service -scan -f results.fpr
                        
                        # Generazione del report
                        ReportGenerator -template "DISA STIG" -format pdf -f fortify-report.pdf -source results.fpr
                        
                        # Opzionale: Caricamento su Fortify Software Security Center (SSC)
                        # fortifyclient -url ${FORTIFY_SSC_URL} -authtoken ${FORTIFY_TOKEN} uploadFPR -file results.fpr -application "Anagrafica Service" -applicationVersion "1.0"
                    '''
                }
                
                // Archivia i risultati della scansione
                archiveArtifacts artifacts: 'results.fpr, fortify-report.pdf', allowEmptyArchive: true
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
