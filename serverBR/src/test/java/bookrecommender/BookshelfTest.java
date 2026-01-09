package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Bookshelf class.
 * Tests all constructors, getters, setters, and book management.
 */
@DisplayName("Bookshelf Tests")
class BookshelfTest {

    private Bookshelf testBookshelf;
    private ArrayList<String> bookTitles;

    @BeforeEach
    void setUp() {
        bookTitles = new ArrayList<>();
        bookTitles.add("1984");
        bookTitles.add("Animal Farm");
        bookTitles.add("Brave New World");

        testBookshelf = new Bookshelf("My Favorites", "user123", bookTitles);
    }

    @Test
    @DisplayName("Should create bookshelf with all fields")
    void testBookshelfConstructorWithAllFields() {
        assertEquals("My Favorites", testBookshelf.getName());
        assertEquals("user123", testBookshelf.getUserid());
        assertEquals(3, testBookshelf.getBooks().size());
        assertTrue(testBookshelf.getBooks().contains("1984"));
        assertTrue(testBookshelf.getBooks().contains("Animal Farm"));
        assertTrue(testBookshelf.getBooks().contains("Brave New World"));
    }

    @Test
    @DisplayName("Should create bookshelf with empty constructor")
    void testBookshelfEmptyConstructor() {
        Bookshelf bookshelf = new Bookshelf();
        assertNotNull(bookshelf);
    }

    @Test
    @DisplayName("Should set bookshelf name")
    void testSetName() {
        testBookshelf.setName("New Name");
        assertEquals("New Name", testBookshelf.getName());
    }

    @Test
    @DisplayName("Should handle empty bookshelf")
    void testEmptyBookshelf() {
        Bookshelf bookshelf = new Bookshelf("Empty Shelf", "user123", new ArrayList<>());
        assertEquals("Empty Shelf", bookshelf.getName());
        assertEquals(0, bookshelf.getBooks().size());
        assertTrue(bookshelf.getBooks().isEmpty());
    }

    @Test
    @DisplayName("Should add books to bookshelf")
    void testAddBooksToBookshelf() {
        ArrayList<String> books = testBookshelf.getBooks();
        books.add("The Great Gatsby");

        assertEquals(4, testBookshelf.getBooks().size());
        assertTrue(testBookshelf.getBooks().contains("The Great Gatsby"));
    }

    @Test
    @DisplayName("Should remove books from bookshelf")
    void testRemoveBooksFromBookshelf() {
        ArrayList<String> books = testBookshelf.getBooks();
        books.remove("1984");

        assertEquals(2, testBookshelf.getBooks().size());
        assertFalse(testBookshelf.getBooks().contains("1984"));
    }

    @Test
    @DisplayName("Should handle null name")
    void testNullName() {
        Bookshelf bookshelf = new Bookshelf(null, "user123", new ArrayList<>());
        assertNull(bookshelf.getName());
    }

    @Test
    @DisplayName("Should handle null userid")
    void testNullUserid() {
        Bookshelf bookshelf = new Bookshelf("Shelf", null, new ArrayList<>());
        assertNull(bookshelf.getUserid());
    }

    @Test
    @DisplayName("Should handle null books list")
    void testNullBooksList() {
        Bookshelf bookshelf = new Bookshelf("Shelf", "user123", null);
        assertNull(bookshelf.getBooks());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testEmptyStrings() {
        Bookshelf bookshelf = new Bookshelf("", "", new ArrayList<>());
        assertEquals("", bookshelf.getName());
        assertEquals("", bookshelf.getUserid());
    }

    @Test
    @DisplayName("Should handle special characters in name")
    void testSpecialCharactersInName() {
        Bookshelf bookshelf = new Bookshelf("Libri d'Amore & Passione", "user123", new ArrayList<>());
        assertEquals("Libri d'Amore & Passione", bookshelf.getName());
    }

    @Test
    @DisplayName("Should handle very long bookshelf name")
    void testVeryLongName() {
        String longName = "A".repeat(500);
        Bookshelf bookshelf = new Bookshelf(longName, "user123", new ArrayList<>());
        assertEquals(longName, bookshelf.getName());
        assertEquals(500, bookshelf.getName().length());
    }

    @Test
    @DisplayName("Should handle duplicate book titles")
    void testDuplicateBookTitles() {
        ArrayList<String> books = new ArrayList<>();
        books.add("1984");
        books.add("1984");
        books.add("1984");

        Bookshelf bookshelf = new Bookshelf("Duplicates", "user123", books);
        assertEquals(3, bookshelf.getBooks().size());
    }

    @Test
    @DisplayName("Should handle very large number of books")
    void testLargeNumberOfBooks() {
        ArrayList<String> manyBooks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            manyBooks.add("Book " + i);
        }

        Bookshelf bookshelf = new Bookshelf("Large Collection", "user123", manyBooks);
        assertEquals(1000, bookshelf.getBooks().size());
    }

    @Test
    @DisplayName("Should maintain book order")
    void testBookOrder() {
        ArrayList<String> orderedBooks = new ArrayList<>();
        orderedBooks.add("First");
        orderedBooks.add("Second");
        orderedBooks.add("Third");

        Bookshelf bookshelf = new Bookshelf("Ordered", "user123", orderedBooks);
        assertEquals("First", bookshelf.getBooks().get(0));
        assertEquals("Second", bookshelf.getBooks().get(1));
        assertEquals("Third", bookshelf.getBooks().get(2));
    }

    @Test
    @DisplayName("Should be serializable")
    void testBookshelfIsSerializable() {
        assertTrue(testBookshelf instanceof java.io.Serializable);
    }

    @Test
    @DisplayName("Should clear all books")
    void testClearBooks() {
        testBookshelf.getBooks().clear();
        assertEquals(0, testBookshelf.getBooks().size());
        assertTrue(testBookshelf.getBooks().isEmpty());
    }

    @Test
    @DisplayName("Should get books list reference")
    void testGetBooksReference() {
        ArrayList<String> books1 = testBookshelf.getBooks();
        ArrayList<String> books2 = testBookshelf.getBooks();
        assertSame(books1, books2);
    }
}
