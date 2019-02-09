package com.elseforif.servlet.utility;

import com.elseforif.entity.net.WebImage;
import java.io.StringWriter;
import java.io.Writer;
import java.io.IOException;

/**
 * This class wraps the 'print...()' methods in HTMLWriter with methods
 * returning strings, rather than writing them directly to the output.
 * This is desirable for a few reasons.  Foremost, it's cleaner to create
 * a substring and pass it as a parameter to another method.  For example,
 * if I want to output a link tag containing an image, I could call 'printA()'
 * in the superclass, passing in the string returned from 'IMG()' in this
 * class, with a single line of code, like this:
 * <pre>out.printA("/some-url", out.IMG("/image/linkImage.gif"));</pre>
 * Another reason is so that strings can be constructed and cached where
 * expedient, such a group of CSS or JavaScript file links to be inserted
 * in the HEAD tags of a set of pages.
 * <br/><br/>
 * This class works by dint of a StringWriter, which it swaps in as the
 * underlying Writer object defined in HTMLWriter, temporarily, then calls
 * a corresponding 'print...()' method in the superclass, which then prints
 * to the StringWriter.  Before returning the string result, it replaces the
 * original Writer, so that calls to 'print...()' will work normally.
 * Obviously this object is not threadsafe, but neither is the superclass.
 */
