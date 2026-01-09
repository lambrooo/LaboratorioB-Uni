package bookrecommender;

import java.io.Serializable;

/**
 * This class represents a comment made by a user on a book.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class UserComment implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String comment;

    /**
     * Constructs a UserComment with the specified user ID and comment.
     *
     * @param userId the user ID who made the comment
     * @param comment the text of the comment
     */
    public UserComment(String userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }

    /**
     * Gets the user ID of the user who made the comment.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the text of the comment.
     *
     * @return the comment text
     */
    public String getComment() {
        return comment;
    }
}