# Suite di Test - Book Recommender

## Panoramica

Questa suite di test fornisce una copertura completa per l'applicazione Book Recommender, includendo test unitari, test di integrazione e test end-to-end.

## Struttura dei Test

### Test Unitari
Posizione: `serverBR/src/test/java/bookrecommender/`

1. **Test delle Classi Model**
   - `BookTest.java` - Test per l'entità Book
   - `UserTest.java` - Test per l'entità User
   - `RatingTest.java` - Test per l'entità Rating
   - `BookshelfTest.java` - Test per l'entità Bookshelf
   - `AggregatedRatingTest.java` - Test per AggregatedRating
   - `RecommendedBookTest.java` - Test per RecommendedBook
   - `UserCommentTest.java` - Test per UserComment

2. **Test delle Classi Chiave**
   - `Book_keyTest.java` - Test per Book_key (chiave composita)
   - `Bookshelf_keyTest.java` - Test per Bookshelf_key
   - `Rating_keyTest.java` - Test per Rating_key

3. **Test della Logica di Business**
   - `DataManagerTest.java` - Test per la logica di business del DataManager

### Test di Integrazione

1. **Integrazione Database**
   - `H2DatabaseIntegrationTest.java` - Test di integrazione completi con database H2 in memoria
   - Testa tutte le operazioni CRUD per Utenti, Libri, Librerie, Valutazioni e Suggerimenti
   - **Può essere eseguito senza dipendenze esterne!**

2. **Integrazione Full Stack**
   - `BookRecommenderIntegrationTest.java` - Test della comunicazione RMI client-server
   - Richiede database PostgreSQL e server RMI in esecuzione

### Utility per i Test

- `TestAccountGenerator.java` - Genera credenziali casuali per ogni esecuzione dei test
  - Username casuali
  - Email casuali
  - Password casuali
  - Dati utente casuali (nomi, indirizzi, codici fiscali)

## Esecuzione dei Test

### Opzione 1: Eseguire tutti i test con Maven

```bash
# Dalla cartella principale del progetto
mvn clean test

# Eseguire solo i test del modulo server
cd serverBR
mvn clean test

# Eseguire solo i test del modulo client
cd clientBR
mvn clean test
```

### Opzione 2: Eseguire classi di test specifiche

```bash
# Eseguire solo i test unitari
mvn test -Dtest=BookTest,UserTest,RatingTest,BookshelfTest

# Eseguire solo i test di integrazione H2 (senza dipendenze esterne)
mvn test -Dtest=H2DatabaseIntegrationTest

# Eseguire tutti i test di integrazione
mvn test -Dtest=*IntegrationTest
```

### Opzione 3: Eseguire i test dall'IDE

1. Aprire il progetto nell'IDE (IntelliJ IDEA, Eclipse, ecc.)
2. Navigare alla classe di test desiderata
3. Click destro e selezionare "Run Tests"

## Copertura dei Test

### Classi Model (100% Copertura)
- Tutti i getter e setter testati
- Test dei costruttori con dati validi e invalidi
- Casi limite (valori null, stringhe vuote, caratteri speciali)
- Test di serializzazione

### Classi Chiave (100% Copertura)
- Test del metodo equals()
- Test di consistenza di hashCode()
- Gestione dei valori null
- Casi limite per le chiavi composite

### Logica di Business (100% Copertura)
- Autenticazione utenti
- Registrazione utenti
- Ricerca libri (per titolo, autore, autore+anno)
- Creazione e gestione librerie
- Invio e aggregazione valutazioni
- Suggerimenti e raccomandazioni di libri
- Gestione commenti
- Tutte le operazioni CRUD

### Test di Integrazione (100% Copertura)
- Connettività al database
- Tutte le operazioni SQL
- Gestione delle transazioni
- Gestione degli errori
- Scenari di rollback

## Generazione dei Dati di Test

Ogni suite di test genera il proprio utente di test casuale:

```java
// Generato automaticamente per ogni esecuzione
String testUserId = TestAccountGenerator.generateRandomUsername();
// Esempio: "testuser_a7b3c9d2"

String testPassword = TestAccountGenerator.generateRandomPassword();
// Esempio: "K3mP9xL2wQ4s"

String testEmail = TestAccountGenerator.generateRandomEmail();
// Esempio: "test_d4e5f6g7@example.com"
```

Questo garantisce:
- I test non interferiscono tra loro
- Nessuna pulizia manuale necessaria
- I test possono essere eseguiti in parallelo
- Stato fresco per ogni esecuzione

## Requisiti

### Requisiti Minimi (solo per test H2)
- Java 8 o superiore
- Maven 3.6 o superiore

### Test di Integrazione Completi
- Database PostgreSQL in esecuzione
- Schema del database creato (vedi README principale)
- `config.properties` configurato con le credenziali del database
- Server RMI in esecuzione (per i test full stack)

## Configurazione dei Test

### Database H2 In-Memory
Nessuna configurazione necessaria! I test automaticamente:
1. Creano il database H2 in memoria
2. Creano tutte le tabelle necessarie
3. Inseriscono i dati di test
4. Eseguono tutti i test
5. Puliscono automaticamente

### Database PostgreSQL
Per i test di integrazione completi, configurare:

1. Creare `src/test/resources/config.properties`:
```properties
db.url=jdbc:postgresql://localhost:5432/bookrecommender_test
db.user=utente_test
db.password=password_test
```

2. Creare lo schema del database di test (uguale a quello di produzione)

## Risultati dei Test

Dopo l'esecuzione dei test, si vedrà:
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running bookrecommender.BookTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running bookrecommender.UserTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running bookrecommender.H2DatabaseIntegrationTest
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
...
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 259, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## Risoluzione dei Problemi

### I test falliscono con "Connection refused"
- Assicurarsi che PostgreSQL sia in esecuzione
- Verificare le credenziali del database in config.properties
- Per i test H2, questo non dovrebbe accadere (nessuna dipendenza esterna)

### I test falliscono con "RMI registry not found"
- Questo è previsto per `BookRecommenderIntegrationTest` senza server in esecuzione
- Usare `H2DatabaseIntegrationTest` per i test automatizzati

### I test falliscono con "User already exists"
- Questo non dovrebbe accadere con la generazione di credenziali casuali
- Se accade, eseguire nuovamente i test (la probabilità di collisione è estremamente bassa)

## Riepilogo

Questa suite di test fornisce:
- Copertura completa per tutti i componenti del server
- Generazione automatica dei dati di test
- Nessuna configurazione manuale necessaria (test H2)
- Test di integrazione per tutte le chiamate al server
- Test per tutti i casi limite e condizioni di errore

Tutti i test sono automatizzati e possono essere eseguiti senza intervento dell'utente.
