package com.chipsandwaycool.servlet.player;

import com.chipsandwaycool.servlet.MadnessServlet;
import com.chipsandwaycool.entity.player.PlayerManager;
import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.entity.player.Player;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;

/**
 * This servlet allows for editing of new and existing players (that is, users
 * of this Web site).
 * <br/><br/>
 * Note:  because of how I'm doing this, if a user makes a goof and this page
 * returns to display an error message without saving the changes, the form
 * fields revert to previous values.  Normally, I'd use the form-handling
 * objects in the elseforif-servlet library, which can retain values even
 * when they're not being saved in the data store.
 */
public class EditPlayer extends MadnessServlet
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
    out.printPreContent();
    out.printMenu(null);
    out.printBeginBox();
    if (request.getAttribute(P_ERRORS) != null)
      out.printDIV("formErrors", (String)(request.getAttribute(P_ERRORS)));
    out.printBeginFORM(URL_EDIT_PLAYER_ACTION);
    if (player.getID() != -1)
      out.printHIDDENInput(P_PLAYER_ID, Integer.toString(player.getID()));
    out.printDIV("formHeading", "Edit a User");
    out.printBeginTABLE(0, 0, 0, null, "class=\"form\"");
    out.print(TR + TD_TOP_RIGHT + "First name:" + END_TD + TD);
    out.printTextINPUT(P_FIRST_NAME, 12, 20, player.getFirstName());
    out.print(END_TD + END_TR + TR + TD_TOP_RIGHT + "Last name:" + END_TD + TD);
    out.printTextINPUT(P_LAST_NAME, 15, 30, player.getLastName());
    out.print(END_TD + END_TR + TR + TD_TOP_RIGHT + "Nickname:" + END_TD + TD);
    out.printTextINPUT(P_NICKNAME, 15, 30, player.getNickname());
    out.print(END_TD + END_TR + TR + TD_TOP_RIGHT + "Email:" + END_TD + TD);
    out.printTextINPUT(P_EMAIL, 15, 100, player.getEmail());
    out.print(END_TD + END_TR + TR + TD_TOP_RIGHT + "Login name:" + END_TD + TD);
    out.printTextINPUT(P_USERNAME, 10, 30, player.getUsername());
    out.print(END_TD + END_TR + TR + TD_TOP_RIGHT + "Password:" + END_TD + TD);
    out.printTextINPUT(P_PASSWORD, 10, 30, player.getPassword());
    out.print(END_TD + END_TR + TR + TD_TOP_RIGHT + "Activate?:" + END_TD + TD);
    out.printRadioINPUT(P_ACTIVE, "true", player.getActive());
    out.print(" yes " + NBSP + NBSP);
    out.printRadioINPUT(P_ACTIVE, "false", !player.getActive());
    out.print(" no");
    out.print(END_TD + END_TR + TR + TD_TOP_RIGHT + "Administrator?:" + END_TD + TD);
    out.printRadioINPUT(P_ADMIN, "true", player.getAdmin());
    out.print(" yes " + NBSP + NBSP);
    out.printRadioINPUT(P_ADMIN, "false", !player.getAdmin());
    out.print(" no" + END_TD + END_TR + TR);
    out.printBeginTD(2, 1, "align=\"center\"");
    out.printSUBMIT(P_SAVE, "Save");
    out.print(END_TD + END_TR + END_TABLE + END_FORM);
    out.printEndBox();
    out.printPostContent();
    }

  }
