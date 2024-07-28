package cops.sync.ad.entity.snowflake;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VW_PATRON_DETAILS_SPONSOR", schema = "TGT_LIBRARY_APP")
@DiscriminatorValue("SPONSOR")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SOURCE_SYSTEM", referencedColumnName = "SOURCE_SYSTEM"),
                         @PrimaryKeyJoinColumn(name = "ONEID", referencedColumnName = "IDENTITY_BK") })
public class SnowflakePatronSponsored extends SnowflakePatron
{
	@Column(name="SOURCE_SYSTEM", insertable=false, updatable=false)
	private String sourceSystem;

	@Column(name="ONEID", insertable=false, updatable=false)
	private String systemId;

	@Column(name = "GRADUATE_TYPE")
	private String graduateType;

	@Column(name = "OTHER_ID")
	private String otherId;

	@Column(name = "PATRON_GROUP")
	private String patronGroup;

	@Column(name = "PATRON_STAT_CAT")
	private String patronStatCat1;

	@Column(name = "PATRON_STAT_CAT2")
	private String patronStatCat2;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "LAST_UPDATED_TIMESTAMP")
	private Date lastUpdatedTimestamp;

	@Override
	public Set<String> getStatCats()
	{
		return TagProcessor.getStatisticalCategories(this);
	}

	@Override
	public String getSourceSystem()
	{
		return sourceSystem;
	}

	@Override
	public void setSourceSystem(String sourceSystem)
	{
		this.sourceSystem = sourceSystem;
	}

	@Override
	public String getSystemId()
	{
		return systemId;
	}

	@Override
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getGraduateType()
	{
		return graduateType;
	}

	public void setGraduateType(String graduateType)
	{
		this.graduateType = graduateType;
	}

	public String getOtherId()
	{
		return otherId;
	}

	public void setOtherId(String otherId)
	{
		this.otherId = otherId;
	}

	public String getPatronGroup()
	{
		return patronGroup;
	}

	public void setPatronGroup(String patronGroup)
	{
		this.patronGroup = patronGroup;
	}

	public String getPatronStatCat1()
	{
		return patronStatCat1;
	}

	public void setPatronStatCat1(String patronStatCat1)
	{
		this.patronStatCat1 = patronStatCat1;
	}

	public String getPatronStatCat2()
	{
		return patronStatCat2;
	}

	public void setPatronStatCat2(String patronStatCat2)
	{
		this.patronStatCat2 = patronStatCat2;
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

		SnowflakePatronSponsored that = (SnowflakePatronSponsored) o;
		return Objects.equals(sourceSystem, that.sourceSystem) && Objects.equals(systemId, that.systemId) &&
		       Objects.equals(graduateType, that.graduateType) && Objects.equals(otherId, that.otherId) &&
		       Objects.equals(patronGroup, that.patronGroup) && Objects.equals(patronStatCat1, that.patronStatCat1) &&
		       Objects.equals(patronStatCat2, that.patronStatCat2) && Objects.equals(isDeleted, that.isDeleted) &&
		       Objects.equals(lastUpdatedTimestamp, that.lastUpdatedTimestamp);
	}

	@Override
	public int hashCode()
	{
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(sourceSystem);
		result = 31 * result + Objects.hashCode(systemId);
		result = 31 * result + Objects.hashCode(graduateType);
		result = 31 * result + Objects.hashCode(otherId);
		result = 31 * result + Objects.hashCode(patronGroup);
		result = 31 * result + Objects.hashCode(patronStatCat1);
		result = 31 * result + Objects.hashCode(patronStatCat2);
		result = 31 * result + Objects.hashCode(isDeleted);
		result = 31 * result + Objects.hashCode(lastUpdatedTimestamp);
		return result;
	}

	@Override
	public String toString()
	{
		return "SnowflakePatronSponsored{" + "sourceSystem='" + sourceSystem + '\'' + ", systemId='" + systemId + '\'' +
		       ", graduateType='" + graduateType + '\'' + ", otherId='" + otherId + '\'' + ", patronGroup='" +
		       patronGroup + '\'' + ", patronStatCat1='" + patronStatCat1 + '\'' + ", patronStatCat2='" +
		       patronStatCat2 + '\'' + ", isDeleted='" + isDeleted + '\'' + ", lastUpdatedTimestamp=" +
		       lastUpdatedTimestamp + "} " + super.toString();
	}
}