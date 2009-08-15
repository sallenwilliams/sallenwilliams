package com.availity.axi.careprofile.pdf;

import java.io.File;

import junit.framework.TestCase;

import com.availity.axi.careprofile.pdf.data.AcpPdfDisplayData;
import com.availity.axi.careprofile.pdf.data.AcpPdfDisplayDataAdapter;
import com.availity.axi.careprofile.pdf.data.AcpPdfDisplayDataAdapterTest;
import com.availity.axi.careprofile.pdf.layout.CareProfilePdfBuilderDirectorTest;
import com.availity.axi.careprofile.pdf.layout.CareProfilePdfBuilderDirector;
import com.availity.bo.careprofile.CareRecord;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class AcpPdfDataLayoutIntegrationTest extends TestCase {
  static String pdfLocation = CareProfilePdfBuilderDirectorTest.PDF_LOCATION;

  public void testBuildPdf() throws Exception {
    new File(pdfLocation).delete();
    assertFalse(new File(pdfLocation).isFile());

    CareRecord careRecord = AcpPdfDisplayDataAdapterTest.createCareRecord();
    //adapt
    AcpPdfDisplayData acpPdfDisplayData = AcpPdfDisplayDataAdapter.adapt(careRecord);
    // build
    CareProfilePdfBuilderDirector careProfilePdf = new CareProfilePdfBuilderDirector(acpPdfDisplayData, pdfLocation);
    careProfilePdf.build();
    assertTrue(new File(pdfLocation).isFile());
  }

}