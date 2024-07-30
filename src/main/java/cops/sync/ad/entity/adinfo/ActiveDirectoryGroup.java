package cops.sync.ad.entity.adinfo;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ads_group")
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

	@ManyToMany
	@JoinTable(name = "ads_user_group", joinColumns = @JoinColumn(name = "group_name"),
	           inverseJoinColumns = @JoinColumn(name = "user_name"))
	Set<ActiveDirectoryUser> members = new HashSet<>();

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

	public Set<ActiveDirectoryUser> getMembers()
	{
		return members;
	}

	public void setMembers(Set<ActiveDirectoryUser> members)
	{
		this.members = members;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof ActiveDirectoryGroup that))
			return false;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(name);
	}
}
