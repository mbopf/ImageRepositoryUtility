package gov.nih.nlm.iru;

import gov.nih.nlm.ceb.data.DBDataSource;
import gov.nih.nlm.ceb.data.DataSourceException;
import gov.nih.nlm.ceb.data.JNDIUnitTestHelper;

import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * IruController
 *
 * Created on December 6, 2005 by Mike Bopf
 *
 * @author mbopf
 */
public class IruController
{
  /** Create static reference to application log */
  private static final Logger LOGGER_ = Logger.getLogger(IruController.class.getName());
  
//  /** Default properties file for data source */
//  public static final String DATA_SOURCE_PROPERTIES = "datasource.properties";
  
  /** Initialize the application log */
  static 
  {   
    // Need to search for the properties file
    Properties prop = new Properties();
    String log4jFile = System.getProperty("log4j.configuration");
    if (log4jFile != null)
    {
      try
      {
        URL url = new URL(log4jFile);
        PropertyConfigurator.configure(url);
      }
      catch (MalformedURLException mue)
      {
        PropertyConfigurator.configure(log4jFile);
      }
    }
    else
    {
      try 
      { 
        prop.load(prop.getClass().getResourceAsStream("/log4j.properties"));
        PropertyConfigurator.configure(prop);
      } catch (Exception e) 
      { //Ignore exception; log4j will work fine but properties file just can't be changed on the fly...
        System.out.println("Could not initialize log4j system properly; continuing without any logging.");
      }
    }
  }
  
  /** Enforce this class to follow Singleton pattern by maintaining this instance */
  private static final IruController INSTANCE_ = new IruController();
    
  /** Creates a new instance of IruController */
  private IruController()
  {
  }
  
  private void init()
  {
    IruTaskList taskList = new IruTaskList();
    try
    {
      JNDIUnitTestHelper.init(DBDataSource.DEFAULT_PROPERTIES_FILE);
      taskList.perform();
    }
    catch (DataSourceException dse)
    {
      System.out.println("DataSourceException caught: " + dse);
      dse.printStackTrace();
      LOGGER_.error("DataSourceException caught: " + dse);
    }
    catch (IOException ioe)
    {
      System.out.println("IOException caught: " + ioe);
      ioe.printStackTrace();
      LOGGER_.error("IOException caught: " + ioe);
    }
    catch (NamingException ne)
    {
      System.out.println("NamingException caught: " + ne);
      ne.printStackTrace();
      LOGGER_.error("NamingException caught: " + ne);
    }
    catch (NoSuchAlgorithmException nsae)
    {
      System.out.println("NoSuchAlgorithmException caught: " + nsae);
      nsae.printStackTrace();
      LOGGER_.error("NoSuchAlgorithmException caught: " + nsae);
    }
    catch (IllegalArgumentException iae)
    {
      LOGGER_.equals("Invalid enum entered");
      System.out.println("IllegalArgumentException caught: " + iae);
      iae.printStackTrace();
    }
  }
    
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    // Call init() routine to display login screen
    LOGGER_.info("Starting up IRU");
    INSTANCE_.init();
  }
  
}
