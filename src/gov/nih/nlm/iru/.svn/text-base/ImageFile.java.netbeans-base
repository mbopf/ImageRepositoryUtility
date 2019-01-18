package gov.nih.nlm.iru;

import gov.nih.nlm.ceb.data.DBDataSource;
import gov.nih.nlm.ceb.data.DataSourceException;
import gov.nih.nlm.ceb.data.JNDIUnitTestHelper;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
/*
 * ImageFile.java
 *
 * Created on December 12, 2005, 12:56 PM by mbopf
 *
 */
public class ImageFile
{
  /** Create static reference to application log */
  private static final Logger LOGGER_ = Logger.getLogger(ImageFile.class.getName());

  private String cebImageId_ = null;
  
  private String origFilename_ = null;
  
  private String parentKeyId_ = null;
  
  private String dataset_ = null;
  
  private String imageType_ = null;
  
  private String compressType_ = null;
  
  private String compressRatio_ = null;
  
  private long ingestDate_ = 0L;
  
  private boolean roiFlag_ = false;
  
  private long size_ = 0L;
  
  private String md5_ = null;
  
  private URL url_ = null;
  
  private String comment_ = null;
  
  private ImageFileDAO dao_ = new ImageFileDAO();
  /** Creates a new instance of ImageFile */
  public ImageFile()
  {
  }
  
  public ImageFile(String cebImageId, String origFilename, String parentKeyId, 
                   String dataset, String imageType, String compressType,
                   String compressRatio, long ingestDate, boolean roiFlag, 
                   long size, String md5, URL url, String comment)
  {
    cebImageId_ = cebImageId;
    origFilename_ = origFilename;
    parentKeyId_ = parentKeyId;
    dataset_ = dataset;
    imageType_ = imageType;
    compressType_ = compressType;
    compressRatio_ = compressRatio;
    ingestDate_ = ingestDate;
    roiFlag_ = roiFlag;
    size_ = size;
    md5_ = md5;
    url_ = url;
    comment_ = comment;
  }
  
  public ImageFile(String cebImageId, String origFilename, String parentKeyId, 
                   String dataset, String imageType, String compressType,
                   String compressRatio, long ingestDate, boolean roiFlag,
                   long size, String md5, String urlString, String comment)
        throws MalformedURLException
  {
    // @todo - handle MalformedURLException
    URL url = new URL(urlString);
    new ImageFile(cebImageId, origFilename, parentKeyId, dataset, imageType, 
                  compressType, compressRatio, ingestDate, roiFlag, size, md5, 
                  url, comment);
  }
  
  public void getByOrigFilename(String origFilename) 
      throws DataSourceException, MalformedURLException
  {
    dao_.getByOrigFilename(origFilename);
  }
  
  public void save() throws DataSourceException
  {
    // Add validity check (i.e., NOT NULL fields aren't null, etc.)
    dao_.save();
  }
  
  public void delete() throws DataSourceException
  {
    dao_.delete();
  }
  
  public String getOrigFilename_()
  {
    return origFilename_;  
  }

  public String getMd5_()
  {
    return md5_;  
  }

  /** Internal Data Access Object (DAO) for this object */
  private class ImageFileDAO
  {
    private static final String TABLE_NAME = "imageData";
    private static final String INSERT_STRING = "INSERT INTO " + TABLE_NAME + 
      " (cebImageId, origFilename, parentKeyId, dataset, imageType, compressType, " +
      "  compressRatio, ingestDate, roiFlag, size, md5, url, comment) " +
      " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ORIGFILE_STRING = "SELECT * FROM " + TABLE_NAME + 
      " WHERE origFilename = '";
    private static final String GET_CEBID_STRING = "SELECT * FROM " + TABLE_NAME + 
      " WHERE cebImageId = '";
    private static final String DELETE_STRING = "DELETE FROM " + TABLE_NAME + 
      " WHERE origFilename = '";
    
