package com.availity.axi.careprofile.pdf.data;

import junit.framework.TestCase;


/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class TableMetadataTest extends TestCase {
  private static final String TEST = "Test";
  private static final int ROWS = 4;
  private static final int COLUMNS = 4;

  public void testHeaderBuilderPositive() throws Exception {
    String[][] displayData = createMatrixOfDisplayData();
    TableMetadata tableMetadata = new TableMetadata(TEST, new float[]{1, 2, 3}, new String[]{"Label1", "Label2"},
        displayData);

    assertEquals(TEST, tableMetadata.getTableHeaderLabel());
    assertEquals(3, tableMetadata.getWidths().length);
    assertEquals(2, tableMetadata.getColumnLabels().length);
    assertEquals(ROWS, tableMetadata.getDisplayData().length);
  }

  private String[][] createMatrixOfDisplayData() {
    String[][] displayData = new String[ROWS][COLUMNS];
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < ROWS; j++) {
        displayData[i][j] = String.valueOf(i + j);
      }
    }
    return displayData;
  }

}