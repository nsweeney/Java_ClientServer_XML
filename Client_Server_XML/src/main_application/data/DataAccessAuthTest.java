package main_application.data;

import java.sql.SQLException;

import org.junit.Test;

public class DataAccessAuthTest
{

	@Test
	public void test() throws AtmDataException, SQLException
	{
		DataAccess d = new DataAccess();
		d.connect();
		d.getAutentication(111, 222);
	}

}
