package week12.app;
import week12.core.AtmObject;

/**
 * A financial institution user.
 * @author scottl
 *
 */
public class User extends AtmObject
{
	/**
	 * Default constructor
	 */
	public User()
	{
		this(-1, -1, "Default", "Default");
	}
	
	/**
	 * Parameterized constructors
	 * @param id userid
	 * @param first First name
	 * @param last last name
	 */
	public User(long id, int pin, String first, String last)
	{	
		m_userId = id;
		m_pin = pin;
		m_firstName = first;
		m_lastName = last;
	}
	
	/**
	 * @return the user id (equivalent to account ID
	 */
	public long getUserId()
	{
		return m_userId;
	}
	
	/**
	 * @return the user pin
	 */
	public int getPin()
	{
		return m_pin;
	}
	
	/**
	 * @param m_userId the user id to set
	 */
	public void setUserId(long m_userId)
	{
		this.m_userId = m_userId;
	}
	
	/**
	 * 
	 * @param pin sets the User pin
	 */
	public void setPin(int pin)
	{
		this.m_pin = pin;
	}
		
	/**
	 * @return the m_firstName
	 */
	public String getFirstName()
	{
		return m_firstName;
	}

	/**
	 * @param m_firstName the m_firstName to set
	 */
	public void setFirstName(String m_firstName)
	{
		this.m_firstName = m_firstName;
	}
	
	/**
	 * @return the m_lastName
	 */
	public String getLastName()
	{
		return m_lastName;
	}
	
	/**
	 * @param m_lastName the m_lastName to set
	 */
	public void setLastName(String m_lastName)
	{
		this.m_lastName = m_lastName;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean result = true;
		
		if( obj == null )
		{
			result = false;
		}
		else if( getClass() != obj.getClass())
		{
			result = false;
		}
		else
		{
			// valid User object, check the contents
			final User otherUser = (User) obj;
			if( !this.m_firstName.equals(otherUser.m_firstName) ||
				!this.m_lastName.equals(otherUser.m_lastName) ||
				!(this.m_userId == otherUser.m_userId) ||
				!(this.m_pin == otherUser.m_pin))
			{
				result = false;
			}
		}
				
		return result;
	}
	
	@Override
	public String toString()
	{
		String fmt = String.format("ID: %d, %s %s", this.m_userId, this.m_firstName, this.m_lastName);
		return fmt;
	}
	
	private String m_firstName;
	private String m_lastName;
	private int m_pin;
	private long m_userId;
}
