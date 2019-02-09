package com.chipsandwaycool.servlet.utility;

import java.io.PrintWriter;
import java.io.IOException;

/**
 * This HTML writer represents one means of "skinning" a Web site.  Since it
 * is the primary object for generating HTML output and is responsible for
 * adding style sheet links, everything style-specific can be encapsulated
 * in a CSS file and this class.
 */
public class MadnessWriterSkin1 extends MadnessWriter
  {

  private static String Logo = null;



  /**
   * Construct a skin-specific MadnessWriter wrapper for PrintWriter 'output'.
   *
   * @param output the PrintWriter I will use for 'write...()' calls.
   * @param contextPrefix the context prefix under which I'm running.
   */
  public MadnessWriterSkin1(PrintWriter output, String contextPrefix)
    {
    super(output, contextPrefix);
    }


  /**
   * Add a skin-specific style sheet to the HEAD tag, for this "skin".
   *
   * @param title the page title
   * @param additionalStuff additional tags to be inserted between the HEAD tags.
   */
  public void printBeginHEAD(String title, String additionalStuff) throws IOException
    {
    super.printBeginHEAD(title, (additionalStuff != null)
          ? additionalStuff + NL + styleSheetLINK("/skin1/css/skin1.css")
          : styleSheetLINK("/skin1/css/skin1.css"));
    }


  /**
   * Output the first part of a page, including the HEAD tag, the opening BODY
   * tag, and any content common to most pages on this site.
   *
   * @param title a title for the page
   * @param additionalHeadStuff any other content to be added to the HEAD tag
   */
  public void printPreContent(String title, String additionalHeadStuff) throws IOException
    {
    if (title == null)
      title = DEFAULT_TITLE;
    printHEAD(title, additionalHeadStuff);
    print(NL + BODY + NL + NL);
    printBeginDIV("logo");
    if (Logo == null)
      {
      StringBuffer logo = new StringBuffer();
      logo.append("The Chips and Way Cool");
      logo.append(SPAN("logo1", " NCAA March "));
      logo.append(SPAN("logo10", "M"));
      logo.append(SPAN("logo11", "a"));
      logo.append(SPAN("logo12", "d"));
      logo.append(SPAN("logo13", "n"));
      logo.append(SPAN("logo14", "e"));
      logo.append(SPAN("logo15", "s"));
      logo.append(SPAN("logo16", "s"));
      logo.append(" Pool");
      Logo = logo.toString();
      }
    printA(URL_HOME, Logo);
    print(END_DIV + NL + NL);
    m_pageState = PAGE_STATE_PRE_CONTENT;
    }


  /**
   * Output a single menu item, for the given URL.
   *
   * @param url the URL for the link
   * @param label the displayed text for the link
   */
  public void printMenuItem(String url, String linkText, String label) throws IOException
    {
    print(NL);
    printBeginDIV("menuItem");
    printIMG("/skin1/img/ballIcon.gif", 24, 24);
    print(NBSP + NBSP);
    printA(url, linkText);
    print(" . . . " + I);
    print(Character.toLowerCase(label.charAt(0)) + label.substring(1));
    print(END_I + END_DIV);
    }


  /**
   * Output some tags to begin some sort of box-like structure.  Call this
   * method to begin the box, then output its contents, and last call
   * 'printEndBox()' to complete the contruct.
   *
   * @param center whether the contents of the box should be centered
   */
  public void printBeginBox(boolean center) throws IOException
    {
    print(NL);
    printBeginTABLE(0, 0, 0, null, "class=\"box\"");
    print(TR);
    printBeginTD(null, "boxborder-nw");
    printBeginDIV("boxborder-nw");
    printSpacer(16, 30);
    print(END_DIV + END_TD);
    printBeginTD(1, 2, "class=\"boxborder-center\"");
    printBeginTABLE(0, 0, 0, "100%");
    print(TR);
    printBeginTD(null, "boxborder-nnw");
    printSpacer(16, 16);
    print(END_TD);
    printBeginTD(null, "boxborder-n");
    printSpacer(16, 16);
    print(END_TD);
    printBeginTD(null, "boxborder-nne");
    printSpacer(16, 16);
    print(END_TABLE_3);
    printBeginDIV(null, center ? "class=\"boxborder-center centered\""
          : "class=\"boxborder-center\"");
    m_pageState = PAGE_STATE_BOX_STARTED;
    }


  /**
   * Close any tags opened by 'printBeginBox()', completing the box.
   */
  public void printEndBox() throws IOException
    {
    print(END_DIV);
    printBeginTABLE(0, 0, 0, "100%");
    print(TR);
    printBeginTD(null, "boxborder-ssw");
    printSpacer(16, 24);
    print(END_TD);
    printBeginTD(null, "boxborder-s");
    printSpacer(16, 24);
    print(END_TD);
    printBeginTD(null, "boxborder-sse");
    printSpacer(16, 24);
    print(END_TABLE_3 + END_TD);
    printBeginTD(null, "boxborder-ne");
    printBeginDIV("boxborder-ne");
    printSpacer(16, 30);
    print(END_DIV + END_TD + END_TR + TR);
    printBeginTD(null, "boxborder-sw");
    printBeginDIV("boxborder-sw");
    printSpacer(16, 30);
    print(END_DIV + END_TD);
    printBeginTD(null, "boxborder-se");
    printBeginDIV("boxborder-se");
    printSpacer(16, 30);
    print(END_DIV + END_TABLE_3 + NL);
    m_pageState = PAGE_STATE_PRE_CONTENT;
    }

  }
