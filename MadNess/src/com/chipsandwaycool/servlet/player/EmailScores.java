package com.chipsandwaycool.servlet.player;

import com.chipsandwaycool.entity.player.PlayerWithScores;
import com.chipsandwaycool.utility.Emailer;
import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.team.TeamManager;
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
 * This servlet uses the Emailer to send a list of everyone's current scores
 * to everyone else.  It then indicates success or failure to send all emails.
 */
public class EmailScores extends MadnessServlet
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
    if (emailScores())
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
   * Email a list of all players' scores to all the players having email
   * addresses.  Return whether all emails were sent successfully.
   *
   * @return whether there were any problems sending the emails
   */
  private boolean emailScores()
    {
    PlayerManager playerMan = PlayerManager.GetInstance();
    List players = playerMan.getPlayersWithScores();
    StringBuffer body = new StringBuffer();
    body.append("Hi Poolsters!"
          + "\n\nFollowing is a snapshot of the current standings after the most recent round of play.  The table lists each player and his or her current score (in descending order), as well as the highest potential score for the pool at this point in time. The two numbers should help you determine if you have a prayer as we move into the next round."
          + "\n\nYour pool hostesses,"
          + "\nChips and Way Cool");
    body.append("\n\n\n  Player Name                   Current Score    Potential Score");
    body.append("\n  --------------------------------------------------------------");
    for (Iterator i = players.iterator(); i.hasNext(); )
      {
      PlayerWithScores player = (PlayerWithScores)(i.next());
      if (!player.getActive())
        continue;
      body.append("\n  ");
      String datum = player.getCompleteName();
      body.append(datum);
      for (int j = datum.length(); j < 30; j++)
        body.append(" ");
      datum = Integer.toString(player.getTotalScore());
      body.append(datum);
      for (int j = datum.length(); j < 17; j++)
        body.append(" ");
      body.append(player.getPotentialScore());
      }
    Emailer emailer = new Emailer();
    String subject = "Chips and Way Cool Current Scores";
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
