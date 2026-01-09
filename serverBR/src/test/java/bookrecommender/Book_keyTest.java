package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Book_key class.
 * Tests equals, hashCode, and key generation.
 */
@DisplayName("Book_key Tests")
class Book_keyTest {

    @Test
    @DisplayName("Should create book key with all fields")
    void testConstructor() {
        Book_key key = new Book_key("1984", "George Orwell", "1949");

        assertEquals("1984", key.getTitle());
        assertEquals("George Orwell/1949", key.getAuthor_Year());
    }

    @Test
    @DisplayName("Should combine author and year correctly")
    void testAuthorYearCombination() {
        Book_key key = new Book_key("Test Book", "Test Author", "2023");
        assertEquals("Test Author/2023", key.getAuthor_Year());
    }

    @Test
    @DisplayName("Should handle equals for same object")
    void testEqualsSameObject() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        assertTrue(key1.equals(key1));
    }

    @Test
    @DisplayName("Should handle equals for identical keys")
    void testEqualsIdenticalKeys() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        Book_key key2 = new Book_key("1984", "George Orwell", "1949");
        assertTrue(key1.equals(key2));
        assertTrue(key2.equals(key1));
    }

    @Test
    @DisplayName("Should handle equals for different titles")
    void testEqualsDifferentTitles() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        Book_key key2 = new Book_key("Animal Farm", "George Orwell", "1949");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle equals for different authors")
    void testEqualsDifferentAuthors() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        Book_key key2 = new Book_key("1984", "Different Author", "1949");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle equals for different years")
    void testEqualsDifferentYears() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        Book_key key2 = new Book_key("1984", "George Orwell", "1950");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle equals with null")
    void testEqualsWithNull() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        assertFalse(key1.equals(null));
    }

    @Test
    @DisplayName("Should handle equals with different class")
    void testEqualsWithDifferentClass() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        assertFalse(key1.equals("Not a Book_key"));
    }

    @Test
    @DisplayName("Should generate same hashCode for identical keys")
    void testHashCodeIdenticalKeys() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        Book_key key2 = new Book_key("1984", "George Orwell", "1949");
        assertEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should generate different hashCode for different keys")
    void testHashCodeDifferentKeys() {
        Book_key key1 = new Book_key("1984", "George Orwell", "1949");
        Book_key key2 = new Book_key("Animal Farm", "George Orwell", "1945");
        assertNotEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    @DisplayName("Should handle null title in equals")
    void testNullTitleEquals() {
        // Note: Book_key doesn't validate null in constructor, but will cause NullPointerException in equals
        // This tests the actual behavior of the class
        try {
            Book_key key1 = new Book_key(null, "Author", "Year");
            Book_key key2 = new Book_key(null, "Author", "Year");
            // This will throw NullPointerException when trying to use equals
            assertThrows(NullPointerException.class, () -> key1.equals(key2));
        } catch (NullPointerException e) {
            // Constructor itself might throw NPE, which is also acceptable behavior
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Should create key with null author")
    void testNullAuthorCreation() {
        // The class allows null values in constructor
        // Testing actual behavior: constructor succeeds but Author_Year will be "null/Year"
        Book_key key = new Book_key("Title", null, "Year");
        assertNotNull(key);
        assertEquals("Title", key.getTitle());
        assertEquals("null/Year", key.getAuthor_Year());
    }

    @Test
    @DisplayName("Should create key with null year")
    void testNullYearCreation() {
        // The class allows null values in constructor
        // Testing actual behavior: constructor succeeds but Author_Year will be "Author/null"
        Book_key key = new Book_key("Title", "Author", null);
        assertNotNull(key);
        assertEquals("Title", key.getTitle());
        assertEquals("Author/null", key.getAuthor_Year());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testEmptyStrings() {
        Book_key key = new Book_key("", "", "");
        assertEquals("", key.getTitle());
        assertEquals("/", key.getAuthor_Year());
    }

    @Test
    @DisplayName("Should handle special characters")
    void testSpecialCharacters() {
        Book_key key = new Book_key("L'Étranger", "Albert Camus", "1942");
        assertEquals("L'Étranger", key.getTitle());
        assertEquals("Albert Camus/1942", key.getAuthor_Year());
    }

    @Test
    @DisplayName("Should be serializable")
    void testIsSerializable() {
        Book_key key = new Book_key("Title", "Author", "Year");
        assertTrue(key instanceof java.io.Serializable);
    }

    @Test
    @DisplayName("Should handle author with forward slash")
    void testAuthorWithForwardSlash() {
        Book_key key = new Book_key("Title", "Author/Coauthor", "2023");
        assertEquals("Author/Coauthor/2023", key.getAuthor_Year());
    }

    @Test
    @DisplayName("Should be case sensitive for title")
    void testCaseSensitiveTitle() {
        Book_key key1 = new Book_key("title", "Author", "Year");
        Book_key key2 = new Book_key("TITLE", "Author", "Year");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should be case sensitive for author")
    void testCaseSensitiveAuthor() {
        Book_key key1 = new Book_key("Title", "author", "Year");
        Book_key key2 = new Book_key("Title", "AUTHOR", "Year");
        assertFalse(key1.equals(key2));
    }

    @Test
    @DisplayName("Should handle very long strings")
    void testVeryLongStrings() {
        String longTitle = "A".repeat(500);
        String longAuthor = "B".repeat(500);
        String longYear = "C".repeat(500);
        Book_key key = new Book_key(longTitle, longAuthor, longYear);

        assertEquals(longTitle, key.getTitle());
        assertEquals(longAuthor + "/" + longYear, key.getAuthor_Year());
    }

    @Test
    @DisplayName("Should maintain hashCode consistency")
    void testHashCodeConsistency() {
        Book_key key = new Book_key("1984", "George Orwell", "1949");
        int hash1 = key.hashCode();
        int hash2 = key.hashCode();
        assertEquals(hash1, hash2);
    }
}
