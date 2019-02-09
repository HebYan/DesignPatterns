package com.elseforif.utility;

import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * This class provides some basic string manipulation methods, along with some
 * useful string constants.  For testing operations, see package
 * com.elseforif.utility.validation.
 */
public final class String1 extends Object
  {

  public static final String[] SP = {
        "",
        " ",
        "  ",
        "   ",
        "    ",
        "     ",
        "      ",
        "       ",
        "        ",
        "         ",
        "          ",
        "           ",
        "            ",
        "             ",
        "              ",
        "               ",
        "                ",
        "                 ",
        "                  ",
        "                   ",
        "                    ",
        "                     " };
  public static final String[] NL = {
        "",
        "\n",
        "\n\n",
        "\n\n\n",
        "\n\n\n\n",
        "\n\n\n\n\n",
        "\n\n\n\n\n\n" };



  /**
   * Replace all occurences of 'from' in string 'value' with 'to', and return the result.
   *
   * @param value the original string
   * @param from the substring to be replaced
   * @param to the new value to be substituted for 'from'
   * @return the new string
   */
  public static String Replace(String value, String from, String to)
    {
    StringBuffer answer = new StringBuffer(400);
    int oldIndex = 0;
    int index = value.indexOf(from);
    int fromLength = from.length();
    while (index != -1)
      {
      answer.append(value.substring(oldIndex, index));
      answer.append(to);
      oldIndex = index + fromLength;
      index = value.indexOf(from, oldIndex);
      }
    if (oldIndex < value.length())
      answer.append(value.substring(oldIndex));
    return answer.toString();
    }


  /**
   * Remove any non-alphabetic characters from 'string'.
   *
   * @param string any string
   * @return only the letters found in 'string'
   */
  public static String Alphabetize(String string)
    {
    StringBuffer answer = new StringBuffer(string.length());
    for (int i = 0; i < string.length(); i++)
      if (Character.isLetter(string.charAt(i)))
        answer.append(string.charAt(i));
    return answer.toString();
    }


  /**
   * Remove any character from 'string' which is not alphanumeric.
   *
   * @param string any ol' string
   * @return only the alphanumeric characters in 'string'
   */
  public static String AlphanumericsOnly(String string)
    {
    StringBuffer answer = new StringBuffer(string.length());
    for (int i = 0; i < string.length(); i++)
      if (Character.isLetterOrDigit(string.charAt(i)))
        answer.append(string.charAt(i));
    return answer.toString();
    }


  /**
   * Remove any nondigit from 'string'.
   *
   * @param string a string, preferably containing a number
   * @return just the digits, please
   */
  public static String Digitize(String string)
    {
    if (string == null)
      return null;
    StringBuffer answer = new StringBuffer(string);
    for (int i = 0; i < answer.length(); i++)
      if ((answer.charAt(i) < '0') || (answer.charAt(i) > '9'))
        answer.deleteCharAt(i--);
    return answer.toString();
    }


  /**
   * Return only the characters in 'numberString' which form a number.
   * This can be nagative and can also contain a decimal.  Any but the
   * first decimal are discarded.
   *
   * @param numberString a string probably containing a number
   * @return the number portion of the string
   */
  public static String NumberOnly(String numberString)
    {
    String answer = "";
    boolean hyphenFound = false;
    boolean dotFound = false;
    char character;

    for (int i = 0; i < numberString.length(); i++)
      {
      character = numberString.charAt(i);
      if ((character >= '0') && (character <= '9'))
        answer += character;
      else if ((character == '.') && !dotFound)
        {
        answer += '.';
        dotFound = true;
        }
      else if ((character == '-') && !hyphenFound)
        {
        answer = '-' + answer;
        hyphenFound = true;
        }
      }
    return answer;
    }


  /**
   * Insert the character '\' before each '"' found in 'string'.  Or in other words,
   * replace all occurrences of """ with "\"", so that the resulting string can be delimited
   * in quotes without the risk of syntax errors.
   *
   * @param string any string
   * @return a new string with all double-quotes escaped
   */
  public static String StringEscape(String string)
    {
    return Replace(string, "\"", "\\\"");
    }


  /**
   * @deprecated see class DatabaseUtility instead
   */
  public static String SafenSQL(String text)
    {
    return Replace(text, "'", "''");
    }


  /**
   * Repeat 'string' 'repeatTimes' number of times, and return the result.
   *
   * @param string a string to be repeated
   * @param repeatTimes the number of times 'string' is to be repeated
   * @return the complete resulting string
   */
  public static String Repeat(String string, int repeatTimes)
    {
    if (repeatTimes < 1)
      return "";
    StringBuffer repeated = new StringBuffer(string.length() * repeatTimes);
    for (int i = 0; i < repeatTimes; i++)
      repeated.append(string);
    return repeated.toString();
    }


  /**
   * Repeat the given character the specified number of times, returning the result.
   *
   * @param character a character to be repeated
   * @param repeatTimes the number of repeats
   * @return the resulting string
   */
  public static String Repeat(char character, int repeatTimes)
    {
    if (repeatTimes < 1)
      return "";
    StringBuffer repeated = new StringBuffer(repeatTimes);
    for (int i = 0; i < repeatTimes; i++)
      repeated.append(character);
    return repeated.toString();
    }


  /**
   * Return a string representation of 'exception', including its stack trace.
   *
   * @param exception an excetpion to be made into a string
   * @return a string form of the exception
   */
  public static String ToString(Exception exception)
    {
    StringWriter string = new StringWriter(1000);
    PrintWriter writer = new PrintWriter(string);
    exception.printStackTrace(writer);
    writer.close();
    return exception.toString() + "\n\n" + string.toString();
    }

  }
