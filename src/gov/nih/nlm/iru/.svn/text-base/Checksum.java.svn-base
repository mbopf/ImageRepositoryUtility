package gov.nih.nlm.iru;

import java.io.*;
import java.security.*;

/*
 * Checksum.java
 *
 * Taken from the "Real's Java HowTo", http://www.rgagnon.com
 * Modified extensively by Mike Bopf (National Library of Medicine)
 *
 * This is a candidate for the CebCommon utility library
 */
public class Checksum 
{
  public int create(String filename)
  {
    try
    {
      byte[] chk = createChecksum(filename);
      File f = new File(filename + ".chk");
      OutputStream os = new FileOutputStream(f);
      os.write(chk);
      os.close();
      System.out.println("Checksum = " + chk);
      System.out.println("Checksum[0] = " + chk[0]);
      String sum = new String(chk);
      System.out.println("Sum = " + sum);
      
      

StringBuffer hexString = new StringBuffer();
for (byte thisByte : chk)
{
  hexString.append(Integer.toHexString(0xFF & thisByte));
}
//for (int i=0;i<digest.length;i++) {
//  hexString.append(
//    Integer.toHexString(0xFF & digest[i]));
//  hexString.append(" ");
//}
System.out.println(hexString.toString());


      return 1;
    }
    catch(Exception e)
    { e.printStackTrace(); return 0;}
  }

  public int check(String filename)
  {
    int rc = 0;
    try
    {
      byte[] chk1 = createChecksum(filename);
      byte[] chk2 = new byte[chk1.length];
      File f = new File(filename + ".chk");
      InputStream is = new FileInputStream(f);
      
      is.read(chk2);
      
      if (new String(chk2).equals(new String(chk1)))
      {
        System.out.println("Same!");
        rc = 1;
      }
      else
      {
        System.out.println("Different!");
        rc = 2;
      }
      is.close();
      return rc;
    }
    catch(Exception e)
    { e.printStackTrace(); return rc;}
  }

  public byte[] createChecksum(String filename) throws Exception
  {
    InputStream fis =  new FileInputStream(filename);
    
    return createChecksum (fis);
  }

    public byte[] createChecksum(InputStream is) 
        throws Exception
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

  public static void main(String args[]) 
  {
     if (args.length == 2) 
     {
        if (args[0].equals("create")) 
        {
           System.exit(new Checksum().create(args[1]));
        }
        else if (args[0].equals("check")) 
        {
           System.exit(new Checksum().check(args[1]));
        }
     }
  }
}