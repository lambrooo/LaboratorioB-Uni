package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Rating class.
 * Tests all getters, setters, and rating functionality.
 */
@DisplayName("Rating Tests")
class RatingTest {

    private Rating testRating;

    @BeforeEach
    void setUp() {
        testRating = new Rating(
            "My Bookshelf",
            "user123",
            "1984",
            5,  // style
            4,  // content
            5,  // pleasantness
            4,  // originality
            5,  // edition
            5,  // final score (will be recalculated)
            "Great style",
            "Good content",
            "Very pleasant",
            "Original ideas",
            "Excellent edition"
        );
    }

    @Test
    @DisplayName("Should create rating with all fields")
    void testRatingConstructorWithAllFields() {
        assertEquals("My Bookshelf", testRating.getBookshelf());
        assertEquals("user123", testRating.getUserid());
        assertEquals("1984", testRating.getBook());
        assertEquals(5, testRating.getStile());
        assertEquals(4, testRating.getContenuto());
        assertEquals(5, testRating.getGradevolezza());
        assertEquals(4, testRating.getOriginalita());
        assertEquals(5, testRating.getEdizione());
        assertEquals(5, testRating.getVotoFinale());
    }

    @Test
    @DisplayName("Should store and retrieve comments")
    void testComments() {
        assertEquals("Great style", testRating.getStyleComment());
        assertEquals("Good content", testRating.getContentComment());
        assertEquals("Very pleasant", testRating.getPleasantnessComment());
        assertEquals("Original ideas", testRating.getOriginalityComment());
        assertEquals("Excellent edition", testRating.getEditionComment());
    }

    @Test
    @DisplayName("Should set and get style comment")
    void testSetStyleComment() {
        testRating.setStyleComment("Updated style comment");
        assertEquals("Updated style comment", testRating.getStyleComment());
    }

    @Test
    @DisplayName("Should set and get content comment")
    void testSetContentComment() {
        testRating.setContentComment("Updated content comment");
        assertEquals("Updated content comment", testRating.getContentComment());
    }

    @Test
    @DisplayName("Should set and get pleasantness comment")
    void testSetPleasantnessComment() {
        testRating.setPleasantnessComment("Updated pleasantness comment");
        assertEquals("Updated pleasantness comment", testRating.getPleasantnessComment());
    }

    @Test
    @DisplayName("Should set and get originality comment")
    void testSetOriginalityComment() {
        testRating.setOriginalityComment("Updated originality comment");
        assertEquals("Updated originality comment", testRating.getOriginalityComment());
    }

    @Test
    @DisplayName("Should set and get edition comment")
    void testSetEditionComment() {
        testRating.setEditionComment("Updated edition comment");
        assertEquals("Updated edition comment", testRating.getEditionComment());
    }

    @Test
    @DisplayName("Should handle null comments")
    void testRatingWithNullComments() {
        Rating rating = new Rating(
            "Bookshelf", "user", "Book", 3, 3, 3, 3, 3, 3,
            null, null, null, null, null
        );

        assertNull(rating.getStyleComment());
        assertNull(rating.getContentComment());
        assertNull(rating.getPleasantnessComment());
        assertNull(rating.getOriginalityComment());
        assertNull(rating.getEditionComment());
    }

    @Test
    @DisplayName("Should handle empty string comments")
    void testRatingWithEmptyComments() {
        Rating rating = new Rating(
            "Bookshelf", "user", "Book", 3, 3, 3, 3, 3, 3,
            "", "", "", "", ""
        );

        assertEquals("", rating.getStyleComment());
        assertEquals("", rating.getContentComment());
        assertEquals("", rating.getPleasantnessComment());
        assertEquals("", rating.getOriginalityComment());
        assertEquals("", rating.getEditionComment());
    }

    @Test
    @DisplayName("Should handle minimum rating values")
    void testMinimumRatingValues() {
        Rating rating = new Rating(
            "Bookshelf", "user", "Book", 1, 1, 1, 1, 1, 1,
            "comment", "comment", "comment", "comment", "comment"
        );

        assertEquals(1, rating.getStile());
        assertEquals(1, rating.getContenuto());
        assertEquals(1, rating.getGradevolezza());
        assertEquals(1, rating.getOriginalita());
        assertEquals(1, rating.getEdizione());
        assertEquals(1, rating.getVotoFinale());
    }

