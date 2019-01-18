package gov.nih.nlm.iru;

import java.io.*;
import java.security.*;
import org.apache.log4j.Logger;

/*
 * Checksummer.java
 *
 * Taken from the "Real's Java HowTo", http://www.rgagnon.com
 * Modified extensively by Mike Bopf (National Library of Medicine)
 *
 * This is a candidate for the CebCommon utility library
 */
public class Checksummer 
{
  /** Create static reference to application log */
  private static final Logger LOGGER_ = Logger.getLogger(Checksummer.class.getName());
  
  public static boolean checkChecksum(String checkSum, File file)
        throws IOException, NoSuchAlgorithmException
  {
    return checkChecksum(checkSum, new FileInputStream(file));
  }
  
  public static boolean checkChecksum(String checkSum, String fileName)
        throws IOException, NoSuchAlgorithmException
  {
    return checkChecksum(checkSum, new FileInputStream(fileName));
  }
  
  public static boolean checkChecksum(String checkSum, InputStream is)
        throws IOException, NoSuchAlgorithmException
  {
    String checkString = new String(createChecksum(is));
    return (checkString.equals(checkSum));
  }

  public static String createChecksum(File file)
        throws IOException, NoSuchAlgorithmException
  {
    return byteArrayToHexString(calcChecksum (new FileInputStream(file)));
  }

  public static String createChecksum(String filename)
        throws IOException, NoSuchAlgorithmException
  {
    return byteArrayToHexString(calcChecksum (new FileInputStream(filename)));
  }

  public static String createChecksum(InputStream is)
        throws IOException, NoSuchAlgorithmException
  {
    return byteArrayToHexString(calcChecksum (is));
  }
  
  private static byte[] calcChecksum(InputStream is) 
        throws IOException, NoSuchAlgorithmException
  {
    byte[] buffer = new byte[1024];
    MessageDigest complete = MessageDigest.getInstance("MD5");
    int numRead;
    do
    {
      numRead = is.read(buffer);
      if (numRead > 0)
      {
        complete.update(buffer, 0, numRead);
      }
    } while (numRead != -1);
    is.close();
    return complete.digest();
  }
  
  public static String copyChecksum(File input, File output)
        throws IOException, NoSuchAlgorithmException
  {
    InputStream is = new FileInputStream(input);
    OutputStream os = new FileOutputStream(output);
    byte[] checkSum = copyChecksum(is, os);
    return byteArrayToHexString(checkSum);
  }
  
  public static byte[] copyChecksum(InputStream is, OutputStream os)
        throws IOException, NoSuchAlgorithmException
  {
    // Transfer bytes from in to out
    byte[] buf = new byte[1024];
    MessageDigest complete = MessageDigest.getInstance("MD5");
    int len;
    while ((len = is.read(buf)) > 0)
    {
      os.write(buf, 0, len);
      complete.update(buf, 0, len);
    }
    is.close();
    os.close();
    return complete.digest();
  }

  /**
   * This method was taken from devx.com. ( http://www.devx.com/tips/Tip/13540 )
   * Convert a byte[] array to readable string format. This makes the "hex"
   * readable!
   * @author Jeff Boyle; modified (slightly) by Mike Bopf
   * @return result String buffer in String format 
   * @param in byte[] buffer to convert to string format
   */
  public static String byteArrayToHexString(byte in[]) 
  {
    byte ch = 0x00;
    int i = 0; 
    if (in == null || in.length <= 0)
    {
        return null;
    }
        
    String pseudo[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    StringBuffer out = new StringBuffer(in.length * 2);

    while (i < in.length) 
    {
      ch = (byte) (in[i] & 0xF0); // Strip off high nibble
      ch = (byte) (ch >>> 4);
      
      // shift the bits down
      ch = (byte) (ch & 0x0F);    
      // must do this is high order bit is on!
      out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
      ch = (byte) (in[i] & 0x0F);    // Strip off low nibble 

      out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
      i++;
    }

    String rslt = new String(out);
    return rslt;
}    

  /**
   * This main routine is for unit testing only.
   */
  public static void main(String args[]) 
  {
    try
    {
      //If only 1 argument, it should be the filename to be checkSummed
      if (args.length == 1) 
      {
        String checkSum = createChecksum(args[0]);
        System.out.println("checkSum = " + checkSum);
      }
      // If two arguments, first is the checkSum string & second is filename
      else if (args.length == 2)
      {
        boolean match = checkChecksum(args[0], args[1]);
        System.out.println("match = " + match);
      }
      else
      {
        System.out.println("Syntax: Checksummer <filename>");
        System.out.println("  OR  : Checksummer <checksum String> <filename>");
      }
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
      System.out.println ("IOException caught: " + ioe.getMessage());
    }
    catch (NoSuchAlgorithmException nsae)
    {
      nsae.printStackTrace();
      System.out.println ("NoSuchAlgorithmException caught: " + nsae.getMessage());
    }
  }
}