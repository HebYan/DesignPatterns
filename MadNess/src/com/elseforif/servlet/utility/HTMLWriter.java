package com.elseforif.servlet.utility;

import com.elseforif.entity.net.WebImage;
import com.elseforif.utility.FileCache;
import com.elseforif.utility.Web1;
import com.elseforif.utility.String1;
import java.io.Writer;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.Iterator;

/**
 * This object represents an alternative to JSPs and other Web-UI frameworks.
 * It is not itself a Writer subclass, but it could become one if the need
 * should arise.  An HTMLWriter wraps a normal java.io.Writer to output HTML
 * strings, and it is designed to be used by servlets to simplify output code.
 * This is not a thread-safe object, so servlets should construct a new
 * instance (or pool them) for each request.
 * <br/><br/>
 * A few members control certain aspects of the HTML that is outputted, such
 * as 'm_xhtml', which controls whether the HTML conforms to the XHTML spec'
 * or instead to the older HTML 4 standards.  Another is 'm_contextPrefix',
 * which defines a string that is prepended to certain URLs passed in to these
 * methods, so that the Web-application context prefix can be given upon
 * this object's construction and not dealt with thereafter.  URLs beginning
 * with a slash are prepended with this value, and others are not--allowing
 * for relative and remote URLs to pass unmangled.
 * <br/><br/>
 * Basically, these methods output one or more HTML tags, and they generally
 * do not add any whitespace around them (with a few very safe exceptions),
 * so that odd HTML layout bugs are not created.  Whitespace can be added
 * to the output using the statics in HTMLConstants, which are also used here.
 */
