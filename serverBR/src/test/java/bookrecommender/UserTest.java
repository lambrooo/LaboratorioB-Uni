package bookrecommender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the User class.
 * Tests all constructors and getter methods.
 */
@DisplayName("User Tests")
class UserTest {

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(
            "Mario",
            "Rossi",
            "RSSMRA80A01H501U",
            "Via Roma 123, Milano",
            "mario.rossi@example.com",
            "mrossi",
            "password123"
        );
    }

    @Test
    @DisplayName("Should create user with all fields")
    void testUserConstructorWithAllFields() {
        assertEquals("Mario", testUser.getName());
        assertEquals("Rossi", testUser.getSurname());
        assertEquals("RSSMRA80A01H501U", testUser.getFiscalCode());
        assertEquals("Via Roma 123, Milano", testUser.getAddress());
        assertEquals("mario.rossi@example.com", testUser.getEmail());
        assertEquals("mrossi", testUser.getUserid());
        assertEquals("password123", testUser.getPassword());
    }

    @Test
    @DisplayName("Should create user with empty constructor")
    void testUserEmptyConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    @DisplayName("Should handle null values in constructor")
    void testUserConstructorWithNullValues() {
        User user = new User(null, null, null, null, null, null, null);

        assertNull(user.getName());
        assertNull(user.getSurname());
        assertNull(user.getFiscalCode());
        assertNull(user.getAddress());
        assertNull(user.getEmail());
        assertNull(user.getUserid());
        assertNull(user.getPassword());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testUserWithEmptyStrings() {
        User user = new User("", "", "", "", "", "", "");

        assertEquals("", user.getName());
        assertEquals("", user.getSurname());
        assertEquals("", user.getFiscalCode());
        assertEquals("", user.getAddress());
        assertEquals("", user.getEmail());
        assertEquals("", user.getUserid());
        assertEquals("", user.getPassword());
    }

    @Test
    @DisplayName("Should handle special characters in name")
    void testUserWithSpecialCharacters() {
        User user = new User(
            "José", "O'Brien", "ABC123", "123 Main St.",
            "jose.obrien@example.com", "jose123", "p@ssw0rd!"
        );

        assertEquals("José", user.getName());
        assertEquals("O'Brien", user.getSurname());
        assertEquals("p@ssw0rd!", user.getPassword());
    }

    @Test
    @DisplayName("Should handle long email addresses")
    void testUserWithLongEmail() {
        String longEmail = "very.long.email.address.with.many.dots@subdomain.example.com";
        User user = new User("Test", "User", "TEST123", "Address", longEmail, "testuser", "pass");

        assertEquals(longEmail, user.getEmail());
    }

    @Test
    @DisplayName("Should handle multiple word addresses")
    void testUserWithMultiWordAddress() {
        String address = "Via Giuseppe Garibaldi 42, Appartamento 5, Milano, 20100, Italia";
        User user = new User("Test", "User", "TEST123", address, "test@example.com", "testuser", "pass");

        assertEquals(address, user.getAddress());
    }

    @Test
    @DisplayName("Should store password as plain text")
    void testPasswordStorage() {
        assertEquals("password123", testUser.getPassword());
    }

    @Test
    @DisplayName("Should handle userid with special characters")
    void testUseridWithSpecialCharacters() {
        User user = new User("Test", "User", "TEST123", "Address", "test@example.com", "user_123", "pass");
        assertEquals("user_123", user.getUserid());
    }

    @Test
    @DisplayName("Should handle very long fiscal code")
    void testUserWithLongFiscalCode() {
        String longFiscalCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        User user = new User("Test", "User", longFiscalCode, "Address", "test@example.com", "testuser", "pass");

        assertEquals(longFiscalCode, user.getFiscalCode());
    }
}
