package com.availity.axi.careprofile.pdf.layout;

import java.io.File;

import junit.framework.TestCase;

import com.availity.axi.careprofile.pdf.data.AcpPdfDisplayData;

public class CareProfilePdfBuilderDirectorTest extends TestCase {
  public final static String PDF_LOCATION = "target/CareProfile.pdf";
  private final static String PDF_LAB_RESULTS_LOCATION = "target/CareProfileLabResults.pdf";
  private static final int PRESCRIPTION_ROWS = 100;
  private static final int PRESCRIPTION_COLUMNS = 8;
  private static final int LAB_ROWS = 50;
  private static final int LAB_COLUMNS = 9;
  private static final int RAD_ROWS = 20;
  private static final int RAD_COLUMNS = 6;
  private static final int PRO_ROWS = 40;
  private static final int PRO_COLUMNS = 6;
  private static final int DIAG_ROWS = 30;
  private static final int DIAG_COLUMNS = 6;
  private static final int HOSPITAL_ROWS = 40;
  private static final int HOSPITAL_COLUMNS = 7;
  private static final int IMMU_ROWS = 30;
  private static final int IMMU_COLUMNS = 6;
  private static final int LAB_RESULTS_ROWS = 4;
  private static final int LAB_RESULTS_COLUMNS = 10;

  public void testCreatePdf() throws Exception {
    new File(PDF_LOCATION).delete();
    assertFalse(new File(PDF_LOCATION).isFile());
    CareProfilePdfBuilderDirector careProfilePdf = new CareProfilePdfBuilderDirector(createDisplayData(), PDF_LOCATION);
    careProfilePdf.build();
    assertTrue(new File(PDF_LOCATION).isFile());
  }

  public void testCreatePdfWithLabResults() throws Exception {
    new File(PDF_LAB_RESULTS_LOCATION).delete();
    assertFalse(new File(PDF_LAB_RESULTS_LOCATION).isFile());
    CareProfilePdfBuilderDirector careProfilePdf = new CareProfilePdfBuilderDirector(createDisplayDataWithLabResults(),
        PDF_LAB_RESULTS_LOCATION);
    careProfilePdf.build();
    assertTrue(new File(PDF_LAB_RESULTS_LOCATION).isFile());
  }

  private AcpPdfDisplayData createDisplayDataWithLabResults() {
    return new AcpPdfDisplayData(createMemberAreaDisplayData(), createPrescriptionsDisplayData(), createLabDisplayData(),
        createLabResultsDisplayData(), createRadiologyDisplayData(), createProviderDisplayData(), createDiagnosisDisplayData(),
        createHospitalDisplayData(), createImmunizationDisplayData());
  }

  public void testCreatePdfWithNull() throws Exception {
    try {
      new CareProfilePdfBuilderDirector(null, "target/CareProfile.pdf");
      fail("Should have failed with an exception!");
    } catch (IllegalArgumentException e) {
      assertEquals("Display Data cannot be null.", e.getMessage());
    }
  }

  private AcpPdfDisplayData createDisplayData() {
    return new AcpPdfDisplayData(createMemberAreaDisplayData(), createPrescriptionsDisplayData(), createLabDisplayData(),
        createLabResultsDisplayData(), createRadiologyDisplayData(), createProviderDisplayData(),
        createDiagnosisDisplayData(), createHospitalDisplayData(), createImmunizationDisplayData());
  }

  String[] createMemberAreaDisplayData() {
    return new String[]{"Jane Doe", "36", "Female:", "123 Main St", "01/01/1970", "813-487-1000",
        "Tampa FL 33610", "John Smith MD", "Family Medicine", "555-555-1234", "BCBSF"};
  }

  private String[][] createPrescriptionsDisplayData() {
    return buildDisplayData(PRESCRIPTION_ROWS, PRESCRIPTION_COLUMNS);
  }

  private String[][] createLabDisplayData() {
    return buildDisplayData(LAB_ROWS, LAB_COLUMNS);
  }

  private String[][] createLabResultsDisplayData() {
    return buildDisplayData(LAB_RESULTS_ROWS, LAB_RESULTS_COLUMNS);
  }

  private String[][] createRadiologyDisplayData() {
    return buildDisplayData(RAD_ROWS, RAD_COLUMNS);
  }

  private String[][] createProviderDisplayData() {
    return buildDisplayData(PRO_ROWS, PRO_COLUMNS);
  }

  private String[][] createDiagnosisDisplayData() {
    return buildDisplayData(DIAG_ROWS, DIAG_COLUMNS);
  }

  private String[][] createHospitalDisplayData() {
    return buildDisplayData(HOSPITAL_ROWS, HOSPITAL_COLUMNS);
  }

  private String[][] createImmunizationDisplayData() {
    return buildDisplayData(IMMU_ROWS, IMMU_COLUMNS);
  }

  private String[][] buildDisplayData(int rows, int columns) {
    String[][] returnMatrix = new String[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        returnMatrix[i][j] = String.valueOf(i + j);
      }
    }
    return returnMatrix;
  }

}