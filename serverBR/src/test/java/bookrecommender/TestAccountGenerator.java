package bookrecommender;

import java.util.Random;
import java.util.UUID;

/**
 * Utility class for generating random test account credentials.
 * This class provides methods to create unique usernames, emails, passwords,
 * and other user information for testing purposes.
 *
 * @version 1.0.0
 * @author Test Suite
 */
public class TestAccountGenerator {

    private static final Random random = new Random();
    private static final String[] FIRST_NAMES = {"John", "Jane", "Michael", "Sarah", "David", "Emma", "Robert", "Lisa", "James", "Mary"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};
    private static final String[] CITIES = {"Milano", "Roma", "Torino", "Napoli", "Bologna", "Firenze", "Venezia", "Genova", "Palermo", "Bari"};

    /**
     * Generates a random username with a UUID suffix to ensure uniqueness.
     *
     * @return A unique username string
     */
    public static String generateRandomUsername() {
        String prefix = "testuser";
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return prefix + "_" + suffix;
    }

    /**
     * Generates a random email address with a UUID to ensure uniqueness.
     *
     * @return A unique email address string
     */
    public static String generateRandomEmail() {
        String prefix = "test";
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return prefix + "_" + suffix + "@example.com";
    }

    /**
     * Generates a random password with alphanumeric characters.
     *
     * @return A random password string of length 12
     */
    public static String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    /**
     * Generates a random first name from a predefined list.
     *
     * @return A random first name
     */
    public static String generateRandomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    /**
     * Generates a random last name from a predefined list.
     *
     * @return A random last name
     */
    public static String generateRandomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    /**
     * Generates a random Italian fiscal code.
     * Note: This is a simplified version for testing purposes and may not be a valid fiscal code.
     *
     * @return A random fiscal code string
     */
    public static String generateRandomFiscalCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        StringBuilder fiscalCode = new StringBuilder();

        // 6 letters
        for (int i = 0; i < 6; i++) {
            fiscalCode.append(chars.charAt(random.nextInt(chars.length())));
        }

        // 2 digits (year)
        for (int i = 0; i < 2; i++) {
            fiscalCode.append(digits.charAt(random.nextInt(digits.length())));
        }

        // 1 letter (month)
        fiscalCode.append(chars.charAt(random.nextInt(chars.length())));

        // 2 digits (day)
        for (int i = 0; i < 2; i++) {
            fiscalCode.append(digits.charAt(random.nextInt(digits.length())));
        }

        // 4 characters (location code + check)
        for (int i = 0; i < 4; i++) {
            if (i < 3) {
                fiscalCode.append(chars.charAt(random.nextInt(chars.length())));
            } else {
                fiscalCode.append(digits.charAt(random.nextInt(digits.length())));
            }
        }

        return fiscalCode.toString();
    }

    /**
     * Generates a random address in an Italian city.
     *
     * @return A random address string
     */
    public static String generateRandomAddress() {
        String city = CITIES[random.nextInt(CITIES.length)];
        int streetNumber = random.nextInt(200) + 1;
        return "Via Roma " + streetNumber + ", " + city;
    }

    /**
     * Creates a complete test user with all random credentials.
     *
     * @return A User object with randomly generated data
     */
    public static User generateRandomUser() {
        return new User(
            generateRandomFirstName(),
            generateRandomLastName(),
            generateRandomFiscalCode(),
            generateRandomAddress(),
            generateRandomEmail(),
            generateRandomUsername(),
            generateRandomPassword()
        );
    }

    /**
     * Creates a test user with specified username and password, but random other fields.
     *
     * @param username The specific username to use
     * @param password The specific password to use
     * @return A User object with specified username/password and random other data
     */
    public static User generateUserWithCredentials(String username, String password) {
        return new User(
            generateRandomFirstName(),
            generateRandomLastName(),
            generateRandomFiscalCode(),
            generateRandomAddress(),
            generateRandomEmail(),
            username,
            password
        );
    }
}
