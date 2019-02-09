package Code;

/**
 * The Class Searches. This is a test method for searches for basic testing. A
 * working search method is implemented in the loadViewStudent class.
 *
 * @author JigarDP&AbdullahA
 * @version 1.0
 */
public class Searches {

	/**
	 * Instantiates a new searches.
	 */
	public Searches() {

	}

	/**
	 * Binary search.(Note: This is just a test method. A working copy of this
	 * method has been re-used in the loadViewStudent class)
	 *
	 * @param studentHashMap
	 *            the student hash map
	 * @param keyId
	 *            the key id
	 * @param keyStart
	 *            the key start
	 * @param high
	 *            the high
	 * 
	 */
	public static void binarySearch(StudentHashMap studentHashMap, int keyId, int keyStart, int high) {

		long timeStart = System.nanoTime();
		int start = keyStart;
		int end = high;
		int mid = (start + end) / 2;

		if (studentHashMap.containsKey(keyId)) {
			String fname = StudentHashMap.getFirstName(keyId);
			String lname = StudentHashMap.getLastName(keyId);
			String major = StudentHashMap.getMajor(keyId);
			String year = StudentHashMap.getYear(keyId);
			String GPA = StudentHashMap.getGPA(keyId);

			System.out.println(keyId + " " + fname + " " + lname + " " + major + " " + year + " " + GPA);

		} else if (keyId < mid) {
			binarySearch(studentHashMap, keyId, start, (mid - 1));
		} else if (keyId > mid) {
			binarySearch(studentHashMap, keyId, (mid + 1), end);
		}

		long timestop = System.nanoTime();

		System.out.println(timestop - timeStart + " ns");
	}

	/**
	 * Linear search. (Note: This is just a test method. A working copy of this
	 * method has been re-used in the loadViewStudent class)
	 *
	 * @param studentHashMap
	 *            the student hash map
	 * @param keyId
	 *            the key id
	 */
	public static void linearSearch(StudentHashMap studentHashMap, int keyId) {

		long timeStart = System.nanoTime();

		if (studentHashMap.containsKey(keyId)) {
			String fname = StudentHashMap.getFirstName(keyId);
			String lname = StudentHashMap.getLastName(keyId);
			String major = StudentHashMap.getMajor(keyId);
			String year = StudentHashMap.getYear(keyId);
			String GPA = StudentHashMap.getGPA(keyId);

			System.out.println(keyId + " " + fname + " " + lname + " " + major + " " + year + " " + GPA);
		}

		long timestop = System.nanoTime();

		System.out.println(timestop - timeStart + " ns");
	}
}