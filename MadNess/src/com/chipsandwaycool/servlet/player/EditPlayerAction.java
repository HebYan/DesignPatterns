package com.chipsandwaycool.servlet.player;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.player.PlayerManager;
import com.chipsandwaycool.entity.player.Player;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;

/**
 * Here's the action handler for the Edit Player form page.  If there are
 * any errors in the form inputs, this sends an error back to Edit Player.
 * Otherwise, it will either save an existing Player or create a new one.
 */
public class EditPlayerAction extends MadnessServlet
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
    PlayerManager playerMan = PlayerManager.GetInstance();
    Player player = null;
    if (request.getParameter(P_PLAYER_ID) != null)
      player = playerMan.select(request.getParameter(P_PLAYER_ID), false);
    else
      player = new Player();
    player.setActive("true".equals(request.getParameter(P_ACTIVE)));
    player.setUsername(scrubInput(request.getParameter(P_USERNAME)));
    player.setPassword(scrubInput(request.getParameter(P_PASSWORD)));
    player.setFirstName(scrubInput(request.getParameter(P_FIRST_NAME)));
    player.setLastName(scrubInput(request.getParameter(P_LAST_NAME)));
    player.setNickname(scrubInput(request.getParameter(P_NICKNAME)));
    player.setEmail(scrubInput(request.getParameter(P_EMAIL)));
    player.setAdmin("true".equals(request.getParameter(P_ADMIN)));
    StringBuffer errors = new StringBuffer();
    if (player.getFirstName() == null)
      errors.append("A first name is required." + BR);
    if (player.getLastName() == null)
      errors.append("A last name is required." + BR);
    if (player.getUsername() == null)
      errors.append("A login name is required." + BR);
    else if ((player.getID() == -1) && playerMan.getUsernameInUse(player.getUsername()))
      errors.append("The login name you entered is already in use.");
    if (player.getPassword() == null)
      errors.append("You must enter a password." + BR);
    if (errors.length() > 0)
      {
      request.setAttribute(P_ERRORS, errors.toString());
      getServletContext().getRequestDispatcher(URL_EDIT_PLAYER).forward(request, response);
      }
    else
      {
      if (player.getID() == -1)
        playerMan.insert(player);
      else
        playerMan.update(player);
      getServletContext().getRequestDispatcher(URL_SCOREBOARD).forward(request, response);
      }
    }

  }
