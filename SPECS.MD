# Specifiche di Progetto: Book Recommender
**Università degli Studi dell'Insubria**  
Dipartimento di Scienze Teoriche e Applicate  
**Corso:** Laboratorio Interdisciplinare B  
**a.a.** 2024/2025  
**Contatto:** loris.bozzato@uninsubria.it

---

## Laboratorio Interdisciplinare - Cos'è

*   **Obiettivi:** mettere a fattor comune e **in pratica** le conoscenze acquisite nei corsi di Progettazione, Sviluppo Concorrente e Distribuito, e Basi Dati.
*   **Come:** progettando e sviluppando un progetto software reale.

---

## Il Progetto – Book Recommender

### Descrizione Generale
Un sistema per la **valutazione e raccomandazione di libri**, in grado di permettere agli utenti registrati di inserire recensioni e a tutti gli utenti di consultare le valutazioni e ricevere consigli di lettura.

### Dettagli sulla Valutazione
*   I libri sono annotati rispetto a diversi criteri di valutazione, indicando la valutazione del criterio su una scala che va da 1 (minimo) a 5 (massimo).
*   È possibile inserire una recensione testuale opzionale (max 256 caratteri liberi) per ognuno dei criteri.
*   Il valore di **Voto Finale** è calcolato come la media (arrotondata) degli altri criteri.

| Criterio Valutazione | Spiegazione | Score | Note (max 256 chars) |
| :--- | :--- | :---: | :--- |
| Stile | Valutazione sullo stile di scrittura del libro | 1 ..... 5 | Opzionale |
| Contenuto | Valutazione sui contenuti del libro | 1 ..... 5 | Opzionale |
| Gradevolezza | Valutazione sul gradimento nella lettura del libro | 1 ..... 5 | Opzionale |
| Originalità | Valutazione sulla originalità del libro | 1 ..... 5 | Opzionale |
| Edizione | Valutazione sulla qualità dell'edizione del libro | 1 ..... 5 | Opzionale |
| **Voto Finale** | **Valutazione complessiva del libro** | **1 ..... 5** | *Calcolato automaticamente* |

*   Oltre a questo, gli utenti possono associare **max. 3 libri suggeriti** per il libro corrente.

### Funzionalità Principali
A partire da un repository di libri (da costruire), l'applicazione «Book Recommender» permette:

#### Utenti registrati
*   di creare una o più «librerie» personali per raccogliere i propri libri di interesse, aggiungere annotazioni ai singoli libri secondo i criteri dati nella tabella precedente e suggerire libri correlati (selezionati dalle proprie librerie).

#### Tutti gli utenti
*   di mostrare, per ciascun libro, un prospetto riassuntivo sulle valutazioni degli utenti in forma aggregata e i libri consigliati dagli utenti.

### Creazione del Repository di Libri
Prima di progettare l'applicazione è necessario costruire un Database con una tabella `Libri` dove memorizzare il repository dei libri, che deve contenere almeno i seguenti campi:
*   Titolo
*   Autori
*   Anno di pubblicazione

**Campi opzionali:**
*   Editore
*   Categoria

> **NOTA:** un file *draft* da cui è possibile estrarre dati bibliografici viene fornito dal docente e reso disponibile sulla pagina e-learning dell'insegnamento (BooksDatasetClean.csv, disponibile da: https://www.kaggle.com/datasets/elvinrustam/books-dataset/data)

---

## Riepilogo Funzionalità Utente

L'applicazione permette di:

1.  **Consultare le informazioni** del repository dei libri (accesso libero).
2.  **Registrarsi** all'applicazione.
3.  **Creare più «librerie»** (cioè raccolte di libri) personali (solo dopo login).
4.  **Inserire le valutazione** dei libri inseriti nelle proprie «librerie», secondo i criteri forniti (solo utenti registrati, dopo login).
5.  **Inserire consigli di libri** (max 3) per i libri contenuti nella proprie librerie (solo utenti registrati, dopo login).

---

## Specifiche Funzionali Dettagliate

#### 1) Per consultare le informazioni di ogni libro (non è necessario login o registrazione):

