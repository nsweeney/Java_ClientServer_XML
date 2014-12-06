package main_application.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import week06.data.AtmDataException;
import week06.data.DataAccess;
import week06.app.Account;
import week06.app.User;

import java.sql.SQLException;

/**
 * Provides the interface to the datastore For this implementation that is a
 * MySql database
 * 
 * @author scottl
 * @author Team 4
 * @author Demetrius Ford
 * @author Susie McQuaig
 * @author Michael Pierre
 * @author Nick Sweeney
 */
public class DataAccess {
	/**
	 * Default Constructor
	 */
	public DataAccess() {

		logger.info("DataAccess initialized");
	}

	/**
	 * Saves an account
	 * 
	 * @param p_account
	 *            - The account to save
	 * @throws AtmDataException
	 *             if there is a save error
	 */
	public void saveAccount(Account p_account) throws AtmDataException {
		Calendar now = Calendar.getInstance(); // Gets the current date and
		// time.
		Date updateDate = new java.sql.Date(now.getTime().getTime());

		try {
			// ****SQL script creating table takes in an int for 'id' column,
			// not Long!!!! Should this be cast to Integer (cant cast Long to
			// Int)?
			m_saveAccountStatement.setLong(1, p_account.getAccountId());
			// ****SQL script creating table takes in an bigint for 'user_id'
			// column, not Long!!!! Should this be cast to BigInt (cant cast
			// long to BigInt)?
			m_saveAccountStatement.setLong(2, p_account.getUser().getUserId());
			m_saveAccountStatement.setString(3, p_account.getName());
			// **No getter for balance in Account class so I created one!!!!
			m_saveAccountStatement.setDouble(4, p_account.getBalance());
			m_saveAccountStatement.setDate(5, updateDate);
			m_saveAccountStatement.executeUpdate();

			// log Insert success
			logger.info("Account with ID " + p_account.getAccountId()
					+ " successefully saved: ");

		} catch (SQLException ex) {
			// log saveAccount error
			logger.severe("SQL Issue with saveAccount method.  Exception: "
					+ ex);
			throw new AtmDataException(ex);
		}
	}

	/**
	 * Get the requested Account
	 * 
	 * @param p_accountId
	 *            - Account ID to retrieve
	 * @return An Account reference
	 * @throws AtmDataException
	 */
	public Account getAccount(int p_accountId) throws AtmDataException {
		Account account = new Account();
		ResultSet resultSet = null;

		try {
			// set our query to search for the passed in 'accountId'
			m_selectAccountIndividualStatement.setLong(1, p_accountId);
			resultSet = m_selectAccountIndividualStatement.executeQuery();

			// this should return only one value since we a querying on a PK
			while (resultSet.next()) {

				account = newAccount(resultSet);
			}
		} catch (SQLException ex) {
			// log getAccount error
			logger.severe("SQL Issue with getAccount method.  Exception: " + ex);
			throw new AtmDataException(ex);
		}

		return account;
	}

	/**
	 * Gets all the accounts in a list
	 * 
	 * @return List of Accounts
	 * @throws AtmDataException
	 */
	public List<Account> getAllAccounts() throws AtmDataException {
		List<Account> accounts = new ArrayList<Account>();
		ResultSet resultSet = null;

		try {
			resultSet = m_selectAccountStatement.executeQuery();

			while (resultSet.next()) {

				accounts.add(newAccount(resultSet));
			}
		} catch (SQLException ex) {
			// log getAllAccounts error
			logger.severe("SQL Issue with getAllAccounts method.  Exception: "
					+ ex);
			throw new AtmDataException(ex);
		}
		return accounts;
	}

	/**
	 * Saves a new user into the MySQL database.
	 * 
	 * @param user
	 * @throws AtmDataException
	 */

	public void saveUser(User user) throws AtmDataException {
		Calendar now = Calendar.getInstance(); // Gets the current date and
												// time.
		Date updateDate = new java.sql.Date(now.getTime().getTime());

		try {
			m_saveUserStatement.setLong(1, user.getUserId());
			m_saveUserStatement.setString(2, user.getFirstName());
			m_saveUserStatement.setString(3, user.getLastName());
			m_saveUserStatement.setDate(4, updateDate);
			m_saveUserStatement.executeUpdate();
			// log that the info was inserted within the database.
			logger.info("SaveUser was executed");
		} catch (SQLException ex) {
			// log saverUser method
			logger.severe("SQL Issue with saveUser method.  Exception: " + ex);
			throw new AtmDataException(ex);
		}
	}

