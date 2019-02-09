package com.chipsandwaycool.entity;

import org.apache.derby.drda.NetworkServerControl;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.net.InetAddress;

/**
 * This is an object for initializing the data store (Derby) and doling out
 * connections to it.  It needs to be initialized before the singleton
 * 'GetInstance()' method can be called.
 */
public class MadnessDatabase extends Object
  {

  private static MadnessDatabase Instance = null;

  private NetworkServerControl  m_derbyServer = null;
  private String                m_databaseURL = null;



  /**
   * Prevent client code from trying to instantiate this singleton directly.
   *
   * @param databaseURL the database JDBC URL
   */
  private MadnessDatabase(String databaseURL) throws Exception
    {
    m_databaseURL = databaseURL;
    if (m_databaseURL.indexOf("//localhost:1527/") == -1)
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    else
      {
      m_derbyServer = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
      m_derbyServer.start(null);
      Class.forName("org.apache.derby.jdbc.ClientDriver");
      }
    }


  /**
   * Initialize this database singleton for use.  Configure the given root
   * database directory (for Derby), and use the specified database name.
   * The 'getConnection()' method will create the database as needed.  Iff
   * 'rebuildDatabase', then drop and recreate all the tables, with a default
   * set of data.  This should normally be needed once only.
   *
   * @param dataDirectory a file-system directory for the database to use
   * @param databaseURL the database URL for connections
   * @param rebuildDatabase whether to rebuild all the tables and base data
   */
  public static void Initialize(String dataDirectory, String databaseURL,
        boolean rebuildDatabase) throws SQLException
    {
    if (dataDirectory == null)
      throw new NullPointerException("It's just not a good idea to use Derby without telling it where to create database files.  Pass in a directory name for that.");
    if (databaseURL == null)
      throw new NullPointerException("I need a database URL.");
    System.setProperty("derby.system.home", dataDirectory);
    try
      {
      Instance = new MadnessDatabase(databaseURL);
      }
    catch (Exception exception)
      {
      throw new RuntimeException("Error in class MadnessDatabase:  an exception occurred while initializing the DataSource.", exception);
      }
    if (rebuildDatabase)
      {
      BaseDataSet baseData = new BaseDataSet();
      baseData.createBaseData();
      }
    }


  /**
   * Return the singleton instance of this database manager class.  Remember
   * to call 'Initialize()' before calling this, else a NullPointerException.
   *
   * @return the single instance of this class
   */
  public static MadnessDatabase GetInstance()
    {
    if (Instance != null)
      return Instance;
    else
      throw new NullPointerException("The MadnessDatabase class has not been successfully inititialized.");
    }


  /**
   * Return a database connection, or null if none can be produced.  Report any errors
   * to my ExceptionLogger, if I have one.
   *
   * @return a connection to the database
   */
  public Connection getConnection() throws SQLException
    {
    if (m_databaseURL != null)
      {
      String connectionString = m_databaseURL + ";create=true";
      return DriverManager.getConnection(connectionString);
      }
    else
      throw new NullPointerException("This Database is not initialized.  No connection for you!");
    }


  /**
   * Shut down my database.
   */
  public void shutDown() throws Exception
    {
    if (m_databaseURL != null)
      if (m_databaseURL.indexOf("//localhost:1527/") == -1)
        DriverManager.getConnection(m_databaseURL + ";shutdown=true");
      else if (m_derbyServer != null)
        m_derbyServer.shutdown();
    }

  }
