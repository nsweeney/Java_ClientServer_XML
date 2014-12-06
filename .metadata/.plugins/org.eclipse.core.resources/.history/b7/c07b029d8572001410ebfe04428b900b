package week12.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import week12.app.Account;
import week12.app.User;
import week12.data.AtmDataException;
import week12.data.DataAccess;
import week12.util.AtmInvalidUserException;
import week12.util.AtmSecurity;

public class JUnitAppTest
{
	static DataAccess da;
	User user;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		da = new DataAccess();
		da.connect();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setUserId(11);		
		user.setPin(5678);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testAtmSecurity()
	{
		AtmSecurity secure = new AtmSecurity();
		
		try
		{
			User user = secure.login(1, 1234);
			System.out.println(user.toString());
		}
		catch(AtmInvalidUserException ex)
		{	
			String msg = "AtmDataException: " + ex.getMessage() + "\n";
			msg += getExceptionTrace(ex);
			fail(msg);
		}
		
	}
	
	@Test
	public void testGetAccount()
	{
		User user = new User(1,1234, "Scott", "LaChance");
		try
		{
			List<Account> accounts = da.getUserAccounts(user);
			assert(accounts.size() > 0);
		}
		catch(AtmDataException ex)
		{		
			String msg = "AtmDataException: " + ex.getMessage() + "\n";
			msg += getExceptionTrace(ex);
			fail(msg);
		}
	}

	@Test
	public void testGetUser()
	{
		try
		{
			User user = da.getUser(1L);
			assert(user != null);
			System.out.println(user.toString());
		}
		catch(AtmDataException e)
		{
			StringBuffer msg = new StringBuffer();		
			java.io.StringWriter writer = new java.io.StringWriter();
			java.io.PrintWriter pWriter = new java.io.PrintWriter(writer);
			e.printStackTrace(pWriter);
			msg.append("AtmDataException: " + e.getMessage() + "\n");
			msg.append(writer.toString() + "\n");
			fail(msg.toString());
			
		}
		catch(AtmInvalidUserException e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testSetUser()
	{
		try
		{
			da.saveUser(user);
			User savedUser = da.getUser(user.getUserId());
			assert(user.equals(savedUser));
		}
		catch(AtmDataException ex)
		{			
			String msg = "AtmDataException: " + ex.getMessage() + "\n";
			msg += getExceptionTrace(ex);
			fail(msg);
		}
		catch(AtmInvalidUserException ex)
		{
			String msg = "AtmInvalidUserException: " + ex.getMessage() + "\n";
			msg += getExceptionTrace(ex);
			fail(msg);
		}
		finally
		{
			try
			{
				da.deleteUser(user);
			}
			catch(AtmDataException ex)
			{
				String msg = "AtmInvalidUserException - deleting: " + ex.getMessage() + "\n";
				msg += getExceptionTrace(ex);
				fail(msg);
			}
		}
	}

	@Test
	public void testUserToString()
	{
		String userInfo = user.toString();
		assert(userInfo.length() > 0);
		System.out.println(userInfo.toString());
	}

	@Test
	public void testUserClassEquals()
	{
		boolean equalTest = true;
		
		// simple user creation
		User user1 = new User();						
		
		User user2 = new User();						
		
		// expect to be same
		if( !user1.equals(user2))
		{
			equalTest = false;
		}
		
		user2.setFirstName("Jim");
		user2.setLastName("Bo");
		user2.setUserId(1);		
		user2.setPin(1234);

		// expect to be different
		if( user.equals(user2))
		{
			equalTest = false;
		}
		
		User user3 = new User(user2.getUserId(), user2.getPin(), user2.getFirstName(), user2.getLastName());		
		
		// expect to be same
		if( !user3.equals(user2))
		{
			equalTest = false;
		}

		if( !equalTest)
		{
			fail("Class instances not equal");
		}
	}
	
	private String getExceptionTrace(Exception ex)
	{
		StringBuffer msg = new StringBuffer();		
		java.io.StringWriter writer = new java.io.StringWriter();
		java.io.PrintWriter pWriter = new java.io.PrintWriter(writer);
		ex.printStackTrace(pWriter);
		
		return msg.toString();
	}
}
