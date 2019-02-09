package com.chipsandwaycool.servlet.player;

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
 * This servlet outputs a CSV file (with the appropriate content-type header)
 * containing a list of users on this site and the twenty teams they picked
 * on the "Pick Teams" page.  Only the string names of both the players and
 * the teams are included, not the IDs, since this is just for humans to read.
 */
public class PicksCSV extends MadnessServlet
  {

  /**
   * Initialize this servlet as needed.
   *
   * @param servletConfig an object for configuring this servlet
   */
  public void init(ServletConfig servletConfig) throws ServletException
    {
    super.init(servletConfig);
    m_securityBits = SECURITY_USER | SECURITY_ADMIN | SECURITY_NO_PRECUTOFF;
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
    PlayerManager playerMan = PlayerManager.GetInstance();
    List players = playerMan.selectAll(true);
    TeamManager teamMan = TeamManager.GetInstance();
    Map teams = teamMan.selectAllAsMap();
    response.setContentType("text/csv");
    out.print("\"Player Name\",\"Email\"");
    for (int i = 20; i > 0; i--)
      {
      out.print(",\"Pick ");
      out.print(i);
      out.print("\"");
      }
    for (Iterator i = players.iterator(); i.hasNext(); )
      {
      Player player = (Player)(i.next());
      if (!player.getActive())
        continue;
      out.print(NL);
      out.printCSVString(player.getCompleteName());
      out.print(",");
      if (player.getEmail() != null)
        out.printCSVString(player.getEmail());
      for (int j = 19; j >= 0; j--)
        {
        out.print(",");
        Team team = (Team)(teams.get(new Integer(player.getPick(j))));
        if (team != null)
          out.printCSVString(team.getHome());
        else
          out.print("\"(no pick)\"");
        }
      }
    }

  }
