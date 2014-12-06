package main_application.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import main_application.app.Account;
import main_application.app.User;

import org.junit.Before;
import org.junit.Test;

public class TestDataAccess
{

	private DataAccess db;

	/**
	 * Get test data ready for testing.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws AtmDataException
	{
		db = new DataAccess();
		db.connect();
	}

	@Test
	public void testThatWeHaveADataLayer()
	{
		assertTrue("Data layer doesn't exist!", this.db instanceof DataAccess);
	}

	@Test
	public void testThatWeCanSaveAnAccount()
	{
		List<User> users = new ArrayList<User>();
		users.add(new User(11, "Dana", "McGee"));
		users.add(new User(22, "Beth", "Bass"));
		users.add(new User(33, "Lucas", "Benson"));

		List<Account> accounts = new ArrayList<Account>();
		accounts.add(new Account(111, users.get(0), "savings", 100.99));
		accounts.add(new Account(222, users.get(1), "savings", 200.99));
		accounts.add(new Account(333, users.get(2), "checking", 300.99));

		for(Account userAccount : accounts)
		{
			try
			{
				this.db.saveAccount(userAccount);
			}
			catch(AtmDataException error)
			{
				System.out.println(error.getStackTrace());
			}
		}
		// needs to be in try catch because of the call to getAllAccounts()
		try
		{
			assertEquals(3, this.db.getAllAccounts().size());
		}
		catch(AtmDataException e)
		{
			System.out
					.println("Execption in TestDataAccess.testThatWeCanSaveAnAccoount().");
			e.printStackTrace();
		}
	}
}