package com.chipsandwaycool.entity.game;

import com.chipsandwaycool.entity.MadnessDatabase;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * This object manages CRUD operations for Game entities.
 */
public class GameManager extends Object
  {

  static GameManager Instance = null;



  /**
   * Prevent client code from instantiating this singleton class directly.
   */
  private GameManager()
    {
    }


  /**
   * Return the sole GameManager instance, creating it on first call.
   *
   * @return the GameManager singleton
   */
  static public GameManager GetInstance()
    {
    if (Instance == null)
      Instance = new GameManager();
    return Instance;
    }


  /**
   * Create a new Game entity in the data store.
   *
   * @param game the new entity object
   */
  public void insert(Game game)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      PreparedStatement statement = connection.prepareStatement(
            "insert into Game (round, position, team1ID, team2ID, winningTeamID) values (?, ?, ?, ?, ?)");
      statement.setInt(1, game.getRound());
      statement.setInt(2, game.getPosition());
      statement.setInt(3, game.getTeam1ID());
      statement.setInt(4, game.getTeam2ID());
      statement.setInt(5, game.getWinningTeamID());
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
   * Retrieve a Game entity from the store.
   *
   * @param gameID the ID of the game requested
   * @return the referenced Game entity
   */
  public Game select(Object gameID)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery("select * from Game where id = " + gameID);
      if (result.next())
        return load(result);
      else
        throw new SQLException("No Game record was found for ID " + gameID + ".");
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
   * Load and return all Game entities, ordered to facilitate the bracket view.
   *
   * @return an array of all Game entities
   */
  public Game[] getGamesInBracketOutputOrder()
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery("select * from Game order by round, position");
      Game[] games = new Game[63];
      int index = 0;
      while (results.next())
        games[index++] = load(results);
      return games;
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
   * Iterator through all the games in "bracket order", checking whether
   * each one's two precedent games have been decided, and setting its
   * state accordingly.  Then update the team IDs for all the games in the
   * data store.
   *
   * @param gamesInBracketOrder the whole list of games, in order
   */
  public void reconcileAndSaveGames(Game[] gamesInBracketOrder)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      connection.setAutoCommit(false);
      PreparedStatement update = connection.prepareStatement(
            "update Game set team1ID = ?, team2ID = ?, winningTeamID = ? where id = ?");
      for (int i = 0; i < gamesInBracketOrder.length; i++)
        {
        Game game = gamesInBracketOrder[i];
        if (i >= 32)
          {
          game.setTeam1ID(gamesInBracketOrder[(i & 31) << 1].getWinningTeamID());
          game.setTeam2ID(gamesInBracketOrder[((i & 31) << 1) + 1].getWinningTeamID());
          if ((game.getTeam1ID() == -1) || (game.getTeam2ID() == -1)
                || ((game.getWinningTeamID() != game.getTeam1ID())
                && (game.getWinningTeamID() != game.getTeam2ID())))
            game.setWinningTeamID(-1);
          }
        update.setInt(1, game.getTeam1ID());
        update.setInt(2, game.getTeam2ID());
        update.setInt(3, game.getWinningTeamID());
        update.setInt(4, game.getID());
        update.executeUpdate();
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
   * Load a Game object based on the current record in the given result set.
   *
   * @param result a result set pointed to a Game record
   * @return the Game object, loaded from the result set
   */
  private Game load(ResultSet result) throws SQLException
    {
    Game game = new Game();
    game.setID(result.getInt("id"));
    game.setRound(result.getInt("round"));
    game.setPosition(result.getInt("position"));
    game.setTeam1ID(result.getInt("team1ID"));
    game.setTeam2ID(result.getInt("team2ID"));
    game.setWinningTeamID(result.getInt("winningTeamID"));
    return game;
    }

  }
