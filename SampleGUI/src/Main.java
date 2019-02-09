import Code.Searches;
import Code.StudentHashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class Main. This class is our test class. We used this class to do some
 * basic testing before we implemented in the GUI
 *
 * @author JigarDP&AbdullahA
 * @version 1.0
 */
public class Main {

	/**
	 * The main method used for testing certain implementation like the
	 * StudentHashMap ans Searches class before using it in our GUI
	 *
	 * @param args
	 *            the arguments
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		StudentHashMap studentHashMap = new StudentHashMap();

		StudentHashMap.addStudent("Abdullah", "Almusallam", "CS", "Sophmore", 3.8);
		StudentHashMap.addStudent("Jigar", "Prajapati", "CS", "Junior", 3.8);
		StudentHashMap.addStudent("Ryan", "Couch", "CS", "Senior", 3.8);
		StudentHashMap.addStudent("Nate", "Irwin", "CS", "Senior", 3.8);
		StudentHashMap.addStudent("Bat", "Man", "Math", "Senior", 1.0);
		StudentHashMap.addStudent("Super", "Man", "Biology", "Senior", 0.5);

		System.out.println("");
		System.out.println("Updated students Records (test) are: ");

		// studentHashMap.removeStudent(****);
		StudentHashMap.getStudentRecords(); // displays all the students

		Searches search = new Searches(); // Testing the search class

		System.out.println("SearchBinaryTest: ");

		Searches.binarySearch(studentHashMap, 5002, 4000, 6000);

		System.out.println("SearchLinearTest: ");

		Searches.linearSearch(studentHashMap, 5002);

	}

}