	/**
	 * Creates a new Account from a result set.
	 * 
	 * @param p_resultSet
	 *            -records from the atm.account table
	 * @return A new Account.
	 * @throws AtmDataException
	 */
	private Account newAccount(ResultSet p_resultSet) throws AtmDataException {
		Account account = new Account();

		try {
			// Does it matter that column is an int?
			long accountId = p_resultSet.getLong("id");
			// Does it matter that column is an int?
			long accountsUserId = p_resultSet.getLong("user_id");
			String name = p_resultSet.getString("name");
			double balance = p_resultSet.getDouble("balance");
			User user = findUser(accountsUserId);
			account = new Account(accountId, user, name, balance);
		} catch (SQLException ex) {
			// log error
			logger.severe("SQL Issue with newAccount method.  Exception: " + ex);
			throw new AtmDataException(ex);
		}

		return account;
	}

	/**
	 * Searches a list of Users to find one with a matching passed in
	 * accountsUserId.
	 * 
	 * @param p_userId
	 *            -user_id you want to match a User with.
	 * @return A single User
	 * @throws AtmDataException
	 */
	private User findUser(long p_userId) throws AtmDataException
	{
		User user = new User();

		try
		{
			List<User> userList = getUsers();

			for(User tempUser : userList)
			{
				if(tempUser.getUserId() == p_userId)
				{
					user.setUserId(p_userId);
					user.setFirstName(tempUser.getFirstName());
					user.setLastName(tempUser.getLastName());
					break;
				}
			}
		}
		catch(Exception ex)
		{
			logger.severe("There appears to be an issue with findUser method: "
					+ ex);
			throw new AtmDataException(ex);
		}

		return user;
	}

	/**
	 * The users of the account table within the MySQL database are returned
	 * within a list called userList. The resultSet executes the MySQL statement
	 * and within the try block a while loop is executed in order to add the
	 * users information towards the userList.
	 * 
	 * @return userList
	 * @throws AtmDataException
	 */
	public List<User> getUsers() throws AtmDataException {
		List<User> userList = new ArrayList<User>();
		ResultSet resultSet = null;

		try {
			resultSet = m_selectUserStatement.executeQuery();

			while (resultSet.next()) {
				long userId = resultSet.getLong("id");
				String first = resultSet.getString("first_name");
				String last = resultSet.getString("last_name");

				userList.add(new User(userId, first, last));
			}
			logger.info("Returns List of users.");
		} catch (SQLException ex) {
			// log getUsers error
			logger.severe("SQL Issue with getUsers method.  Exception: " + ex);
			throw new AtmDataException(ex);
		}

		return userList;
	}

	/**
	 * getAutentication This method queries the database to check if the user is
	 * within the ACCOUNT table.
	 * 
	 * @param pin
	 * @param account_id
	 * @return userId count to see if the user exist within the database.
	 * @throws AtmDataException
	 */
	public long getAutentication(long pin, long account_id)
			throws AtmDataException {
		ResultSet resultSet = null;

		try {

			// pin looks at 'id' column
			m_selectAuthenticateUserStatement.setLong(1, pin);
			// account_id looks at 'user-id' column
			m_selectAuthenticateUserStatement.setLong(2, account_id);
			resultSet = m_selectAuthenticateUserStatement.executeQuery();

			resultSet.next();
			long userId = resultSet.getLong("COUNT(id)");

			return userId;

		} catch (SQLException ex) {
			// log exception
			logger.severe("SQL Issue with getAutentication method.  Exception: "
					+ ex.getMessage());
			throw new AtmDataException(ex);
		}

	}

