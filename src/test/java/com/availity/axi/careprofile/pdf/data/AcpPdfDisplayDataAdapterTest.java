package com.availity.axi.careprofile.pdf.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.joda.time.Interval;

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
import com.availity.bo.careprofile.ActorReference;
import com.availity.bo.careprofile.CcrDateTime;
import com.availity.bo.careprofile.Product;
import com.availity.bo.careprofile.CareRecordObject;
import com.availity.bo.careprofile.Comment;
import com.availity.bo.careprofile.ProcedureGroup;
import com.availity.bo.careprofile.EncounterGroup;


/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class AcpPdfDisplayDataAdapterTest extends TestCase {

  private static int PRESCRIPTION_ROWS = 0;
  private static final int LAB_ORDERS_ROWS = 0;
  private static int LAB_RESULTS_ROWS = 0;
  private static int RADIOLOGY_ROWS = 0;
  private static int PROVIDER_ROWS = 0;
  private static int DIAGNOSIS_ROWS = 0;
  private static int HOSPITAL_ROWS = 0;
  private static int IMMUNIZATION_ROWS = 0;

  private CareRecord careRecord;

  public void testSingleRecordPositive() throws Exception {

    AcpPdfDisplayData acpPdfDisplayData = null;
    assertNull(acpPdfDisplayData);
    acpPdfDisplayData = AcpPdfDisplayDataAdapter.adapt(careRecord);
    assertNotNull(acpPdfDisplayData);

    memberAreaExercise(acpPdfDisplayData);
    prescriptionsExercise(acpPdfDisplayData);
//    labExercise(acpPdfDisplayData);
    labResultsExercise(acpPdfDisplayData);
    radiologyExercise(acpPdfDisplayData);
    providerExercise(acpPdfDisplayData);
    diagnosisExercise(acpPdfDisplayData);
    hospitalExercise(acpPdfDisplayData);
    immunizationsExercise(acpPdfDisplayData);
  }

  private void providerExercise(AcpPdfDisplayData acpPdfDisplayData) {
    List<Provider> providerIn = new ArrayList(careRecord.getEncounters().getProviders().values());
    String[][] providerOut = acpPdfDisplayData.getProviderDisplayData();
    int providerRowCounter = 0;
    for (Provider provider : providerIn) {
      Actor providerActor = provider.getActorReference().getActor();
      assertEquals(provider.getLatestVisit().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT),
          providerOut[providerRowCounter][0]);
      assertEquals(providerActor.getPerson().getName().getFullName(),
          providerOut[providerRowCounter][1]);
      assertEquals(providerActor.getSpecialty().getText(), providerOut[providerRowCounter][2]);
      assertEquals(providerActor.getTelephone().getValue(), providerOut[providerRowCounter][3]);
      assertEquals(providerActor.getAddress().getCity() + "/" + providerActor.getAddress().getState(), providerOut[providerRowCounter][4]);
      assertEquals(provider.getCount().toString(), providerOut[providerRowCounter][5]);
      providerRowCounter++;
    }
  }

  private void immunizationsExercise(AcpPdfDisplayData acpPdfDisplayData) {
    Collection<List<Procedure>> immunizationsIn = careRecord.getProcedures().getImmunizations().values();
    String[][] immunizationsOut = acpPdfDisplayData.getImmunizationDisplayData();
    for (List<Procedure> immunizationsOuter : immunizationsIn) {
      int immunizationRowCounter = 0;
      for (Procedure immunization : immunizationsOuter) {
        assertEquals(immunization.getDateTime().getRange().getStart().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT), immunizationsOut[immunizationRowCounter][0]);
        assertEquals(immunization.getDescription().getValue(), immunizationsOut[immunizationRowCounter][1]);
        assertEquals(immunization.getDescription().getText(), immunizationsOut[immunizationRowCounter][2]);
        assertTrue(immunizationsOut[immunizationRowCounter][3] != null && Integer.parseInt(immunizationsOut[immunizationRowCounter][3]) > 0);
        assertEquals(immunization.getRenderingProvider().getActor().getPerson().getName().getFullName(), immunizationsOut[immunizationRowCounter][4]);
        assertEquals(immunization.getSource().getActorReference().getActor().getOrganization(), immunizationsOut[immunizationRowCounter][5]);
        immunizationRowCounter++;
      }
    }
  }

  private void hospitalExercise(AcpPdfDisplayData acpPdfDisplayData) {
    List<Encounter> hospitalIn = new ArrayList<Encounter>(careRecord.getEncounters().getHospitalEncounters().values());
    String[][] hospitalOut = acpPdfDisplayData.getHospitalDisplayData();
    int hospitalRowCounter = 0;
    for (Encounter hospital : hospitalIn) {
      assertEquals(hospital.getDateOfService().getRange().getStart().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT),
          hospitalOut[hospitalRowCounter][0]);
      assertEquals(hospital.getDateOfService().getRange().getEnd().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT),
          hospitalOut[hospitalRowCounter][1]);
      assertEquals(hospital.getDescription().getValue(), hospitalOut[hospitalRowCounter][2]);
      assertEquals(hospital.getDescription().getText(), hospitalOut[hospitalRowCounter][3]);
      assertEquals(hospital.getLocations().get(0).getText(), hospitalOut[hospitalRowCounter][4]);
      assertEquals(hospital.getRenderingProvider().getActor().getPerson().getName().getFullName(), hospitalOut[hospitalRowCounter][5]);
      assertEquals(hospital.getSource().getActorReference().getActor().getOrganization(), hospitalOut[hospitalRowCounter][6]);
      hospitalRowCounter++;
    }
  }

  private void diagnosisExercise(AcpPdfDisplayData acpPdfDisplayData) {
    ArrayList<List<Problem>> diagnosisIn = new ArrayList<List<Problem>>(careRecord.getProblems().values());
    String[][] diagnosisOut = acpPdfDisplayData.getDiagnosisDisplayData();
    int diagnosisRowCounter = 0;
    for (List<Problem> problemsOuter : diagnosisIn) {
      for (Problem diagnosis : problemsOuter) {
        assertEquals(diagnosis.getEncounter().getDateOfService().getRange().getStart().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT),
            diagnosisOut[diagnosisRowCounter][0]);
        assertEquals(diagnosis.getDescription().getValue(), diagnosisOut[diagnosisRowCounter][1]);
        assertEquals(diagnosis.getDescription().getText(), diagnosisOut[diagnosisRowCounter][2]);
        assertEquals(diagnosis.getEncounter().getLocations().get(0).getText(), diagnosisOut[diagnosisRowCounter][3]);
        assertEquals(diagnosis.getEncounter().getRenderingProvider().getActor().getPerson().getName().getFullName(),
            diagnosisOut[diagnosisRowCounter][4]);
        assertEquals(diagnosis.getSource().getActorReference().getActor().getOrganization(), diagnosisOut[diagnosisRowCounter][5]);
        diagnosisRowCounter++;
      }
    }
  }

  private void radiologyExercise(AcpPdfDisplayData acpPdfDisplayData) {
    Collection<List<Procedure>> radiologiesIn = careRecord.getProcedures().getRadiologies().values();
    String[][] radiologiesOut = acpPdfDisplayData.getRadiologyDisplayData();
    for (List<Procedure> radiologiesOuter : radiologiesIn) {
      int radiologyRowCounter = 0;
      for (Procedure radiology : radiologiesOuter) {
        assertEquals(radiology.getDateTime().getDateTime().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT), radiologiesOut[radiologyRowCounter][0]);
        assertEquals(radiology.getDescription().getValue(), radiologiesOut[radiologyRowCounter][1]);
        assertEquals(radiology.getDescription().getText(), radiologiesOut[radiologyRowCounter][2]);
        assertEquals(radiology.getOrderingProvider().getActor().getPerson().getName().getFullName(), radiologiesOut[radiologyRowCounter][3]);
        assertEquals(radiology.getRenderingProvider().getActor().getPerson().getName().getFullName(), radiologiesOut[radiologyRowCounter][4]);
        assertEquals(radiology.getSource().getActorReference().getActor().getOrganization(), radiologiesOut[radiologyRowCounter][5]);
        radiologyRowCounter++;
      }
    }
  }

  private void labResultsExercise(AcpPdfDisplayData acpPdfDisplayData) {
    List<Result> labResultsIn = careRecord.getResults();
    String[][] labResultsOut = acpPdfDisplayData.getLabResultsDisplayData();

    assertEquals(LAB_RESULTS_ROWS, calculateNumberOfLabResultRows(labResultsIn));

    for (Result lab : labResultsIn) {
      List<Result.Test> tests = lab.getTests();
      int testRowCounter = 0;
      for (Result.Test test : tests) {
        String collected = lab.getDateTime().getDateTime().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT);
        assertEquals(collected, labResultsOut[testRowCounter][0]);
        assertEquals(test.getDescription().getText(), labResultsOut[testRowCounter][1]);

        StringBuffer flagsSb = new StringBuffer();
        for (CodeDescription codeDescription : test.getFlags()) {
          flagsSb.append(codeDescription.getText()).append(" ");
        }
        assertEquals(flagsSb.toString().trim(), labResultsOut[testRowCounter][2]);
        assertEquals(test.getTestResult().getValue(), labResultsOut[testRowCounter][3]);
        assertNotNull(labResultsOut[testRowCounter][4]);
        assertTrue(labResultsOut[testRowCounter][4].length() > 0);
        assertEquals(test.getComment().getDescription().getText(), labResultsOut[testRowCounter][5]);
        assertEquals(test.getOrderingProvider().getActor().getPerson().getName().getFullName(), labResultsOut[testRowCounter][6]);
        assertEquals(lab.getSource().getDescription().getText(), labResultsOut[testRowCounter][7]);
        testRowCounter++;
      }
    }
  }

  private void prescriptionsExercise(AcpPdfDisplayData acpPdfDisplayData) {
    List<Medication> prescriptionsIn = careRecord.getMedications();
    String[][] prescriptionsOut = acpPdfDisplayData.getPrescriptionsDisplayData();

    assertEquals(PRESCRIPTION_ROWS, prescriptionsIn.size());

    for (int i = 0; i < PRESCRIPTION_ROWS; i++) {
      Medication prescription = prescriptionsIn.get(i);
      Medication.Fulfillment fulfillment = prescription.getLatestFulfillment();
      assertEquals(fulfillment.getDateFilled().getDateTime().toString(ConverterUtil.DateTimeConverter.SHORT_FORMAT), prescriptionsOut[i][0]);
      assertEquals(prescription.getDrugName(), prescriptionsOut[i][1]);
      assertEquals(fulfillment.getDosage(), prescriptionsOut[i][2]);
      assertEquals(prescription.getQuantity().toString(), prescriptionsOut[i][3]);
      assertEquals(fulfillment.getRoute(), prescriptionsOut[i][4]);
      assertEquals(prescription.getIsNew().toString(), prescriptionsOut[i][5]);
      assertEquals(fulfillment.getPrescriber().getActor().getPerson().getName().getFullName(), prescriptionsOut[i][6]);
      assertEquals(prescription.getSource().getActorReference().getActor().getOrganization(), prescriptionsOut[i][7]);
    }
  }

  private void memberAreaExercise(AcpPdfDisplayData acpPdfDisplayData) {
    String[] memberData = acpPdfDisplayData.getMemberAreaDisplayData();
    assertEquals("John M Smith", memberData[0]);
    assertTrue(Integer.parseInt(memberData[1]) > 0);
    assertEquals("M", memberData[2]);
    assertEquals("line1", memberData[3]);
    assertEquals("2006-10-01", memberData[4]);
    assertEquals("111-111-1111", memberData[5]);
    assertEquals("line2", memberData[6]);
    assertEquals("Jan Smith", memberData[7]);
    assertEquals("Family Medicine", memberData[8]);
    assertEquals("111-111-1111", memberData[9]);
    assertEquals("ORG", memberData[10]);
  }

  public void testSingleCareRecordInitializeLabResultsPositive() throws Exception {
    AcpPdfDisplayData acpPdfDisplayData = null;
    assertNull(acpPdfDisplayData);
    acpPdfDisplayData = AcpPdfDisplayDataAdapter.initializePdfDisplayData(careRecord);
    assertNotNull(acpPdfDisplayData);
    assertEquals(PRESCRIPTION_ROWS, acpPdfDisplayData.getPrescriptionsDisplayData().length);
    assertEquals(LAB_RESULTS_ROWS, acpPdfDisplayData.getLabResultsDisplayData().length);
    assertEquals(LAB_ORDERS_ROWS, acpPdfDisplayData.getLabDisplayData().length);
    assertEquals(RADIOLOGY_ROWS, acpPdfDisplayData.getRadiologyDisplayData().length);
    assertEquals(PROVIDER_ROWS, acpPdfDisplayData.getProviderDisplayData().length);
    assertEquals(DIAGNOSIS_ROWS, acpPdfDisplayData.getDiagnosisDisplayData().length);
    assertEquals(HOSPITAL_ROWS, acpPdfDisplayData.getHospitalDisplayData().length);
    assertEquals(IMMUNIZATION_ROWS, acpPdfDisplayData.getImmunizationDisplayData().length);
  }

  public void testNoDataForSections() throws Exception {
    assertTrue(true);
  }

  protected void setUp() throws Exception {
    // NOTE: as of 4/28 Lab does not appear to have been accounted for in the domain
    careRecord = createCareRecord();
    PRESCRIPTION_ROWS = careRecord.getMedications().size();
    LAB_RESULTS_ROWS = calculateNumberOfLabResultRows(careRecord.getResults());
    RADIOLOGY_ROWS = calculateNumberOfRadiologyRows(careRecord.getProcedures().getRadiologies());
    PROVIDER_ROWS = careRecord.getEncounters().getProviders().size();
    DIAGNOSIS_ROWS = calculateNumberOfDiagnosisRows(careRecord.getProblems());
    HOSPITAL_ROWS = careRecord.getEncounters().getHospitalEncounters().size();
    IMMUNIZATION_ROWS = careRecord.getProcedures().getImmunizations().size();
  }

  private int calculateNumberOfDiagnosisRows(Map<String, List<Problem>> problems) {
    int returnNumberOfRows = 0;
    Collection<List<Problem>> diagnosisOuterouter = problems.values();
    for (List inner : diagnosisOuterouter) {
      returnNumberOfRows += inner.size();
    }
    return returnNumberOfRows;
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

  public static CareRecord createCareRecord() {
    CareRecord careRecord = new CareRecord();
    ActorReference patient = new ActorReference();
    patient.setActor(new Actor());
    patient.getActor().setPerson(new Actor.Person());
    patient.getActor().getPerson().setName(new Actor.Name("John", "M", "Smith"));

    patient.getActor().getPerson().setDateOfBirth(new CcrDateTime());
    patient.getActor().getPerson().getDateOfBirth().setDateTime(new DateTime(2006, 10, 1, 0, 0, 0, 0));
    patient.getActor().getPerson().setGender(new CodeDescription("M"));
    patient.getActor().setTelephone(new Actor.Communication(new CodeDescription("Office"),
      "111-111-1111"));
    patient.getActor().setAddress(new Actor.Address(new CodeDescription("Office"),
      "line1", "line2", "city", "state", "postal"));
    careRecord.setPatient(patient);

    ActorReference physician = new ActorReference();
    physician.setActor(new Actor());
    physician.getActor().setPerson(new Actor.Person());
    physician.getActor().getPerson().setName(new Actor.Name("Jan", "Smith"));
    physician.getActor().setTelephone(new Actor.Communication(new CodeDescription("Office"),
      "111-111-1111"));
    physician.getActor().setSpecialty(new CodeDescription("Family Medicine"));
    physician.getActor().setAddress(new Actor.Address(new CodeDescription(), "", "", "Jacksonville", "FL", ""));
    careRecord.setPrimaryPhysician(physician);

    ActorReference payer = new ActorReference();
    payer.setActor(new Actor());
    payer.getActor().setOrganization("ORG");
    careRecord.setFrom(payer);

    Medication prescription = new Medication();
    prescription.setLatestFulfillment(new Medication.Fulfillment(new CcrDateTime(),
      new ActorReference(), "route", "dosage"));
    prescription.getLatestFulfillment().getDateFilled().setDateTime(
      new DateTime(2008, 10, 1, 0, 0, 0, 0));
    prescription.setProduct(new Product());
    prescription.getProduct().setProductName(new CodeDescription("product"));
    prescription.setQuantity(30);
    prescription.setIsNew(Boolean.TRUE);
    // add prescriber
    Actor.Person prescriberPerson = new Actor.Person();
    prescriberPerson.setName(new Actor.Name("Jeff", "M", "Fisher"));
    Actor actor = new Actor();
    actor.setPerson(prescriberPerson);
    actor.setOrganization("BCBSF");
    ActorReference actorReferenceJeffFisher = new ActorReference();
    actorReferenceJeffFisher.setActor(actor);
    prescription.getLatestFulfillment().setPrescriber(actorReferenceJeffFisher);
    // add source
    CareRecordObject.Source sourceWithDescription = new CareRecordObject.Source();
    sourceWithDescription.setDescription(new CodeDescription("BCBSF"));
    prescription.setSource(sourceWithDescription);

    List<Medication> prescriptions = Arrays.asList(prescription);
    careRecord.setMedications(prescriptions);

    Result result = new Result();
    result.setDateTime(new CcrDateTime());
    result.getDateTime().setDateTime(new DateTime(2009, 01, 01, 0, 0, 0, 0));

    Result.Test test1 = new Result.Test();
    test1.setDescription(new CodeDescription("test one"));
    test1.setFlags(Arrays.asList(new CodeDescription("L"), new CodeDescription("H")));
    test1.setTestResult(new Result.TestResult("result"));
    test1.setNormalResults(Arrays.asList(new Result.NormalResult(), new Result.NormalResult()));
    test1.getNormalResults().get(0).setDescription(new CodeDescription("Normal Result High"));
    test1.getNormalResults().get(0).setValue("60");
    test1.getNormalResults().get(1).setDescription(new CodeDescription("Normal Result Low"));
    test1.getNormalResults().get(1).setValue("50");
    test1.setTestClass("class");
    test1.setRenderingProvider(careRecord.getPrimaryPhysician());
    test1.setOrderingProvider(careRecord.getPrimaryPhysician());
    test1.setComment(new Comment());
    test1.getComment().setDescription(new CodeDescription("comment"));
    test1.setSource(new CareRecordObject.Source());
    test1.getSource().setActorReference(payer);

    Result.Test test2 = new Result.Test();
    test2.setDescription(new CodeDescription("test two"));
    test2.setFlags(Arrays.asList(new CodeDescription("L"), new CodeDescription("H")));
    test2.setTestResult(new Result.TestResult("result"));
    test2.setNormalResults(Arrays.asList(new Result.NormalResult(), new Result.NormalResult()));
    test2.getNormalResults().get(0).setDescription(new CodeDescription("Normal Result High"));
    test2.getNormalResults().get(0).setValue("70");
    test2.getNormalResults().get(1).setDescription(new CodeDescription("Normal Result Low"));
    test2.getNormalResults().get(1).setValue("10");
    test2.setTestClass("class");
    test2.setRenderingProvider(careRecord.getPrimaryPhysician());
    test2.setOrderingProvider(careRecord.getPrimaryPhysician());
    test2.setComment(new Comment());
    test2.getComment().setDescription(new CodeDescription("comment"));
    test2.setSource(new CareRecordObject.Source());
    test2.getSource().setActorReference(payer);

    result.setSource(sourceWithDescription);

    result.setTests(Arrays.asList(test1, test2));

    List<Result> results = Arrays.asList(result);
    careRecord.setResults(results);

    Procedure rad1 = new Procedure();
    rad1.setDateTime(new CcrDateTime());
    rad1.getDateTime().setRange(new Interval(new DateTime(2009, 01, 01, 0, 0, 0, 0),
      new DateTime(2009, 01, 15, 0, 0, 0, 0)));
    rad1.getDateTime().setDateTime(new DateTime(2009, 01, 01, 0, 0, 0, 0));
    rad1.setDescription(new CodeDescription("code", "CPT"));
    rad1.getDescription().setText("proc1");
    rad1.setOrderingProvider(careRecord.getPrimaryPhysician());
    rad1.setRenderingProvider(careRecord.getPrimaryPhysician());
    rad1.setPosition(Arrays.asList(new CodeDescription("mod", "a", "b", "c")));
    rad1.setSource(sourceWithDescription);

    Procedure rad2 = new Procedure();
    rad2.setDateTime(new CcrDateTime());
    rad2.getDateTime().setRange(new Interval(new DateTime(2009, 01, 01, 0, 0, 0, 0),
        new DateTime(2009, 01, 15, 0, 0, 0, 0)));
    rad2.getDateTime().setDateTime(new DateTime(2009, 02, 02, 0, 0, 0, 0));
    rad2.setDescription(new CodeDescription("code", "CPT"));
    rad2.getDescription().setText("proc2");
    rad2.setOrderingProvider(careRecord.getPrimaryPhysician());
    rad2.setRenderingProvider(careRecord.getPrimaryPhysician());
    rad2.setPosition(Arrays.asList(new CodeDescription("mod", "a", "b", "c")));
    rad2.setSource(sourceWithDescription);

    ProcedureGroup group = new ProcedureGroup();
    group.setRadiologies(new HashMap<String, List<Procedure>>());
    group.getRadiologies().put("", Arrays.asList(rad1, rad2));
    careRecord.setProcedures(group);

    Provider provider = new Provider();
    provider.setActorReference(careRecord.getPrimaryPhysician());
    provider.setCount(1);
    provider.setLatestVisit(new DateTime(2009, 01, 01, 0, 0, 0, 0));

    EncounterGroup egroup = new EncounterGroup();
    egroup.setProviders(new HashMap<String, Provider>());
    egroup.getProviders().put(provider.getActorReference().getActorID(), provider);

    Encounter encounter = new Encounter();
    encounter.setDateOfService(new CcrDateTime());
    encounter.getDateOfService().setRange(new Interval(
      new DateTime(2009, 01, 01, 0, 0, 0, 0),
      new DateTime(2009, 01, 15, 0, 0, 0, 0)));
    encounter.setType(Encounter.Type.Institutional);
    encounter.setLocations(Arrays.asList(new CodeDescription("pos")));
    encounter.setRenderingProvider(careRecord.getPrimaryPhysician());
    encounter.setProcedures(Arrays.asList(rad1));

    Problem problem = new Problem();
    problem.setDescription(new CodeDescription("code", "icd", "ver", "problem"));
    problem.setSource(new CareRecordObject.Source());
    problem.getSource().setActorReference(payer);
    problem.setEncounter(encounter);
    problem.setProcedure(Arrays.asList(rad1));
    Actor actorWithOrg = new Actor();
    actorWithOrg.setPerson(null);
    actorWithOrg.setOrganization("BCBSF");
    ActorReference actorReferenceWithActorOrg = new ActorReference();
    actorReferenceWithActorOrg.setActor(actorWithOrg);
    sourceWithDescription.setActorReference(actorReferenceWithActorOrg);
    problem.setSource(sourceWithDescription);

    encounter.setDiagnosis(Arrays.asList(problem));
    encounter.setSource(new CareRecordObject.Source());
    encounter.getSource().setActorReference(payer);
    encounter.setDescription(new CodeDescription("code", "icd", "ver", "problem"));

    Map<String, List<Problem>> problems = new HashMap<String, List<Problem>>();
    problems.put(encounter.getCcrDataObjectId(), Arrays.asList(problem, problem));

    careRecord.setProblems(problems);

    egroup.setHospitalEncounters(new HashMap<String, Encounter>());
    egroup.getHospitalEncounters().put("", encounter);

    careRecord.setEncounters(egroup);

    Procedure imm = new Procedure();
    imm.setDateTime(new CcrDateTime());
    imm.getDateTime().setRange(new Interval(new DateTime(2009, 01, 01, 0, 0, 0, 0),
      new DateTime(2009, 01, 15, 0, 0, 0, 0)));
    imm.setDescription(new CodeDescription("code", "CPT"));
    imm.getDescription().setText("proc");
    imm.setOrderingProvider(careRecord.getPrimaryPhysician());
    imm.setRenderingProvider(careRecord.getPrimaryPhysician());
    imm.setPosition(Arrays.asList(new CodeDescription("mod", "a", "b", "c")));
    imm.setSource(new CareRecordObject.Source());
    actorReferenceJeffFisher.getActor().getPerson().setDateOfBirth(new CcrDateTime());
    actorReferenceJeffFisher.getActor().getPerson().getDateOfBirth().setDateTime(new DateTime(1972, 01, 01, 0, 0, 0, 0));
    imm.setSource(sourceWithDescription);
    imm.setPatient(patient);

    careRecord.getProcedures().setImmunizations(
      new HashMap<String, List<Procedure>>());
    careRecord.getProcedures().getImmunizations().put(
      "", Arrays.asList(imm));
    return careRecord;
  }

}