package com.chipsandwaycool.servlet;

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
 * The Login servlet processes the login form displayed on the home page,
 * redirecting back to that page upon successful login or to display an
 * authentication error.
 */
public class Login extends MadnessServlet
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
   *
   * @param request a servlet request
   * @param response a servlet response
   * @param session the user's session
   * @param out a MadnessWriter for output
   */
  protected void doBoth(HttpServletRequest request, HttpServletResponse response,
        HttpSession session, MadnessWriter out) throws ServletException, IOException
    {
    StringBuffer url = new StringBuffer();
    url.append(request.getContextPath());
    url.append(URL_HOME);
    if ("true".equals(request.getParameter(P_LOGOUT)))
      session.invalidate();
    else
      {
      String error = login(request, session);
      if (error != null)
        {
        url.append("?" + P_LOGIN_ERROR + "=");
        url.append(out.HTMLEscape(error));///////// This should be a URL encoding.
        }
      }
    response.sendRedirect(url.toString());
    }


  /**
   * Perform the actual login operation, using whatever credentials are
   * offered in the 'request', and initializing 'session' accordingly.
   *
   * @param request a servlet request
   * @param session the user session
   * @return a user error message, in case of a failed operation, or null
   */
  private String login(HttpServletRequest request, HttpSession session)
    {
    String username = request.getParameter(P_USERNAME);
    String password = request.getParameter(P_PASSWORD);
    String error = null;
    if ((username == null) || (password == null) || (username.length() == 0))
      error = "Please enter a valid username and password.";
    else
      {
      PlayerManager playerMan = PlayerManager.GetInstance();
      Player player = playerMan.selectByUsername(username);
      if (player == null)
        error = "There is no player in the system by that username.  It is case-sensitive.";
      else if (!player.getActive())
        error = "This account has been deactivated.";
      else if (!password.equals(player.getPassword()))
        error = "Your login credentials are invalid.  Please try again.";
      else
        session.setAttribute(P_PLAYER_ID, new Integer(player.getID()));
      }
    return error;
    }

  }
