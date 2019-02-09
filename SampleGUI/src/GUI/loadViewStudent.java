package GUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Code.Searches;
import Code.StudentHashMap;

/**
 * The Class loadViewStudent defines the layout that displays records of
 * students. The class also allows searching and sorting.
 *
 * @author JigarDP&AbdullahA
 * @version 1.0
 */
public class loadViewStudent {

	/** The frame. */
	public static JFrame frame;

	/** The student hash map. */
	static StudentHashMap studentHashMap;

	/** The table. */
	private static JTable table;

	/** The search. */
	static Searches search = new Searches();

	/** The time start. */
	static long timeStart;

	/** The timestop. */
	static long timestop;

	/**
	 * Create the application.
	 *
	 * @param studentMap
	 *            the StudentHashMap
	 */
	public loadViewStudent(StudentHashMap studentMap) {
		studentHashMap = studentMap;
		initialize();
	}

	/**
	 * To table model.
	 *
	 * @return the table model that displays all the student in the system (Student
	 *         HashMap)
	 */
	private static TableModel toTableModel() {

		DefaultTableModel model = new DefaultTableModel(
				new Object[] { "Student ID", "First Name", "Last Name", "Major", "Year", "GPA" }, 0);

		@SuppressWarnings("rawtypes")
		Iterator iterator = studentHashMap.keySet().iterator();

		while (iterator.hasNext()) {
			int key = (int) iterator.next();
			String fname = StudentHashMap.getFirstName(key);
			String lname = StudentHashMap.getLastName(key);
			String major = StudentHashMap.getMajor(key);
			String year = StudentHashMap.getYear(key);
			String GPA = StudentHashMap.getGPA(key);

			// insert on first position by pushing down the existing rows
			model.insertRow(0, new Object[] { key, fname, lname, major, year, GPA });

			// append at the end
			// model.addRow(new Object[]{key, value});
		}

		return model;
	}

	/** The model 2. */
	private static DefaultTableModel model2 = new DefaultTableModel(
			new Object[] { "Student ID", "First Name", "Last Name", "Major", "Year", "GPA" }, 0);

	/**
	 * Binary search.
	 *
	 * @param studentMap
	 *            the StudentHashMap
	 * @param keyId
	 *            the Student Id to search for
	 * @param keyStart
	 *            the minimum value the binary search will use
	 * @param high
	 *            the maximum value the binary search will use
	 * @return the table model that returns the student associated to the ID being
	 *         searched for. If not found displays a 'not found' message.
	 */
	private static TableModel binarySearch(StudentHashMap studentMap, int keyId, int keyStart, int high) {
		timeStart = System.nanoTime();
		int start = keyStart;
		int end = high;
		int mid = (start + end) / 2;

		if (keyId == mid) {

			if (studentHashMap.containsKey(keyId)) {
				String fname = StudentHashMap.getFirstName(keyId);
				String lname = StudentHashMap.getLastName(keyId);
				String major = StudentHashMap.getMajor(keyId);
				String year = StudentHashMap.getYear(keyId);
				String GPA = StudentHashMap.getGPA(keyId);

				DefaultTableModel rem = (DefaultTableModel) table.getModel();
				int rows = rem.getRowCount();
				// Remove rows one by one from the end of the table
				for (int i = rows - 1; i >= 0; i--) {
					rem.removeRow(i);
				}
				// Displays the student that is found in the JTable
				model2.insertRow(0, new Object[] { keyId, fname, lname, major, year, GPA });

			} else {
				// This is displayed if student is not found
				JOptionPane.showMessageDialog(frame, "Student not found");
			}

		} else if (keyId < mid) {
			binarySearch(studentMap, keyId, start, (mid - 1));
		} else if (keyId > mid) {
			binarySearch(studentMap, keyId, (mid + 1), end);
		}

		timestop = System.nanoTime();

		return model2;
	}

	/**
	 * Linear search.
	 *
	 * @param studentMap
	 *            the StudentHashMap
	 * @param keyId
	 *            the Student ID to search for
	 * @return the table model that returns the student associated to the ID being
	 *         searched for. If not found displays a 'not found' message.
	 */
	private static TableModel linearSearch(StudentHashMap studentMap, int keyId) {

		timeStart = System.nanoTime();

		DefaultTableModel model = new DefaultTableModel(
				new Object[] { "Student ID", "First Name", "Last Name", "Major", "Year", "GPA" }, 0);

		if (studentHashMap.containsKey(keyId)) {
			String fname = StudentHashMap.getFirstName(keyId);
			String lname = StudentHashMap.getLastName(keyId);
			String major = StudentHashMap.getMajor(keyId);
			String year = StudentHashMap.getYear(keyId);
			String GPA = StudentHashMap.getGPA(keyId);

			// Displays the student that is found in the JTable
			model.insertRow(0, new Object[] { keyId, fname, lname, major, year, GPA });

		} else {
			// This is displayed if student is not found
			JOptionPane.showMessageDialog(frame, "Student not found");
		}

		timestop = System.nanoTime();

		return model;
	}

