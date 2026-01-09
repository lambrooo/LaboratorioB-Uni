package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RecommendedBook class.
 * Tests title and count getters/setters.
 */
@DisplayName("RecommendedBook Tests")
class RecommendedBookTest {

    private RecommendedBook recommendedBook;

    @BeforeEach
    void setUp() {
        recommendedBook = new RecommendedBook("1984", 5);
    }

    @Test
    @DisplayName("Should create recommended book with title and count")
    void testConstructor() {
        assertEquals("1984", recommendedBook.getTitle());
        assertEquals(5, recommendedBook.getCount());
    }

    @Test
    @DisplayName("Should set and get title")
    void testSetTitle() {
        recommendedBook.setTitle("Animal Farm");
        assertEquals("Animal Farm", recommendedBook.getTitle());
    }

    @Test
    @DisplayName("Should set and get count")
    void testSetCount() {
        recommendedBook.setCount(10);
        assertEquals(10, recommendedBook.getCount());
    }

    @Test
    @DisplayName("Should handle null title")
    void testNullTitle() {
        RecommendedBook book = new RecommendedBook(null, 3);
        assertNull(book.getTitle());
    }

    @Test
    @DisplayName("Should handle empty string title")
    void testEmptyStringTitle() {
        RecommendedBook book = new RecommendedBook("", 3);
        assertEquals("", book.getTitle());
    }

    @Test
    @DisplayName("Should handle zero count")
    void testZeroCount() {
        RecommendedBook book = new RecommendedBook("Book", 0);
        assertEquals(0, book.getCount());
    }

    @Test
    @DisplayName("Should handle negative count")
    void testNegativeCount() {
        RecommendedBook book = new RecommendedBook("Book", -5);
        assertEquals(-5, book.getCount());
    }

    @Test
    @DisplayName("Should handle very large count")
    void testVeryLargeCount() {
        RecommendedBook book = new RecommendedBook("Book", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, book.getCount());
    }

    @Test
    @DisplayName("Should handle very long title")
    void testVeryLongTitle() {
        String longTitle = "A".repeat(1000);
        RecommendedBook book = new RecommendedBook(longTitle, 5);
        assertEquals(longTitle, book.getTitle());
        assertEquals(1000, book.getTitle().length());
    }

    @Test
    @DisplayName("Should handle special characters in title")
    void testSpecialCharactersInTitle() {
        String title = "L'Étranger & Other Stories: A Collection";
        RecommendedBook book = new RecommendedBook(title, 3);
        assertEquals(title, book.getTitle());
    }

    @Test
    @DisplayName("Should update title multiple times")
    void testUpdateTitleMultipleTimes() {
        recommendedBook.setTitle("First");
        assertEquals("First", recommendedBook.getTitle());

        recommendedBook.setTitle("Second");
        assertEquals("Second", recommendedBook.getTitle());

        recommendedBook.setTitle("Third");
        assertEquals("Third", recommendedBook.getTitle());
    }

    @Test
    @DisplayName("Should update count multiple times")
    void testUpdateCountMultipleTimes() {
        recommendedBook.setCount(1);
        assertEquals(1, recommendedBook.getCount());

        recommendedBook.setCount(5);
        assertEquals(5, recommendedBook.getCount());

        recommendedBook.setCount(10);
        assertEquals(10, recommendedBook.getCount());
    }

    @Test
    @DisplayName("Should set title to null")
    void testSetTitleToNull() {
        recommendedBook.setTitle(null);
        assertNull(recommendedBook.getTitle());
    }

    @Test
    @DisplayName("Should increment count")
    void testIncrementCount() {
        int initialCount = recommendedBook.getCount();
        recommendedBook.setCount(initialCount + 1);
        assertEquals(initialCount + 1, recommendedBook.getCount());
    }

    @Test
    @DisplayName("Should decrement count")
    void testDecrementCount() {
        int initialCount = recommendedBook.getCount();
        recommendedBook.setCount(initialCount - 1);
        assertEquals(initialCount - 1, recommendedBook.getCount());
    }

    @Test
    @DisplayName("Should handle count overflow")
    void testCountOverflow() {
        recommendedBook.setCount(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, recommendedBook.getCount());
    }

    @Test
    @DisplayName("Should handle count underflow")
    void testCountUnderflow() {
        recommendedBook.setCount(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, recommendedBook.getCount());
    }
}
