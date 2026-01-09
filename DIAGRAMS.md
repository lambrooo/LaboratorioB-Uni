**Nota bene**: i diagrammi sono scritti in linguaggio [Mermaid](https://mermaid.js.org/).  
Per la corretta visualizzazione si consiglia di aprire questo file direttamente su GitHub.

## Diagramma delle classi

```mermaid
classDiagram
    class User {
        -String Name
        -String Surname
        -String FiscalCode
        -String Address
        -String Email
        -String Userid
        -String Password
        +User()
        +User(String Name, String Surname, String FiscalCode, String Address, String Email, String Userid, String Password)
        +getName() String
        +getSurname() String
        +getFiscalCode() String
        +getAddress() String
        +getEmail() String
        +getUserid() String
        +getPassword() String
    }

    class Book {
        -String Title
        -String Author
        -String Year
        -String Genre
        +Book()
        +Book(String Title, String Author, String Year, String Genre)
        +getTitle() String
        +getAuthor() String
        +getGenre() String
        +getYear() String
    }

    class Bookshelf {
        -String Name
        -String Userid
        -ArrayList~String~ Books
        +Bookshelf()
        +Bookshelf(String Name, String Userid, ArrayList~String~ Books)
        +getName() String
        +getUserid() String
        +getBooks() ArrayList~String~
    }

    class Rating {
        -String BookshelfName
        -String userid
        -String Book
        -int Stile
        -int Contenuto
        -int Gradevolezza
        -int Originalita
        -int Edizione
        -int VotoFinale
        -String styleComment
        -String contentComment
        -String pleasantnessComment
        -String originalityComment
        -String editionComment
        +Rating(String BookshelfName, String userid, String Book, int stile, int contenuto, int gradevolezza, int originalita, int edizione, int votofinale, String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment)
        +getBookshelf() String
        +getUserid() String
        +getBook() String
        +getStile() int
        +getContenuto() int
        +getGradevolezza() int
        +getOriginalita() int
        +getEdizione() int
        +getVotoFinale() int
        +getStyleComment() String
        +getContentComment() String
        +getPleasantnessComment() String
        +getOriginalityComment() String
        +getEditionComment() String
        +setStyleComment(String styleComment)
        +setContentComment(String contentComment)
        +setPleasantnessComment(String pleasantnessComment)
        +setOriginalityComment(String originalityComment)
        +setEditionComment(String editionComment)
    }

    class RecommendedBook {
        -String title
        -int count
        +RecommendedBook(String title, int count)
        +getTitle() String
        +getCount() int
        +setTitle(String title)
        +setCount(int count)
    }

    class UserComment {
        -String userId
        -String comment
        +UserComment(String userId, String comment)
        +getUserId() String
        +getComment() String
    }

    class BookRecommender {
        -IBookRecommenderService service
        +main(String[] args)
        +login(String userid, String password) boolean
        +useridExist(String text) boolean
        +createUser(String name, String surname, String fiscalCode, String address, String mail, String userid, String password)
        +cercaLibro(String criterio, String valore1, String valore2, String bookshelf, String user) ArrayList~Book~
        +getBooks() DefaultComboBoxModel~Book~
        +searchBookshelf(String bookshelfName, String userid) boolean
        +appendBookshelf(String bookshelfName, String userid, ArrayList~Book~ bookshelfBooks)
        +getBookshelves(String userid) HashMap~Bookshelf_key, Bookshelf~
        +getBookshelfBooks(String bookshelfName, String userid) ArrayList~String~
        +appendRating(String userid, String bookshelf, String bookTitle, String bookAuthor, String bookYear, int style, int content, int pleasantness, int originality, int edition, int finalScore, String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment) boolean
        +getRatingValue(String title, String criterion) int
        +getRatingValue(String userid, String title, String criterion) int
        +insertSuggestion(String userid, String sourceBookTitle, String sourceBookAuthor, String sourceBookYear, ArrayList~Book~ suggestedBooks) boolean
        +getSuggestedBooksWithCount(String title) ArrayList~RecommendedBook~
        +getSuggestedBooks(String title) ArrayList~String~
        +getAggregatedRating(String title) AggregatedRating
        +getUserComments(String title) ArrayList~UserComment~
        +addCriterionCommentToBook(String userid, String bookTitle, String criterion, String comment) boolean
        +getBookByTitle(String title) Book
    }

    class IBookRecommenderService {
        <<Interface>>
        +login(String userid, String password) boolean
        +useridExist(String text) boolean
        +createUser(String name, String surname, String fiscalCode, String address, String mail, String userid, String password)
        +cercaLibro(String criterio, String valore1, String valore2, String bookshelf, String user) ArrayList~Book~
        +getBooks() DefaultComboBoxModel~Book~
        +searchBookshelf(String bookshelfName, String userid) boolean
        +appendBookshelf(String bookshelfName, String userid, ArrayList~Book~ bookshelfBooks)
        +getBookshelves(String userid) HashMap~Bookshelf_key, Bookshelf~
        +getBookshelfBooks(String bookshelfName, String userid) ArrayList~String~
        +appendRating(String userid, String bookshelf, String bookTitle, String bookAuthor, String bookYear, int style, int content, int pleasantness, int originality, int edition, int finalScore, String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment) boolean
        +getRatingValue(String title, String criterion) int
        +getRatingValue(String userid, String title, String criterion) int
        +insertSuggestion(String userid, String sourceBookTitle, String sourceBookAuthor, String sourceBookYear, ArrayList~Book~ suggestedBooks) boolean
        +getSuggestedBooksWithCount(String title) ArrayList~RecommendedBook~
        +getSuggestedBooks(String title) ArrayList~String~
        +getAggregatedRating(String title) AggregatedRating
        +getUserComments(String title) ArrayList~UserComment~
        +addCriterionCommentToBook(String userid, String bookTitle, String criterion, String comment) boolean
        +getBookByTitle(String title) Book
    }

    User "1" -- "0..*" Bookshelf : owns
    User "1" -- "0..*" Rating : provides
    User "1" -- "0..*" UserComment : writes
    Book "1" -- "0..*" Bookshelf : contained in
    Book "1" -- "0..*" Rating : rated
    Book "1" -- "0..*" RecommendedBook : recommended
    Book "1" -- "0..*" UserComment : commented
    Bookshelf "1" -- "0..*" Book : contains
    Rating "1" -- "1" Book : rates
    Rating "1" -- "1" User : by
    RecommendedBook "1" -- "1" Book : recommendation for
    UserComment "1" -- "1" Book : on
    UserComment "1" -- "1" User : by
    BookRecommender ..|> IBookRecommenderService : implements

```

## Diagramma di sequenza

```mermaid
sequenceDiagram
    participant U as Utente
    participant G as GUI
    participant BR as BookRecommender(Client)
    participant S as BookRecommenderService(Server)
    participant DB as Database

    U->>G: Inserisce credenziali
    G->>BR: login(userid, password)
    BR->>S: login(userid, password)
    S->>DB: SELECT * FROM UtentiRegistrati WHERE userid = ? AND password = ?
    DB-->>S: Risultato query
    S-->>BR: true/false
    BR-->>G: true/false
    alt Login riuscito
        G->>G: Apri pagina principale
    else Login fallito
        G->>G: Mostra messaggio di errore
    end
```

## Diagramma ER

```mermaid
erDiagram
    UtentiRegistrati ||--o{ Librerie : possiede
    UtentiRegistrati ||--o{ ValutazioniLibri : effettua
    UtentiRegistrati ||--o{ ConsigliLibri : suggerisce
    Libri ||--o{ Librerie : contenuto
    Libri ||--o{ ValutazioniLibri : valutato
    Libri ||--o{ ConsigliLibri : origine
    Libri ||--o{ ConsigliLibri : suggerito
    Librerie ||--o{ ValutazioniLibri : contiene
    
    UtentiRegistrati {
        string userid PK
        string name
        string surname
        string fiscalCode
        string address
        string email
        string password
    }
    
    Libri {
        string title PK
        string author PK
        string year PK
        string genre
    }
    
    Librerie {
        string bookshelf_name PK
        string userid PK
        string book_title PK
        string book_author PK
        string book_year PK
    }
    
    ValutazioniLibri {
        string userid PK
        string book_title PK
        string book_author PK
        string book_year PK
        int style_rating
        int content_rating
        int pleasantness_rating
        int originality_rating
        int edition_rating
        int final_score
        string style_comment
        string content_comment
        string pleasantness_comment
        string originality_comment
        string edition_comment
    }
    
    ConsigliLibri {
        string userid PK
        string source_book_title PK
        string source_book_author PK
        string source_book_year PK
        string suggested_book_title PK
        string suggested_book_author PK
        string suggested_book_year PK
    }
```