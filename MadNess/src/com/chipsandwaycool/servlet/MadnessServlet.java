package com.chipsandwaycool.servlet;

import com.chipsandwaycool.servlet.utility.MadnessWriter;
import com.chipsandwaycool.servlet.utility.MadnessWriterSkin1;
import com.chipsandwaycool.entity.player.PlayerManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * This is the base class for servlets belonging to the March Madness Web site.
 * Its purpose is to handle any common servlet logic, such as error handling,
 * so that specific subclasses can focus on their own, specific output.
 * <br/><br/>
 * It is <i>not</i> the responsibility of this servlet class to form any actual
 * HTML output, since that is handled by the MadnessWriter object.  However,
 * a basic bitfield-based security mechanism is built into this class, just to
 * keep things self-contained.
 */
public abstract class MadnessServlet extends HttpServlet implements MadnessConstants
  {

  private static DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
  protected static long CutoffDate = -1;

  protected int     m_securityBits = 0;



  /**
   * This method should be called during initialization to specify the cutoff
   * date, after which players can no longer modify their picks, and certain
   * data becomes available to them, according to the business rules.  This
   * date thus dynamically affects security within this site.
   *
   * @param cutoffDateString a critical cutoff date governing functionality
   */
  public static void SetCutoffDate(String cutoffDateString) throws ParseException
    {
    CutoffDate = DateFormat.parse(cutoffDateString).getTime();
    }


  /**
   * Return whether the global cutoff date has been passed in time.  (See
   * 'SetCutoffDate()'.)  This method is used to dynamically control access
   * to certain features, according to the business requirements.
   *
   * @return whether the global cutoff date has been passed
   */
  public static boolean GetCutoffDateIsPassed()
    {
    return System.currentTimeMillis() >= CutoffDate;
    }


  /**
   * Service a "GET" request to this servlet.  This calls a handler common
   * to both GET and POST requests, since I seldom find it important know
   * which is being used.
   *
   * @param request a servlet request
   * @param response a servlet response
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    doBoth(request, response);
    }


  /**
   * Service a "POST" request to this servlet.  This calls a handler common
   * to both GET and POST requests, since I seldom find it important know
   * which is being used.
   *
   * @param request a servlet request
   * @param response a servlet response
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    doBoth(request, response);
    }


  /**
   * Service a request to this servlet, handling any common headers and
   * dealing with errors which may arise.  Perform a basic security check
   * for simple bit-based, servlet-level security.  Create an HTML output
   * object and pass it to a protected method to handle the page output for
   * this servlet (subclass).
   * <br/><br/>
   * This central control method is also where we do some security checks,
   * based on the bits each servlet sets on the 'm_securityBits' member.  Most
   * security violations are taken as system errors, but if a non-public page
   * is accessed by a user who isn't logged in, we assume the session has
   * timed out (or a bookmark was used), and we forward to the home page.
   *
   * @param request a servlet request
   * @param response a servlet response
   */
  public void doBoth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    MadnessWriter writer = null;
    try
      {
      HttpSession session = request.getSession(true);
      writer = getHTMLWriter(request, response, session);
      response.setContentType("text/html");
      response.setHeader("Pragma", "no-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setDateHeader("Expires", 0);
      if ((m_securityBits & SECURITY_ANYBODY) == 0)
        throw new IllegalStateException("The servlet you requested should never be invoked, by anyone, ever!  (It probably hasn't defined enough security flags.)");
      Integer playerID = (Integer)(session.getAttribute(P_PLAYER_ID));
      boolean playerIsAdmin = false;
      if (playerID == null)
        {
        if ((m_securityBits & SECURITY_PUBLIC) == 0)
          {
          getServletContext().getRequestDispatcher(URL_HOME).forward(request, response);
          return;
          }
        }
      else
        {
        PlayerManager playerMan = PlayerManager.GetInstance();
        playerIsAdmin = playerMan.select(playerID, false).getAdmin();
        if (!playerIsAdmin)
          {
          if ((m_securityBits & SECURITY_USER) == 0)
            throw new ServletException("Normal users are not allowed to view this page.");
          }
        else if ((m_securityBits & SECURITY_ADMIN) == 0)
          throw new ServletException("Administrators are not allowed to view this page.  Weird, huh?");
        }
      if (((m_securityBits & SECURITY_NO_PRECUTOFF) != 0)
            && !GetCutoffDateIsPassed() && !playerIsAdmin)
        throw new ServletException("You may not access this servlet prior to the cutoff date:  " + CutoffDate + ".");
      if (((m_securityBits & SECURITY_NO_POSTCUTOFF) != 0)
            && GetCutoffDateIsPassed() && !playerIsAdmin)
        throw new ServletException("You may not access this servlet after the cutoff date:  " + CutoffDate + ".");
      doBoth(request, response, session, writer);
      }
    catch (Throwable error)
      {
      if (error instanceof OutOfMemoryError)
        throw (OutOfMemoryError)error;
      try
        {
        writer.printErrorPage(error);
        }
      finally
        {
        while (error != null)
          {
          error.printStackTrace();
          error = error.getCause();
          }
        }
      }
    }


  /**
   * Construct and return a MadnessWriter (or subclass) for the given request
   * object.  This implements the "skin" feature, but it may also be useful
   * if one or more servlets wanted to use a more specialized HTML writer.
   *
   * @param request a servlet request for whose output a writer is needed
   * @param response the servlet response
   * @return a MadnessWriter for HTML output
   */
  protected MadnessWriter getHTMLWriter(HttpServletRequest request,
        HttpServletResponse response, HttpSession session) throws IOException
    {
    String skinName = getSkinName(session, false);
    if ("skin1".equals(skinName))
      return new MadnessWriterSkin1(response.getWriter(), request.getContextPath());
    else
      return new MadnessWriter(response.getWriter(), request.getContextPath());
    }


  /**
   * Return the name of the user's current "skin".  Iff 'toggle', change it
   * first to the one not currently selected.
   *
   * @param session the user session
   * @param toggle whether to toggle the skin
   * @return the user's current skin name
   */
  protected String getSkinName(HttpSession session, boolean toggle)
    {
    String skinName = (String)(session.getAttribute(P_SKIN_NAME));
    if (skinName == null)
      skinName = getServletContext().getInitParameter("skinName");
    if (toggle)
      {
      skinName = ((skinName == null) || "default".equals(skinName)) ? "skin1" : "default";
      session.setAttribute(P_SKIN_NAME, skinName);
      }
    return skinName;
    }


  /**
   * Service a request to this servlet using a MadnessWriter for output.
   * This is the method which should normally be overridden in subclasses.
   *
   * @param request a servlet request
   * @param response a servlet response
   * @param session the user's session
   * @param out a MadnessWriter for output
   */
  protected void doBoth(HttpServletRequest request, HttpServletResponse response,
        HttpSession session, MadnessWriter out) throws ServletException, IOException
    {
    }


  /**
   * Trim 'value', if it's not null, and then set it to null if the length
   * turns out to be zero.  Return the result.  This is used to scrub form
   * input values, so that empty strings become nulls.
   */
  protected String scrubInput(String value)
    {
    if (value != null)
      {
      value = value.trim();
      if (value.length() == 0)
        value = null;
      }
    return value;
    }

  }
