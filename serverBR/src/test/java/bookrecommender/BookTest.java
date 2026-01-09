package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Book class.
 * Tests all constructors, getters, and the toString method.
 */
@DisplayName("Book Tests")
class BookTest {

    @Test
    @DisplayName("Should create book with all fields")
    void testBookConstructorWithAllFields() {
        Book book = new Book("1984", "George Orwell", "1949", "Dystopian");

        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertEquals("1949", book.getYear());
        assertEquals("Dystopian", book.getGenre());
    }

    @Test
    @DisplayName("Should create book with empty constructor")
    void testBookEmptyConstructor() {
        Book book = new Book();
        assertNotNull(book);
    }

    @Test
    @DisplayName("Should return title in toString")
    void testToString() {
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Fiction");
        assertEquals("The Great Gatsby", book.toString());
    }

    @Test
    @DisplayName("Should handle null values in constructor")
    void testBookConstructorWithNullValues() {
        Book book = new Book(null, null, null, null);

        assertNull(book.getTitle());
        assertNull(book.getAuthor());
        assertNull(book.getYear());
        assertNull(book.getGenre());
    }

    @Test
    @DisplayName("Should handle empty strings in constructor")
    void testBookConstructorWithEmptyStrings() {
        Book book = new Book("", "", "", "");

        assertEquals("", book.getTitle());
        assertEquals("", book.getAuthor());
        assertEquals("", book.getYear());
        assertEquals("", book.getGenre());
    }

    @Test
    @DisplayName("Should handle special characters in fields")
    void testBookWithSpecialCharacters() {
        Book book = new Book("L'Étranger", "Albert Camus", "1942", "Fiction");

        assertEquals("L'Étranger", book.getTitle());
        assertEquals("Albert Camus", book.getAuthor());
    }

    @Test
    @DisplayName("Should handle very long strings in fields")
    void testBookWithLongStrings() {
        String longTitle = "A".repeat(500);
        String longAuthor = "B".repeat(500);
        Book book = new Book(longTitle, longAuthor, "2023", "Genre");

        assertEquals(longTitle, book.getTitle());
        assertEquals(longAuthor, book.getAuthor());
    }

    @Test
    @DisplayName("Should handle numeric year as string")
    void testBookWithNumericYear() {
        Book book = new Book("Test Book", "Test Author", "2023", "Test Genre");
        assertEquals("2023", book.getYear());
    }

    @Test
    @DisplayName("Should handle non-numeric year")
    void testBookWithNonNumericYear() {
        Book book = new Book("Test Book", "Test Author", "Unknown", "Test Genre");
        assertEquals("Unknown", book.getYear());
    }

    @Test
    @DisplayName("Should be serializable")
    void testBookIsSerializable() {
        Book book = new Book("Test", "Author", "2023", "Genre");
        assertTrue(book instanceof java.io.Serializable);
    }
}
