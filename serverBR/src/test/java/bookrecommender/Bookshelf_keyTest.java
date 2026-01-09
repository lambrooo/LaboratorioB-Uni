package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Bookshelf_key class.
 * Tests equals, hashCode, and key generation.
 */
@DisplayName("Bookshelf_key Tests")
class Bookshelf_keyTest {

    @Test
    @DisplayName("Should create bookshelf key with name and userid")
    void testConstructor() {
        Bookshelf_key key = new Bookshelf_key("My Favorites", "user123");

        assertEquals("My Favorites", key.getName());
        assertEquals("user123", key.getuserid());
    }

    @Test
    @DisplayName("Should handle equals for same object")
    void testEqualsSameObject() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        assertTrue(key1.equals(key1));
    }

    @Test
    @DisplayName("Should handle equals for identical keys")
    void testEqualsIdenticalKeys() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        Bookshelf_key key2 = new Bookshelf_key("Favorites", "user123");
        assertTrue(key1.equals(key2));
        assertTrue(key2.equals(key1));
    }

    @Test
    @DisplayName("Should handle equals for different names")
    void testEqualsDifferentNames() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        Bookshelf_key key2 = new Bookshelf_key("ReadLater", "user123");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle equals for different userids")
    void testEqualsDifferentUserIds() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        Bookshelf_key key2 = new Bookshelf_key("Favorites", "user456");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle equals with null")
    void testEqualsWithNull() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        assertFalse(key1.equals(null));
    }

    @Test
    @DisplayName("Should handle equals with different class")
    void testEqualsWithDifferentClass() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        assertFalse(key1.equals("Not a Bookshelf_key"));
    }

    @Test
    @DisplayName("Should generate same hashCode for identical keys")
    void testHashCodeIdenticalKeys() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        Bookshelf_key key2 = new Bookshelf_key("Favorites", "user123");
        assertEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should generate different hashCode for different keys")
    void testHashCodeDifferentKeys() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        Bookshelf_key key2 = new Bookshelf_key("ReadLater", "user456");
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should handle null name in equals")
    void testNullNameEquals() {
        try {
            Bookshelf_key key1 = new Bookshelf_key(null, "user123");
            Bookshelf_key key2 = new Bookshelf_key(null, "user123");
            assertThrows(NullPointerException.class, () -> key1.equals(key2));
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Should handle null userid in equals")
    void testNullUserIdEquals() {
        try {
            Bookshelf_key key1 = new Bookshelf_key("Favorites", null);
            Bookshelf_key key2 = new Bookshelf_key("Favorites", null);
            assertThrows(NullPointerException.class, () -> key1.equals(key2));
        } catch (NullPointerException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testEmptyStrings() {
        Bookshelf_key key = new Bookshelf_key("", "");
        assertEquals("", key.getName());
        assertEquals("", key.getuserid());
    }

    @Test
    @DisplayName("Should handle special characters in name")
    void testSpecialCharactersInName() {
        Bookshelf_key key = new Bookshelf_key("Libri d'Amore & Passione", "user123");
        assertEquals("Libri d'Amore & Passione", key.getName());
    }

    @Test
    @DisplayName("Should handle special characters in userid")
    void testSpecialCharactersInUserId() {
        Bookshelf_key key = new Bookshelf_key("Favorites", "user_123");
        assertEquals("user_123", key.getuserid());
    }

    @Test
    @DisplayName("Should be serializable")
    void testIsSerializable() {
        Bookshelf_key key = new Bookshelf_key("Favorites", "user123");
        assertTrue(key instanceof java.io.Serializable);
    }

    @Test
    @DisplayName("Should be case sensitive for name")
    void testCaseSensitiveName() {
        Bookshelf_key key1 = new Bookshelf_key("favorites", "user123");
        Bookshelf_key key2 = new Bookshelf_key("FAVORITES", "user123");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should be case sensitive for userid")
    void testCaseSensitiveUserId() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        Bookshelf_key key2 = new Bookshelf_key("Favorites", "USER123");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle very long strings")
    void testVeryLongStrings() {
        String longName = "A".repeat(500);
        String longUserId = "B".repeat(500);
        Bookshelf_key key = new Bookshelf_key(longName, longUserId);

        assertEquals(longName, key.getName());
        assertEquals(longUserId, key.getuserid());
    }

    @Test
    @DisplayName("Should maintain hashCode consistency")
    void testHashCodeConsistency() {
        Bookshelf_key key = new Bookshelf_key("Favorites", "user123");
        int hash1 = key.hashCode();
        int hash2 = key.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Should handle whitespace in fields")
    void testWhitespaceInFields() {
        Bookshelf_key key = new Bookshelf_key("  Favorites  ", "  user123  ");
        assertEquals("  Favorites  ", key.getName());
        assertEquals("  user123  ", key.getuserid());
    }

    @Test
    @DisplayName("Should allow same bookshelf name for different users")
    void testSameNameDifferentUsers() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user1");
        Bookshelf_key key2 = new Bookshelf_key("Favorites", "user2");
        assertFalse(key1.equals(key2));
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should allow different bookshelf names for same user")
    void testDifferentNamesSameUser() {
        Bookshelf_key key1 = new Bookshelf_key("Favorites", "user123");
        Bookshelf_key key2 = new Bookshelf_key("ReadLater", "user123");
        assertFalse(key1.equals(key2));
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }
}
