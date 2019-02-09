package com.chipsandwaycool.entity.team;

import com.chipsandwaycool.entity.MadnessDatabase;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Map;
import java.util.HashMap;

/**
 * This object manages CRUD operations for Team entities.
 */
public class TeamManager extends Object
  {

  static TeamManager Instance = null;



  /**
   * Prevent client code from instantiating this singleton class directly.
   */
  protected TeamManager()
    {
    }


  /**
   * Return the sole TeamManager instance, creating it on first call.
   *
   * @return the TeamManager singleton
   */
  static public TeamManager GetInstance()
    {
    if (Instance == null)
      Instance = new TeamManager();
    return Instance;
    }


  /**
   * Create a new Team entity in the data store.
   *
   * @param team the new entity object
   */
  public void insert(Team team)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      PreparedStatement statement = connection.prepareStatement(
            "insert into Team (rank, home, name) values (?, ?, ?)");
      statement.setInt(1, team.getRank());
      statement.setString(2, team.getHome());
      statement.setString(3, team.getName());
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
   * Retrieve a Team entity from the store.
   *
   * @param teamID the ID of the team requested
   * @return the referenced Team entity
   */
  public Team select(Object teamID)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery("select * from Team where id = " + teamID);
      if (result.next())
        return load(result);
      else
        throw new SQLException("No Team record was found for ID " + teamID + ".");
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
   * Return the list of all Team entities, in order of "rank" if
   * 'sortByRankNotName', or by University name if not.
   *
   * @return all the 64 teams
   */
  public Team[] selectAll()
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      Statement statement = connection.createStatement();
      ResultSet results = statement.executeQuery("select * from Team order by rank");
      Team[] teams = new Team[64];
      for (int i = 0; i < teams.length; i++)
        {
        results.next();
        teams[i] = load(results);
        }
      return teams;
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
   * Return all 64 teams as a map, whose keys are Integers representing
   * the IDs, and whose values are the Team objects.
   *
   * @return all the teams, mapped to their IDs
   */
  public Map selectAllAsMap()
    {
    Team[] teams = selectAll();
    Map teamMap = new HashMap(teams.length);
    for (int i = 0; i < teams.length; i++)
      teamMap.put(new Integer(teams[i].getID()), teams[i]);
    return teamMap;
    }


  /**
   * Update the attributes for an existing Team entity.
   *
   * @param team a team to be updated in the store
   */
  public void update(Team team)
    {
    Connection connection = null;
    try
      {
      connection = MadnessDatabase.GetInstance().getConnection();
      PreparedStatement statement = connection.prepareStatement(
            "update Team set rank = ?, home = ?, name = ? where id = ?");
      statement.setInt(1, team.getRank());
      statement.setString(2, team.getHome());
      statement.setString(3, team.getName());
      statement.setInt(4, team.getID());
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
   * Load a Team object based on the current record in the given result set.
   *
   * @param result a result set pointed to a Team record
   * @return the Team object, loaded from the result set
   */
  private Team load(ResultSet result) throws SQLException
    {
    Team team = new Team();
    team.setID(result.getInt("id"));
    team.setRank(result.getInt("rank"));
    team.setHome(result.getString("home"));
    team.setName(result.getString("name"));
    return team;
    }

  }
