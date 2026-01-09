package bookrecommender;

import java.io.Serializable;

/**
 * This class represents a composite key for a book, based on its title, author, and publication year.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class Book_key implements Serializable {
    
    private static final long serialVersionUID = 1L;

	private String Title, Author_Year;

	    /**
     * Constructs a new Book_key with the specified title, author, and year.
     * The author and year are combined to form a single Author_Year string.
     * 
     * @param Title The title of the book
     * @param Author The author of the book
     * @param Year The publication year of the book
     */
    public Book_key(String Title, String Author, String Year) {
		
		this.Title = Title;
		this.Author_Year = Author + "/" + Year;
	}
	    /**
     * Returns the title of the book.
     * @return The title of the book.
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Returns the combined author and year string for the book.
     * @return The author and year of the book in "Author/Year" format.
     */
    public String getAuthor_Year() {
        return Author_Year;
    }
    
    /**
     * Overrides the equals method to compare two Book_key objects.
     * 
     * @param obj The object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book_key other = (Book_key) obj;
        return Title.equals(other.Title) && Author_Year.equals(other.Author_Year);
    }

    /**
     * Overrides the hashCode method to generate a unique hash code.
     * 
     * @return The hash code of the object
     */
    @Override
    public int hashCode() {
        return 31 * Title.hashCode() + Author_Year.hashCode();
    }
}