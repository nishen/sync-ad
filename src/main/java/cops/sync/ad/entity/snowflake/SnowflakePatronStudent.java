package cops.sync.ad.entity.snowflake;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VW_PATRON_DETAILS_STUDENT", schema = "TGT_LIBRARY_APP")
@DiscriminatorValue("AMIS")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SOURCE_SYSTEM", referencedColumnName = "SOURCE_SYSTEM"),
                         @PrimaryKeyJoinColumn(name = "STUDENT_BK", referencedColumnName = "IDENTITY_BK") })
public class SnowflakePatronStudent extends SnowflakePatron
{
	@Column(name = "COURSE_BK")
	private String courseBk;

	@Column(name = "OFFERING_YEAR_BK")
	private String offeringYearBk;

	@Column(name = "CS_AVAIL_YR")
	private Integer csAvailYr;

	@Column(name = "CS_STG_CD")
	private String csStgCd;

	@Column(name = "CS_STTS_CD")
	private String csSttsCd;

	@Column(name = "CS_START_DT")
	private Date csStartDt;

	@Column(name = "CS_LIAB_CAT_CD")
	private String csLiabCatCd;

	@Column(name = "CS_ATTNDC_MODE_CD")
	private String csAttndcModeCd;

	@Column(name = "CS_STUDY_TYPE_CD")
	private String csStudyTypeCd;

	@Column(name = "CS_APP_DATE")
	private Date csAppDate;

	@Column(name = "CS_FOE_CD")
	private String csFoeCd;

	@Column(name = "CS_CR_VAL")
	private BigDecimal csCrVal;

	@Column(name = "CS_STUDY_BASIS_CD")
	private String csStudyBasisCd;

	@Column(name = "CS_LOAD_CAT_CD")
	private String csLoadCatCd;

	@Column(name = "CS_SHORT_TITLE")
	private String csShortTitle;

	@Column(name = "CS_CAT_TYPE_CD")
	private String csCatTypeCd;

	@Column(name = "CS_CAT_TYPE_DESC")
	private String csCatTypeDesc;

	@Column(name = "CS_CAT_LVL_CD")
	private String csCatLvlCd;

	@Column(name = "ORG_UNIT_CD")
	private String orgUnitCd;

	@Column(name = "ORG_UNIT_NM")
	private String orgUnitName;

	@Column(name = "ORG_UNIT_TYPE_CD")
	private String orgUnitTypeCd;

	@Column(name = "ORG_UNIT_SHORT_NM")
	private String orgUnitShortName;

	@Column(name = "COURSE_CODES")
	private String courseCodes;