    private void save() throws DataSourceException
    {
//      DBDataSource.getDataSource(DBDataSource.DEFAULT_DS, 
//                                 JNDIUnitTestHelper.PROPERTIES_FILE);
      DBDataSource ds = DBDataSource.getDataSource();
      PreparedStatement stm = null;
      Connection connection = null;
      String urlString = null;
      if (url_ != null)
      {
        urlString = url_.toExternalForm();
      }
      
      java.util.Date date = null;
      String dateStr = null;
      if (ingestDate_ != 0L)
      {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new java.util.Date(ingestDate_);
        dateStr = sdf.format(date); 
      }
      
      try
      {
        connection = ds.getConnection();
        stm = connection.prepareStatement(INSERT_STRING);
        stm.setString(1, cebImageId_);
        stm.setString(2, origFilename_);
        stm.setString(3, parentKeyId_);
        stm.setString(4, dataset_);
        stm.setString(5, imageType_);
        stm.setString(6, compressType_);
        stm.setString(7, compressRatio_);
        stm.setString(8, dateStr);
        stm.setBoolean(9, roiFlag_);
        stm.setLong(10, size_);
        stm.setString(11, md5_);
        stm.setString(12, urlString);
        stm.setString(13, comment_);
        int count = stm.executeUpdate();
//        LOGGER_.debug("count = " + count);
        
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
    }
    
    private void getByOrigFilename(String origFilename) 
        throws DataSourceException, MalformedURLException
    {
      if (origFilename == null || origFilename.length() <=0 )
      {
        throw new IllegalStateException("must specify 'origFilename' argument");
      }
//      DBDataSource.getDataSource(DBDataSource.DEFAULT_DS, 
//                                 JNDIUnitTestHelper.PROPERTIES_FILE);
      DBDataSource ds = DBDataSource.getDataSource();
      Statement stm = null;
      Connection connection = null;
      ResultSet rs = null;
      ImageFile imageFile = null;
      try
      {
        connection = ds.getConnection();
        stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
              ResultSet.CONCUR_READ_ONLY);
        rs = stm.executeQuery(GET_ORIGFILE_STRING + origFilename + "'");

        if (rs.next() == false)
        {
          origFilename_ = null; //Record not found
        }
        else
        {
          cebImageId_ = rs.getString(1);
          origFilename_ = rs.getString(2);
          parentKeyId_ = rs.getString(3);
          dataset_ = rs.getString(4);
          imageType_ = rs.getString(5);
          compressType_ = rs.getString(6);
          compressRatio_ = rs.getString(7);
          java.sql.Date sqlDate = rs.getDate(8);
          roiFlag_ = rs.getBoolean(9);
          size_ = rs.getLong(10);
          md5_ = rs.getString(11);
          String urlString = rs.getString(12);
          comment_ = rs.getString(13);

          if (sqlDate != null)
          {
            ingestDate_ = sqlDate.getTime();
          }
          if (urlString != null)
          {
            URL url_ = new URL(urlString);
          }
        }
        rs.close();
        rs = null;
        stm.close();
        stm = null;
        connection.close();
        connection = null;
      }
      catch (SQLException se)
      {
        LOGGER_.error("Caught SQLException while Querying by filename", se);
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
    }

    private void delete() throws DataSourceException
    {
      if (origFilename_ == null || origFilename_.length() <=0 )
      {
        throw new IllegalStateException("must specify 'origFilename' argument");
      }
//      DBDataSource.getDataSource(DBDataSource.DEFAULT_DS, 
//                                 JNDIUnitTestHelper.PROPERTIES_FILE);
      DBDataSource ds = DBDataSource.getDataSource();
      Statement stm = null;
      Connection connection = null;
      int result = 0;
      try
      {
        connection = ds.getConnection();
        stm = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
              ResultSet.CONCUR_READ_ONLY);
        result = stm.executeUpdate(DELETE_STRING + origFilename_ + "'");

        if (result != 1)
        {
          throw new IllegalStateException("Expected exactly 1 record to be deleted");
        }

        stm.close();
        stm = null;
        connection.close();
        connection = null;
      }
      catch (SQLException se)
      {
        LOGGER_.error("Caught SQLException while Querying by filename", se);
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
    }
  }
}
