package com.availity.axi.careprofile.pdf.data;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class AcpPdfDisplayData {
  private String[][] prescriptionsDisplayData;
  private String[] memberAreaDisplayData;
  private String[][] labDisplayData;
  private String[][] radiologyDisplayData;
  private String[][] providerDisplayData;
  private String[][] diagnosisDisplayData;
  private String[][] hospitalDisplayData;
  private String[][] immunizationDisplayData;
  private String[][] labResultsDisplayData;

  public AcpPdfDisplayData(String[] memberAreaDisplayData, String[][] prescriptionsDisplayData,
                           String[][] labDisplayData, String[][] labResultsDisplayData,
                           String[][] radiologyDisplayData, String[][] providerDisplayData,
                           String[][] diagnosisDisplayData, String[][] hospitalDisplayData,
                           String[][] immunizationDisplayData) {
    initialize(memberAreaDisplayData, prescriptionsDisplayData, labDisplayData, labResultsDisplayData,
        radiologyDisplayData, providerDisplayData, diagnosisDisplayData, hospitalDisplayData, immunizationDisplayData);
  }

  public String[][] getPrescriptionsDisplayData() {
    return prescriptionsDisplayData;
  }

  public String[][] getLabDisplayData() {
    return labDisplayData;
  }

  public String[][] getRadiologyDisplayData() {
    return radiologyDisplayData;
  }

  public String[][] getProviderDisplayData() {

    return providerDisplayData;
  }

  public String[][] getDiagnosisDisplayData() {
    return diagnosisDisplayData;
  }

  public String[][] getHospitalDisplayData() {
    return hospitalDisplayData;
  }

  public String[][] getImmunizationDisplayData() {
    return immunizationDisplayData;
  }

  public String[] getMemberAreaDisplayData() {
    return memberAreaDisplayData;
  }

  public String[][] getLabResultsDisplayData() {
    return labResultsDisplayData;
  }

  private void initialize(String[] memberAreaDisplayData, String[][] prescriptionsDisplayData, String[][] labDisplayData,
                          String[][] labResultsDisplayData, String[][] radiologyDisplayData, String[][] providerDisplayData,
                          String[][] diagnosisDisplayData, String[][] hospitalDisplayData, String[][] immunizationDisplayData) {
    this.memberAreaDisplayData = memberAreaDisplayData;
    this.prescriptionsDisplayData = prescriptionsDisplayData;
    this.labDisplayData = labDisplayData;
    this.labResultsDisplayData = labResultsDisplayData;
    this.setRadiologyDisplayData(radiologyDisplayData);
    this.providerDisplayData = providerDisplayData;
    this.diagnosisDisplayData = diagnosisDisplayData;
    this.hospitalDisplayData = hospitalDisplayData;
    this.immunizationDisplayData = immunizationDisplayData;
  }

  public void setRadiologyDisplayData(String[][] radiologyDisplayData) {
    this.radiologyDisplayData = radiologyDisplayData;
  }
}
