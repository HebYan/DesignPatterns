package com.elseforif.log;

/**
 * A MetaLog is a central object which can perform basic logging to the
 * standard output.  This can be preferable to the more direct
 * 'System.out.println()' calls, since such calls cannot conveniently
 * be disabled globally or filtered.
 * <br><br>
 * This class also maintains a global debug flag, which can be used freely.
 * <br><br>
 * I also use this as a base class for a central definitive object for
 * each Web site I create.  This object facilitates the sharing of constant
 * values between servlets and applets while providing a lightweight logging
 * interface for the applets.
 */
public abstract class MetaLog extends Object
  {

  public static boolean DebugMode = false;



  /**
   * Log a string message to the console.
   *
   * @param message a string to be logged
   */
  public static void Log(String message)
    {
    if (message != null)
      System.out.println(message);
    }


  /**
   * Log a message an exception stack track to the console.  The message is 'message',
   * if that is supplied, or 'exception.getMessage()' otherwise.
   *
   * @param message an optional message describing the exception
   * @param exception the exception to be logged
   */
  public static void Log(String message, Exception exception)
    {
    if (message == null)
      message = exception.getMessage();
    if (message != null)
      System.out.println(message);
    exception.printStackTrace();
    }


  /**
   * Log a "debug" message, iff the debug mode is set to true.
   *
   * @param message a message to be logged if debug logging is enabled
   */
  public static void Debug(String message)
    {
    if (DebugMode)
      System.out.println(message);
    }


  /**
   * Return whether the global debug mode is enabled
   *
   * @return whether the debug mode is enabled
   */
  public static boolean GetDebugMode()
    {
    return DebugMode;
    }


  /**
   * Set the global debug mode to 'debugMode'.  Messages logged using 'Debug()'
   * will only be outputted if this is true.
   *
   * @param debugMode whether to enable the global debug mode
   */
  public static void SetDebugMode(boolean debugMode)
    {
    DebugMode = debugMode;
    }

  }
