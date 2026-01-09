package bookrecommender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;

/**
 * This class implements the IBookRecommenderService interface and provides the remote methods
 * for the Book Recommender application server. It handles interactions with the DataManager
 * to perform operations on users, books, bookshelves, ratings, and suggestions.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class BookRecommenderServer extends UnicastRemoteObject implements IBookRecommenderService {

    private static final long serialVersionUID = 1L;
    private DataManager dataManager;

    /**
     * Constructs a new BookRecommenderServer object.
     * Initializes the DataManager and prompts for database connection details.
     * 
     * @throws RemoteException if a remote communication error occurs
     * @throws SQLException if a database access error occurs
     */
    protected BookRecommenderServer() throws RemoteException, SQLException {
        super();
        String dbUrl = null;
        String dbUser = null;
        String dbPassword = null;
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.err.println("Sorry, unable to find config.properties");
                throw new FileNotFoundException("config.properties not found in classpath");
            }
            prop.load(input);
            dbUrl = prop.getProperty("db.url");
            dbUser = prop.getProperty("db.user");
            dbPassword = prop.getProperty("db.password");
            
            System.out.println("Connecting to database at: " + dbUrl);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RemoteException("Error loading database configuration", ex);
        }
        this.dataManager = DataManager.getInstance(dbUrl, dbUser, dbPassword);
    }

    /**
     * Main method to start the Book Recommender server.
     * Creates an RMI registry and binds the server instance to it.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            Registry registry;
            try {
                registry = LocateRegistry.getRegistry(1099);
                registry.list(); // Test if the registry is alive
            } catch (RemoteException e) {
                System.out.println("RMI registry not found, creating one...");
                registry = LocateRegistry.createRegistry(1099);
            }
            BookRecommenderServer server = new BookRecommenderServer();
            registry.rebind("BookRecommenderService", server);
            System.out.println("BookRecommender Server ready.");

            // Add shutdown hook to close the connection pool
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down BookRecommender Server...");
                if (server.dataManager != null) {
                    server.dataManager.close();
                    System.out.println("Database connection pool closed.");
                }
            }));

        } catch (Exception e) {
            System.err.println("BookRecommender Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a user with the provided userid and password.
     * 
     * @param userid The user's ID
     * @param password The user's password
     * @return true if authentication is successful, false otherwise
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public boolean login(String userid, String password) throws RemoteException {
        return dataManager.checkCredentials(userid, password);
    }

    /**
     * Checks if a given user ID already exists in the system.
     * 
     * @param text The user ID to check
     * @return true if the user ID exists, false otherwise
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public boolean useridExist(String text) throws RemoteException {
        return dataManager.useridExist(text);
    }

    /**
     * Creates a new user in the system.
     * 
     * @param name The user's first name
     * @param surname The user's last name
     * @param fiscalCode The user's fiscal code
     * @param address The user's address
     * @param mail The user's email address
     * @param userid The user's chosen ID
     * @param password The user's chosen password
     * @throws RemoteException if a remote communication error occurs or a database error occurs
     */
    @Override
    public void createUser(String name, String surname, String fiscalCode, String address, String mail, String userid, String password) throws RemoteException {
        try {
            dataManager.createUser(name, surname, fiscalCode, address, mail, userid, password);
        } catch (SQLException e) {
            throw new RemoteException("Error creating user", e);
        }
    }

    /**
     * Searches for books based on specified criteria.
     * 
     * @param criterio The search criterion (e.g., "titolo", "autore", "autoreanno")
     * @param valore1 The primary search value (e.g., book title, author name)
     * @param valore2 The secondary search value (e.g., year, if searching by author and year)
     * @param bookshelf Optional: The name of the bookshelf to search within
     * @param user Optional: The user ID associated with the bookshelf search
     * @return An ArrayList of Book objects matching the criteria
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public ArrayList<Book> cercaLibro(String criterio, String valore1, String valore2, String bookshelf, String user) throws RemoteException {
        return dataManager.searchBook(criterio, valore1, valore2, bookshelf, user);
    }

    /**
     * Retrieves a DefaultComboBoxModel containing all books in the system.
     * 
     * @return A DefaultComboBoxModel of Book objects
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public DefaultComboBoxModel<Book> getBooks() throws RemoteException {
        return dataManager.getBooks();
    }

    /**
     * Searches for a bookshelf by name and user ID.
     * 
     * @param bookshelfName The name of the bookshelf
     * @param userid The ID of the user who owns the bookshelf
     * @return true if the bookshelf exists, false otherwise
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public boolean searchBookshelf(String bookshelfName, String userid) throws RemoteException {
        return dataManager.searchBookshelf(bookshelfName, userid);
    }

    /**
     * Appends a new bookshelf for a user.
     * 
     * @param bookshelfName The name of the new bookshelf
     * @param userid The ID of the user creating the bookshelf
     * @param bookshelfBooks An ArrayList of Book objects to be added to the bookshelf
     * @throws RemoteException if a remote communication error occurs or a database error occurs
     */
    @Override
    public void appendBookshelf(String bookshelfName, String userid, ArrayList<Book> bookshelfBooks) throws RemoteException {
        try {
            dataManager.appendBookshelf(bookshelfName, userid, bookshelfBooks);
        } catch (SQLException e) {
            throw new RemoteException("Error appending bookshelf", e);
        }
    }

    /**
     * Retrieves all bookshelves for a given user.
     * 
     * @param userid The ID of the user
     * @return A HashMap where keys are Bookshelf_key and values are Bookshelf objects
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public HashMap<Bookshelf_key, Bookshelf> getBookshelves(String userid) throws RemoteException {
        return dataManager.getBookshelves(userid);
    }

    /**
     * Retrieves the list of book titles within a specific bookshelf for a given user.
     * 
     * @param bookshelfName The name of the bookshelf
     * @param userid The ID of the user who owns the bookshelf
     * @return An ArrayList of book titles in the specified bookshelf
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public ArrayList<String> getBookshelfBooks(String bookshelfName, String userid) throws RemoteException {
        return dataManager.getBookshelfBooks(bookshelfName, userid);
    }

    /**
     * Appends a new rating for a book by a user.
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
     * @throws RemoteException if a remote communication error occurs or a database error occurs
     */
    @Override
    public boolean appendRating(String userid, String bookshelf, String bookTitle, String bookAuthor, String bookYear, int style, int content, int pleasantness, int originality, int edition, int finalScore, String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment) throws RemoteException {
        try {
            return dataManager.appendRating(userid, bookshelf, bookTitle, bookAuthor, bookYear, style, content, pleasantness, originality, edition, finalScore, styleComment, contentComment, pleasantnessComment, originalityComment, editionComment);
        } catch (SQLException e) {
            throw new RemoteException("Error appending rating", e);
        }
    }

    /**
     * Retrieves the aggregated rating value for a specific criterion of a book.
     * 
     * @param title The title of the book
     * @param criterion The rating criterion (e.g., "Stile", "Contenuto")
     * @return The aggregated rating value
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public int getRatingValue(String title, String criterion) throws RemoteException {
        return dataManager.getRatingValue(title, criterion);
    }

    /**
     * Retrieves the rating value for a specific criterion of a book by a particular user.
     * 
     * @param userid The ID of the user
     * @param title The title of the book
     * @param criterion The rating criterion (e.g., "Stile", "Contenuto")
     * @return The user's rating value for the specified criterion
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public int getRatingValue(String userid, String title, String criterion) throws RemoteException {
        return dataManager.getRatingValue(userid, title, criterion);
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
     * @throws RemoteException if a remote communication error occurs or a database error occurs
     */
    @Override
    public boolean insertSuggestion(String userid, String sourceBookTitle, String sourceBookAuthor, String sourceBookYear, ArrayList<Book> suggestedBooks) throws RemoteException {
        try {
            return dataManager.insertSuggestion(userid, sourceBookTitle, sourceBookAuthor, sourceBookYear, suggestedBooks);
        } catch (SQLException e) {
            throw new RemoteException("Error inserting suggestion", e);
        }
    }

    /**
     * Retrieves a list of recommended books with their suggestion counts for a given book.
     * 
     * @param title The title of the book for which to retrieve suggestions
     * @return An ArrayList of RecommendedBook objects
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public ArrayList<RecommendedBook> getSuggestedBooksWithCount(String title) throws RemoteException {
        return dataManager.getSuggestedBooksWithCount(title);
    }

    /**
     * Retrieves a list of suggested book titles for a given book.
     * 
     * @param title The title of the book for which to retrieve suggested titles
     * @return An ArrayList of suggested book titles
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public ArrayList<String> getSuggestedBooks(String title) throws RemoteException {
        ArrayList<RecommendedBook> recommendedBooks = dataManager.getSuggestedBooksWithCount(title);
        ArrayList<String> suggestedBooks = new ArrayList<>();
        for (RecommendedBook recommendedBook : recommendedBooks) {
            suggestedBooks.add(recommendedBook.getTitle());
        }
        return suggestedBooks;
    }

    /**
     * Retrieves the aggregated rating for a specific book.
     * 
     * @param title The title of the book
     * @return An AggregatedRating object containing the aggregated ratings for the book
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public AggregatedRating getAggregatedRating(String title) throws RemoteException {
        return dataManager.getAggregatedRating(title);
    }

    /**
     * Retrieves all user comments for a specific book.
     * 
     * @param title The title of the book
     * @return An ArrayList of UserComment objects for the book
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public ArrayList<UserComment> getUserComments(String title) throws RemoteException {
        return dataManager.getUserComments(title);
    }

    /**
     * Adds a comment for a specific rating criterion of a book by a user.
     * 
     * @param userid The ID of the user adding the comment
     * @param bookTitle The title of the book
     * @param criterion The rating criterion for which the comment is added
     * @param comment The comment text
     * @return true if the comment was successfully added, false otherwise
     * @throws RemoteException if a remote communication error occurs or a database error occurs
     */
    @Override
    public boolean addCriterionCommentToBook(String userid, String bookTitle, String criterion, String comment) throws RemoteException {
        try {
            return dataManager.addCriterionCommentToBook(userid, bookTitle, criterion, comment);
        } catch (SQLException e) {
            throw new RemoteException("Error adding criterion comment", e);
        }
    }

    /**
     * Retrieves a Book object by its title.
     * 
     * @param title The title of the book to retrieve
     * @return The Book object if found, null otherwise
     * @throws RemoteException if a remote communication error occurs
     */
    @Override
    public Book getBookByTitle(String title) throws RemoteException {
        return dataManager.getBookByTitle(title);
    }

    /**
     * Adds a single book to an existing bookshelf.
     * 
     * @param bookshelfName The name of the bookshelf
     * @param userid The ID of the user who owns the bookshelf
     * @param book The Book object to be added to the bookshelf
     * @return true if the book was successfully added, false otherwise
     * @throws RemoteException if a remote communication error occurs or a database error occurs
     */
    public boolean addBookToBookshelf(String bookshelfName, String userid, Book book) throws RemoteException {
        try {
            return dataManager.addBookToBookshelf(bookshelfName, userid, book);
        } catch (SQLException e) {
            throw new RemoteException("Error adding book to bookshelf", e);
        }
    }

    /**
     * Deletes a bookshelf and all its contents.
     * 
     * @param userid The ID of the user who owns the bookshelf
     * @param bookshelfName The name of the bookshelf to delete
     * @return true if the bookshelf was successfully deleted, false otherwise
     * @throws RemoteException if a remote communication error occurs or a database error occurs
     */
    @Override
    public boolean deleteBookshelf(String userid, String bookshelfName) throws RemoteException {
        try {
            return dataManager.deleteBookshelf(userid, bookshelfName);
        } catch (SQLException e) {
            throw new RemoteException("Error deleting bookshelf", e);
        }
    }

    /**
     * Renames a bookshelf.
     * 
     * @param userid The ID of the user who owns the bookshelf
     * @param oldName The current name of the bookshelf
     * @param newName The new name for the bookshelf
     * @return true if the bookshelf was successfully renamed, false otherwise
     * @throws RemoteException if a remote communication error occurs or a database error occurs
     */
    @Override
    public boolean renameBookshelf(String userid, String oldName, String newName) throws RemoteException {
        try {
            return dataManager.renameBookshelf(userid, oldName, newName);
        } catch (SQLException e) {
            throw new RemoteException("Error renaming bookshelf", e);
        }
    }
}
