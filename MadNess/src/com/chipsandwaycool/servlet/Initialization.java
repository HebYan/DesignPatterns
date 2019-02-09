package com.chipsandwaycool.servlet;

import com.chipsandwaycool.utility.Emailer;
import com.chipsandwaycool.entity.MadnessDatabase;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.text.ParseException;
import java.io.File;

/**
 * This is the initializer servlet for the March Madness site.  It is not
 * invoked by the client, but its 'init()' method is configured to execute
 * whenever the webapp starts up, so one-time setup can take place.
 */
public class Initialization extends MadnessServlet
  {

  /**
   * Initialize this March Madness site.  This expects a few init-params
   * to be defined for this servlet class in the "web.xml" file.  If they
   * not there or invalid, an exception is thrown.
   *
   * @param servletConfig an object containing details about this servlet's environment and configuration
   */
  public void init(ServletConfig servletConfig) throws ServletException
    {
    System.out.println("Initializing chipsandwaycool.com.");
    super.init(servletConfig);
    String cutoffDate = servletConfig.getInitParameter("cutoff-date");
    if (cutoffDate == null)
      throw new NullPointerException("A configuration parameter is required to specify the cutoff date for choosing teams.");
    try
      {
      MadnessServlet.SetCutoffDate(cutoffDate);
      }
    catch (ParseException exception)
      {
      throw new ServletException("It appears the cutoff date parameter was not formatted correctly.", exception);
      }
    String smtpHost = servletConfig.getInitParameter("smtp-host");
    String senderEmail = servletConfig.getInitParameter("sender-email");
    if ((smtpHost == null) || (senderEmail == null))
      throw new NullPointerException("An SMTP host name and a sender email address must be specified in \"web.xml\".");
    Emailer.SetSMTPHost(smtpHost);
    Emailer.SetDefaultSender(senderEmail);
    String dataFiles = servletConfig.getInitParameter("data-files-dir");
    if (dataFiles == null)
      throw new NullPointerException("You must specify the directory where Derby will store the data files for this system.");
    File dataFilesDir = new File(dataFiles);
    if (!dataFilesDir.isDirectory())
      throw new ServletException("The directory specified for the database files, \"" + dataFiles + "\", was not found (to be a directory).");
    String databaseURL = servletConfig.getInitParameter("database-url");
    if (databaseURL == null)
      throw new NullPointerException("A database URL is required.");
    boolean rebuildDatabase = "true".equals(servletConfig.getInitParameter("rebuild-database"));
    try
      {
      MadnessDatabase.Initialize(dataFiles, databaseURL, rebuildDatabase);
      }
    catch (Exception exception)
      {
      exception.printStackTrace();
      throw new ServletException("There was an SQL exception while initializing the data layer.", exception);
      }
    System.out.println("chipsandwaycool.com initialized.");
    }


  /**
   * Perform any cleanup needed for shutting down this site.
   */
  public void destroy()
    {
    super.destroy();
    try { MadnessDatabase.GetInstance().shutDown(); }
    catch (Exception ignored) { }
    }

  }
