/*
 * SlideIdConvert.java
 *
 * Created on November 28, 2005, 12:21 PM
 *
 */

package gov.nih.nlm.iru;
import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author mbopf
 */
public class SlideIdConvert
{
  private static final String ACCESSNO_PATTERN = "[S-]*\\w+-\\d+";
  
  private String accessNo_ = "";
  private String batch_ = "";
  private String slide_ = "";
  
  /** Creates a new instance of SlideIdConvert */
  public SlideIdConvert()
  {
  }
  
  public void convert() throws IOException
  {
    // Compile pattern and prepare input
    Pattern pattern = Pattern.compile(ACCESSNO_PATTERN);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String line = "";
    line = in.readLine();
    while (line != null)
    {
//      System.out.println("line = " + line);
      Matcher matcher = pattern.matcher(line);
      
      if (matcher.find())
      {
        accessNo_ = matcher.group();
//        System.out.println("accessNo_ = " + accessNo_);
        int batchSlideStart = matcher.end();
        
        String batchSlideStr = line.substring(batchSlideStart, line.length());
//        System.out.println("batchSlideStr = " + batchSlideStr);
      
        String[] trailingValues = batchSlideStr.split("-");
//        System.out.println("trailingValues.length = " + trailingValues.length);
        if (trailingValues.length == 1)
        {
          batch_ = "";
          slide_ = trailingValues[0];
        }
        else if (trailingValues.length == 2)
        {
           batch_ = trailingValues[0];
          slide_ = trailingValues[1];
        }
        else if (trailingValues.length == 3)
        {
          batch_ = trailingValues[1];
          slide_ = trailingValues[2];
        }
        else
        {
          throw new IllegalStateException("Invalid number of split fields; batchSlideStr = "
            + batchSlideStr + "; trailingValues.length = " + trailingValues.length);
        }
        System.out.println(accessNo_ + "#" + batch_ + "#" + slide_);
        
      }
      else
      {
        //error
      }
      line = in.readLine();
    }
    
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    SlideIdConvert sic = new SlideIdConvert();
    try
    {
      sic.convert();
    }
    catch (IOException ioe)
    {
      System.out.println("IOException caught");
      ioe.printStackTrace();
    }
    catch (IllegalStateException ise)
    {
      System.out.println("IllegalStateException caught");
      ise.printStackTrace();
    }
  }
}
