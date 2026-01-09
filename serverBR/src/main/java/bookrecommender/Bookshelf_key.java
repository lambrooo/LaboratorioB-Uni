package bookrecommender;

import java.io.Serializable;

/**
 * Represents a composite key for identifying bookshelves.
 * Combines the bookshelf name and user ID to form a unique identifier.
 * 
 * @version 1.0.0 (01-07-2024)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class Bookshelf_key implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String Name, userid;

    /**
     * Constructs a new Bookshelf_key with the specified name and user ID.
     * 
     * @param Name The name of the bookshelf
     * @param userid The ID of the user who owns the bookshelf
     */
    public Bookshelf_key(String Name, String userid) {
        
        this.Name = Name;
        this.userid = userid;
    }

    /**
     * Returns the name of the bookshelf.
     * @return The bookshelf name
     */
    public String getName() {
        return Name;
    }

    /**
     * Returns the user ID of the bookshelf owner.
     * @return The user ID
     */
    public String getuserid() {
        return userid;
    }
    
    /**
     * Compares this Bookshelf_key to another object for equality.
     * Two keys are equal if they have the same name and user ID.
     * 
     * @param obj The object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bookshelf_key other = (Bookshelf_key) obj;
        return Name.equals(other.Name) && userid.equals(other.userid);
    }

    /**
     * Generates a hash code for this Bookshelf_key.
     * 
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return 31 * Name.hashCode() + userid.hashCode();
    }
}