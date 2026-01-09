package bookrecommender;

/**
 * This class represents a book rating, including various criteria scores and optional comments.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */

public class Rating {

    private String BookshelfName, userid, Book;
    private int Stile, Contenuto, Gradevolezza, Originalita, Edizione, VotoFinale;
    private String styleComment, contentComment, pleasantnessComment, originalityComment, editionComment;

    /**
     * Constructs a new Rating object with detailed rating information and comments.
     * 
     * @param BookshelfName The name of the bookshelf the book belongs to.
     * @param userid The ID of the user who provided the rating.
     * @param Book The title of the book being rated.
     * @param stile The rating for style (1-5).
     * @param contenuto The rating for content (1-5).
     * @param gradevolezza The rating for pleasantness (1-5).
     * @param originalita The rating for originality (1-5).
     * @param edizione The rating for edition (1-5).
     * @param votofinale The final calculated score (1-5).
     * @param styleComment Optional comment for style.
     * @param contentComment Optional comment for content.
     * @param pleasantnessComment Optional comment for pleasantness.
     * @param originalityComment Optional comment for originality.
     * @param editionComment Optional comment for edition.
     */
    public Rating(String BookshelfName, String userid, String Book, int stile, int contenuto,
                int gradevolezza, int originalita, int edizione, int votofinale,
                String styleComment, String contentComment, String pleasantnessComment, String originalityComment, String editionComment){
        
        this.BookshelfName = BookshelfName;
        this.userid = userid;
        this.Book = Book;
        this.Stile = stile;
        this.Contenuto = contenuto;
        this.Gradevolezza = gradevolezza;
        this.Originalita = originalita;
        this.Edizione = edizione;
        this.VotoFinale = votofinale;
        this.styleComment = styleComment;
        this.contentComment = contentComment;
        this.pleasantnessComment = pleasantnessComment;
        this.originalityComment = originalityComment;
        this.editionComment = editionComment;
    }

    /**
     * Returns the name of the bookshelf the book belongs to.
     * @return The bookshelf name.
     */
    public String getBookshelf() {
        return BookshelfName;
    }

    /**
     * Returns the ID of the user who provided the rating.
     * @return The user ID.
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Returns the title of the book that was rated.
     * @return The book title.
     */
    public String getBook() {
        return Book;
    }
    
    /**
     * Returns the style rating.
     * @return The style rating (1-5).
     */
    public int getStile() {
        return Stile;
    }
    
    /**
     * Returns the content rating.
     * @return The content rating (1-5).
     */
    public int getContenuto() {
        return Contenuto;
    }
    
    /**
     * Returns the pleasantness rating.
     * @return The pleasantness rating (1-5).
     */
    public int getGradevolezza() {
        return Gradevolezza;
    }
    
    /**
     * Returns the originality rating.
     * @return The originality rating (1-5).
     */
    public int getOriginalita() {
        return Originalita;
    }
    
    /**
     * Returns the edition rating.
     * @return The edition rating (1-5).
     */
    public int getEdizione() {
        return Edizione;
    }
    
    /**
     * Returns the final calculated score.
     * @return The final score (1-5).
     */
    public int getVotoFinale() {
        return VotoFinale;
    }

    /**
     * Returns the comment for the style rating.
     * @return The style comment.
     */
    public String getStyleComment() {
        return styleComment;
    }

    /**
     * Sets the comment for the style rating.
     * @param styleComment The new style comment.
     */
    public void setStyleComment(String styleComment) {
        this.styleComment = styleComment;
    }

    /**
     * Returns the comment for the content rating.
     * @return The content comment.
     */
    public String getContentComment() {
        return contentComment;
    }

    /**
     * Sets the comment for the content rating.
     * @param contentComment The new content comment.
     */
    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    /**
     * Returns the comment for the pleasantness rating.
     * @return The pleasantness comment.
     */
    public String getPleasantnessComment() {
        return pleasantnessComment;
    }

    /**
     * Sets the comment for the pleasantness rating.
     * @param pleasantnessComment The new pleasantness comment.
     */
    public void setPleasantnessComment(String pleasantnessComment) {
        this.pleasantnessComment = pleasantnessComment;
    }

    /**
     * Returns the comment for the originality rating.
     * @return The originality comment.
     */
    public String getOriginalityComment() {
        return originalityComment;
    }

    /**
     * Sets the comment for the originality rating.
     * @param originalityComment The new originality comment.
     */
    public void setOriginalityComment(String originalityComment) {
        this.originalityComment = originalityComment;
    }

    /**
     * Returns the comment for the edition rating.
     * @return The edition comment.
     */
    public String getEditionComment() {
        return editionComment;
    }

    /**
     * Sets the comment for the edition rating.
     * @param editionComment The new edition comment.
     */
    public void setEditionComment(String editionComment) {
        this.editionComment = editionComment;
    }
}
