package com.availity.axi.careprofile.pdf.layout;

import java.awt.*;
import java.io.IOException;

import com.availity.axi.careprofile.pdf.PdfTableUtility;
import com.availity.axi.careprofile.pdf.data.TableMetadata;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class TableBuilder implements Builder {

  private TableMetadata tableMetaData;

  public TableBuilder(TableMetadata tableMetadata) {
    if (tableMetadata == null) {
      throw new IllegalArgumentException("TableMetadata cannot be null.");
    }
    this.tableMetaData = tableMetadata;
  }

  public Element build() throws DocumentException, IOException {
    PdfPTable table = PdfTableUtility.getInstance().constructTable(tableMetaData.getWidths().length, tableMetaData.getWidths(), PdfPCell.NO_BORDER);
    table.addCell(constructHeaderCell());
    String[] columnLabels = tableMetaData.getColumnLabels();
    for (String columnLabel : columnLabels) {
      table.addCell(constructColumnHeaders(columnLabel));
    }
    addDataToTable(table, tableMetaData.getDisplayData());
    return table;
  }

  private void addDataToTable(PdfPTable table, String[][] displayData) {
    for (String[] row : displayData) {
      for (String column : row) {
        Chunk memberChunk = new Chunk(column, FontFactory.getFont(FontFactory.HELVETICA, 8));
        PdfPCell cell = new PdfPCell(new Paragraph(memberChunk));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
      }
    }
  }

  private PdfPCell constructColumnHeaders(String columnLabel) {
    Chunk memberChunk = new Chunk(columnLabel, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD));
    PdfPCell cell = new PdfPCell(new Paragraph(memberChunk));
    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    cell.setBackgroundColor(new Color(229, 235, 247));
    cell.setBorderColor(Color.WHITE);
    return cell;
  }

  private PdfPCell constructHeaderCell() {
    Chunk memberChunk = new Chunk(tableMetaData.getTableHeaderLabel(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD));
    PdfPCell cell = new PdfPCell(new Paragraph(memberChunk));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setColspan(tableMetaData.getWidths().length);
    cell.setBackgroundColor(new Color(226, 223, 223));
    cell.setBorder(PdfPCell.NO_BORDER);
    return cell;
  }
}
