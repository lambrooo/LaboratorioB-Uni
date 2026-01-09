package bookrecommender;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests using H2 in-memory database.
 * These tests can run without any external dependencies.
 */
@DisplayName("H2 Database Integration Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class H2DatabaseIntegrationTest {

    private static final String H2_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;NON_KEYWORDS=YEAR";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    private static JdbcDataAccessService dataAccessService;
    private static Connection connection;
    private static String testUserId;
    private static String testPassword;

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        // Create H2 database connection
        connection = DriverManager.getConnection(H2_URL, H2_USER, H2_PASSWORD);

        // Create tables
        createTables();

        // Insert test data
        insertTestData();

        // Initialize DataAccessService with H2
        dataAccessService = new JdbcDataAccessService(H2_URL, H2_USER, H2_PASSWORD);

        // Generate test credentials
        testUserId = TestAccountGenerator.generateRandomUsername();
        testPassword = TestAccountGenerator.generateRandomPassword();

        System.out.println("H2 Database initialized with test data");
        System.out.println("Test User ID: " + testUserId);
    }

    private static void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create Users table
            stmt.execute("CREATE TABLE UtentiRegistrati (" +
                    "name VARCHAR(100), " +
                    "surname VARCHAR(100), " +
                    "fiscalCode VARCHAR(16), " +
                    "address VARCHAR(255), " +
                    "email VARCHAR(100), " +
                    "userid VARCHAR(50) PRIMARY KEY, " +
                    "password VARCHAR(100))");

            // Create Books table
            // Note: Using "publication_year" as an alias to avoid H2 reserved keyword "year"
            stmt.execute("CREATE TABLE Libri (" +
                    "title VARCHAR(255), " +
                    "author VARCHAR(100), " +
                    "year VARCHAR(4), " +  // H2 in PostgreSQL mode allows this
                    "genre VARCHAR(50), " +
                    "PRIMARY KEY (title, author, year))");

            // Create Bookshelves table
            stmt.execute("CREATE TABLE Librerie (" +
                    "bookshelf_name VARCHAR(255), " +
                    "userid VARCHAR(50), " +
                    "book_title VARCHAR(255), " +
                    "book_author VARCHAR(100), " +
                    "book_year VARCHAR(4), " +
                    "PRIMARY KEY (bookshelf_name, userid, book_title, book_author, book_year), " +
                    "FOREIGN KEY (userid) REFERENCES UtentiRegistrati(userid))");

            // Create Ratings table
            stmt.execute("CREATE TABLE ValutazioniLibri (" +
                    "userid VARCHAR(50), " +
                    "bookshelf_name VARCHAR(255), " +
                    "book_title VARCHAR(255), " +
                    "book_author VARCHAR(100), " +
                    "book_year VARCHAR(4), " +
                    "style_rating INT, " +
                    "content_rating INT, " +
                    "pleasantness_rating INT, " +
                    "originality_rating INT, " +
                    "edition_rating INT, " +
                    "final_score INT, " +
                    "style_comment TEXT, " +
                    "content_comment TEXT, " +
                    "pleasantness_comment TEXT, " +
                    "originality_comment TEXT, " +
                    "edition_comment TEXT, " +
                    "PRIMARY KEY (userid, book_title), " +
                    "FOREIGN KEY (userid) REFERENCES UtentiRegistrati(userid))");

            // Create Suggestions table
            stmt.execute("CREATE TABLE ConsigliLibri (" +
                    "userid VARCHAR(50), " +
                    "source_book_title VARCHAR(255), " +
                    "source_book_author VARCHAR(100), " +
                    "source_book_year VARCHAR(4), " +
                    "suggested_book_title VARCHAR(255), " +
                    "suggested_book_author VARCHAR(100), " +
                    "suggested_book_year VARCHAR(4), " +
                    "FOREIGN KEY (userid) REFERENCES UtentiRegistrati(userid))");
        }
    }

    private static void insertTestData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Insert sample users
            stmt.execute("INSERT INTO UtentiRegistrati VALUES " +
                    "('Mario', 'Rossi', 'RSSMRA80A01H501U', 'Via Roma 1, Milano', 'mario@example.com', 'mrossi', 'password123')");

            stmt.execute("INSERT INTO UtentiRegistrati VALUES " +
                    "('Luigi', 'Verdi', 'VRDLGU85B02F205Z', 'Via Milano 2, Roma', 'luigi@example.com', 'lverdi', 'password456')");

            // Insert sample books
            stmt.execute("INSERT INTO Libri VALUES " +
                    "('1984', 'George Orwell', '1949', 'Dystopian')");

            stmt.execute("INSERT INTO Libri VALUES " +
                    "('Animal Farm', 'George Orwell', '1945', 'Satire')");

            stmt.execute("INSERT INTO Libri VALUES " +
                    "('Brave New World', 'Aldous Huxley', '1932', 'Dystopian')");

            stmt.execute("INSERT INTO Libri VALUES " +
                    "('Fahrenheit 451', 'Ray Bradbury', '1953', 'Dystopian')");

            // Insert sample bookshelves
            stmt.execute("INSERT INTO Librerie VALUES " +
                    "('Favorites', 'mrossi', '1984', 'George Orwell', '1949')");

            stmt.execute("INSERT INTO Librerie VALUES " +
                    "('Favorites', 'mrossi', 'Animal Farm', 'George Orwell', '1945')");

            // Insert sample ratings
            stmt.execute("INSERT INTO ValutazioniLibri VALUES " +
                    "('mrossi', 'Favorites', '1984', 'George Orwell', '1949', " +
                    "5, 4, 5, 4, 5, 5, 'Great style', 'Good content', 'Very enjoyable', 'Original', 'Good edition')");

            // Insert sample suggestions
            stmt.execute("INSERT INTO ConsigliLibri VALUES " +
                    "('mrossi', '1984', 'George Orwell', '1949', 'Brave New World', 'Aldous Huxley', '1932')");

            stmt.execute("INSERT INTO ConsigliLibri VALUES " +
                    "('mrossi', '1984', 'George Orwell', '1949', 'Fahrenheit 451', 'Ray Bradbury', '1953')");
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should retrieve all users from database")
    void testGetAllUsers() throws SQLException {
        HashMap<String, User> users = dataAccessService.getAllUsers();

        assertNotNull(users);
        assertTrue(users.size() >= 2);
        assertTrue(users.containsKey("mrossi"));
        assertTrue(users.containsKey("lverdi"));

        User mario = users.get("mrossi");
        assertEquals("Mario", mario.getName());
        assertEquals("Rossi", mario.getSurname());
        assertEquals("password123", mario.getPassword());
    }

    @Test
    @Order(2)
    @DisplayName("Should retrieve all books from database")
    void testGetAllBooks() throws SQLException {
        HashMap<Book_key, Book> books = dataAccessService.getAllBooks();

        assertNotNull(books);
        assertTrue(books.size() >= 4);

        // Check for specific book
        Book_key key1984 = new Book_key("1984", "George Orwell", "1949");
        assertTrue(books.containsKey(key1984));

        Book book1984 = books.get(key1984);
        assertEquals("1984", book1984.getTitle());
        assertEquals("George Orwell", book1984.getAuthor());
        assertEquals("1949", book1984.getYear());
        assertEquals("Dystopian", book1984.getGenre());
    }

    @Test
    @Order(3)
    @DisplayName("Should retrieve all bookshelves from database")
    void testGetAllBookshelves() throws SQLException {
        HashMap<Bookshelf_key, Bookshelf> bookshelves = dataAccessService.getAllBookshelves();

        assertNotNull(bookshelves);
        assertTrue(bookshelves.size() >= 1);

        Bookshelf_key favoritesKey = new Bookshelf_key("Favorites", "mrossi");
        assertTrue(bookshelves.containsKey(favoritesKey));

        Bookshelf favorites = bookshelves.get(favoritesKey);
        assertEquals("Favorites", favorites.getName());
        assertEquals("mrossi", favorites.getUserid());
        assertTrue(favorites.getBooks().size() >= 2);
    }

    @Test
    @Order(4)
    @DisplayName("Should retrieve all ratings from database")
    void testGetAllRatings() throws SQLException {
        HashMap<Rating_key, Rating> ratings = dataAccessService.getAllRatings();

        assertNotNull(ratings);
        assertTrue(ratings.size() >= 1);

        Rating_key ratingKey = new Rating_key("1984", "mrossi");
        assertTrue(ratings.containsKey(ratingKey));

        Rating rating = ratings.get(ratingKey);
        assertEquals("mrossi", rating.getUserid());
        assertEquals("1984", rating.getBook());
        assertEquals(5, rating.getStile());
        assertEquals(4, rating.getContenuto());
    }

    @Test
    @Order(5)
    @DisplayName("Should retrieve all suggestions from database")
    void testGetAllSuggestions() throws SQLException {
        HashMap<String, ArrayList<String>> suggestions = dataAccessService.getAllSuggestions();

        assertNotNull(suggestions);
        assertTrue(suggestions.size() >= 1);

        // Check for suggestions from mrossi for 1984
        boolean foundSuggestion = false;
        for (String key : suggestions.keySet()) {
            if (key.contains("1984")) {
                ArrayList<String> suggested = suggestions.get(key);
                assertTrue(suggested.size() >= 1);
                foundSuggestion = true;
                break;
            }
        }
        assertTrue(foundSuggestion);
    }

    @Test
    @Order(6)
    @DisplayName("Should save new user to database")
    void testSaveUser() throws SQLException {
        User newUser = TestAccountGenerator.generateUserWithCredentials(testUserId, testPassword);

        dataAccessService.saveUser(newUser);

        // Verify user was saved
        HashMap<String, User> users = dataAccessService.getAllUsers();
        assertTrue(users.containsKey(testUserId));

        User savedUser = users.get(testUserId);
        assertEquals(newUser.getName(), savedUser.getName());
        assertEquals(newUser.getEmail(), savedUser.getEmail());
        assertEquals(testPassword, savedUser.getPassword());
    }

    @Test
    @Order(7)
    @DisplayName("Should save new bookshelf to database")
    void testSaveBookshelf() throws SQLException {
        String bookshelfName = "TestShelf_" + System.currentTimeMillis();
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("1984", "George Orwell", "1949", "Dystopian"));

        dataAccessService.saveBookshelf(bookshelfName, testUserId, books);

        // Verify bookshelf was saved
        HashMap<Bookshelf_key, Bookshelf> bookshelves = dataAccessService.getAllBookshelves();
        Bookshelf_key key = new Bookshelf_key(bookshelfName, testUserId);
        assertTrue(bookshelves.containsKey(key));
    }

    @Test
    @Order(8)
    @DisplayName("Should save new rating to database")
    void testSaveRating() throws SQLException {
        String bookshelfName = "TestShelf_" + System.currentTimeMillis();

        // First create a bookshelf for the rating
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Animal Farm", "George Orwell", "1945", "Satire"));
        dataAccessService.saveBookshelf(bookshelfName, testUserId, books);

        // Now save rating
        dataAccessService.saveRating(
                testUserId, bookshelfName, "Animal Farm", "George Orwell", "1945",
                4, 4, 5, 3, 4, 4,
                "style", "content", "pleasant", "original", "edition"
        );

        // Verify rating was saved
        HashMap<Rating_key, Rating> ratings = dataAccessService.getAllRatings();
        Rating_key ratingKey = new Rating_key("Animal Farm", testUserId);
        assertTrue(ratings.containsKey(ratingKey));

        Rating savedRating = ratings.get(ratingKey);
        assertEquals(4, savedRating.getStile());
        assertEquals(5, savedRating.getGradevolezza());
    }

    @Test
    @Order(9)
    @DisplayName("Should save suggestion to database")
    void testSaveSuggestion() throws SQLException {
        ArrayList<Book> suggestedBooks = new ArrayList<>();
        suggestedBooks.add(new Book("Brave New World", "Aldous Huxley", "1932", "Dystopian"));

        dataAccessService.saveSuggestion(
                testUserId, "1984", "George Orwell", "1949", suggestedBooks
        );

        // Verify suggestion was saved
        HashMap<String, ArrayList<String>> suggestions = dataAccessService.getAllSuggestions();
        boolean found = false;
        for (ArrayList<String> suggested : suggestions.values()) {
            if (suggested.contains("Brave New World")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    @Order(10)
    @DisplayName("Should save comment to existing rating")
    void testSaveComment() throws SQLException {
        String newComment = "Updated style comment";

        dataAccessService.saveComment(testUserId, "Animal Farm", "stile", newComment);

        // Verify comment was updated
        HashMap<Rating_key, Rating> ratings = dataAccessService.getAllRatings();
        Rating_key ratingKey = new Rating_key("Animal Farm", testUserId);
        Rating rating = ratings.get(ratingKey);

        assertEquals(newComment, rating.getStyleComment());
    }

    @Test
    @Order(11)
    @DisplayName("Should add book to existing bookshelf")
    void testAddBookToBookshelf() throws SQLException {
        String bookshelfName = "TestShelf2_" + System.currentTimeMillis();

        // Create bookshelf
        ArrayList<Book> initialBooks = new ArrayList<>();
        initialBooks.add(new Book("1984", "George Orwell", "1949", "Dystopian"));
        dataAccessService.saveBookshelf(bookshelfName, testUserId, initialBooks);

        // Add another book
        Book newBook = new Book("Animal Farm", "George Orwell", "1945", "Satire");
        dataAccessService.addBookToBookshelf(bookshelfName, testUserId, newBook);

        // Verify book was added
        HashMap<Bookshelf_key, Bookshelf> bookshelves = dataAccessService.getAllBookshelves();
        Bookshelf_key key = new Bookshelf_key(bookshelfName, testUserId);
        Bookshelf bookshelf = bookshelves.get(key);

        assertEquals(2, bookshelf.getBooks().size());
        assertTrue(bookshelf.getBooks().contains("Animal Farm"));
    }

    @Test
    @Order(12)
    @DisplayName("Should delete bookshelf from database")
    void testDeleteBookshelf() throws SQLException {
        String bookshelfName = "ToDelete_" + System.currentTimeMillis();

        // Create bookshelf
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("1984", "George Orwell", "1949", "Dystopian"));
        dataAccessService.saveBookshelf(bookshelfName, testUserId, books);

        // Delete bookshelf
        dataAccessService.deleteBookshelf(testUserId, bookshelfName);

        // Verify bookshelf was deleted
        HashMap<Bookshelf_key, Bookshelf> bookshelves = dataAccessService.getAllBookshelves();
        Bookshelf_key key = new Bookshelf_key(bookshelfName, testUserId);
        assertFalse(bookshelves.containsKey(key));
    }

    @Test
    @Order(13)
    @DisplayName("Should rename bookshelf in database")
    void testRenameBookshelf() throws SQLException {
        String oldName = "OldName_" + System.currentTimeMillis();
        String newName = "NewName_" + System.currentTimeMillis();

        // Create bookshelf
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("1984", "George Orwell", "1949", "Dystopian"));
        dataAccessService.saveBookshelf(oldName, testUserId, books);

        // Rename bookshelf
        dataAccessService.renameBookshelf(testUserId, oldName, newName);

        // Verify bookshelf was renamed
        HashMap<Bookshelf_key, Bookshelf> bookshelves = dataAccessService.getAllBookshelves();
        Bookshelf_key oldKey = new Bookshelf_key(oldName, testUserId);
        Bookshelf_key newKey = new Bookshelf_key(newName, testUserId);

        assertFalse(bookshelves.containsKey(oldKey));
        assertTrue(bookshelves.containsKey(newKey));

        Bookshelf renamedShelf = bookshelves.get(newKey);
        assertEquals(newName, renamedShelf.getName());
    }

    @AfterAll
    static void tearDown() throws SQLException {
        if (dataAccessService != null) {
            dataAccessService.close();
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        System.out.println("H2 Database tests completed and closed");
    }
}
