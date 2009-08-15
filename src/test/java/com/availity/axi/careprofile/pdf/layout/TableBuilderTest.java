package com.availity.axi.careprofile.pdf.layout;

import junit.framework.TestCase;

import com.availity.axi.careprofile.pdf.data.TableMetadata;
import com.availity.axi.careprofile.pdf.layout.TableBuilder;

import com.lowagie.text.pdf.PdfPTable;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class TableBuilderTest extends TestCase {

  private TableMetadata prescriptionsTableMetadata;
  private static final int ROWS = 5;
  private static final int COLUMNS = 8;

  public void setUp() {
    prescriptionsTableMetadata = new TableMetadata(TableBuilder.PRESCRIPTIONS_TABLE_LABEL, TableBuilder.PRESCRIPTIONS_WIDTHS,
        TableBuilder.PRESCRIPTIONS_COLUMN_HEADER_LABELS, createMatrixOfDisplayData());
  }

  public void testPrescriptionsBuilderPositive() throws Exception {
    TableBuilder tableBuilder = new TableBuilder(prescriptionsTableMetadata);
    PdfPTable element = (PdfPTable) tableBuilder.build();
    assertNotNull(element);
    assertEquals(ROWS + 2, element.getRows().size());
  }

  public void testNull() throws Exception {
    try {
      new TableBuilder(null);
      fail("Should have thrown an exception!");
    } catch (IllegalArgumentException iae) {
      assertEquals("TableMetadata cannot be null.", iae.getMessage());
    }
  }

  private String[][] createMatrixOfDisplayData() {
    String[][] displayData = new String[ROWS][COLUMNS];
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        displayData[i][j] = String.valueOf(i + j);
      }
    }
    return displayData;
  }
}