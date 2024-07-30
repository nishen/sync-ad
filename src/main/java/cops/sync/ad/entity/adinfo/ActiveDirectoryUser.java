package cops.sync.ad.entity.adinfo;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ads_user")
public class ActiveDirectoryUser
{
	@Id
	@Column(name = "name", length = 100)
	String name;

	@Column(name = "dn", length = 500)
	String dn;

	@Column(name = "normalised_dn", length = 500)
	String normalisedDn;

	@Column(name = "cn", length = 200)
	String cn;

	@Column(name = "sam_account_name", length = 50)
	String samAccountName;

	@Column(name = "display_name", length = 200)
	String displayName;

	@Column(name = "last_logon")
	Date lastLogon;

	@Column(name = "account_expires")
	Date accountExpires;

	@Column(name = "password_last_set")
	Date passwordLastSet;

	@Column(name = "created")
	Date created;

	@Column(name = "updated")
	Date updated;

	@ManyToMany(mappedBy = "members")
	Set<ActiveDirectoryGroup> memberOf = new HashSet<>();

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNormalisedDn()
	{
		return normalisedDn;
	}

	public void setNormalisedDn(String normalisedDn)
	{
		this.normalisedDn = normalisedDn;
	}

	public String getDn()
	{
		return dn;
	}

	public void setDn(String dn)
	{
		this.dn = dn;
	}

	public String getCn()
	{
		return cn;
	}

	public void setCn(String cn)
	{
		this.cn = cn;
	}

	public String getSamAccountName()
	{
		return samAccountName;
	}

	public void setSamAccountName(String samAccountName)
	{
		this.samAccountName = samAccountName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public Date getLastLogon()
	{
		return lastLogon;
	}

	public void setLastLogon(Date lastLogon)
	{
		this.lastLogon = lastLogon;
	}

	public Date getAccountExpires()
	{
		return accountExpires;
	}

	public void setAccountExpires(Date accountExpires)
	{
		this.accountExpires = accountExpires;
	}

	public Date getPasswordLastSet()
	{
		return passwordLastSet;
	}

	public void setPasswordLastSet(Date passwordLastSet)
	{
		this.passwordLastSet = passwordLastSet;
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

	public Set<ActiveDirectoryGroup> getMemberOf()
	{
		return memberOf;
	}

	public void setMemberOf(Set<ActiveDirectoryGroup> memberOf)
	{
		this.memberOf = memberOf;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof ActiveDirectoryUser that))
			return false;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(name);
	}
}