public class HTMLWriter extends Object implements HTMLConstants
  {

  protected Writer          m_output = null;
  protected String          m_contextPrefix = "";
  protected boolean         m_xhtml = true;
  protected FileCache       m_cache = null;
  protected String          m_preHeadCode = null;
  protected String          m_transpixelImageURL = "";



  /**
   * Construct an HTMLWriter wrapper for Writer 'output'.
   *
   * @param output the Writer I will use for 'print...()' calls.
   */
  public HTMLWriter(Writer output)
    {
    this(output, null);
    }


  /**
   * Construct an HTMLWriter wrapper for Writer 'output'.
   * Prepend 'contextPrefix' to any local URLs beginning with "/".
   *
   * @param output the Writer I will use for 'print...()' calls.
   * @param contextPrefix my local URL prefix.
   */
  public HTMLWriter(Writer output, String contextPrefix)
    {
    m_output = output;
    if (contextPrefix != null)
      {
      if ((contextPrefix.length() > 0) && !contextPrefix.startsWith("/"))
        contextPrefix = "/" + contextPrefix;
      m_contextPrefix = contextPrefix;
      }
    }


  /**
   * Return my underlying writer object.
   *
   * @return my output Writer
   */
  public Writer getWriter()
    {
    return m_output;
    }


  /**
   * Flush my Writer object's output.
   */
  public void flush() throws IOException
    {
    m_output.flush();
    }


  /**
   * Close my contained Writer object.
   */
  public void close() throws IOException
    {
    m_output.close();
    }


  /**
   * Set whether I should write XHTML or old-school HTML 4.
   *
   * @param xhtml whether I should write XHTML (the default)
   */
  public void setXHTML(boolean xhtml)
    {
    m_xhtml = xhtml;
    }


  /**
   * Return whether I am writing XHTML and not HTML 4.
   *
   * @return whether I am in XHTML mode (the default)
   */
  public boolean getXHTML()
    {
    return m_xhtml;
    }


  /**
   * Set the code to be outputted before the first HTML tag in the page.
   * This is output when 'printHEAD()' or 'printBeginHEAD()' is called,
   * and it should include any XML prologue or doctype declaration needed
   * for the type of HTML being produced.
   *
   * @param preHeadCode the pre-HTML code for this writer
   */
  public void setPreHeadCode(String preHeadCode)
    {
    m_preHeadCode = preHeadCode;
    }


  /**
   * Return the code to be outputted before the first HTML tag in the page.
   *
   * @return the pre-HTML code for this writer
   */
  public String getPreHeadCode()
    {
    return m_preHeadCode;
    }


  /**
   * Write 'string' unmodified to my Writer.
   *
   * @param string a string to be written
   */
  public void print(String string) throws IOException
    {
    m_output.write(string);
    }


  /**
   * Write an integer as a string.
   *
   * @param toString any integer
   */
  public void print(int toString) throws IOException
    {
    m_output.write(Integer.toString(toString));
    }


  /**
   * Write out an invisible, named link tag (with a "name" attribute instead
   * of "href").
   *
   * @param name the name index for this page link
   */
  public void printA(String name) throws IOException
    {
    m_output.write("<a name=\"");
    m_output.write(name);
    m_output.write("\"></a>");
    }


  /**
   * Write a custom link tag.
   *
   * @param url the URL for the link
   * @param link the HTML output to be the active link on the page
   */
  public void printA(String url, String link) throws IOException
    {
    printA(url, link, null, null);
    }


  /**
   * Write a custom link tag.
   *
   * @param url the URL for the link
   * @param link the HTML output to be the active link on the page
   * @param style a style to be used
   */
  public void printA(String url, String link, String style) throws IOException
    {
    printA(url, link, style, null);
    }


  /**
   * Write a custom link tag.
   *
   * @param url the URL for the link
   * @param link the HTML output to be the active link on the page
   * @param style a style to be used
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   */
  public void printA(String url, String link, String style, String attributes) throws IOException
    {
    m_output.write("<a href=\"");
    if (url.startsWith("/"))
      m_output.write(m_contextPrefix);
    m_output.write(url);
    m_output.write("\"");
    if (style != null)
      {
      m_output.write(" class=\"");
      m_output.write(style);
      m_output.write("\"");
      }
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(">");
    m_output.write(link);
    m_output.write("</a>");
    }


  /**
   * Write a complete JavaScript script producing a pop-up message dialog whose text is 'message'.
   *
   * @param message the text to be displayed in the dialog
   */
  public void printAlertSCRIPT(String message) throws IOException
    {
    printSCRIPT("alert(\"" + message + "\");");
    }


  /**
   * Write an APPLET tag pair having the specified attributes.
   *
   * @param appletName a name for the applet, or null
   * @param properClassName the full name of the class, sans the ".class" part
                            (since that is not properly part of the class name)
   * @param width the value of the WIDTH attribute
   * @param height the value of the HEIGHT attribute
   */
  public void printAPPLET(String appletName, String properClassName,
                          String width, String height) throws IOException
    {
    printBeginAPPLET(appletName, properClassName, width, height, null);
    m_output.write(END_APPLET);
    }


  /**
   * Write an AREA tag for defining an image map.  Call this multiply after
   * 'MAP()', and then remember to print the END_MAP tag.
   *
   * @param left the left-hand coordinate
   * @param top the top coordinate
   * @param right the right-hand coordinate
   * @param bottom the bottom coordinate
   * @param uri the URI to which the area points
   * @param altText and optional ALT text value
   */
  public void printAREA(int left, int top, int right, int bottom,
                        String uri, String altText) throws IOException
    {
    m_output.write(NL + "<area href=\"");
    if (uri.startsWith("/"))
      m_output.write(m_contextPrefix);
    m_output.write(uri);
    m_output.write("\"");
    if (altText != null)
      {
      m_output.write(" alt=\"");
      m_output.write(altText);
      m_output.write("\"");
      }
    m_output.write(" coords=\"");
    m_output.write(Integer.toString(left));
    m_output.write(",");
    m_output.write(Integer.toString(top));
    m_output.write(",");
    m_output.write(Integer.toString(right));
    m_output.write(",");
    m_output.write(Integer.toString(bottom));
    m_output.write("\" />");
    }


  /**
   * Write an opening APPLET tag having the specified attributes.
   *
   * @param appletName a name for the applet, or null
   * @param properClassName the full name of the class, sans the ".class" part
                            (since that is not properly part of the class name)
   * @param width the value of the WIDTH attribute
   * @param height the value of the HEIGHT attribute
   */
  public void printBeginAPPLET(String appletName, String properClassName,
                               String width, String height) throws IOException
    {
    printBeginAPPLET(appletName, properClassName, width, height, null);
    }


  /**
   * Write an opening APPLET tag having the specified attributes.
   *
   * @param appletName a name for the applet, or null
   * @param properClassName the full name of the class, sans the ".class" part
                            (since that is not properly part of the class name)
   * @param width the value of the WIDTH attribute
   * @param height the value of the HEIGHT attribute
   * @param archive the archive for the applet, instead of a codebase
   */
  public void printBeginAPPLET(String appletName, String properClassName,
                               String width, String height, String archive) throws IOException
    {
    m_output.write("<object classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\" width=\"");
    m_output.write(width);
    m_output.write("\" height=\"");
    m_output.write(height);
    m_output.write("\"><param name=\"name\" value=\"");
    m_output.write(appletName);
    m_output.write("\"/><param name=\"code\" value=\"");
    m_output.write(properClassName);
    m_output.write("\"/><param name=\"archive\" value=\"");
    m_output.write(archive);
    m_output.write("\"/><!--[if !IE]-->");
    m_output.write("<object");
    if (appletName != null)
      {
      m_output.write(" name=\"");
      m_output.write(appletName);
      m_output.write("\"");
      }
    m_output.write(" classid=\"java:");
    m_output.write(properClassName);
    if (archive == null)
      m_output.write("\" codebase=\"/javaclasses");
    else
      {
      m_output.write("\" archive=\"");
      m_output.write(archive);
      }
    m_output.write("\" width=\"");
    m_output.write(width);
    m_output.write("\" height=\"");
    m_output.write(height);
    m_output.write("\"><!--[endif]-->");
    }


  /**
   * Write an IMG link to a single-pixel, black GIF image.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
  public void printBlackpixelIMG(int width, int height) throws IOException
    {
    printIMG(BlackPixelImageURL, null, width, height);
    }
   */


  /**
   * Write a beginning BLOCKQUOTE tag with the given values.
   *
   * @param attributes extra attributes to add into the brew
   * @param style a CSS class to use
   */
  public void printBeginBLOCKQUOTE(String attributes, String style) throws IOException
    {
    m_output.write("<blockquote ");
    if (attributes != null)
      m_output.write(attributes);
    if (style != null)
      {
      m_output.write(" class=\"");
      m_output.write(style);
      m_output.write("\"");
      }
    m_output.write(">");
    }


  /**
   * Write a BODY tag having the given attributes.
   *
   * @param image a value for the BACKGROUND attribute, or null
   * @param color a value for the BGCOLOR attribute, or null
   */
  public void printBeginBODY(String image, String color) throws IOException
    {
    printBeginBODY(image, color, null);
    }


  /**
   * Write a BODY tag having the given attributes.
   *
   * @param image a value for the BACKGROUND attribute, or null
   * @param color a value for the BGCOLOR attribute, or null
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   */
  public void printBeginBODY(String image, String color, String attributes) throws IOException
    {
    m_output.write(NL + "<body");
    if ((image != null) || (color != null))
      {
      m_output.write(" ");
      int index = -1;
      if (attributes != null)
        {
        index = attributes.indexOf("style=\"");
        if (index == -1)
          {
          m_output.write(attributes);
          m_output.write(" ");
          }
        else
          m_output.write(attributes.substring(0, index));
        }
      m_output.write("style=\"background-image: ");
      if (image != null)
        {
        m_output.write("url(");
        m_output.write(image);
        m_output.write(");");
        }
      else
        m_output.write("none;");
      if (color != null)
        {
        m_output.write(" background-color: ");
        m_output.write(color);
        m_output.write(";");
        }
      if (index == -1)
        m_output.write("\"");
      else
        {
        m_output.write(" ");
        m_output.write(attributes.substring(index + 7));
        }
      }
    else if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(">" + NL + NL);
    }


  /**
   * Write an INPUT tag of type CHECKBOX whose value is "true".
   *
   * @param name the name of the checkbox parameter
   */
  public void printCHECKBOX(String name) throws IOException
    {
    printCHECKBOX(name, "true", false);
    }


  /**
   * Write an INPUT tag of type CHECKBOX.
   *
   * @param name the name of the value of the checkbox
   * @param value the value for the checkbox
   */
  public void printCHECKBOX(String name, String value) throws IOException
    {
    printCHECKBOX(name, value, false);
    }


  /**
   * Write an INPUT tag of type CHECKBOX, preselected according to 'selected',
   * whose value is "true".
   *
   * @param name the name of the value of the checkbox
   * @param selected whether the checkbox should be selected initially
   */
  public void printCHECKBOX(String name, boolean selected) throws IOException
    {
    printCHECKBOX(name, "true", selected);
    }


  /**
   * Write an INPUT tag of type CHECKBOX, preselected according to 'selected'.
   *
   * @param name the name of the value of the checkbox
   * @param value the value for the checkbox
   * @param selected whether the checkbox should be selected initially
   */
  public void printCHECKBOX(String name, String value, boolean selected) throws IOException
    {
    m_output.write("<input name=\"");
    m_output.write(name);
    m_output.write("\" type=\"checkbox\" value=\"");
    m_output.write(value);
    m_output.write("\"");
    if (selected)
      if (m_xhtml)
        m_output.write(" checked=\"checked\"");
      else
        m_output.write(" checked");
    m_output.write(" />");
    }


  /**
   * Write 'value' itself if it is not null, or an empty string if it is.
   *
   * @param value any string or null
   */
  public void printDisplayNull(String value) throws IOException
    {
    m_output.write((value != null) ? value : DisplayNull);
    }


  /**
   * Write a DIV tag whose style is 'style'.
   *
   * @param style an optional CSS style
   */
  public void printBeginDIV(String style) throws IOException
    {
    printBeginDIV(style, null);
    }


  /**
   * Write a DIV tag whose style is 'style'.
   *
   * @param style an optional CSS style
   * @param attributes any extra attributes for the DIV tag
   */
  public void printBeginDIV(String style, String attributes) throws IOException
    {
    m_output.write("<div");
    if (style != null)
      {
      m_output.write(" class=\"");
      m_output.write(style);
      m_output.write("\"");
      }
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(">");
    }


  /**
   * Write a DIV tag whose style is 'style'.  Include the given text, and add
   * an end tag.
   *
   * @param style an optional CSS style
   * @param text the HTML to be the DIV's contents
   */
  public void printDIV(String style, String text) throws IOException
    {
    printBeginDIV(style, null);
    if (text != null)
      m_output.write(text);
    m_output.write(END_DIV);
    }


  /**
   * Write a DIV tag whose style is 'style' and having any extra attribute in 'attributes'.
   * Include the given text, and add an end tag.
   *
   * @param style an optional CSS style
   * @param text the HTML to be the DIV's contents
   * @param attributes any extra attributes for the DIV tag
   */
  public void printDIV(String style, String text, String attributes) throws IOException
    {
    printBeginDIV(style, attributes);
    if (text != null)
      m_output.write(text);
    m_output.write(END_DIV);
    }


  /**
   * Write the contents of the specified file, assumed ASCII.  If the file
   * cannot be read, write an error message.
   *
   * @param path the path to the file to be output
   */
  public void printFile(String path) throws IOException
    {
    if (m_cache == null)
      m_cache = new FileCache();
    try
      {
      m_output.write((String)(m_cache.reparseFile(path)));
      }
    catch (Exception exception)
      {
      exception.printStackTrace();
      m_output.write("(Could not read file: " + path + ")");
      }
    }


  /**
   * Write a form input tag of type "FILE", for uploading files.
   *
   * @param name the name of the input
   */
  public void printFILEInput(String name) throws IOException
    {
    printINPUT(name, "file", 0, 0, null, null, 0);
    }


  /**
   * Write a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   */
  public void printFILEInput(String name, int size, int maxSize,
                             String attributes, int tabIndex) throws IOException
    {
    printINPUT(name, "file", size, maxSize, null, attributes, tabIndex);
    }


  /**
   * Write a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   */
  public void printFILEInput(String name, int size, int maxSize,
        String value, String attributes, int tabIndex) throws IOException
    {
    printINPUT(name, "file", size, maxSize, value, attributes, tabIndex);
    }


  /**
   * Write a default-type FORM tag with the specified target URL.  The name of the form is "form1".
   *
   * @param url the URL target of the form
   */
  public void printBeginFORM(String url) throws IOException
    {
    printBeginFORM("form1", true, url, null);
    }


  /**
   * Write a custom FORM tag.
   *
   * @param name the name of the form
   * @param getNotPost whether GET should be used or POST
   * @param url the target URL for the form
   */
  public void printBeginFORM(String name, boolean getNotPost, String url) throws IOException
    {
    printBeginFORM(name, getNotPost, url, null);
    }


  /**
   * Write a custom FORM tag.
   *
   * @param name the name of the form
   * @param getNotPost whether GET should be used or POST
   * @param url the target URL for the form
   * @param onSubmit a value for the onSubmit attribute (typically of the form:  'return validate();')
   */
  public void printBeginFORM(String name, boolean getNotPost, String url,
                        String onSubmit) throws IOException
    {
    m_output.write("<form ");
    m_output.write(m_xhtml ? "id" : "name");
    m_output.write("=\"");
    m_output.write(name);
    m_output.write("\" method=\"");
    m_output.write(getNotPost ? "get" : "post");
    m_output.write("\" action=\"");
    if (url.startsWith("/"))
      m_output.write(m_contextPrefix);
    m_output.write(url);
    m_output.write("\"");
    if (onSubmit != null)
      {
      m_output.write(" onsubmit=\"");
      m_output.write(onSubmit);
      m_output.write("\"");
      }
    m_output.write(">");
    }


  /**
   * Write a little SCRIPT tag pair for focusing a particular form field
   * immediately.
   *
   * @param formName the name of the form whose field is to be focused
   * @param fieldName the name of the form field to focus
   */
  public void printFormFocusSCRIPT(String formName, String fieldName) throws IOException
    {
    printSCRIPT("document." + formName + "." + fieldName + ".focus();");
    }


  /**
   * Write a custom FRAME tag for a frameset.
   *
   * @param name a name for the frame
   * @param url the URL of the frame's page
   * @param noresize whether the NORESIZE attribute should be inserted
   * @param scrolling a value for the SCROLLING attribute
   */
  public void printFRAME(String name, String url, boolean noresize,
                         String scrolling) throws IOException
    {
    m_output.write(NL + "  <frame name=\"");
    m_output.write(name);
    m_output.write("\" src=\"");
    if (url.startsWith("/"))
      m_output.write(m_contextPrefix);
    m_output.write(url);
    m_output.write("\"");
    if (m_xhtml)
      m_output.write(" />");
    else
      {
      m_output.write(" frameborder=0 border=0 marginwidth=0 marginheight=0 topmargin=0 leftmargin=0 ");
      if (noresize)
        m_output.write("noresize ");
      m_output.write("scrolling=");
      m_output.write(scrolling);
      m_output.write(">");
      }
    }


  /**
   * Write a beginning FRAMESET tag having the specified properties.
   *
   * @param columnsNotRows whether the frameset is to be divided by columns or rows
   * @param division the specification of columns or rows, as represented in the value of attributes COLS and ROWs
   */
  public void printBeginFRAMESET(boolean columnsNotRows, String division) throws IOException
    {
    m_output.write(NL + "<frameset ");
    m_output.write(columnsNotRows ? "cols" : "rows");
    m_output.write("=\"");
    m_output.write(division);
    m_output.write("\"");
    if (!m_xhtml)
      m_output.write(" frameborder=no frameborder=0 border=0 framespacing=0");
    m_output.write(">");
    }


  /**
   * Write an H tag pair having the specified properties.
   *
   * @param level the heading level (where 1 denotes an H1 tag, and so on)
   * @param heading the HTML to be inserted between the tags
   */
  public void printH(int level, String heading) throws IOException
    {
    printH(level, heading, null);
    }


  /**
   * Write an H tag pair having the specified properties.
   *
   * @param level the heading level (where 1 denotes an H1 tag, and so on)
   * @param heading the HTML to be inserted between the tags
   * @param attributes any extra attributes for this heading
   */
  public void printH(int level, String heading, String attributes) throws IOException
    {
    m_output.write(NL + "<h");
    m_output.write(Integer.toString(level));
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(">");
    m_output.write(heading);
    m_output.write("</h");
    m_output.write(Integer.toString(level));
    m_output.write(">");
    }


  /**
   * Write a HEAD construct using 'title' for the TITLE tag pair.
   *
   * @param title a page title
   */
  public void printHEAD(String title) throws IOException
    {
    printHEAD(title, null);
    }


  /**
   * Write a beginning HEAD tag construct with the given title, including
   * any additional tags in 'tags'.
   *
   * @param title a page title
   * @param tags any additional tags to be included in the HEAD tag pair
   */
  public void printBeginHEAD(String title, String tags) throws IOException
    {
    if (m_preHeadCode != null)
      m_output.write(m_preHeadCode);
    else if (m_xhtml)
      m_output.write(DOCTYPE_XHTML_TRANSITIONAL);
    else
      m_output.write(DOCTYPE_HTML_4_LOOSE);
    m_output.write(NL + NL);
    m_output.write("<html>" + NL + NL + "<head>");
    if (title != null)
      {
      m_output.write(NL + "  <title>");
      m_output.write(title);
      m_output.write("</title>");
      }
    if (tags != null)
      m_output.write(tags);
    m_output.write(NL);
    }


  /**
   * Write a HEAD tag construct with the given title, including any additional tags
   * in 'tags'.
   *
   * @param title a page title
   * @param tags any additional tags to be included in the HEAD tag pair
   */
  public void printHEAD(String title, String tags) throws IOException
    {
    printBeginHEAD(title, tags);
    m_output.write("</head>" + NL);
    }


  /**
   * Write a hidden INPUT tag.
   *
   * @param name the name of the input
   * @param value a value for the VALUE attribute, or null
   */
  public void printHIDDENInput(String name, String value) throws IOException
    {
    printINPUT(name, "hidden", 0, 0, value, null, 0);
    }


  /**
   * Write an HR tag having the speicifed width.
   *
   * @param width a value for the WIDTH attribute
   */
  public void printHR(String width) throws IOException
    {
    printHR(width, null);
    }


  /**
   * Write a custom HR tag.
   *
   * @param width a value for the WIDTH attribute
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   */
  public void printHR(String width, String attributes) throws IOException
    {
    m_output.write(NL + "<hr width=");
    m_output.write(width);
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(" />");
    }


  /**
   * Write 'htmlString' with any special HTML characters escaped in order
   * to keep the surrounding HTML well formed.  This replaces greater-than
   * and less-than symbols, etc. with their HTML escaped counterparts (i.e.,
   * "&gt;" and "&lt;").
   *
   * @param htmlString a raw string to be formatted for HTML output
   */
  public void printHTMLEscape(String htmlString) throws IOException
    {
    print(Web1.HTMLEscape(htmlString));
    }


  /**
   * Write a "fav icon" LINK tag, to display an icon in the location bar.
   * (This is normally a 16x16 ICO file.)
   *
   * @param url the URL for the icon image
   */
  public void printIconLINK(String url) throws IOException
    {
    m_output.write(NL + "  <link rel=\"shortcut icon\" href=\"");
    if (url.startsWith("/"))
      m_output.write(m_contextPrefix);
    m_output.write(url);
    m_output.write("\" type=\"image/x-icon\" />");
    }


  /**
   * Write an IMG link tag.
   *
   * @param url the URL of the image file
   */
  public void printIMG(String url) throws IOException
    {
    printIMG(url, null, 0, 0);
    }


  /**
   * Write a custom IMG link tag.
   *
   * @param url the URL of the image file
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   */
  public void printIMG(String url, String attributes) throws IOException
    {
    printIMG(url, attributes, 0, 0);
    }


  /**
   * Write a custom IMG link tag.
   *
   * @param url the URL of the image file
   * @param width the width of the image in pixels, or 0 for unspecified
   * @param height the height of the image in pixels, or 0 for unspecified
   */
  public void printIMG(String url, int width, int height) throws IOException
    {
    printIMG(url, null, width, height);
    }


  /**
   * Write a custom IMG link tag.
   *
   * @param image a WebImage to be outputted
   */
  public void printIMG(WebImage image) throws IOException
    {
    printIMG(image.getPath(), null, image.getWidth(), image.getHeight());
    }


  /**
   * Write a custom IMG link tag.
   *
   * @param image a WebImage to be outputted
   * @param attributes additional attributes to be included
   */
  public void printIMG(WebImage image, String attributes) throws IOException
    {
    printIMG(image.getPath(), attributes, image.getWidth(), image.getHeight());
    }


  /**
   * Write a custom IMG link tag.
   *
   * @param url the URL of the image file
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @param width the width of the image in pixels, or 0 for unspecified
   * @param height the height of the image in pixels, or 0 for unspecified
   */
  public void printIMG(String url, String attributes, int width, int height) throws IOException
    {
    m_output.write("<img src=\"");
    if (url.startsWith("/"))
      m_output.write(m_contextPrefix);
    m_output.write(url);
    m_output.write("\"");
    if (width > 0)
      {
      m_output.write(" width=\"");
      m_output.write(Integer.toString(width));
      m_output.write("\"");
      }
    if (height > 0)
      {
      m_output.write(" height=\"");
      m_output.write(Integer.toString(height));
      m_output.write("\"");
      }
    m_output.write(" ");
    if (attributes != null)
      m_output.write(attributes);
    else
      {
      m_output.write("alt=\"");
      m_output.write(Integer.toString(width));
      m_output.write("x");
      m_output.write(Integer.toString(height));
      m_output.write(" image.\"");
      }
    m_output.write(" />");
    }


  /**
   * Write a custom INPUT tag.
   *
   * @param name the name of the input
   * @param type the type of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   */
  public void printINPUT(String name, String type, int size, int maxSize,
        String value, String attributes, int tabIndex) throws IOException
    {
    m_output.write(NL + "  <input name=\"");
    m_output.write(name);
    m_output.write("\" id=\"");
    m_output.write(name);
    m_output.write("\" type=\"");
    m_output.write(type);
    m_output.write("\" ");
    if (size > 0)
      {
      m_output.write("size=\"");
      m_output.write(Integer.toString(size));
      m_output.write("\"");
      }
    if (maxSize > 0)
      {
      m_output.write(" maxlength=\"");
      m_output.write(Integer.toString(maxSize));
      m_output.write("\"");
      }
    if (tabIndex > 0)
      {
      m_output.write(" tabindex=\"");
      m_output.write(Integer.toString(tabIndex));
      m_output.write("\"");
      }
    if (value != null)
      {
      m_output.write(" value=\"");
      printHTMLEscape(value);
      m_output.write("\"");
      }
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(" />");
    }


  /**
   * Write an open MAP tag with name.  After this, 'AREA()' can be called
   * multiply to define the  areas of the image map.  Then add an 'END_MAP'.
   *
   * @param name the image MAP
   */
  public void printBeginMAP(String name) throws IOException
    {
    m_output.write("<map name=\"");
    m_output.write(name);
    m_output.write("\">");
    }


  /**
   * Iff 'maybe' is not null, write it.
   *
   * @param maybe a value to output if it is not null
   */
  public void printMaybe(String maybe) throws IOException
    {
    if (maybe != null)
      m_output.write(maybe);
    }


  /**
   * Iff 'maybe' is not null, write it.
   *
   * @param maybe a value to output if it is not null
   * @param style an optional CSS style to use with an enclosing SPAN pair
   */
  public void printMaybe(String maybe, String style) throws IOException
    {
    printMaybe(null, maybe, null, style, -1);
    }


  /**
   * Iff 'maybe' is not null, write all non-null parameters in order.
   *
   * @param prefix a value to prepend to the output if 'maybe' is not null
   * @param maybe a value to output if it is not null
   * @param suffix a value to append to the output if 'maybe' is not null
   */
  public void printMaybe(String prefix, String maybe, String suffix) throws IOException
    {
    printMaybe(prefix, maybe, suffix, null, -1);
    }


  /**
   * Iff 'maybe' is not null, write all non-null parameters in order.
   *
   * @param prefix a value to prepend to the output if 'maybe' is not null
   * @param maybe a value to output if it is not null
   * @param suffix a value to append to the output if 'maybe' is not null
   * @param style an optional CSS style to use with an enclosing SPAN pair
   */
  public void printMaybe(String prefix, String maybe, String suffix, String style) throws IOException
    {
    printMaybe(prefix, maybe, suffix, style, -1);
    }


  /**
   * Iff 'maybe' is not null, write all non-null parameters in order.
   *
   * @param prefix a value to prepend to the output if 'maybe' is not null
   * @param maybe a value to output if it is not null
   * @param suffix a value to append to the output if 'maybe' is not null
   * @param maxCharactersPerLine wrap lines after this many characters
   */
  public void printMaybe(String prefix, String maybe, String suffix, int maxCharactersPerLine) throws IOException
    {
    printMaybe(prefix, maybe, suffix, null, maxCharactersPerLine);
    }


  /**
   * Iff 'maybe' is not null, write all non-null parameters in order.  If
   * 'maxCharactersPerLine' is positive, insert BR tags into 'maybe' at
   * no more than so many characters' distance, to achieve line wrapping.
   *
   * @param prefix a value to prepend to the output if 'maybe' is not null
   * @param maybe a value to output if it is not null
   * @param suffix a value to append to the output if 'maybe' is not null
   * @param style an optional CSS style to use with an enclosing SPAN pair
   * @param maxCharactersPerLine wrap lines after this many characters
   */
  public void printMaybe(String prefix, String maybe, String suffix, String style, int maxCharactersPerLine) throws IOException
    {
    if (maybe != null)
      {
      if (prefix != null)
        m_output.write(prefix);
      if (style != null)
        printBeginSPAN(style);
      if (maxCharactersPerLine > 0)
        {
        int endIndex = 0;
        while (maybe.length() > 0)
          {
          if (endIndex != 0)
            m_output.write(BR);
          if (maybe.length() <= maxCharactersPerLine)
            endIndex = maybe.length();
          else
            {
            for (endIndex = maxCharactersPerLine; endIndex > 0; endIndex--)
              if (maybe.charAt(endIndex) == ' ')
                break;
            if (endIndex < 1)
              endIndex = maxCharactersPerLine;
            }
          m_output.write(maybe.substring(0, endIndex));
          maybe = maybe.substring(endIndex);
          }
        }
      else
        m_output.write(maybe);
      if (style != null)
        m_output.write(END_SPAN);
      if (suffix != null)
        m_output.write(suffix);
      }
    }


  /**
   * Write a META tag with the given name and value.
   *
   * @param name the meta-tag name
   * @param value the meta-tag value
   */
  public void printMETATag(String name, String value) throws IOException
    {
    m_output.write("<meta name=\"");
    m_output.write(name);
    m_output.write("\" content=\"");
    m_output.write(value);
    m_output.write("\"");
    if (m_xhtml)
      m_output.write(" /");
    m_output.write(">" + NL);
    }


  /**
   * Write a pair of script tags enclosing a JavaScript command to open
   * a new window of the specified properties.
   *
   * @param url the target document for the new window
   * @param windowName an HTML name for the window
   * @param widthPart the fraction of the total screen width to be used as the window's width (0 to 1)
   * @param heightPart the fraction of the total screen height to be used as the window's height (0 to 1)
   */
  public void printNewWindowSCRIPT(String url, String windowName,
                                   float widthPart, float heightPart) throws IOException
    {
    printNewWindowSCRIPT(url, windowName, "\" + (" + widthPart + " * screen.availWidth) + \"",
              "\" + (" + heightPart + " * screen.availWidth) + \"");
    }


  /**
   * Write a pair of script tags enclosing a JavaScript command to open
   * a new window of the specified properties.
   *
   * @param url the target document for the new window
   * @param windowName an HTML name for the window
   * @param width a string to be inserted as the "width" attribute of the new window
   * @param height a string to be inserted as the "height" attribute of the new window
   */
  public void printNewWindowSCRIPT(String url, String windowName,
                                   String width, String height) throws IOException
    {
    if (url.startsWith("/"))
      url = m_contextPrefix + url;
    printSCRIPT("parameterString = \"width=" + width + ",height=" + height
              + ",location=no,menubar=yes,toolbar=yes,resizable=yes,scrollbars=yes,status=no\";"
              + NL + "window.open(\"" + url + "\", \"" + windowName + "\", parameterString);");
    }


  /**
   * Write 'value' itself iff it is not null.
   *
   * @param value any string or null
   */
  public void printNoNull(String value) throws IOException
    {
    if (value != null)
      m_output.write(value);
    }


  /**
   * Write 'value' itself if it is not null, or "&nbsp;" if it is.  This is
   * mainly for table cells, since some browsers don't properly show cell
   * borders around cells having no content.
   *
   * @param value any string or null
   */
  public void printNoNullTD(String value) throws IOException
    {
    m_output.write((value != null) ? value : "&nbsp;");
    }


  /**
   * Write 'value' itself if it is not null, or "&nbsp;" if it is.  This
   * is mainly for table cells, since some browsers don't properly show
   * cell borders around cells having no content.  Display no more than
   * 'maxLength' characters in the TD.
   *
   * @param value any string or null
   * @param maxLength a maximum length for the displayed value
   */
  public void printNoNullTDMax(String value, int maxLength) throws IOException
    {
    if (value == null)
      m_output.write("&nbsp;");
    else if (value.length() > maxLength)
      m_output.write(value.substring(0, maxLength));
    else
      m_output.write(value);
    }


  /**
   * Write an OPTION tag pair having 'text' as its output text.
   *
   * @param value a value for the VALUE attribute, or null
   * @param text the output text to be inserted between the tags
   */
  public void printOPTION(String value, String text) throws IOException
    {
    printOPTION(value, text, false);
    }


  /**
   * Write an OPTION tag pair having 'text' as its output text.  If 'value'
   * equals 'selectedValue', make this option selected.
   *
   * @param value a value for the VALUE attribute, or null
   * @param text the output text to be inserted between the tags
   * @param selectedValue a string which may equal 'value'
   */
  public void printOPTION(String value, String text, Object selectedValue) throws IOException
    {
    printOPTION(value, text, (selectedValue != null)
        && value.equals(selectedValue.toString()));
    }


  /**
   * Write an OPTION tag pair having 'text' as its output text.
   *
   * @param value a value for the VALUE attribute, or null
   * @param text the output text to be inserted between the tags
   * @param selected whether the SELECTED attribute should be inserted
   */
  public void printOPTION(String value, String text, boolean selected) throws IOException
    {
    m_output.write(NL + "    <option");
    if (value != null)
      {
      m_output.write(" value=\"");
      m_output.write(value);
      m_output.write("\"");
      }
    if (selected)
      if (m_xhtml)
        m_output.write(" selected=\"selected\"");
      else
        m_output.write(" selected");
    m_output.write(">");
    printHTMLEscape(text);
    m_output.write("</option>");
    }


  /**
   * Write the OPTIONS of a SELECT drop-down.  'options' is a Map or a List
   * used to populate the OPTIONs.  If 'options' is a List, use each value
   * for the key and the displayed text.
   *
   * @param options a Map or List containing the keys and display values
   */
  public void printOPTIONs(Object options) throws IOException
    {
    printOPTIONs(options, null);
    }


  /**
   * Write the OPTIONS of a SELECT drop-down.  'options' is a Map or a List
   * used to populate the OPTIONs.  Preselect any OPTION whose key matches
   * 'value'.  If 'options' is a List, use each value for the key and the
   * displayed text.
   * <br><br>
   * This method is effective when passed a LinkedHashMap, since that brings
   * order to the key set.
   *
   * @param options a Map or List containing the keys and display values
   * @param selectedValue a value to match for preselection
   */
  public void printOPTIONs(Object options, String selectedValue) throws IOException
    {
    boolean mapNotList = options instanceof Map;
    if (!mapNotList && !(options instanceof List))
      throw new IllegalArgumentException("'options' was neither a Map nor a List.");
    Object key = null;
    String text = null;
    if (mapNotList)
      for (Iterator i = ((Map)options).keySet().iterator(); i.hasNext(); )
        {
        key = i.next();
        text = (String)(((Map)options).get(key));
        printOPTION(key.toString(), text, selectedValue);
        }
    else
      for (Iterator i = ((List)options).iterator(); i.hasNext(); )
        {
        text = i.next().toString();
        printOPTION(text, text, selectedValue);
        }
    }


  /**
   * Write a PARAM tag for use within an APPLET tag pair.
   *
   * @param name the name of the applet parameter
   * @param value the applet parameter's value
   */
  public void printPARAM(String name, String value) throws IOException
    {
    m_output.write("<param name=\"");
    m_output.write(name);
    m_output.write("\" value=\"");
    m_output.write(value);
    m_output.write("\" />");
    }


  /**
   * Write a default INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   */
  public void printPasswordINPUT(String name) throws IOException
    {
    printINPUT(name, "password", 0, 0, null, null, 0);
    }


  /**
   * Write a custom INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   */
  public void printPasswordINPUT(String name, int size, int maxSize, String value) throws IOException
    {
    printINPUT(name, "password", size, maxSize, value, null, 0);
    }


  /**
   * Write a custom INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @param attributes additional attributes, or null
   */
  public void printPasswordINPUT(String name, int size, int maxSize, String value,
                                 String attributes) throws IOException
    {
    printINPUT(name, "password", size, maxSize, value, attributes, 0);
    }


  /**
   * Write a custom INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   */
  public void printPasswordINPUT(String name, int size, int maxSize,
                                 String value, int tabIndex) throws IOException
    {
    printINPUT(name, "password", size, maxSize, value, null, tabIndex);
    }


  /**
   * Write a custom INPUT tag of type PASSWORD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters
   * @param value a value to be placed in the text field by default, or null
   * @param attributes any additional attributes
   */
  public void printPasswordINPUT(String name, int size, int maxSize,
                                 String value, String attributes, int tabIndex) throws IOException
    {
    printINPUT(name, "password", size, maxSize, value, attributes, tabIndex);
    }


  /**
   * Write a JavaScript popup dialog to appear when a link is clicked.
   *
   * @param displayHTML the HTML to display for the link
   * @param popupText the text to be displayed in the dialog
   */
  public void printPopupLink(String displayHTML, String popupText) throws IOException
    {
    printA("/", displayHTML, null, "onclick='alert(\"" + Web1.AlertStringEscape(popupText) + "\"); return false;'");
    }


  /**
   * Write an INPUT tag of type RADIO.
   *
   * @param name the control's name
   * @param value the value for the input option
   */
  public void printRadioINPUT(String name, String value) throws IOException
    {
    printRadioINPUT(name, value, false);
    }


  /**
   * Write an INPUT tag of type RADIO.
   *
   * @param name the control's name
   * @param value the value for the input option
   * @param selected whether it should be preselected
   */
  public void printRadioINPUT(String name, String value, boolean selected) throws IOException
    {
    m_output.write("<input name=\"");
    m_output.write(name);
    m_output.write("\" type=\"radio\"");
    if (value != null)
      {
      m_output.write(" value=\"");
      m_output.write(value);
      m_output.write("\"");
      }
    if (selected)
      if (m_xhtml)
        m_output.write(" checked=\"checked\"");
      else
        m_output.write(" checked");
    m_output.write(" />");
    }


  /**
   * Write an input tag of type "reset".
   *
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   * @param attributes any additional attributes
   */
  public void printRESET(String value, String attributes) throws IOException
    {
    printSUBMIT(null, value, null, attributes, false);
    }


  /**
   * Write a JavaScript SCRIPT tag pair containing the script 'script'.
   *
   * @param script the JavaScript script to be enclosed in SCRIPT tags
   */
  public void printSCRIPT(String script) throws IOException
    {
    m_output.write(NL + SCRIPT + NL);
    m_output.write(script);
    m_output.write(NL + END_SCRIPT + NL);
    }


  /**
   * Write a JavaScript SCRIPT tag pair linking an external JavaScript file.
   *
   * @param url the URL of the JavaScript file to be included
   */
  public void printSCRIPTFile(String url) throws IOException
    {
    m_output.write(NL + "<script type=\"text/javascript\" src=\"");
    if (url.startsWith("/"))
      m_output.write(m_contextPrefix);
    m_output.write(url);
    m_output.write("\"></script>" + NL);
    }


  /**
   * Write a JavaScript function having signature 'signature' and 'code'
   * as its body.
   *
   * @param signature the function signature (in the form:  diddle(howManyTimes))
   * @param code the body of the function
   */
  public void printSCRIPTFunction(String signature, String code) throws IOException
    {
    m_output.write(NL + "function ");
    m_output.write(signature);
    m_output.write(NL + "  {" + NL);
    m_output.write(code);
    m_output.write(NL + "  }" + NL);
    }


  /**
   * Write a custom opening SELECT tag.
   *
   * @param name a value for the NAME attribute
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   */
  public void printBeginSELECT(String name, String attributes) throws IOException
    {
    m_output.write(NL + "  <select name=\"");
    m_output.write(name);
    m_output.write("\" id=\"");
    m_output.write(name);
    m_output.write("\"");
    if (attributes != null)
      m_output.write(" " + attributes);
    m_output.write(">");
    }


  /**
   * Write a complete SELECT input using 'options' to populate it.  Preselect
   * any value matching 'value'.  'options' must be either a Map or a List.
   * In the case of a List, each element is used as the value and the displayed
   * text.
   *
   * @param name the name of the input
   * @param options a Map or List to be used in populating the drop-down
   * @param selectedValue a value to match against
   */
  public void printSELECT(String name, Object options, String selectedValue) throws IOException
    {
    printSELECT(name, options, selectedValue, null);
    }


  /**
   * Write a complete SELECT input using 'options' to populate it.  Preselect
   * any value matching 'value'.  'options' must be either a Map or a List.
   * In the case of a List, each element is used as the value and the displayed
   * text.
   *
   * @param name the name of the input
   * @param options a Map or List to be used in populating the drop-down
   * @param selectedValue a value to match against
   * @param attributes any additional attributes needed
   */
  public void printSELECT(String name, Object options, String selectedValue,
                          String attributes) throws IOException
    {
    printBeginSELECT(name, attributes);
    printOPTIONs(options, selectedValue);
    m_output.write(END_SELECT);
    }


  /**
   * Write an IMG link to a single-pixel, transparent GIF image.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
   */
  public void printSpacer(int width, int height) throws IOException
    {
    printIMG(m_transpixelImageURL, null, width, height);
    }


  /**
   * Write an IMG link to a single-pixel, transparent GIF image.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   */
  public void printSpacer(int width, int height, String attributes) throws IOException
    {
    printIMG(m_transpixelImageURL, attributes, width, height);
    }


  /**
   * Write a block-level spacer.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
   */
  public void printSpacerBlock(int width, int height) throws IOException
    {
    m_output.write("<div class=\"spacer\">");
    printIMG(m_transpixelImageURL, null, width, height);
    m_output.write("</div>");
    }


  /**
   * Write a SPAN having the specified style.
   *
   * @param style the name of the CSS style to apply to this SPAN tag
   */
  public void printBeginSPAN(String style) throws IOException
    {
    m_output.write("<span class=\"");
    m_output.write(style);
    m_output.write("\">");
    }


  /**
   * Write a SPAN having the specified attributes.
   *
   * @param style the name of the CSS style to apply to this SPAN tag
   * @param text the HTML code to be placed in the tag pair
   */
  public void printSPAN(String style, String text) throws IOException
    {
    m_output.write("<span");
    if (style != null)
      {
      m_output.write(" class=\"");
      m_output.write(style);
      m_output.write("\"");
      }
    m_output.write(">");
    if (text != null)
      m_output.write(text);
    m_output.write(END_SPAN);
    }


  /**
   * Write 'string' with a backslash inserted before all double-quotes.
   *
   * @param string any string
   */
  public void printStringEscape(String string) throws IOException
    {
    m_output.write(String1.Replace(string, "\"", "\\\""));
    }


  /**
   * Write 'string' with a backslash inserted before all double-quotes and a blank string
   * if null.
   *
   * @param string any string
   */
  public void printStringEscapeNoNull(String string) throws IOException
    {
    if (string != null)
      printStringEscape(string);
    }


  /**
   * Write a STYLE tag pair containing the given style definitions.
   *
   * @param styles any number of CSS style definitions
   */
  public void printSTYLE(String styles) throws IOException
    {
    m_output.write("<style type=\"text/css\">" + NL + NL);
    m_output.write(styles);
    m_output.write(NL + NL + "</style>");
    }


  /**
   * Write a STYLE tag pair for the given StyleSheet.
   *
   * @param styleSheet a style sheet definition to be written
   */
  public void printSTYLE(StyleSheet styleSheet) throws IOException
    {
    m_output.write("<style type=\"text/css\">" + NL + NL);
    m_output.write(styleSheet.output());
    m_output.write(NL + NL + "</style>");
    }


  /**
   * Write a LINK tag importing a style sheet, for use in the HTML header.  No "TITLE"
   * attribute is included, so the link is "persistent" (this is normally what I want).
   *
   * @param url the URL of the style sheet
   */
  public void printStyleSheetLINK(String url) throws IOException
    {
    printStyleSheetLINK(url, null);
    }


  /**
   * Write a LINK tag importing a style sheet, for use in the HTML header.  If 'name' is not null,
   * it becomes the value of the "TITLE" attribute, so the link is a "preferred alternate".
   *
   * @param url the URL of the style sheet
   * @param name a name for the style sheet
   */
  public void printStyleSheetLINK(String url, String name) throws IOException
    {
    m_output.write(NL + "  <link rel=\"stylesheet\" href=\"");
    if (url.startsWith("/"))
      m_output.write(m_contextPrefix);
    m_output.write(url);
    m_output.write("\" type=\"text/css\"");
    if (name != null)
      {
      m_output.write(" title=\"");
      m_output.write(name);
      m_output.write("\"");
      }
    m_output.write(m_xhtml ? " />" : ">");
    }


  /**
   * Write a SUBMIT input tag having the given value text.
   *
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   */
  public void printSUBMIT(String value) throws IOException
    {
    printSUBMIT(null, value, null, null, true);
    }


  /**
   * Write a SUBMIT input tag having the given value text and the given name.
   *
   * @param name a parameter name for the submit
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   */
  public void printSUBMIT(String name, String value) throws IOException
    {
    printSUBMIT(name, value, null, null, true);
    }


  /**
   * Write a SUBMIT input tag having the given value text and the given name.
   * <br/><br/>
   * NOTE:  THE THIRD PARAMETER USED TO BE 'onClick'.
   *
   * @param name a parameter name for the submit
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   * @param attributes any additional attributes to be included
   */
  public void printSUBMIT(String name, String value, String attributes) throws IOException
    {
    printSUBMIT(name, value, null, attributes, true);
    }


  /**
   * Write a SUBMIT input tag having the given value text, the given name,
   * and an optional "onClick" handler.
   *
   * @param name a parameter name for the SUBMIT
   * @param value a value to be displayed on the SUBMIT control (and, alas, passed as a form parameter too)
   * @param onClick a JavaScript on-click handler command (sans quotes)
   * @param attributes any additional attributes to be included
   */
  public void printSUBMIT(String name, String value, String onClick,
                       String attributes, boolean submitNotReset) throws IOException
    {
    m_output.write(NL + "  <input");
    if (name != null)
      {
      m_output.write(" name=\"");
      m_output.write(name);
      m_output.write("\" id=\"");
      m_output.write(name);
      m_output.write("\"");
      }
    m_output.write(" type=\"");
    m_output.write(submitNotReset ? "submit" : "reset");
    m_output.write("\" value=\"");
    printStringEscape(value);
    m_output.write("\"");
    if (onClick != null)
      {
      m_output.write(" onClick='");
      m_output.write(onClick);
      m_output.write("'");
      }
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(" />");
    }


  /**
   * Write a custom opening TABLE tag.
   *
   * @param border the border value for the table in pixels
   * @param cellpadding the cell padding for the table in pixels
   * @param cellspacing the call spacing for the table in pixels
   */
  public void printBeginTABLE(int border, int cellpadding, int cellspacing) throws IOException
    {
    printBeginTABLE(border, cellpadding, cellspacing, null, null);
    }


  /**
   * Write a custom opening TABLE tag.
   *
   * @param border the border value for the table in pixels
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @param width a value for the WIDTH attribute, or null
   */
  public void printBeginTABLE(int border, String width, String attributes) throws IOException
    {
    printBeginTABLE(border, 0, 0, width, attributes);
    }


  /**
   * Write a custom opening TABLE tag.
   *
   * @param border the border value for the table in pixels
   * @param cellpadding the cell padding for the table in pixels
   * @param cellspacing the call spacing for the table in pixels
   * @param width a value for the WIDTH attribute, or null
   */
  public void printBeginTABLE(int border, int cellpadding, int cellspacing, String width) throws IOException
    {
    printBeginTABLE(border, cellpadding, cellspacing, width, null);
    }


  /**
   * Write a custom opening TABLE tag.
   *
   * @param border the border value for the table in pixels
   * @param cellpadding the cell padding for the table in pixels
   * @param cellspacing the call spacing for the table in pixels
   * @param width a value for the WIDTH attribute, or null
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   */
  public void printBeginTABLE(int border, int cellpadding, int cellspacing,
                         String width, String attributes) throws IOException
    {
    m_output.write("<table border=\"");
    m_output.write(Integer.toString(border));
    m_output.write("\" cellpadding=\"");
    m_output.write(Integer.toString(cellpadding));
    m_output.write("\" cellspacing=\"");
    m_output.write(Integer.toString(cellspacing));
    m_output.write("\"");
    if (width != null)
      {
      m_output.write(" width=\"");
      m_output.write(width);
      m_output.write("\"");
      }
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(">");
    }


  /**
   * Write a TD tag pair using style "tableHeader" and having 'label' as
   * its content.
   *
   * @param label the HTML to be inserted in the table cell
   */
  public void printTableHeaderCell(String label) throws IOException
    {
    printBeginTD(1, 1, null, "tableHeader");
    m_output.write(label);
    m_output.write(END_TD);
    }


  /**
   * Write an opening TBODY tag with the given attributes.
   *
   * @param attributes any attributes needed
   */
  public void printBeginTBODY(String attributes) throws IOException
    {
    m_output.write("<tbody");
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    m_output.write(">");
    }


  /**
   * Write a custom opening TD tag.
   *
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   */
  public void printBeginTD(String attributes) throws IOException
    {
    printBeginTD(1, 1, attributes, null);
    }


  /**
   * Write a custom opening TD tag.
   *
   * @param attributes an additional string of attributes to be inserted into the tag, or null
   * @param style the style for this table cell
   */
  public void printBeginTD(String attributes, String style) throws IOException
    {
    printBeginTD(1, 1, attributes, style);
    }


  /**
   * Write an opening TD tag with the given cellular dimensions.  Both
   * dimensions should be at least one cell.
   *
   * @param colspan the number of columns the TD should span
   * @param rowspan the number of rows the TD should span
   */
  public void printBeginTD(int colspan, int rowspan) throws IOException
    {
    printBeginTD(colspan, rowspan, null, null);
    }


  /**
   * Write an opening TD tag with the given cellular dimensions and other
   * attributes.  Both dimensions should be at least one cell.
   *
   * @param colspan the number of columns the TD should span
   * @param rowspan the number of rows the TD should span
   * @param attributes any additional attributes needed for the tag
   */
  public void printBeginTD(int colspan, int rowspan, String attributes) throws IOException
    {
    printBeginTD(colspan, rowspan, attributes, null);
    }


  /**
   * Write an opening TD tag with the given cellular dimensions and other
   * attributes.  Both dimensions should be at least one cell.
   *
   * @param colspan the number of columns the TD should span
   * @param rowspan the number of rows the TD should span
   * @param attributes any additional attributes needed for the tag
   * @param style an optional CSS style name for the cell
   */
  public void printBeginTD(int colspan, int rowspan, String attributes, String style) throws IOException
    {
    m_output.write("<td");
    if (colspan > 1)
      {
      m_output.write(" colspan=\"");
      m_output.write(Integer.toString(colspan));
      m_output.write("\"");
      }
    if (rowspan > 1)
      {
      m_output.write(" rowspan=\"");
      m_output.write(Integer.toString(rowspan));
      m_output.write("\"");
      }
    if (attributes != null)
      {
      m_output.write(" ");
      m_output.write(attributes);
      }
    if (style != null)
      {
      m_output.write(" class=\"");
      m_output.write(style);
      m_output.write("\"");
      }
    m_output.write(">");
    }


  /**
   * Write a custom TEXTAREA begin tag
   *
   * @param name the name of the input
   * @param rows the number of rows in the textarea
   * @param cols the number of columns in the textarea
   */
  public void printBeginTEXTAREA(String name, int rows, int cols) throws IOException
    {
    m_output.write(NL + "  <textarea name=\"");
    m_output.write(name);
    m_output.write("\" rows=\"");
    m_output.write(Integer.toString(rows));
    m_output.write("\" cols=\"");
    m_output.write(Integer.toString(cols));
    m_output.write("\">");
    }


  /**
   * Write a custom TEXTAREA begin and closing tag with the value in the middle.
   *
   * @param name the name of the input
   * @param rows the number of rows in the textarea
   * @param cols the number of columns in the textarea
   * @param value a value to be placed in the text field by default, or null
   */
  public void printTEXTAREA(String name, int rows, int cols, String value) throws IOException
    {
    printBeginTEXTAREA(name, rows, cols);
    if (value != null)
      m_output.write(value);
    m_output.write("</textarea>");
    }


  /**
   * Write a default INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   */
  public void printTextINPUT(String name) throws IOException
    {
    printINPUT(name, "text", 0, 0, null, null, 0);
    }


  /**
   * Write a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters (or 0)
   */
  public void printTextINPUT(String name, int size, int maxSize) throws IOException
    {
    printINPUT(name, "text", size, maxSize, null, null, 0);
    }


  /**
   * Write a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters (or 0)
   * @param value a value to be placed in the text field by default, or null
   */
  public void printTextINPUT(String name, int size, int maxSize, String value) throws IOException
    {
    printINPUT(name, "text", size, maxSize, value, null, 0);
    }


  /**
   * Write a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters (or 0)
   * @param value a value to be placed in the text field by default, or null
   * @param attributes any additional attributes
   */
  public void printTextINPUT(String name, int size, int maxSize, String value,
        String attributes) throws IOException
    {
    printINPUT(name, "text", size, maxSize, value, attributes, 0);
    }


  /**
   * Write a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters (or 0)
   * @param value a value to be placed in the text field by default, or null
   */
  public void printTextINPUT(String name, int size, int maxSize, String value,
                             int tabIndex) throws IOException
    {
    printINPUT(name, "text", size, maxSize, value, null, tabIndex);
    }


  /**
   * Write a custom INPUT tag of type TEXTFIELD.
   *
   * @param name the name of the input
   * @param size the visible length of the input in characters
   * @param maxSize the maximum length of this input's value in characters (or 0)
   * @param value a value to be placed in the text field by default, or null
   * @param attributes any additional attributes
   */
  public void printTextINPUT(String name, int size, int maxSize, String value,
                             String attributes, int tabIndex) throws IOException
    {
    printINPUT(name, "text", size, maxSize, value, attributes, tabIndex);
    }


  /**
   * Write an opening TR tag with the given CSS class name.
   *
   * @param style the style for this table row
   */
  public void printBeginTR(String style) throws IOException
    {
    if (style != null)
      {
      m_output.write("<tr class=\"");
      m_output.write(style);
      m_output.write("\">");
      }
    else
      m_output.write(TR);
    }


  /**
   * Write an IMG link to a single-pixel, white GIF image.
   *
   * @param width the width (to which to stretch the image on the page) in pixels
   * @param height the height (to which to stretch the image on the page) in pixels
  public void printWhitepixelIMG(int width, int height) throws IOException
    {
    printIMG(TranspixelImageURL, null, width, height);
    }
   */

  }