	/**
	 * Registers the JDBC driver, and MySQL database host. The MySQL database
	 * statements are pre-compiled.
	 * 
	 * @throws AtmDataException
	 */
	public void connect() throws AtmDataException {
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");

			// setup the connection with the DB.
			m_connect = DriverManager
					.getConnection("jdbc:mysql://localhost/atm?"
							+ "user=root&password=mainroot");
			//
			// pre-compile prepared statements
			//
			m_saveUserStatement = m_connect.prepareStatement(INSERT_USER_SQL);
			m_selectUserStatement = m_connect.prepareStatement(SELECT_USER_SQL);
			// Nick added START
			m_saveAccountStatement = m_connect
					.prepareStatement(INSERT_ACCOUNT_SQL);
			m_selectAccountStatement = m_connect
					.prepareStatement(SELECT_ACCOUNT_SQL);
			m_selectAccountIndividualStatement = m_connect
					.prepareStatement(SELECT_ACCOUNT_INDIVIDUAL_SQL);
			m_selectAuthenticateUserStatement = m_connect
					.prepareStatement(SELECT_AUTHENTICATE_USER_SQL); // Mike
																		// Added
			// Nick added END
			logger.info("Connections set");
		} catch (SQLException ex) {
			// log exception
			logger.severe("SQL Issue with connect method.  Exception: "
					+ ex.getMessage());
			throw new AtmDataException(ex);
		} catch (Exception ex) {
			// log exception
			logger.severe("There appears to be an issue: " + ex);
			throw new AtmDataException(ex);
		}
	}

	/**
	 * Returns the connection.
	 * 
	 * @return m_connect
	 */
	public Connection getConnection() {
		return m_connect;
	}

	/**
	 * Connection object for the query database.
	 */
	private Connection m_connect = null;

	/**
	 * m_saveUserStatement is the prepared statement variable to set up the
	 * INSERT query for the users.
	 */
	private PreparedStatement m_saveUserStatement;

	/**
	 * m_selectUserStatement is the prepared statement variable to set up the
	 * SELECT query for the users.
	 */
	private PreparedStatement m_selectUserStatement;

	/**
	 * m_saveAccountStatement is the prepared statement variable to set up the
	 * INSERT query for the accounts.
	 */
	private PreparedStatement m_saveAccountStatement;

	/**
	 * m_selectAccountStatement is the prepared statement variable to set up the
	 * SELECT query for the accounts.
	 */
	private PreparedStatement m_selectAccountStatement;

	/**
	 * m_selectAccountIndividualStatement is the prepared statement variable to
	 * set up the the SELECT statement to select the individual accounts.
	 */
	private PreparedStatement m_selectAccountIndividualStatement;

	/**
	 * INSERT_USER_SQL is the variable that registers the INSERT query for the
	 * user table within the MySQL database.
	 */
	private String INSERT_USER_SQL = "insert into  atm.user values (?, ?, ?, ?)";

	/**
	 * SELECT_USER_SQL is the variable that registers the SELECT query for the
	 * user. This query selects the id, first name, last name from user database
	 * table.
	 */
	private String SELECT_USER_SQL = "SELECT id, first_name, last_name from atm.user";

	/**
	 * INSERT_ACCOUNT_SQL is the variable that registers the INSERT query from
	 * the account table within the MySQL database.
	 */
	private String INSERT_ACCOUNT_SQL = "insert into  atm.account values (?, ?, ?, ?, ?)";

	/**
	 * SELECT_ACCOUNT_SQL is the variable that registers the SELECT query from
	 * the account table within the MySQL database. This query selects the id
	 * user id, name, balance, and last_update.
	 */
	private String SELECT_ACCOUNT_SQL = "SELECT id, user_id, name, balance, last_update from atm.account";

	/**
	 * SELECT_ACCOUNT_INDIVIDUAL_SQL is the variable that registers the SELECT
	 * SQL query for the account table. The fields that are being queried from
	 * the account table from this variable is is, user_id, name, balance,
	 * last_update.
	 */
	private String SELECT_ACCOUNT_INDIVIDUAL_SQL = "SELECT id, user_id, name, balance, last_update from atm.account WHERE id = ?";
	/**
	 * Creating the logger object and, calling the getLogger method in order to
	 * use logging level for the DataAccess class.
	 */
	private final static Logger logger = Logger.getLogger(DataAccess.class
			.getName());
	/**
	 * Created for use in the getAuthentication method
	 */
	private PreparedStatement m_selectAuthenticateUserStatement;
	/**
	 * Created for use in the getAuthentication method
	 */
	private String SELECT_AUTHENTICATE_USER_SQL = "SELECT COUNT(id) FROM atm.account WHERE id=? AND user_id=?";

}
