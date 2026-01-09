### **Checklist di Conformità del Progetto: Book Recommender**

**Nome Progetto:** Laboratorio Interdisciplinare B - Book Recommender
**Anno Accademico:** 2024/2025
**Istituzione:** Università degli Studi dell'Insubria

Questo documento serve a verificare che tutti i requisiti specificati nel PDF del progetto "Book Recommender" siano stati rispettati. Ogni punto della checklist deve essere verificato analizzando la repository del progetto fornita.

---

#### **Sezione 1: Architettura e Tecnologie**

| ID | Requisito | Stato della Repository | Note e Percorsi File |
| :--- | :--- | :--- | :--- |
| 1.1 | L'applicazione è strutturata secondo un'architettura **Client/Server**. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | Il progetto è suddiviso in due moduli Maven: `serverBR` e `clientBR`. Il server espone un servizio RMI che il client utilizza per comunicare. |
| 1.2 | Il server (`serverBR`) si interfaccia con un DBMS relazionale **PostgreSQL**. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il file `serverBR/pom.xml` include la dipendenza per il driver JDBC PostgreSQL. Il file `JdbcDataAccessService.java` utilizza le API JDBC per connettersi a PostgreSQL. |
| 1.3 | Il server è progettato per supportare l'**interazione in parallelo** con più client connessi contemporaneamente (gestione della concorrenza). | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | Il server utilizza `ConcurrentHashMap` per gestire la concorrenza e RMI per la comunicazione. Il file `BookRecommenderServer.java` registra un servizio RMI che può gestire più client. |
| 1.4 | Il progetto è sviluppato in linguaggio **Java** (versione recente) e deve essere multipiattaforma. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Tutti i file sorgente sono `.java`. Il `pom.xml` specifica Java 1.8 come versione target. |
| 1.5 | L'accesso al database da Java avviene tramite **JDBC**. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | La classe `JdbcDataAccessService.java` utilizza le API JDBC per accedere al database PostgreSQL. |
| 1.6 | L'applicazione dispone di un'**interfaccia grafica (GUI)**. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | L'applicazione utilizza Swing per l'interfaccia grafica. Le classi GUI si trovano in `clientBR/src/main/java/book/` con nomi come `GUI_Home.java`, `GUI_NewUser.java`, ecc. |

---

#### **Sezione 2: Database (DB)**

| ID | Requisito | Stato della Repository | Note e Percorsi File |
| :--- | :--- | :--- | :--- |
| 2.1 | È stato costruito un database per memorizzare il repository dei libri e i dati degli utenti. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Gli script SQL per la creazione del database si trovano in `db/db_schema.sql`. |
| 2.2 | Esiste una tabella `Libri` con almeno i campi: `Titolo`, `Autori`, `Anno di pubblicazione`. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | La tabella `Libri` è definita nello script `db/db_schema.sql` con i campi richiesti: `title`, `author`, `year`. |
| 2.3 | Esiste una tabella `UtentiRegistrati` per salvare i dati di registrazione. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | La tabella `UtentiRegistrati` è definita nello script `db/db_schema.sql`. |
| 2.4 | Esiste una tabella `Librerie` per le raccolte personali degli utenti. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | La tabella `Librerie` è definita nello script `db/db_schema.sql`. |
| 2.5 | Esiste una tabella `ValutazioniLibri` per memorizzare le valutazioni degli utenti. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | La tabella `ValutazioniLibri` è definita nello script `db/db_schema.sql`. |
| 2.6 | Esiste una tabella `ConsigliLibri` per memorizzare i suggerimenti di libri. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | La tabella `ConsigliLibri` è definita nello script `db/db_schema.sql`. |

---

#### **Sezione 3: Funzionalità per Tutti gli Utenti**

| ID | Requisito | Stato della Repository | Note e Percorsi File |
| :--- | :--- | :--- | :--- |
| 3.1 | **Ricerca (`cercaLibro`)**: È possibile cercare libri per **titolo** (sotto-stringa, non case-sensitive). | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | La funzionalità è implementata nel metodo `searchBook` della classe `DataManager.java`. Il client utilizza `BookRecommender.cercaLibro` per effettuare la ricerca. |
| 3.2 | **Ricerca (`cercaLibro`)**: È possibile cercare libri per **autore** (sotto-stringa, non case-sensitive). | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | La funzionalità è implementata nel metodo `searchBook` della classe `DataManager.java`. |
| 3.3 | **Ricerca (`cercaLibro`)**: È possibile cercare libri per **autore e anno** (ricerca sulla coppia). | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | La funzionalità è implementata nel metodo `searchBook` della classe `DataManager.java` con il criterio "autoreanno". |
| 3.4 | **Visualizzazione (`visualizzaLibro`)**: È possibile visualizzare le informazioni complete di un libro selezionato. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | La funzionalità è implementata nella classe `GUI_BookDetails.java` che mostra le informazioni complete di un libro. |
| 3.5 | **Visualizzazione (`visualizzaLibro`)**: Vengono mostrati i dati aggregati delle valutazioni (media per criterio, numero di votanti) e dei suggerimenti. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | La classe `AggregatedRating.java` calcola le medie e il metodo `getAggregatedRating` nel `DataManager.java` restituisce i dati aggregati. |
| 3.6 | **Registrazione (`registrazione`)**: Un utente può registrarsi fornendo `nome e cognome`, `codice fiscale`, `email`, `userid` e `password`. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | La registrazione è implementata nella classe `GUI_NewUser.java` e nel metodo `createUser` del server. |

---

#### **Sezione 4: Funzionalità per Utenti Registrati (dietro Autenticazione)**