	private static void sortStudentId() {

		timeStart = System.nanoTime();

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>();

		int columnIndexToSort = 0;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

		sorter.setSortKeys(sortKeys);
		sorter.sort();

		timestop = System.nanoTime();

	}

	private static void sortGPA() {
		timeStart = System.nanoTime();

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>();

		int columnIndexToSort = 5;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));

		sorter.setSortKeys(sortKeys);
		sorter.sort();

		timestop = System.nanoTime();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 646, 501);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setTitle("Student Records Viewer");
		frame.setResizable(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 610, 204);

		JLabel lblViewStudentRecords = new JLabel("View Student Records");
		lblViewStudentRecords.setBounds(10, 11, 610, 39);
		lblViewStudentRecords.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewStudentRecords.setFont(new Font("Serif", Font.PLAIN, 30));

		JButton btnFinish = new JButton("Finish");
		btnFinish.setBounds(500, 388, 106, 23);
		btnFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				DefaultTableModel rem = (DefaultTableModel) table.getModel();
				int rows = rem.getRowCount();
				// Remove rows one by one from the end of the table
				for (int i = rows - 1; i >= 0; i--) {
					rem.removeRow(i);
				}

				frame.dispose(); // closes the frame and returns back to the
									// main menu
			}
		});

		table = new JTable();
		table.setModel(toTableModel());
		scrollPane.setViewportView(table);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "For Developers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 298, 610, 79);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		final JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 23, 590, 45);
		panel.add(lblNewLabel);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(frame.getContentPane(), popupMenu);

		JMenu mnSort = new JMenu("SORT");
		popupMenu.add(mnSort);

		JMenuItem mntmSortByStudent = new JMenuItem("Sort by Student ID");
		mntmSortByStudent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sortStudentId();
				lblNewLabel.setText("For this sort it took " + (timestop - timeStart) + " ns");
			}
		});
		mnSort.add(mntmSortByStudent);

		JMenuItem mntmSortByGpa = new JMenuItem("Sort by GPA");
		mntmSortByGpa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortGPA();
				lblNewLabel.setText("For this sort it took " + (timestop - timeStart) + " ns");
			}
		});
		mnSort.add(mntmSortByGpa);
		frame.getContentPane().add(lblViewStudentRecords);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(btnFinish);

		final JButton btnNewButton = new JButton("Clear Search");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				DefaultTableModel rem = (DefaultTableModel) table.getModel();
				int rows = rem.getRowCount();
				// Remove rows one by one from the end of the table
				for (int i = rows - 1; i >= 0; i--) {
					rem.removeRow(i);
				}

				table.setModel(toTableModel());
				btnNewButton.setEnabled(false);
			}
		});
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton.setBounds(20, 387, 115, 23);
		frame.getContentPane().add(btnNewButton);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnSearch = new JMenu("Search");
		menuBar.add(mnSearch);

		JMenuItem mntmBinarySearch = new JMenuItem("Binary Search");
		mntmBinarySearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// At this point, the compiler check is input is integer and is
				// it is then it sends the input to the binarySearch method.
				try {
					int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Please enter the Student ID"));

					if (id > 4000 && id < 6000) {
						table.setModel(binarySearch(studentHashMap, id, 4000, 6000));
						lblNewLabel.setText("For Binary Search it took " + (timestop - timeStart) + " ns");
						btnNewButton.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(frame, "Student ID should be within valid range");
					}

					// If input is not integer then outputs this message
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frame, "Student ID is not of valid type");
				}
			}
		});
		mnSearch.add(mntmBinarySearch);

		JMenuItem mntmLinearSearch = new JMenuItem("Linear Search");
		mntmLinearSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// At this point, the compiler check is input is integer and is
				// it is then it sends the input to the linearSearch method.
				try {
					int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Please enter the Student ID"));

					if (id > 4000 && id < 6000) {
						table.setModel(linearSearch(studentHashMap, id));
						lblNewLabel.setText("For Linear Search it took " + (timestop - timeStart) + " ns");
						btnNewButton.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(frame, "Student ID should be within valid range");
					}

					// If input is not integer then outputs this message
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frame, "Student ID is not of valid type");
				}
			}
		});
		mnSearch.add(mntmLinearSearch);
	}

	/**
	 * Adds the popup.
	 *
	 * @param component
	 *            the component
	 * @param popup
	 *            the popup
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