	@Column(name = "COURSE_TITLES")
	private String courseTitles;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "LAST_UPDATED_TIMESTAMP")
	private Date lastUpdatedTimestamp;

	@Override
	public Set<String> getStatCats()
	{
		return TagProcessor.getStatisticalCategories(this);
	}

	public String getCourseBk()
	{
		return courseBk;
	}

	public void setCourseBk(String courseBk)
	{
		this.courseBk = courseBk;
	}

	public String getOfferingYearBk()
	{
		return offeringYearBk;
	}

	public void setOfferingYearBk(String offeringYearBk)
	{
		this.offeringYearBk = offeringYearBk;
	}

	public Integer getCsAvailYr()
	{
		return csAvailYr;
	}

	public void setCsAvailYr(Integer csAvailYr)
	{
		this.csAvailYr = csAvailYr;
	}

	public String getCsStgCd()
	{
		return csStgCd;
	}

	public void setCsStgCd(String csStgCd)
	{
		this.csStgCd = csStgCd;
	}

	public String getCsSttsCd()
	{
		return csSttsCd;
	}

	public void setCsSttsCd(String csSttsCd)
	{
		this.csSttsCd = csSttsCd;
	}

	public Date getCsStartDt()
	{
		return csStartDt;
	}

	public void setCsStartDt(Date csStartDt)
	{
		this.csStartDt = csStartDt;
	}

	public String getCsLiabCatCd()
	{
		return csLiabCatCd;
	}

	public void setCsLiabCatCd(String csLiabCatCd)
	{
		this.csLiabCatCd = csLiabCatCd;
	}

	public String getCsAttndcModeCd()
	{
		return csAttndcModeCd;
	}

	public void setCsAttndcModeCd(String csAttndcModeCd)
	{
		this.csAttndcModeCd = csAttndcModeCd;
	}

	public String getCsStudyTypeCd()
	{
		return csStudyTypeCd;
	}

	public void setCsStudyTypeCd(String csStudyTypeCd)
	{
		this.csStudyTypeCd = csStudyTypeCd;
	}

	public Date getCsAppDate()
	{
		return csAppDate;
	}

	public void setCsAppDate(Date csAppDate)
	{
		this.csAppDate = csAppDate;
	}

	public String getCsFoeCd()
	{
		return csFoeCd;
	}

	public void setCsFoeCd(String csFoeCd)
	{
		this.csFoeCd = csFoeCd;
	}

	public BigDecimal getCsCrVal()
	{
		return csCrVal;
	}

	public void setCsCrVal(BigDecimal csCrVal)
	{
		this.csCrVal = csCrVal;
	}

	public String getCsStudyBasisCd()
	{
		return csStudyBasisCd;
	}

	public void setCsStudyBasisCd(String csStudyBasisCd)
	{
		this.csStudyBasisCd = csStudyBasisCd;
	}

	public String getCsLoadCatCd()
	{
		return csLoadCatCd;
	}

	public void setCsLoadCatCd(String csLoadCatCd)
	{
		this.csLoadCatCd = csLoadCatCd;
	}

	public String getCsShortTitle()
	{
		return csShortTitle;
	}

	public void setCsShortTitle(String csShortTitle)
	{
		this.csShortTitle = csShortTitle;
	}

	public String getCsCatTypeCd()
	{
		return csCatTypeCd;
	}

	public void setCsCatTypeCd(String csCatTypeCd)
	{
		this.csCatTypeCd = csCatTypeCd;
	}

	public String getCsCatTypeDesc()
	{
		return csCatTypeDesc;
	}

	public void setCsCatTypeDesc(String csCatTypeDesc)
	{
		this.csCatTypeDesc = csCatTypeDesc;
	}

	public String getCsCatLvlCd()
	{
		return csCatLvlCd;
	}

	public void setCsCatLvlCd(String csCatLvlCd)
	{
		this.csCatLvlCd = csCatLvlCd;
	}

	public String getOrgUnitCd()
	{
		return orgUnitCd;
	}

	public void setOrgUnitCd(String orgUnitCd)
	{
		this.orgUnitCd = orgUnitCd;
	}

	public String getOrgUnitName()
	{
		return orgUnitName;
	}

	public void setOrgUnitName(String orgUnitName)
	{
		this.orgUnitName = orgUnitName;
	}

	public String getOrgUnitTypeCd()
	{
		return orgUnitTypeCd;
	}

	public void setOrgUnitTypeCd(String orgUnitTypeCd)
	{
		this.orgUnitTypeCd = orgUnitTypeCd;
	}

	public String getOrgUnitShortName()
	{
		return orgUnitShortName;
	}

	public void setOrgUnitShortName(String orgUnitShortName)
	{
		this.orgUnitShortName = orgUnitShortName;
	}

	public String getCourseCodes()
	{
		return courseCodes;
	}

	public void setCourseCodes(String courseCodes)
	{
		this.courseCodes = courseCodes;
	}

	public String getCourseTitles()
	{
		return courseTitles;
	}

	public void setCourseTitles(String courseTitles)
	{
		this.courseTitles = courseTitles;
	}

	public String getIsDeleted()
	{
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted)
	{
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdatedTimestamp()
	{
		return lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp)
	{
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		SnowflakePatronStudent that = (SnowflakePatronStudent) o;
		return Objects.equals(courseBk, that.courseBk) && Objects.equals(offeringYearBk, that.offeringYearBk) &&
		       Objects.equals(csAvailYr, that.csAvailYr) && Objects.equals(csStgCd, that.csStgCd) &&
		       Objects.equals(csSttsCd, that.csSttsCd) && Objects.equals(csStartDt, that.csStartDt) &&
		       Objects.equals(csLiabCatCd, that.csLiabCatCd) && Objects.equals(csAttndcModeCd, that.csAttndcModeCd) &&
		       Objects.equals(csStudyTypeCd, that.csStudyTypeCd) && Objects.equals(csAppDate, that.csAppDate) &&
		       Objects.equals(csFoeCd, that.csFoeCd) && Objects.equals(csCrVal, that.csCrVal) &&
		       Objects.equals(csStudyBasisCd, that.csStudyBasisCd) && Objects.equals(csLoadCatCd, that.csLoadCatCd) &&
		       Objects.equals(csShortTitle, that.csShortTitle) && Objects.equals(csCatTypeCd, that.csCatTypeCd) &&
		       Objects.equals(csCatTypeDesc, that.csCatTypeDesc) && Objects.equals(csCatLvlCd, that.csCatLvlCd) &&
		       Objects.equals(orgUnitCd, that.orgUnitCd) && Objects.equals(orgUnitName, that.orgUnitName) &&
		       Objects.equals(orgUnitTypeCd, that.orgUnitTypeCd) &&
		       Objects.equals(orgUnitShortName, that.orgUnitShortName) &&
		       Objects.equals(courseCodes, that.courseCodes) && Objects.equals(courseTitles, that.courseTitles) &&
		       Objects.equals(isDeleted, that.isDeleted) &&
		       Objects.equals(lastUpdatedTimestamp, that.lastUpdatedTimestamp);
	}

	@Override
	public int hashCode()
	{
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(courseBk);
		result = 31 * result + Objects.hashCode(offeringYearBk);
		result = 31 * result + Objects.hashCode(csAvailYr);
		result = 31 * result + Objects.hashCode(csStgCd);
		result = 31 * result + Objects.hashCode(csSttsCd);
		result = 31 * result + Objects.hashCode(csStartDt);
		result = 31 * result + Objects.hashCode(csLiabCatCd);
		result = 31 * result + Objects.hashCode(csAttndcModeCd);
		result = 31 * result + Objects.hashCode(csStudyTypeCd);
		result = 31 * result + Objects.hashCode(csAppDate);
		result = 31 * result + Objects.hashCode(csFoeCd);
		result = 31 * result + Objects.hashCode(csCrVal);
		result = 31 * result + Objects.hashCode(csStudyBasisCd);
		result = 31 * result + Objects.hashCode(csLoadCatCd);
		result = 31 * result + Objects.hashCode(csShortTitle);
		result = 31 * result + Objects.hashCode(csCatTypeCd);
		result = 31 * result + Objects.hashCode(csCatTypeDesc);
		result = 31 * result + Objects.hashCode(csCatLvlCd);
		result = 31 * result + Objects.hashCode(orgUnitCd);
		result = 31 * result + Objects.hashCode(orgUnitName);
		result = 31 * result + Objects.hashCode(orgUnitTypeCd);
		result = 31 * result + Objects.hashCode(orgUnitShortName);
		result = 31 * result + Objects.hashCode(courseCodes);
		result = 31 * result + Objects.hashCode(courseTitles);
		result = 31 * result + Objects.hashCode(isDeleted);
		result = 31 * result + Objects.hashCode(lastUpdatedTimestamp);
		return result;
	}

	@Override
	public String toString()
	{
		return "SnowflakePatronStudent{" + "courseBk='" + courseBk + '\'' + ", offeringYearBk='" + offeringYearBk +
		       '\'' + ", csAvailYr=" + csAvailYr + ", csStgCd='" + csStgCd + '\'' + ", csSttsCd='" + csSttsCd + '\'' +
		       ", csStartDt=" + csStartDt + ", csLiabCatCd='" + csLiabCatCd + '\'' + ", csAttndcModeCd='" +
		       csAttndcModeCd + '\'' + ", csStudyTypeCd='" + csStudyTypeCd + '\'' + ", csAppDate=" + csAppDate +
		       ", csFoeCd='" + csFoeCd + '\'' + ", csCrVal=" + csCrVal + ", csStudyBasisCd='" + csStudyBasisCd + '\'' +
		       ", csLoadCatCd='" + csLoadCatCd + '\'' + ", csShortTitle='" + csShortTitle + '\'' + ", csCatTypeCd='" +
		       csCatTypeCd + '\'' + ", csCatTypeDesc='" + csCatTypeDesc + '\'' + ", csCatLvlCd='" + csCatLvlCd + '\'' +
		       ", orgUnitCd='" + orgUnitCd + '\'' + ", orgUnitName='" + orgUnitName + '\'' + ", orgUnitTypeCd='" +
		       orgUnitTypeCd + '\'' + ", orgUnitShortName='" + orgUnitShortName + '\'' + ", courseCodes='" +
		       courseCodes + '\'' + ", courseTitles='" + courseTitles + '\'' + ", isDeleted='" + isDeleted + '\'' +
		       ", lastUpdatedTimestamp=" + lastUpdatedTimestamp + "} " + super.toString();
	}
}
