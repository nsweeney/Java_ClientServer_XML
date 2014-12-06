package week12.xml;

import week12.data.AtmDataException;
import week12.data.DataAccess;
import week12.AtmException;
import week12.app.User;
import week12.core.AtmObject;
import week12.util.AtmInvalidUserException;
import week12.util.LoginRequest;
import week12.util.LoginResponse;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * Provides a set of utility methods to manage XML
 * 
 * @author scottl
 * 
 */
public class XmlUtility
{
	/**
	 * Serializes an AtmObject reference to an XML document
	 * 
	 * @param obj
	 *            AtmObject to serialize
	 * @return XML Document
	 */
	public static Document objectToXml(AtmObject obj) throws AtmException
	{
		Document dom = null;

		if(obj instanceof LoginResponse)
		{
			dom = generateLoginRequestXml((LoginRequest)obj);
		}
		else if(obj instanceof LoginResponse)
		{
			dom = generateLoginResponseXml((LoginResponse)obj);
		}
		else
		{
			throw new AtmException("Unknown AtmObject");
		}

		return dom;
	}

	/**
	 * Takes an XML document and converts it to the appropriate ATM domain
	 * object.
	 * 
	 * @param dom
	 *            Document reference
	 * @return Appropriate ATM domain object
	 */
	public static AtmObject xmlToObject(Document dom) throws AtmException
	{
		AtmObject atmObject = null;

		// get the root and determine the type of object
		Element root = dom.getRootElement();
		String elementName = root.getName();
		try
		{
			// extend this section to build the appropriate object
			if(elementName.equals("login-request"))
			{
				// generate the LoginRequest object
				atmObject = getLoginObject(root);
			}
			// extend this section to build the appropriate object
			else if(elementName.equals("login-response"))
			{
				// generate the LoginResponse object
				atmObject = getLoginResponseObject(root);
			}
		}
		catch(NumberFormatException ex)
		{
			throw new AtmException(ex);
		}

		return atmObject;
	}

	private static AtmObject getLoginResponseObject(Element root)
			throws NumberFormatException, AtmDataException,
			AtmInvalidUserException
	{
		String loggedInString = root.getAttributeValue("logged-in");
		String userIdString = root.getAttributeValue("user-id");
		boolean loggedIn = Boolean.parseBoolean(loggedInString);
		int userId = Integer.parseInt(userIdString);
		DataAccess da = new DataAccess();
		da.connect();
		User user = da.getUser(userId);
		return new LoginResponse(loggedIn, userId, user);
	}

	private static AtmObject getLoginObject(Element root)
	throws NumberFormatException, AtmDataException,
	AtmInvalidUserException
	{
		String pinString = root.getAttributeValue("pin");
		String userIdString = root.getAttributeValue("user-id");
		int pin = Integer.parseInt(pinString);
		int usertId = Integer.parseInt(userIdString);
		return new LoginRequest(usertId, pin);
	}

	private static Document generateLoginRequestXml(LoginRequest request)
	{
		Document dom = null;

		// build the root element
		Element root = new Element("login-request");
		Attribute version = new Attribute("version", "1.0");
		root.setAttribute(version);
		String pinFmt = String.format("%d", request.getPin());
		Attribute pin = new Attribute("pin", pinFmt);
		root.setAttribute(pin);
		String userFmt = String.format("%d", request.getUserId());
		Attribute account = new Attribute("user-id", userFmt);
		root.setAttribute(account);

		dom = new Document(root);

		return dom;
	}

	private static Document generateLoginResponseXml(LoginResponse response)
	{
		Document dom = null;

		// build the root element
		Element root = new Element("login-response");
		Attribute version = new Attribute("version", "1.0");
		root.setAttribute(version);
		String loggedInFmt = response.getLoggedIn().toString();
		Attribute loggedIn = new Attribute("logged-in", loggedInFmt);
		root.setAttribute(loggedIn);
		String sessionFmt = String.format("%d", response.getSessionId());
		Attribute account = new Attribute("account-id", sessionFmt);
		root.setAttribute(account);

		dom = new Document(root);

		return dom;
	}
}
