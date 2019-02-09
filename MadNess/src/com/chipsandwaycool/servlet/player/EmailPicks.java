package com.chipsandwaycool.servlet.player;

import com.chipsandwaycool.utility.Emailer;
import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.team.TeamManager;
import com.chipsandwaycool.entity.team.Team;
import com.chipsandwaycool.entity.player.PlayerManager;
import com.chipsandwaycool.entity.player.Player;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.Iterator;

/**
 * This servlet uses an emailer component to send a list of everyone's picks
 * to everyone's email addresses.  Then it outputs a page indicating the
 * result.
 */
public class EmailPicks extends MadnessServlet
  {

  /**
   * Initialize this servlet as needed.
   *
   * @param servletConfig an object for configuring this servlet
   */
  public void init(ServletConfig servletConfig) throws ServletException
    {
    super.init(servletConfig);
    m_securityBits = SECURITY_ADMIN;
    }


  /**
   * Service a request to this servlet using a MadnessWriter for output.
   *
   * @param request a servlet request
   * @param response a servlet response
   * @param session the user's session
   * @param out a MadnessWriter for output
   */
  protected void doBoth(HttpServletRequest request, HttpServletResponse response,
        HttpSession session, MadnessWriter out) throws ServletException, IOException
    {
    out.printPreContent();
    out.printMenu(null);
    out.printBeginBox();
    if (emailPicks())
      {
      out.printDIV("heading", "Emails Sent");
      out.print("All emails were sent successfully.");
      out.print(BR + BR + "Be careful not to reload this page, for that will resend all the emails.");
      }
    else
      out.print("Unfortunately, one or more emails could not be sent.  There are many reasons why this might be.  Possibly one of the players' email addresses is malformed.");
    out.printEndBox();
    out.printPostContent();
    }


  /**
   * Email a list of all players' team picks to all the players having email
   * addresses.  Return whether all emails were sent successfully.
   *
   * @return whether there were any problems sending the emails
   */
  private boolean emailPicks()
    {
    PlayerManager playerMan = PlayerManager.GetInstance();
    List players = playerMan.selectAll(true);
    TeamManager teamMan = TeamManager.GetInstance();
    Map teams = teamMan.selectAllAsMap();
    StringBuffer body = new StringBuffer();
    body.append("Hi Poolsters!"
          + "\n\nHere's a list of the picks for each player in this year's pool. This information is identical to what you'll see when you click on the Team Picks as CSV link on the Web site."
          + "\n\nGood luck!"
          + "\n\nYour pool hostesses,"
          + "\nChips and Way Cool");
    for (Iterator i = players.iterator(); i.hasNext(); )
      {
      Player player = (Player)(i.next());
      if (!player.getActive())
        continue;
      body.append("\n\n\n");
      body.append(player.getCompleteName());
      if (player.getEmail() != null)
        {
        body.append(" (");
        body.append(player.getEmail());
        body.append(")");
        }
      body.append(":\n");
      for (int j = 19; j >= 0; j--)
        {
        body.append("\nPick #");
        body.append(j + 1);
        body.append(":  ");
        if (j < 9)
          body.append(" ");
        Team team = (Team)(teams.get(new Integer(player.getPick(j))));
        if (team != null)
          body.append(team.getHome());
        else
          body.append("(no pick)");
        }
      }
    Emailer emailer = new Emailer();
    String subject = "Chips and Way Cool Team Picks";
    boolean allEmailsSucceeded = true;
    for (Iterator i = players.iterator(); i.hasNext(); )
      {
      Player player = (Player)(i.next());
      if (!player.getActive() || (player.getEmail() == null))
        continue;
      if (!emailer.sendEmail(null, player.getEmail(), subject, body.toString()))
        allEmailsSucceeded = false;
      }
    return allEmailsSucceeded;
    }

  }
