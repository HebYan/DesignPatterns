package com.chipsandwaycool.servlet;

import com.chipsandwaycool.entity.player.PlayerManager;
import com.chipsandwaycool.entity.player.Player;
import com.chipsandwaycool.entity.game.GameManager;
import com.chipsandwaycool.entity.game.Game;
import com.chipsandwaycool.entity.team.TeamManager;
import com.chipsandwaycool.entity.team.Team;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * This is the home page for the March Madness Web site.  It outputs the
 * "bracket", diagramming the series of single-elimination games in the
 * tournament and showing the names of teams as they advance toward the final.
 * This content is public, so users need not be logged in to view it, but if
 * they are, any teams they have picked to win are highlighted.
 * <br/><br/>
 * A simple login form is displayed on this page, for access to the rest of
 * the site.
 */
public class Home extends MadnessServlet
  {

  /**
   * Initialize this servlet as needed.
   *
   * @param servletConfig an object for configuring this servlet
   */
  public void init(ServletConfig servletConfig) throws ServletException
    {
    super.init(servletConfig);
    m_securityBits = SECURITY_ANYBODY;
    }


  /**
   * Service a request to this servlet using a MadnessWriter for output.
   * If a certain argument is passed, this toggles a "skin" value in the
   * user's session, so that an alternate skin can be used.  This is an
   * easter egg:  the link for this parameter is hidden in a comma in the
   * welcome message.
   *
   * @param request a servlet request
   * @param response a servlet response
   * @param session the user's session
   * @param out a MadnessWriter for output
   */
  protected void doBoth(HttpServletRequest request, HttpServletResponse response,
        HttpSession session, MadnessWriter out) throws ServletException, IOException
    {
    if ("true".equals(request.getParameter(P_TOGGLE_SKIN)))
      {
      getSkinName(session, true);
      out = getHTMLWriter(request, response, session);
      }
    out.printPreContent(null, out.SCRIPTFile("/js/home.js"));
    Player player = null;
    if (session.getAttribute(P_PLAYER_ID) != null)
      {
      PlayerManager playerMan = PlayerManager.GetInstance();
      player = playerMan.select(session.getAttribute(P_PLAYER_ID), true);
      out.printBeginBox();
      out.printBeginDIV("smallHeading");
      out.print("Welcome");
      out.printA(URL_HOME + "?" + P_TOGGLE_SKIN + "=true", ",");
      out.print(" ");
      out.print(player.getNicknameOrFirstName());
      out.print("!" + END_DIV);
      out.printEndBox();
      out.printMenu(URL_HOME);
      }
    else
      {
      out.printBeginBox();
      String loginError = request.getParameter(P_LOGIN_ERROR);
      if (loginError != null)
        out.printDIV("formErrors", loginError);
      out.printBeginFORM(URL_LOGIN);
      out.print(TABLE + NL);
      out.print(NL + TR + TD + "Login: " + NBSP + " username ");
      out.printTextINPUT(P_USERNAME);
      out.print(NBSP + " password ");
      out.printPasswordINPUT(P_PASSWORD);
      out.print(NBSP + NBSP);
      out.printSUBMIT("Log in");
      out.print(NBSP + NBSP + NBSP + NBSP);
      out.printSUBMIT("instructions", "Get started");
      out.print(END_TD + END_TR + END_TABLE + END_FORM);
      out.printEndBox();
      }
    doBracket(out, player);
    if ((player != null) && player.getAdmin())
      out.printAdminMenu(null);
    out.printPostContent();
    }


  /**
   * Output the "bracket", showing the series of games in the tournament
   * and the winning teams as they become known.  If 'player' is not null
   * (indicating the user is logged in), highlight any teams which the player
   * has picked to win.
   *
   * @param out an HTML output writer
   * @param player a player object whose picks are to be highlighted
   */
  private void doBracket(MadnessWriter out, Player player) throws IOException
    {
    GameManager gameMan = GameManager.GetInstance();
    Game[] games = gameMan.getGamesInBracketOutputOrder();
    TeamManager teamMan = TeamManager.GetInstance();
    Map teams = teamMan.selectAllAsMap();
    out.printBeginBox();
    out.print(TABLE);
    int gameIndex = 0;
    String spacer = SP[25];
    for (int i = 0; i < 128; i++)
      {
      gameIndex = 0;
      out.print(NL + TR);
      out.printBeginTD(null, "bracketSpacer");
      out.printSpacer(1, 6);
      out.print(END_TD);
      for (int j = 0; j < 6; j++)
        {
        Game game = games[(i >> (j + 2)) + gameIndex];
        if ((i % (4 << j)) == 0)
          {
          out.printBeginTD(1, (1 << j) + 1, "class=\"bracketTop\"");
          if ((j == 0) && ((i % 32) == 0))
            out.printDIV("regionHeading", REGION_NAMES[i >> 5] + ":");
          if (game.getTeam1ID() >= 0)
            {
            Team team = (Team)(teams.get(new Integer(game.getTeam1ID())));
            out.printTeamForBracket(team, player);
            }
          else
            out.print(spacer);
          out.print(END_TD);
          }
        else if ((i % (4 << j)) == ((1 << j) + 1))
          {
          out.printBeginTD(1, 2 << j, "class=\"bracketBottom\"");
          if (game.getTeam2ID() >= 0)
            {
            Team team = (Team)(teams.get(new Integer(game.getTeam2ID())));
            out.printTeamForBracket(team, player);
            }
          else
            out.print(spacer);
          out.print(END_TD);
          }
        else if ((i % (4 << j)) == ((1 << j) * 3 + 1))
          {
          out.printBeginTD(1, (1 << j) - 1);
          out.print(END_TD);
          }
        gameIndex |= 32 >> j;
        }
      if (i == 0)
        {
        out.printBeginTD(1, 64, "class=\"bracketTop\"");
        Game game = games[62];
        if (game.getWinningTeamID() >= 0)
          {
          Team team = (Team)(teams.get(new Integer(game.getWinningTeamID())));
          out.printTeamForBracket(team, player);
          }
        else
          out.print(spacer);
        out.print(END_TD);
        }
      else if (i == 64)
        {
        out.printBeginTD(1, 64, "class=\"bracketLast\"");
        out.print(NBSP + END_TD);
        }
      out.print(NL + END_TR);
      }
    out.print(END_TABLE + BR + BR + BR);
    out.printEndBox();
    }

  }
