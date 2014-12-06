package week12.util;

import week12.core.AtmObject;

/**
 * Encapsulates the login request information
 * @author scottl
 *
 */
public class LoginRequest extends AtmObject
{
	/**
	 * Constructor
	 * @param userId User ID
	 * @param pin - User provided pin
	 */
	public LoginRequest(long userId, int pin )
	{
		m_pin = pin;
		m_userId = userId;
	}

	/**
	 * @return the m_pin
	 */
	public int getPin()
	{
		return m_pin;
	}
	
	/**
	 * @param m_pin the m_pin to set
	 */
	public void setPin(int m_pin)
	{
		this.m_pin = m_pin;
	}

	/**
	 * @return the m_accountId
	 */
	public long getUserId()
	{
		return m_userId;
	}

	/**
	 * @param m_accountId the m_accountId to set
	 */
	public void setAccountId(long m_accountId)
	{
		this.m_userId = m_accountId;
	}

	@Override
	public boolean equals(Object obj)
	{
		boolean result = false;
		if( obj instanceof LoginRequest)
		{
			LoginRequest rhs = (LoginRequest)obj;
			if( this.getPin() == rhs.getPin() &&
				this.getUserId() == rhs.getUserId())
			{
				result = true;
			}
		}
		
		return result;
	}
	
	private int m_pin;
	private long m_userId; 
}
