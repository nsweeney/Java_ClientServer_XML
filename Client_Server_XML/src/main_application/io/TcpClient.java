package main_application.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import main_application.AtmException;

import org.jdom2.Document;

import main_application.core.AtmObject;
import main_application.util.LoginRequest;
import main_application.util.LoginResponse;
import main_application.xml.XmlUtility;

/**
 * This is the client for a Login application. It provides an example for
 * exchanging data between two end points The Socket class is the TCP
 * implementation. This implementation supports multiple client connections,
 * similar to how a full ATM system might need to be implemented to allow
 * multiple ATMs to communicate with the bank server.
 * 
 * @author nick
 * 
 */
public class TcpClient {
	/**
	 * Entry point for the class. This example puts a lot of code in the main.
	 * Suggest refactoring if you use this as a starting point and NOT having it
	 * in main.
	 * 
	 * @param args
	 *            command line arguments
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new TcpClient().run();
	}

	public void run() throws IOException {
		initializeClient();
		startSession();
	}

	public void sendMessage(ObjectInputStream in, ObjectOutputStream out,
			Document loginRequestXML) throws IOException
	{

		Document msgResponse = null;

		out.writeObject(loginRequestXML);

		try
		{
			log("Waiting for response from server...");
			msgResponse = (Document)in.readObject();
			AtmObject loginResonse = null;
			try
			{
				loginResonse = XmlUtility.xmlToObject(msgResponse);
				log("Response from server received...");
			}
			catch(AtmException e1)
			{
				logger.severe("Issue with xmlToObject method.  Exception: " + e1);				
			}

			AtmObject loginResponse = XmlUtility.xmlToObject(msgResponse);
			log("Login Valid: " + ((LoginResponse)loginResponse).getLoggedIn());
			log("Session ID: " + ((LoginResponse)loginResponse).getSessionId());
		}
		catch(ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(AtmException e)
		{
			logger.severe("Issue with xmlToObject method.  Exception: " + e);
		}
	}

	private void initializeClient() {
		try {
			log("initializeClient");
			m_server = new Socket(m_host, m_port);
			m_stdIn = new BufferedReader(new InputStreamReader(System.in));

			/* Obtain an output stream to the server... */
			m_writer = new ObjectOutputStream(m_server.getOutputStream());

			/* Obtain an an input stream */
			m_reader = new ObjectInputStream(m_server.getInputStream());

		} catch (IOException ex) {
			System.err.println(ex);
			System.exit(1);
		}
	}

	private Document generateLoginRequest() {

		boolean isValidLoginResponseInput = false;
		LoginRequest userLoginRequest = null;
		Document dom = null;
		Scanner input = new Scanner(System.in);

		log("generateLoginRequest");

		while (!isValidLoginResponseInput) {
			try {
				int user_pin;
				long user_accountID;

				System.out.println("Please enter your PIN: ");
				user_pin = input.nextInt();
				System.out.println("Please enter your Account ID: ");
				user_accountID = input.nextLong();
				isValidLoginResponseInput = true;
				// create a LoginRequest object from the users valid input
				userLoginRequest = new LoginRequest(user_pin, user_accountID);
				// turn the object into XML
				dom = XmlUtility.objectToXml(userLoginRequest);

			} catch (InputMismatchException ime) {
				log("Input invalid.  Please enter a whole number for PIN and Account ID.");
				input.nextLine();
			} catch (AtmException e) {
				logger.severe("Issue with objectToXml method.  Exception: "
						+ e);
			}
		}
		return dom;

	}

	private void startSession() throws IOException {
		Scanner input = new Scanner(System.in);
		boolean continueTesting = true;
		boolean continueTesting2;
		String userInput;

		log("enter startSession");

		// Loop - allowing user to test sending LoginRequests and receiving
		// LoginResponses from server
		while (continueTesting) {
			
			// Create a LoginRequest object from the users input and then turn that into an
			// XML object.
			usersLoginRequestXML = generateLoginRequest();
			
			// Send the usersLoginRequestXML object to the server for processing
			sendMessage(m_reader, m_writer, usersLoginRequestXML);

			continueTesting2 = true;

			while (continueTesting2) {
				System.out.println("Would like to try this again? y/n: ");
				userInput = input.next();

				if (!userInput.equals("y") && !userInput.equals("n")) {
					input.nextLine();
					continue;
				} else if (userInput.equals("y")) {
					continueTesting = true;
					continueTesting2 = false;
				} else {
					continueTesting = false;
					continueTesting2 = false;
				}
			}

		}
		log("exit startSession");
	}

	private void log(String msg) {
		System.out.println(msg);
	}

	// Private data
	private static int m_port = 1001; /* port to connect to */
	private static String m_host = "localhost"; /* host to connect to */

	private static BufferedReader m_stdIn;

	// private static String m_nick;
	private static Socket m_server = null;

	private static ObjectInputStream m_reader;
	private static ObjectOutputStream m_writer;

	private static Document usersLoginRequestXML;

	private static ObjectOutputStream outStream;
	
	/**
	 * Create TcpClient logger object to accommodate logging levels.
	 */
	private final static Logger logger = Logger.getLogger(TcpClient.class
			.getName());	
}
