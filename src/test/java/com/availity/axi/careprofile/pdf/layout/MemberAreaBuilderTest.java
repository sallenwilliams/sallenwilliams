package com.availity.axi.careprofile.pdf.layout;

import junit.framework.TestCase;

import com.availity.axi.careprofile.pdf.layout.MemberAreaBuilder;
import com.availity.axi.careprofile.pdf.layout.CareProfilePdfBuilderDirectorTest;

import com.lowagie.text.pdf.PdfPTable;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class MemberAreaBuilderTest extends TestCase {

  public void testMemberAreaBuilderPositive() throws Exception {
    CareProfilePdfBuilderDirectorTest careProfilePdfBuilderDirectorTest = new CareProfilePdfBuilderDirectorTest();
    MemberAreaBuilder memberAreaBuilder = new MemberAreaBuilder(careProfilePdfBuilderDirectorTest.createMemberAreaDisplayData());
    PdfPTable table = (PdfPTable) memberAreaBuilder.build();
    assertNotNull(table);
    assertTrue(table.size() == 6);
  }

  public void testMemberAreaBuilderNulls() throws Exception {

    try {
      new MemberAreaBuilder(null);
      fail("Should have thrown an exception.");
    } catch (Exception e) {
      assertEquals("Member Area display data cannot be null.", e.getMessage());
    }
  }
}