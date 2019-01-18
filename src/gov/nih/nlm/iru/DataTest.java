package gov.nih.nlm.iru;

import gov.nih.nlm.ceb.data.DBDataSource;
import gov.nih.nlm.ceb.data.DataSourceException;
import gov.nih.nlm.ceb.data.JNDIUnitTestHelper;
import java.io.*;
import java.sql.*;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/*
 * DataTest.java
 */

public class DataTest 
{
  private static final Logger LOGGER_ = Logger.getLogger(DataTest.class.getName());
  
  public static void main(String args[]) 
  {
    if (args.length != 5)
    {
      System.out.println
      ("Usage : java DataTest <cebImageId> <fileName> <parentImage> <md5> <url>\n");
      System.exit(-1);
    }
    try
    {
      JNDIUnitTestHelper.init(null);
      System.exit (writeToDB (args[0], args[1], args[2], args[3], args[4]));
    }
    catch (DataSourceException dse)
    {
      System.out.println("Caught DataSourceException");
      dse.printStackTrace();
      System.exit (-1);
    }
    catch (NamingException ne)
    {
      System.out.println("Caught IOException");
      ne.printStackTrace();
      System.exit (-1);
    }
    catch (IOException ioe)
    {
      System.out.println("Caught IOException");
      ioe.printStackTrace();
      System.exit (-1);
    }
    catch (SQLException se)
    {
      System.out.println("Caught SQLException");
      se.printStackTrace();
      System.exit (-1);
    }
  }

  public static int writeToDB(String cebImageId, String fileName, String parentImage,
                              String md5, String url) 
         throws DataSourceException, SQLException
  {
    String INSERT_STRING = "INSERT INTO image " +
                           "(cebImageId, origFilename, parentImage, md5, url) " +
                           "VALUES (?, ?, ?, ?, ?)";
    DBDataSource.getDataSource(DBDataSource.DEFAULT_DS,
                               DBDataSource.DEFAULT_PROPERTIES_FILE);
    DBDataSource ds = DBDataSource.getDataSource();
    PreparedStatement stm = null;
    Connection connection = null;

    try 
    {
      connection = ds.getConnection();
      stm = connection.prepareStatement(INSERT_STRING);
      stm.setString(1, cebImageId);
      stm.setString(2, fileName);
      stm.setString(3, parentImage);
      stm.setString(4, md5);
      stm.setString(5, url);
      int count = stm.executeUpdate();
      System.out.println("count = " + count);

      stm.close();
      stm = null;
      connection.close();
      connection = null;
    }
    catch (SQLException se)
    {
      LOGGER_.error("Caught SQLException while inserting line", se);
    }
    finally
    {
      try
      {
        if (stm != null)
        {
          stm.close();
          connection.close();
        }
      }
      catch (SQLException se)
      {} //Nothing to do
    }
    return 0;
  }
}