package com.availity.axi.careprofile.pdf.layout;

import java.io.IOException;

import com.availity.axi.careprofile.pdf.layout.Builder;
import com.availity.axi.careprofile.pdf.PdfTableUtility;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class DisclaimerBuilder implements Builder {
  private static final String DISCLAIMER_TITLE_TEXT = "CareProfile Disclaimer";
  private static final String DISCLAIMER_TEXT = "Information provided through the CareProfile includes only information " +
      "submitted to participating insurance companies for payment purposes. The information is not a medical report, nor " +
      "is it intended to be a complete record of a patient’s health information. Certain information may have been " +
      "intentionally excluded (due to its sensitivity – psychiatric, substance abuse, HIV/AIDS, sexually transmitted " +
      "diseases, and abortion related data – or for other reasons) and the health record may also contain errors. " +
      "Physicians must use their professional judgment to verify this information and should not exclusively rely on " +
      "this information to treat their patients. ";
  private static final int NUMBER_OF_COLUMNS = 1;


  public Element build() throws DocumentException, IOException {
    PdfPTable table = PdfTableUtility.getInstance().constructTable(NUMBER_OF_COLUMNS);
    table.addCell(constructTitleCell());
    table.addCell(constructBody());
    return table;
  }

  private PdfPCell constructBody() {
    Paragraph body = new Paragraph(DISCLAIMER_TEXT, FontFactory.getFont(FontFactory.HELVETICA, 8));
    PdfPCell bodyCell = new PdfPCell(body);
    bodyCell.setBorder(PdfPCell.NO_BORDER);
    return bodyCell;
  }

  private PdfPCell constructTitleCell() {
    Paragraph title = new Paragraph(DISCLAIMER_TITLE_TEXT, FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
    PdfPCell titleCell = new PdfPCell(title);
    titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    titleCell.setBorder(PdfPCell.NO_BORDER);
    return titleCell;
  }
}
