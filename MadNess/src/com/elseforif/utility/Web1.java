package com.elseforif.utility;

import com.elseforif.log.MetaLog;
import java.applet.Applet;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class provides a number of static facilitators useful for Web programming,
 * such as applets and servlets.
 */
public final class Web1 extends Object
  {

  public static final int BROWSER_UNDETERMINED = 0;
  public static final int BROWSER_NETSCAPE = 10;
  public static final int BROWSER_NETSCAPE_3_0_1 = 11;
  public static final int BROWSER_NETSCAPE_4_0 = 12;
  public static final int BROWSER_NETSCAPE_4_8 = 13;
  public static final int BROWSER_NETSCAPE_4_6_1 = 14;
  public static final int BROWSER_NETSCAPE_5 = 15;
  public static final int BROWSER_NETSCAPE_MAX = 29;
  public static final int BROWSER_INTERNET_EXPLORER = 30;
  public static final int BROWSER_INTERNET_EXPLORER_3_0_2 = 31;
  public static final int BROWSER_INTERNET_EXPLORER_4_0_1 = 32;
  public static final int BROWSER_INTERNET_EXPLORER_5_0 = 33;
  public static final int BROWSER_INTERNET_EXPLORER_5_0_1 = 34;
  public static final int BROWSER_INTERNET_EXPLORER_5_5 = 35;
  public static final int BROWSER_INTERNET_EXPLORER_MAX = 49;

  public static final int OS_UNDETERMINED = 0;
  public static final int OS_WINNT = 1;
  public static final int OS_WIN9X = 2;
  public static final int OS_WIN2K = 3;
  public static final int OS_SUN = 4;
  public static final int OS_LINUX = 5;
  public static final int OS_MAC = 6;

  public static final String SESSION_COOKIE_NAME = "JSESSIONID=";



  /**
   * This used to wrap the URL encoding method in URLEncoder, an interface
   * which seems to get more and more complicated with each version of Java.
   * I've given up on URLEncoder, in fact, and written my own damn encoder.
   * Fuck you, Sun, for making me do this menial thingc because you couldn't
   * fathom character encodings, and so I've got to deal with browser that
   * can't fathom character encodings.
   * <br><br>
   * Also due to browser wimpiness, I can't call 'Character.toString(char)',
   * and such handy methods.  This whole class must be browser-safe.
   *
   * @param url a URL to be encoded
   * @return the URL-encoded equivalent
   */
  public static String URLEncode(String url)
    {
    if (url == null)
      return null;
    StringBuffer answer = new StringBuffer(url.length() << 1);
    char character = 0;
    int otherCharacter = 0;
    for (int i = 0; i < url.length(); i++)
      {
      character = url.charAt(i);
      if (((character >= 'a') && (character <= 'z'))
            || ((character >= 'A') && (character <= 'Z'))
            || ((character >= '0') && (character <= '9')))
        answer.append("" + character);
      else
        {
        answer.append("%");
        otherCharacter = (character >> 4) & 15;
        if (otherCharacter < 10)
          answer.append(otherCharacter);
        else
          answer.append((char)('A' + otherCharacter - 10));
        otherCharacter = character & 15;
        if (otherCharacter < 10)
          answer.append(otherCharacter);
        else
          answer.append((char)('A' + otherCharacter - 10));
        }
      }
    return answer.toString();
    }


  /**
   * Create and return a URL object for the given string form.  If the given string
   * is a malformed URL, this returns null.  This is bad.  Something should be done.
   *
   * @param url a string representing a URL
   * @return an actual URL object, or null if it can't be created
   */
  public static URL GetURL(String url)
    {
    URL urinal = null;
    try { urinal = new URL(url); }
    catch (MalformedURLException exception) { urinal = null; }
    return urinal;
    }


  /**
   * Create a URL based on the base URL of 'baseApplet', whose path is 'url'.
   * Return null if I 'url' is improperly formed.
   *
   * @param url a URL string, sans the server and protocol stuff
   * @param baseApplet an Applet whose base url is to be used to form the new URL
   * @return a new URL based on the given values
   */
  public static URL GetDocumentBasedURL(String url, Applet baseApplet)
    {
    URL urinal = null;
    try { urinal = new URL(baseApplet.getDocumentBase(), url); }
    catch (MalformedURLException exception) { urinal = null; }
    return urinal;
    }


  /**
   * Summon a Web document from the given Applet, either in its own window
   * or another, depending on 'target'.
   *
   * @param applet an Applet from which to spawn the new URL
   * @param url a string representing the URL to be called
   * @param target the name of the target window, or null if the applet's window
   *               should be used
   */
  public static void ShowDocument(Applet applet, String url, String target)
    {
    try
      {
      URL urinal = null;
      if (url.charAt(0) == '/')
        urinal = new URL(applet.getDocumentBase(), url);
      else
        {
        if (url.indexOf("://") == -1)
          url = "http://" + url;
        urinal = new URL(url);
        }
      if (target == null)
        applet.getAppletContext().showDocument(urinal);
      else
        applet.getAppletContext().showDocument(urinal, target);
      }
    catch (MalformedURLException exception)
      {
      MetaLog.Log("The URL " + url + "was poorly formed, it seems.", exception);
      }
    }


  /**
   * This is just here to remind myself of how to do this!
   *
   * @deprecated because it's so damn easy
   */
  public static Image GetImage(String url)
    {
    return Toolkit.getDefaultToolkit().getImage(url);
    }


  /**
   * This is just here to remind myself of how to do this!
   *
   * @deprecated because it's so damn easy
   */
  public static Image GetImage(URL url)
    {
    return Toolkit.getDefaultToolkit().getImage(url);
    }


  /**
   * Convert the given file path into one suitable for a URL.  Damn, this music
   * I'm listening to is annoying.  Usually this coffee shop plays cool 80's
   * tunes--my stuff.  But now I'm hearing some weird acid jazz trancy shit
   * with little variation and a heavy, frenetic high-hat line that just bugs
   * the hell out of me.
   *
   * @param url a file path to be made into a URL
   * @return the Webified form of the path
   */
  public static String WebifyFilePath(String url)
    {
    StringBuffer answer = new StringBuffer((int)(url.length() * 1.3));
    char character = 0;
    String escape = null;
    for (int i = 0; i < url.length(); i++)
      {
      character = url.charAt(i);
      if (Character.isLetterOrDigit(character) || (character == '/')
            || (character == '.') || (character == '-'))
        answer.append(character);
      else
        {
        answer.append("%");
        escape = Integer.toString((int)character, 16).toUpperCase();
        if (escape.length() == 1)
          answer.append("0");
        answer.append(escape);
        }
      }
    return answer.toString();
    }


  /**
   * Replace any characters in 'htmlString' which are special characters in HTML with their respective
   * HTML escape codes, returning the result.  This is useful for inserting strings of unknown content
   * into a Web page so that it will not violate the well-formedness or coherency of the surrounding HTML.
   *
   * @param htmlString any string
   * @return a new string with any special HTML characters escaped
   */
  public static String HTMLEscape(String htmlString)
    {
    if (htmlString == null)
      return "";
    StringBuffer answer = new StringBuffer(100);
    int lastIndex = 0;
    char lastChar = 0;
    for (int i = 0; i < htmlString.length(); i++)
      {
      switch (htmlString.charAt(i))
        {
        case '<':
          answer.append(htmlString.substring(lastIndex, i) + "&lt;");
          lastIndex = i + 1;
          break;

        case '>':
          answer.append(htmlString.substring(lastIndex, i) + "&gt;");
          lastIndex = i + 1;
          break;

        case '"':
          answer.append(htmlString.substring(lastIndex, i) + "&quot;");
          lastIndex = i + 1;
          break;

        case '&':
          answer.append(htmlString.substring(lastIndex, i) + "&amp;");
          lastIndex = i + 1;
          break;

        case 8212:
          answer.append(htmlString.substring(lastIndex, i) + "&mdash;");
          lastIndex = i + 1;
          break;

        case ' ':
          if (lastChar == ' ')
            {
            answer.append(htmlString.substring(lastIndex, i) + "&nbsp;");
            lastIndex = i + 1;
            }
          break;
        }
      }
    answer.append(htmlString.substring(lastIndex, htmlString.length()));
    return answer.toString();
    }


  /**
   * Replace any characters in 'htmlString' which are special characters in HTML with their respective
   * HTML escape codes, returning the result.  This is useful for inserting strings of unknown content
   * into a Web page so that it will not violate the well-formedness or coherency of the surrounding HTML.
   *
   * @param string any string
   * @return a new string with any special HTML characters escaped
   * @deprecated
   */
  public static String ToHTML(String text)
    {
    if (text == null)
      return "";
    StringBuffer answer = new StringBuffer(400);
    int lastIndex = 0;
    for (int i = 0; i < text.length(); i++)
      {
      switch (text.charAt(i))
        {
        case '\n':
          answer.append(text.substring(lastIndex, i) + "<br>");
          lastIndex = i + 1;
          break;
/*
        case '>':
          answer.append(htmlString.substring(lastIndex, i) + "&gt;");
          lastIndex = i + 1;
          break;

        case '"':
          answer.append(htmlString.substring(lastIndex, i) + "&quot;");
          lastIndex = i + 1;
          break;
*/
        }
      }
    answer.append(text.substring(lastIndex, text.length()));
    return answer.toString();
    }


  /**
   * Convert 'string' to a form suitable for use as a JavaScript string,
   * for an 'alert()' dialog statement.
   *
   * @param string a message to be displayed in a JavaScript alert dialog
   * @return a JavaScript-escaped form of the string
   */
  public static String AlertStringEscape(String string)
    {
    if (string == null)
      return "";
    StringBuffer answer = new StringBuffer(100);
    int lastIndex = 0;
    for (int i = 0; i < string.length(); i++)
      {
      switch (string.charAt(i))
        {
        case '\"':
          answer.append(string.substring(lastIndex, i) + "\\\"");
          lastIndex = i + 1;
          break;

        case '\n':
          answer.append(string.substring(lastIndex, i) + "\\n");
          lastIndex = i + 1;
          break;

        case '\'':
          answer.append(string.substring(lastIndex, i) + "`");
          lastIndex = i + 1;
          break;
        }
      }
    answer.append(string.substring(lastIndex, string.length()));
    return answer.toString();
    }


  /**
   * Return a brief string representing the Web browser described in 'browserSpec'.  'browserSpec'
   * should be a browser specification value for the HTTP header "USER-AGENT" that is sent by a browser
   * in an HTTP request.  The return value is something like "Nav. 4.08" for Navigator 4.08.  If the
   * browser specification cannot be interpreted, this returns "(unknown)".
   *
   * @param browserSpec a string describing a Web browser version
   * @return a brief string identifier for the specified browser
   */
  public static String GetShortBrowserName(String browserSpec)
    {
    if (browserSpec.equals("Mozilla/4.08 [en] (WinNT; U ;Nav)"))
      return "Nav. 4.08";
    else if (browserSpec.equals("Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)"))
      return "I.E. 5.01";
    else if (browserSpec.equals("Mozilla/4.0 (compatible; MSIE 5.5; Windows 98; Win 9x 4.90)"))
      return "I.E. 5.5";
    else
      return "(unknown)";
    }


  /**
   * Returns the id of the browser of the computer who generated the inputed
   * brower spec.
   *
   * @param browserSpec a string describing a Web browser version
   * @return the ID of the browser
   */
  public static int GetBrowserIDForBrowserSpec(String browserSpec)
    {
    if (browserSpec.indexOf("MSIE") != -1)
      {
      if (browserSpec.indexOf("MSIE 5.01") != -1)
        return BROWSER_INTERNET_EXPLORER_5_0_1;
      if (browserSpec.indexOf("MSIE 5.5") != -1)
        return BROWSER_INTERNET_EXPLORER_5_5;
      if (browserSpec.indexOf("MSIE 5") != -1)
        return BROWSER_INTERNET_EXPLORER_5_0;
      if (browserSpec.indexOf("MSIE 4.01") != -1)
        return BROWSER_INTERNET_EXPLORER_4_0_1;
      if (browserSpec.indexOf("MSIE 3.02") != -1)
        return BROWSER_INTERNET_EXPLORER_3_0_2;
      return BROWSER_INTERNET_EXPLORER;
      }
    else if (browserSpec.indexOf("Mozilla/") != -1)
      {
      if (browserSpec.indexOf("Mozilla/5.0") != -1)
        return BROWSER_NETSCAPE_5;
      if (browserSpec.indexOf("Mozilla/4.61") != -1)
        return BROWSER_NETSCAPE_4_6_1;
      if (browserSpec.indexOf("Mozilla/4.0") != -1)
        return BROWSER_NETSCAPE_4_0;
      if (browserSpec.indexOf("Mozilla/4.08") != -1)
        return BROWSER_NETSCAPE_4_8;
      if (browserSpec.indexOf("Mozilla/3.0.1") != -1)
        return BROWSER_NETSCAPE_3_0_1;
      else
        return BROWSER_NETSCAPE;
      }
    else
      return BROWSER_UNDETERMINED;
    }


  /**
   * Returns the id of the operating system of the computer who generated the inputed
   * brower spec.
   *
   * @param browserSpec a string describing a Web browser version
   * @return the ID of the OS
   */
  public static int GetOSIDForBrowserSpec(String browserSpec)
    {
    if ((browserSpec.indexOf("WinNT") != -1)
        || (browserSpec.indexOf("Windows NT") != -1))
      return OS_WINNT;
    else if (browserSpec.indexOf("Win 9x") != -1)
      return OS_WIN9X;
    else if (browserSpec.indexOf("SunOS") != -1)
      return OS_SUN;
    else
      return OS_UNDETERMINED;
    }


  /**
   * Append a trailing forward slash to 'path' if it hasn't one already, and return the result.
   *
   * @param path a URL which may or may not have an ending "/"
   * @return the path with an ending forward slash
   */
  public static String AddTrailingSlashAsNeeded(String path)
    {
    return (!path.endsWith("/")) ? (path + '/') : path;
    }


  /**
   * Return append a name/value pair to a given URL, 'url', using "?" if there are
   * no other parameters in the query string or "&" if there are other parameters.
   * URL encode 'value' before appending it.
   *
   * @param url the URL string to which the parameter is to be appended
   * @param name the name of the parameter
   * @param value the parameter's value (not yet URL encoded)
   * @return the URL with the specified name/value appended
   */
  public static String AppendParameterToURL(String url, String name, String value)
    {
    String anchor = "";
    int index = url.indexOf("#");
    if (index != -1)
      {
      anchor = url.substring(index);
      url = url.substring(0, index);
      }
    StringBuffer answer = new StringBuffer(url);
    if (url.indexOf("?") != -1)
      answer.append("&");
    else
      answer.append("?");
    answer.append(name);
    answer.append("=");
    answer.append(URLEncode(value));
    answer.append(anchor);
    return answer.toString();
    }


  /**
   * Return the servlet session cookie from the URLConnection object.
   * <br><br>
   * Note:  this may be specific to just Resin, because of the cookie name.
   *
   * @param connection a URLConnection from which to divine the session cookie
   * @return the sesson cookie
   */
  public static String GetSessionCookie(URLConnection connection)
    {
    String cookieHeader = connection.getHeaderField("Set-Cookie");
    int startIndex = cookieHeader.indexOf(SESSION_COOKIE_NAME);
    if (startIndex != -1)
      {
      startIndex += SESSION_COOKIE_NAME.length();
      int endIndex = cookieHeader.indexOf(';', startIndex);
      if (endIndex != -1)
        return cookieHeader.substring(startIndex, endIndex);
      else
        return cookieHeader.substring(startIndex);
      }
    return null;
    }


  /**
   * Set a session cookie on the given URLConnection, as gathered using
   * 'GetSessionCookie()'.
   * <br><br>
   * Note:  this too may be specific to just Resin.  Note also that this
   * wipes out all other cookies.
   *
   * @param connection the URLConnection on which to set the cookie
   * @param value the cookie value
   */
  public static void SetSessionCookie(URLConnection connection, String value)
    {
    if (value != null)
      connection.setRequestProperty("Set-Cookie", SESSION_COOKIE_NAME + '=' + value);
    }

  }
