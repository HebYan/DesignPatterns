package com.elseforif.utility;

import java.io.StringWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/**
 * This object reads files from a file system and caches them in some form.
 * 'reparseFile()' returns an object for the file's contents, and each time
 * it is invoked, the timestamp of the file on disk is compared with that
 * of the cached version here, and iff they differ the file is reread.
 * <br><br>
 * This base implementation assumes all files are ASCII and reads them into
 * one string each.  Thus, the return from 'reparseFile()' can be cast to
 * a String.  Subclasses, however, may override 'readFile()' to return some
 * other object type.
 * <br><br>
 * Uh, why the heck are these methods returning Objects?  Maybe so that subclasses
 * can provide a smart interface, returning Strings for text files and DOMs for
 * XML documents.
 *
 * @author Jason Van Cleve
 */
public class FileCache extends Object
  {

  protected static final int PATH = 0;
  protected static final int DATE = 1;
  protected static final int OBJECT = 2;

  protected MultiMap    m_files = new MultiMap(3, 12);



  /**
   * Return whether the specified XML file is newer on disk than my cached copy
   * or I have not cached the file yet.
   *
   * @param filePath the file path of the XML document
   * @return whether the file has been changed since 'date'
   */
  public boolean getIsNew(String filePath) throws IOException
    {
    File file = new File(filePath);
    if (!file.exists())
      throw new IOException("The file '" + filePath + "' was not found, Bozo.");
    int index = m_files.indexOf(PATH, filePath);
    if (index == -1)
      return true;
    Long date = (Long)(m_files.get(DATE, index));
    return ((date == null) || (date.longValue() != file.lastModified()));
    }


  /**
   * Return whether the specified XML file is newer on disk than
   * the given timestamp.
   *
   * @param filePath the file path of the XML document
   * @param date the timestamp against which the file is to be compared
   * @return whether the file has been changed since 'date'
   */
  public boolean getIsNew(String filePath, Long date) throws Exception
    {
    reparseFile(filePath);
    int index = m_files.indexOf(PATH, filePath);
//    Long lastDate = (Long)(m_files.get(index, DATE));  // dont know how this
    Long lastDate = (Long)(m_files.get(DATE, index));         // got like that
    if ((lastDate != null)
          && ((date == null) || (lastDate.longValue() > date.longValue())))
      return true;
    else
      return false;
    }


  /**
   * Return the last-modified timestamp for the specified XML file.
   *
   * @param filePath the path to the XML file
   * @return the file's date of last modification
   */
  public Long getFileDate(String filePath) throws Exception
    {
    reparseFile(filePath);
    int index = m_files.indexOf(PATH, filePath);
    return (Long)(m_files.get(index, DATE));
    }


  /**
   * Check the specified file against my cache.  If it has been changed
   * since I last parsed it, reparse it.
   *
   * @param filePath the file path to the file
   */
  public Object reparseFile(String filePath) throws Exception
    {
    File file = new File(filePath);
    if (!file.exists())
      throw new IOException("The file '" + filePath + "' was not found, Bozo.");
    int index = m_files.indexOf(PATH, filePath);
    if (index != -1)
      {
      Long date = (Long)(m_files.get(DATE, index));
      if ((date == null) || (date.longValue() != file.lastModified()))
        m_files.set(DATE, index, new Long(file.lastModified()));
      else
        return m_files.get(OBJECT, index);
      }
    else
      {
      index = m_files.add(filePath) - 1;
      m_files.set(DATE, new Long(file.lastModified()));
      }
    Object object = readFile(file);
    m_files.set(OBJECT, index, object);
    return object;
    }


  /**
   * Read the given file from disk, and return an object representing it.
   * For this implementation, return a String containing the file's ASCII
   * contents.  (An XML-file implementation might return a DOM object
   * instead.)
   *
   * @param file a File to be read
   * @return an object representing the file's contents
   */
  protected Object readFile(File file) throws Exception
    {
    FileReader reader = new FileReader(file);
    StringWriter writer = new StringWriter(2024);
    int character = -1;
    while ((character = reader.read()) != -1)
      writer.write(character);
    return writer.toString();
    }

  }
