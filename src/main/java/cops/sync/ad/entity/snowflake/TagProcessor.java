package cops.sync.ad.entity.snowflake;

import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class TagProcessor
{
	private static final Logger log = Logger.getLogger(TagProcessor.class);

	public static Set<String> getStatisticalCategories(SnowflakePatronStaff p)
	{
		Set<String> statCats = new LinkedHashSet<>();

		statCats.add(assignEntityType(p));
		statCats.add(assignPatronCategory(p));
		statCats.add(assignFaculty(p));
		statCats.add(assignDepartment(p));

		log.tracev("statcats - staff [{0}]: {1}", p.getSourceSystem(), String.join(",", statCats));

		return statCats;
	}

	public static Set<String> getStatisticalCategories(SnowflakePatronStudent p)
	{
		Set<String> statCats = new LinkedHashSet<>();

		statCats.add(assignEntityType(p));
		statCats.add(assignPatronCategory(p));
		statCats.add(assignFaculty(p));
		statCats.add(assignCourseCategory(p));
		statCats.addAll(assignCourseCodes(p));
		statCats.addAll(assignCourseTitles(p));
		statCats.add(assignCourseLoadType(p));
		statCats.add(assignStudyBasis(p));
		statCats.add(assignAttendanceMode(p));

		log.tracev("statcats - student [{0}]: {1}", p.getSourceSystem(), String.join(",", statCats));

		return statCats;
	}

	public static Set<String> getStatisticalCategories(SnowflakePatronSponsored p)
	{
		Set<String> statCats = new LinkedHashSet<>();

		statCats.add(assignEntityType(p));
		statCats.add(assignPatronCategory(p));

		log.tracev("statcats - sponsored [{0}]: {1}", p.getSourceSystem(), String.join(",", statCats));

		return statCats;
	}

	public static Set<String> getStatisticalCategories(SnowflakePatronELC p)
	{
		Set<String> statCats = new LinkedHashSet<>();

		statCats.add(assignEntityType(p));
		statCats.add(assignPatronCategory(p));
		statCats.add(assignFaculty(p));
		statCats.add(assignDepartment(p));

		log.tracev("statcats - elc [{0}]: {1}", p.getSourceSystem(), String.join(",", statCats));

		return statCats;
	}

	private static String assignEntityType(SnowflakePatronStaff p)
	{
		if (p.getEntityId() == null)
			return "Entity_OTHER";

		return switch (p.getEntityId())
		{
			case "ACCES" -> "Entity_ACCES";
			case "CMBF" -> "Entity_CMBF";
			case "MGSM" -> "Entity_MGSM";
			case "MU" -> "Entity_MU";
			case "MUCA" -> "Entity_MUCA";
			case "MUH" -> "LIB".equals(p.getUnionCode()) ? "Entity_MUH_PRIV" : "Entity_MUH";
			case "U@MQ" -> "Entity_U@MQ";
			default -> "Entity_OTHER";
		};
	}

	private static String assignEntityType(SnowflakePatronStudent p)
	{
		return "Entity_STUDENT";
	}

	private static String assignEntityType(SnowflakePatronSponsored p)
	{
		return "Entity_SPONSOR";
	}

	private static String assignEntityType(SnowflakePatronELC p)
	{
		return "Entity_ELC";
	}

	private static String assignPatronCategory(SnowflakePatronStaff p)
	{
		if ("OTHER".equals(p.getPositionType()) && "CLAIM".equals(p.getPayLevel()))
			return "PatronCat_ClaimOneOffPayment";

		return switch (p.getPositionType())
		{
			case "ACHON" -> "PatronCat_AcadHon";
			case "APER" -> "PatronCat_AcadCon";
			case "ATEM" -> "PatronCat_AcadFix";
			case "ACAS" -> "PatronCat_AcadCas";
			case "GPER" -> "PatronCat_ProfCon";
			case "GTEM" -> "PatronCat_ProfFix";
			case "GCAS" -> "PatronCat_ProfCas";
			case "NEP" -> "PatronCat_Contractor";
			case "OPER" -> "PatronCat_OtherCon";
			case "OTEM" -> "PatronCat_OtherFix";
			case "OCAS" -> "PatronCat_OtherCas";
			case "HPER", "HPERA" -> "PatronCat_MUHCon";
			case "HTEM", "HTEMA" -> "PatronCat_MUHFix";
			case "HCAS", "HCASA" -> "PatronCat_MUHCas";
			case "TPER" -> "PatronCat_MUICCon";
			case "TTEM" -> "PatronCat_MUICFix";
			case "TCAS" -> "PatronCat_MUICCas";
			default -> "PatronCat_Other";
		};
	}

	private static String assignPatronCategory(SnowflakePatronStudent p)
	{
		if ("Master of Disability Studies".equals(p.getCsShortTitle()))
			return "PatronCat_RIDBCStudent";

		if ("UG".equals(p.getCsCatLvlCd()) && "178".equals(p.getCsCatTypeCd()))
			return "PatronCat_Undergrad_Research";
		else if ("UG".equals(p.getCsCatLvlCd()))
			return "PatronCat_Undergrad";
		else if ("PG".equals(p.getCsCatLvlCd()))
			return "PatronCat_Postgrad";
		else if ("N".equals(p.getCsCatLvlCd()))
			return "PatronCat_NonAward";

		return "PatronCat_Other";
	}

	private static String assignPatronCategory(SnowflakePatronSponsored p)
	{
		if (p.getPatronStatCat1() != null)
			return p.getPatronStatCat1()
			        .replace('.', '_');

		return "PatronCat_Other";
	}

	private static String assignPatronCategory(SnowflakePatronELC p)
	{
		return "PatronCat_ELC";
	}

	private static String assignFaculty(SnowflakePatronStaff p)
	{
		if (p.getFacultyId() == null)
			return "Faculty_Other";

		return switch (p.getFacultyId())
		{
			case "0" -> "Faculty_MGSM";
			case "1" -> "Faculty_BusinessEconomics";
			case "2" -> "Faculty_Arts";
			case "3" -> "Faculty_HumanSciences";
			case "4" -> "Faculty_Science";
			case "5" -> "Faculty_Medicine";
			case "6" -> "Faculty_MUIC";
			case "8" -> "Faculty_Administration";
			// case "9" -> "Faculty_Other"; // also the default.
			case "A" -> "Faculty_AML";
			case "B" -> "Faculty_MUCA";
			case "C" -> "Faculty_CMBFLtd";
			case "E", "H", "I", "O" -> "Faculty_MUH";
			case "U" -> "Faculty_U@MQ";
			default -> "Faculty_Other";
		};
	}

	private static String assignFaculty(SnowflakePatronStudent p)
	{
		if (p.getOrgUnitCd() == null)
			return "Faculty_Other";

		return switch (p.getOrgUnitCd())
		{
			case "1011" -> "Faculty_BusinessEconomics";
			case "1240" -> "Faculty_MGSM";
			case "2011" -> "Faculty_Arts";
			case "3011" -> "Faculty_HumanSciences";
			case "4011" -> "Faculty_Science";
			case "5011" -> "Faculty_Medicine";
			case "6011" -> "Faculty_MUIC";
			case "6040" -> "Faculty_English";
			case "9011" -> "Faculty_MacUni";
			case "9036" -> "Faculty_OUA";
			default -> "Faculty_Other";
		};
	}

	private static String assignFaculty(SnowflakePatronELC p)
	{
		return "Faculty_ELC";
	}

	private static String assignDepartment(SnowflakePatronStaff p)
	{
		if (p.getDepartmentId() == null)
			return "Dept_Other";

		return switch (p.getDepartmentId())
		{
			case "010" -> "Dept_PublicExecutive";
			case "011" -> "Dept_DirectPrograms";
			case "012" -> "Dept_Research";
			case "015" -> "Dept_ExternalRelations";
			case "023" -> "Dept_NRConfCentre";
			case "034" -> "Dept_CBDExec";
			case "046" -> "Dept_IndirectOperations";
			case "047" -> "Dept_IndirectOther";
			case "101" -> "Dept_BusEconAdmin";
			case "103" -> "Dept_AccCorpGov";
			case "105" -> "Dept_Actuarial";
			case "107" -> "Dept_Mgmt";
			case "108" -> "Dept_Mktg";
			case "109" -> "Dept_BusLaw";
			case "120" -> "Dept_Economics";
			case "122" -> "Dept_AppFinActuarial";
			case "124" -> "Dept_MGSM";
			case "131" -> "Dept_AssocDeanLearningTeaching";
			case "132" -> "Dept_AssocDeanCurricQualAssurance";
			case "133" -> "Dept_AssocDeanResearch";
			case "134" -> "Dept_AssocDeanHDR";
			case "135" -> "Dept_AssocDeanInternational";
			case "136" -> "Dept_AssocDeanCorpEngagement";
			case "137" -> "Dept_AssocDeanPgProgPath";
			case "138" -> "Dept_AssocDeanUgProgPath";
			case "139" -> "Dept_SessionalAcadStaffUnit";
			case "141" -> "Dept_FacultyAdminFBE";
			case "151" -> "Dept_CentresOfResearch";
			case "201" -> "Dept_ArtsAdmin";
			case "203" -> "Dept_AncientHist";
			case "204" -> "Dept_MacSchoolEdu";
			case "205" -> "Dept_Intl";
			case "209" -> "Dept_MMCC";
			case "220" -> "Dept_Law";
			case "224" -> "Dept_Sociology";
			case "226" -> "Dept_Anthropology";
			case "228" -> "Dept_Philosophy";
			case "230" -> "Dept_English";
			case "232" -> "Dept_Indigenous";
			case "234" -> "Dept_MHPI";
			case "236" -> "Dept_PICT";
			case "238" -> "Dept_CSI";
			case "240" -> "Dept_GeographyPlanning";
			case "301" -> "Dept_HumSciAdmin";
			case "303" -> "Dept_Education";
			case "305" -> "Dept_EarlyChildhood";
			case "307" -> "Dept_Linguistics";
			case "309" -> "Dept_Psychology";
			case "320" -> "Dept_CogSci";
			case "322" -> "Dept_AdvMed";
			case "324" -> "Dept_HealthProf";
			case "401" -> "Dept_SciAdmin";
			case "402" -> "Dept_MQAF";
			case "403" -> "Dept_BioSci";
			case "404" -> "Dept_AusAstroOptics";
			case "405" -> "Dept_BrainBehaviourEvo";
			case "407" -> "Dept_Chiropractic";
			case "409" -> "Dept_EnviroGeog";
			case "410" -> "Dept_EnviroSci";
			case "420" -> "Dept_EarthPlanetarySci";
			case "422" -> "Dept_Math";
			case "424" -> "Dept_PhysAstro";
			case "426" -> "Dept_Computing";
			case "428" -> "Dept_Statistics";
			case "430" -> "Dept_ChemBioSci";
			case "432" -> "Dept_Engineering";
			case "501" -> "Dept_MedHealthHumanSciences";
			case "502" -> "Dept_MedicinePrograms";
			case "503" -> "Dept_BiomedSci";
			case "504" -> "Dept_ClinicalMed";
			case "505" -> "Dept_HealthProf";
			case "506" -> "Dept_HealthSysPop";
			case "507" -> "Dept_AIHI";
			case "508" -> "Dept_MUCA";
			case "510" -> "Dept_Linguistics";
			case "530" -> "Dept_CognitiveSciences";
			case "550" -> "Dept_Psychology";
			case "601" -> "Dept_MUIC_Admin";
			case "602" -> "Dept_MUIC_Foundation";
			case "603" -> "Dept_MUIC_Diploma";
			case "604" -> "Dept_MUIC_EngLang";
			case "611" -> "Dept_MUSCC_BusEcon";
			case "612" -> "Dept_MUSCC_Arts";
			case "613" -> "Dept_MUSCC_HumSci";
			case "614" -> "Dept_MUSCC_SciEng";
			case "615" -> "Dept_MUSCC_Medicine";
			case "618" -> "Dept_MUSCC_Admin";
			case "801" -> "Dept_VC";
			case "803" -> "Dept_COO";
			case "804" -> "Dept_StratPlanInfo";
			case "805" -> "Dept_Informatics";
			case "807" -> "Dept_Finance";
			case "809" -> "Dept_Property";
			case "810" -> "Dept_DVCCorpEngAdv";
			case "811" -> "Dept_Marketing";
			case "820" -> "Dept_HR";
			case "822" -> "Dept_Registrar";
			case "824" -> "Dept_International";
			case "826" -> "Dept_DVCProvost";
			case "828" -> "Dept_Library";
			case "829" -> "Dept_ArchivesCollections";
			case "830" -> "Dept_DVCResearch";
			case "840" -> "Dept_DVCStudRegistrar";
			case "850" -> "Dept_VPStrategyDev";
			case "860" -> "Dept_DVCStudRegistrar";
			case "871" -> "Dept_InternationalDirectorGeneral";
			case "872" -> "Dept_MITeams";
			case "873" -> "Dept_GBD_AdEmeaAmerica";
			case "874" -> "Dept_GBD_Asia";
			case "880" -> "Dept_UniversityFoundation";
			case "890" -> "Dept_MqProperty";
			case "891" -> "Dept_MqPropertyDirector";
			case "892" -> "Dept_MqPropertyAssetMgmt";
			case "893" -> "Dept_MqPropertyProjectMgmt";
			case "895" -> "Dept_CapitalWorksPLOffset";
			case "896" -> "Dept_EngineeringWorks";
			case "897" -> "Dept_UpgradeImprovement";
			case "898" -> "Dept_MajorProjects";
			case "899" -> "Dept_OtherCmpProjects";
			case "903" -> "Dept_ExternalAcad";
			case "911" -> "Dept_ControlledEntities";
			case "915" -> "Dept_StudentGroups";
			case "950" -> "Dept_CapitalWorks";
			case "A10" -> "Dept_AdminAccess";
			case "A20" -> "Dept_MarketingAccess";
			case "A30" -> "Dept_CRT";
			case "A40" -> "Dept_ELCGeneral";
			case "A50" -> "Dept_ELCProjects";
			case "A60" -> "Dept_IELTS";
			case "A70" -> "Dept_ILC";
			case "B10" -> "Dept_BoneJoint";
			case "B20" -> "Dept_Cancer";
			case "B30" -> "Dept_CardioResp";
			case "B40" -> "Dept_NeuroSci";
			case "B50" -> "Dept_SurgeryGastro";
			case "B51" -> "Dept_SurgeryGastro2";
			case "B60" -> "Dept_PrimaryCareWellDiag";
			case "B61" -> "Dept_PrimaryCareWellDiag2";
			case "B70" -> "Dept_CritCareAnasthetics";
			case "B80" -> "Dept_ExecMgmt";
			case "C10" -> "Dept_CMBFAcademic";
			case "C20" -> "Dept_CMBFAdmin";
			case "EEX", "EXE" -> "Dept_MUH_Exec";
			case "HAG" -> "Dept_MUH_Agency";
			case "HAN" -> "Dept_MUH_Angiography";
			case "HCC" -> "Dept_MUH_CCU";
			case "HCL" -> "Dept_MUH_Clinics";
			case "HCS" -> "Dept_MUH_AgencyCasuals";
			case "HDO" -> "Dept_MUH_DayOncology";
			case "HED", "HEN" -> "Dept_MUH_Endoscopy";
			case "HIC" -> "Dept_MUH_ICU";
			case "HNA" -> "Dept_MUH_NurseAdm";
			case "HPO" -> "Dept_MUH_PeriOp";
			case "HWD" -> "Dept_MUH_ClinicalWards";
			case "ITS" -> "Dept_MUH_TechSvc";
			case "OCL" -> "Dept_MUH_OClinic";
			case "OFA" -> "Dept_MUH_PropertyBldgMaint";
			case "OFI" -> "Dept_MUH_Finance";
			case "OHI" -> "Dept_MUH_HealthInfo";
			case "OHO" -> "Dept_MUH_HotelSvc";
			case "OHR" -> "Dept_MUH_HR";
			case "OPA" -> "Dept_MUH_PatientAdmin";
			case "OOA" -> "Dept_MUH_OfficeAdmin";
			case "OPR" -> "Dept_MUH_Projects";
			case "OSC" -> "Dept_MUH_ClinicalSvcSupport";
			case "U10" -> "Dept_CampusSvc";
			case "U20" -> "Dept_ChildrensSvc";
			case "U30" -> "Dept_ClubSocEngagement";
			case "U40" -> "Dept_Operations";
			case "U50" -> "Dept_Sport";
			case "U60" -> "Dept_Marketing";
			default -> "Dept_Other";
		};
	}

	private static String assignDepartment(SnowflakePatronELC p)
	{
		return "Dept_ELC";
	}

	private static String assignCourseCategory(SnowflakePatronStudent p)
	{
		if (p.getCsCatTypeCd() == null)
			return "CrsCat_Other";

		return switch (p.getCsCatTypeCd())
		{
			case "100" -> "CrsCat_HigherDr";
			case "110" -> "CrsCat_DrRes";
			case "120" -> "CrsCat_DrCwk";
			case "122" -> "CrsCat_DrCombined";
			case "125" -> "CrsCat_HonoursOfMaster";
			case "130" -> "CrsCat_MastersRes";
			case "132" -> "CrsCat_MastersCombined";
			case "135" -> "CrsCat_Masters";
			case "140", "141" -> "CrsCat_MastersCwk";
			case "142" -> "CrsCat_MastersCwkOUA";
			case "145" -> "CrsCat_MastersExtended";
			case "147" -> "CrsCat_MastersDouble";
			case "148" -> "CrsCat_MastersBachelorsCombined";
			case "150" -> "CrsCat_PGDip";
			case "151" -> "CrsCat_GradDip";
			case "152" -> "CrsCat_PGDipOUA";
			case "155" -> "CrsCat_PGDipExt";
			case "160" -> "CrsCat_PGCert";
			case "161" -> "CrsCat_GradCert";
			case "162" -> "CrsCat_PGCertOUA";
			case "165" -> "CrsCat_PGQual";
			case "170" -> "CrsCat_BHon";
			case "178" -> "CrsCat_MRes";
			case "180" -> "CrsCat_BPassDouble";
			case "182" -> "CrsCat_BachelorsGeneralist";
			case "184" -> "CrsCat_BachelorsSpecialist";
			case "185" -> "CrsCat_BCombined";
			case "186" -> "CrsCat_BachelorsDouble";
			case "188" -> "CrsCat_BachelorsMastersCombined";
			case "190" -> "CrsCat_BPass";
			case "192" -> "CrsCat_BOUA";
			case "195" -> "CrsCat_GradDip";
			case "197" -> "CrsCat_GradCert";
			case "198" -> "CrsCat_GradCertOUA";
			case "200" -> "CrsCat_NonAward";
			case "202" -> "CrsCat_SummerSchool";
			case "203" -> "CrsCat_CombinedExchResNonAward";
			case "204" -> "CrsCat_PGConcRes";
			case "205" -> "CrsCat_UGConcRes";
			case "206" -> "CrsCat_PGXInst";
			case "208" -> "CrsCat_UGXInst";
			case "209" -> "CrsCat_UGXInstOUA";
			case "210" -> "CrsCat_AssociateDegree";
			case "215" -> "CrsCat_EngLangNonAward";
			case "220", "222" -> "CrsCat_UGDip";
			case "225" -> "CrsCat_UGCert";
			case "230" -> "CrsCat_NonAwardOUA";
			case "280" -> "CrsCat_NonAwardGifted";
			case "310" -> "CrsCat_Foundation";
			default -> "CrsCat_Other";
		};
	}

	private static Set<String> assignCourseCodes(SnowflakePatronStudent p)
	{
		if (p.getCourseCodes() == null || "".equals(p.getCourseCodes()))
			return Set.of("CrsCode_Other");

		return Arrays.stream(p.getCourseCodes()
		                      .replace(" ", "_")
		                      .split("\\|"))
		             .map(s -> "CrsCode_" + s)
		             .collect(toSet());
	}

	private static Set<String> assignCourseTitles(SnowflakePatronStudent p)
	{
		if (p.getCourseTitles() == null || "".equals(p.getCourseTitles()))
			return Set.of("CrsTitle_Other");

		return Arrays.stream(p.getCourseTitles()
		                      .replace(" ", "_")
		                      .split("\\|"))
		             .map(s -> "CrsTitle_" + s)
		             .collect(toSet());
	}

	private static String assignCourseLoadType(SnowflakePatronStudent p)
	{
		if (p.getCsLoadCatCd() == null)
			return "Load_Other";

		return switch (p.getCsLoadCatCd())
		{
			case "FT" -> "Load_FullTime";
			case "PT" -> "Load_PartTime";
			default -> "Load_Other";
		};
	}

	private static String assignStudyBasis(SnowflakePatronStudent p)
	{
		if (p.getCsStudyBasisCd() == null)
			return "StudyBasis_Other";

		return switch (p.getCsStudyBasisCd())
		{
			case "$CRDT" -> "StudyBasis_Credit";
			case "$TIME" -> "StudyBasis_Time";
			default -> "StudyBasis_Other";
		};
	}

	private static String assignAttendanceMode(SnowflakePatronStudent p)
	{
		if (p.getCsAttndcModeCd() == null)
			return "Attendance_Other";

		return switch (p.getCsAttndcModeCd())
		{
			case "INT" -> "Attendance_Internal";
			case "EXT" -> "Attendance_External";
			default -> "Attendance_Other";
		};
	}
}
