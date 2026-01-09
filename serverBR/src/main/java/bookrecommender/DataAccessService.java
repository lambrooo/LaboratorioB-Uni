package bookrecommender;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This interface defines the contract for data access operations in the Book Recommender application.
 * It provides methods for retrieving and saving various data entities to a persistent storage.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public interface DataAccessService {
    /**
     * Retrieves all registered users from the data source.
     * @return A HashMap of User objects, keyed by user ID.
     * @throws SQLException if a database access error occurs.
     */
    HashMap<String, User> getAllUsers() throws SQLException;
    /**
     * Retrieves all books from the data source.
     * @return A HashMap of Book objects, keyed by Book_key.
     * @throws SQLException if a database access error occurs.
     */
    HashMap<Book_key, Book> getAllBooks() throws SQLException;
    /**
     * Retrieves all bookshelves from the data source.
     * @return A HashMap of Bookshelf objects, keyed by Bookshelf_key.
     * @throws SQLException if a database access error occurs.
     */
    HashMap<Bookshelf_key, Bookshelf> getAllBookshelves() throws SQLException;
    /**
     * Retrieves all book ratings from the data source.
     * @return A HashMap of Rating objects, keyed by Rating_key.
     * @throws SQLException if a database access error occurs.
     */
    HashMap<Rating_key, Rating> getAllRatings() throws SQLException;
    /**
     * Retrieves all book suggestions from the data source.
     * @return A HashMap of ArrayLists of suggested book titles, keyed by source book title.
     * @throws SQLException if a database access error occurs.
     */
    HashMap<String, ArrayList<String>> getAllSuggestions() throws SQLException;

    /**
     * Saves a new user to the data source.
     * @param user The User object to save.
     * @throws SQLException if a database access error occurs.
     */
    void saveUser(User user) throws SQLException;
    /**
     * Saves a new bookshelf to the data source.
     * @param bookshelfName The name of the bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @param books An ArrayList of Book objects to be associated with the bookshelf.
     * @throws SQLException if a database access error occurs.
     */
    void saveBookshelf(String bookshelfName, String userid, ArrayList<Book> books) throws SQLException;
    /**
     * Saves a new book rating to the data source.
     * @param userid The ID of the user who provided the rating.
     * @param bookshelf The name of the bookshelf where the book is located.
     * @param bookTitle The title of the book being rated.
     * @param bookAuthor The author of the book being rated.
     * @param bookYear The publication year of the book being rated.
     * @param style The style rating.
     * @param content The content rating.
     * @param pleasantness The pleasantness rating.
     * @param originality The originality rating.
     * @param edition The edition rating.
     * @param finalScore The final calculated score.
     * @param styleComment Optional comment for style.
     * @param contentComment Optional comment for content.
     * @param pleasantnessComment Optional comment for pleasantness.
     * @param originalityComment Optional comment for originality.
     * @param editionComment Optional comment for edition.
     * @throws SQLException if a database access error occurs.
     */
    void saveRating(String userid, String bookshelf, String bookTitle, String bookAuthor, String bookYear, int style, int content, int pleasantness, int originality, int edition, int finalScore, String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment) throws SQLException;
    /**
     * Saves a new book suggestion to the data source.
     * @param userId The ID of the user who made the suggestion.
     * @param sourceBookTitle The title of the book for which the suggestion is made.
     * @param sourceBookAuthor The author of the book for which the suggestion is made.
     * @param sourceBookYear The publication year of the book for which the suggestion is made.
     * @param suggestedBooks An ArrayList of Book objects being suggested.
     * @throws SQLException if a database access error occurs.
     */
    void saveSuggestion(String userId, String sourceBookTitle, String sourceBookAuthor, String sourceBookYear, ArrayList<Book> suggestedBooks) throws SQLException;
    /**
     * Saves a new comment for a specific rating criterion of a book.
     * @param userId The ID of the user who made the comment.
     * @param bookTitle The title of the book.
     * @param criterion The rating criterion for which the comment is added.
     * @param comment The comment text.
     * @throws SQLException if a database access error occurs.
     */
    void saveComment(String userId, String bookTitle, String criterion, String comment) throws SQLException;
    /**
     * Adds a single book to an existing bookshelf in the data source.
     * @param bookshelfName The name of the bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @param book The Book object to be added to the bookshelf.
     * @throws SQLException if a database access error occurs.
     */
    void addBookToBookshelf(String bookshelfName, String userid, Book book) throws SQLException;
    /**
     * Deletes a bookshelf from the data source.
     * @param userid The ID of the user who owns the bookshelf.
     * @param bookshelfName The name of the bookshelf to delete.
     * @throws SQLException if a database access error occurs.
     */
    void deleteBookshelf(String userid, String bookshelfName) throws SQLException;
    /**
     * Renames a bookshelf in the data source.
     * @param userid The ID of the user who owns the bookshelf.
     * @param oldName The current name of the bookshelf.
     * @param newName The new name for the bookshelf.
     * @throws SQLException if a database access error occurs.
     */
    void renameBookshelf(String userid, String oldName, String newName) throws SQLException;
}