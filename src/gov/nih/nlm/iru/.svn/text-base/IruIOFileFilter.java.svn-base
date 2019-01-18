package gov.nih.nlm.iru;

import java.net.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;

/*
 * IruInputFilter.java
 *
 * Created on May 30, 2006, 3:36 PM by mbopf
 *
 */
public class IruIOFileFilter
{
  /** Create static reference to application log */
  private static final Logger LOGGER_ = 
      Logger.getLogger(IruIOFileFilter.class.getName());
  
  /** Possible input modes for the IRU */
  public enum InputMode { ALL, EXT, WILD, LIST, MAP, REGEX }
  
  private InputMode inputMode_ = null;
  
  private String inputFilter_ = null;
  
  private boolean subdirFlag_ = false;
  
  private String inputDir_ = null;
  
  private String outputDir_ = null;
  
  /** Creates a new instance of IruInputFilter */
  public IruIOFileFilter(String inputFilter, boolean subdirFlag, 
                        String inputDir, String outputDir)
  {
    inputMode_ =
        InputMode.valueOf(IruResourceBundle.getINSTANCE_().getInputMode());

    inputFilter_ = inputFilter; 
    subdirFlag_ = subdirFlag;
    inputDir_ = inputDir;
    outputDir_ = outputDir;
  }
  
  /**
   *
   */
  public void getFiles(List<File> inputList, List<File> outputList) 
      throws IOException
  {
    if (inputMode_ == null || inputDir_ == null || outputDir_ == null)
    {
      String msg = "inputMode_, inputDir_ and outputDir_ must be non-null";
      LOGGER_.error(msg);
      throw new IllegalStateException(msg);
    }
    LOGGER_.debug("inputMode_ = " + inputMode_);
        
    File inDirFile = new File(inputDir_);
    if (!inDirFile.exists())
    {
      String msg = "InputDir does not exist";
      LOGGER_.error(msg);
      throw new IllegalStateException(msg);
    }
    
    // Append trailing slash in outputDir if user didn't include it
    if (outputDir_ != null && !outputDir_.endsWith("/"))
    {
      outputDir_ = outputDir_ + "/";
    }
    
    // Check for invalid combinations of inputDir & inputMode
    switch (inputMode_)
    {
      case ALL:
        if (inputFilter_ != null && inputFilter_.length() > 0)
        {
          LOGGER_.warn("Specified inputFilter IGNORED for inputMode of ALL");
        }
      case EXT:
      case WILD:
      case REGEX:
        if (!inDirFile.isDirectory())
        {
          String msg = "Invalid combination - inputDirectory must be a directory";
          LOGGER_.error(msg);
          throw new IllegalStateException(msg);
        }
        break;
      case LIST:
      case MAP:
        if (inDirFile.isDirectory())
        {
          String msg = "Invalid combination - inputDirectory must point to a file";
          LOGGER_.error(msg);
          throw new IllegalStateException(msg);
        }
        if (subdirFlag_)
        {
          LOGGER_.warn("Subdirectory flag IGNORED for inputMode of LIST/MAP");
        }
        break;
      default:
        String msg = "Unexpected InputMode - exiting";
        LOGGER_.error(msg);
        throw new IllegalStateException(msg);
    }
    
    // Use commonIO library to get list of files based on inputs
    IOFileFilter fileFilter = null;
    IOFileFilter dirFilter = null;
    
    if (subdirFlag_)
    {
      dirFilter = FileFilterUtils.trueFileFilter();
    }
    // else - dirFilter is null, which is what we want
    
    switch (inputMode_)
    {
      case ALL:
      case EXT:
      case WILD:
        fileFilter = determineFilter(inputMode_, inputFilter_);
        Collection<File> fileColl = 
            FileUtils.listFiles(inDirFile, fileFilter, dirFilter);
        inputList.addAll(fileColl);
        // Sort the returnList; @todo - make sorting an option
//        Collections.sort(inputList); 
        
        // Create output list based on the input files (if copying)
//      Note - outputDir_ is being used to indirectly determine the DbMode
//        since it can be empty if we are only checking.
//        if (outputDir_ != null && outputDir_.length() > 0)
//        {
          for (File inFile : inputList)
          {
            String fileName = inFile.getName();
            File outFile = new File(outputDir_ + fileName);
            outputList.add(outFile);
          }
//        }
        
        break;
      case REGEX:
        // Implement custom filter using java.io.FilenameFilter and wrap it
        //using FileFilterUtils.asFileFilter(FileFilter filter)
        String msg = "REGEX case not yet implemented";
        LOGGER_.error(msg);
        throw new IllegalStateException(msg);
      case LIST:
      case MAP:
      {
        List<String> lineList = FileUtils.readLines(inDirFile, null);
        for (String line : lineList)
        {
          // Remove leading & trailing whitespace and ignore comments & blank lines
          line = line.trim();
          if (line.length() == 0 || line.startsWith("#"))
          {
            continue;
          }
          
          // Pull off first token in case a map is being used as the input LIST
          String [] tokens = line.split("\\s+");
          if (tokens.length == 0)
          {
            String mesg = "Invalid LIST or MAP file line: '" + line + "'";
            LOGGER_.error(mesg);
            throw new IllegalStateException(mesg);
          }
          
          File inputFile = new File(tokens[0]);
          if (inputFile.exists() && inputFile.isFile())
          {
            inputList.add(inputFile);
          }
          else
          {
            String mesg = "Input file does not exist: '" + tokens[0] + "'";
            LOGGER_.error(mesg);
            throw new IllegalStateException(mesg);
          }
          
          // Create output list
          File outputFile = null;
          if (inputMode_ == InputMode.LIST)
          {
            if (tokens.length > 1)
            {
              LOGGER_.warn("Extra values in LIST file; line: '" + line + "'");
            }
            String fileName = inputFile.getName();
            outputFile = new File(outputDir_ + fileName);
          }
          else if (inputMode_ == InputMode.MAP)
          {
            if (tokens.length > 2)
            {
              LOGGER_.warn("Extra values in MAP file; line: '" + line + "'");
            }
            else if (tokens.length < 2)
            {
              String mesg = "Invalid MAP file line: '" + line + "'";
              LOGGER_.error(mesg);
              throw new IllegalStateException(mesg);
            }
            
            outputFile = new File(outputDir_ + tokens[1]);
          }
          // File may or may not exist - let calling routine handle errors
          outputList.add(outputFile);
        }
       break;
      }
      default:
      {
        String mesg = "Invalid inputMode_";
        LOGGER_.error(mesg);
        throw new IllegalStateException(mesg);
      }
    }
    
    // Check to make sure returned lists are of the same size
    if (inputList.size() != outputList.size())
    {
      String msg = "Input & Output lists have different sizes";
      LOGGER_.error(msg);
      throw new IllegalStateException(msg);
    }
  }
  
  /**
   *
   */
  public IOFileFilter determineFilter(InputMode inputMode, String filterString)
  {
    switch (inputMode)
    {
      case ALL: 
        return FileFilterUtils.trueFileFilter();
      case EXT: 
        return FileFilterUtils.suffixFileFilter(filterString);
      case WILD: 
        return new WildcardFilter(filterString);
      case REGEX:
        // Implement custom filter using java.io.FilenameFilter and wrap it
        //using FileFilterUtils.asFileFilter(FileFilter filter)
        LOGGER_.error("REGEX case not yet implemented");
        throw new IllegalStateException("Case not yet implemented");
      default:
      {
        String msg = "Invalid inputMode = " + inputMode;
        LOGGER_.error(msg);
        throw new IllegalStateException(msg);
      }
    }
  }
}
