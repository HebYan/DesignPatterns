package com.chipsandwaycool.servlet.game;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.game.GameManager;
import com.chipsandwaycool.entity.game.Game;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;

/**
 * This guy responds to the Edit Winners form by assigning the winners of
 * games according to the user input and then asking the business object
 * to "reconcile" the user's selections.  This ensures a state of integrity
 * among the whole series of games, since the user might assign a winner to
 * a game in round three but also change the winner of a precedent game,
 * making the first selection invalid.  Once all the changes are thus
 * reconciled, they are stored in the database.
 */
public class EditWinnersAction extends MadnessServlet
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
    for (int i = 0; i < games.length; i++)
      {
      String teamID = request.getParameter(P_GAME + games[i].getID());
      if (teamID != null)
        games[i].setWinningTeamID(Integer.parseInt(teamID));
      }
    gameMan.reconcileAndSaveGames(games);
    getServletContext().getRequestDispatcher(URL_EDIT_WINNERS).forward(request, response);
    }

  }
