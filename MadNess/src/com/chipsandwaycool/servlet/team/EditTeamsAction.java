package com.chipsandwaycool.servlet.team;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.team.TeamManager;
import com.chipsandwaycool.entity.team.Team;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;

/**
 * This "action" servlet responds to the Edit Teams page, updating the names
 * of all sixty-four teams when a site administrator has changed them.
 */
public class EditTeamsAction extends MadnessServlet
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
    TeamManager teamMan = TeamManager.GetInstance();
    Team[] teams = teamMan.selectAll();
    boolean allHomesValid = true;
    boolean allNamesValid = true;
    for (int i = 0; i < teams.length; i++)
      {
      String id = Integer.toString(teams[i].getID());
      teams[i].setHome(scrubInput(request.getParameter(P_HOME + id)));
      if (teams[i].getHome() == null)
        allHomesValid = false;
      teams[i].setName(scrubInput(request.getParameter(P_NAME + id)));
      if (teams[i].getName() == null)
        allNamesValid = false;
      }
    if (!allHomesValid || !allNamesValid)
      {
      StringBuffer errors = new StringBuffer();
      if (!allHomesValid)
        errors.append("One or more \"home\" values is missing." + BR);
      if (!allNamesValid)
        errors.append("One or more team names is missing." + BR);
      request.setAttribute(P_ERRORS, errors.toString());
      getServletContext().getRequestDispatcher(URL_EDIT_TEAMS).forward(request, response);
      }
    else
      {
      for (int i = 0; i < teams.length; i++)
        teamMan.update(teams[i]);
      getServletContext().getRequestDispatcher(URL_HOME).forward(request, response);
      }
    }

  }
