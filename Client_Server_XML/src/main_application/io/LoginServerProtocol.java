package main_application.io;

import main_application.AtmException;
import main_application.app.SystemIdGenerator;
import main_application.data.AtmDataException;
import main_application.data.DataAccess;
import main_application.util.LoginRequest;
import main_application.util.LoginResponse;
import main_application.xml.XmlUtility;

import org.jdom2.Document;

public class LoginServerProtocol
{

	private ClientConn m_conn;

	public LoginServerProtocol(ClientConn c)
	{
		m_conn = c;
	}

	/**
	 * Process a message coming from the client We take the message based on the
	 * user input in which is the pin, and account id Thereafter before sending
	 * a response we check the database to see if the user pin and, the users
	 * account ID matches what was entered. If the user entered the correct
	 * information than the message gets processed.
	 * 
	 * @param msg
	 * @return the XML to the client.
	 */
	public Document process(LoginRequest msg)
	{
		boolean isValidLogin = false;
		LoginResponse loginResponse = null;
		Document responseDom = null;
		LoginRequest testRequest = null;
		// needed to set session ID in response
		SystemIdGenerator systemIdGenerator = SystemIdGenerator.getInstance();

		log("Server: processing");

		// take in String an convert to XML
		if(msg != null)
		{
			testRequest = msg;
		}
		else
		{
			msg = new LoginRequest(testRequest.getPin(),
					testRequest.getAccountId());
			testRequest = msg;
		}

		// Check if this LoginRequest is valid (query MySQL to see
		// if PIN and Account ID are valid or not)
		// Try Block for Database connection
		try
		{
			DataAccess da = new DataAccess();
			da.connect();
			// If getAutentication greater than 0 return true (because the
			// method returns a number representing the users id).
			isValidLogin = (da.getAutentication(testRequest.getPin(),
					testRequest.getAccountId()) > 0) ? true : false;

		}
		catch(AtmDataException ex)
		{
			log(ex.getMessage());
			ex.getStackTrace();
		}
		// after validity check create a LoginResponse object representing the
		// response (login info provided by user will be valid or not valid)s
		if(isValidLogin)
		{
			// Create response saying the login is valid and also the session
			// id.
			loginResponse = new LoginResponse(true,
					systemIdGenerator.getNextId());
		}
		else
		{
			// Create response saying the login is NOT valid and also the
			// session id.
			loginResponse = new LoginResponse(false,
					systemIdGenerator.getNextId());
		}
		// convert LoginResponse object to XML
		try
		{
			responseDom = XmlUtility.objectToXml(loginResponse);
		}
		catch(AtmException atmError)
		{
			atmError.printStackTrace();
		}
		// send XML to client
		return responseDom;
	}

	private void log(String msg)
	{
		System.out.println(msg);
	}
}