**a. Funzionalità di ricerca `cercaLibro()`**
*   **Ricerca per titolo**: prende in input una stringa di caratteri e restituisce i libri nel cui titolo compare la stringa di caratteri.
*   **Ricerca per autore**: prende in input un autore e restituisce i titoli dei libri in cui questo compare tra gli autori.
*   **Ricerca per autore e anno**: prende in input un autore e un anno e restituisce i titoli dei libri corrispondenti ad autore e anno ricercati.

> **Note:**
> - Tutte le ricerche sono **non case sensitive** e funzionano con **sotto-stringhe** del testo cercato.
> - La ricerca autore/anno va fatta sulla coppia, non indipendentemente sulle due componenti.

**b. Funzionalità di selezione e visualizzazione `visualizzaLibro()`**
*   Una volta trovato il libro di interesse, deve essere possibile visualizzare tutte le informazioni relative al libro selezionato.
*   Tra le informazioni deve esserci un prospetto riassuntivo delle valutazioni associate al libro (o l'indicazione che per il libro ricercato non sono disponibili valutazioni degli utenti) e i libri consigliati per il libro corrente (o l'indicazione che non ci sono consigli dagli utenti).
*   Se presenti, si potranno visualizzare tali dati in forma aggregata (es. numero di utenti per valutazione e media dei punteggi per ogni criterio; per ogni libro consigliato, quanti utenti lo hanno suggerito), oltre agli eventuali commenti lasciati dagli utenti.

#### 2) Per registrarsi all'applicazione, tramite la funzione `registrazione()`, l'utente deve inserire:
*   nome e cognome
*   codice fiscale
*   indirizzo di posta elettronica
*   userid
*   password per accedere al sistema

I dati della registrazione devono essere salvati in una tabella del DB denominata `UtentiRegistrati`.

#### 3) Per creare le proprie «librerie» (raccolte di libri) personali, l'utente registrato:
a. deve autenticarsi tramite userid e password fornite al momento della registrazione.
b. tramite la funzione `registraLibreria()` deve inserire:
    *   Nome della libreria
    *   Elenco dei libri associati alla libreria

I dati di ogni libreria per ciascun utente registrato sono memorizzati in una tabella del DB denominata `Librerie`.

#### 4) Per inserire valutazioni di libri, l'utente registrato:
a. deve autenticarsi tramite userid e password fornite al momento della registrazione.
b. può ricercare e selezionare libri contenuti nelle proprie librerie personali.
c. può ora usare la funzione `inserisciValutazioneLibro()`.

