package com.availity.axi.careprofile.pdf.data;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class TableMetadata {

  private final String tableHeaderLabel;
  private final float[] widths;
  private final String[] columnLabels;
  private final String[][] displayData;

  public TableMetadata(String tableHeaderLabel, float[] widths, String[] columnLabels, String[][] displayData) {
    this.tableHeaderLabel = tableHeaderLabel;
    this.widths = widths;
    this.columnLabels = columnLabels;
    this.displayData = displayData;
  }

  public String getTableHeaderLabel() {
    return tableHeaderLabel;
  }

  public float[] getWidths() {
    return widths;
  }

  public String[] getColumnLabels() {
    return columnLabels;
  }

  public String[][] getDisplayData() {
    return this.displayData;
  }
}
