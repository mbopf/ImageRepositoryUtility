/*
 * RegExpTokenizer.java
 *
 * Created on November 28, 2005, 10:20 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.nih.nlm.iru;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author mbopf
 */
public class RegExpTokenizer implements Iterator
{
  /** Holds the original input to search for tokens */
  private CharSequence input;
  
  /** Used to find tokens */
  private Matcher matcher;
  
  /** If true, the String between tokens are returned */
  private boolean returnDelims;
  
  /** The current delimiter value. If non-null, should be returned
      at the next call to next() */
  private String delim;
  
  /** The current matched value. If non-null and delim=null,
      should be returned at the next call to next() */
  private String match;
  
  /** The value of matcher.end() from the last successful match. */
  private int lastEnd = 0;
  
  /**
   * Creates a new instance of RegExpTokenizer 
   */
  public RegExpTokenizer()
  {
  }
  
  // patternStr is a regular expression pattern that identifies tokens.
  // If returnDelims delim is false, only those tokens that match the
  // pattern are returned. If returnDelims true, the text between
  // matching tokens are also returned. If returnDelims is true, the
  // tokens are returned in the following sequence - delimiter, token,
  // delimiter, token, etc. Tokens can never be empty but delimiters might
  // be empty (empty string).
  public RegExpTokenizer(CharSequence input, String patternStr, boolean returnDelims)
  {
    // Save values
    this.input = input;
    this.returnDelims = returnDelims;
    
    // Compile pattern and prepare input
    Pattern pattern = Pattern.compile(patternStr);
    matcher = pattern.matcher(input);
  }
  
  // Returns true if there are more tokens or delimiters.
  public boolean hasNext()
  {
    if (matcher == null)
    {
      return false;
    }
    if (delim != null || match != null)
    {
      return true;
    }
    if (matcher.find())
    {
      if (returnDelims)
      {
        delim = input.subSequence(lastEnd, matcher.start()).toString();
      }
      match = matcher.group();
      lastEnd = matcher.end();
    }
    else if (returnDelims && lastEnd < input.length())
    {
      delim = input.subSequence(lastEnd, input.length()).toString();
      lastEnd = input.length();
      
      // Need to remove the matcher since it appears to automatically
      // reset itself once it reaches the end.
      matcher = null;
    }
    return delim != null || match != null;
  }
  
  // Returns the next token (or delimiter if returnDelims is true).
  public Object next()
  {
    String result = null;
    
    if (delim != null)
    {
      result = delim;
      delim = null;
    }
    else if (match != null)
    {
      result = match;
      match = null;
    }
    return result;
  }
  
  // Returns true if the call to next() will return a token rather
  // than a delimiter.
  public boolean isNextToken()
  {
    return delim == null && match != null;
  }
  
  // Not supported.
  public void remove()
  {
    throw new UnsupportedOperationException();
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    CharSequence inputStr = "97A-00469-1-2";
    String patternStr = "[S-]*\\w+-\\d+";
    
    // Set to false if only the tokens that match the pattern are to be returned.
    // If true, the text between matching tokens are also returned.
    boolean returnDelims = true;
    
    // Create the tokenizer
    Iterator tokenizer = new RegExpTokenizer(inputStr, patternStr, returnDelims);
    
    String tokenOrDelim = "";
    // Get the tokens (and delimiters)
    for (; tokenizer.hasNext(); )
    {
      tokenOrDelim = (String)tokenizer.next();
      System.out.println("tokenOrDelim = " + tokenOrDelim);
    }
    // [ "97A-3456-", "scum" ]
  }
  
}
