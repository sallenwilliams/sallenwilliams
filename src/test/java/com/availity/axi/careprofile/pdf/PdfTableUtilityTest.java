package com.availity.axi.careprofile.pdf;

import junit.framework.TestCase;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class PdfTableUtilityTest extends TestCase {

  private static final float[] SIX_WIDTHS = {.9f, 2f, 1.3f, 2f, 1.2f, 1.8f};
  private static final float[] FOUR_WIDTHS = {.9f, 2f, 1.3f, 2f};

  public void testPositive() throws Exception {
    PdfPTable table = PdfTableUtility.getInstance().constructTable(4, FOUR_WIDTHS, PdfPCell.NO_BORDER);
    assertNotNull(table);
    assertEquals(FOUR_WIDTHS.length, table.getAbsoluteWidths().length);
  }

  public void testZeroNumberOfColumns() throws Exception {
    try {
      PdfTableUtility.getInstance().constructTable(0, new float[0], 0);
      fail("Should have failed with an ILA");
    } catch (IllegalArgumentException e) {
      assertEquals("Number of columns must be greater than zero.", e.getMessage());
    }
  }

  public void testZeroSizeWidths() throws Exception {
    try {
      PdfTableUtility.getInstance().constructTable(4, new float[0], PdfPCell.NO_BORDER);
      fail("Should have failed with an ILA");
    } catch (IllegalArgumentException e) {
      assertEquals("Number of widths must be greater than zero.", e.getMessage());
    }
  }

  public void testNonMatchingColumnsAndWidths() throws Exception {
    try {
      PdfTableUtility.getInstance().constructTable(4, SIX_WIDTHS, PdfPCell.NO_BORDER);
      fail("Should have failed with an ILA");
    } catch (IllegalArgumentException e) {
      assertEquals("Number of columns must be equal to number of widths.", e.getMessage());
    }
  }

  public void testNumberOfColumnsAndBorder() throws Exception {
    int numberOfColumns = 20;
    PdfPTable table = PdfTableUtility.getInstance().constructTable(numberOfColumns);
    assertNotNull(table);
    assertEquals(numberOfColumns, table.getAbsoluteWidths().length);
  }

  public void testZeroNumberOfColumnsForColumnsAndBorder() throws Exception {
    try {
      PdfTableUtility.getInstance().constructTable(0);
      fail("Should have failed with an ILA");
    } catch (IllegalArgumentException e) {
      assertEquals("Number of columns must be greater than zero.", e.getMessage());
    }
  }
}