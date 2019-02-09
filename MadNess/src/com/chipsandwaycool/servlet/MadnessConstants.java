package com.chipsandwaycool.servlet;

import com.elseforif.servlet.utility.HTMLConstants;

/**
 * This interface defines various constant values needed throughout this
 * servlet UI.  I like to access such constants by "implementing" the
 * interface, which obviates having class-name references everywhere.
 * Much cleaner!
 */
public interface MadnessConstants extends HTMLConstants
  {

  public static final String[] REGION_NAMES = { "Midwest", "West", "East", "South" };
  public static final String[] ROUND_NAMES = { "Round 1", "Round 2", "Sweet 16", "Elite 8", "Final 4", "Champ." };

  public static final int SECURITY_PUBLIC = 1;
  public static final int SECURITY_USER = 2;
  public static final int SECURITY_ADMIN = 4;
  public static final int SECURITY_ANYBODY = SECURITY_PUBLIC | SECURITY_USER | SECURITY_ADMIN;
  public static final int SECURITY_NO_PRECUTOFF = 8;
  public static final int SECURITY_NO_POSTCUTOFF = 16;

  public static final String P_ACTIVE = "active";
  public static final String P_ADMIN = "admin";
  public static final String P_EMAIL = "email";
  public static final String P_ERRORS = "errors";
  public static final String P_FIRST_NAME = "firstName";
  public static final String P_GAME = "game";
  public static final String P_HOME = "home";
  public static final String P_LAST_NAME = "lastName";
  public static final String P_LOGIN_ERROR = "loginError";
  public static final String P_LOGOUT = "logout";
  public static final String P_NAME = "name";
  public static final String P_NICKNAME = "nickname";
  public static final String P_PASSWORD = "password";
  public static final String P_PICK = "pick";
  public static final String P_PLAYER_ID = "playerID";
  public static final String P_RANK = "rank";
  public static final String P_SAVE = "save";
  public static final String P_SKIN_NAME = "skinName";
  public static final String P_SUCCESS = "success";
  public static final String P_TOGGLE_SKIN = "toggleSkin";
  public static final String P_USERNAME = "username";

  public static final String URL_BRACKET_IMAGE = "http://assets.espn.go.com/i/ncaa/07men_bracket.gif";
  public static final String URL_EDIT_PLAYER = "/edit-player";
  public static final String URL_EDIT_PLAYER_ACTION = "/edit-player-action";
  public static final String URL_EDIT_TEAMS = "/edit-teams";
  public static final String URL_EDIT_TEAMS_ACTION = "/edit-teams-action";
  public static final String URL_EDIT_WINNERS = "/edit-winners";
  public static final String URL_EDIT_WINNERS_ACTION = "/edit-winners-action";
  public static final String URL_ELSEFORIF = "http://www.elseforif.com/";
  public static final String URL_EMAIL_PICKS = "/email-picks";
  public static final String URL_EMAIL_SCORES = "/email-scores";
  public static final String URL_HOME = "/home";
  public static final String URL_INSTRUCTIONS = "/instructions";
  public static final String URL_LOGIN = "/login";
  public static final String URL_PICKS = "/picks";
  public static final String URL_PICKS_ACTION = "/picks-action";
  public static final String URL_PICKS_CSV = "/picks.csv";
  public static final String URL_SCOREBOARD = "/scoreboard";

  }
