package cops.sync.ad.entity.adinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ads_user")
public class ActiveDirectoryUser extends ActiveDirectoryObject
{
	@Column(name = "display_name", length = 200)
	String displayName;

	@Column(name = "last_logon_timestamp")
	Date lastLogonTimestamp;

	@Column(name = "account_expires")
	Date accountExpires;

	@Column(name = "password_last_set")
	Date passwordLastSet;

	@Column(name = "user_principal_name", length = 80)
	String userPrincipalName;

	@Column(name = "mail", length = 80)
	String mail;

	@Column(name = "mail_nickname", length = 80)
	String mailNickname;

	@Column(name = "division", length = 80)
	String division;

	@Column(name = "employee_type", length = 80)
	String employeeType;

	@Column(name = "extension_attribute_01", length = 100)
	String extensionAttribute01;

	@Column(name = "extension_attribute_02", length = 100)
	String extensionAttribute02;

	@Column(name = "extension_attribute_03", length = 100)
	String extensionAttribute03;

	@Column(name = "extension_attribute_04", length = 100)
	String extensionAttribute04;

	@Column(name = "extension_attribute_05", length = 100)
	String extensionAttribute05;

	@Column(name = "extension_attribute_06", length = 100)
	String extensionAttribute06;

	@Column(name = "extension_attribute_07", length = 100)
	String extensionAttribute07;

	@Column(name = "extension_attribute_08", length = 100)
	String extensionAttribute08;

	@Column(name = "extension_attribute_09", length = 100)
	String extensionAttribute09;

	@Column(name = "extension_attribute_10", length = 100)
	String extensionAttribute10;

	@Column(name = "extension_attribute_11", length = 100)
	String extensionAttribute11;

	@Column(name = "extension_attribute_12", length = 100)
	String extensionAttribute12;

	@Column(name = "extension_attribute_13", length = 100)
	String extensionAttribute13;

	@Column(name = "extension_attribute_14", length = 100)
	String extensionAttribute14;

	@Column(name = "extension_attribute_15", length = 100)
	String extensionAttribute15;

	@Column(name = "extension_attribute_16", length = 100)
	String extensionAttribute16;

	@Column(name = "flags", length = 20)
	String flags;

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public Date getLastLogonTimestamp()
	{
		return lastLogonTimestamp;
	}

