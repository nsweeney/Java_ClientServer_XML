package main_application;

import main_application.io.TcpServer;
import test.AbstractTestCase;

public class TestCommunication extends AbstractTestCase
{
	/**
	 * Default constructor
	 */
	public TestCommunication()
	{
		super("TestCommunication");
	}

	@Override
	protected boolean runTest()
	{
		boolean result = true;

		// Tests
		boolean comms = testComms();

		result = comms;

		return result;
	}

	private boolean testComms()
	{
		TcpServer server = new TcpServer();

		return false;
	}
}
