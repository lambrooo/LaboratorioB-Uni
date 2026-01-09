package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Rating_key class.
 * Tests equals, hashCode, and key generation.
 */
@DisplayName("Rating_key Tests")
class Rating_keyTest {

    @Test
    @DisplayName("Should create rating key with book and userid")
    void testConstructor() {
        Rating_key key = new Rating_key("1984", "user123");

        assertEquals("1984", key.getBook());
        assertEquals("user123", key.getUserid());
    }

    @Test
    @DisplayName("Should handle equals for same object")
    void testEqualsSameObject() {
        Rating_key key1 = new Rating_key("1984", "user123");
        assertTrue(key1.equals(key1));
    }

    @Test
    @DisplayName("Should handle equals for identical keys")
    void testEqualsIdenticalKeys() {
        Rating_key key1 = new Rating_key("1984", "user123");
        Rating_key key2 = new Rating_key("1984", "user123");
        assertTrue(key1.equals(key2));
        assertTrue(key2.equals(key1));
    }

    @Test
    @DisplayName("Should handle equals for different books")
    void testEqualsDifferentBooks() {
        Rating_key key1 = new Rating_key("1984", "user123");
        Rating_key key2 = new Rating_key("Animal Farm", "user123");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle equals for different userids")
    void testEqualsDifferentUserIds() {
        Rating_key key1 = new Rating_key("1984", "user123");
        Rating_key key2 = new Rating_key("1984", "user456");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle equals with null")
    void testEqualsWithNull() {
        Rating_key key1 = new Rating_key("1984", "user123");
        assertFalse(key1.equals(null));
    }

    @Test
    @DisplayName("Should handle equals with different class")
    void testEqualsWithDifferentClass() {
        Rating_key key1 = new Rating_key("1984", "user123");
        assertFalse(key1.equals("Not a Rating_key"));
    }

    @Test
    @DisplayName("Should generate same hashCode for identical keys")
    void testHashCodeIdenticalKeys() {
        Rating_key key1 = new Rating_key("1984", "user123");
        Rating_key key2 = new Rating_key("1984", "user123");
        assertEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should generate different hashCode for different keys")
    void testHashCodeDifferentKeys() {
        Rating_key key1 = new Rating_key("1984", "user123");
        Rating_key key2 = new Rating_key("Animal Farm", "user456");
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should handle null book in equals")
    void testNullBookEquals() {
        try {
            Rating_key key1 = new Rating_key(null, "user123");
            Rating_key key2 = new Rating_key(null, "user123");
            assertThrows(NullPointerException.class, () -> key1.equals(key2));
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Should handle null userid in equals")
    void testNullUserIdEquals() {
        try {
            Rating_key key1 = new Rating_key("1984", null);
            Rating_key key2 = new Rating_key("1984", null);
            assertThrows(NullPointerException.class, () -> key1.equals(key2));
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testEmptyStrings() {
        Rating_key key = new Rating_key("", "");
        assertEquals("", key.getBook());
        assertEquals("", key.getUserid());
    }

    @Test
    @DisplayName("Should handle special characters in book")
    void testSpecialCharactersInBook() {
        Rating_key key = new Rating_key("L'Étranger", "user123");
        assertEquals("L'Étranger", key.getBook());
    }

    @Test
    @DisplayName("Should handle special characters in userid")
    void testSpecialCharactersInUserId() {
        Rating_key key = new Rating_key("1984", "user_123");
        assertEquals("user_123", key.getUserid());
    }

    @Test
    @DisplayName("Should be serializable")
    void testIsSerializable() {
        Rating_key key = new Rating_key("1984", "user123");
        assertTrue(key instanceof java.io.Serializable);
    }

    @Test
    @DisplayName("Should be case sensitive for book")
    void testCaseSensitiveBook() {
        Rating_key key1 = new Rating_key("book", "user123");
        Rating_key key2 = new Rating_key("BOOK", "user123");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should be case sensitive for userid")
    void testCaseSensitiveUserId() {
        Rating_key key1 = new Rating_key("1984", "user123");
        Rating_key key2 = new Rating_key("1984", "USER123");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle very long strings")
    void testVeryLongStrings() {
        String longBook = "A".repeat(500);
        String longUserId = "B".repeat(500);
        Rating_key key = new Rating_key(longBook, longUserId);

        assertEquals(longBook, key.getBook());
        assertEquals(longUserId, key.getUserid());
    }

    @Test
    @DisplayName("Should maintain hashCode consistency")
    void testHashCodeConsistency() {
        Rating_key key = new Rating_key("1984", "user123");
        int hash1 = key.hashCode();
        int hash2 = key.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Should handle whitespace in fields")
    void testWhitespaceInFields() {
        Rating_key key = new Rating_key("  1984  ", "  user123  ");
        assertEquals("  1984  ", key.getBook());
        assertEquals("  user123  ", key.getUserid());
    }

    @Test
    @DisplayName("Should allow same book for different users")
    void testSameBookDifferentUsers() {
        Rating_key key1 = new Rating_key("1984", "user1");
        Rating_key key2 = new Rating_key("1984", "user2");
        assertFalse(key1.equals(key2));
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should allow different books for same user")
    void testDifferentBooksSameUser() {
        Rating_key key1 = new Rating_key("1984", "user123");
        Rating_key key2 = new Rating_key("Animal Farm", "user123");
        assertFalse(key1.equals(key2));
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should ensure one rating per book per user")
    void testOneRatingPerBookPerUser() {
        Rating_key key1 = new Rating_key("1984", "user123");
        Rating_key key2 = new Rating_key("1984", "user123");

        // Same book and user should create equal keys
        assertTrue(key1.equals(key2));
        assertEquals(key1.hashCode(), key2.hashCode());
    }
}
