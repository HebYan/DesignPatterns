package com.chipsandwaycool.servlet.player;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.player.PlayerManager;
import com.chipsandwaycool.entity.player.Player;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This servlet processes the form input submitted through the Picks page.
 * Users should have selected twenty different teams on that page, assigning
 * each one a points value, all of which data are persisted here, and then
 * we forward back to the Picks page.
 */
public class PicksAction extends MadnessServlet
  {

  /**
   * Initialize this servlet as needed.
   *
   * @param servletConfig an object for configuring this servlet
   */
  public void init(ServletConfig servletConfig) throws ServletException
    {
    super.init(servletConfig);
    m_securityBits = SECURITY_USER | SECURITY_ADMIN | SECURITY_NO_POSTCUTOFF;
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
    Player player = playerMan.select(session.getAttribute(P_PLAYER_ID), false);
    String playerID = request.getParameter(P_PLAYER_ID);
    if (!player.getAdmin())
      if (playerID != null)
        throw new ServletException("You may not edit other players' picks.");
      else if (GetCutoffDateIsPassed())
        throw new ServletException("You may not edit your picks after the cutoff date:  " + CutoffDate + ".");
    int[] picks = new int[20];
    for (int i = 0; i < picks.length; i++)
      {
      String value = request.getParameter(P_PICK + (20 - i));
      if (value == null)
        throw new NullPointerException("That's funny:  the PicksAction servlet was called with one or more missing request parameters.");
      picks[i] = Integer.parseInt(value);
      }
    Integer playerIDInteger = (playerID != null)
          ? new Integer(playerID) : new Integer(player.getID());
    playerMan.setPlayerPicks(playerIDInteger, picks);
    request.setAttribute(P_SUCCESS, "true");
    getServletContext().getRequestDispatcher(URL_PICKS).forward(request, response);
    }

  }
