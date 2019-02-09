package com.chipsandwaycool.servlet.utility;

import com.chipsandwaycool.entity.team.Team;
import com.chipsandwaycool.entity.player.Player;
import com.chipsandwaycool.servlet.MadnessConstants;
import com.chipsandwaycool.servlet.MadnessServlet;
import com.elseforif.servlet.utility.HTMLFlexiWriter;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class extends HTMLFlexiWriter to add output services specific to the
 * March Madness Web site.  The design is to make it as simple as possible for
 * servlets to output a page--encapsulating any HTML constructs which may be
 * common to more than one servlet--without sacrificing any flexibility.
 * <br/><br/>
 * Most servlets will call 'printPreContent()' first, to output everything up
 * to the specific content of each one; and then 'printPostContent()' will
 * complete the page, closing any tags opened by the former method.
 * <br/><br/>
 * The 'PAGE_STATE...' constants are just a means of keeping rough track of
 * where we are in the page output, in case an exception is thrown.  This
 * allows us, in most cases, to output an error message and complete the page
 * in a coherent fashion.
 */
public class MadnessWriter extends HTMLFlexiWriter implements MadnessConstants
  {

  public static final String DEFAULT_TITLE = "The Chips and Way Cool NCAA March Madness Pool";

  protected static final int PAGE_STATE_NO_OUTPUT_YET = 0;
  protected static final int PAGE_STATE_PRE_CONTENT = 1;
  protected static final int PAGE_STATE_BOX_STARTED = 2;

  protected int   m_pageState = PAGE_STATE_NO_OUTPUT_YET;



  /**
   * Construct a MadnessWriter wrapper for PrintWriter 'output'.  Add the
   * given context prefix to all URLs outputs that begin with "/".
   *
   * @param output the PrintWriter I will use for 'write...()' calls.
   * @param contextPrefix the context prefix under which I'm running.
   */
  public MadnessWriter(PrintWriter output, String contextPrefix)
    {
    super(output, contextPrefix);
    m_preHeadCode = DOCTYPE_XHTML_STRICT;
    m_transpixelImageURL = "/img/transpixel.gif";
    }


  /**
   * Output the first part of a page, including the HEAD tag, the opening BODY
   * tag, and any content common to most pages on this site.
   */
  public void printPreContent() throws IOException
    {
    printPreContent(null, null);
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
    printBeginTABLE(0, 0, 0, "100%");
    print(TR);
    printBeginTD("style=\"width: 40px;\"");
    print(END_TD);
    printBeginTD(null, "logo");
    printA(URL_HOME, IMG("/img/logo.gif", 370, 83));
    print(END_TD + TD);
    printSpacer(364, 1);
    print(END_TABLE_3 + NL + NL);
    m_pageState = PAGE_STATE_PRE_CONTENT;
    }


  /**
   * Output a beginning HEAD tag with page title 'title' and including the code
   * in 'additionalAttributes', if any.
   * <br/><br/>
   * This method is an override of superclass HTMLWriter.  Effectively, it just
   * adds some default tags to the 'additionalStuff' argument, which is then
   * passed back up to the superclass.  'printHEAD()', which is called in
   * 'printPreContent()' above, invokes this method.
   *
   * @param title the page title
   * @param additionalStuff any extra code to be inserted between the HEAD tags.
   */
  public void printBeginHEAD(String title, String additionalStuff) throws IOException
    {
    StringBuffer headCode = new StringBuffer(150);
    headCode.append(iconLINK("/img/siteIcon.ico"));
    headCode.append(styleSheetLINK("/css/default.css"));
    if (additionalStuff != null)
      {
      headCode.append(NL);
      headCode.append(additionalStuff);
      }
    super.printBeginHEAD(title, headCode.toString());
    }


  /**
   * Output the last part of the page, any footer-type content, etc., making
   * sure any tags opened by 'printPreContent()' are duly closed.
   */
  public void printPostContent() throws IOException
    {
    printBeginDIV("footer");
    printA(URL_ELSEFORIF, IMG("/img/elseforif.gif", 88, 31));
    print("Copyright &copy; 2007" + BR + "by Else For If");
    print(END_DIV + BOTH_END_TAGS);
    }


  /**
   * Output a "box" containing a menu of links for those who have logged in,
   * not including the administrative options.  Exclude the link for
   * 'sourceURL', since that is that page we're now displaying.
   *
   * @param sourceURL the URL to be excluded
   */
  public void printMenu(String sourceURL) throws IOException
    {
    printBeginBox();
    if (!URL_HOME.equals(sourceURL))
      printMenuItem(URL_HOME, "Bracket", "The Home page shows the bracket for all regions.");
    if (!URL_PICKS.equals(sourceURL))
      printMenuItem(URL_PICKS, "Pick Teams", "Select your 20 winning teams before the games start!");
    if (!URL_PICKS_CSV.equals(sourceURL) && MadnessServlet.GetCutoffDateIsPassed())
      printMenuItem(URL_PICKS_CSV, "Team Picks as CSV", "Click this to download everyone's picks as a spreadsheet.");
    if (!URL_SCOREBOARD.equals(sourceURL))
      printMenuItem(URL_SCOREBOARD, "Scoreboard", "This shows everyone's current scores.");
    if (!URL_INSTRUCTIONS.equals(sourceURL))
      printMenuItem(URL_INSTRUCTIONS, "Instructions", "Learn all about this Web site.");
    printMenuItem(URL_BRACKET_IMAGE, "Print Bracket", "A GIF image of the bracket should print nicely.");
    printMenuItem(URL_LOGIN + "?" + P_LOGOUT + "=true", "Log Out", "Click this link to log out.");
    printEndBox();
    }


  /**
   * Output a "box" containing a menu of links for admin users who have logged
   * in.  Exclude the link for 'sourceURL', since that is that page we're now
   * displaying.
   *
   * @param sourceURL the URL to be excluded
   */
  public void printAdminMenu(String sourceURL) throws IOException
    {
    printBeginBox();
    if (!URL_EDIT_PLAYER.equals(sourceURL))
      printMenuItem(URL_EDIT_PLAYER, "Add a New Player", "Add a new player to the pool.");
    if (!URL_EDIT_WINNERS.equals(sourceURL))
      printMenuItem(URL_EDIT_WINNERS, "Enter Winning Teams", "Enter the winners of current games.");
    if (!URL_EDIT_TEAMS.equals(sourceURL))
      printMenuItem(URL_EDIT_TEAMS, "Edit Team Names", "Enter the names of all teams and their universities.");
    printEndBox();
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
    printIMG("/img/ballIcon.gif", 24, 24);
    print(NBSP + NBSP);
    printA(url, linkText);
    print(NBSP + " ." + NBSP + " ." + NBSP + " . " + NBSP + I);
    print(Character.toLowerCase(label.charAt(0)) + label.substring(1));
    print(END_I + END_DIV);
    }


  /**
   * Output some tags to begin some sort of box-like structure.  Call this
   * method to begin the box, then output its contents, and last call
   * 'printEndBox()' to complete the construct.
   */
  public void printBeginBox() throws IOException
    {
    printBeginBox(false);
    }


  /**
   * Output some tags to begin some sort of box-like structure.  Call this
   * method to begin the box, then output its contents, and last call
   * 'printEndBox()' to complete the construct.  Center the contents of the
   * box, iff 'center'.
   *
   * @param center whether the contents of the box should be centered
   */
  public void printBeginBox(boolean center) throws IOException
    {
    String spacer = spacer(8, 8);
    print(NL);
    printBeginTABLE(0, 0, 0, null, "class=\"box\"");
    print(TR);
    printBeginTD(null, "boxborder-nw");
    print(spacer);
    print(END_TD);
    printBeginTD(null, "boxborder-n");
    print(spacer);
    print(END_TD);
    printBeginTD(null, "boxborder-ne");
    print(spacer);
    print(END_TD + END_TR + TR);
    printBeginTD(null, "boxborder-w");
    print(spacer);
    print(END_TD);
    printBeginTD(null, center ? "boxborder-center centered" : "boxborder-center");
    m_pageState = PAGE_STATE_BOX_STARTED;
    }


  /**
   * Close any tags opened by 'printBeginBox()', completing the box.
   */
  public void printEndBox() throws IOException
    {
    String spacer = spacer(8, 8);
    print(END_TD);
    printBeginTD(null, "boxborder-e");
    print(spacer);
    print(END_TD + END_TR + TR);
    printBeginTD(null, "boxborder-sw");
    print(spacer);
    print(END_TD);
    printBeginTD(null, "boxborder-s");
    print(spacer);
    print(END_TD);
    printBeginTD(null, "boxborder-se");
    print(spacer);
    print(END_TABLE_3 + NL);
    m_pageState = PAGE_STATE_PRE_CONTENT;
    }


  /**
   * Output a simple page indicating an error of some kind.
   *
   * @param error the error itself
   */
  public void printErrorPage(Throwable error) throws IOException
    {
    if (m_pageState == PAGE_STATE_NO_OUTPUT_YET)
      printPreContent();
    if (m_pageState != PAGE_STATE_BOX_STARTED)
      printBeginBox();
    else
      print(NBSP + BR + BR);
    printBeginDIV("smallHeading");
    if (error instanceof ServletException)
      {
      print("Error:  ");
      print(error.getMessage());
      }
    else
      print("There was some problem loading this page.  Sorry!");
    print(END_DIV);
    printEndBox();
    printPostContent();
    }


  /**
   * Write a custom opening TD tag.  This is overridden just to add a newline,
   * which is not done in the superclass for reasons of browser compatibility.
   *
   * @param colspan the number of columns the TD should span
   * @param rowspan the number of rows the TD should span
   * @param attributes any additional attributes needed for the tag
   * @param style an optional CSS style name for the cell
   */
  public void printBeginTD(int colspan, int rowspan, String attributes, String style) throws IOException
    {
    print(NL);
    super.printBeginTD(colspan, rowspan, attributes, style);
    }


  /**
   * Output a little HTML displaying the given Team object, formatted
   * to fit into the "bracket" diagram on the home page.
   *
   * @param team the team to be displayed
   */
  public void printTeamForBracket(Team team, Player player) throws IOException
    {
    if ((player != null) && (player.getHasPickedTeam(team.getID())))
      {
      printBeginSPAN("bracketNamePicked");
      printHTMLEscape(team.getHome());
      print(" (");
      print((team.getRank() & 15) + 1);
      print("/");
      printSPAN("bracketNamePoints",
            Integer.toString(player.getPointsForTeam(team.getID())));
      print(")" + END_SPAN);
      }
    else
      {
      printBeginSPAN("bracketName");
      printHTMLEscape(team.getHome());
      print(" (");
      print((team.getRank() & 15) + 1);
      print(")" + END_SPAN);
      }
    }


  /**
   * Output a string value into a CSV file, enclosing it in quotes (if it is
   * not null) and escaping any embedded quotes as needed.
   *
   * @param value the string value
   */
  public void printCSVString(String value) throws IOException
    {
    if (value == null)
      return;
    print("\"");
    print(value.replaceAll("\"", "\"\""));
    print("\"");
    }

  }
