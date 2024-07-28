package cops.sync.ad.entity.snowflake;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VW_PATRON_DETAILS_STAFF", schema = "TGT_LIBRARY_APP")
@DiscriminatorValue("HRIS")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SOURCE_SYSTEM", referencedColumnName = "SOURCE_SYSTEM"),
                         @PrimaryKeyJoinColumn(name = "STAFFID", referencedColumnName = "IDENTITY_BK") })
public class SnowflakePatronStaff extends SnowflakePatron
{
	@Column(name = "ROLETYPE")
	private String roleType;

	@Column(name = "POSITIONNUMBER")
	private String positionNumber;

	@Column(name = "POSITIONTITLE")
	private String positionTitle;

	@Column(name = "JOBNUMBER")
	private String jobNumber;

	@Column(name = "DISCIPLINEID")
	private String disciplineId;

	@Column(name = "DISCIPLINENAME")
	private String disciplineName;

	@Column(name = "DEPARTMENTID")
	private String departmentId;

	@Column(name = "DEPARTMENTNAME")
	private String departmentName;

	@Column(name = "FACULTYID")
	private String facultyId;

	@Column(name = "FACULTYNAME")
	private String facultyName;

	@Column(name = "ENTITYID")
	private String entityId;

	@Column(name = "ENTITYNAME")
	private String entityName;

	@Column(name = "STARTDATE")
	private Date startDate;

	@Column(name = "ENDDATE")
	private Date endDate;

	@Column(name = "OCCUPANCY")
	private String occupancy;

	@Column(name = "OCCUPANCYPRIORITY")
	private String occupancyPriority;

	@Column(name = "POSITIONTYPE")
	private String positionType;

	@Column(name = "PAYAWARD")
	private String payAward;

	@Column(name = "PAYLEVEL")
	private String payLevel;

	@Column(name = "PAYSTEP")
	private String payStep;

	@Column(name = "AVGHOURS")
	private BigDecimal avgHours;

	@Column(name = "PERCENTFTE")
	private BigDecimal percentFTE;

	@Column(name = "MANAGERPOSITION")
	private String managerPosition;

	@Column(name = "WORK_FUN")
	private String workFun;

	@Column(name = "WORK_FUNCTION_DESC")
	private String workFunDesc;

