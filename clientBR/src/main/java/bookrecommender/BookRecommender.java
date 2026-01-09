package bookrecommender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * This class acts as the client-side entry point for the Book Recommender application.
 * It handles the connection to the RMI server and provides static methods to interact
 * with the remote Book Recommender service.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class BookRecommender {

    private static IBookRecommenderService service;

    /**
     * Main method to start the client application.
     * Establishes a connection to the RMI server and initializes the GUI.
     * 
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            String host = null;
            try (InputStream input = BookRecommender.class.getClassLoader().getResourceAsStream("client.properties")) {
                Properties prop = new Properties();
                if (input == null) {
                    System.err.println("Sorry, unable to find client.properties");
                    throw new FileNotFoundException("client.properties not found in classpath");
                }
                prop.load(input);
                host = prop.getProperty("server.host");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading client configuration: " + ex.getMessage(), "Configuration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Registry registry = LocateRegistry.getRegistry(host);
            service = (IBookRecommenderService) registry.lookup("BookRecommenderService");
            
            // Setup FlatLaf
            FlatLightLaf.setup();
            
            new GUI_Home();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to server: " + e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Authenticates a user with the provided userid and password against the remote service.
     * @param userid The user's ID.
     * @param password The user's password.
     * @return true if authentication is successful, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean login(String userid, String password) throws RemoteException {
        return service.login(userid, password);
    }

    /**
     * Checks if a given user ID already exists in the remote system.
     * @param text The user ID to check.
     * @return true if the user ID exists, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean useridExist(String text) throws RemoteException {
        return service.useridExist(text);
    }

    /**
     * Creates a new user in the remote system.
     * @param name The user's first name.
     * @param surname The user's last name.
     * @param fiscalCode The user's fiscal code.
     * @param address The user's address.
     * @param mail The user's email address.
     * @param userid The user's chosen ID.
     * @param password The user's chosen password.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static void createUser(String name, String surname, String fiscalCode, String address, String mail, String userid, String password) throws RemoteException {
        service.createUser(name, surname, fiscalCode, address, mail, userid, password);
    }

    /**
     * Searches for books based on specified criteria using the remote service.
     * @param criterio The search criterion (e.g., "titolo", "autore", "autoreanno").
     * @param valore1 The primary search value (e.g., book title, author name).
     * @param valore2 The secondary search value (e.g., year, if searching by author and year).
     * @param bookshelf Optional: The name of the bookshelf to search within.
     * @param user Optional: The user ID associated with the bookshelf search.
     * @return An ArrayList of Book objects matching the criteria.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static ArrayList<Book> cercaLibro(String criterio, String valore1, String valore2, String bookshelf, String user) throws RemoteException {
        return service.cercaLibro(criterio, valore1, valore2, bookshelf, user);
    }

    /**
     * Retrieves a DefaultComboBoxModel containing all books from the remote service.
     * @return A DefaultComboBoxModel of Book objects.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static DefaultComboBoxModel<Book> getBooks() throws RemoteException {
        return service.getBooks();
    }

    /**
     * Searches for a bookshelf by name and user ID using the remote service.
     * @param bookshelfName The name of the bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @return true if the bookshelf exists, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean searchBookshelf(String bookshelfName, String userid) throws RemoteException {
        return service.searchBookshelf(bookshelfName, userid);
    }

    /**
     * Appends a new bookshelf for a user using the remote service.
     * @param bookshelfName The name of the new bookshelf.
     * @param userid The ID of the user creating the bookshelf.
     * @param bookshelfBooks An ArrayList of Book objects to be added to the bookshelf.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static void appendBookshelf(String bookshelfName, String userid, ArrayList<Book> bookshelfBooks) throws RemoteException {
        service.appendBookshelf(bookshelfName, userid, bookshelfBooks);
    }

    /**
     * Retrieves all bookshelves for a given user from the remote service.
     * @param userid The ID of the user.
     * @return A HashMap where keys are Bookshelf_key and values are Bookshelf objects.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static HashMap<Bookshelf_key, Bookshelf> getBookshelves(String userid) throws RemoteException {
        return service.getBookshelves(userid);
    }

    /**
     * Retrieves the list of book titles within a specific bookshelf for a given user from the remote service.
     * @param bookshelfName The name of the bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @return An ArrayList of book titles in the specified bookshelf.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static ArrayList<String> getBookshelfBooks(String bookshelfName, String userid) throws RemoteException {
        return service.getBookshelfBooks(bookshelfName, userid);
    }

    /**
     * Appends a new rating for a book by a user using the remote service.
     * @param userid The ID of the user providing the rating.
     * @param bookshelf The name of the bookshelf where the book is located.
     * @param bookTitle The title of the book being rated.
     * @param bookAuthor The author of the book being rated.
     * @param bookYear The publication year of the book being rated.
     * @param style The style rating (1-5).
     * @param content The content rating (1-5).
     * @param pleasantness The pleasantness rating (1-5).
     * @param originality The originality rating (1-5).
     * @param edition The edition rating (1-5).
     * @param finalScore The final calculated score (1-5).
     * @param styleComment Optional comment for style.
     * @param contentComment Optional comment for content.
     * @param pleasantnessComment Optional comment for pleasantness.
     * @param originalityComment Optional comment for originality.
     * @param editionComment Optional comment for edition.
     * @return true if the rating was successfully appended, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean appendRating(String userid, String bookshelf, String bookTitle, String bookAuthor, String bookYear, int style, int content, int pleasantness, int originality, int edition, int finalScore, String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment) throws RemoteException {
        return service.appendRating(userid, bookshelf, bookTitle, bookAuthor, bookYear, style, content, pleasantness, originality, edition, finalScore, styleComment, contentComment, pleasantnessComment, originalityComment, editionComment);
    }

    /**
     * Retrieves the aggregated rating value for a specific criterion of a book from the remote service.
     * @param title The title of the book.
     * @param criterion The rating criterion (e.g., "Stile", "Contenuto").
     * @return The aggregated rating value.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static int getRatingValue(String title, String criterion) throws RemoteException {
        return service.getRatingValue(title, criterion);
    }

    /**
     * Retrieves the rating value for a specific criterion of a book by a particular user from the remote service.
     * @param userid The ID of the user.
     * @param title The title of the book.
     * @param criterion The rating criterion (e.g., "Stile", "Contenuto").
     * @return The user's rating value for the specified criterion.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static int getRatingValue(String userid, String title, String criterion) throws RemoteException {
        return service.getRatingValue(userid, title, criterion);
    }

    /**
     * Inserts a new book suggestion from a user using the remote service.
     * @param userid The ID of the user making the suggestion.
     * @param sourceBookTitle The title of the book for which the suggestion is made.
     * @param sourceBookAuthor The author of the book for which the suggestion is made.
     * @param sourceBookYear The publication year of the book for which the suggestion is made.
     * @param suggestedBooks An ArrayList of Book objects being suggested.
     * @return true if the suggestion was successfully inserted, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean insertSuggestion(String userid, String sourceBookTitle, String sourceBookAuthor, String sourceBookYear, ArrayList<Book> suggestedBooks) throws RemoteException {
        return service.insertSuggestion(userid, sourceBookTitle, sourceBookAuthor, sourceBookYear, suggestedBooks);
    }

    /**
     * Retrieves a list of recommended books with their suggestion counts for a given book from the remote service.
     * @param title The title of the book for which to retrieve suggestions.
     * @return An ArrayList of RecommendedBook objects.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static ArrayList<RecommendedBook> getSuggestedBooksWithCount(String title) throws RemoteException {
        return service.getSuggestedBooksWithCount(title);
    }

    /**
     * Retrieves a list of suggested book titles for a given book from the remote service.
     * @param title The title of the book for which to retrieve suggested titles.
     * @return An ArrayList of suggested book titles.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static ArrayList<String> getSuggestedBooks(String title) throws RemoteException {
        return service.getSuggestedBooks(title);
    }

    /**
     * Retrieves the aggregated rating for a specific book from the remote service.
     * @param title The title of the book.
     * @return An AggregatedRating object containing the aggregated ratings for the book.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static AggregatedRating getAggregatedRating(String title) throws RemoteException {
        return service.getAggregatedRating(title);
    }

    /**
     * Retrieves all user comments for a specific book from the remote service.
     * @param title The title of the book.
     * @return An ArrayList of UserComment objects for the book.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static ArrayList<UserComment> getUserComments(String title) throws RemoteException {
        return service.getUserComments(title);
    }

    /**
     * Adds a comment for a specific rating criterion of a book by a user using the remote service.
     * @param userid The ID of the user adding the comment.
     * @param bookTitle The title of the book.
     * @param criterion The rating criterion for which the comment is added.
     * @param comment The comment text.
     * @return true if the comment was successfully added, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean addCriterionCommentToBook(String userid, String bookTitle, String criterion, String comment) throws RemoteException {
        return service.addCriterionCommentToBook(userid, bookTitle, criterion, comment);
    }

    /**
     * Retrieves a Book object by its title from the remote service.
     * @param title The title of the book to retrieve.
     * @return The Book object if found, null otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static Book getBookByTitle(String title) throws RemoteException {
        return service.getBookByTitle(title);
    }

    /**
     * Adds a single book to an existing bookshelf using the remote service.
     * @param bookshelfName The name of the bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @param book The Book object to be added to the bookshelf.
     * @return true if the book was successfully added, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean addBookToBookshelf(String bookshelfName, String userid, Book book) throws RemoteException {
        return service.addBookToBookshelf(bookshelfName, userid, book);
    }

    /**
     * Deletes a bookshelf and all its contents using the remote service.
     * @param userid The ID of the user who owns the bookshelf.
     * @param bookshelfName The name of the bookshelf to delete.
     * @return true if the bookshelf was successfully deleted, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean deleteBookshelf(String userid, String bookshelfName) throws RemoteException {
        return service.deleteBookshelf(userid, bookshelfName);
    }

    /**
     * Renames a bookshelf using the remote service.
     * @param userid The ID of the user who owns the bookshelf.
     * @param oldName The current name of the bookshelf.
     * @param newName The new name for the bookshelf.
     * @return true if the bookshelf was successfully renamed, false otherwise.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean renameBookshelf(String userid, String oldName, String newName) throws RemoteException {
        return service.renameBookshelf(userid, oldName, newName);
    }
}