package bookrecommender;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * This Class is used to manage Bookshelf
 * 
 * @version 1.0.0 (01-07-2024)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class Bookshelf implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String Name, Userid;
    private ArrayList<String> Books;
    
    public Bookshelf() {
        // Default constructor
    }
    
    public Bookshelf(String Name,String Userid, ArrayList<String> Books){
        
        this.Name = Name;
        this.Userid = Userid;
        this.Books = Books;
    }

    /**
     * Returns the name of the bookshelf.
     * @return The name of the bookshelf
     */
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Returns the user ID of the bookshelf owner.
     * @return The user ID of the bookshelf owner
     */
    public String getUserid() {
        return Userid;
    }

    /**
     * Returns the list of books in the bookshelf.
     * @return The list of books in the bookshelf
     */
    public ArrayList<String> getBooks() {
        return Books;
    }    
}
