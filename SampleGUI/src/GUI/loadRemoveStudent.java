package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import Code.StudentHashMap;

/**
 * The Class loadRemoveStudent defines the layout that remove students from the
 * database (Student HashMap)
 *
 * @author JigarDP&AbdullahA
 * @version 1.0
 */
public class loadRemoveStudent {

	/** The frame. */
	JFrame frame;

	/** The student hash map. */
	static StudentHashMap studentHashMap;

	/** The text field. */
	private JTextField textField;

	/**
	 * Create the application.
	 *
	 * @param studentMap
	 *            the StudentHashMap
	 */
	public loadRemoveStudent(StudentHashMap studentMap) {
		studentHashMap = studentMap;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JButton btnRemoveStudent = new JButton("Remove Student");
		btnRemoveStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Border border = BorderFactory.createLineBorder(Color.red);

				// Checks if input is empty. If its empty then the textfield becomes red
				if ((textField.getText().equals(""))) {
					textField.setBorder(border);
				}
				// if input is not empty then it proceeds to remove student
				else {

					// checks if input is an Integer. If its integer then removes the ID from the
					// Database (Student HashMAp)
					try {
						int id = Integer.parseInt(textField.getText().trim());

						if (studentHashMap.containsKey(id)) {
							JOptionPane.showMessageDialog(frame, "Removed: " + "(" + id + ")"
									+ StudentHashMap.getFirstName(id) + " " + StudentHashMap.getLastName(id));
							StudentHashMap.removeStudent(id);
						} else {
							JOptionPane.showMessageDialog(frame, "Student not Found");
						}

						// If id is not integer then outputs and error message
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(frame, "ID is not of valid type");
						textField.setText("");
						textField.setBorder(border);
					}
				}
			}
		});
		btnRemoveStudent.setBounds(145, 184, 145, 29);
		frame.getContentPane().add(btnRemoveStudent);

		textField = new JTextField();

		textField.setBounds(228, 70, 134, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblEnterIdHere = new JLabel("Enter ID here");
		lblEnterIdHere.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterIdHere.setBounds(47, 73, 122, 22);
		frame.getContentPane().add(lblEnterIdHere);

		JLabel label = new JLabel("Remove Student Menu");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(6, 6, 438, 42);
		frame.getContentPane().add(label);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose(); // closes the frame and returns back to the main menu
			}
		});
		btnExit.setBounds(155, 218, 122, 29);
		frame.getContentPane().add(btnExit);
	}
}
