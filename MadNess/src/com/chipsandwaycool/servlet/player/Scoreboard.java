package com.chipsandwaycool.servlet.player;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.entity.player.Player;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.player.PlayerManager;
import com.chipsandwaycool.entity.player.PlayerWithScores;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * The Scoreboard is where each player (user) on this site is listed along
 * with their scores for each round, total scores and highest potential
 * scores, for comparison.
 */
public class Scoreboard extends MadnessServlet
  {

  /**
   * Initialize this servlet as needed.
   *
   * @param servletConfig an object for configuring this servlet
   */
  public void init(ServletConfig servletConfig) throws ServletException
    {
    super.init(servletConfig);
    m_securityBits = SECURITY_USER | SECURITY_ADMIN;
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
    Player currentPlayer = playerMan.select(session.getAttribute("playerID"), false);
    List players = playerMan.getPlayersWithScores();
    boolean linkPicks = GetCutoffDateIsPassed() || currentPlayer.getAdmin();
    out.printPreContent();
    out.printMenu(URL_SCOREBOARD);
    out.printBeginBox(true);
    out.printDIV("heading", "Scoreboard");
    out.printBeginTABLE(0, 0, 0, null, "class=\"scoreboard\"");
    out.print(NL + TR + TH + "Name");
    for (int i = 0; i < ROUND_NAMES.length; i++)
      {
      out.print(END_TH + TH);
      out.print(ROUND_NAMES[i]);
      }
    out.print(END_TH + TH + "Total" + END_TH + TH + "Potential" + END_TH + END_TR + NL);
    for (Iterator i = players.iterator(); i.hasNext(); )
      {
      PlayerWithScores player = (PlayerWithScores)(i.next());
      if (!player.getActive() && !currentPlayer.getAdmin())
        continue;
      out.print(TR);
      out.printBeginTD(null, "firstColumn");
      if (!player.getActive())
        out.printBeginSPAN("inactive");
      if (!linkPicks)
        out.print(player.getShortName());
      else
        out.printA(URL_PICKS + "?" + P_PLAYER_ID + "=" + player.getID(),
              player.getShortName(), player.getActive() ? null : "inactive");
      if (currentPlayer.getAdmin())
        {
        out.print(" ");
        out.printA(URL_EDIT_PLAYER + "?" + P_PLAYER_ID + "=" + player.getID(), "(edit)");
        }
      if (!player.getActive())
        out.print(END_SPAN);
      for (int j = 0; j < 6; j++)
        {
        out.print(END_TD + TD);
        out.print(player.getScore(j));
        }
      out.print(END_TD + TD);
      out.print(player.getTotalScore());
      out.print(END_TD + TD);
      out.print(player.getPotentialScore());
      out.print(END_TD + NL + END_TR);
      }
    out.printBeginTR("bottom");
    out.printBeginTD(9, 1);
    out.print(BR + END_TABLE_3);
    out.printEndBox();
    if (currentPlayer.getAdmin())
      out.printAdminMenu(null);
    out.printPostContent();
    }

  }
