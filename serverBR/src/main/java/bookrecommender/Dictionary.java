package bookrecommender;

/**
 * This enum manages and contains all the messages used in the application.
 * It provides a constructor to initialize messages and a method to retrieve them.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public enum Dictionary {

	CONFIG_FONT( "Cambria" ),
	
	
	SYS_GENERIC_ERROR("*GENERIC ERROR* "),
	SYS_GENERIC_ERROR_INFO("Oups, something went wrong... Please restart the application"),
	SYS_GENERIC_FILE_WARNING("*WARN* file "),
	SYS_GENERIC_FILE_WARNING_INFO(" not found"),
	
	SYS_LOADING_REPO_ERROR_INFO("Oups, something went wrong during the repository population or the input was not correct. Please make sure that the input is correct and restart the application..."),
	SYS_INPUT_FILES_NOT_FOUND_ERROR_INFO("Oups, something went wrong during files loading... Please restart the application to continue"),
	
	PLACE_HOLDER( "" );
	
	private String msg;

	    /**
     * Constructs a Dictionary enum constant with the specified message.
     * @param msg The message associated with the enum constant.
     */
    private Dictionary( String msg ){
		
		this.msg = msg;
	}
	
	    /**
     * Returns the message associated with this enum constant.
     * @return The message string.
     */
    public String getMsg(){
		
		return this.msg;
	}
}