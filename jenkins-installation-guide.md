# Guida all'installazione di Jenkins per Anagrafica Service

Questa guida fornisce istruzioni dettagliate per installare e configurare Jenkins per il progetto Anagrafica Service.

## Installazione di Jenkins

### Su Windows

1. Scarica l'installer di Jenkins da [jenkins.io](https://www.jenkins.io/download/)
2. Esegui l'installer e segui le istruzioni a schermo
3. Jenkins sarà disponibile all'indirizzo `http://localhost:8080/`

### Su Linux (Ubuntu/Debian)

```bash
# Aggiungi la chiave del repository Jenkins
wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -

# Aggiungi il repository Jenkins
sudo sh -c 'echo deb https://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'

# Aggiorna i repository e installa Jenkins
sudo apt-get update
sudo apt-get install jenkins

# Avvia il servizio Jenkins
sudo systemctl start jenkins
sudo systemctl enable jenkins
```

### Su Docker

```bash
# Crea un volume per i dati di Jenkins
docker volume create jenkins-data

# Avvia Jenkins
docker run -d --name jenkins -p 8080:8080 -p 50000:50000 -v jenkins-data:/var/jenkins_home jenkins/jenkins:lts
```

## Configurazione iniziale di Jenkins

1. Accedi a Jenkins all'indirizzo `http://localhost:8080/`
2. Recupera la password amministratore iniziale:
   - Su Windows: `C:\Program Files\Jenkins\secrets\initialAdminPassword`
   - Su Linux: `sudo cat /var/lib/jenkins/secrets/initialAdminPassword`
   - Su Docker: `docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword`
3. Installa i plugin consigliati
4. Crea un utente amministratore
5. Configura l'URL di Jenkins

## Installazione dei plugin necessari

Vai su "Manage Jenkins" > "Manage Plugins" > "Available" e installa i seguenti plugin:

1. Git Plugin
2. Pipeline Plugin
3. Maven Integration Plugin
4. JUnit Plugin
5. Docker Plugin
6. Email Extension Plugin
7. SonarQube Scanner Plugin
8. Timestamper Plugin
9. Workspace Cleanup Plugin
10. Build User Vars Plugin

## Configurazione degli strumenti

### JDK

1. Vai su "Manage Jenkins" > "Global Tool Configuration" > "JDK"
2. Clicca su "Add JDK"
3. Nome: `JDK 17`
4. Deseleziona "Install automatically" se JDK è già installato sul server
5. JAVA_HOME: Percorso all'installazione di JDK 17
   - Windows: `C:\Program Files\Java\jdk-17`
   - Linux: `/usr/lib/jvm/java-17-openjdk-amd64`

### Maven

1. Vai su "Manage Jenkins" > "Global Tool Configuration" > "Maven"
2. Clicca su "Add Maven"
3. Nome: `Maven 3.8.6`
4. Seleziona "Install automatically" o specifica il percorso di installazione

### Docker

Assicurati che Docker sia installato sul server Jenkins e che l'utente Jenkins possa eseguire comandi Docker.

Su Linux:
```bash
sudo usermod -aG docker jenkins
sudo systemctl restart jenkins
```

### SonarQube

1. Vai su "Manage Jenkins" > "Configure System" > "SonarQube servers"
2. Clicca su "Add SonarQube"
3. Nome: `SonarQube`
4. URL del server: `http://sonarqube-server:9000`
5. Aggiungi le credenziali di autenticazione se necessario

## Configurazione delle credenziali

1. Vai su "Manage Jenkins" > "Manage Credentials" > "Jenkins" > "Global credentials" > "Add Credentials"
2. Aggiungi le seguenti credenziali:
   - ID: `github-credentials` - Tipo: Username with password - Per accedere al repository GitHub
   - Altre credenziali necessarie per Docker Registry, SonarQube, ecc.

## Installazione dei job

1. Copia le cartelle `anagrafica-service` e/o `anagrafica-service-freestyle` nella directory `JENKINS_HOME/jobs/`
2. Riavvia Jenkins o ricarica la configurazione (Manage Jenkins > Reload Configuration from Disk)

## Configurazione delle notifiche email

1. Vai su "Manage Jenkins" > "Configure System" > "E-mail Notification"
2. Configura il server SMTP e l'indirizzo email del mittente
3. Vai su "Extended E-mail Notification" e configura le impostazioni avanzate

## Configurazione della rete Docker

Crea una rete Docker per i container dell'applicazione:

```bash
docker network create app-network
```

## Test della configurazione

1. Vai alla dashboard di Jenkins
2. Seleziona il job `anagrafica-service` o `anagrafica-service-freestyle`
3. Clicca su "Build Now" per avviare una build manuale
4. Verifica che tutte le fasi della build vengano completate correttamente

## Risoluzione dei problemi comuni

### Problemi di permessi Docker

Se Jenkins non riesce ad eseguire comandi Docker, verifica che l'utente Jenkins sia nel gruppo Docker e riavvia Jenkins.

### Problemi di connessione a GitHub

Verifica che le credenziali GitHub siano configurate correttamente e che il server Jenkins possa accedere a GitHub.

### Problemi con Maven

Verifica che Maven sia configurato correttamente e che possa accedere ai repository Maven necessari.

### Problemi con i test

Se i test falliscono, verifica che il database MySQL sia configurato correttamente e accessibile.