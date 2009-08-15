package com.availity.axi.careprofile.pdf.layout;

import junit.framework.TestCase;

import com.availity.axi.careprofile.pdf.layout.DisclaimerBuilder;

import com.lowagie.text.pdf.PdfPTable;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class DisclaimerBuilderTest extends TestCase {

  public void testDisclaimerBuilderPositive() throws Exception {
    DisclaimerBuilder disclaimerBuilder = new DisclaimerBuilder();
    PdfPTable disclaimerTable = (PdfPTable) disclaimerBuilder.build();
    assertNotNull(disclaimerTable);
    assertTrue(disclaimerTable.size() == 2);
  }
}