public class HTMLFlexiWriter extends HTMLWriter
  {

  private StringWriter      m_stringWriter = null;
  private StringBuffer      m_stringBuffer = null;
  private Writer            m_tempWriter = null;



  /**
   * Construct an HTMLFlexiWriter wrapper for Writer 'output'.
   *
   * @param output the Writer I will use for 'write...()' calls.
   */
  public HTMLFlexiWriter(Writer output)
    {
    this(output, null);
    }


  /**
   * Construct an HTMLFlexiWriter wrapper for Writer 'output'.
   * Prepend 'contextPrefix' to any local URLs beginning with "/".
   *
   * @param output the Writer I will use for 'write...()' calls.
   * @param contextPrefix my local URL prefix.
   */
  public HTMLFlexiWriter(Writer output, String contextPrefix)
    {
    super(output, contextPrefix);
    m_stringWriter = new StringWriter();
    m_stringBuffer = m_stringWriter.getBuffer();
    }


  /**
   * Prepare to print to my StringWriter, in order to return the result as
   * a string.  Clear the string buffer, so that previous strings will not
   * be included.
   */
  protected void writeToString()
    {
    m_stringBuffer.setLength(0);
    m_tempWriter = m_output;
    m_output = m_stringWriter;
    }


  /**
   * Replace the original Writer object for further printing to the output,
   * return whatever string was produced since the previous call to
   * 'writeToString()'.
   *
   * @return the string generated after 'writeToString()' was last called
   */
  protected String getString()
    {
    m_output = m_tempWriter;
    return m_stringBuffer.toString();
    }


  /**
   * Return an invisible, named link tag (with a "name" attribute instead of "href").
   *
   * @param name the name index for this page link
   * @return HTML output for a named A tag
   */
  public String A(String name) throws IOException
    {
    writeToString();
    printA(name);
    return getString();
    }


  /**
   * Return a custom link tag.
   *
   * @param url the URL for the link
   * @param link the HTML output to be the active link on the page
   * @return HTML output for an A tag
   */
  public String A(String url, String link) throws IOException
    {
    writeToString();
    printA(url, link);
    return getString();
    }


  /**
   * Return a custom link tag.
   *
   * @param url the URL for the link
   * @param link the HTML output to be the active link on the page
   * @param style a style to be used
   * @return HTML output for an A tag
   */
  public String A(String url, String link, String style) throws IOException
    {
    writeToString();
    printA(url, link, style);
    return getString();
    }


  /**
   * Return a custom link tag.
   *
   * @param url the URL for the link
   * @param link the HTML output to be the active link on the page
   * @param style a style to be used
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @return HTML output for an A tag
   */
  public String A(String url, String link, String style, String attributes) throws IOException
    {
    writeToString();
    printA(url, link, style, attributes);
    return getString();
    }


  /**
   * Return a complete JavaScript script producing a pop-up message dialog whose text is 'message'.
   *
   * @param message the text to be displayed in the dialog
   * @return HTML output for a JavaScript script
   */
  public String alertSCRIPT(String message) throws IOException
    {
    writeToString();
    printAlertSCRIPT(message);
    return getString();
    }


  /**
   * Return an APPLET tag pair having the specified attributes.
   *
   * @param appletName a name for the applet, or null
   * @param properClassName the full name of the class, sans the ".class" part
                            (since that is not properly part of the class name)
   * @param width the value of the WIDTH attribute
   * @param height the value of the HEIGHT attribute
   * @return HTML output for an APPLET tag pair
   */
  public String APPLET(String appletName, String properClassName, String width, String height) throws IOException
    {
    writeToString();
    printAPPLET(appletName, properClassName, width, height);
    return getString();
    }


  /**
   * Return an AREA tag for defining an image map.  Call this multiply after
   * 'MAP()', and then remember to add the END_MAP tag.
   *
   * @param top the top coordinate
   * @param left the left-hand coordinate
   * @param bottom the bottom coordinate
   * @param right the right-hand coordinate
   * @param uri the URI to which the area points
   * @param altText and optional ALT text value
   * @return an AREA tag for an image MAP
   */
  public String AREA(int left, int top, int right, int bottom,
                     String uri, String altText) throws IOException
    {
    writeToString();
    printAREA(left, top, right, bottom, uri, altText);
    return getString();
    }


  /**
   * Return an opening APPLET tag having the specified attributes.
   *
   * @param appletName a name for the applet, or null
   * @param properClassName the full name of the class, sans the ".class" part
                            (since that is not properly part of the class name)
   * @param width the value of the WIDTH attribute
   * @param height the value of the HEIGHT attribute
   * @return HTML output for an opening APPLET tag
   */
  public String beginAPPLET(String appletName, String properClassName, String width, String height) throws IOException
    {
    writeToString();
    printBeginAPPLET(appletName, properClassName, width, height);
    return getString();
    }


  /**
   * Return an opening APPLET tag having the specified attributes.
   *
   * @param appletName a name for the applet, or null
   * @param properClassName the full name of the class, sans the ".class" part
                            (since that is not properly part of the class name)
   * @param width the value of the WIDTH attribute
   * @param height the value of the HEIGHT attribute
   * @param archive the archive for the applet, instead of a codebase
   * @return HTML output for an opening APPLET tag
   */
  public String beginAPPLET(String appletName, String properClassName,
                            String width, String height, String archive) throws IOException
    {
    writeToString();
    printBeginAPPLET(appletName, properClassName, width, height, archive);
    return getString();
    }


  /**
   * Return a custom TEXTAREA begin tag
   *
   * @param name the name of the input
   * @param rows the number of rows in the textarea
   * @param cols the number of columns in the textarea
   * @return HTML output for a custom textarea
   */
  public String beginTEXTAREA(String name, int rows, int cols) throws IOException
    {
    writeToString();
    printBeginTEXTAREA(name, rows, cols);
    return getString();
    }


  /**
   * Return an IMG link to a single-pixel, black GIF image.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
   * @return HTML output for a black image
  public String blackpixelIMG(int width, int height) throws IOException
    {
    writeToString();
    printBlackpixelIMG(width, height);
    return getString();
    }
   */


  /**
   * Return a BLOCKQUOTE tag with the given values.
   *
   * @param attributes extra attributes to add into the brew
   * @param style a CSS class to use
   * @return HTML output for a BLOCKQUOTE begin tag
   */
  public String beginBLOCKQUOTE(String attributes, String style) throws IOException
    {
    writeToString();
    printBeginBLOCKQUOTE(attributes, style);
    return getString();
    }


  /**
   * Return a BODY tag having the given attributes.
   *
   * @param image a value for the BACKGROUND attribute, or null
   * @param color a value for the BGCOLOR attribute, or null
   * @return HTML output for a BODY tag
   */
  public String beginBODY(String image, String color) throws IOException
    {
    writeToString();
    printBeginBODY(image, color);
    return getString();
    }


  /**
   * Return a BODY tag having the given attributes.
   *
   * @param image a value for the BACKGROUND attribute, or null
   * @param color a value for the BGCOLOR attribute, or null
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @return HTML output for a BODY tag
   */
  public String beginBODY(String image, String color, String attributes) throws IOException
    {
    writeToString();
    printBeginBODY(image, color, attributes);
    return getString();
    }


  /**
   * Return an INPUT tag of type CHECKBOX.
   *
   * @param name the name of the value of the checkbox
   * @return HTML output for a CHECKBOX INPUT tag
   */
  public String CHECKBOX(String name) throws IOException
    {
    writeToString();
    printCHECKBOX(name);
    return getString();
    }


  /**
   * Return an INPUT tag of type CHECKBOX.
   *
   * @param name the name of the value of the checkbox
   * @param value the value for the checkbox
   * @return HTML output for a CHECKBOX INPUT tag
   */
  public String CHECKBOX(String name, String value) throws IOException
    {
    writeToString();
    printCHECKBOX(name, value);
    return getString();
    }


  /**
   * Return an INPUT tag of type CHECKBOX, preselected according to 'selected'.
   *
   * @param name the name of the value of the checkbox
   * @param selected whether the checkbox should be selected initially
   * @return HTML output for a CHECKBOX INPUT tag
   */
  public String CHECKBOX(String name, boolean selected) throws IOException
    {
    writeToString();
    printCHECKBOX(name, selected);
    return getString();
    }


  /**
   * Return an INPUT tag of type CHECKBOX, preselected according to 'selected'.
   *
   * @param name the name of the value of the checkbox
   * @param value the value for the checkbox
   * @param selected whether the checkbox should be selected initially
   * @return HTML output for a CHECKBOX INPUT tag
   */
  public String CHECKBOX(String name, String value, boolean selected) throws IOException
    {
    writeToString();
    printCHECKBOX(name, value, selected);
    return getString();
    }


  /**
   * Return 'value' itself if it is not null, or an empty string if it is.
   *
   * @param value any string or null
   * @return 'value' itself if it is not null, or an empty string if it is
   */
  public String displayNull(String value) throws IOException
    {
    writeToString();
    printDisplayNull(value);
    return getString();
    }


  /**
   * Return an opening DIV tag with the given style.
   *
   * @param style a style for the DIV
   * @return an opening DIV tag
   */
  public String beginDIV(String style) throws IOException
    {
    writeToString();
    printBeginDIV(style);
    return getString();
    }


  /**
   * Return a DIV tag whose style is 'style' and having any extra attribute in 'attributes'.
   *
   * @param style an optional CSS style
   * @param attributes any extra attributes for the DIV tag
   */
  public String beginDIV(String style, String attributes) throws IOException
    {
    writeToString();
    printBeginDIV(style, attributes);
    return getString();
    }


  /**
   * Return a DIV tag whose style is 'style'.  Include the given text, and add
   * an end tag.
   *
   * @param style an optional CSS style
   * @param text the HTML to be the DIV's contents
   */
  public String DIV(String style, String text) throws IOException
    {
    writeToString();
    printDIV(style, text);
    return getString();
    }


  /**
   * Return a DIV tag whose style is 'style' and having any extra attribute
   * in 'attributes'.  Include the given text, and add an end tag.
   *
   * @param style an optional CSS style
   * @param text the HTML to be the DIV's contents
   * @param attributes any extra attributes for the DIV tag
   */
  public String DIV(String style, String text, String attributes) throws IOException
    {
    writeToString();
    printDIV(style, text, attributes);
    return getString();
    }


  /**
   * Return the contents of the specified file, assumed ASCII.  If the file
   * cannot be read, return an error message.
   *
   * @param path the path to the file to be output
   * @return the contents of the text file
   */
  public String file(String path) throws IOException
    {
    writeToString();
    printFile(path);
    return getString();
    }


  /**
   * Return a form input tag of type "FILE", for uploading files.
   *
   * @param name the name of the input
   * @return HTML output for a file upload INPUT tag
   */
  public String FILEInput(String name) throws IOException
    {
    writeToString();
    printFILEInput(name);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @return HTML output for a custom text INPUT
   */
  public String FILEInput(String name, int size, int maxSize,
                          String attributes, int tabIndex) throws IOException
    {
    writeToString();
    printFILEInput(name, size, maxSize, attributes, tabIndex);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @return HTML output for a custom text INPUT
   */
  public String FILEInput(String name, int size, int maxSize, String value,
                          String attributes, int tabIndex) throws IOException
    {
    writeToString();
    printFILEInput(name, size, maxSize, value, attributes, tabIndex);
    return getString();
    }


  /**
   * Return a FORM tag with NAME="form1" having the specified action and using the GET method.
   *
   * @param url the value of the ACTION attribute
   * @return HTML output for a FORM tag
   */
  public String beginFORM(String url) throws IOException
    {
    writeToString();
    printBeginFORM(url);
    return getString();
    }


  /**
   * Return a custom FORM tag.
   *
   * @param name the name of the form
   * @param getNotPost whether GET should be used or POST
   * @param url the target URL for the form
   * @return HTML output for a custom FORM tag
   */
  public String beginFORM(String name, boolean getNotPost, String url) throws IOException
    {
    writeToString();
    printBeginFORM(name, getNotPost, url);
    return getString();
    }


  /**
   * Return a custom FORM tag.
   *
   * @param name the name of the form
   * @param getNotPost whether GET should be used or POST
   * @param url the target URL for the form
   * @param onSubmit a value for the onSubmit attribute (typically of the form:  'return validate();')
   * @return HTML output for a custom FORM tag
   */
  public String beginFORM(String name, boolean getNotPost,
                     String url, String onSubmit) throws IOException
    {
    writeToString();
    printBeginFORM(name, getNotPost, url, onSubmit);
    return getString();
    }


  /**
   * Return a little SCRIPT tag pair for focusing a particular form field
   * immediately.
   *
   * @param formName the name of the form whose field is to be focused
   * @param fieldName the name of the form field to focus
   */
  public String formFocusSCRIPT(String formName, String fieldName) throws IOException
    {
    writeToString();
    printFormFocusSCRIPT(formName, fieldName);
    return getString();
    }


  /**
   * Return a custom FRAME tag for a frameset.
   *
   * @param name a name for the frame
   * @param url the URL of the frame's page
   * @param noresize whether the NORESIZE attribute should be inserted
   * @param scrolling a value for the SCROLLING attribute
   * @return HTML output for a FRAME tag
   */
  public String FRAME(String name, String url, boolean noresize,
                      String scrolling) throws IOException
    {
    writeToString();
    printFRAME(name, url, noresize, scrolling);
    return getString();
    }


  /**
   * Return a FRAMESET tag having the specified properties.
   *
   * @param columnsNotRows whether the frameset is to be divided by columns or rows
   * @param division the specification of columns or rows, as represented in the value of attributes COLS and ROWs
   * @return HTML output for a FRAMESET tag
   */
  public String beginFRAMESET(boolean columnsNotRows, String division) throws IOException
    {
    writeToString();
    printBeginFRAMESET(columnsNotRows, division);
    return getString();
    }


  /**
   * Return an H tag pair having the specified properties.
   *
   * @param level the heading level (where 1 denotes an H1 tag, and so on)
   * @param heading the HTML to be inserted between the tags
   * @return HTML output for an H tag pair
   */
  public String H(int level, String heading) throws IOException
    {
    writeToString();
    printH(level, heading);
    return getString();
    }


  /**
   * Return an H tag pair having the specified properties.
   *
   * @param level the heading level (where 1 denotes an H1 tag, and so on)
   * @param heading the HTML to be inserted between the tags
   * @param attributes any extra attributes for this heading
   * @return HTML output for an H tag pair
   */
  public String H(int level, String heading, String attributes) throws IOException
    {
    writeToString();
    printH(level, heading, attributes);
    return getString();
    }


  /**
   * Return a custom HEAD tag pair.
   *
   * @param title a page title
   * @return a complete HEAD tag pair
   */
  public String HEAD(String title) throws IOException
    {
    writeToString();
    printHEAD(title);
    return getString();
    }


  /**
   * Return a custom HEAD tag pair, including any contained tags in 'tags'.
   * 'tags' can be a style sheet LINK tag, for example.
   *
   * @param title a page title
   * @param tags any additional tags to go within the HEAD tags, or null
   * @return a complete HEAD tag pair
   */
  public String HEAD(String title, String tags) throws IOException
    {
    writeToString();
    printHEAD(title, tags);
    return getString();
    }


  /**
   * Return a hidden INPUT tag.
   *
   * @param name the name of the input
   * @param value a value for the VALUE attribute, or null
   * @return HTML outpu for a hidden INPUT tag
   */
  public String HIDDENInput(String name, String value) throws IOException
    {
    writeToString();
    printHIDDENInput(name, value);
    return getString();
    }


  /**
   * Return an HR tag having the speicifed width.
   *
   * @param width a value for the WIDTH attribute
   * @return HTML output for an HR tag
   */
  public String HR(String width) throws IOException
    {
    writeToString();
    printHR(width);
    return getString();
    }


  /**
   * Return a custom HR tag.
   *
   * @param width a value for the WIDTH attribute
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @return HTML output for an HR tag
   */
  public String HR(String width, String attributes) throws IOException
    {
    writeToString();
    printHR(width, attributes);
    return getString();
    }


  /**
   * Return 'htmlString' with any special HTML characters escaped in order
   * to keep the surrounding HTML well formed.  This replaces greater-than and less-than
   * symbols, etc. with their HTML escaped counterparts (i.e., "&gt;" and "&lt;").
   *
   * @param htmlString a raw string to be formatted for HTML output
   * @return the HTML escaped value
   */
  public String HTMLEscape(String htmlString) throws IOException
    {
    writeToString();
    printHTMLEscape(htmlString);
    return getString();
    }


  /**
   * Return a "fav icon" LINK tag, to display an icon in the location bar.
   * (This is normally a 16x16 ICO file.)
   *
   * @param url the URL for the icon image
   * @return HTML for a favorite-icon LINK tag
   */
  public String iconLINK(String url) throws IOException
    {
    writeToString();
    printIconLINK(url);
    return getString();
    }


  /**
   * Return an IMG link tag.
   *
   * @param url the URL of the image file
   * @return HTML output for an IMG tag
   */
  public String IMG(String url) throws IOException
    {
    writeToString();
    printIMG(url);
    return getString();
    }


  /**
   * Return a custom IMG link tag.
   *
   * @param url the URL of the image file
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @return HTML output for an IMG tag
   */
  public String IMG(String url, String attributes) throws IOException
    {
    writeToString();
    printIMG(url, attributes);
    return getString();
    }


  /**
   * Return a custom IMG link tag.
   *
   * @param url the URL of the image file
   * @param width the width of the image in pixels, or 0 for unspecified
   * @param height the height of the image in pixels, or 0 for unspecified
   * @return HTML output for an IMG tag
   */
  public String IMG(String url, int width, int height) throws IOException
    {
    writeToString();
    printIMG(url, width, height);
    return getString();
    }


  /**
   * Return a custom IMG link tag.
   *
   * @param image a WebImage to be outputted
   * @return HTML output for an IMG tag
   */
  public String IMG(WebImage image) throws IOException
    {
    writeToString();
    printIMG(image);
    return getString();
    }


  /**
   * Return a custom IMG link tag.
   *
   * @param image a WebImage to be outputted
   * @param attributes additional attributes to be included
   * @return HTML output for an IMG tag
   */
  public String IMG(WebImage image, String attributes) throws IOException
    {
    writeToString();
    printIMG(image, attributes);
    return getString();
    }


  /**
   * Return a custom IMG link tag.
   * <br><br>
   * Note:  HTML 4 requires an "alt" attribute for every image; so if an
   * 'attributes' value is passed, it should contain the 'alt' attribute.
   *
   * @param url the URL of the image file
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @param width the width of the image in pixels, or 0 for unspecified
   * @param height the height of the image in pixels, or 0 for unspecified
   * @return HTML output for an IMG tag
   */
  public String IMG(String url, String attributes, int width, int height) throws IOException
    {
    writeToString();
    printIMG(url, attributes, width, height);
    return getString();
    }


  /**
   * Return a custom INPUT tag.
   *
   * @param name the name of the input
   * @param type the type of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @return HTML output for a custom text INPUT
   */
  public String INPUT(String name, String type, int size, int maxSize,
                      String value, String attributes, int tabIndex) throws IOException
    {
    writeToString();
    printINPUT(name, type, size, maxSize, value, attributes, tabIndex);
    return getString();
    }


  /**
   * Return an open MAP tag with name.  After this, 'AREA()' can be called
   * multiply to define the  areas of the image map.  Then add an 'END_MAP'.
   *
   * @param name the image MAP
   * @return a MAP tag
   */
  public String beginMAP(String name) throws IOException
    {
    writeToString();
    printBeginMAP(name);
    return getString();
    }


  /**
   * Iff 'maybe' is not null, return it.  Else return a safe empty string.
   *
   * @param maybe a value to output if it is not null
   */
  public String maybe(String maybe) throws IOException
    {
    writeToString();
    printMaybe(maybe);
    return getString();
    }


  /**
   * Iff 'maybe' is not null, return it.  Else return a safe empty string.
   *
   * @param maybe a value to output if it is not null
   * @param style an optional CSS style to use with an enclosing SPAN pair
   */
  public String maybe(String maybe, String style) throws IOException
    {
    writeToString();
    printMaybe(maybe, style);
    return getString();
    }


  /**
   * Iff 'maybe' is not null, return all non-null parameters in order.
   *
   * @param prefix a value to prepend to the output if 'maybe' is not null
   * @param maybe a value to output if it is not null
   * @param suffix a value to append to the output if 'maybe' is not null
   */
  public String maybe(String prefix, String maybe, String suffix) throws IOException
    {
    writeToString();
    printMaybe(prefix, maybe, suffix);
    return getString();
    }


  /**
   * Iff 'maybe' is not null, return all non-null parameters in order.
   *
   * @param prefix a value to prepend to the output if 'maybe' is not null
   * @param maybe a value to output if it is not null
   * @param suffix a value to append to the output if 'maybe' is not null
   * @param style an optional CSS style to use with an enclosing SPAN pair
   */
  public String maybe(String prefix, String maybe, String suffix, String style) throws IOException
    {
    writeToString();
    printMaybe(prefix, maybe, suffix, style);
    return getString();
    }


  /**
   * Iff 'maybe' is not null, return all non-null parameters in order.
   *
   * @param prefix a value to prepend to the output if 'maybe' is not null
   * @param maybe a value to output if it is not null
   * @param suffix a value to append to the output if 'maybe' is not null
   * @param maxCharactersPerLine wrap lines after this many characters
   */
  public String maybe(String prefix, String maybe, String suffix, int maxCharactersPerLine) throws IOException
    {
    writeToString();
    printMaybe(prefix, maybe, suffix, maxCharactersPerLine);
    return getString();
    }


  /**
   * Iff 'maybe' is not null, return all non-null parameters in order.
   * If 'maxCharactersPerLine' is positive, insert BR tags into 'maybe'
   * at no more than so many characters' distance, to achieve line wrapping.
   *
   * @param prefix a value to prepend to the output if 'maybe' is not null
   * @param maybe a value to output if it is not null
   * @param suffix a value to append to the output if 'maybe' is not null
   * @param style an optional CSS style to use with an enclosing SPAN pair
   * @param maxCharactersPerLine wrap lines after this many characters
   */
  public String maybe(String prefix, String maybe, String suffix, String style, int maxCharactersPerLine) throws IOException
    {
    writeToString();
    printMaybe(prefix, maybe, suffix, style, maxCharactersPerLine);
    return getString();
    }


  /**
   * Return a META tag with the given name and value.
   *
   * @param name the meta-tag name
   * @param value the meta-tag value
   * @return a META tag
   */
  public String METATag(String name, String value) throws IOException
    {
    writeToString();
    printMETATag(name, value);
    return getString();
    }


  /**
   * Return a pair of script tags enclosing a JavaScript command to open a new window of the specified properties.
   *
   * @param url the target document for the new window
   * @param windowName an HTML name for the window
   * @param widthPart the fraction of the total screen width to be used as the window's width (0 to 1)
   * @param heightPart the fraction of the total screen height to be used as the window's height (0 to 1)
   * @return a JavaScript script to open a page in a new window
   */
  public String newWindowSCRIPT(String url, String windowName,
                                float widthPart, float heightPart) throws IOException
    {
    writeToString();
    printNewWindowSCRIPT(url, windowName, widthPart, heightPart);
    return getString();
    }


  /**
   * Return a pair of script tags enclosing a JavaScript command to open a new window of the specified properties.
   *
   * @param url the target document for the new window
   * @param windowName an HTML name for the window
   * @param width a string to be inserted as the "width" attribute of the new window
   * @param height a string to be inserted as the "height" attribute of the new window
   * @return a JavaScript script to open a page in a new window
   */
  public String newWindowSCRIPT(String url, String windowName,
                                String width, String height) throws IOException
    {
    writeToString();
    printNewWindowSCRIPT(url, windowName, width, height);
    return getString();
    }


  /**
   * Return 'value' itself if it is not null, or an empty string if it is.
   *
   * @param value any string or null
   * @return 'value' itself if it is not null, or an empty string if it is
   */
  public String noNull(String value) throws IOException
    {
    writeToString();
    printNoNull(value);
    return getString();
    }


  /**
   * Return 'value' itself if it is not null, or "&nbsp;" if it is.  This is mainly for table cells,
   * since some browsers don't properly show cell borders around cells having no content.
   *
   * @param value any string or null
   * @return 'value' itself if it is not null, or "&nbsp;" if it is
   */
  public String noNullTD(String value) throws IOException
    {
    writeToString();
    printNoNullTD(value);
    return getString();
    }


  /**
   * Return 'value' itself if it is not null, or "&nbsp;" if it is.  This is mainly for table cells,
   * since some browsers don't properly show cell borders around cells having no content.
   *
   * @param value any string or null
   * @return 'value' itself if it is not null, or "&nbsp;" if it is
   */
  public String noNullTDMax(String value, int maxLength) throws IOException
    {
    writeToString();
    printNoNullTDMax(value, maxLength);
    return getString();
    }


  /**
   * Return 'value' itself if it is both not null and nonempty, or null if not.
   * (Or does that sound too negative?)
   *
   * @param value any string or null
   * @return null if the string is empty or null, 'value' itself if not
  public String nullIfEmpty(String value) throws IOException
    {
    writeToString();
    printNullIfEmpty(value);
    return getString();
    }
   */


  /**
   * Return an OPTION tag pair having 'text' as its output text.
   *
   * @param value a value for the VALUE attribute, or null
   * @param text the output text to be inserted between the tags
   * @return HTML output for an OPTION tag pair
   */
  public String OPTION(String value, String text) throws IOException
    {
    writeToString();
    printOPTION(value, text);
    return getString();
    }


  /**
   * Return an OPTION tag pair having 'text' as its output text.  If 'value' equals 'somethingWhickMayEqualValue',
   * make this option selected.
   *
   * @param value a value for the VALUE attribute, or null
   * @param text the output text to be inserted between the tags
   * @param selectedValue a string which may equal 'value'
   * @return HTML output for an OPTION tag pair
   */
  public String OPTION(String value, String text, Object selectedValue) throws IOException
    {
    writeToString();
    printOPTION(value, text, selectedValue);
    return getString();
    }


  /**
   * Return an OPTION tag pair having 'text' as its output text.
   *
   * @param value a value for the VALUE attribute, or null
   * @param text the output text to be inserted between the tags
   * @param selected whether the SELECTED attribute should be inserted
   * @return HTML output for an OPTION tag pair
   */
  public String OPTION(String value, String text, boolean selected) throws IOException
    {
    writeToString();
    printOPTION(value, text, selected);
    return getString();
    }


  /**
   * Return the OPTIONS of a SELECT drop-down.  'options' is a Map or a List
   * used to populate the OPTIONs.  If 'options' is a List, use each value
   * for the key and the displayed text.
   * <br><br>
   * This method is effective when passed a LinkedHashMap, since that brings
   * order to the key set.
   *
   * @param options a Map or List containing the keys and display values
   * @return a bunch of OPTIONs
   */
  public String OPTIONs(Object options) throws IOException
    {
    writeToString();
    printOPTIONs(options);
    return getString();
    }


  /**
   * Return the OPTIONS of a SELECT drop-down.  'options' is a Map or a List
   * used to populate the OPTIONs.  Preselect any OPTION whose key matches
   * 'value'.  If 'options' is a List, use each value for the key and the
   * displayed text.
   * <br><br>
   * This method is effective when passed a LinkedHashMap, since that brings
   * order to the key set.
   *
   * @param options a Map or List containing the keys and display values
   * @param selectedValue a value to match for preselection
   * @return a bunch of OPTIONs
   */
  public String OPTIONs(Object options, String selectedValue) throws IOException
    {
    writeToString();
    printOPTIONs(options, selectedValue);
    return getString();
    }


  /**
   * Return a PARAM tag for use within an APPLET tag pair.
   *
   * @param name the name of the applet parameter
   * @param value the applet parameter's value
   * @return HTML output for a PARAM tag
   */
  public String PARAM(String name, String value) throws IOException
    {
    writeToString();
    printPARAM(name, value);
    return getString();
    }


  /**
   * Return a default INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @return HTML output for a PASSWORD input
   */
  public String passwordINPUT(String name) throws IOException
    {
    writeToString();
    printPasswordINPUT(name);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @return HTML output for a custom password INPUT
   */
  public String passwordINPUT(String name, int size, int maxSize, String value) throws IOException
    {
    writeToString();
    printPasswordINPUT(name, size, maxSize, value);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @param attributes any additional attributes
   * @return HTML output for a custom text INPUT
   */
  public String passwordINPUT(String name, int size, int maxSize, String value,
                              String attributes) throws IOException
    {
    writeToString();
    printPasswordINPUT(name, size, maxSize, value, attributes);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @return HTML output for a custom text INPUT
   */
  public String passwordINPUT(String name, int size, int maxSize, String value,
                              int tabIndex) throws IOException
    {
    writeToString();
    printPasswordINPUT(name, size, maxSize, value, tabIndex);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @param attributes any additional attributes
   * @return HTML output for a custom text INPUT
   */
  public String passwordINPUT(String name, int size, int maxSize, String value,
                              String attributes, int tabIndex) throws IOException
    {
    writeToString();
    printPasswordINPUT(name, size, maxSize, value, attributes, tabIndex);
    return getString();
    }


  /**
   * Return HTML code for a JavaScript "popup" link, one that displays a message
   * dialog instead of actually linking to another page.
   *
   * @param displayHTML the HTML (or just text) be displayed as the link
   * @param popupText the text to be displayed in the JavaScript dialog
   * @return HTML code for such a thing
   */
  public String popupLink(String displayHTML, String popupText) throws IOException
    {
    writeToString();
    printPopupLink(displayHTML, popupText);
    return getString();
    }


  /**
   * Return an INPUT tag of type RADIO.
   *
   * @return HTML output for a radio INPUT
   */
  public String radioINPUT(String name, String value) throws IOException
    {
    writeToString();
    printRadioINPUT(name, value);
    return getString();
    }


  /**
   * Return an INPUT tag of type RADIO.
   *
   * @return HTML output for a radio INPUT
   */
  public String radioINPUT(String name, String value, boolean selected) throws IOException
    {
    writeToString();
    printRadioINPUT(name, value, selected);
    return getString();
    }


  /**
   * Return an INPUT tag of type "reset" having the given value text.
   *
   * @param value a value to be displayed on the INPUT control
   * @param attributes any additional attributes to be included
   * @return HTML output for the SUBMIT input
   */
  public String RESET(String value, String attributes) throws IOException
    {
    writeToString();
    printRESET(value, attributes);
    return getString();
    }


  /**
   * Return a JavaScript SCRIPT tag pair containing the script 'script'.
   *
   * @param script the JavaScript script to be enclosed in SCRIPT tags
   * @return HTML output for a JavaScript script
   */
  public String SCRIPT(String script) throws IOException
    {
    writeToString();
    printSCRIPT(script);
    return getString();
    }


  /**
   * Return a JavaScript SCRIPT tag pair linking an external JavaScript file.
   *
   * @param url the URL of the JavaScript file to be included
   * @return HTML output for a JavaScript "include"
   */
  public String SCRIPTFile(String url) throws IOException
    {
    writeToString();
    printSCRIPTFile(url);
    return getString();
    }


  /**
   * Return a JavaScript function having signature 'signature' and 'code' as its body.
   *
   * @param signature the function signature (in the form:  diddle(howManyTimes))
   * @param code the body of the function
   * @return HTML output for a JavaScript function
   */
  public String SCRIPTFunction(String signature, String code) throws IOException
    {
    writeToString();
    printSCRIPTFunction(signature, code);
    return getString();
    }


  /**
   * Return a custom opening SELECT tag.
   *
   * @param name a value for the NAME attribute
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @return HTML output for a SELECT tag
   */
  public String beginSELECT(String name, String attributes) throws IOException
    {
    writeToString();
    printBeginSELECT(name, attributes);
    return getString();
    }


  /**
   * Return a complete SELECT input using 'options' to populate it.  Preselect
   * any value matching 'value'.  'options' must be either a Map or a List.
   * In the case of a List, each element is used as the value and the displayed
   * text.
   *
   * @param name the name of the input
   * @param options a Map or List to be used in populating the drop-down
   * @param selectedValue a value to match against
   * @return a SELECT input element
   */
  public String SELECT(String name, Object options, String selectedValue) throws IOException
    {
    writeToString();
    printSELECT(name, options, selectedValue);
    return getString();
    }


  /**
   * Return a complete SELECT input using 'options' to populate it.  Preselect
   * any value matching 'value'.  'options' must be either a Map or a List.
   * In the case of a List, each element is used as the value and the displayed
   * text.
   *
   * @param name the name of the input
   * @param options a Map or List to be used in populating the drop-down
   * @param selectedValue a value to match against
   * @param attributes any additional attributes needed
   * @return a SELECT input element
   */
  public String SELECT(String name, Object options, String selectedValue,
                       String attributes) throws IOException
    {
    writeToString();
    printSELECT(name, options, selectedValue, attributes);
    return getString();
    }


  /**
   * Return an IMG link to a single-pixel, transparent GIF image.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
   * @return HTML output for a trasparent image tag
   */
  public String spacer(int width, int height) throws IOException
    {
    writeToString();
    printSpacer(width, height);
    return getString();
    }


  /**
   * Return an IMG link to a single-pixel, transparent GIF image.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @return HTML output for a trasparent image tag
   */
  public String spacer(int width, int height, String attributes) throws IOException
    {
    writeToString();
    printSpacer(width, height, attributes);
    return getString();
    }


  /**
   * Return a block-level spacer.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
   * @return HTML output for a trasparent spacer
   */
  public String spacerBlock(int width, int height) throws IOException
    {
    writeToString();
    printSpacerBlock(width, height);
    return getString();
    }


  /**
   * Return a SPAN having the specified style.
   *
   * @param style the name of the CSS style to apply to this SPAN tag
   * @return HTML output for a SPAN tag
   */
  public String beginSPAN(String style) throws IOException
    {
    writeToString();
    printBeginSPAN(style);
    return getString();
    }


  /**
   * Return a SPAN having the specified attributes.
   *
   * @param style the name of the CSS style to apply to this SPAN tag
   * @return HTML output for a SPAN tag
   */
  public String SPAN(String style, String text) throws IOException
    {
    writeToString();
    printSPAN(style, text);
    return getString();
    }


  /**
   * Return 'string' with a backslash inserted before all double-quotes.
   *
   * @param string any string
   * @return the string with double-quotes escaped
   */
  public String stringEscape(String string) throws IOException
    {
    writeToString();
    printStringEscape(string);
    return getString();
    }



  /**
   * Return 'string' with a backslash inserted before all double-quotes and a blank string
   * if null.
   *
   * @param string any string
   * @return the string with double-quotes escaped
   */
  public String stringEscapeNoNull(String string) throws IOException
    {
    writeToString();
    printStringEscapeNoNull(string);
    return getString();
    }


  /**
   * Return a STYLE tag pair including 'styles', which should be any number of
   * CSS style definitions.
   *
   * @param styles any number of CSS-formatted style definitions
   * @return an HTML STYLE tag pair containing the given styles
   */
  public String STYLE(String styles) throws IOException
    {
    writeToString();
    printSTYLE(styles);
    return getString();
    }


  /**
   * Return a STYLE tag pair for the given StyleSheet object.
   * <br/><br/>
   * Note:  this maybe should go in HTML1, but that it depends on StyleSheet.
   *
   * @return a style sheet to be outputted
   */
  public String STYLE(StyleSheet styleSheet) throws IOException
    {
    writeToString();
    printSTYLE(styleSheet);
    return getString();
    }


  /**
   * Return a LINK tag importing a style sheet, for use in the HTML header.
   *
   * @param url the URL of the style sheet
   */
  public String styleSheetLINK(String url) throws IOException
    {
    writeToString();
    printStyleSheetLINK(url);
    return getString();
    }


  /**
   * Return a LINK tag importing a style sheet, for use in the HTML header.
   *
   * @param url the URL of the style sheet
   * @param name a name for the style sheet
   */
  public String styleSheetLINK(String url, String name) throws IOException
    {
    writeToString();
    printStyleSheetLINK(url, name);
    return getString();
    }


  /**
   * Return a SUBMIT input tag having the given value text.
   *
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   * @return HTML output for the SUBMIT input
   */
  public String SUBMIT(String value) throws IOException
    {
    writeToString();
    printSUBMIT(value);
    return getString();
    }


  /**
   * Return a SUBMIT input tag having the given value text and the given name.
   *
   * @param name a parameter name for the SUBMIT
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   * @return HTML output for the SUBMIT input
   */
  public String SUBMIT(String name, String value) throws IOException
    {
    writeToString();
    printSUBMIT(name, value);
    return getString();
    }


  /**
   * Return a SUBMIT input tag having the given value text and the given name.
   *
   * @param name a parameter name for the SUBMIT
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   * @param attributes any additional attributes to be included
   * @return HTML output for the SUBMIT input
   */
  public String SUBMIT(String name, String value, String attributes) throws IOException
    {
    writeToString();
    printSUBMIT(name, value, attributes);
    return getString();
    }


  /**
   * Return a SUBMIT input tag having the given value text, the given name,
   * and an optional "onClick" handler.
   *
   * @param name a parameter name for the SUBMIT
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   * @param onClick a JavaScript on-click handler command (sans quotes)
   * @param attributes any additional attributes to be included
   * @return HTML output for the SUBMIT input
   */
  public String SUBMIT(String name, String value, String onClick,
                       String attributes, boolean submitNotReset) throws IOException
    {
    writeToString();
    printSUBMIT(name, value, onClick, attributes, submitNotReset);
    return getString();
    }


  /**
   * Return a custom opening TABLE tag.
   *
   * @param border the border value for the table in pixels
   * @param cellpadding the cell padding for the table in pixels
   * @param cellspacing the call spacing for the table in pixels
   * @return HTML output for an opening TABLE tag
   */
  public String beginTABLE(int border, int cellpadding, int cellspacing) throws IOException
    {
    writeToString();
    printBeginTABLE(border, cellpadding, cellspacing);
    return getString();
    }


  /**
   * Return a custom opening TABLE tag.
   *
   * @param border the border value for the table in pixels
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @param width a value for the WIDTH attribute, or null
   * @return HTML output for an opening TABLE tag
   */
  public String beginTABLE(int border, String width, String attributes) throws IOException
    {
    writeToString();
    printBeginTABLE(border, width, attributes);
    return getString();
    }


  /**
   * Return a custom opening TABLE tag.
   *
   * @param border the border value for the table in pixels
   * @param cellpadding the cell padding for the table in pixels
   * @param cellspacing the call spacing for the table in pixels
   * @param width a value for the WIDTH attribute, or null
   * @return HTML output for an opening TABLE tag
   */
  public String beginTABLE(int border, int cellpadding, int cellspacing, String width) throws IOException
    {
    writeToString();
    printBeginTABLE(border, cellpadding, cellspacing, width);
    return getString();
    }


  /**
   * Return a custom opening TABLE tag.
   *
   * @param border the border value for the table in pixels
   * @param cellpadding the cell padding for the table in pixels
   * @param cellspacing the call spacing for the table in pixels
   * @param width a value for the WIDTH attribute, or null
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @return HTML output for an opening TABLE tag
   */
  public String beginTABLE(int border, int cellpadding, int cellspacing,
                      String width, String attributes) throws IOException
    {
    writeToString();
    printBeginTABLE(border, cellpadding, cellspacing, width, attributes);
    return getString();
    }


  /**
   * Return a TD tag pair using style "tableHeader" and having 'label' as its content.
   *
   * @param label the HTML to be inserted in the table cell
   * @return HTML output for a TD tag pair
   */
  public String tableHeaderCell(String label) throws IOException
    {
    writeToString();
    printTableHeaderCell(label);
    return getString();
    }


  /**
   * Write an opening TBODY tag with the given attributes.
   *
   * @param attributes any attributes needed
   */
  public String beginTBODY(String attributes) throws IOException
    {
    writeToString();
    printBeginTBODY(attributes);
    return getString();
    }


  /**
   * Write a custom opening TD tag.
   *
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @return HTML output for a TD tag
   */
  public String beginTD(String attributes) throws IOException
    {
    writeToString();
    printBeginTD(attributes);
    return getString();
    }


  /**
   * Return a custom opening TD tag.
   *
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @param style the style for this table cell
   * @return HTML output for a TD tag
   */
  public String beginTD(String attributes, String style) throws IOException
    {
    writeToString();
    printBeginTD(attributes, style);
    return getString();
    }


  /**
   * Return an opening TD tag with the given cellular dimensions.  Both
   * dimensions should be at least one cell.
   *
   * @param colspan the number of columns the TD should span
   * @param rowspan the number of rows the TD should span
   * @return HTML output for a custom TD cell
   */
  public String beginTD(int colspan, int rowspan) throws IOException
    {
    writeToString();
    printBeginTD(colspan, rowspan);
    return getString();
    }


  /**
   * Return an opening TD tag with the given cellular dimensions.  Both
   * dimensions should be at least one cell.
   *
   * @param colspan the number of columns the TD should span
   * @param rowspan the number of rows the TD should span
   * @param attributes any additional attributes needed for the tag
   * @return HTML output for a custom TD cell
   */
  public String beginTD(int colspan, int rowspan, String attributes) throws IOException
    {
    writeToString();
    printBeginTD(colspan, rowspan, attributes);
    return getString();
    }


  /**
   * Return an opening TD tag with the given cellular dimensions and other
   * attributes.  Both dimensions should be at least one cell.
   *
   * @param colspan the number of columns the TD should span
   * @param rowspan the number of rows the TD should span
   * @param attributes any additional attributes needed for the tag
   * @param style an optional CSS style name for the cell
   * @return HTML output for a custom TD cell
   */
  public String beginTD(int colspan, int rowspan, String attributes, String style) throws IOException
    {
    writeToString();
    printBeginTD(colspan, rowspan, attributes, style);
    return getString();
    }


  /**
   * Return a custom TEXTAREA begin and closing tag with the value in the middle
   *
   * @param name the name of the input
   * @param rows the number of rows in the textarea
   * @param cols the number of columns in the textarea
   * @param value a value to be placed in the text field by default, or null
   * @return HTML output for a custom textarea
   */
  public String TEXTAREA(String name, int rows, int cols, String value) throws IOException
    {
    writeToString();
    printTEXTAREA(name, rows, cols, value);
    return getString();
    }


  /**
   * Return a default INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @return HTML output for a text INPUT
   */
  public String textINPUT(String name) throws IOException
    {
    writeToString();
    printTextINPUT(name);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @return HTML output for a custom text INPUT
   */
  public String textINPUT(String name, int size, int maxSize) throws IOException
    {
    writeToString();
    printTextINPUT(name, size, maxSize);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @return HTML output for a custom text INPUT
   */
  public String textINPUT(String name, int size, int maxSize, String value) throws IOException
    {
    writeToString();
    printTextINPUT(name, size, maxSize, value);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @param attributes any other attributes to be included
   * @return HTML output for a custom text INPUT
   */
  public String textINPUT(String name, int size, int maxSize, String value,
                          String attributes) throws IOException
    {
    writeToString();
    printTextINPUT(name, size, maxSize, value, attributes);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @return HTML output for a custom text INPUT
   */
  public String textINPUT(String name, int size, int maxSize, String value,
                          int tabIndex) throws IOException
    {
    writeToString();
    printTextINPUT(name, size, maxSize, value, tabIndex);
    return getString();
    }


  /**
   * Return a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @return HTML output for a custom text INPUT
   */
  public String textINPUT(String name, int size, int maxSize,
                          String value, String attributes, int tabIndex) throws IOException
    {
    writeToString();
    printTextINPUT(name, size, maxSize, value, attributes, tabIndex);
    return getString();
    }


  /**
   * Return an opening TR tag with the given CSS class name.
   *
   * @param style the style for this table row
   * @return HTML output for an opening TR tag
   */
  public String beginTR(String style) throws IOException
    {
    writeToString();
    printBeginTR(style);
    return getString();
    }


  /**
   * Return an IMG link to a single-pixel, white GIF image.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
   * @return HTML output for a white image
  public String whitepixelIMG(int width, int height) throws IOException
    {
    writeToString();
    printWhitepixelIMG(width, height);
    return getString();
    }
   */

  }
