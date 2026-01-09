package bookrecommender;

/**
 * This class represents a user with their personal information and login credentials.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class User {

	private String Name, Surname, FiscalCode, Address, Email, Userid, Password;
	
	    /**
     * Constructs an empty User object.
     */
    public User() {}

	    /**
     * Constructs a new User with the specified personal information and login credentials.
     * 
     * @param Name The user's first name.
     * @param Surname The user's last name.
     * @param FiscalCode The user's fiscal code.
     * @param Address The user's address.
     * @param Email The user's email address.
     * @param Userid The user's chosen ID.
     * @param Password The user's chosen password.
     */
    public User(String Name, String Surname, String FiscalCode, String Address, String Email, String Userid, String Password) {
		
		this.Name = Name;
		this.Surname = Surname;
		this.FiscalCode = FiscalCode;
		this.Address = Address;
		this.Email = Email;
		this.Userid = Userid;
		this.Password = Password;
	}

	/**
     * Returns the user's first name.
     * @return The user's first name.
     */
    public String getName() {
		return Name;
	}

	/**
     * Returns the user's last name.
     * @return The user's last name.
     */
    public String getSurname() {
		return Surname;
	}

	/**
     * Returns the user's fiscal code.
     * @return The user's fiscal code.
     */
    public String getFiscalCode() {
		return FiscalCode;
	}

	/**
     * Returns the user's address.
     * @return The user's address.
     */
    public String getAddress() {
		return Address;
	}

	/**
     * Returns the user's email address.
     * @return The user's email address.
     */
    public String getEmail() {
		return Email;
	}

	/**
     * Returns the user's ID.
     * @return The user's ID.
     */
    public String getUserid() {
		return Userid;
	}

	/**
     * Returns the user's password.
     * @return The user's password.
     */
    public String getPassword() {
		return Password;
	}
}
