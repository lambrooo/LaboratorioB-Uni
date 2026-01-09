# Guida all'Avvio di Book Recommender

Questa guida illustra i passaggi necessari per configurare e avviare l'applicazione Book Recommender.

## 1. Prerequisiti

Prima di iniziare, assicurarsi di avere installato:

- **Java Development Kit (JDK) 8 o superiore**: L'applicazione è sviluppata in Java.
- **Apache Maven**: Utilizzato per la compilazione del progetto e la gestione delle dipendenze.
- **PostgreSQL**: L'applicazione utilizza PostgreSQL come database.

## 2. Configurazione del Database

### 2.1 Installazione di PostgreSQL
Se PostgreSQL non è installato, scaricarlo dal sito ufficiale: [https://www.postgresql.org/download/](https://www.postgresql.org/download/)

### 2.2 Creazione del Database
Prima di procedere, creare manualmente un database PostgreSQL vuoto. È possibile farlo tramite il tool `psql` o un'interfaccia grafica come pgAdmin.

**Tramite `psql`:**
```bash
psql -U postgres
```
(Inserire la password del superutente PostgreSQL quando richiesto)

Nel prompt di `psql`, creare il database:
```sql
CREATE DATABASE bookrecommender_db;
\q
```

### 2.3 Esecuzione dello Script di Schema
Una volta creato il database, è possibile configurare automaticamente lo schema tramite Maven.
Dalla cartella principale del progetto, eseguire:

```bash
mvn install -Psetup-db -Ddb.username=nome_utente -Ddb.name=bookrecommender_db -Ddb.password=password_utente
```

Sostituire `nome_utente` e `password_utente` con le proprie credenziali PostgreSQL.

### 2.4 Popolamento della Tabella Libri
La tabella `Libri` deve essere popolata con i dati iniziali. Dalla cartella principale del progetto, eseguire:

```bash
psql -U nome_utente -d bookrecommender_db -f db/populate_books.sql
```

## 3. Compilazione del Progetto

1. Aprire un terminale nella cartella principale del progetto
2. Eseguire il comando Maven per compilare tutti i moduli:

```bash
mvn clean install
```

Questo comando compila il codice sorgente e genera i file JAR eseguibili nelle rispettive cartelle `target`.

## 4. Avvio del Server

1. Dalla cartella principale del progetto, spostarsi nella cartella `serverBR`:
   ```bash
   cd serverBR
   ```

2. Avviare il server:
   ```bash
   java -jar target/serverBR-1.0-SNAPSHOT.jar
   ```

3. Il server richiederà le credenziali del database:
   ```
   === Book Recommender Server Database Configuration ===
   Enter database host (e.g., localhost): localhost
   Enter database port (e.g., 5432): 5432
   Enter database name (e.g., bookrecommender_db): bookrecommender_db
   Enter database username: nome_utente
   Enter database password: password_utente
   ```

Il server si avvierà e rimarrà in attesa di connessioni client. Lasciare aperta questa finestra del terminale.

## 5. Avvio del Client

1. Aprire un nuovo terminale (il server deve rimanere in esecuzione)
2. Dalla cartella principale del progetto, spostarsi nella cartella `clientBR`:
   ```bash
   cd clientBR
   ```

3. Avviare il client:
   ```bash
   java -jar target/clientBR-1.0-SNAPSHOT.jar
   ```

4. Il client chiederà l'indirizzo del server (es. `localhost` se in esecuzione sulla stessa macchina)

5. Una volta connesso, sarà possibile:
   - Cercare libri senza effettuare il login
   - Registrare un nuovo account
   - Effettuare il login con le proprie credenziali
   - Creare librerie personali
   - Valutare libri
   - Suggerire raccomandazioni

È possibile avviare più istanze del client contemporaneamente per testare l'accesso concorrente.

## 6. Supporto alla Concorrenza

Il server Book Recommender supporta l'accesso concorrente da parte di più utenti. I meccanismi implementati includono:

- **Connection Pooling (HikariCP)**: Per l'accesso efficiente al database
- **Strutture dati thread-safe (ConcurrentHashMap)**: Per la gestione sicura dei dati in memoria
- **Meccanismi di sincronizzazione (ReentrantReadWriteLock)**: Per operazioni atomiche complesse
- **Java RMI**: Supporta nativamente connessioni client multiple

## 7. Utenti Iniziali

Non sono presenti utenti pre-registrati. Per utilizzare le funzionalità riservate agli utenti registrati, è necessario **registrare un nuovo utente** tramite l'interfaccia grafica del client:

1. Nella schermata iniziale, cliccare su "Registrati"
2. Compilare tutti i campi richiesti
3. Una volta registrato, effettuare il login con le nuove credenziali

## 8. Gestione delle Dipendenze

Il progetto utilizza Apache Maven per la gestione delle dipendenze. Tutte le librerie necessarie vengono scaricate automaticamente durante la compilazione.

Per ambienti senza accesso a Internet, sono disponibili anche i file JAR precompilati:
- Dipendenze server: `serverBR/lib/`
- Dipendenze client: `clientBR/lib/`

## 9. Risoluzione dei Problemi

### Problemi Comuni

**1. Errore di Connessione Rifiutata**
- Verificare che il server sia in esecuzione
- Controllare che il client stia usando l'indirizzo corretto (es. `localhost` per esecuzione locale)

**2. Connessione al Database Fallita**
- Verificare che PostgreSQL sia in esecuzione
- Controllare che le credenziali siano corrette
- Verificare che il database esista

**3. Porta già in uso**
- La porta RMI predefinita (1099) potrebbe essere occupata
- Se il problema persiste, terminare i processi che utilizzano la porta 1099

**4. Errore di Classe Non Trovata**
- Verificare che tutti i file JAR siano presenti nel classpath
- I JAR "shaded" dovrebbero contenere tutte le dipendenze necessarie

**5. Errore RMI (Method Not Supported)**
- Assicurarsi di utilizzare versioni compatibili di client e server
- Ricompilare entrambi i moduli con `mvn clean install`
- Avviare sempre client e server dalle rispettive cartelle `target/`

### Verifica dello Stato del Server
Per verificare che il server sia avviato correttamente:
1. Cercare il messaggio "BookRecommender Server ready." nel terminale del server
2. Verificare che non compaiano messaggi di errore dopo l'avvio
3. Provare a connettersi con un client

### Verifica della Connessione al Database
Prima di avviare il server, verificare l'accesso al database:
```bash
psql -h localhost -p 5432 -U nome_utente -d bookrecommender_db
```

Se la connessione ha successo, anche il server dovrebbe potersi connettere con le stesse credenziali.

---

**Autori:** Matteo Mantica e Leonardo Lambruschi
**Corso:** Laboratorio Interdisciplinare B - Università dell'Insubria
**Versione:** 1.0.0
