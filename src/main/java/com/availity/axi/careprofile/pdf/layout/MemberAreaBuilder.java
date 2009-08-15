package com.availity.axi.careprofile.pdf.layout;

import java.awt.*;

import com.availity.axi.careprofile.pdf.layout.Builder;
import com.availity.axi.careprofile.pdf.PdfTableUtility;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class MemberAreaBuilder implements Builder {
  private String[] memberAreaDisplayData;

  public MemberAreaBuilder(String[] memberAreaDisplayData) {
    if (memberAreaDisplayData == null) {
      throw new IllegalArgumentException("Member Area display data cannot be null.");
    }
    this.memberAreaDisplayData = memberAreaDisplayData;
  }

  public Element build() throws Exception {
    PdfPTable table = PdfTableUtility.getInstance().constructTable(MEMBER_AREA_WIDTHS.length, MEMBER_AREA_WIDTHS, PdfPCell.BOTTOM);
    constructMemberNameRow(table, memberAreaDisplayData[0]);
    // starting with an index of 1 because 0 is Member name
    for (int i = 1; i < MEMBER_AREA_LABELS.length; i++) {
      constructLabelCell(table, MEMBER_AREA_LABELS[i]);
      constructValueCell(table, memberAreaDisplayData[i]);
    }
    finishOutPayerRow(table);
    addBlankRow(table);
    return table;
  }

  private void finishOutPayerRow(PdfPTable table) {
    BLANK_CELL.setBorder(PdfPCell.BOTTOM);
    BLANK_CELL.setBorderColor(Color.LIGHT_GRAY);
    for (int i = 0; i < 4; i++) {
      table.addCell(BLANK_CELL);
    }
  }

  private void addBlankRow(PdfPTable table) {
    BLANK_CELL.setBorder(PdfPCell.NO_BORDER);
    for (float MEMBER_AREA_WIDTH : MEMBER_AREA_WIDTHS) {
      table.addCell(BLANK_CELL);
    }
  }

  private void constructMemberNameRow(PdfPTable table, String name) {
    Chunk memberChunk = new Chunk(MEMBER_AREA_LABELS[0], FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
    Chunk nameChunk = new Chunk(name, FontFactory.getFont(FontFactory.HELVETICA, 15));
    Paragraph memberName = new Paragraph();
    memberName.add(memberChunk);
    memberName.add(nameChunk);
    PdfPCell memberCell = new PdfPCell(memberName);
    memberCell.setColspan(MEMBER_AREA_WIDTHS.length);
    memberCell.setBorder(PdfPCell.BOTTOM);
    memberCell.setBorderColor(Color.LIGHT_GRAY);
    table.addCell(memberCell);
  }

  private void constructValueCell(PdfPTable table, String value) {
    Paragraph ageValueParagraph = new Paragraph(value, FontFactory.getFont(FontFactory.HELVETICA, 10));
    PdfPCell cell = new PdfPCell(ageValueParagraph);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    cell.setBorder(PdfPCell.BOTTOM);
    cell.setBorderColor(Color.LIGHT_GRAY);
    table.addCell(cell);
  }

  private void constructLabelCell(PdfPTable table, String label) {
    Paragraph ageValueParagraph = new Paragraph(label, FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD));
    PdfPCell cell = new PdfPCell(ageValueParagraph);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    cell.setBorder(PdfPCell.BOTTOM);
    cell.setBorderColor(Color.LIGHT_GRAY);
    table.addCell(cell);
  }
}
