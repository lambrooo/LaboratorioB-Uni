package bookrecommender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * This class provides a JDBC-based implementation of the DataAccessService interface.
 * It connects to a PostgreSQL database to perform CRUD operations on application data.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class JdbcDataAccessService implements DataAccessService {

    private HikariDataSource dataSource;

    /**
     * Constructs a new JdbcDataAccessService.
     * Initializes the database connection parameters and loads the PostgreSQL JDBC driver.
     * 
     * @param url The JDBC URL for the PostgreSQL database.
     * @param user The username for database access.
     * @param password The password for database access.
     */
    public JdbcDataAccessService(String url, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    /**
     * Establishes and returns a new database connection from the connection pool.
     * @return A new Connection object.
     * @throws SQLException if a database access error occurs.
     */
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the connection pool.
     */
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    /**
     * Retrieves all registered users from the database.
     * @return A HashMap of User objects, keyed by user ID.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public HashMap<String, User> getAllUsers() throws SQLException {
        HashMap<String, User> users = new HashMap<>();
        String sql = "SELECT name, surname, fiscalCode, address, email, userid, password FROM UtentiRegistrati";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("fiscalCode"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("userid"),
                        rs.getString("password")
                );
                users.put(user.getUserid(), user);
            }
        }
        return users;
    }

    /**
     * Retrieves all books from the database.
     * @return A HashMap of Book objects, keyed by Book_key.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public HashMap<Book_key, Book> getAllBooks() throws SQLException {
        HashMap<Book_key, Book> books = new HashMap<>();
        String sql = "SELECT title, author, year, genre FROM Libri";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("year"),
                        rs.getString("genre")
                );
                books.put(new Book_key(book.getTitle(), book.getAuthor(), book.getYear()), book);
            }
        }
        return books;
    }

    /**
     * Retrieves all bookshelves from the database.
     * @return A HashMap of Bookshelf objects, keyed by Bookshelf_key.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public HashMap<Bookshelf_key, Bookshelf> getAllBookshelves() throws SQLException {
        HashMap<Bookshelf_key, Bookshelf> bookshelves = new HashMap<>();
        String sql = "SELECT bookshelf_name, userid, book_title, book_author, book_year FROM Librerie";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String bookshelfName = rs.getString("bookshelf_name");
                String userId = rs.getString("userid");
                String bookTitle = rs.getString("book_title");

                Bookshelf_key key = new Bookshelf_key(bookshelfName, userId);
                bookshelves.computeIfAbsent(key, k -> new Bookshelf(bookshelfName, userId, new ArrayList<>()))
                           .getBooks().add(bookTitle);
            }
        }
        return bookshelves;
    }

    /**
     * Retrieves all book ratings from the database.
     * @return A HashMap of Rating objects, keyed by Rating_key.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public HashMap<Rating_key, Rating> getAllRatings() throws SQLException {
        HashMap<Rating_key, Rating> ratings = new HashMap<>();
        String sql = "SELECT userid, bookshelf_name, book_title, book_author, book_year, style_rating, content_rating, pleasantness_rating, originality_rating, edition_rating, final_score, style_comment, content_comment, pleasantness_comment, originality_comment, edition_comment FROM ValutazioniLibri";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Rating rating = new Rating(
                        rs.getString("bookshelf_name"),
                        rs.getString("userid"),
                        rs.getString("book_title"),
                        rs.getInt("style_rating"),
                        rs.getInt("content_rating"),
                        rs.getInt("pleasantness_rating"),
                        rs.getInt("originality_rating"),
                        rs.getInt("edition_rating"),
                        rs.getInt("final_score"),
                        rs.getString("style_comment"),
                        rs.getString("content_comment"),
                        rs.getString("pleasantness_comment"),
                        rs.getString("originality_comment"),
                        rs.getString("edition_comment")
                );
                ratings.put(new Rating_key(rating.getBook(), rating.getUserid()), rating);
            }
        }
        return ratings;
    }

    /**
     * Retrieves all book suggestions from the database.
     * @return A HashMap of ArrayLists of suggested book titles, keyed by user ID and source book title.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public HashMap<String, ArrayList<String>> getAllSuggestions() throws SQLException {
        HashMap<String, ArrayList<String>> suggestions = new HashMap<>();
        String sql = "SELECT userid, source_book_title, suggested_book_title FROM ConsigliLibri";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String userId = rs.getString("userid");
                String sourceBookTitle = rs.getString("source_book_title");
                String suggestedBookTitle = rs.getString("suggested_book_title");
                String key = userId + "|" + sourceBookTitle;
                suggestions.computeIfAbsent(key, k -> new ArrayList<>()).add(suggestedBookTitle);
            }
        }
        return suggestions;
    }

    /**
     * Saves a new user to the database.
     * @param user The User object to save.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO UtentiRegistrati (name, surname, fiscalCode, address, email, userid, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getFiscalCode());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getUserid());
            pstmt.setString(7, user.getPassword());
            pstmt.executeUpdate();
        }
    }

    /**
     * Saves a new bookshelf to the database.
     * @param bookshelfName The name of the bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @param books An ArrayList of Book objects to be associated with the bookshelf.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void saveBookshelf(String bookshelfName, String userid, ArrayList<Book> books) throws SQLException {
        String sql = "INSERT INTO Librerie (bookshelf_name, userid, book_title, book_author, book_year) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Book book : books) {
                pstmt.setString(1, bookshelfName);
                pstmt.setString(2, userid);
                pstmt.setString(3, book.getTitle());
                pstmt.setString(4, book.getAuthor());
                pstmt.setString(5, book.getYear());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    /**
     * Saves a new book rating to the database.
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
    @Override
    public void saveRating(String userid, String bookshelf, String bookTitle, String bookAuthor, String bookYear, int style, int content, int pleasantness, int originality, int edition, int finalScore, String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment) throws SQLException {
        String sql = "INSERT INTO ValutazioniLibri (userid, bookshelf_name, book_title, book_author, book_year, style_rating, content_rating, pleasantness_rating, originality_rating, edition_rating, final_score, style_comment, content_comment, pleasantness_comment, originality_comment, edition_comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            pstmt.setString(2, bookshelf);
            pstmt.setString(3, bookTitle);
            pstmt.setString(4, bookAuthor);
            pstmt.setString(5, bookYear);
            pstmt.setInt(6, style);
            pstmt.setInt(7, content);
            pstmt.setInt(8, pleasantness);
            pstmt.setInt(9, originality);
            pstmt.setInt(10, edition);
            pstmt.setInt(11, finalScore);
            pstmt.setString(12, styleComment);
            pstmt.setString(13, contentComment);
            pstmt.setString(14, pleasantnessComment);
            pstmt.setString(15, originalityComment);
            pstmt.setString(16, editionComment);
            pstmt.executeUpdate();
        }
    }

    /**
     * Saves a new book suggestion to the database.
     * @param userId The ID of the user who made the suggestion.
     * @param sourceBookTitle The title of the book for which the suggestion is made.
     * @param sourceBookAuthor The author of the book for which the suggestion is made.
     * @param sourceBookYear The publication year of the book for which the suggestion is made.
     * @param suggestedBooks An ArrayList of Book objects being suggested.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void saveSuggestion(String userId, String sourceBookTitle, String sourceBookAuthor, String sourceBookYear, ArrayList<Book> suggestedBooks) throws SQLException {
        // Use ON CONFLICT DO NOTHING to gracefully handle duplicate suggestions
        String sql = "INSERT INTO ConsigliLibri (userid, source_book_title, source_book_author, source_book_year, suggested_book_title, suggested_book_author, suggested_book_year) VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Book suggestedBook : suggestedBooks) {
                pstmt.setString(1, userId);
                pstmt.setString(2, sourceBookTitle);
                pstmt.setString(3, sourceBookAuthor);
                pstmt.setString(4, sourceBookYear);
                pstmt.setString(5, suggestedBook.getTitle());
                pstmt.setString(6, suggestedBook.getAuthor());
                pstmt.setString(7, suggestedBook.getYear());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    /**
     * Saves a new comment for a specific rating criterion of a book to the database.
     * @param userId The ID of the user who made the comment.
     * @param bookTitle The title of the book.
     * @param criterion The rating criterion for which the comment is added.
     * @param comment The comment text.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void saveComment(String userId, String bookTitle, String criterion, String comment) throws SQLException {
        String columnName;
        switch (criterion.toLowerCase()) {
            case "stile":
                columnName = "style_comment";
                break;
            case "contenuto":
                columnName = "content_comment";
                break;
            case "gradevolezza":
                columnName = "pleasantness_comment";
                break;
            case "originalita":
                columnName = "originality_comment";
                break;
            case "edizione":
                columnName = "edition_comment";
                break;
            default:
                throw new IllegalArgumentException("Invalid comment criterion: " + criterion);
        }

        String sql = "UPDATE ValutazioniLibri SET " + columnName + " = ? WHERE userid = ? AND book_title = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, comment);
            pstmt.setString(2, userId);
            pstmt.setString(3, bookTitle);
            pstmt.executeUpdate();
        }
    }

    /**
     * Adds a single book to an existing bookshelf in the database.
     * @param bookshelfName The name of the bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @param book The Book object to be added to the bookshelf.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void addBookToBookshelf(String bookshelfName, String userid, Book book) throws SQLException {
        String sql = "INSERT INTO Librerie (bookshelf_name, userid, book_title, book_author, book_year) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookshelfName);
            pstmt.setString(2, userid);
            pstmt.setString(3, book.getTitle());
            pstmt.setString(4, book.getAuthor());
            pstmt.setString(5, book.getYear());
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a bookshelf from the data source.
     * @param userid The ID of the user who owns the bookshelf.
     * @param bookshelfName The name of the bookshelf to delete.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void deleteBookshelf(String userid, String bookshelfName) throws SQLException {
        // First delete associated ratings (Child records)
        String sqlRatings = "DELETE FROM ValutazioniLibri WHERE userid = ? AND bookshelf_name = ?";
        // Then delete the bookshelf (Parent records)
        String sqlBookshelf = "DELETE FROM Librerie WHERE userid = ? AND bookshelf_name = ?";
        
        try (Connection conn = getConnection()) {
            boolean originalAutoCommit = conn.getAutoCommit();
            try {
                conn.setAutoCommit(false); // Start transaction

                try (PreparedStatement pstmtRatings = conn.prepareStatement(sqlRatings)) {
                    pstmtRatings.setString(1, userid);
                    pstmtRatings.setString(2, bookshelfName);
                    pstmtRatings.executeUpdate();
                }

                try (PreparedStatement pstmtBookshelf = conn.prepareStatement(sqlBookshelf)) {
                    pstmtBookshelf.setString(1, userid);
                    pstmtBookshelf.setString(2, bookshelfName);
                    pstmtBookshelf.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(originalAutoCommit);
            }
        }
    }

    /**
     * Renames a bookshelf in the data source.
     * Handles Foreign Key constraints by cloning parent rows, updating children, and deleting old parents.
     * @param userid The ID of the user who owns the bookshelf.
     * @param oldName The current name of the bookshelf.
     * @param newName The new name for the bookshelf.
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void renameBookshelf(String userid, String oldName, String newName) throws SQLException {
        // Strategy: 
        // 1. Insert new rows in Librerie with newName (copying from oldName)
        // 2. Update ValutazioniLibri to refer to newName
        // 3. Delete rows in Librerie with oldName
        
        String sqlCopy = "INSERT INTO Librerie (bookshelf_name, userid, book_title, book_author, book_year) " +
                         "SELECT ?, userid, book_title, book_author, book_year FROM Librerie " +
                         "WHERE userid = ? AND bookshelf_name = ?";
                         
        String sqlUpdateRatings = "UPDATE ValutazioniLibri SET bookshelf_name = ? " +
                                  "WHERE userid = ? AND bookshelf_name = ?";
                                  
        String sqlDeleteOld = "DELETE FROM Librerie WHERE userid = ? AND bookshelf_name = ?";

        try (Connection conn = getConnection()) {
            boolean originalAutoCommit = conn.getAutoCommit();
            try {
                conn.setAutoCommit(false); // Start transaction

                // 1. Copy Bookshelf Rows
                try (PreparedStatement pstmtCopy = conn.prepareStatement(sqlCopy)) {
                    pstmtCopy.setString(1, newName);
                    pstmtCopy.setString(2, userid);
                    pstmtCopy.setString(3, oldName);
                    pstmtCopy.executeUpdate();
                }

                // 2. Update Ratings
                try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdateRatings)) {
                    pstmtUpdate.setString(1, newName); // New Parent Name
                    pstmtUpdate.setString(2, userid);
                    pstmtUpdate.setString(3, oldName); // Old Parent Name condition
                    pstmtUpdate.executeUpdate();
                }

                // 3. Delete Old Bookshelf Rows
                try (PreparedStatement pstmtDelete = conn.prepareStatement(sqlDeleteOld)) {
                    pstmtDelete.setString(1, userid);
                    pstmtDelete.setString(2, oldName);
                    pstmtDelete.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(originalAutoCommit);
            }
        }
    }
}