	public void setLastLogonTimestamp(Date lastLogonTimestamp)
	{
		this.lastLogonTimestamp = lastLogonTimestamp;
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

	public String getUserPrincipalName()
	{
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName)
	{
		this.userPrincipalName = userPrincipalName;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getMailNickname()
	{
		return mailNickname;
	}

	public void setMailNickname(String mailNickname)
	{
		this.mailNickname = mailNickname;
	}

	public String getDivision()
	{
		return division;
	}

	public void setDivision(String division)
	{
		this.division = division;
	}

	public String getEmployeeType()
	{
		return employeeType;
	}

	public void setEmployeeType(String employeeType)
	{
		this.employeeType = employeeType;
	}

	public String getExtensionAttribute01()
	{
		return extensionAttribute01;
	}

	public void setExtensionAttribute01(String extensionAttribute01)
	{
		this.extensionAttribute01 = extensionAttribute01;
	}

	public String getExtensionAttribute02()
	{
		return extensionAttribute02;
	}

	public void setExtensionAttribute02(String extensionAttribute02)
	{
		this.extensionAttribute02 = extensionAttribute02;
	}

	public String getExtensionAttribute03()
	{
		return extensionAttribute03;
	}

	public void setExtensionAttribute03(String extensionAttribute03)
	{
		this.extensionAttribute03 = extensionAttribute03;
	}

	public String getExtensionAttribute04()
	{
		return extensionAttribute04;
	}

	public void setExtensionAttribute04(String extensionAttribute04)
	{
		this.extensionAttribute04 = extensionAttribute04;
	}

	public String getExtensionAttribute05()
	{
		return extensionAttribute05;
	}

	public void setExtensionAttribute05(String extensionAttribute05)
	{
		this.extensionAttribute05 = extensionAttribute05;
	}

	public String getExtensionAttribute06()
	{
		return extensionAttribute06;
	}

	public void setExtensionAttribute06(String extensionAttribute06)
	{
		this.extensionAttribute06 = extensionAttribute06;
	}

	public String getExtensionAttribute07()
	{
		return extensionAttribute07;
	}

	public void setExtensionAttribute07(String extensionAttribute07)
	{
		this.extensionAttribute07 = extensionAttribute07;
	}

	public String getExtensionAttribute08()
	{
		return extensionAttribute08;
	}

	public void setExtensionAttribute08(String extensionAttribute08)
	{
		this.extensionAttribute08 = extensionAttribute08;
	}

	public String getExtensionAttribute09()
	{
		return extensionAttribute09;
	}

	public void setExtensionAttribute09(String extensionAttribute09)
	{
		this.extensionAttribute09 = extensionAttribute09;
	}

	public String getExtensionAttribute10()
	{
		return extensionAttribute10;
	}

	public void setExtensionAttribute10(String extensionAttribute10)
	{
		this.extensionAttribute10 = extensionAttribute10;
	}

	public String getExtensionAttribute11()
	{
		return extensionAttribute11;
	}

	public void setExtensionAttribute11(String extensionAttribute11)
	{
		this.extensionAttribute11 = extensionAttribute11;
	}

	public String getExtensionAttribute12()
	{
		return extensionAttribute12;
	}

	public void setExtensionAttribute12(String extensionAttribute12)
	{
		this.extensionAttribute12 = extensionAttribute12;
	}

	public String getExtensionAttribute13()
	{
		return extensionAttribute13;
	}

	public void setExtensionAttribute13(String extensionAttribute13)
	{
		this.extensionAttribute13 = extensionAttribute13;
	}

	public String getExtensionAttribute14()
	{
		return extensionAttribute14;
	}

	public void setExtensionAttribute14(String extensionAttribute14)
	{
		this.extensionAttribute14 = extensionAttribute14;
	}

	public String getExtensionAttribute15()
	{
		return extensionAttribute15;
	}

	public void setExtensionAttribute15(String extensionAttribute15)
	{
		this.extensionAttribute15 = extensionAttribute15;
	}

	public String getExtensionAttribute16()
	{
		return extensionAttribute16;
	}

	public void setExtensionAttribute16(String extensionAttribute16)
	{
		this.extensionAttribute16 = extensionAttribute16;
	}

	public String getFlags()
	{
		return flags;
	}

	public void setFlags(String flags)
	{
		this.flags = flags;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		ActiveDirectoryUser that = (ActiveDirectoryUser) o;
		return Objects.equals(displayName, that.displayName) && Objects.equals(lastLogonTimestamp, that.lastLogonTimestamp) &&
		       Objects.equals(accountExpires, that.accountExpires) &&
		       Objects.equals(passwordLastSet, that.passwordLastSet) &&
		       Objects.equals(userPrincipalName, that.userPrincipalName) && Objects.equals(mail, that.mail) &&
		       Objects.equals(mailNickname, that.mailNickname) && Objects.equals(division, that.division) &&
		       Objects.equals(employeeType, that.employeeType) &&
		       Objects.equals(extensionAttribute01, that.extensionAttribute01) &&
		       Objects.equals(extensionAttribute02, that.extensionAttribute02) &&
		       Objects.equals(extensionAttribute03, that.extensionAttribute03) &&
		       Objects.equals(extensionAttribute04, that.extensionAttribute04) &&
		       Objects.equals(extensionAttribute05, that.extensionAttribute05) &&
		       Objects.equals(extensionAttribute06, that.extensionAttribute06) &&
		       Objects.equals(extensionAttribute07, that.extensionAttribute07) &&
		       Objects.equals(extensionAttribute08, that.extensionAttribute08) &&
		       Objects.equals(extensionAttribute09, that.extensionAttribute09) &&
		       Objects.equals(extensionAttribute10, that.extensionAttribute10) &&
		       Objects.equals(extensionAttribute11, that.extensionAttribute11) &&
		       Objects.equals(extensionAttribute12, that.extensionAttribute12) &&
		       Objects.equals(extensionAttribute13, that.extensionAttribute13) &&
		       Objects.equals(extensionAttribute14, that.extensionAttribute14) &&
		       Objects.equals(extensionAttribute15, that.extensionAttribute15) &&
		       Objects.equals(extensionAttribute16, that.extensionAttribute16) && Objects.equals(flags, that.flags);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), displayName, lastLogonTimestamp, accountExpires, passwordLastSet,
		                    userPrincipalName, mail, mailNickname, division, employeeType, extensionAttribute01,
		                    extensionAttribute02, extensionAttribute03, extensionAttribute04, extensionAttribute05,
		                    extensionAttribute06, extensionAttribute07, extensionAttribute08, extensionAttribute09,
		                    extensionAttribute10, extensionAttribute11, extensionAttribute12, extensionAttribute13,
		                    extensionAttribute14, extensionAttribute15, extensionAttribute16, flags);
	}

	@Override
	public String toString()
	{
		return "ActiveDirectoryUser{" + "displayName='" + displayName + '\'' + ", lastLogonTimestamp=" + lastLogonTimestamp +
		       ", accountExpires=" + accountExpires + ", passwordLastSet=" + passwordLastSet + ", userPrincipalName='" +
		       userPrincipalName + '\'' + ", mail='" + mail + '\'' + ", mailNickname='" + mailNickname + '\'' +
		       ", division='" + division + '\'' + ", employeeType='" + employeeType + '\'' +
		       ", extensionAttribute01='" + extensionAttribute01 + '\'' + ", extensionAttribute02='" +
		       extensionAttribute02 + '\'' + ", extensionAttribute03='" + extensionAttribute03 + '\'' +
		       ", extensionAttribute04='" + extensionAttribute04 + '\'' + ", extensionAttribute05='" +
		       extensionAttribute05 + '\'' + ", extensionAttribute06='" + extensionAttribute06 + '\'' +
		       ", extensionAttribute07='" + extensionAttribute07 + '\'' + ", extensionAttribute08='" +
		       extensionAttribute08 + '\'' + ", extensionAttribute09='" + extensionAttribute09 + '\'' +
		       ", extensionAttribute10='" + extensionAttribute10 + '\'' + ", extensionAttribute11='" +
		       extensionAttribute11 + '\'' + ", extensionAttribute12='" + extensionAttribute12 + '\'' +
		       ", extensionAttribute13='" + extensionAttribute13 + '\'' + ", extensionAttribute14='" +
		       extensionAttribute14 + '\'' + ", extensionAttribute15='" + extensionAttribute15 + '\'' +
		       ", extensionAttribute16='" + extensionAttribute16 + '\'' + ", flags='" + flags + '\'' + '}';
	}
}
