package com.elseforif.utility;

import com.elseforif.log.MetaLog;
import java.util.List;
import java.util.ArrayList;

/**
 * A MultiMap is sort of a two-dimensional vector.  It's like a Map, except
 * that every element is a key, and there can be any number of them per index.
 * In tabular terms, a MultiMap is constructed with a fixed number of columns,
 * called "positions" here; and then rows can be added at liberty and referenced
 * by an "index".  The reason I use the terms "position" and "index" instead of
 * "row" and "column" is that this collection is implemented as an array of
 * lists:  so finding objects within a given position is easy, yet finding them
 * within an index, or "row", requires iteration and may not be directly
 * supported.  Thus, I don't expect that more than a few positions should
 * be used, normally.
 * <br><br>
 * Note that the number of elements in each position is kept the same, true to the tabular paradigm.
 */
public class MultiMap extends Object
  {

  static final int DEFAULT_LIST_CAPACITY = 10;

  protected List[] m_listArray = null;
  protected int m_listInitialCapacity = 0;
  protected int m_length = 0;



  /**
   * Construct a MultiMap with 'positions' number of positions (or columns, if you prefer).
   *
   * @param positions the number of positions (keys per index) I shall have
   */
  public MultiMap(int positions)
    {
    this(positions, DEFAULT_LIST_CAPACITY);
    }


  /**
   * Construct a MultiMap with 'positions' number of positions (or columns, if you prefer)
   * and an initial capacity of 'initialCapacity' indexes (or rows) per position.  (See class comment.)
   *
   * @param positions the number of positions (keys per index) I shall have
   * @param initialCapacity an initial capacity for elements within each position
   */
  public MultiMap(int positions, int initialCapacity)
    {
    m_listArray = new List[positions];
    m_listInitialCapacity = initialCapacity;
    for (int i = 0; i < positions; i++)
      m_listArray[i] = new ArrayList(m_listInitialCapacity);
    }


  /**
   * Return the length of this MultiMap, that is, the number of objects in each position.
   * (Or the number of rows, if you prefer the tabular paradigm.)
   *
   * @return my length in terms of elements per position
   */
  public int getLength()
    {
    return m_length;
    }


  /**
   * Add an element at the first position, incrementing my entire length by one.  (Null elements
   * are added to all remaining positions to keep each on the same length.)  The two-argument 'set()'
   * can be called hereafter to fill out the remaining positions, as needed.  Return my new length.
   *
   * @param position0Value an Object to be placed in position 0, at a new index
   * @return my new length
   */
  public int add(Object position0Value)
    {
    m_listArray[0].add(position0Value);
    for (int i = 1; i < m_listArray.length; i++)
      m_listArray[i].add(null);
    return ++m_length;
    }


  /**
   * Add a new index, placing the given Objects in the first two positions.  This is a convenience
   * method.  The proper way is to start with the one-argument form and then call the two-argument 'set()'
   * to fill out the remaining positions.  Return my new length.
   *
   * @param position0Value a new element for position 0
   * @param position1Value a new element for position 1
   * @return my new length
   */
  public int add(Object position0Value, Object position1Value)
    {
    add(position0Value);
    set(1, position1Value);
    return m_length;
    }


  /**
   * Add a new index, placing the given Objects in the first three positions.  This is a convenience
   * method.  The proper way is to start with the one-argument form and then call the two-argument 'set()'
   * to fill out the remaining positions.  Return my new length.
   *
   * @param position0Value a new element for position 0
   * @param position1Value a new element for position 1
   * @param position2Value a new element for position 2
   * @return my new length
   */
  public int add(Object position0Value, Object position1Value, Object position2Value)
    {
    add(position0Value);
    set(1, position1Value);
    set(2, position2Value);
    return m_length;
    }


  /**
   * Set the element at the last existing index, at position 'position', to 'value'.  Because it assumes
   * the last added index, it can be used after 'add(Object)' to fill out the new index at subsequent
   * positions.
   *
   * @param position the position at which to assign the element
   * @param value the element Object to be assigned
   */
  public void set(int position, Object value)
    {
    m_listArray[position].set(m_length - 1, value);
    }


  /**
   * Set the element at the given position and index (or column and row, respectively), to 'value'.
   * This cannot add a new index value.  Use 'add()' for that.
   *
   * @param position the position at which to assign the element
   * @param index the index at which the element should be assigned
   * @param value the element Object to be assigned
   */
  public void set(int position, int index, Object value)
    {
    m_listArray[position].set(index, value);
    }


  /**
   * Return the element at the given position and index (or column and row, respectively).
   *
   * @param position the position of the element
   * @param index the element's index at the given position
   * @return the object at the given position and index
   */
  public Object get(int position, int index)
    {
    return m_listArray[position].get(index);
    }


  /**
   * Return the index at which 'value' lives within the given position, or -1 if it is not found.
   * (As noted in the class comment, this is a fast lookup, because each position is implemented
   * as a List.  Searching by index requires iteration and should therefore be avoided.)
   *
   * @param position the position to be searched for 'value'
   * @param value the element sought (by equality, not identity)
   */
  public int indexOf(int position, Object value)
    {
    return m_listArray[position].indexOf(value);
    }


  /**
   * Spew some diagnostic information about this MultiMap to the console.
   */
  void spew()
    {
    for (int i = 0; i < m_listArray.length; i++)
      MetaLog.Log("List at position " + i + " has " + m_listArray[i].size() + " elements.");
    }

  }
