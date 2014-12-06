package test;

import java.util.ArrayList;

/**
 * File: TestHarness.java
 */
public class TestEngine
{
	public TestEngine()
	{
		m_tests = new ArrayList<AbstractTestCase>();
	}
	
	/**
	 * Add a test case for the engine to run
	 * 
	 * @param test An AbstractTestCase subclass instance
	 */
	public void addTest(AbstractTestCase test)
	{
		m_tests.add(test);
	}
	
	/**
	 * Iterates the list of tests and executes them
	 */
	public void runTests()
	{
		
		for(AbstractTestCase test : m_tests)
		{			
			String resultString = "Success";
			trace(" -- starting test: " + test.getName());
			
			// This is using the polymorphic behavior of OOP. 
			// Each test case implements the abstract runTest method
			// which is uniquely implemented by each subclass test case
			boolean result = test.run();
			if( !result  )
			{
				trace("  ** Failed: " + test.results());
				resultString = "Failed";
			}
			else
			{
				trace("Results:\n" + test.results());
			}
			
			trace(" -- Completed test: " + test.getName() + " - " + resultString);
		}
	}
    
	static private void trace(String msg)
	{
		System.out.println(msg);
	}   
    
    private ArrayList<AbstractTestCase> m_tests;
}