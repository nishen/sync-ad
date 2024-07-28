package cops.sync.ad.entity.snowflake;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VW_PATRON_DETAILS_ELC", schema = "TGT_LIBRARY_APP")
@DiscriminatorValue("ELC")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SOURCE_SYSTEM", referencedColumnName = "SOURCE_SYSTEM"),
                         @PrimaryKeyJoinColumn(name = "IDENTITY_BK", referencedColumnName = "IDENTITY_BK") })
public class SnowflakePatronELC extends SnowflakePatron
{
	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Column(name = "LAST_UPDATED_TIMESTAMP")
	private Date lastUpdatedTimestamp;

	@Override
	public Set<String> getStatCats()
	{
		return TagProcessor.getStatisticalCategories(this);
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

		SnowflakePatronELC that = (SnowflakePatronELC) o;
		return Objects.equals(isDeleted, that.isDeleted) &&
		       Objects.equals(lastUpdatedTimestamp, that.lastUpdatedTimestamp);
	}

	@Override
	public int hashCode()
	{
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(isDeleted);
		result = 31 * result + Objects.hashCode(lastUpdatedTimestamp);
		return result;
	}

	@Override
	public String toString()
	{
		return "SnowflakePatronELC{" + "isDeleted='" + isDeleted + '\'' + ", lastUpdatedTimestamp=" +
		       lastUpdatedTimestamp + "} " + super.toString();
	}
}