package com.chipsandwaycool.servlet.player;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.entity.player.Player;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.team.TeamManager;
import com.chipsandwaycool.entity.team.Team;
import com.chipsandwaycool.entity.player.PlayerManager;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * This is the page where users can choose the twenty teams they think
 * likeliest to win, arranging according to points values from 20 down
 * to one point.  A JavaScript file accompanies this page to try and keep
 * the user from selecting the same team twice, and to display a warning
 * when fewer than twenty teams are picked.
 */
public class Picks extends MadnessServlet
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
    Player player = playerMan.select(session.getAttribute(P_PLAYER_ID), true);
    boolean readOnly = GetCutoffDateIsPassed() && !player.getAdmin();
    String playerID = request.getParameter(P_PLAYER_ID);
    if (playerID != null)
      if (readOnly || player.getAdmin())
        player = playerMan.select(playerID, true);
      else
        throw new ServletException("You may not view other players' picks until the cutoff date has passed:  " + CutoffDate + ".");
    TeamManager teamMan = TeamManager.GetInstance();
    Team[] teams = teamMan.selectAll();
    Map selectTeams = getDropDownMap(teams);
    out.printPreContent(null, out.SCRIPTFile("/js/picks.js"));
    out.printMenu(URL_PICKS);
    out.printBeginBox();
    if ("true".equals(request.getAttribute(P_SUCCESS)))
      out.printDIV("smallHeading", "Team picks were saved successfully.");
    out.printDIV("reminder", "(Reminder:  \"Pick 20\" represents the team you think likeliest to win.  \"Pick 1\" is the least likely.)");
    out.print(NL);
    out.printBeginTABLE(0, 0, 0, "100%");
    out.print(TR + NL + TD_TOP_CENTER);
    out.printBeginFORM("picksForm", true, URL_PICKS_ACTION);
    if (playerID != null)
      out.printHIDDENInput(P_PLAYER_ID, playerID);
    out.printBeginTABLE(0, 0, 0, null, "class=\"pickList\"");
    for (int i = 20; i > 0; i--)
      {
      out.print(NL + TR + TD + "Pick" + NBSP);
      out.print(i);
      out.print(":" + END_TD + TD);
      String teamID = Integer.toString(player.getPick(i - 1));
      if (!readOnly)
        out.printSELECT(P_PICK + i, selectTeams, teamID);
      else
        {
        String teamName = (String)(selectTeams.get(teamID));
        out.print((teamName != null) ? teamName : "(no pick)");
        }
      out.print(END_TD + END_TR);
      }
    out.print(NL + TR + NL);
    out.printBeginTD(2, 1, "class=\"centered\"");
    if (!readOnly)
      out.printSUBMIT(P_SAVE, "Save");
    out.print(END_TABLE_3 + END_FORM + BR + END_TD + TD_TOP_CENTER);
    doRegionList(teams, out);
    out.print(END_TABLE_3);
    out.printEndBox();
    out.printPostContent();
    }


  /**
   * Output the right-hand list of regions and teams within those regions,
   * for the user's reference.
   *
   * @param teams a list of team in the tournament
   * @param out an HTML writer for output
   */
  private void doRegionList(Team[] teams, MadnessWriter out) throws IOException
    {
    out.print(TABLE + TR);
    out.printBeginTD(null, "regionList");
    for (int i = 0; i < teams.length; i++)
      {
      if ((i & 15) == 0)
        {
        if (i == 32)
          {
          out.print(END_TD + NL);
          out.printBeginTD(null, "regionList");
          }
        out.print(NL + DIV);
        out.print(REGION_NAMES[i >> 4]);
        out.print(":" + END_DIV + OL);
        }
      out.print(NL + LI);
      out.printHTMLEscape(teams[i].getFullName());
      out.print(" (");
      out.print((teams[i].getRank() & 15) + 1);
      out.print(")");
      out.print(END_LI);
      if ((i % 16) == 15)
        out.print(END_OL);
      }
    out.print(END_TABLE_3);
    }


  /**
   * Without re-sorting 'teams' itself, create an return a sorted Map to be
   * used for the options in a SELECT element, with the teams re-sorted by
   * University name.  Include a no-selection option.
   *
   * @param teams an array of Teams
   * @return a re-sorted Map of the teams
   */
  private Map getDropDownMap(Team[] teams)
    {
    Team[] alphaTeams = new Team[teams.length];
    for (int i = 0; i < teams.length; i++)
      alphaTeams[i] = teams[i];
    Arrays.sort(alphaTeams);
    Map teamMap = new LinkedHashMap(alphaTeams.length + 1);
    teamMap.put("-1", "(select)");
    for (int i = 0; i < alphaTeams.length; i++)
      teamMap.put(Integer.toString(alphaTeams[i].getID()), alphaTeams[i].getFullName());
    return teamMap;
    }

  }