	@Column(name = "UNION_CODE")
	private String unionCode;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "LAST_UPDATED_TIMESTAMP")
	private Date lastUpdatedTimestamp;

	@Override
	public Set<String> getStatCats()
	{
		return TagProcessor.getStatisticalCategories(this);
	}

	public String getRoleType()
	{
		return roleType;
	}

	public void setRoleType(String roleType)
	{
		this.roleType = roleType;
	}

	public String getPositionNumber()
	{
		return positionNumber;
	}

	public void setPositionNumber(String positionNumber)
	{
		this.positionNumber = positionNumber;
	}

	public String getPositionTitle()
	{
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle)
	{
		this.positionTitle = positionTitle;
	}

	public String getJobNumber()
	{
		return jobNumber;
	}

	public void setJobNumber(String jobNumber)
	{
		this.jobNumber = jobNumber;
	}

	public String getDisciplineId()
	{
		return disciplineId;
	}

	public void setDisciplineId(String disciplineId)
	{
		this.disciplineId = disciplineId;
	}

	public String getDisciplineName()
	{
		return disciplineName;
	}

	public void setDisciplineName(String disciplineName)
	{
		this.disciplineName = disciplineName;
	}

	public String getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public String getFacultyId()
	{
		return facultyId;
	}

	public void setFacultyId(String facultyId)
	{
		this.facultyId = facultyId;
	}

	public String getFacultyName()
	{
		return facultyName;
	}

	public void setFacultyName(String facultyName)
	{
		this.facultyName = facultyName;
	}

	public String getEntityId()
	{
		return entityId;
	}

	public void setEntityId(String entityId)
	{
		this.entityId = entityId;
	}

	public String getEntityName()
	{
		return entityName;
	}

	public void setEntityName(String entityName)
	{
		this.entityName = entityName;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getOccupancy()
	{
		return occupancy;
	}

	public void setOccupancy(String occupancy)
	{
		this.occupancy = occupancy;
	}

	public String getOccupancyPriority()
	{
		return occupancyPriority;
	}

	public void setOccupancyPriority(String occupancyPriority)
	{
		this.occupancyPriority = occupancyPriority;
	}

	public String getPositionType()
	{
		return positionType;
	}

	public void setPositionType(String positionType)
	{
		this.positionType = positionType;
	}

	public String getPayAward()
	{
		return payAward;
	}

	public void setPayAward(String payAward)
	{
		this.payAward = payAward;
	}

	public String getPayLevel()
	{
		return payLevel;
	}

	public void setPayLevel(String payLevel)
	{
		this.payLevel = payLevel;
	}

	public String getPayStep()
	{
		return payStep;
	}

	public void setPayStep(String payStep)
	{
		this.payStep = payStep;
	}

	public BigDecimal getAvgHours()
	{
		return avgHours;
	}

	public void setAvgHours(BigDecimal avgHours)
	{
		this.avgHours = avgHours;
	}

	public BigDecimal getPercentFTE()
	{
		return percentFTE;
	}

	public void setPercentFTE(BigDecimal percentFTE)
	{
		this.percentFTE = percentFTE;
	}

	public String getManagerPosition()
	{
		return managerPosition;
	}

	public void setManagerPosition(String managerPosition)
	{
		this.managerPosition = managerPosition;
	}

	public String getWorkFun()
	{
		return workFun;
	}

	public void setWorkFun(String workFun)
	{
		this.workFun = workFun;
	}

	public String getWorkFunDesc()
	{
		return workFunDesc;
	}

	public void setWorkFunDesc(String workFunDesc)
	{
		this.workFunDesc = workFunDesc;
	}

	public String getUnionCode()
	{
		return unionCode;
	}

	public void setUnionCode(String unionCode)
	{
		this.unionCode = unionCode;
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

		SnowflakePatronStaff that = (SnowflakePatronStaff) o;
		return Objects.equals(roleType, that.roleType) && Objects.equals(positionNumber, that.positionNumber) &&
		       Objects.equals(positionTitle, that.positionTitle) && Objects.equals(jobNumber, that.jobNumber) &&
		       Objects.equals(disciplineId, that.disciplineId) && Objects.equals(disciplineName, that.disciplineName) &&
		       Objects.equals(departmentId, that.departmentId) && Objects.equals(departmentName, that.departmentName) &&
		       Objects.equals(facultyId, that.facultyId) && Objects.equals(facultyName, that.facultyName) &&
		       Objects.equals(entityId, that.entityId) && Objects.equals(entityName, that.entityName) &&
		       Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) &&
		       Objects.equals(occupancy, that.occupancy) && Objects.equals(occupancyPriority, that.occupancyPriority) &&
		       Objects.equals(positionType, that.positionType) && Objects.equals(payAward, that.payAward) &&
		       Objects.equals(payLevel, that.payLevel) && Objects.equals(payStep, that.payStep) &&
		       Objects.equals(avgHours, that.avgHours) && Objects.equals(percentFTE, that.percentFTE) &&
		       Objects.equals(managerPosition, that.managerPosition) && Objects.equals(workFun, that.workFun) &&
		       Objects.equals(workFunDesc, that.workFunDesc) && Objects.equals(unionCode, that.unionCode) &&
		       Objects.equals(isDeleted, that.isDeleted) &&
		       Objects.equals(lastUpdatedTimestamp, that.lastUpdatedTimestamp);
	}

	@Override
	public int hashCode()
	{
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(roleType);
		result = 31 * result + Objects.hashCode(positionNumber);
		result = 31 * result + Objects.hashCode(positionTitle);
		result = 31 * result + Objects.hashCode(jobNumber);
		result = 31 * result + Objects.hashCode(disciplineId);
		result = 31 * result + Objects.hashCode(disciplineName);
		result = 31 * result + Objects.hashCode(departmentId);
		result = 31 * result + Objects.hashCode(departmentName);
		result = 31 * result + Objects.hashCode(facultyId);
		result = 31 * result + Objects.hashCode(facultyName);
		result = 31 * result + Objects.hashCode(entityId);
		result = 31 * result + Objects.hashCode(entityName);
		result = 31 * result + Objects.hashCode(startDate);
		result = 31 * result + Objects.hashCode(endDate);
		result = 31 * result + Objects.hashCode(occupancy);
		result = 31 * result + Objects.hashCode(occupancyPriority);
		result = 31 * result + Objects.hashCode(positionType);
		result = 31 * result + Objects.hashCode(payAward);
		result = 31 * result + Objects.hashCode(payLevel);
		result = 31 * result + Objects.hashCode(payStep);
		result = 31 * result + Objects.hashCode(avgHours);
		result = 31 * result + Objects.hashCode(percentFTE);
		result = 31 * result + Objects.hashCode(managerPosition);
		result = 31 * result + Objects.hashCode(workFun);
		result = 31 * result + Objects.hashCode(workFunDesc);
		result = 31 * result + Objects.hashCode(unionCode);
		result = 31 * result + Objects.hashCode(isDeleted);
		result = 31 * result + Objects.hashCode(lastUpdatedTimestamp);
		return result;
	}

	@Override
	public String toString()
	{
		return "SnowflakePatronStaff{" + "roleType='" + roleType + '\'' + ", positionNumber='" + positionNumber + '\'' +
		       ", positionTitle='" + positionTitle + '\'' + ", jobNumber='" + jobNumber + '\'' + ", disciplineId='" +
		       disciplineId + '\'' + ", disciplineName='" + disciplineName + '\'' + ", departmentId='" + departmentId +
		       '\'' + ", departmentName='" + departmentName + '\'' + ", facultyId='" + facultyId + '\'' +
		       ", facultyName='" + facultyName + '\'' + ", entityId='" + entityId + '\'' + ", entityName='" +
		       entityName + '\'' + ", startDate=" + startDate + ", endDate=" + endDate + ", occupancy='" + occupancy +
		       '\'' + ", occupancyPriority='" + occupancyPriority + '\'' + ", positionType='" + positionType + '\'' +
		       ", payAward='" + payAward + '\'' + ", payLevel='" + payLevel + '\'' + ", payStep='" + payStep + '\'' +
		       ", avgHours=" + avgHours + ", percentFTE=" + percentFTE + ", managerPosition='" + managerPosition +
		       '\'' + ", workFun='" + workFun + '\'' + ", workFunDesc='" + workFunDesc + '\'' + ", unionCode='" +
		       unionCode + '\'' + ", isDeleted='" + isDeleted + '\'' + ", lastUpdatedTimestamp=" +
		       lastUpdatedTimestamp + "} " + super.toString();
	}
}
