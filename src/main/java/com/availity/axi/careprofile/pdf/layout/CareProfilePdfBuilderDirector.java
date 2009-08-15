package com.availity.axi.careprofile.pdf.layout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.availity.axi.careprofile.pdf.data.AcpPdfDisplayData;
import com.availity.axi.careprofile.pdf.data.TableMetadata;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public class CareProfilePdfBuilderDirector {

  private Document document;
  private final List<Builder> builders = new ArrayList<Builder>();
  private AcpPdfDisplayData displayData;

  public CareProfilePdfBuilderDirector(AcpPdfDisplayData displayData, String outputPath) throws DocumentException, FileNotFoundException,
      IllegalArgumentException {
    if (displayData == null) {
      throw new IllegalArgumentException("Display Data cannot be null.");
    }
    this.displayData = displayData;
    document = new Document(PageSize.LETTER);
    PdfWriter.getInstance(document, new FileOutputStream(outputPath));
    builders.add(new HeaderBuilder());
    builders.add(new MemberAreaBuilder(displayData.getMemberAreaDisplayData()));
    builders.add(new DisclaimerBuilder());
    builders.add(new NewLineBuilder());
    builders.add(new TableBuilder(constructTableMetaDataForPrescriptions()));
    builders.add(new NewLineBuilder());
    if (displayData.getLabResultsDisplayData() != null &&
        displayData.getLabResultsDisplayData().length != 0) {
      builders.add(new TableBuilder(constructTableMetaDataForLabResults()));
    } else {
      builders.add(new TableBuilder(constructTableMetaDataForLab()));
    }
    builders.add(new NewLineBuilder());
    builders.add(new TableBuilder(constructTableMetaDataForRadiology()));
    builders.add(new NewLineBuilder());
    builders.add(new TableBuilder(constructTableMetaDataForProvider()));
    builders.add(new NewLineBuilder());
    builders.add(new TableBuilder(constructTableMetaDataForDiagnosis()));
    builders.add(new NewLineBuilder());
    builders.add(new TableBuilder(constructTableMetaDataForHospital()));
    builders.add(new NewLineBuilder());
    builders.add(new TableBuilder(constructTableMetaDataForImmunization()));
  }

  public void build() throws Exception {
    Date beginTime = new Date();
    document.open();
    for (Builder builder : builders) {
      document.add(builder.build());
    }
    document.close();
    Date endTime = new Date();
    long resultTime = endTime.getTime() - beginTime.getTime();
    System.out.println("The pdf creation time was " + resultTime / 1000 + " seconds.");
  }

  private TableMetadata constructTableMetaDataForPrescriptions() {
    return new TableMetadata(Builder.PRESCRIPTIONS_TABLE_LABEL, Builder.PRESCRIPTIONS_WIDTHS,
        Builder.PRESCRIPTIONS_COLUMN_HEADER_LABELS, displayData.getPrescriptionsDisplayData());
  }

  private TableMetadata constructTableMetaDataForLab() {
    return new TableMetadata(Builder.LAB_ORDERS_TABLE_LABEL, Builder.LAB_ORDERS_WIDTHS,
        Builder.LAB_ORDERS_COLUMN_HEADER_LABELS, displayData.getLabDisplayData());
  }

  private TableMetadata constructTableMetaDataForLabResults() {
    return new TableMetadata(Builder.LAB_RESULTS_TABLE_LABEL, Builder.LAB_RESULTS_WIDTHS,
        Builder.LAB_RESULTS_COLUMN_HEADER_LABELS, displayData.getLabResultsDisplayData());
  }

  private TableMetadata constructTableMetaDataForRadiology() {
    return new TableMetadata(Builder.RAD_TABLE_LABEL, Builder.RAD_WIDTHS,
        Builder.RAD_COLUMN_HEADER_LABELS, displayData.getRadiologyDisplayData());
  }

  private TableMetadata constructTableMetaDataForProvider() {
    return new TableMetadata(Builder.PROVIDER_TABLE_LABEL, Builder.PROVIDER_WIDTHS,
        Builder.PROVIDER_COLUMN_HEADER_LABELS, displayData.getProviderDisplayData());
  }

  private TableMetadata constructTableMetaDataForDiagnosis() {
    return new TableMetadata(Builder.DIAGNOSIS_TABLE_LABEL, Builder.DIAGNOSIS_WIDTHS,
        Builder.DIAGNOSIS_COLUMN_HEADER_LABELS, displayData.getDiagnosisDisplayData());
  }

  private TableMetadata constructTableMetaDataForHospital() {
    return new TableMetadata(Builder.HOSPITAL_TABLE_LABEL, Builder.HOSPITAL_WIDTHS,
        Builder.HOSPITAL_COLUMN_HEADER_LABELS, displayData.getHospitalDisplayData());
  }

  private TableMetadata constructTableMetaDataForImmunization() {
    return new TableMetadata(Builder.IMMUNIZATION_TABLE_LABEL, Builder.IMMUNIZATION_WIDTHS,
        Builder.IMMUNIZATION_COLUMN_HEADER_LABELS, displayData.getImmunizationDisplayData());
  }

}