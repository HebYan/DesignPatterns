package com.chipsandwaycool.servlet;

import com.chipsandwaycool.servlet.utility.MadnessWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This servlet outputs a page of instructions detailing how this site works.
 * The main content of the page is included from a static HTML file.
 */
public class Instructions extends MadnessServlet
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
    out.printPreContent();
    if (session.getAttribute(P_PLAYER_ID) != null)
      out.printMenu(URL_INSTRUCTIONS);
    out.printBeginBox();
    out.printDIV("heading", "Instructions");
    getServletContext().getRequestDispatcher("/Instructions.html").include(request, response);
    out.printEndBox();
    out.printPostContent();
    }

  }