(Il libro e le valutazioni associate dall'utente sono memorizzati nella tabella `ValutazioniLibri`).

| Criterio Valutazione | Spiegazione | Score | Note (max 256 chars) |
| :--- | :--- | :---: | :--- |
| Stile | Valutazione sullo stile di scrittura del libro | 1 ..... 5 | Opzionale |
| Contenuto | Valutazione sui contenuti del libro | 1 ..... 5 | Opzionale |
| Gradevolezza | Valutazione sul gradimento nella lettura del libro | 1 ..... 5 | Opzionale |
| Originalità | Valutazione sulla originalità del libro | 1 ..... 5 | Opzionale |
| Edizione | Valutazione sulla qualità dell'edizione del libro | 1 ..... 5 | Opzionale |
| **Voto Finale** | **Valutazione complessiva del libro** | **1 ..... 5** | *Calcolato automaticamente* |

#### 5) Per inserire suggerimenti di libri, l'utente registrato:
a. deve autenticarsi tramite userid e password fornite al momento della registrazione.
b. può ricercare e selezionare libri contenuti nelle proprie librerie personali.
c. può ora usare la funzione `inserisciSuggerimentoLibro()`.

(Il libro e i libri suggeriti associati dall'utente sono memorizzati nella tabella del DB `ConsigliLibri`).

> **Note importanti:**
> - I libri su cui si possono aggiungere valutazioni e consigli devono essere sempre libri **all'interno delle librerie dell'utente**.
> - **Non si devono poter aggiungere altri libri al repository**.

### Menù Iniziale dell'Applicazione
All'avvio l'applicazione mostra un menù iniziale dove:

**Tutti possono:**
*   Cercare libri tramite titolo, autore, anno.
*   Visualizzare le valutazioni e consigli associati ad un libro selezionato.
*   Registrarsi all'applicazione.

**Gli utenti registrati possono:**
*   Effettuare il login all'applicazione.
*   Creare nuove librerie ed inserirvi libri.
*   Inserire valutazioni per un libro selezionato.
*   Inserire suggerimenti per un libro selezionato.

---

## Architettura e Aspetti Chiave

### Architettura della Piattaforma
La **Piattaforma Book Recommender (BR)** consiste di:
1.  Un modulo `serverBR`, che interfacciandosi con un DBMS relazionale (**PostgreSQL**) fornisce servizi di back-end.
2.  Un modulo `clientBR`, che fornisce tutti i servizi e funzionalità designate per gli utilizzatori dell'applicazione.

**Al lancio di `serverBR` deve essere richiesto di specificare:**
1.  Le credenziali per accedere al `dbBR` (database di supporto all'esecuzione dei servizi della piattaforma BR).
2.  L'host del DB.

Una volta lanciato `serverBR`, questo dovrà rimanere in attesa di richieste di connessione da parte di client `clientBR`.

> L'applicazione BR dovrà essere realizzata in modo tale da supportare **l'interazione in parallelo con più utenti** connessi alla piattaforma da postazioni (client) differenti.

### Aspetti Chiave
*   **Strutture dati:**
    *   `Libri`
    *   `UtentiRegistrati`
    *   `Librerie`
    *   `ValutazioniLibri`
    *   `ConsigliLibri`
*   **Cosa e come memorizzare su DB** (progettazione e realizzazione).
*   **Interfaccia utente** (interfaccia grafica).
*   **Gestione della Concorrenza** (i servizi dell'applicazione vengono erogati in parallelo a più utenti, e possono verificarsi accessi concorrenti a risorse condivise).

---

## To-Do List del Progetto

#### 1. Progettazione della Soluzione
*   Le attività di analisi e progettazione devono essere adeguatamente documentate facendo uso del **linguaggio UML** per l'applicazione software e del modello **Entity-Relationship (ER)** per il database.
*   È richiesto di progettare l'applicazione avvalendosi dove possibile dell'uso di **design patterns**, e di realizzare l'applicazione con un'opportuna **interfaccia grafica**, usando il linguaggio Java e gli strumenti utili.
*   Progettare e realizzare un database utilizzando **PostgreSQL** per la sua implementazione (http://www.postgresql.org) e **JDBC** per l'accesso alla base di dati da programma Java (http://jdbc.postgresql.org).

#### 2. Sviluppo della Soluzione – Database
*   Si ristrutturi, se necessario, secondo le metodologie di progettazione i requisiti descritti. Si scelgano le metodologie per la costruzione dello schema ER, motivando le scelte fatte.
*   Si definisca lo schema concettuale ER per il database, evidenziando le entità e le associazioni di interesse, nonché i vincoli di cardinalità e di identificazione, motivando le scelte effettuate. Altri eventuali vincoli devono essere espressi in linguaggio naturale.
*   Si effettui la ristrutturazione dello schema ER motivando le scelte effettuate. E' richiesto di produrre un documento di analisi dei requisiti ristrutturato e documentazione associata allo schema ER (ristrutturato e non), con eventuale specifica di vincoli in linguaggio naturale.
*   Si effettui la traduzione dello schema ER ristrutturato in un equivalente schema relazionale. E' richiesto di produrre la documentazione associata allo schema relazionale derivato dallo schema concettuale.
*   Si realizzi il database utilizzando PostgreSQL, e SQL per la definizione dei dati, l'implementazione dei vincoli identificati, e la manipolazione dei dati, secondo le operazioni previste dall'applicazione.
*   Documentare gli script SQL necessari alla creazione della base di dati e dei vincoli definiti sui dati e le query SQL a supporto dei servizi erogati da Interfacce di Programmazione.

#### 3. Sviluppo della Soluzione - Codice Sorgente
1.  Il progetto deve essere sviluppato in linguaggio **Java** (versione recente) e deve essere **multipiattaforma**.
2.  Il codice deve essere opportunamente commentato in formato **Javadoc**.
3.  Il package `bookrecommender` deve essere definito e deve contenere le relative classi (ulteriori package sono ammessi).
4.  Sono richiesti due eseguibili (`.jar`) separati per l'esecuzione di Client e Server.
5.  L'intestazione di tutti i file `*.java` deve contenere **nome, cognome, num. matricola, sede (VA o CO)** degli autori del progetto.

#### 4. Documentazione di Progetto
*   Vanno prodotti 2 documenti distinti:
    *   Manuale Utente
    *   Manuale Tecnico
*   (Seguirà lezione ad hoc su come scrivere un corretto documento utente e manuale tecnico).

---

## Consegna

Il progetto deve essere consegnato come **link a una directory OneDrive dell'Università o Google Drive** del project manager e al link si dovrà trovare una **dir compressa** denominata con `cognome_matricola` del project manager del team. La cartella compressa dovrà contenere:

*   **File `autori.txt`**: file di testo contenente cognome, nome, numero di matricola e sede (VA o CO) di ogni membro del team; in caso di progetto svolto in gruppo, link al repository GitHub di progetto.
*   **Directory `/src`**: codice sorgente del progetto.
*   **Directory `/bin`**: files eseguibili (`.jar`) dell'applicazione.
*   **Directory `/doc`**: manuale utente e manuale tecnico in formato `.pdf`, JavaDoc generata.
*   **Directory `/lib`**: eventuali librerie esterne per compilazione e esecuzione.
*   **File `pom.xml`**: file di build di Maven (https://maven.apache.org/) per compilare il progetto, creare database, generare JavaDoc ecc.
*   **File `README.txt`**: file di testo con indicazioni precise sull'installazione e sulla compilazione, specificando i comandi Maven da utilizzare.

### Modalità di Consegna
*   La consegna avviene tramite **form online reso disponibile sulla piattaforma E-learning** (Verranno richiesti: link al file del progetto, dati dei componenti del gruppo, GitHub del gruppo).
*   **Responsabile della consegna sarà il Project Manager (identificato tramite email istituzionale di ateneo)**.
*   L'apertura del form e la data di consegna verranno comunicate per ogni appello tramite annuncio sulla piattaforma E-learning.
*   (La consegna del progetto è prevista indicativamente 10 giorni prima della data di appello d'esame).

---

## Valutazione

### Discussione Orale
In fase di discussione orale verrà:
*   richiesto allo studente di saper **argomentare in modo opportuno le scelte** progettuali, algoritmiche, e implementative adottate.
*   verificata l'effettiva **padronanza delle tecniche** utilizzate attraverso una serie di domande.

### Aspetti Considerati
Nella valutazione verranno considerati questi aspetti:

*   **Applicazione sviluppata:**
    *   Gestione architettura distribuita Client/Server e concorrenza.
    *   Aderenza delle funzionalità implementate ai requisiti.
    *   Controllo degli errori e gestione delle eccezioni.
    *   Implementazione e gestione del DB.
    *   Navigabilità della GUI.
    *   Struttura e completezza della consegna.

*   **Documentazione:**
    *   Struttura e completezza dei manuali.
    *   Linguaggio adatto per manuale tecnico e utente.
    *   Manuale utente: descrizione funzionalità (con screenshot).
    *   Manuale tecnico: scelte architetturali, algoritmiche e strutture dati.
    *   Manuale tecnico: documentazione progettazione SW (con UML).
    *   Manuale tecnico: documentazione progettazione DB (con ER).
    *   Documentazione JavaDoc.

---

## Dubbi e Domande

Per ogni dubbio sulle specifiche che non sia chiarito durante la lezione di presentazione del progetto, è possibile scrivere sul forum dedicato nella pagina del corso.

Il forum si chiama **"Specifiche progetto Lab B"**.