package main_application.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The ATM logger for the ATM project
 * 
 */
public class AtmLogger
{
	/**
	 * Initializes the logging system for our purposes
	 * 
	 * @throws IOException
	 */
	static public void setup() throws IOException
	{
		// get the global logger to configure it
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		// suppress the logging output to the console
		Logger rootLogger = Logger.getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		if(handlers.length > 0)
		{
			if(handlers[0] instanceof ConsoleHandler)
			{
				logger.removeHandler(handlers[0]);
			}

		}

		logger.setLevel(Level.INFO);
		fileTxt = new FileHandler("Logging.txt");
		fileHTML = new FileHandler("Logging.html");

		// create a TXT formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);

		// create an HTML formatter
		formatterHTML = new AtmHtmlLoggingFormatter();
		fileHTML.setFormatter(formatterHTML);

		logger.addHandler(fileHTML);
	}

	static public void addAtmHandler(Logger logger)
	{
		logger.addHandler(fileTxt);
		logger.addHandler(fileHTML);
	}

	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;
	static private FileHandler fileHTML;
	static private Formatter formatterHTML;
}
