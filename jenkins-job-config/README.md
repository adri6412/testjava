# Configurazione Job Jenkins per Anagrafica Service

Questa cartella contiene i file di configurazione necessari per impostare i job Jenkins per il progetto Anagrafica Service.

## Prerequisiti

Prima di installare i job, assicurati che Jenkins abbia i seguenti plugin installati:

1. Git Plugin
2. Pipeline Plugin
3. Maven Integration Plugin
4. JUnit Plugin
5. Docker Plugin
6. Email Extension Plugin
7. SonarQube Scanner Plugin
8. Timestamper Plugin
9. Workspace Cleanup Plugin

## Configurazione degli strumenti in Jenkins

Assicurati che in Jenkins siano configurati i seguenti strumenti:

1. JDK 17 (Manage Jenkins > Global Tool Configuration > JDK)
2. Maven 3.8.6 o superiore (Manage Jenkins > Global Tool Configuration > Maven)
3. Docker (installato sul server Jenkins)
4. SonarQube (Manage Jenkins > Configure System > SonarQube servers)

## Installazione dei job

### Metodo 1: Importazione manuale

1. Copia le cartelle `anagrafica-service` e/o `anagrafica-service-freestyle` nella directory `JENKINS_HOME/jobs/`
2. Riavvia Jenkins o ricarica la configurazione (Manage Jenkins > Reload Configuration from Disk)

### Metodo 2: Creazione tramite Jenkins CLI

Puoi utilizzare Jenkins CLI per creare i job:

```bash
java -jar jenkins-cli.jar -s http://your-jenkins-url/ create-job anagrafica-service < anagrafica-service/config.xml
java -jar jenkins-cli.jar -s http://your-jenkins-url/ create-job anagrafica-service-freestyle < anagrafica-service-freestyle/config.xml
```

### Metodo 3: Creazione tramite API REST

Puoi anche utilizzare l'API REST di Jenkins:

```bash
curl -X POST 'http://your-jenkins-url/createItem?name=anagrafica-service' --data-binary @anagrafica-service/config.xml -H "Content-Type:text/xml"
curl -X POST 'http://your-jenkins-url/createItem?name=anagrafica-service-freestyle' --data-binary @anagrafica-service-freestyle/config.xml -H "Content-Type:text/xml"
```

## Configurazione delle credenziali

Assicurati di configurare le seguenti credenziali in Jenkins:

1. `github-credentials`: Credenziali per accedere al repository GitHub
2. Credenziali per il Docker Registry (se necessario)
3. Credenziali per SonarQube (se necessario)

## Note aggiuntive

- Il job Pipeline utilizza il Jenkinsfile presente nel repository
- Il job Freestyle esegue gli stessi passaggi ma con configurazione diretta in Jenkins
- Entrambi i job sono configurati per eseguire una build ogni 15 minuti in caso di modifiche al repository

## Personalizzazione

Puoi personalizzare i file di configurazione in base alle tue esigenze specifiche:

- Modifica gli URL dei repository
- Aggiorna i nomi degli strumenti (JDK, Maven)
- Modifica i comandi Docker
- Aggiorna le configurazioni di notifica email