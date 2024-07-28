package cops.sync.ad.entity.adinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "active_directory_group")
public class ActiveDirectoryGroup
{
	@Id
	@Column(name = "name", length = 100)
	String name;

	@Column(name = "normalised_dn", length = 500)
	String normalisedDn;

	@Column(name = "dn", length = 500)
	String dn;

	@Column(name = "cn", length = 100)
	String cn;

	@Column(name = "created")
	Date created;

	@Column(name = "updated")
	Date updated;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDn()
	{
		return dn;
	}

	public void setDn(String dn)
	{
		this.dn = dn;
	}

	public String getNormalisedDn()
	{
		return normalisedDn;
	}

	public void setNormalisedDn(String normalisedDn)
	{
		this.normalisedDn = normalisedDn;
	}

	public String getCn()
	{
		return cn;
	}

	public void setCn(String cn)
	{
		this.cn = cn;
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

	public Date getUpdated()
	{
		return updated;
	}

	public void setUpdated(Date updated)
	{
		this.updated = updated;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ActiveDirectoryGroup that = (ActiveDirectoryGroup) o;
		return Objects.equals(name, that.name) && Objects.equals(normalisedDn, that.normalisedDn) &&
		       Objects.equals(dn, that.dn) && Objects.equals(cn, that.cn) && Objects.equals(created, that.created) &&
		       Objects.equals(updated, that.updated);
	}

	@Override
	public int hashCode()
	{
		int result = Objects.hashCode(name);
		result = 31 * result + Objects.hashCode(normalisedDn);
		result = 31 * result + Objects.hashCode(dn);
		result = 31 * result + Objects.hashCode(cn);
		result = 31 * result + Objects.hashCode(created);
		result = 31 * result + Objects.hashCode(updated);
		return result;
	}

	@Override
	public String toString()
	{
		return "ActiveDirectoryGroup{" + "name='" + name + '\'' + ", normalisedDn='" + normalisedDn + '\'' + ", dn='" +
		       dn + '\'' + ", cn='" + cn + '\'' + ", created=" + created + ", updated=" + updated + '}';
	}
}
