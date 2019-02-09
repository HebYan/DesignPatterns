package com.chipsandwaycool.entity.team;

/**
 * This object represents one of the sixty-four teams in the NCAA tournament.
 * Each one is given a rank within its region, which is encapsulated here
 * along with the team's name.
 */
public class Team extends Object implements Comparable
  {

  private int       m_id = -1;
  private int       m_rank = -1;
  private String    m_home = null;
  private String    m_name = null;



  /**
   * Instantiate a new Team without initializing it to meaningful values.
   */
  public Team()
    {
    }


  /**
   * Create a new team with the given attributes.
   *
   * @param rank this teams rank within a region (zero-based)
   * @param home the name of the city or school of the team
   * @param name the team's own simple name, like "Ducks"
   */
  public Team(int rank, String home, String name)
    {
    m_rank = rank;
    m_home = home;
    m_name = name;
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
   * Set my regional rank (zero-based).
   *
   * @param rank this teams rank within a region (zero-based)
   */
  public void setRank(int rank)
    {
    m_rank = rank;
    }


  /**
   * Return my regional rank (zero-based).
   *
   * @return this teams rank within a region (zero-based)
   */
  public int getRank()
    {
    return m_rank;
    }


  /**
   * Set my home city or school name.
   *
   * @param home the name of the city or school of the team
   */
  public void setHome(String home)
    {
    m_home = home;
    }


  /**
   * Return my city or school name.
   *
   * @return the name of the city or school of the team
   */
  public String getHome()
    {
    return m_home;
    }


  /**
   * Set my team name.
   *
   * @param name the team's own simple name, like "Ducks"
   */
  public void setName(String name)
    {
    m_name = name;
    }


  /**
   * Return my team name.
   *
   * @return the team's own simple name, like "Ducks"
   */
  public String getName()
    {
    return m_name;
    }


  /**
   * Return my full name, including my home city or school.
   *
   * @return my full name
   */
  public String getFullName()
    {
    return m_home + ' ' + m_name;
    }


  /**
   * Compare this Team with another, to sort by University name.
   *
   * @param other another Team object
   * @return an integer comparison value for sorting
   */
  public int compareTo(Object other)
    {
    return m_home.compareTo(((Team)other).m_home);
    }

  }