    @Test
    @DisplayName("Should handle maximum rating values")
    void testMaximumRatingValues() {
        Rating rating = new Rating(
            "Bookshelf", "user", "Book", 5, 5, 5, 5, 5, 5,
            "comment", "comment", "comment", "comment", "comment"
        );

        assertEquals(5, rating.getStile());
        assertEquals(5, rating.getContenuto());
        assertEquals(5, rating.getGradevolezza());
        assertEquals(5, rating.getOriginalita());
        assertEquals(5, rating.getEdizione());
        assertEquals(5, rating.getVotoFinale());
    }

    @Test
    @DisplayName("Should handle zero rating values")
    void testZeroRatingValues() {
        Rating rating = new Rating(
            "Bookshelf", "user", "Book", 0, 0, 0, 0, 0, 0,
            "comment", "comment", "comment", "comment", "comment"
        );

        assertEquals(0, rating.getStile());
        assertEquals(0, rating.getContenuto());
        assertEquals(0, rating.getGradevolezza());
        assertEquals(0, rating.getOriginalita());
        assertEquals(0, rating.getEdizione());
        assertEquals(0, rating.getVotoFinale());
    }

    @Test
    @DisplayName("Should handle negative rating values")
    void testNegativeRatingValues() {
        Rating rating = new Rating(
            "Bookshelf", "user", "Book", -1, -1, -1, -1, -1, -1,
            "comment", "comment", "comment", "comment", "comment"
        );

        assertEquals(-1, rating.getStile());
        assertEquals(-1, rating.getContenuto());
        assertEquals(-1, rating.getGradevolezza());
        assertEquals(-1, rating.getOriginalita());
        assertEquals(-1, rating.getEdizione());
        assertEquals(-1, rating.getVotoFinale());
    }

    @Test
    @DisplayName("Should handle very long comments")
    void testVeryLongComments() {
        String longComment = "A".repeat(1000);
        Rating rating = new Rating(
            "Bookshelf", "user", "Book", 3, 3, 3, 3, 3, 3,
            longComment, longComment, longComment, longComment, longComment
        );

        assertEquals(longComment, rating.getStyleComment());
        assertEquals(1000, rating.getStyleComment().length());
    }

    @Test
    @DisplayName("Should update comments after creation")
    void testUpdateCommentsAfterCreation() {
        Rating rating = new Rating(
            "Bookshelf", "user", "Book", 3, 3, 3, 3, 3, 3,
            "old", "old", "old", "old", "old"
        );

        rating.setStyleComment("new style");
        rating.setContentComment("new content");
        rating.setPleasantnessComment("new pleasantness");
        rating.setOriginalityComment("new originality");
        rating.setEditionComment("new edition");

        assertEquals("new style", rating.getStyleComment());
        assertEquals("new content", rating.getContentComment());
        assertEquals("new pleasantness", rating.getPleasantnessComment());
        assertEquals("new originality", rating.getOriginalityComment());
        assertEquals("new edition", rating.getEditionComment());
    }

    @Test
    @DisplayName("Should handle null bookshelf name")
    void testNullBookshelfName() {
        Rating rating = new Rating(
            null, "user", "Book", 3, 3, 3, 3, 3, 3,
            "comment", "comment", "comment", "comment", "comment"
        );

        assertNull(rating.getBookshelf());
    }

    @Test
    @DisplayName("Should handle null user id")
    void testNullUserId() {
        Rating rating = new Rating(
            "Bookshelf", null, "Book", 3, 3, 3, 3, 3, 3,
            "comment", "comment", "comment", "comment", "comment"
        );

        assertNull(rating.getUserid());
    }

    @Test
    @DisplayName("Should handle null book title")
    void testNullBookTitle() {
        Rating rating = new Rating(
            "Bookshelf", "user", null, 3, 3, 3, 3, 3, 3,
            "comment", "comment", "comment", "comment", "comment"
        );

        assertNull(rating.getBook());
    }
}
