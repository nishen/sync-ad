package cops.sync.ad.entity.snowflake;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "VW_PATRON_AUTHORISATIONS", schema = "TGT_LIBRARY_APP")
public class SnowflakePatronAuthorisations
{
	@Id
	@Column(name = "SOURCE_IDENTITY_HK")
	private String sourceIdentityHk;

	@Column(name = "ONEID")
	private String oneId;

	@Column(name = "SYSTEM_ID")
	private String systemId;

	@Column(name = "SYSTEM_NAME")
	private String systemName;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt;

	@Column(name = "GRANTED")
	private String granted;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_TIMESTAMP")
	private Date lastUpdatedTimestamp;

	public String getSourceIdentityHk()
	{
		return sourceIdentityHk;
	}

	public void setSourceIdentityHk(String sourceIdentityHk)
	{
		this.sourceIdentityHk = sourceIdentityHk;
	}

	public String getOneId()
	{
		return oneId;
	}

	public void setOneId(String oneId)
	{
		this.oneId = oneId;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getSystemName()
	{
		return systemName;
	}

	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt)
	{
		this.updatedAt = updatedAt;
	}

	public String getGranted()
	{
		return granted;
	}

	public void setGranted(String granted)
	{
		this.granted = granted;
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

		SnowflakePatronAuthorisations that = (SnowflakePatronAuthorisations) o;
		return Objects.equals(sourceIdentityHk, that.sourceIdentityHk) && Objects.equals(oneId, that.oneId) &&
		       Objects.equals(systemId, that.systemId) && Objects.equals(systemName, that.systemName) &&
		       Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) &&
		       Objects.equals(granted, that.granted) && Objects.equals(isDeleted, that.isDeleted) &&
		       Objects.equals(lastUpdatedTimestamp, that.lastUpdatedTimestamp);
	}

	@Override
	public int hashCode()
	{
		int result = Objects.hashCode(sourceIdentityHk);
		result = 31 * result + Objects.hashCode(oneId);
		result = 31 * result + Objects.hashCode(systemId);
		result = 31 * result + Objects.hashCode(systemName);
		result = 31 * result + Objects.hashCode(createdAt);
		result = 31 * result + Objects.hashCode(updatedAt);
		result = 31 * result + Objects.hashCode(granted);
		result = 31 * result + Objects.hashCode(isDeleted);
		result = 31 * result + Objects.hashCode(lastUpdatedTimestamp);
		return result;
	}

	@Override
	public String toString()
	{
		return "SnowflakePatronAuthorisations{" + "sourceIdentityHk='" + sourceIdentityHk + '\'' + ", oneId='" + oneId +
		       '\'' + ", systemId='" + systemId + '\'' + ", systemName='" + systemName + '\'' + ", createdAt=" +
		       createdAt + ", updatedAt=" + updatedAt + ", granted='" + granted + '\'' + ", isDeleted='" + isDeleted +
		       '\'' + ", lastUpdatedTimestamp=" + lastUpdatedTimestamp + '}';
	}
}
