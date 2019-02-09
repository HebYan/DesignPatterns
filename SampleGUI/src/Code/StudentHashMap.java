package Code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * The Class StudentHashMap defines the Data Structure of our project that is
 * the HashMap.
 *
 * @author JigarDP&AbdullahA
 * @version 1.0
 */
public class StudentHashMap {

	/** The student hash map. */
	@SuppressWarnings("rawtypes")
	private static HashMap studentHashMap;

	/** The key id. */
	private static int keyId = 5011;

	/** The Student ID. */
	private static int StudentID = 0;

	/**
	 * Instantiates a new student hash map.
	 */
	public StudentHashMap() {
		studentHashMap = new HashMap<Integer, List<String>>();

	}

	/**
	 * Key generator.
	 *
	 * @return the keyID (which is the random StudentID)
	 */
	public static int keyGenerator() {
		int min = 5300;
		int max = 5600;

		Random rn = new Random();
		int n = max - min + 1;
		int i = rn.nextInt() % n;
		keyId = min + i;

		return keyId;
	}

	/**
	 * Adds the student.
	 *
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param major
	 *            the major
	 * @param year
	 *            the year
	 * @param GPA
	 *            the gpa
	 */
	@SuppressWarnings("unchecked")
	public static void addStudent(String firstName, String lastName, String major, String year, double GPA) {

		StudentID = keyId;

		String GPA2 = "" + GPA;
		List<String> information = new ArrayList<String>();
		information.add(firstName);
		information.add(lastName);
		information.add(major);
		information.add(year);
		information.add(GPA2);
		studentHashMap.put(keyId, information);
		keyGenerator();
	}

	/**
	 * Gets the first name.
	 *
	 * @param key
	 *            the key
	 * @return the first name
	 */
	public static String getFirstName(int key) {
		@SuppressWarnings("unchecked")
		ArrayList<String> information = (ArrayList<String>) studentHashMap.get(key);
		String nameFirst = information.get(0);
		return nameFirst;
	}

	/**
	 * Gets the last name.
	 *
	 * @param key
	 *            the key
	 * @return the last name
	 */
	public static String getLastName(int key) {
		@SuppressWarnings("unchecked")
		ArrayList<String> information = (ArrayList<String>) studentHashMap.get(key);
		String nameLast = information.get(1);
		return nameLast;
	}

	/**
	 * Gets the major.
	 *
	 * @param key
	 *            the key
	 * @return the major
	 */
	public static String getMajor(int key) {
		@SuppressWarnings("unchecked")
		ArrayList<String> information = (ArrayList<String>) studentHashMap.get(key);
		String major = information.get(2);
		return major;
	}

	/**
	 * Gets the year.
	 *
	 * @param key
	 *            the key
	 * @return the year
	 */
	public static String getYear(int key) {
		@SuppressWarnings("unchecked")
		ArrayList<String> information = (ArrayList<String>) studentHashMap.get(key);
		String year = information.get(3);
		return year;
	}

	/**
	 * Gets the gpa.
	 *
	 * @param key
	 *            the key
	 * @return the gpa
	 */
	public static String getGPA(int key) {
		@SuppressWarnings("unchecked")
		ArrayList<String> information = (ArrayList<String>) studentHashMap.get(key);
		String GPA = information.get(4);
		return GPA;
	}

	/**
	 * Removes the student.
	 *
	 * @param keyId
	 *            the key id
	 */
	public static void removeStudent(int keyId) {
		studentHashMap.remove(keyId);
	}

	/**
	 * Gets the student records.
	 *
	 * @return the student records
	 */
	public static void getStudentRecords() {

		@SuppressWarnings("rawtypes")
		Iterator iterator = studentHashMap.keySet().iterator();

		while (iterator.hasNext()) {
			int key = (int) iterator.next();
			String fname = getFirstName(key);
			String lname = getLastName(key);
			String major = getMajor(key);
			String year = getYear(key);
			String GPA = getGPA(key);

			System.out.println(key + " " + fname + " " + lname + " " + major + " " + year + " " + GPA);
		}

	}

	/**
	 * Gets the key ID.
	 *
	 * @return the key ID
	 */
	public int getKeyID() {
		return StudentID;
	}

	/**
	 * Size.
	 *
	 * @return the size of the studentHashMap
	 */
	public int size() {
		return studentHashMap.size();
	}

	/**
	 * Gets the value of the associated key.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
	public String get(int key) {
		return (String) studentHashMap.get(key);
	}

	/**
	 * Contains key method.
	 *
	 * @param Key
	 *            the key
	 * @return true, if successful
	 */
	public boolean containsKey(int Key) {
		return studentHashMap.containsKey(Key);
	}

	/**
	 * Key set.
	 *
	 * @return the sets the
	 */
	@SuppressWarnings("rawtypes")
	public Set keySet() {
		return studentHashMap.keySet();
	}

}
