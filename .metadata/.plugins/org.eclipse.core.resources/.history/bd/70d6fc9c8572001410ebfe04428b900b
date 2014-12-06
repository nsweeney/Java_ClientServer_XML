package week12.util;

import week12.app.User;
import week12.core.AtmObject;

/**
 * Encapsulates the login request information
 * @author scottl
 *
 */
public class LoginResponse extends AtmObject
{
	/**
	 * Constructor
	 * @param loggedIn true if logged in successfully, false otherwise
	 * @param sessionId - SessionId token)
	 */
	public LoginResponse(boolean loggedIn, long sessionId, User user)
	{
		m_loggedIn = loggedIn;
		m_sessionId = sessionId;
		m_user = user;
	}

	/**
	 * @return true if logged in, otherwise false
	 */
	public Boolean getLoggedIn()
	{
		return m_loggedIn;
	}
	
	/**
	 * @return the internal session ID
	 */
	public long getSessionId()
	{
		return m_sessionId;
	}
	
	public User getUser()
	{
		return m_user;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if( obj instanceof LoginResponse)
		{
			LoginResponse rhs = (LoginResponse)obj;
			
			if( this.getLoggedIn() == rhs.getLoggedIn() &&
				this.getSessionId() == rhs.getSessionId())
			{
				result = true;
			}
		}
		
		return result;
	}

	private Boolean m_loggedIn;
	private long m_sessionId; 
	private User m_user;
}
