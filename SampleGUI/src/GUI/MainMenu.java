package GUI;

import Code.StudentHashMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class MainMenu defines the Main Menu of our program. It also has the
 * buttons that load either the Add, Remove or View Students menu.
 *
 * @author JigarDP&Abdullah
 * @version 1.0
 */
public class MainMenu {

	/** The frame. **/
	private JFrame frame;

	/** Our own implemented Student HashMap which will be used by our GUI. */
	public static StudentHashMap studentHashMap = new StudentHashMap();

	/**
	 * Launch the application.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		StudentHashMap.addStudent("Abdullah", "Almusallam", "CS", "Sophmore", 3.8);
		StudentHashMap.addStudent("Jigar", "Prajapati", "CS", "Junior", 3.6);
		StudentHashMap.addStudent("Ryan", "Couch", "CS", "Senior", 3.0);
		StudentHashMap.addStudent("Nate", "Irwin", "CS", "Senior", 0.2);
		StudentHashMap.addStudent("Bat", "Man", "Math", "Senior", 1.0);
		StudentHashMap.addStudent("Super", "Man", "Biology", "Senior", 0.5);
		StudentHashMap.addStudent("Iron", "Man", "CS", "Sophmore", 2.0);
		StudentHashMap.addStudent("Spider", "Man", "CS", "Junior", 1.8);
		StudentHashMap.addStudent("Jon", "Snow", "CS", "Senior", 2.7);
		StudentHashMap.addStudent("Rick", "James", "Math", "Senior", 3.8);
		StudentHashMap.addStudent("Tyrion", "Lannister", "IT", "Senior", 3.7);
		StudentHashMap.addStudent("Samwell", "Tarly", "Biology", "Senior", 0.5);
		StudentHashMap.addStudent("Khal", "Drogo", "CS", "Junior", 1.8);
		StudentHashMap.addStudent("Khalisi", "Daenerys", "Chemistry", "Senior", 2.7);
		StudentHashMap.addStudent("The", "Rock", "Math", "Senior", 3.4);
		StudentHashMap.addStudent("Sansa", "Stark", "IT", "Senior", 3.2);
		StudentHashMap.addStudent("Arya", "Stark", "Biology", "Senior", 0.8);
		StudentHashMap.addStudent("Robb", "Stark", "math", "Freshmen", 3.6);
		StudentHashMap.addStudent("Benjen", "Stark", "Geography", "Senior", 3.6);


		frame = new JFrame();
		frame.setBounds(100, 100, 670, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Student Database");
		lblNewLabel.setFont(new Font("Serif", Font.PLAIN, 36));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 654, 54);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Add Student");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							// loads the add student menu
							loadAddStudent window = new loadAddStudent(studentHashMap);
							window.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNewButton.setBounds(143, 76, 364, 40);
		frame.getContentPane().add(btnNewButton);

		JButton btnRemoveStudent = new JButton("Remove Student");
		btnRemoveStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							// loads the remove student menu
							loadRemoveStudent window = new loadRemoveStudent(studentHashMap);
							window.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		btnRemoveStudent.setBounds(143, 142, 364, 40);
		frame.getContentPane().add(btnRemoveStudent);

		JButton btnViewStudentRecords = new JButton("View Student Records");
		btnViewStudentRecords.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							// loads the view student menu
							@SuppressWarnings("unused")
							loadViewStudent window = new loadViewStudent(studentHashMap);
							loadViewStudent.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnViewStudentRecords.setBounds(143, 210, 364, 40);
		frame.getContentPane().add(btnViewStudentRecords);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);// Exits the menu
			}
		});
		btnExit.setBounds(231, 300, 189, 40);
		frame.getContentPane().add(btnExit);

		// frame.setUndecorated(true);
		// frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
	}

}
