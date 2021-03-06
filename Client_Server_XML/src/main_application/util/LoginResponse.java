package main_application.util;

import main_application.core.AtmObject;

/**
 * Encapsulates the login request information
 *
 */
public class LoginResponse extends AtmObject
{
	/**
	 * Constructor
	 * 
	 * @param loggedIn
	 *            true if logged in successfully, false otherwise
	 * @param sessionId
	 *            - SessionId token)
	 */
	public LoginResponse(boolean loggedIn, long sessionId)
	{
		m_loggedIn = loggedIn;
		m_sessionId = sessionId;
	}

	/**
	 * @return the m_pin
	 */
	public Boolean getLoggedIn()
	{
		return m_loggedIn;
	}

	/**
	 * @return the m_accountId
	 */
	public long getSessionId()
	{
		return m_sessionId;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if(obj instanceof LoginResponse)
		{
			LoginResponse rhs = (LoginResponse)obj;

			if(this.getLoggedIn() == rhs.getLoggedIn()
					&& this.getSessionId() == rhs.getSessionId())
			{
				result = true;
			}
		}

		return result;
	}

	private Boolean m_loggedIn;
	private long m_sessionId;
}
