package com.chipsandwaycool.servlet.game;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.game.GameManager;
import com.chipsandwaycool.entity.game.Game;
import com.chipsandwaycool.entity.team.TeamManager;
import com.chipsandwaycool.entity.team.Team;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;
import java.util.Map;

/**
 * The Edit Winners page is used to enter and modify the winners of any game
 * for which both teams are known.  Initially, the teams are known only for
 * the first round of games, but as their winners are entered, subsequent
 * games' teams are assigned automatically, and those games also appear on
 * this page.
 */
public class EditWinners extends MadnessServlet
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
    GameManager gameMan = GameManager.GetInstance();
    Game[] games = gameMan.getGamesInBracketOutputOrder();
    TeamManager teamMan = TeamManager.GetInstance();
    Map teams = teamMan.selectAllAsMap();
    out.printPreContent();
    out.printMenu(null);
    out.printBeginBox(true);
    out.printDIV("heading", "Select Game Winners");
    out.print(NL);
    out.printBeginFORM(URL_EDIT_WINNERS_ACTION);
    out.printBeginTABLE(0, 0, 0, "100%", "class=\"scoreboard\"");
    out.print(NL + TR);
    for (int i = 0; i < ROUND_NAMES.length; i++)
      {
      out.print(TH);
      out.print(ROUND_NAMES[i]);
      out.print(END_TH);
      }
    out.print(END_TR + TR);
    out.printBeginTD(null, "firstColumn");
    boolean firstInColumn = true;
    for (int i = 0; i < games.length; i++)
      {
      if ((games[i].getTeam1ID() >= 0) && (games[i].getTeam2ID() >= 0))
        {
        if (!firstInColumn)
          out.print(BR + BR + NL);
        firstInColumn = false;
        out.print("Game ");
        out.print(games[i].getPosition() + 1);
        out.print(":" + BR);
        out.printBeginSELECT(P_GAME + games[i].getID(), null);
        String winningTeamID = Integer.toString(games[i].getWinningTeamID());
        out.printOPTION("-1", "(unknown)", winningTeamID);
        Team team = (Team)(teams.get(new Integer(games[i].getTeam1ID())));
        out.printOPTION(Integer.toString(team.getID()), team.getHome(), winningTeamID);
        team = (Team)(teams.get(new Integer(games[i].getTeam2ID())));
        out.printOPTION(Integer.toString(team.getID()), team.getHome(), winningTeamID);
        out.print(NL + END_SELECT + NL);
        }
      if ((i == 31) || (i == 47) || (i == 55) || (i == 59) || (i == 61))
        {
        out.print(NBSP + END_TD + TD);
        firstInColumn = true;
        }
      }
    out.print(NBSP + END_TD + END_TR);
    out.printBeginTR("bottom");
    out.printBeginTD(6, 1);
    out.print(BR);
    out.printSUBMIT(P_SAVE, "Save");
    out.print(END_TD + END_TR + END_TABLE + END_FORM);
    out.printEndBox();
    out.printBeginBox();
    out.printMenuItem(URL_EMAIL_PICKS, "Email All Picks To All Players", "Send an email to active players, showing all their picks.");
    out.printMenuItem(URL_EMAIL_SCORES, "Email Scores To All Players", "Email the Scoreboard to all active players.");
    out.printEndBox();
    out.printPostContent();
    }

  }
