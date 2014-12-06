package main_application.data;

import java.sql.Connection;

public class DataBaseAccessTestNICK
{

	public static void main(String[] args)
	{

		// Create instance of the DataAccess class and get a connection for it.
		DataAccess da = new DataAccess();
		Connection conn = da.getConnection();
		try
		{
			da.connect();
		}
		catch(AtmDataException e)
		{
			System.out.println("db Connection issue.");
			e.printStackTrace();
		}

		boolean isLoginValid = false;

		try
		{
			if(da.getAutentication(111, 222) > 0)
			{
				System.out
						.println("Login valid:  There is a user in the db with pin/id value in id column with value of 111 and userid value of 222 in account table.");
			}
		}
		catch(AtmDataException e)
		{
			System.out.println("db Connection issue.");
			e.printStackTrace();
		}

	}

}

/*
 * --Run this script in MySQL if you want to undo what this class does. delete
 * from atm.account where id = 116; delete from atm.account where id = 226;
 * delete from atm.account where id = 336;
 */
