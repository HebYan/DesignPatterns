package com.elseforif.servlet.utility;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This encapsulates a CSS style sheet, storing style in a map of maps, which
 * allows for any number of styles, each containing any number of attributes.
 * <br><br>
 * Attributes can be added singly or multiply (in semicolon-separated groups,
 * which are parsed apart) using the same 'putAttributes()' method, which
 * adds styles implicitly.  This is a trifle ambiguous, and it's less than
 * perfectly efficient, since parsing is done even when only one attribute
 * is passed.  But it's no buggier than having two separate methods, and
 * speed is not important, so I'm keeping it this way for now.
 */
public class StyleSheet extends Hashtable
  {

  /**
   * Add one or more attributes to the named style (creating its map as needed).
   * Multiple styles in 'attributes' should be separated by semicolons.
   *
   * @param styleName the name of the style
   * @param attributes any attributes for the style
   */
  public void putAttributes(String styleName, String attributes)
    {
    String attribute = null;
    String value = null;
    Hashtable style = (Hashtable)(get(styleName));
    if (style == null)
      {
      style = new Hashtable(5);
      put(styleName, style);
      }
    int oldIndex = 0;
    int index = 0;
    while (oldIndex < attributes.length())
      {
      index = attributes.indexOf(';', oldIndex);
      if (index == -1)
        index = attributes.length();
      value = attributes.substring(oldIndex, index);
      oldIndex = value.indexOf(':');
      if (oldIndex != -1)
        {
        attribute = value.substring(0, oldIndex).trim();
        value = value.substring(oldIndex + 1).trim();
        if ((attribute.length() > 0) && (value.length() > 0))
          style.put(attribute, value);
        }
      oldIndex = index + 1;
      }
    if (style.isEmpty())
      remove(styleName);
    }


  /**
   * Return all my styles in a CSS format, suitable to become a .css file or
   * for inclusion in an HTML page between a pair of STYLE tags.
   *
   * @return all my style definitions in a CSS format
   */
  public String output()
    {
    String answer = "";
    Enumeration styles = keys();
    String styleName = null;
    Hashtable style = null;
    Enumeration attributes = null;
    String attributeName = null;
    while (styles.hasMoreElements())
      {
      styleName = (String)(styles.nextElement());
      if (answer.length() > 0)
        answer += "\n\n\n";
      answer += styleName + "\n  {";
      style = (Hashtable)(get(styleName));
      attributes = style.keys();
      while (attributes.hasMoreElements())
        {
        attributeName = (String)(attributes.nextElement());
        answer += "\n  " + attributeName + ": " + style.get(attributeName) + ';';
        }
      answer += "\n  }";
      }
    return answer;
    }

  }
