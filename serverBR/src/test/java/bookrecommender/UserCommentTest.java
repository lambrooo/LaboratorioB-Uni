package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserComment class.
 * Tests userid and comment getters.
 */
@DisplayName("UserComment Tests")
class UserCommentTest {

    private UserComment userComment;

    @BeforeEach
    void setUp() {
        userComment = new UserComment("user123", "This is a great book!");
    }

    @Test
    @DisplayName("Should create user comment with userid and comment")
    void testConstructor() {
        assertEquals("user123", userComment.getUserId());
        assertEquals("This is a great book!", userComment.getComment());
    }

    @Test
    @DisplayName("Should handle null userid")
    void testNullUserId() {
        UserComment comment = new UserComment(null, "Comment");
        assertNull(comment.getUserId());
        assertEquals("Comment", comment.getComment());
    }

    @Test
    @DisplayName("Should handle null comment")
    void testNullComment() {
        UserComment comment = new UserComment("user123", null);
        assertEquals("user123", comment.getUserId());
        assertNull(comment.getComment());
    }

    @Test
    @DisplayName("Should handle empty string userid")
    void testEmptyStringUserId() {
        UserComment comment = new UserComment("", "Comment");
        assertEquals("", comment.getUserId());
    }

    @Test
    @DisplayName("Should handle empty string comment")
    void testEmptyStringComment() {
        UserComment comment = new UserComment("user123", "");
        assertEquals("", comment.getComment());
    }

    @Test
    @DisplayName("Should handle very long comment")
    void testVeryLongComment() {
        String longComment = "A".repeat(5000);
        UserComment comment = new UserComment("user123", longComment);
        assertEquals(longComment, comment.getComment());
        assertEquals(5000, comment.getComment().length());
    }

    @Test
    @DisplayName("Should handle very long userid")
    void testVeryLongUserId() {
        String longUserId = "user" + "1".repeat(1000);
        UserComment comment = new UserComment(longUserId, "Comment");
        assertEquals(longUserId, comment.getUserId());
    }

    @Test
    @DisplayName("Should handle special characters in comment")
    void testSpecialCharactersInComment() {
        String specialComment = "Stile: Eccellente! Contenuto: ★★★★★ @#$%^&*()";
        UserComment comment = new UserComment("user123", specialComment);
        assertEquals(specialComment, comment.getComment());
    }

    @Test
    @DisplayName("Should handle special characters in userid")
    void testSpecialCharactersInUserId() {
        String specialUserId = "user_123-abc@domain.com";
        UserComment comment = new UserComment(specialUserId, "Comment");
        assertEquals(specialUserId, comment.getUserId());
    }

    @Test
    @DisplayName("Should handle multiline comment")
    void testMultilineComment() {
        String multilineComment = "Line 1\nLine 2\nLine 3";
        UserComment comment = new UserComment("user123", multilineComment);
        assertEquals(multilineComment, comment.getComment());
        assertTrue(comment.getComment().contains("\n"));
    }

    @Test
    @DisplayName("Should handle comment with HTML tags")
    void testCommentWithHtmlTags() {
        String htmlComment = "<b>Great book!</b> <i>Highly recommended</i>";
        UserComment comment = new UserComment("user123", htmlComment);
        assertEquals(htmlComment, comment.getComment());
    }

    @Test
    @DisplayName("Should handle comment with quotes")
    void testCommentWithQuotes() {
        String quotedComment = "The author said \"This is amazing\" and I agree!";
        UserComment comment = new UserComment("user123", quotedComment);
        assertEquals(quotedComment, comment.getComment());
    }

    @Test
    @DisplayName("Should handle Unicode characters in comment")
    void testUnicodeCharactersInComment() {
        String unicodeComment = "素晴らしい本です！ 🎉 📚";
        UserComment comment = new UserComment("user123", unicodeComment);
        assertEquals(unicodeComment, comment.getComment());
    }

    @Test
    @DisplayName("Should handle whitespace-only comment")
    void testWhitespaceOnlyComment() {
        String whitespaceComment = "   \t\n   ";
        UserComment comment = new UserComment("user123", whitespaceComment);
        assertEquals(whitespaceComment, comment.getComment());
    }

    @Test
    @DisplayName("Should handle all null parameters")
    void testAllNullParameters() {
        UserComment comment = new UserComment(null, null);
        assertNull(comment.getUserId());
        assertNull(comment.getComment());
    }

    @Test
    @DisplayName("Should handle numeric userid")
    void testNumericUserId() {
        UserComment comment = new UserComment("12345", "Comment");
        assertEquals("12345", comment.getUserId());
    }

    @Test
    @DisplayName("Should preserve comment formatting")
    void testPreserveCommentFormatting() {
        String formattedComment = "Stile: Excellent\n  - Very well written\n  - Great pacing\nContenuto: Good";
        UserComment comment = new UserComment("user123", formattedComment);
        assertEquals(formattedComment, comment.getComment());
    }
}
