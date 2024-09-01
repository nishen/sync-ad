package cops.sync.ad.entity.adinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ads_user")
public class ActiveDirectoryUser extends ActiveDirectoryObject
{
	@Column(name = "display_name", length = 200)
	String displayName;

	@Column(name = "last_logon")
	Date lastLogon;

	@Column(name = "account_expires")
	Date accountExpires;

	@Column(name = "password_last_set")
	Date passwordLastSet;

	@ManyToMany(mappedBy = "members")
	Set<ActiveDirectoryGroup> memberOf = new HashSet<>();

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

	public Set<ActiveDirectoryGroup> getMemberOf()
	{
		return memberOf;
	}

	public void setMemberOf(Set<ActiveDirectoryGroup> memberOf)
	{
		this.memberOf = memberOf;
	}

	@Override
	public String toString()
	{
		return "ActiveDirectoryUser{" + "displayName='" + displayName + '\'' + ", lastLogon=" + lastLogon +
		       ", accountExpires=" + accountExpires + ", passwordLastSet=" + passwordLastSet + ", memberOf=" +
		       memberOf + ", samAccountName='" + samAccountName + '\'' + ", name='" + name + '\'' + ", dn='" + dn +
		       '\'' + ", normalisedDn='" + normalisedDn + '\'' + ", cn='" + cn + '\'' + ", created=" + created +
		       ", updated=" + updated + '}';
	}
}
