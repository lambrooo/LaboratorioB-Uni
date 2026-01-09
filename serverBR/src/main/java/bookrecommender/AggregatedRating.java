package bookrecommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

/**
 * This class manages aggregated ratings for books.
 * It allows adding ratings for different criteria and calculates average ratings and counts.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see Book
 */
public class AggregatedRating implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Map<String, List<Integer>> ratings;

    /**
     * Constructs a new AggregatedRating object.
     * Initializes the ratings map.
     */
    public AggregatedRating() {
        this.ratings = new HashMap<>();
    }

    /**
     * Adds a rating for a specific criterion.
     * 
     * @param criterion The criterion for which the rating is being added
     * @param value The rating value to be added
     */
    public void addRating(String criterion, int value) {
        ratings.computeIfAbsent(criterion, k -> new ArrayList<>()).add(value);
    }

    /**
     * Calculates the average rating for a specific criterion.
     * 
     * @param criterion The criterion for which to calculate the average rating
     * @return The average rating for the specified criterion, or 0 if no ratings exist
     */
    public double getAverage(String criterion) {
        List<Integer> values = ratings.get(criterion);
        if (values == null || values.isEmpty()) {
            return 0;
        }
        return values.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    /**
     * Gets the count of ratings for a specific criterion.
     * 
     * @param criterion The criterion for which to get the rating count
     * @return The number of ratings for the specified criterion
     */
    public int getCount(String criterion) {
        List<Integer> values = ratings.get(criterion);
        return values == null ? 0 : values.size();
    }
}