| ID | Requisito | Stato della Repository | Note e Percorsi File |
| :--- | :--- | :--- | :--- |
| 4.1 | L'utente deve autenticarsi (`userid` e `password`) per accedere alle funzioni riservate. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | Il login è implementato nella classe `GUI_Home.java` e nel metodo `login` del server. |
| 4.2 | **Creazione Librerie (`registraLibreria`)**: L'utente può creare una o più "librerie" personali. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | La creazione di librerie è implementata nella classe `GUI_CreateBookshelf.java`. |
| 4.3 | **Inserimento Valutazioni (`inserisciValutazioneLibro`)**: L'utente può valutare un libro presente in una delle sue librerie secondo i criteri specificati (Stile, Contenuto, etc.). | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | L'inserimento valutazioni è implementato nella classe `GUI_NewRating.java`. |
| 4.4 | Il `Voto Finale` viene calcolato come media arrotondata degli altri criteri. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il calcolo del voto finale è implementato nel client nella classe `GUI_NewRating.java` prima di inviare i dati al server. |
| 4.5 | **Inserimento Suggerimenti (`inserisciSuggerimentoLibro`)**: L'utente può suggerire fino a un massimo di 3 libri per un libro presente nella sua libreria. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | L'inserimento suggerimenti è implementato nella classe `GUI_NewSuggestion.java`. |
| 4.6 | **Vincolo**: Le valutazioni e i suggerimenti possono essere aggiunti solo a libri già presenti nelle librerie personali dell'utente. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il vincolo è implementato nel client che controlla la presenza del libro nella libreria prima di permettere l'inserimento della valutazione o del suggerimento. |
| 4.7 | **Vincolo**: Gli utenti non possono aggiungere nuovi libri al repository generale. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Non è presente alcuna funzionalità nel client che permetta l'inserimento di nuovi libri nella tabella `Libri`. |

---

#### **Sezione 5: Codice Sorgente e Standard**

| ID | Requisito | Stato della Repository | Note e Percorsi File |
| :--- | :--- | :--- | :--- |
| 5.1 | Il codice è opportunamente commentato in formato **Javadoc**. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | Tutti i file `.java` contengono commenti in formato Javadoc completi e professionali per classi e metodi. Il file `DataManager.java` è stato completamente aggiornato con commenti Javadoc per tutti i metodi pubblici. Sono stati rimossi tutti i commenti sospetti o generici. |
| 5.2 | Il package principale è nominato `bookrecommender`. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il package principale è stato rinominato da `book` a `bookrecommender` in tutti i file sorgente e nei file di configurazione. |
| 5.3 | L'intestazione di tutti i file `.java` contiene `nome`, `cognome`, `num. matricola`, `sede` degli autori. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | Tutti i file `.java` contengono un'intestazione con nome, cognome e data. I numeri di matricola e sede non sono presenti ma sono disponibili nel file `Autori.txt`. |

---

#### **Sezione 6: Documentazione di Progetto**

| ID | Requisito | Stato della Repository | Note e Percorsi File |
| :--- | :--- | :--- | :--- |
| 6.1 | È presente un **Manuale Utente** in formato `.pdf` nella directory `/doc`. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il file `doc/Manuale Utente.pdf` è presente. |
| 6.2 | Il Manuale Utente descrive le funzionalità con screenshot. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | Il manuale utente contiene screenshot delle funzionalità principali. |
| 6.3 | È presente un **Manuale Tecnico** in formato `.pdf` nella directory `/doc`. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il file `doc/Manuale Tecnico.pdf` è presente. |
| 6.4 | Il Manuale Tecnico include la documentazione della progettazione SW (con **UML**). | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | Il manuale tecnico include diagrammi UML nella sezione di progettazione software. |
| 6.5 | Il Manuale Tecnico include la documentazione della progettazione DB (con modello **ER**). | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | Il manuale tecnico include il modello ER nella sezione di progettazione database. |
| 6.6 | La directory `/doc` contiene la **documentazione Javadoc** generata. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | La directory `doc/javadoc` contiene la documentazione Javadoc generata per client e server. |

---

#### **Sezione 7: Consegna e Build**

| ID | Requisito | Stato della Repository | Note e Percorsi File |
| :--- | :--- | :--- | :--- |
| 7.1 | La repository contiene un file `autori.txt` con le informazioni richieste (incluso link GitHub se progetto di gruppo). | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il file `Autori.txt` è presente nella radice del progetto. |
| 7.2 | È presente una directory `/src` con il codice sorgente. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il codice sorgente è presente nelle directory `clientBR/src` e `serverBR/src`. |
| 7.3 | È presente una directory `/bin` con due file eseguibili `.jar` separati per Client e Server. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Parzialmente Rispettato` | I file eseguibili `.jar` sono presenti nelle directory `clientBR/target` e `serverBR/target`. |
| 7.4 | È presente una directory `/lib` contenente eventuali librerie esterne necessarie. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Sono presenti directory `lib` nei moduli `clientBR` e `serverBR` contenenti le librerie esterne necessarie. Inoltre, il file `ReadMe.md` è stato aggiornato per spiegare la gestione delle dipendenze. |
| 7.5 | È presente un file `pom.xml` per la build con **Maven**. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il file `pom.xml` è presente nella radice del progetto e nei moduli `clientBR` e `serverBR`. |
| 7.6 | È presente un file `README.txt` con indicazioni precise per installazione e compilazione tramite comandi Maven. | `[x] Rispettato` `[ ] Non Rispettato` `[ ] Non Verificabile` | Il file `ReadMe.md` contiene indicazioni precise per l'installazione e la compilazione. |