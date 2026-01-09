package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AggregatedRating class.
 * Tests rating aggregation, averaging, and counting functionality.
 */
@DisplayName("AggregatedRating Tests")
class AggregatedRatingTest {

    private AggregatedRating aggregatedRating;

    @BeforeEach
    void setUp() {
        aggregatedRating = new AggregatedRating();
    }

    @Test
    @DisplayName("Should create empty aggregated rating")
    void testEmptyConstructor() {
        AggregatedRating rating = new AggregatedRating();
        assertNotNull(rating);
    }

    @Test
    @DisplayName("Should add single rating")
    void testAddSingleRating() {
        aggregatedRating.addRating("Stile", 5);
        assertEquals(5.0, aggregatedRating.getAverage("Stile"));
        assertEquals(1, aggregatedRating.getCount("Stile"));
    }

    @Test
    @DisplayName("Should add multiple ratings for same criterion")
    void testAddMultipleRatingsForSameCriterion() {
        aggregatedRating.addRating("Stile", 5);
        aggregatedRating.addRating("Stile", 4);
        aggregatedRating.addRating("Stile", 3);

        assertEquals(4.0, aggregatedRating.getAverage("Stile"));
        assertEquals(3, aggregatedRating.getCount("Stile"));
    }

    @Test
    @DisplayName("Should calculate average correctly")
    void testCalculateAverage() {
        aggregatedRating.addRating("Contenuto", 5);
        aggregatedRating.addRating("Contenuto", 3);
        aggregatedRating.addRating("Contenuto", 4);

        assertEquals(4.0, aggregatedRating.getAverage("Contenuto"), 0.001);
    }

    @Test
    @DisplayName("Should handle multiple criteria")
    void testMultipleCriteria() {
        aggregatedRating.addRating("Stile", 5);
        aggregatedRating.addRating("Contenuto", 4);
        aggregatedRating.addRating("Gradevolezza", 3);
        aggregatedRating.addRating("Originalita", 2);
        aggregatedRating.addRating("Edizione", 1);

        assertEquals(5.0, aggregatedRating.getAverage("Stile"));
        assertEquals(4.0, aggregatedRating.getAverage("Contenuto"));
        assertEquals(3.0, aggregatedRating.getAverage("Gradevolezza"));
        assertEquals(2.0, aggregatedRating.getAverage("Originalita"));
        assertEquals(1.0, aggregatedRating.getAverage("Edizione"));
    }

    @Test
    @DisplayName("Should return zero for non-existent criterion")
    void testNonExistentCriterion() {
        assertEquals(0.0, aggregatedRating.getAverage("NonExistent"));
        assertEquals(0, aggregatedRating.getCount("NonExistent"));
    }

    @Test
    @DisplayName("Should handle zero ratings")
    void testZeroRatings() {
        aggregatedRating.addRating("Stile", 0);
        aggregatedRating.addRating("Stile", 0);

        assertEquals(0.0, aggregatedRating.getAverage("Stile"));
        assertEquals(2, aggregatedRating.getCount("Stile"));
    }

    @Test
    @DisplayName("Should handle negative ratings")
    void testNegativeRatings() {
        aggregatedRating.addRating("Stile", -1);
        aggregatedRating.addRating("Stile", -2);
        aggregatedRating.addRating("Stile", -3);

        assertEquals(-2.0, aggregatedRating.getAverage("Stile"));
    }

    @Test
    @DisplayName("Should handle mixed positive and negative ratings")
    void testMixedRatings() {
        aggregatedRating.addRating("Stile", 5);
        aggregatedRating.addRating("Stile", -5);

        assertEquals(0.0, aggregatedRating.getAverage("Stile"));
    }

    @Test
    @DisplayName("Should handle large number of ratings")
    void testLargeNumberOfRatings() {
        for (int i = 1; i <= 1000; i++) {
            aggregatedRating.addRating("Stile", 5);
        }

        assertEquals(5.0, aggregatedRating.getAverage("Stile"));
        assertEquals(1000, aggregatedRating.getCount("Stile"));
    }

    @Test
    @DisplayName("Should handle decimal averages")
    void testDecimalAverages() {
        aggregatedRating.addRating("Stile", 5);
        aggregatedRating.addRating("Stile", 4);

        assertEquals(4.5, aggregatedRating.getAverage("Stile"));
    }

    @Test
    @DisplayName("Should handle criterion with null")
    void testNullCriterion() {
        aggregatedRating.addRating(null, 5);
        assertEquals(5.0, aggregatedRating.getAverage(null));
    }

    @Test
    @DisplayName("Should handle empty string criterion")
    void testEmptyStringCriterion() {
        aggregatedRating.addRating("", 5);
        assertEquals(5.0, aggregatedRating.getAverage(""));
    }

    @Test
    @DisplayName("Should be case sensitive for criteria")
    void testCaseSensitiveCriteria() {
        aggregatedRating.addRating("Stile", 5);
        aggregatedRating.addRating("stile", 3);

        assertEquals(5.0, aggregatedRating.getAverage("Stile"));
        assertEquals(3.0, aggregatedRating.getAverage("stile"));
    }

    @Test
    @DisplayName("Should handle very high rating values")
    void testVeryHighRatingValues() {
        aggregatedRating.addRating("Stile", 1000000);
        aggregatedRating.addRating("Stile", 2000000);

        assertEquals(1500000.0, aggregatedRating.getAverage("Stile"));
    }

    @Test
    @DisplayName("Should accumulate ratings correctly")
    void testAccumulateRatings() {
        aggregatedRating.addRating("VotoFinale", 1);
        assertEquals(1, aggregatedRating.getCount("VotoFinale"));

        aggregatedRating.addRating("VotoFinale", 2);
        assertEquals(2, aggregatedRating.getCount("VotoFinale"));

        aggregatedRating.addRating("VotoFinale", 3);
        assertEquals(3, aggregatedRating.getCount("VotoFinale"));

        assertEquals(2.0, aggregatedRating.getAverage("VotoFinale"));
    }

    @Test
    @DisplayName("Should be serializable")
    void testIsSerializable() {
        assertTrue(aggregatedRating instanceof java.io.Serializable);
    }

    @Test
    @DisplayName("Should handle all standard criteria")
    void testAllStandardCriteria() {
        String[] criteria = {"Stile", "Contenuto", "Gradevolezza", "Originalita", "Edizione", "VotoFinale"};

        for (int i = 0; i < criteria.length; i++) {
            aggregatedRating.addRating(criteria[i], i + 1);
        }

        for (int i = 0; i < criteria.length; i++) {
            assertEquals(i + 1.0, aggregatedRating.getAverage(criteria[i]));
        }
    }

    @Test
    @DisplayName("Should maintain separate counts for different criteria")
    void testSeparateCountsForDifferentCriteria() {
        aggregatedRating.addRating("Stile", 5);
        aggregatedRating.addRating("Stile", 5);
        aggregatedRating.addRating("Contenuto", 3);

        assertEquals(2, aggregatedRating.getCount("Stile"));
        assertEquals(1, aggregatedRating.getCount("Contenuto"));
    }
}
