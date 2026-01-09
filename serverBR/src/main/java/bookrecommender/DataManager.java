package bookrecommender;

import java.util.concurrent.ConcurrentHashMap;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;

/**
 * This class manages the application's data, providing methods to access and manipulate
 * users, books, bookshelves, ratings, and suggestions. It acts as a central data manager
 * that coordinates between the UI layer and the data access layer.
 * 
 * @version 1.0.0 (04-09-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class DataManager {

    private static DataManager instance;
    private DataAccessService dataAccessService;

    private ConcurrentHashMap<String, User> users;
    private HashMap<Book_key, Book> books;
    private ConcurrentHashMap<Bookshelf_key, Bookshelf> bookshelf_books;
    private ConcurrentHashMap<Rating_key, Rating> rating_books;
    private ConcurrentHashMap<String, ArrayList<String>> suggestions;

    /**
     * Constructs a new DataManager with the specified database connection parameters.
     * Initializes all data structures and loads data from the database.
     * 
     * @param dbUrl The JDBC URL for the database connection
     * @param dbUser The username for the database connection
     * @param dbPassword The password for the database connection
     * @throws SQLException if a database access error occurs
     */
    private DataManager(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        this.dataAccessService = new JdbcDataAccessService(dbUrl, dbUser, dbPassword);
        this.users = new ConcurrentHashMap<>(dataAccessService.getAllUsers());
        this.books = dataAccessService.getAllBooks();
        this.bookshelf_books = new ConcurrentHashMap<>(dataAccessService.getAllBookshelves());
        this.rating_books = new ConcurrentHashMap<>(dataAccessService.getAllRatings());
        this.suggestions = new ConcurrentHashMap<>(dataAccessService.getAllSuggestions());
    }

    /**
     * Returns the singleton instance of DataManager, creating it if it doesn't exist.
     * 
     * @param dbUrl The JDBC URL for the database connection
     * @param dbUser The username for the database connection
     * @param dbPassword The password for the database connection
     * @return The singleton instance of DataManager
     * @throws SQLException if a database access error occurs
     */
    public static DataManager getInstance(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        if (instance == null) {
            instance = new DataManager(dbUrl, dbUser, dbPassword);
        }
        return instance;
    }

    /**
     * Checks if the provided credentials are valid for the given user ID.
     * 
     * @param userid The user ID to check
     * @param password The password to verify
     * @return true if the credentials are valid, false otherwise
     */
    public boolean checkCredentials(String userid, String password) {
        User user = users.get(userid);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Checks if a user with the given user ID already exists.
     * 
     * @param userid The user ID to check
     * @return true if the user exists, false otherwise
     */
    public boolean useridExist(String userid) {
        return users.containsKey(userid);
    }

    /**
     * Creates a new user with the provided information.
     * 
     * @param name The user's first name
     * @param surname The user's last name
     * @param fiscalCode The user's fiscal code
     * @param address The user's address
     * @param mail The user's email address
     * @param userid The user's chosen ID
     * @param password The user's chosen password
     * @throws SQLException if a database access error occurs
     */
    public void createUser(String name, String surname, String fiscalCode, String address, String mail, String userid, String password) throws SQLException {
        if (useridExist(userid)) {
            throw new IllegalArgumentException("User with this userid already exists.");
        }
        User newUser = new User(name, surname, fiscalCode, address, mail, userid, password);
        dataAccessService.saveUser(newUser);
        users.put(userid, newUser);
    }

    /**
     * Searches for books based on the specified criteria.
     * 
     * @param criteria The search criteria (e.g., "titolo", "autore", "autoreanno")
     * @param value1 The primary search value (e.g., book title, author name)
     * @param value2 The secondary search value (e.g., year, if searching by author and year)
     * @param bookshelf Optional: The name of the bookshelf to search within
     * @param user Optional: The user ID associated with the bookshelf search
     * @return An ArrayList of Book objects matching the criteria
     */
    public ArrayList<Book> searchBook(String criteria, String value1, String value2, String bookshelf, String user) {
        ArrayList<Book> results = new ArrayList<>();
        String lowerValue1 = value1.toLowerCase().trim();
        String lowerValue2 = (value2 != null) ? value2.toLowerCase().trim() : null;

        for (Book book : books.values()) {
            boolean match = false;
            switch (criteria.toLowerCase()) {
                case "titolo":
                    match = book.getTitle().toLowerCase().contains(lowerValue1);
                    break;
                case "autore":
                    match = book.getAuthor().toLowerCase().contains(lowerValue1);
                    break;
                case "autoreanno":
                    match = book.getAuthor().toLowerCase().contains(lowerValue1) && (lowerValue2 == null || book.getYear().equalsIgnoreCase(lowerValue2));
                    break;
            }
            if (match) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Retrieves the aggregated rating for a specific book.
     * 
     * @param title The title of the book
     * @return An AggregatedRating object containing the aggregated ratings for the book
     */
    public AggregatedRating getAggregatedRating(String title) {
        AggregatedRating aggregatedRating = new AggregatedRating();
        for (Rating rating : rating_books.values()) {
            if (rating.getBook().equals(title)) {
                aggregatedRating.addRating("Stile", rating.getStile());
                aggregatedRating.addRating("Contenuto", rating.getContenuto());
                aggregatedRating.addRating("Gradevolezza", rating.getGradevolezza());
                aggregatedRating.addRating("Originalita", rating.getOriginalita());
                aggregatedRating.addRating("Edizione", rating.getEdizione());
                aggregatedRating.addRating("VotoFinale", rating.getVotoFinale());
            }
        }
        return aggregatedRating;
    }

    /**
     * Retrieves a DefaultComboBoxModel containing all books.
     * 
     * @return A DefaultComboBoxModel of Book objects
     */
    public DefaultComboBoxModel<Book> getBooks() {
        DefaultComboBoxModel<Book> bookModel = new DefaultComboBoxModel<>();
        for (Book book : books.values()) {
            bookModel.addElement(book);
        }
        return bookModel;
    }

    /**
     * Retrieves books filtered by the specified search criteria.
     * 
     * @param searchingType The type of search (e.g., "title", "author")
     * @param text The search text
     * @return A HashMap of Book objects matching the search criteria
     */
    public HashMap<Book_key, Book> getBooks(String searchingType, String text) {
        HashMap<Book_key, Book> filteredBooks = new HashMap<>();
        String lowerText = text.toLowerCase().trim();

        for (Map.Entry<Book_key, Book> entry : books.entrySet()) {
            Book book = entry.getValue();
            boolean match = false;
            switch (searchingType.toLowerCase()) {
                case "title":
                case "titolo":
                    match = book.getTitle().toLowerCase().contains(lowerText);
                    break;
                case "author":
                case "autore":
                    match = book.getAuthor().toLowerCase().contains(lowerText);
                    break;
            }
            if (match) {
                filteredBooks.put(entry.getKey(), book);
            }
        }
        return filteredBooks;
    }

    /**
     * Checks if a bookshelf with the given name exists for the specified user.
     * 
     * @param bookshelfName The name of the bookshelf
     * @param userid The ID of the user who owns the bookshelf
     * @return true if the bookshelf exists, false otherwise
     */
    public boolean searchBookshelf(String bookshelfName, String userid) {
        Bookshelf_key key = new Bookshelf_key(bookshelfName, userid);
        return bookshelf_books.containsKey(key);
    }

    /**
     * Creates a new bookshelf for the specified user.
     * 
     * @param bookshelfName The name of the new bookshelf
     * @param userid The ID of the user creating the bookshelf
     * @param bookshelfBooks An ArrayList of Book objects to be added to the bookshelf
     * @throws SQLException if a database access error occurs
     */
    public void appendBookshelf(String bookshelfName, String userid, ArrayList<Book> bookshelfBooks) throws SQLException {
        Bookshelf newBookshelf = new Bookshelf(bookshelfName, userid, new ArrayList<>());
        for (Book book : bookshelfBooks) {
            newBookshelf.getBooks().add(book.getTitle()); // Bookshelf still stores titles
        }
        dataAccessService.saveBookshelf(bookshelfName, userid, bookshelfBooks);
        bookshelf_books.put(new Bookshelf_key(bookshelfName, userid), newBookshelf);
    }

    /**
     * Retrieves all bookshelves for a given user.
     * 
     * @param userid The ID of the user
     * @return A HashMap where keys are Bookshelf_key and values are Bookshelf objects
     */
    public HashMap<Bookshelf_key, Bookshelf> getBookshelves(String userid) {
        HashMap<Bookshelf_key, Bookshelf> userBookshelves = new HashMap<>();
        for (Map.Entry<Bookshelf_key, Bookshelf> entry : bookshelf_books.entrySet()) {
            if (entry.getKey().getuserid().equals(userid)) {
                userBookshelves.put(entry.getKey(), entry.getValue());
            }
        }
        return userBookshelves;
    }

    /**
     * Retrieves the list of book titles within a specific bookshelf for a given user.
     * 
     * @param bookshelfName The name of the bookshelf
     * @param userid The ID of the user who owns the bookshelf
     * @return An ArrayList of book titles in the specified bookshelf
     */
    public ArrayList<String> getBookshelfBooks(String bookshelfName, String userid) {
        Bookshelf_key key = new Bookshelf_key(bookshelfName, userid);
        Bookshelf bookshelf = bookshelf_books.get(key);
        return bookshelf != null ? new ArrayList<>(bookshelf.getBooks()) : new ArrayList<>();
    }

    /**
     * Adds a new rating for a book by a user.
     * 
     * @param userid The ID of the user providing the rating
     * @param bookshelf The name of the bookshelf where the book is located
     * @param bookTitle The title of the book being rated
     * @param bookAuthor The author of the book being rated
     * @param bookYear The publication year of the book being rated
     * @param style The style rating (1-5)
     * @param content The content rating (1-5)
     * @param pleasantness The pleasantness rating (1-5)
     * @param originality The originality rating (1-5)
     * @param edition The edition rating (1-5)
     * @param finalScore The final calculated score (1-5)
     * @param styleComment Optional comment for style
     * @param contentComment Optional comment for content
     * @param pleasantnessComment Optional comment for pleasantness
     * @param originalityComment Optional comment for originality
     * @param editionComment Optional comment for edition
     * @return true if the rating was successfully appended, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean appendRating(String userid, String bookshelf, String bookTitle, String bookAuthor, String bookYear, int style, int content, int pleasantness, int originality, int edition, int finalScore, String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment) throws SQLException {
        // Check if the user has already rated this book
        Rating_key key = new Rating_key(bookTitle, userid);
        if (rating_books.containsKey(key)) {
            return false; // User has already rated this book
        }
        
        finalScore = (style + content + pleasantness + originality + edition) / 5;
        Rating newRating = new Rating(bookshelf, userid, bookTitle, style, content, pleasantness, originality, edition, finalScore, styleComment, contentComment, pleasantnessComment, originalityComment, editionComment);
        dataAccessService.saveRating(userid, bookshelf, bookTitle, bookAuthor, bookYear, style, content, pleasantness, originality, edition, finalScore, styleComment, contentComment, pleasantnessComment, originalityComment, editionComment);
        rating_books.put(key, newRating);
        return true;
    }

    /**
     * Inserts a new book suggestion from a user.
     * 
     * @param userid The ID of the user making the suggestion
     * @param sourceBookTitle The title of the book for which the suggestion is made
     * @param sourceBookAuthor The author of the book for which the suggestion is made
     * @param sourceBookYear The publication year of the book for which the suggestion is made
     * @param suggestedBooks An ArrayList of Book objects being suggested
     * @return true if the suggestion was successfully inserted, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean insertSuggestion(String userid, String sourceBookTitle, String sourceBookAuthor, String sourceBookYear, ArrayList<Book> suggestedBooks) throws SQLException {
        dataAccessService.saveSuggestion(userid, sourceBookTitle, sourceBookAuthor, sourceBookYear, suggestedBooks);

        // Update the in-memory suggestions map using the same key format as getAllSuggestions
        String key = userid + "|" + sourceBookTitle;
        ArrayList<String> currentSuggestions = suggestions.getOrDefault(key, new ArrayList<>());
        for (Book book : suggestedBooks) {
            if (!currentSuggestions.contains(book.getTitle())) {
                currentSuggestions.add(book.getTitle());
            }
        }
        suggestions.put(key, currentSuggestions);

        return true;
    }

    /**
     * Retrieves the aggregated rating value for a specific criterion of a book.
     * 
     * @param title The title of the book
     * @param criterion The rating criterion (e.g., "Stile", "Contenuto")
     * @return The aggregated rating value
     */
    public int getRatingValue(String title, String criterion) {
        int value = 0;
        int count = 0;
        for (Rating rating : rating_books.values()) {
            if (rating.getBook().equals(title)) {
                count++;
                value += getRatingField(rating, criterion);
            }
        }
        return count > 0 ? value / count : 0;
    }

    /**
     * Retrieves the rating value for a specific criterion of a book by a particular user.
     * 
     * @param userid The ID of the user
     * @param title The title of the book
     * @param criterion The rating criterion (e.g., "Stile", "Contenuto")
     * @return The user's rating value for the specified criterion
     */
    public int getRatingValue(String userid, String title, String criterion) {
        Rating_key key = new Rating_key(title, userid);
        Rating rating = rating_books.get(key);
        return rating != null ? getRatingField(rating, criterion) : 0;
    }

    /**
     * Retrieves a specific rating field value from a Rating object based on the criterion.
     * 
     * @param rating The Rating object
     * @param criterion The rating criterion (e.g., "Stile", "Contenuto")
     * @return The rating value for the specified criterion
     */
    private int getRatingField(Rating rating, String criterion) {
        switch (criterion) {
            case "Stile": return rating.getStile();
            case "Contenuto": return rating.getContenuto();
            case "Gradevolezza": return rating.getGradevolezza();
            case "Originalita": return rating.getOriginalita();
            case "Edizione": return rating.getEdizione();
            case "VotoFinale": return rating.getVotoFinale();
            default: throw new IllegalArgumentException("Invalid criterion: " + criterion);
        }
    }

    /**
     * Retrieves a list of recommended books with their suggestion counts for a given book.
     * 
     * @param title The title of the book for which to retrieve suggestions
     * @return An ArrayList of RecommendedBook objects
     */
    public ArrayList<RecommendedBook> getSuggestedBooksWithCount(String title) {
        HashMap<String, Integer> suggestionCount = new HashMap<>();
        
        // Key format is "userid|source_book_title", so we need to check if the title matches the source book
        for (Map.Entry<String, ArrayList<String>> entry : suggestions.entrySet()) {
            String key = entry.getKey();
            // Extract source book title from key (format: "userid|source_book_title")
            int separatorIndex = key.indexOf("|");
            if (separatorIndex != -1) {
                String sourceBookTitle = key.substring(separatorIndex + 1);
                if (sourceBookTitle.equals(title)) {
                    // This entry is for the book we're looking for
                    for (String suggestedBookTitle : entry.getValue()) {
                        suggestionCount.put(suggestedBookTitle, suggestionCount.getOrDefault(suggestedBookTitle, 0) + 1);
                    }
                }
            }
        }

        ArrayList<RecommendedBook> recommendedBooks = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : suggestionCount.entrySet()) {
            recommendedBooks.add(new RecommendedBook(entry.getKey(), entry.getValue()));
        }
        return recommendedBooks;
    }

    /**
     * Retrieves all user comments for a specific book.
     * 
     * @param title The title of the book
     * @return An ArrayList of UserComment objects for the book
     */
    public ArrayList<UserComment> getUserComments(String title) {
        ArrayList<UserComment> comments = new ArrayList<>();
        for (Rating rating : rating_books.values()) {
            if (rating.getBook().equals(title)) {
                StringBuilder fullComment = new StringBuilder();
                if (rating.getStyleComment() != null && !rating.getStyleComment().isEmpty()) {
                    fullComment.append("Stile: ").append(rating.getStyleComment()).append("; ");
                }
                if (rating.getContentComment() != null && !rating.getContentComment().isEmpty()) {
                    fullComment.append("Contenuto: ").append(rating.getContentComment()).append("; ");
                }
                if (rating.getPleasantnessComment() != null && !rating.getPleasantnessComment().isEmpty()) {
                    fullComment.append("Gradevolezza: ").append(rating.getPleasantnessComment()).append("; ");
                }
                if (rating.getOriginalityComment() != null && !rating.getOriginalityComment().isEmpty()) {
                    fullComment.append("Originalità: ").append(rating.getOriginalityComment()).append("; ");
                }
                if (rating.getEditionComment() != null && !rating.getEditionComment().isEmpty()) {
                    fullComment.append("Edizione: ").append(rating.getEditionComment()).append("; ");
                }

                if (fullComment.length() > 0) {
                    comments.add(new UserComment(rating.getUserid(), fullComment.toString().trim()));
                }
            }
        }
        return comments;
    }

    /**
     * Adds a comment for a specific rating criterion of a book by a user.
     * 
     * @param userid The ID of the user adding the comment
     * @param bookTitle The title of the book
     * @param criterion The rating criterion for which the comment is added
     * @param comment The comment text
     * @return true if the comment was successfully added, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean addCriterionCommentToBook(String userid, String bookTitle, String criterion, String comment) throws SQLException {
        Rating_key key = new Rating_key(bookTitle, userid);
        Rating rating = rating_books.get(key);
        if (rating == null) {
            return false; // No rating exists for this book and user
        }

        // Set the specific comment based on the criterion
        switch (criterion.toLowerCase()) {
            case "stile":
                rating.setStyleComment(comment);
                break;
            case "contenuto":
                rating.setContentComment(comment);
                break;
            case "gradevolezza":
                rating.setPleasantnessComment(comment);
                break;
            case "originalita":
                rating.setOriginalityComment(comment);
                break;
            case "edizione":
                rating.setEditionComment(comment);
                break;
            default:
                throw new IllegalArgumentException("Invalid comment criterion: " + criterion);
        }

        dataAccessService.saveComment(userid, bookTitle, criterion, comment); // Save the comment to the database
        return true;
    }

    /**
     * Retrieves a Book object by its title.
     * 
     * @param title The title of the book to retrieve
     * @return The Book object if found, null otherwise
     */
    public Book getBookByTitle(String title) {
        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Adds a single book to an existing bookshelf.
     * 
     * @param bookshelfName The name of the bookshelf
     * @param userid The ID of the user who owns the bookshelf
     * @param book The Book object to be added to the bookshelf
     * @return true if the book was successfully added, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean addBookToBookshelf(String bookshelfName, String userid, Book book) throws SQLException {
        Bookshelf_key key = new Bookshelf_key(bookshelfName, userid);
        Bookshelf bookshelf = bookshelf_books.get(key);
        
        if (bookshelf == null) {
            return false; // Bookshelf doesn't exist
        }
        
        // Check if book already exists in the bookshelf
        if (bookshelf.getBooks().contains(book.getTitle())) {
            return false; // Book already exists in the bookshelf
        }
        
        // Add book to in-memory bookshelf
        bookshelf.getBooks().add(book.getTitle());
        
        try {
            // Add book to database
            dataAccessService.addBookToBookshelf(bookshelfName, userid, book);
            return true;
        } catch (SQLException e) {
            // Rollback the in-memory operation if database save fails
            bookshelf.getBooks().remove(book.getTitle());
            throw e;
        }
    }

    /**
     * Deletes a bookshelf and all its contents.
     * @param userid The ID of the user who owns the bookshelf.
     * @param bookshelfName The name of the bookshelf to delete.
     * @return true if the bookshelf was successfully deleted, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean deleteBookshelf(String userid, String bookshelfName) throws SQLException {
        Bookshelf_key key = new Bookshelf_key(bookshelfName, userid);
        if (!bookshelf_books.containsKey(key)) {
            return false;
        }

        dataAccessService.deleteBookshelf(userid, bookshelfName);
        bookshelf_books.remove(key);
        return true;
    }

    /**
     * Renames a bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @param oldName The current name of the bookshelf.
     * @param newName The new name for the bookshelf.
     * @return true if the bookshelf was successfully renamed, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean renameBookshelf(String userid, String oldName, String newName) throws SQLException {
        Bookshelf_key oldKey = new Bookshelf_key(oldName, userid);
        Bookshelf_key newKey = new Bookshelf_key(newName, userid);

        if (!bookshelf_books.containsKey(oldKey)) {
            return false; // Bookshelf to rename doesn't exist
        }
        if (bookshelf_books.containsKey(newKey)) {
            return false; // New name already taken
        }

        dataAccessService.renameBookshelf(userid, oldName, newName);

        // Update in-memory map
        Bookshelf bookshelf = bookshelf_books.remove(oldKey);
        bookshelf.setName(newName);
        bookshelf_books.put(newKey, bookshelf);
        
        return true;
    }

    /**
     * Closes the underlying data access service, releasing any resources.
     */
    public void close() {
        if (dataAccessService instanceof JdbcDataAccessService) {
            ((JdbcDataAccessService) dataAccessService).close();
        }
    }
}