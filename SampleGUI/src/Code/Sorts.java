package Code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * The Class Sorts.
 */
public class Sorts {

	private static JTable table;

	/**
	 * Sort model only for testing. Working copy in the loadViewStuden Class
	 */
	@SuppressWarnings("unused")
	private static void sortModel() {

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);

		List<RowSorter.SortKey> sortKeys = new ArrayList<>();

		int columnIndexToSort = 0;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

		sorter.setSortKeys(sortKeys);
		sorter.sort();

		// System.out.println(((System.nanoTime() - t) / 1000000000.0) + " Sorting in
		// Seconds");

	}
}
