package com.chipsandwaycool.entity.player;

/**
 * This object is only used on the Scoreboard page, to contain scores, along
 * with the inherited Player attributes, which are calculated on-the-fly when
 * that page is invoked.  Once the scores--including the greatest potential
 * score--are tallied, the servlet can easily display them.
 */
public class PlayerWithScores extends Player implements Comparable
  {

  private int[]   m_roundScores = new int[6];
  private int     m_potentialScore = 0;



  /**
   * Add a given number of points to my scores.  Calculate the appropriate
   * round according to 'gameIndex', in order to add the points to that round.
   *
   * @param gameIndex the bracket-order index of the game
   * @param points the number of points I get for the win
   */
  public void addWin(int gameIndex, int points)
    {
    if (gameIndex < 32)
      m_roundScores[0] += points;
    else if (gameIndex < 48)
      m_roundScores[1] += points;
    else if (gameIndex < 56)
      m_roundScores[2] += points;
    else if (gameIndex < 60)
      m_roundScores[3] += points;
    else if (gameIndex < 62)
      m_roundScores[4] += points;
    else
      m_roundScores[5] += points;
    }


  /**
   * Return my score, thus far, for the specified round.
   *
   * @param round the round for which a score is wanted
   * @return my actual score for that round
   */
  public int getScore(int round)
    {
    return m_roundScores[round];
    }


  /**
   * Return my total score, thus far, adding up all five rounds.
   *
   * @return my total score
   */
  public int getTotalScore()
    {
    return m_roundScores[0] + m_roundScores[1] + m_roundScores[2]
          + m_roundScores[3] + m_roundScores[4] + m_roundScores[5];
    }


  /**
   * Add 'points' to my current potential score.
   *
   * @param points a points value to be added to my potential
   */
  public void addPotential(int points)
    {
    m_potentialScore += points;
    }


  /**
   * Return my maximum potential points, based on the available game scores.
   *
   * @return the best potential score for this player
   */
  public int getPotentialScore()
    {
    return m_potentialScore;
    }


  /**
   * Compare this PlayerWithScores to another, for sorting by total score.
   *
   * @param otherPlayer a player to compare to this one
   * @return a comparison value between this player and 'otherPlayer'
   */
  public int compareTo(Object otherPlayer)
    {
    int difference = ((PlayerWithScores)otherPlayer).getTotalScore() - getTotalScore();
    if (difference == 0)
      difference = ((PlayerWithScores)otherPlayer).getPotentialScore() - getPotentialScore();
    return difference;
    }

  }
