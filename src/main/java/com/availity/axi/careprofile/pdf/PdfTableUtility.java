package com.availity.axi.careprofile.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;

public class PdfTableUtility {
  private static final PdfTableUtility instance = new PdfTableUtility();

  public static PdfTableUtility getInstance() {
    return instance;
  }

  private PdfTableUtility() {
  }

  public PdfPTable constructTable(int numberOfColumns, float[] widths, int borderStyle) throws DocumentException,
      IllegalArgumentException {
    if (numberOfColumns <= 0) {
      throw new IllegalArgumentException("Number of columns must be greater than zero.");
    }
    if (widths.length <= 0) {
      throw new IllegalArgumentException("Number of widths must be greater than zero.");
    }
    if (widths.length != numberOfColumns) {
      throw new IllegalArgumentException("Number of columns must be equal to number of widths.");
    }
    PdfPTable table = new PdfPTable(numberOfColumns);
    table.setWidths(widths);
    table.getDefaultCell().setBorder(borderStyle);
    return table;
  }

  public PdfPTable constructTable(int numberOfColumns) {
    if (numberOfColumns <= 0) {
      throw new IllegalArgumentException("Number of columns must be greater than zero.");
    }
    PdfPTable table = new PdfPTable(numberOfColumns);
    table.getDefaultCell().setBorder(com.lowagie.text.Rectangle.NO_BORDER);
    return table;
  }
}