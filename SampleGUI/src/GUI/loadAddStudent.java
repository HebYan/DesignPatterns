package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import Code.StudentHashMap;

/**
 * The Class loadAddStudent defines the layout that adds students to the
 * database (Student HashMap)
 *
 * @author JigarDP&AbdullahA
 * @version 1.0
 */
public class loadAddStudent {

	/** The frame. */
	public JFrame frame;

	/** The text field. */
	private JTextField textField;

	/** The lbl last name. */
	private JLabel lblLastName;

	/** The lbl major. */
	private JLabel lblMajor;

	/** The lbl year. */
	private JLabel lblYear;

	/** The lbl gpa. */
	private JLabel lblGpa;

	/** The text field 1. */
	private JTextField textField_1;

	/** The text field 2. */
	private JTextField textField_2;

	/** The text field 4. */
	private JTextField textField_4;

	/** The btn new button. */
	private JButton btnNewButton;

	/** The btn new button 1. */
	private JButton btnNewButton_1;

	/** The student hash map. */
	static StudentHashMap studentHashMap;

	/**
	 * Create the application.
	 *
	 * @param studentMap
	 *            the Student HashMap
	 */
	public loadAddStudent(StudentHashMap studentMap) {
		studentHashMap = studentMap;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 656, 478);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		JLabel lblAddStudentMenu = new JLabel("Add Student Menu");
		lblAddStudentMenu.setFont(new Font("Serif", Font.PLAIN, 30));
		lblAddStudentMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddStudentMenu.setBounds(10, 11, 594, 70);
		frame.getContentPane().add(lblAddStudentMenu);

		textField = new JTextField();
		textField.setBounds(313, 93, 305, 31);
		frame.getContentPane().add(textField);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstName.setBounds(10, 92, 293, 32);
		frame.getContentPane().add(lblFirstName);

		lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastName.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		lblLastName.setBounds(10, 135, 293, 32);
		frame.getContentPane().add(lblLastName);

		lblMajor = new JLabel("Major");
		lblMajor.setHorizontalAlignment(SwingConstants.CENTER);
		lblMajor.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		lblMajor.setBounds(10, 178, 293, 32);
		frame.getContentPane().add(lblMajor);

		lblYear = new JLabel("Year");
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		lblYear.setBounds(10, 221, 293, 32);
		frame.getContentPane().add(lblYear);

		lblGpa = new JLabel("GPA");
		lblGpa.setHorizontalAlignment(SwingConstants.CENTER);
		lblGpa.setFont(new Font("Berlin Sans FB", Font.PLAIN, 20));
		lblGpa.setBounds(10, 264, 293, 32);
		frame.getContentPane().add(lblGpa);

		textField_1 = new JTextField();
		textField_1.setBounds(313, 135, 305, 31);
		frame.getContentPane().add(textField_1);

		textField_2 = new JTextField();
		textField_2.setBounds(313, 178, 305, 31);
		frame.getContentPane().add(textField_2);

		textField_4 = new JTextField();
		textField_4.setBounds(313, 264, 305, 31);
		frame.getContentPane().add(textField_4);

		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Freshman", "Sophomore", "Junior", "Senior" })); // Combobox
																													// for
																													// selecting
																													// the
																													// year
		comboBox.setBounds(313, 226, 149, 29);
		frame.getContentPane().add(comboBox);

		@SuppressWarnings("unused")
		String Year2 = (String) comboBox.getSelectedItem();

		btnNewButton = new JButton("Add Student");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Border border = BorderFactory.createLineBorder(Color.red);

				// Checks if any field is empty. If any field is empty then the textfield(s)
				// become red
				if ((textField.getText().equals("")) || (textField_1.getText().equals(""))
						|| (textField_2.getText().equals("")) || (comboBox.getSelectedItem().equals(""))
						|| (textField_4.getText().equals(""))) {

					if ((textField.getText().equals(""))) {
						textField.setBorder(border);
					}
					if ((textField_1.getText().equals(""))) {
						textField_1.setBorder(border);
					}
					if ((textField_2.getText().equals(""))) {
						textField_2.setBorder(border);
					}
					if (comboBox.getSelectedItem().equals("")) {
						comboBox.setBorder(border);
					}
					if ((textField_4.getText().equals(""))) {
						textField_4.setBorder(border);
					}

					// If the field is not empty then it proceeds to add the student
				} else {
					String fName = textField.getText();
					String LName = textField_1.getText();
					String Major = textField_2.getText();
					String Year = (String) comboBox.getSelectedItem();

					Double gpa = 0.0;

					// checks if GPA is double
					try {

						gpa = Double.parseDouble(textField_4.getText());
						Double.valueOf(2.3);

						// Range check if statement
						if ((textField.getText().length() >= 15) || (textField_1.getText().length() >= 20)
								|| (textField_2.getText().length() >= 30) || (gpa < 0 || gpa > 4.0)) {
							if ((textField.getText().length() >= 15)) {
								JOptionPane.showMessageDialog(frame, "First name should be less than 15 characters");
								textField.setText("");
								textField.setBorder(border);
							}

							else if ((textField_1.getText().length() >= 20)) {
								JOptionPane.showMessageDialog(frame, "Last name should be less than 20 characters");
								textField_1.setText("");
								textField_1.setBorder(border);
							}

							else if ((textField_2.getText().length() >= 30)) {
								JOptionPane.showMessageDialog(frame, "Major should be less than 30 characters");
								textField_2.setText("");
								textField_2.setBorder(border);
							}

							else if (gpa < 0 || gpa > 4.0) {
								JOptionPane.showMessageDialog(frame, "GPA should be between 0.0 to 4.0");
								textField_4.setText("");
								textField_4.setBorder(border);
							}

						} else {
							StudentHashMap.addStudent(fName, LName, Major, Year, gpa);
							studentHashMap.getKeyID();
							JOptionPane.showMessageDialog(frame,
									"Added: " + fName + " " + LName + ". Student ID is: " + studentHashMap.getKeyID());

							textField.setText("");
							textField_1.setText("");
							textField_2.setText("");
							comboBox.setSelectedItem("");
							textField_4.setText("");
						}

						// if GPA is not double then displays this message
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(frame, "GPA is not of valid type");
						textField_4.setText("");
						textField_4.setBorder(border);
					}

				}

			}
		});
		btnNewButton.setBounds(216, 339, 185, 43);
		frame.getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("Finish");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose(); // closes the frame and returns back to the main menu
			}
		});
		btnNewButton_1.setBounds(248, 393, 131, 23);
		frame.getContentPane().add(btnNewButton_1);

	}
}
