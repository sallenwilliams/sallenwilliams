package com.availity.axi.careprofile.pdf.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.defaultString;
import org.joda.time.DateTime;
import org.joda.time.Period;

import com.availity.axi.careprofile.web.ConverterUtil;
import com.availity.bo.careprofile.Actor;
import com.availity.bo.careprofile.CareRecord;
import com.availity.bo.careprofile.CodeDescription;
import com.availity.bo.careprofile.Encounter;
import com.availity.bo.careprofile.Medication;
import com.availity.bo.careprofile.Problem;
import com.availity.bo.careprofile.Procedure;
import com.availity.bo.careprofile.Provider;
import com.availity.bo.careprofile.Result;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class AcpPdfDisplayDataAdapter {
  private static final int NUMBER_OF_MEMBER_AREA_ELEMENTS = 11;
  private static final int PRESCRIPTION_COLUMNS = 8;
  //  private static final int LAB_COLUMNS = 9;
  private static final int LAB_RESULTS_COLUMNS = 8;
  private static final int RADIOLOGY_COLUMNS = 6;
  private static final int PROVIDER_COLUMNS = 6;
  private static final int DIAGNOSIS_COLUMNS = 6;
  private static final int HOSPITAL_COLUMNS = 7;
  private static final int IMMUNIZATIONS_COLUMNS = 6;
  private static final ConverterUtil.ActorConverter actorConverter = new ConverterUtil.ActorConverter();
  private static final ConverterUtil.SourceConverter sourceConverter = new ConverterUtil.SourceConverter();

  protected static AcpPdfDisplayData initializePdfDisplayData(final CareRecord careRecord) {
    String[] memberArea = new String[NUMBER_OF_MEMBER_AREA_ELEMENTS];
    String[][] providers = new String[careRecord.getEncounters().getProviders().size()][PROVIDER_COLUMNS];
    String[][] prescriptions = new String[careRecord.getMedications().size()][PRESCRIPTION_COLUMNS];
    String[][] labs = new String[0][0];
    String[][] labResults = new String[calculateNumberOfLabResultRows(careRecord.getResults())][LAB_RESULTS_COLUMNS];
    String[][] radiologies = new String[calculateNumberOfRadiologyRows(careRecord.getProcedures().getRadiologies())][RADIOLOGY_COLUMNS];
    String[][] diagnosis = new String[calculateNumberOfDiagnosisRows(careRecord.getProblems())][DIAGNOSIS_COLUMNS];
    String[][] hospitals = new String[careRecord.getEncounters().getHospitalEncounters().size()][HOSPITAL_COLUMNS];
    String[][] immunizations = new String[careRecord.getProcedures().getImmunizations().size()][IMMUNIZATIONS_COLUMNS];

    return new AcpPdfDisplayData(memberArea, prescriptions, labs, labResults, radiologies,
        providers, diagnosis, hospitals, immunizations);
  }

  public static AcpPdfDisplayData adapt(final CareRecord careRecord) {
    AcpPdfDisplayData returnAcpPdfDisplayData = initializePdfDisplayData(careRecord);
    buildMemberArea(careRecord, returnAcpPdfDisplayData);
    buildPrescriptions(careRecord, returnAcpPdfDisplayData);
    buildLabResults(careRecord, returnAcpPdfDisplayData);
//    buildLabOrders(careRecord, returnAcpPdfDisplayData);
    buildRadiology(careRecord, returnAcpPdfDisplayData);
    buildProviders(careRecord, returnAcpPdfDisplayData);
    buildDiagnosis(careRecord, returnAcpPdfDisplayData);
    buildHospital(careRecord, returnAcpPdfDisplayData);
    buildImmunization(careRecord, returnAcpPdfDisplayData);
    return returnAcpPdfDisplayData;
  }

  private static void buildProviders(CareRecord careRecord, AcpPdfDisplayData returnAcpPdfDisplayData) {
    String[][] providerDataElements = returnAcpPdfDisplayData.getProviderDisplayData();
    List<Provider> providers = new ArrayList<Provider>(careRecord.getEncounters().getProviders().values());
    int providerRowCounter = 0;
    for (Provider provider : providers) {
      Actor providerActor = provider.getActorReference().getActor();
      providerDataElements[providerRowCounter][0] = provider.getLatestVisit().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
      providerDataElements[providerRowCounter][1] = actorConverter.convertToString(providerActor, Locale.US);
      providerDataElements[providerRowCounter][2] = providerActor.getSpecialty().getText();
      providerDataElements[providerRowCounter][3] = providerActor.getTelephone().getValue();
      providerDataElements[providerRowCounter][4] = providerActor.getAddress().getCity() + "/" + providerActor.getAddress().getState();
      providerDataElements[providerRowCounter][5] = provider.getCount().toString();
      providerRowCounter++;
    }
  }

  private static void buildImmunization(CareRecord careRecord, AcpPdfDisplayData returnAcpPdfDisplayData) {
    String[][] immunizationDataElements = returnAcpPdfDisplayData.getImmunizationDisplayData();
    Collection<List<Procedure>> immunizations = careRecord.getProcedures().getImmunizations().values();
    int immunizationRowCounter = 0;
    for (List<Procedure> immunizationsOuter : immunizations) {
      for (Procedure immunization : immunizationsOuter) {
        immunizationDataElements[immunizationRowCounter][0] = immunization.getDateTime().getRange().getStart().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
        immunizationDataElements[immunizationRowCounter][1] = immunization.getDescription().getValue();
        immunizationDataElements[immunizationRowCounter][2] = immunization.getDescription().getText();
        String age = String.valueOf(new Period(immunization.getPatient().getActor().getPerson().
            getDateOfBirth().getDateTime(), new DateTime()).getYears());
        immunizationDataElements[immunizationRowCounter][3] = age;
        immunizationDataElements[immunizationRowCounter][4] = actorConverter.convertToString(immunization.getRenderingProvider().getActor(), Locale.US);
        immunizationDataElements[immunizationRowCounter][5] = sourceConverter.convertToString(immunization.getSource(), Locale.US);
        immunizationRowCounter++;
      }
    }
  }

  private static void buildHospital(CareRecord careRecord, AcpPdfDisplayData returnAcpPdfDisplayData) {
    String[][] hospitalDataElements = returnAcpPdfDisplayData.getHospitalDisplayData();
    List<Encounter> hospitals = new ArrayList<Encounter>(careRecord.getEncounters().getHospitalEncounters().values());
    int hospitalRowCounter = 0;
    for (Encounter hospital : hospitals) {
      hospitalDataElements[hospitalRowCounter][0] = hospital.getDateOfService().getRange().getStart()
          .toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
      hospitalDataElements[hospitalRowCounter][1] = hospital.getDateOfService().getRange().getEnd()
          .toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
      hospitalDataElements[hospitalRowCounter][2] = hospital.getDescription().getValue();
      hospitalDataElements[hospitalRowCounter][3] = hospital.getDescription().getText();
      hospitalDataElements[hospitalRowCounter][4] = hospital.getLocations().get(0).getText();
      hospitalDataElements[hospitalRowCounter][5] = actorConverter.convertToString(hospital.getRenderingProvider().getActor(), Locale.US);
      hospitalDataElements[hospitalRowCounter][6] = sourceConverter.convertToString(hospital.getSource(), Locale.US);
      hospitalRowCounter++;
    }
  }

  private static void buildDiagnosis(CareRecord careRecord, AcpPdfDisplayData returnAcpPdfDisplayData) {
    String[][] diagnosisDataElements = returnAcpPdfDisplayData.getDiagnosisDisplayData();
    ArrayList<List<Problem>> diagnosises = new ArrayList<List<Problem>>(careRecord.getProblems().values());
    int diagnosisRowCounter = 0;
    for (List<Problem> diagnosisOuter : diagnosises) {
      for (Problem diagnosis : diagnosisOuter) {
        diagnosisDataElements[diagnosisRowCounter][0] =
            diagnosis.getEncounter().getDateOfService().getRange().getStart().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
        diagnosisDataElements[diagnosisRowCounter][1] = diagnosis.getDescription().getValue();
        diagnosisDataElements[diagnosisRowCounter][2] = diagnosis.getDescription().getText();
        diagnosisDataElements[diagnosisRowCounter][3] = diagnosis.getEncounter().getLocations().get(0).getText();
        diagnosisDataElements[diagnosisRowCounter][4] = actorConverter.convertToString(diagnosis.getEncounter()
            .getRenderingProvider().getActor(), Locale.US);
        diagnosisDataElements[diagnosisRowCounter][5] = sourceConverter.convertToString(diagnosis.getSource(), Locale.US);
        diagnosisRowCounter++;
      }
    }
  }

  private static void buildRadiology(CareRecord careRecord, AcpPdfDisplayData returnAcpPdfDisplayData) {
    String[][] radiologyDataElements = returnAcpPdfDisplayData.getRadiologyDisplayData();
    Collection<List<Procedure>> radiologies = careRecord.getProcedures().getRadiologies().values();
    int radiologyRowCounter = 0;
    for (List<Procedure> radiologiesOuter : radiologies) {
      for (Procedure radiology : radiologiesOuter) {
        radiologyDataElements[radiologyRowCounter][0] = radiology.getDateTime().getDateTime().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
        radiologyDataElements[radiologyRowCounter][1] = radiology.getDescription().getValue();
        radiologyDataElements[radiologyRowCounter][2] = radiology.getDescription().getText();
        radiologyDataElements[radiologyRowCounter][3] = actorConverter.convertToString(radiology.getOrderingProvider().getActor(), Locale.US);
        radiologyDataElements[radiologyRowCounter][4] = actorConverter.convertToString(radiology.getRenderingProvider().getActor(), Locale.US);
        radiologyDataElements[radiologyRowCounter][5] = sourceConverter.convertToString(radiology.getSource(), Locale.US);
        radiologyRowCounter++;
      }
    }
  }

  private static void buildLabResults(CareRecord careRecord, AcpPdfDisplayData returnAcpPdfDisplayData) {
    String[][] labResultDataElements = returnAcpPdfDisplayData.getLabResultsDisplayData();
    List<Result> results = careRecord.getResults();
    for (Result labResult : results) {
      List<Result.Test> tests = labResult.getTests();
      int testRowCounter = 0;
      for (Result.Test test : tests) {
        labResultDataElements[testRowCounter][0] = labResult.getDateTime().getDateTime().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
        labResultDataElements[testRowCounter][1] = test.getDescription().getText();
        labResultDataElements[testRowCounter][2] = convertFlags(test);
        labResultDataElements[testRowCounter][3] = test.getTestResult().getValue();
        labResultDataElements[testRowCounter][4] = convertNormalResults(test);
        labResultDataElements[testRowCounter][5] = test.getComment().getDescription().getText();
        labResultDataElements[testRowCounter][6] = actorConverter.convertToString(test.getOrderingProvider().getActor(), Locale.US);
        labResultDataElements[testRowCounter][7] = sourceConverter.convertToString(labResult.getSource(), Locale.US);
        testRowCounter++;
      }
    }
  }

  private static String convertNormalResults(Result.Test test) {
    String high = null;
    String low = null;
    String normal = null;
    List<Result.NormalResult> normalResults = test.getNormalResults();
    for (Result.NormalResult result : normalResults) {
      String description = result.getDescription().getText();
      if (description.toLowerCase().endsWith("low")) {
        low = result.getValue();
      } else if (description.toLowerCase().endsWith("high")) {
        high = result.getValue();
      } else {
        normal = result.getValue();
      }
    }
    if (normal == null) {
      return defaultString(low) + "-" + defaultString(high);
    } else {
      return defaultString(normal);
    }
  }

  private static String convertFlags(Result.Test test) {
    List<CodeDescription> flags = test.getFlags();
    StringBuffer flagOutput = new StringBuffer();
    for (CodeDescription flag : flags) {
      flagOutput.append(flag.getText()).append(" ");
    }
    return flagOutput.toString().trim();
  }

  private static void buildPrescriptions(CareRecord careRecord, AcpPdfDisplayData returnAcpPdfDisplayData) {
    String[][] prescriptionDataElements = returnAcpPdfDisplayData.getPrescriptionsDisplayData();
    List<Medication> medications = careRecord.getMedications();
    for (int row = 0; row < medications.size(); row++) {
      Medication prescription = medications.get(row);
      Medication.Fulfillment fulfillment = prescription.getLatestFulfillment();
      prescriptionDataElements[row][0] = fulfillment.getDateFilled().getDateTime().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
      prescriptionDataElements[row][1] = prescription.getDrugName();
      prescriptionDataElements[row][2] = fulfillment.getDosage();
      prescriptionDataElements[row][3] = prescription.getQuantity().toString();
      prescriptionDataElements[row][4] = fulfillment.getRoute();
      prescriptionDataElements[row][5] = prescription.getIsNew().toString();
      prescriptionDataElements[row][6] = fulfillment.getPrescriber().getActor().getPerson().getName().getFullName();
      prescriptionDataElements[row][7] = sourceConverter.convertToString(prescription.getSource(), Locale.US);
    }
  }

  private static void buildMemberArea(final CareRecord careRecord, AcpPdfDisplayData returnAcpPdfDisplayData) {
    String[] memberAreaDataElements = returnAcpPdfDisplayData.getMemberAreaDisplayData();
    String age = String.valueOf(new Period(careRecord.getPatient().getActor().getPerson().getDateOfBirth().getDateTime(), new DateTime()).getYears());
    Actor patient = careRecord.getPatient().getActor();
    Actor primaryPhysician = careRecord.getPrimaryPhysician().getActor();
    memberAreaDataElements[0] = careRecord.getPatient().getActor().getPerson().getName().getFullName();
    memberAreaDataElements[1] = age;
    memberAreaDataElements[2] = patient.getPerson().getGender().getText();
    memberAreaDataElements[3] = patient.getAddress().getLine1();
    memberAreaDataElements[4] = patient.getPerson().getDateOfBirth().getDateTime().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
    memberAreaDataElements[5] = patient.getTelephone().getValue();
    memberAreaDataElements[6] = patient.getAddress().getLine2();
    memberAreaDataElements[7] = primaryPhysician.getPerson().getName().getFullName();
    memberAreaDataElements[8] = primaryPhysician.getSpecialty().getText();
    memberAreaDataElements[9] = primaryPhysician.getTelephone().getValue();
    memberAreaDataElements[10] = careRecord.getFrom().getActor().getOrganization();
  }

  private static int calculateNumberOfDiagnosisRows(Map<String, List<Problem>> problems) {
    int returnNumberOfDiagnosisRows = 0;
    Collection<List<Problem>> problemsOuter = problems.values();
    for (List<Problem> problemsInner : problemsOuter) {
      returnNumberOfDiagnosisRows += problemsInner.size();

    }
    return returnNumberOfDiagnosisRows;
  }

  private static int calculateNumberOfRadiologyRows(Map<String, List<Procedure>> radiologies) {
    int returnNumberOfRadiologyRows = 0;
    Collection<List<Procedure>> radiologiesOuter = radiologies.values();
    for (List<Procedure> radiologiesInner : radiologiesOuter) {
      returnNumberOfRadiologyRows += radiologiesInner.size();

    }
    return returnNumberOfRadiologyRows;
  }

  private static int calculateNumberOfLabResultRows(List<Result> results) {
    int returnNumberOfLabResultRows = 0;
    for (Result labResult : results) {
      returnNumberOfLabResultRows += labResult.getTests().size();
    }
    return returnNumberOfLabResultRows;
  }

}
