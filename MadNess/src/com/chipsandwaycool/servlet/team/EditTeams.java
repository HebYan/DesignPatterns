package com.chipsandwaycool.servlet.team;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.team.Team;
import com.chipsandwaycool.entity.team.TeamManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;

/**
 * This page is used to modify the set of sixty-four teams in the tournament.
 * They are displayed in order of rank and grouped by region, and the user
 * can change them all at once.
 */
public class EditTeams extends MadnessServlet
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
    out.printPreContent();
    out.printMenu(null);
    out.printBeginBox();
    if (request.getAttribute(P_ERRORS) != null)
      out.printDIV("formErrors", (String) (request.getAttribute(P_ERRORS)));
    out.printDIV("heading", "Edit Teams");
    out.print(NL);
    out.printBeginFORM(URL_EDIT_TEAMS_ACTION);
    out.printBeginTABLE(0, 0, 10, null, "class=\"form\"");
    for (int i = 0; i < teams.length; i++)
      {
      int id = teams[i].getID();
      if ((i & 15) == 0)
        {
        out.print(NL + TR);
        out.printBeginTD(4, 1);
        if (i != 0)
          out.print(BR);
        out.printDIV("formHeading", REGION_NAMES[i >> 4] + ":");
        out.print(END_TD + END_TR);
        }
      out.print(TR + TD_NOWRAP + "Rank ");
      out.print((i & 15) + 1);
      out.print("..." + END_TD + TD_NOWRAP + "University: ");
      out.printTextINPUT(P_HOME + id, 20, 20, teams[i].getHome());
      out.print(END_TD + TD_NOWRAP + "Team name: ");
      out.printTextINPUT(P_NAME + id, 20, 30, teams[i].getName());
      out.printHIDDENInput(P_RANK + id, Integer.toString(i & 15));
      out.print(END_TD + END_TR);
      }
    out.print(TR);
    out.printBeginTD(4, 1, "align=\"center\"");
    out.print(BR);
    out.printSUBMIT(P_SAVE, "Save");
    out.print(END_TD + END_TR + END_TABLE + END_FORM);
    out.printEndBox();
    out.printPostContent();
    }

  }
