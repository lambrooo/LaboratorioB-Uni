package bookrecommender;

import java.io.Serializable;

/**
 * This class creates a key used to manage book ratings.
 * It combines a book title and a user ID to form a unique identifier for each rating.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see Book
 * @see User
 */
public class Rating_key implements Serializable {
    
    private static final long serialVersionUID = 1L;

    String book;
    String userid;

    /**
     * Constructor for the rating key.
     * 
     * @param book The title of the rated book
     * @param userid The ID of the user who made the rating
     */
    public Rating_key(String book, String userid) {
        this.book = book;
        this.userid = userid;
    }

    /**
     * Returns the book title.
     * 
     * @return The book title
     */
    public String getBook() {
        return book;
    }

    /**
     * Returns the user ID.
     * 
     * @return The user ID
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Overrides the equals method to compare two Rating_key objects.
     * 
     * @param obj The object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rating_key other = (Rating_key) obj;
        return book.equals(other.book) && userid.equals(other.userid);
    }

    /**
     * Overrides the hashCode method to generate a unique hash code.
     * 
     * @return The hash code of the object
     */
    @Override
    public int hashCode() {
        return 31 * book.hashCode() + userid.hashCode();
    }
}