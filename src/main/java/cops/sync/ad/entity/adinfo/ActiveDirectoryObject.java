package cops.sync.ad.entity.adinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public abstract class ActiveDirectoryObject
{
	@Id
	@Column(name = "sam_account_name", length = 120)
	String samAccountName;

	@Column(name = "name", length = 100)
	String name;

	@Column(name = "dn", length = 500)
	String dn;

	@Column(name = "normalised_dn", length = 500)
	String normalisedDn;

	@Column(name = "cn", length = 200)
	String cn;

	@Column(name = "description", length = 500)
	String description;

	@Column(name = "created")
	Date created;

	@Column(name = "updated")
	Date updated;

	public String getSamAccountName()
	{
		return samAccountName;
	}

	public void setSamAccountName(String samAccountName)
	{
		this.samAccountName = samAccountName;
	}

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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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
		if (o == null || getClass() != o.getClass())
			return false;
		ActiveDirectoryObject that = (ActiveDirectoryObject) o;
		return Objects.equals(samAccountName, that.samAccountName) && Objects.equals(name, that.name) &&
		       Objects.equals(dn, that.dn) && Objects.equals(normalisedDn, that.normalisedDn) &&
		       Objects.equals(cn, that.cn) && Objects.equals(description, that.description) &&
		       Objects.equals(created, that.created) && Objects.equals(updated, that.updated);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(samAccountName, name, dn, normalisedDn, cn, description, created, updated);
	}

	@Override
	public String toString()
	{
		return "ActiveDirectoryObject{" + "samAccountName='" + samAccountName + '\'' + ", name='" + name + '\'' +
		       ", dn='" + dn + '\'' + ", normalisedDn='" + normalisedDn + '\'' + ", cn='" + cn + '\'' +
		       ", description='" + description + '\'' + ", created=" + created + ", updated=" + updated + '}';
	}
}
