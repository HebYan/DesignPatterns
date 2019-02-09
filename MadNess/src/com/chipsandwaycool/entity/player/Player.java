package com.chipsandwaycool.entity.player;

/**
 * This represents a user of this site, a player in the March Madness pool
 * (not a player in the actual NCAA tournament).  It encapsulates the user's
 * login information as well as some personal information, and whether the
 * user has administrative priveleges.
 */
public class Player extends Object
  {

  private int       m_id = -1;
  private boolean   m_active = true;
  private String    m_username = null;
  private String    m_password = null;
  private String    m_firstName = null;
  private String    m_lastName = null;
  private String    m_nickname = null;
  private String    m_email = null;
  private boolean   m_admin = false;
  private int[]     m_picks = null;



  /**
   * Instantiate a new Player without initializing it to meaningful values.
   */
  public Player()
    {
    }


  /**
   * Create a new Player with the given attributes.
   *
   * @param active whether the player is active or enabled
   * @param username this player's login name
   * @param password my password
   * @param firstName my first name
   * @param lastName my last name
   * @param nickname a nickname for this player
   * @param email the player's email address
   * @param admin whether this player acts as an admin on the site
   */
  public Player(String username, boolean active, String password, String firstName,
        String lastName, String nickname, String email, boolean admin)
    {
    m_active = active;
    m_username = username;
    m_password = password;
    m_firstName = firstName;
    m_lastName = lastName;
    m_nickname = nickname;
    m_email = email;
    m_admin = admin;
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
   * Set whether this player is active or enabled.
   *
   * @param active this player's active status
   */
  public void setActive(boolean active)
    {
    m_active = active;
    }


  /**
   * Return whether this player is active or enabled.
   *
   * @return this player's active status
   */
  public boolean getActive()
    {
    return m_active;
    }


  /**
   * Set my username.
   *
   * @param username this player's login name
   */
  public void setUsername(String username)
    {
    m_username = username;
    }


  /**
   * Return my username.
   *
   * @return this player's login name
   */
  public String getUsername()
    {
    return m_username;
    }


  /**
   * Set my password.
   *
   * @param password my password
   */
  public void setPassword(String password)
    {
    m_password = password;
    }


  /**
   * Return my password.
   *
   * @return my password
   */
  public String getPassword()
    {
    return m_password;
    }


  /**
   * Set this player's first name.
   *
   * @param firstName my first name
   */
  public void setFirstName(String firstName)
    {
    m_firstName = firstName;
    }


  /**
   * Return my first name.
   *
   * @return my first name
   */
  public String getFirstName()
    {
    return m_firstName;
    }


  /**
   * Set my last name.
   *
   * @param lastName my last name
   */
  public void setLastName(String lastName)
    {
    m_lastName = lastName;
    }


  /**
   * Return my last name.
   *
   * @return my last name
   */
  public String getLastName()
    {
    return m_lastName;
    }


  /**
   * Set a nickname for this player.
   *
   * @param nickname a nickname for this player
   */
  public void setNickname(String nickname)
    {
    m_nickname = nickname;
    }


  /**
   * Return this player's nickname.
   *
   * @return a nickname for this player
   */
  public String getNickname()
    {
    return m_nickname;
    }


  /**
   * Return my nickname if I have one, else return my first name.
   *
   * @return my nickname or first name
   */
  public String getNicknameOrFirstName()
    {
    return (m_nickname != null) ? m_nickname : m_firstName;
    }


  /**
   * Set the email address for this player.
   *
   * @param email this player's email
   */
  public void setEmail(String email)
    {
    m_email = email;
    }


  /**
   * Return this player's email address.
   *
   * @return an email address for this player
   */
  public String getEmail()
    {
    return m_email;
    }


  /**
   * Return a full name for this player, including both first and last name
   * and the nickname, if one is defined.
   *
   * @return a complete name for this player
   */
  public String getCompleteName()
    {
    if (m_nickname != null)
      return m_firstName + " \"" + m_nickname + "\" " + m_lastName;
    else
      return m_firstName + " " + m_lastName;
    }


  /**
   * Return a partial name for this player, including the nickname and last
   * name, or the first and the nickname, if no nickname is defined.
   *
   * @return a short name for this player
   */
  public String getShortName()
    {
    if (m_nickname != null)
      return "\"" + m_nickname + "\" " + m_lastName;
    else
      return m_firstName + " " + m_lastName;
    }


  /**
   * Set my administrator flag.
   *
   * @param admin whether this player acts as an admin on the site
   */
  public void setAdmin(boolean admin)
    {
    m_admin = admin;
    }


  /**
   * Return my administrator flag.
   *
   * @return whether this player acts as an admin on the site
   */
  public boolean getAdmin()
    {
    return m_admin;
    }


  /**
   * Set the list of team picks for this player, in order of points.
   *
   * @param picks this player's team picks
   */
  public void setPicks(int[] picks)
    {
    m_picks = picks;
    }


  /**
   * Return the team ID for the zero-based pick index (where index 0 is worth
   * 1 point and index 19 is worth 20).  If no team is picked yet for the
   * given index, return -1.
   *
   * @param index the zero-based index of the desired team pick
   * @return the team ID for the given pick index
   */
  public int getPick(int index)
    {
    return m_picks[index];
    }


  /**
   * Return the points value this player has associated with the specified
   * team ID, or return 0 if that team is not among my picks.
   *
   * @param teamID the ID of the team in question
   * @return the point value associated, or zero
   */
  public int getPointsForTeam(int teamID)
    {
    for (int i = 0; i < m_picks.length; i++)
      if (m_picks[i] == teamID)
        return i + 1;
    return 0;
    }


  /**
   * Return whether the specified team is among my picks.
   *
   * @param teamID the ID of the team in question
   * @return whether I have picked that team to win
   */
  public boolean getHasPickedTeam(int teamID)
    {
    return getPointsForTeam(teamID) > 0;
    }

  }
