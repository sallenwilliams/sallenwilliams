package com.availity.axi.careprofile.pdf.layout;

import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPCell;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public interface Builder {

  PdfPCell BLANK_CELL = new PdfPCell();
  float[] MEMBER_AREA_WIDTHS = {.9f, 2f, 1.3f, 2f, 1.2f, 1.8f};
  String[] MEMBER_AREA_LABELS = {"Member:", "Age:", "Gender:", "Address:", "DOB:", "Phone:", "", "PCP:", "Specialty:", "Phone:", "Payer:"};
  float[] PRESCRIPTIONS_WIDTHS = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
  String PRESCRIPTIONS_TABLE_LABEL = "Prescriptions";
  String[] PRESCRIPTIONS_COLUMN_HEADER_LABELS = {"Date Filled", "Drug Name", "Dosage", "Quantity Filled",
      "Route", "New or Refill", "Prescribing Physician", "Data Source"};

  String LAB_ORDERS_TABLE_LABEL = "Lab Orders";
  float[] LAB_ORDERS_WIDTHS = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
  String[] LAB_ORDERS_COLUMN_HEADER_LABELS = {"Date Collected", "Test Name", "Result", "Flag",
      "Normal Range", "Comment", "Ordering Physician", "Test Class", "Data Source"};

  String LAB_RESULTS_TABLE_LABEL = "Lab Results";
  float[] LAB_RESULTS_WIDTHS = {1f, .9f, 1f, 1f, 1f, 1f, 1f, 1.1f};
  String[] LAB_RESULTS_COLUMN_HEADER_LABELS = {"Date Collected", "Test", "Flag", "Result",
      "Normal Range", "Comment", "Ordering Physician", "Data Source"};

  String RAD_TABLE_LABEL = "Radiology";
  float[] RAD_WIDTHS = {1f, 1f, 1f, 1f, 1f, 1f};
  String[] RAD_COLUMN_HEADER_LABELS = {"Date of Service", "Procedure Code", "Description", "Ordering Provider",
      "Rendering Provider", "Data Source"};

  String PROVIDER_TABLE_LABEL = "Provider";
  float[] PROVIDER_WIDTHS = {1f, 1f, 1f, 1f, 1f, 1f};
  String[] PROVIDER_COLUMN_HEADER_LABELS = {"Date of Last Visit", "Name", "Specialty", "Phone", "City/State",
      "# of Visits"};

  String DIAGNOSIS_TABLE_LABEL = "Diagnosis";
  float[] DIAGNOSIS_WIDTHS = {1f, 1f, 1f, 1f, 1f, 1f};
  String[] DIAGNOSIS_COLUMN_HEADER_LABELS = {"Date of Service", "Diagnosis", "Description", "Place of Service",
      "Rendering Provider", "Data Source"};

  String HOSPITAL_TABLE_LABEL = "Hospital";
  float[] HOSPITAL_WIDTHS = {1f, 1f, 1f, 1f, 1f, 1f, 1f};
  String[] HOSPITAL_COLUMN_HEADER_LABELS = {"Date of Service From", "Date of Service To", "Diagnosis", "Description", "Place of Service",
      "Rendering Provider", "Data Source"};

  String IMMUNIZATION_TABLE_LABEL = "Immunization";
  float[] IMMUNIZATION_WIDTHS = {1f, 1f, 1f, 1f, 1f, 1f};
  String[] IMMUNIZATION_COLUMN_HEADER_LABELS = {"Date of Service", "Procedure Code", "Description", "Age",
      "Rendering Provider", "Data Source"};

  Element build() throws Exception;
}
