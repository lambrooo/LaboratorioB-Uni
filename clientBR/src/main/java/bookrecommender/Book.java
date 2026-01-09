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
	 * GETTER METHOD
	 * @return Title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * GETTER METHOD
	 * @return Author
	 */
	public String getAuthor() {
		return Author;
	}

	/**
	 * GETTER METHOD
	 * @return Genre
	 */
	public String getGenre() {
		return Genre;
	}

	/**
	 * GETTER METHOD
	 * @return Year
	 */
	public String getYear() {
		return Year;
	}
	
	@Override
	public String toString() {
	    return this.getTitle();
	}


}