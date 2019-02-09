package com.elseforif.entity.net;

/**
 * This is a simple encapsulation of a path to an image and its width and height.
 */
public class WebImage extends Object
  {

  String        m_path = null;
  int           m_width = 0;
  int           m_height = 0;



  /**
   * Create an unititialized Image object.
   */
  public WebImage()
    {
    }


  /**
   * Construct a copy of 'image'.
   *
   * @param image a WebImage to be copied
   */
  public WebImage(WebImage image)
    {
    m_path = image.m_path;
    m_width = image.m_width;
    m_height = image.m_height;
    }


  /**
   * Create a new Image object and initialize it according to the parameters.
   *
   * @param path the relative path of the image
   * @param width of the image
   * @param height of the image
   */
  public WebImage(String path, int width, int height)
    {
    m_path = path;
    m_width = width;
    m_height = height;
    }


  /**
   * Return the relative path of the image
   *
   * @return the relative path of the image
   */
  public String getPath()
    {
    return m_path;
    }


  /**
   * Set the path of the image
   *
   * @param path the relative path of the image
   */
  public void setPath(String path)
    {
    m_path = path;
    }


  /**
   * Return the width of the image
   *
   * @return the width of the image
   */
  public int getWidth()
    {
    return m_width;
    }


  /**
   * Return the width of the image as a String
   *
   * @return the width of the image
   */
  public String getWidthString()
    {
    return Integer.toString(m_width);
    }


  /**
   * Sets the width of the image.
   *
   * @param width of the image
   */
  public void setWidth(int width)
    {
    m_width = width;
    }


  /**
   * Return the height of the image
   *
   * @return the height of the image
   */
  public int getHeight()
    {
    return m_height;
    }


  /**
   * Return the height of the image in String
   *
   * @return the height of the image
   */
  public String getHeightString()
    {
    return Integer.toString(m_height);
    }


  /**
   * Sets the height of the image.
   *
   * @param height of the image
   */
  public void setHeight(int height)
    {
    m_height = height;
    }


  /**
   * Sets the width and height of the image.
   *
   * @param width of the image
   * @param height of the image
   */
  public void setDimensions(int width, int height)
    {
    m_width = width;
    m_height = height;
    }


  /**
   * Return myself as a formatted string.
   *
   * @return a string representation of myself
   */
  public String toString()
    {
    return super.toString()
          + "\n  path:  " + getPath()
          + "\n  width:  " + getWidth()
          + "\n  height:  " + getHeight();
    }

  }

