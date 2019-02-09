package com.chipsandwaycool.entity.game;

/**
 * This object represents one of the games in the NCAA playoff bracket.
 * It defines a round and a position within that round.  The numbers are
 * nicely binary:  there are sixteen games in the first round, four for each
 * region.  The games are single-elimination, so round two will have eight
 * games, and so on to round five, which is the final game.  Thus, there are
 * thirty-one games total.  Note that rounds and positions are both zero-based
 * within this object, and the output code must add one as needed.
 * <br/><br/>
 * As games are played, the winning teams become known and can be identified
 * in corresponding games within subsequent rounds.  So for any one game,
 * the teams playing, and the winning team of those two, may not be known,
 * in which case -1 is used as a null value.
 */
public class Game extends Object
  {

  private int       m_id = -1;
  private int       m_round = -1;
  private int       m_position = -1;
  private int       m_team1ID = -1;
  private int       m_team2ID = -1;
  private int       m_winningTeamID = -1;


  /**
   * Instantiate a new Game without initializing it to meaningful values.
   */
  public Game()
    {
    }


  /**
   * Create a new Game with the given attributes.
   *
   * @param round this game's round
   * @param position the position of the game within the round
   * @param team1ID the ID of team 1 for this game, or -1
   * @param team2ID the ID of team 2 for this game
   * @param winningTeamID the ID of the winning team, if known
   */
  public Game(int round, int position, int team1ID, int team2ID, int winningTeamID)
    {
    m_round = round;
    m_position = position;
    m_team1ID = team1ID;
    m_team2ID = team2ID;
    m_winningTeamID = winningTeamID;
    }


  /**
   * Set my unique ID.
   *
   * @param id my ID
   */
  public void setID(int id)
    {
    m_id = id;
    }


  /**
   * Return my unique ID.
   *
   * @return my ID
   */
  public int getID()
    {
    return m_id;
    }


  /**
   * Set my zero-based round.
   *
   * @param round this game's round
   */
  public void setRound(int round)
    {
    m_round = round;
    }


  /**
   * Return my zero-based round.
   *
   * @return this game's round
   */
  public int getRound()
    {
    return m_round;
    }


  /**
   * Set my position with my round.
   *
   * @param position the position of the game within the round
   */
  public void setPosition(int position)
    {
    m_position = position;
    }


  /**
   * Return my position with my round.
   *
   * @return the position of the game within the round
   */
  public int getPosition()
    {
    return m_position;
    }


  /**
   * Set my first team's ID.
   *
   * @param team1ID the ID of team 1 for this game, or -1
   */
  public void setTeam1ID(int team1ID)
    {
    m_team1ID = team1ID;
    }


  /**
   * Return my first team's ID.
   *
   * @return the ID of team 1 for this game, or -1
   */
  public int getTeam1ID()
    {
    return m_team1ID;
    }


  /**
   * Set my second team's ID.
   *
   * @param team2ID the ID of team 2 for this game
   */
  public void setTeam2ID(int team2ID)
    {
    m_team2ID = team2ID;
    }


  /**
   * Return my second team's ID.
   *
   * @return the ID of team 2 for this game
   */
  public int getTeam2ID()
    {
    return m_team2ID;
    }


  /**
   * Set the ID of the team who won this game.
   *
   * @param winningTeamID the ID of the winning team, if known
   */
  public void setWinningTeamID(int winningTeamID)
    {
    m_winningTeamID = winningTeamID;
    }


  /**
   * Return the ID of the team who won this game.
   *
   * @return the ID of the winning team, if known
   */
  public int getWinningTeamID()
    {
    return m_winningTeamID;
    }


  /**
   * Return a simple string representation of this object.
   *
   * @return myself as a string
   */
  public String toString()
    {
    return "round " + m_round + ", position " + m_position;
    }

  }
