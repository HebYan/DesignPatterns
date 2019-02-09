package com.chipsandwaycool.entity.player;

import com.chipsandwaycool.entity.MadnessDatabase;
import com.chipsandwaycool.entity.game.GameManager;
import com.chipsandwaycool.entity.game.Game;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

/**
 * This object manages CRUD operations for Player entities.
 */
public class PlayerManager extends Object
  {

  static PlayerManager Instance = null;



  /**
   * Prevent client code from instantiating this singleton class directly.
   */
  private PlayerManager()
    {
    }


  /**
   * Return the sole PlayerManager instance, creating it on first call.
   *
   * @return the PlayerManager singleton
   */
  static public PlayerManager GetInstance()
    {
    if (Instance == null)
      Instance = new PlayerManager();
    return Instance;
    }


  /**
   * Create a new Player entity in the data store.
   *
   * @param player the new entity object
   */
  public void insert(Player player)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      PreparedStatement statement = connection.prepareStatement(
            "insert into Player (active, username, password, firstName, lastName, nickname, email, admin) values (?, ?, ?, ?, ?, ?, ?, ?)");
      statement.setBoolean(1, player.getActive());
      statement.setString(2, player.getUsername());
      statement.setString(3, player.getPassword());
      statement.setString(4, player.getFirstName());
      statement.setString(5, player.getLastName());
      statement.setString(6, player.getNickname());
      statement.setString(7, player.getEmail());
      statement.setBoolean(8, player.getAdmin());
      statement.executeUpdate();
      }
    catch (SQLException exception)
      {
      throw new RuntimeException(exception);
      }
    finally
      {
      if (connection != null)
        try { connection.close(); }
        catch (SQLException ignored) { }
      }
    }


  /**
   * Update a Player entity's attributes.
   *
   * @param player the player entity to be updated
   */
  public void update(Player player)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      PreparedStatement statement = connection.prepareStatement(
            "update Player set active = ?, username = ?, password = ?, firstName = ?, lastName = ?, nickname = ?, email = ?, admin = ? where id = ?");
      statement.setBoolean(1, player.getActive());
      statement.setString(2, player.getUsername());
      statement.setString(3, player.getPassword());
      statement.setString(4, player.getFirstName());
      statement.setString(5, player.getLastName());
      statement.setString(6, player.getNickname());
      statement.setString(7, player.getEmail());
      statement.setBoolean(8, player.getAdmin());
      statement.setInt(9, player.getID());
      statement.executeUpdate();
      }
    catch (SQLException exception)
      {
      throw new RuntimeException(exception);
      }
    finally
      {
      if (connection != null)
        try { connection.close(); }
        catch (SQLException ignored) { }
      }
    }


  /**
   * Retrieve a Player entity from the store.  Attach the player's picks
   * if specified.
   *
   * @param playerID the ID of the player requested
   * @param addPicks whether to add the player's team picks
   * @return the referenced Player entity
   */
  public Player select(Object playerID, boolean addPicks)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery("select * from Player where id = " + playerID);
      if (result.next())
        {
        Player player = load(result, null);
        if (addPicks)
          player.setPicks(getPlayerPicks(connection, playerID));
        return player;
        }
      else
        throw new SQLException("No Player record was found for ID " + playerID + ".");
      }
    catch (SQLException exception)
      {
      throw new RuntimeException(exception);
      }
    finally
      {
      if (connection != null)
        try { connection.close(); }
        catch (SQLException ignored) { }
      }
    }


  /**
   * Retrieve the Player entity having the given username, if any, null if not.
   *
   * @param username the username of the requested player
   * @return the referenced Player entity
   */
  public Player selectByUsername(Object username)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery("select * from Player where username = '" + username + "'");
      if (result.next())
        return load(result, null);
      else
        return null;
      }
    catch (SQLException exception)
      {
      throw new RuntimeException(exception);
      }
    finally
      {
      if (connection != null)
        try { connection.close(); }
        catch (SQLException ignored) { }
      }
    }


  /**
   * Return whether the given username belongs to any existing users.
   *
   * @param username the username in question
   * @return whether the username is in use
   */
  public boolean getUsernameInUse(String username)
    {
    Player player = selectByUsername(username);
    return player != null;
    }


  /**
   * Return a list of Players on this site.  Attach their picks as specified.
   *
   * @param addPicks whether to add the players' team picks
   * @return the referenced Player entity
   */
  public List selectAll(boolean addPicks)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery("select * from Player");
      List players = new ArrayList(30);
      while (result.next())
        {
        Player player = load(result, null);
        if (addPicks)
          player.setPicks(getPlayerPicks(connection, new Integer(player.getID())));
        players.add(player);
        }
      return players;
      }
    catch (SQLException exception)
      {
      throw new RuntimeException(exception);
      }
    finally
      {
      if (connection != null)
        try { connection.close(); }
        catch (SQLException ignored) { }
      }
    }


  /**
   * Set the indicated player's team picks to 'picks', which should be an
   * array of twenty integers representing team IDs, the first one worth
   * twenty points, the second worth nineteen, etcetera.  The value -1 is
   * allowed, where the player hasn't chosen a team for any given position.
   *
   * @param playerID the player's own ID
   * @param picks an array of the player's chosen team IDs
   */
  public void setPlayerPicks(Object playerID, int[] picks)
    {
    if (playerID == null)
      throw new NullPointerException("Method 'setPlayerPicks()' was invoked with a null player ID.");
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      connection.setAutoCommit(false);
      Statement statement = connection.createStatement();
      statement.executeUpdate("delete from Pick where playerID = " + playerID);
      PreparedStatement insert = connection.prepareStatement(
            "insert into Pick (playerID, points, teamID) values (?, ?, ?)");
      insert.setInt(1, ((Integer)playerID).intValue());
      for (int i = 0; i < picks.length; i++)
        if (picks[i] >= 0)
          {
          insert.setInt(2, 20 - i);
          insert.setInt(3, picks[i]);
          insert.executeUpdate();
          }
      connection.commit();
      }
    catch (SQLException exception)
      {
      throw new RuntimeException(exception);
      }
    finally
      {
      if (connection != null)
        try
          {
          connection.rollback();
          connection.setAutoCommit(true);
          connection.close();
          }
        catch (SQLException ignored) { }
      }
    }


  /**
   * Return a list of PlayerWithScores, calculating the scores for each
   * player according to the current state of the tournament.
   *
   * @return a list of players and their current scores
   */
  public List getPlayersWithScores()
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery("select * from Player");
      List players = new ArrayList();
      PlayerWithScores player = null;
      while (result.next())
        {
        player = new PlayerWithScores();
        players.add(load(result, player));
        player.setPicks(getPlayerPicks(connection, new Integer(player.getID())));
        }
      calculateScores(players);
      Collections.sort(players);
      return players;
      }
    catch (SQLException exception)
      {
      throw new RuntimeException(exception);
      }
    finally
      {
      if (connection != null)
        try { connection.close(); }
        catch (SQLException ignored) { }
      }
    }


  /**
   * Return the indicated player's team picks, in the form of an array of
   * twenty integers representing team IDs, the first one worth
   * twenty points, the second worth nineteen, etcetera.  The value -1 will
   * appear where the player hasn't chosen a team for a given position.
   *
   * @param connection a Connection object to use
   * @param playerID the player's own ID
   * @return an array of the player's chosen team IDs
   */
  private int[] getPlayerPicks(Connection connection, Object playerID) throws SQLException
    {
    Statement statement = connection.createStatement();
    ResultSet results = statement.executeQuery(
          "select * from Pick where playerID = " + playerID);
    int[] picks = new int[20];
    while (results.next())
      picks[results.getInt("points") - 1] = results.getInt("teamID");
    results.close();
    statement.close(); // do this everywhere
    return picks;
    }


  /**
   * Populate the given list of PlayerWithScores objects by caculating
   * each one's scores for each round, the total and potential scores,
   * based on the current state of the tournament.
   *
   * @param playersWithScores the list of PlayerWithScores objects
   */
  private void calculateScores(List playersWithScores)
    {
    GameManager gameMan = GameManager.GetInstance();
    Game[] games = gameMan.getGamesInBracketOutputOrder();
    for (Iterator players = playersWithScores.iterator(); players.hasNext(); )
      {
      PlayerWithScores player = (PlayerWithScores)(players.next());
      int[] potentials = new int[games.length];
      for (int i = 0; i < games.length; i++)
        {
        if (games[i].getWinningTeamID() >= 0)
          {
          potentials[i] = player.getPointsForTeam(games[i].getWinningTeamID());
          player.addWin(i, potentials[i]);
          player.addPotential(potentials[i]);
          }
        else
          {
          if (games[i].getTeam1ID() >= 0)
            potentials[i] = player.getPointsForTeam(games[i].getTeam1ID());
          else if (i > 31)
            potentials[i] = potentials[(i & 31) << 1];
          if (games[i].getTeam2ID() >= 0)
            potentials[i] = Math.max(player.getPointsForTeam(games[i].getTeam2ID()), potentials[i]);
          else if (i > 31)
            potentials[i] = Math.max(potentials[((i & 31) << 1) + 1], potentials[i]);
          player.addPotential(potentials[i]);
          }
        }
      }
    }


  /**
   * Load a Player object based on the current record in the given result set.
   *
   * @param result a result set pointed to a Player record
   * @return the Player object, loaded from the result set
   */
  private Player load(ResultSet result, Player player) throws SQLException
    {
    if (player == null)
      player = new Player();
    player.setID(result.getInt("id"));
    player.setActive(result.getBoolean("active"));
    player.setUsername(result.getString("username"));
    player.setPassword(result.getString("password"));
    player.setFirstName(result.getString("firstName"));
    player.setLastName(result.getString("lastName"));
    player.setNickname(result.getString("nickname"));
    player.setEmail(result.getString("email"));
    player.setAdmin(result.getBoolean("admin"));
    return player;
    }

  }
