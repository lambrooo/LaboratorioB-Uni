package bookrecommender;

/**
 * This class represents a book with its title, author, publication year, and genre.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class Book implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String Title, Author, Year, Genre;
	
	    /**
     * Constructs an empty Book object.
     */
    public Book() {}
	
	    /**
     * Constructs a new Book with the specified title, author, year, and genre.
     * 
     * @param Title The title of the book
     * @param Author The author of the book
     * @param Year The publication year of the book
     * @param Genre The genre of the book
     */
    public Book(String Title, String Author, String Year, String Genre) {
		
		this.Title = Title;
		this.Author = Author;
		this.Year = Year;
		this.Genre = Genre;
	}

	/**
	 * Returns the title of the book.
	 * @return The title of the book
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * Returns the author of the book.
	 * @return The author of the book
	 */
	public String getAuthor() {
		return Author;
	}

	/**
	 * Returns the genre of the book.
	 * @return The genre of the book
	 */
	public String getGenre() {
		return Genre;
	}

	/**
	 * Returns the publication year of the book.
	 * @return The publication year of the book
	 */
	public String getYear() {
		return Year;
	}
	
	@Override
	public String toString() {
	    return this.getTitle();
	}


}
