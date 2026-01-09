package bookrecommender;

import java.io.Serializable;

/**
 * This class represents a recommended book with its title and the count of recommendations.
 * It is used to store and manage information about books suggested by users.
 * 
 * @version 1.0.0 (01-07-2024)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class RecommendedBook implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private int count;

    /**
     * Constructs a new RecommendedBook with the specified title and count.
     * 
     * @param title The title of the recommended book
     * @param count The number of times this book has been recommended
     */
    public RecommendedBook(String title, int count) {
        this.title = title;
        this.count = count;
    }

    /**
     * Gets the title of the recommended book.
     * 
     * @return The title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the recommended book.
     * 
     * @param title The new title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the count of recommendations for this book.
     * 
     * @return The number of times this book has been recommended
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count of recommendations for this book.
     * 
     * @param count The new count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
}