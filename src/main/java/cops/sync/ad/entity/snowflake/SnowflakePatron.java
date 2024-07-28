package cops.sync.ad.entity.snowflake;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VW_PATRON_DETAILS", schema = "TGT_LIBRARY_APP")
@IdClass(PatronKey.class)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "SOURCE_SYSTEM", discriminatorType = DiscriminatorType.STRING)
public abstract class SnowflakePatron
{
	@Id
	@Column(name = "SOURCE_SYSTEM")
	private String sourceSystem;

	@Id
	@Column(name = "IDENTITY_BK")
	private String systemId;

	@Column(name = "SOURCE_IDENTITY_HK")
	private String sourceIdentityHk;

	@Column(name = "ONEID")
	private String oneId;

	@Column(name = "PARTY_ID")
	private BigDecimal partyId;

	@Column(name = "FAMILY_NAME")
	private String familyName;

	@Column(name = "GIVEN_NAME")
	private String givenName;

	@Column(name = "OTHER_NAME")
	private String otherName;

	@Column(name = "PREFERRED_NAME")
	private String preferredName;

	@Column(name = "INITIALS")
	private String initials;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "DECEASED_FLAG")
	private String deceasedFlag;

	@Column(name = "IS_CURRENT")
	private String isCurrent;

	@Column(name = "EMAIL_ADDRESS_1")
	private String emailAddress1;

	@Column(name = "EMAIL_ADDRESS_2")
	private String emailAddress2;

	@Column(name = "EMAIL_ADDRESS_3")
	private String emailAddress3;

	@Column(name = "HOME_PHONE")
	private String homePhone;

	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;

	@Column(name = "WORK_PHONE")
	private String workPhone;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "HOME_ADDRESS_LINE_1")
	private String homeAddressLine1;

	@Column(name = "HOME_ADDRESS_LINE_2")
	private String homeAddressLine2;

	@Column(name = "HOME_ADDRESS_LINE_3")
	private String homeAddressLine3;

	@Column(name = "HOME_SUBURB")
	private String homeSuburb;

	@Column(name = "HOME_STATE")
	private String homeState;

	@Column(name = "HOME_POSTAL_CODE")
	private String homePostalCode;

	@Column(name = "HOME_COUNTRY")
	private String homeCountry;

	@Column(name = "POST_ADDRESS_LINE_1")
	private String postAddressLine1;

	@Column(name = "POST_ADDRESS_LINE_2")
	private String postAddressLine2;

	@Column(name = "POST_ADDRESS_LINE_3")
	private String postAddressLine3;

	@Column(name = "POST_SUBURB")
	private String postSuburb;

	@Column(name = "POST_STATE")
	private String postState;

	@Column(name = "POST_POSTAL_CODE")
	private String postPostalCode;

	@Column(name = "POST_COUNTRY")
	private String postCountry;

	@Column(name = "LAST_UPDATED_TIMESTAMP")
	private Date lastUpdatedTimestampBase;

	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	@Column(name = "OTHER_ID1")
	private String otherId1;

	@Column(name = "OTHER_ID2")
	private String otherId2;

	@Column(name = "OTHER_ID3")
	private String otherId3;

	@Column(name = "BARCODE")
	private String barcode;

	@Column(name = "IS_DELETED")
	private String baseIsDeleted;

	@Transient
	private String acNum;

	public abstract Set<String> getStatCats();

	public String getSourceSystem()
	{
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem)
	{
		this.sourceSystem = sourceSystem;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

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

	public BigDecimal getPartyId()
	{
		return partyId;
	}

	public void setPartyId(BigDecimal partyId)
	{
		this.partyId = partyId;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	public String getGivenName()
	{
		return givenName;
	}

	public void setGivenName(String givenName)
	{
		this.givenName = givenName;
	}

	public String getOtherName()
	{
		return otherName;
	}

	public void setOtherName(String otherName)
	{
		this.otherName = otherName;
	}

	public String getPreferredName()
	{
		return preferredName;
	}

	public void setPreferredName(String preferredName)
	{
		this.preferredName = preferredName;
	}

	public String getInitials()
	{
		return initials;
	}

	public void setInitials(String initials)
	{
		this.initials = initials;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getDeceasedFlag()
	{
		return deceasedFlag;
	}

	public void setDeceasedFlag(String deceasedFlag)
	{
		this.deceasedFlag = deceasedFlag;
	}

	public String getIsCurrent()
	{
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent)
	{
		this.isCurrent = isCurrent;
	}

	public String getEmailAddress1()
	{
		return emailAddress1;
	}

	public void setEmailAddress1(String emailAddress1)
	{
		this.emailAddress1 = emailAddress1;
	}

	public String getEmailAddress2()
	{
		return emailAddress2;
	}

	public void setEmailAddress2(String emailAddress2)
	{
		this.emailAddress2 = emailAddress2;
	}

	public String getEmailAddress3()
	{
		return emailAddress3;
	}

	public void setEmailAddress3(String emailAddress3)
	{
		this.emailAddress3 = emailAddress3;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	public String getWorkPhone()
	{
		return workPhone;
	}

	public void setWorkPhone(String workPhone)
	{
		this.workPhone = workPhone;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getHomeAddressLine1()
	{
		return homeAddressLine1;
	}

	public void setHomeAddressLine1(String homeAddressLine1)
	{
		this.homeAddressLine1 = homeAddressLine1;
	}

	public String getHomeAddressLine2()
	{
		return homeAddressLine2;
	}

	public void setHomeAddressLine2(String homeAddressLine2)
	{
		this.homeAddressLine2 = homeAddressLine2;
	}

	public String getHomeAddressLine3()
	{
		return homeAddressLine3;
	}

	public void setHomeAddressLine3(String homeAddressLine3)
	{
		this.homeAddressLine3 = homeAddressLine3;
	}

	public String getHomeSuburb()
	{
		return homeSuburb;
	}

	public void setHomeSuburb(String homeSuburb)
	{
		this.homeSuburb = homeSuburb;
	}

	public String getHomeState()
	{
		return homeState;
	}

	public void setHomeState(String homeState)
	{
		this.homeState = homeState;
	}

	public String getHomePostalCode()
	{
		return homePostalCode;
	}

	public void setHomePostalCode(String homePostalCode)
	{
		this.homePostalCode = homePostalCode;
	}

	public String getHomeCountry()
	{
		return homeCountry;
	}

	public void setHomeCountry(String homeCountry)
	{
		this.homeCountry = homeCountry;
	}

	public String getPostAddressLine1()
	{
		return postAddressLine1;
	}

	public void setPostAddressLine1(String postAddressLine1)
	{
		this.postAddressLine1 = postAddressLine1;
	}

	public String getPostAddressLine2()
	{
		return postAddressLine2;
	}

	public void setPostAddressLine2(String postAddressLine2)
	{
		this.postAddressLine2 = postAddressLine2;
	}

	public String getPostAddressLine3()
	{
		return postAddressLine3;
	}

	public void setPostAddressLine3(String postAddressLine3)
	{
		this.postAddressLine3 = postAddressLine3;
	}

	public String getPostSuburb()
	{
		return postSuburb;
	}

	public void setPostSuburb(String postSuburb)
	{
		this.postSuburb = postSuburb;
	}

	public String getPostState()
	{
		return postState;
	}

	public void setPostState(String postState)
	{
		this.postState = postState;
	}

	public String getPostPostalCode()
	{
		return postPostalCode;
	}

	public void setPostPostalCode(String postPostalCode)
	{
		this.postPostalCode = postPostalCode;
	}

	public String getPostCountry()
	{
		return postCountry;
	}

	public void setPostCountry(String postCountry)
	{
		this.postCountry = postCountry;
	}

	public Date getLastUpdatedTimestampBase()
	{
		return lastUpdatedTimestampBase;
	}

	public void setLastUpdatedTimestampBase(Date lastUpdatedTimestampBase)
	{
		this.lastUpdatedTimestampBase = lastUpdatedTimestampBase;
	}

	public Date getExpiryDate()
	{
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate)
	{
		this.expiryDate = expiryDate;
	}

	public String getOtherId1()
	{
		return otherId1;
	}

	public void setOtherId1(String otherId1)
	{
		this.otherId1 = otherId1;
	}

	public String getOtherId2()
	{
		return otherId2;
	}

	public void setOtherId2(String otherId2)
	{
		this.otherId2 = otherId2;
	}

	public String getOtherId3()
	{
		return otherId3;
	}

	public void setOtherId3(String otherId3)
	{
		this.otherId3 = otherId3;
	}

	public String getBarcode()
	{
		return barcode;
	}

	public void setBarcode(String barcode)
	{
		this.barcode = barcode;
	}

	public String getBaseIsDeleted()
	{
		return baseIsDeleted;
	}

	public void setBaseIsDeleted(String baseIsDeleted)
	{
		this.baseIsDeleted = baseIsDeleted;
	}

	public String getAcNum()
	{
		return acNum;
	}

	public void setAcNum(String acNum)
	{
		this.acNum = acNum;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SnowflakePatron that = (SnowflakePatron) o;
		return Objects.equals(sourceSystem, that.sourceSystem) && Objects.equals(systemId, that.systemId) &&
		       Objects.equals(sourceIdentityHk, that.sourceIdentityHk) && Objects.equals(oneId, that.oneId) &&
		       Objects.equals(partyId, that.partyId) && Objects.equals(familyName, that.familyName) &&
		       Objects.equals(givenName, that.givenName) && Objects.equals(otherName, that.otherName) &&
		       Objects.equals(preferredName, that.preferredName) && Objects.equals(initials, that.initials) &&
		       Objects.equals(title, that.title) && Objects.equals(birthDate, that.birthDate) &&
		       Objects.equals(gender, that.gender) && Objects.equals(deceasedFlag, that.deceasedFlag) &&
		       Objects.equals(isCurrent, that.isCurrent) && Objects.equals(emailAddress1, that.emailAddress1) &&
		       Objects.equals(emailAddress2, that.emailAddress2) && Objects.equals(emailAddress3, that.emailAddress3) &&
		       Objects.equals(homePhone, that.homePhone) && Objects.equals(mobilePhone, that.mobilePhone) &&
		       Objects.equals(workPhone, that.workPhone) && Objects.equals(fax, that.fax) &&
		       Objects.equals(homeAddressLine1, that.homeAddressLine1) &&
		       Objects.equals(homeAddressLine2, that.homeAddressLine2) &&
		       Objects.equals(homeAddressLine3, that.homeAddressLine3) && Objects.equals(homeSuburb, that.homeSuburb) &&
		       Objects.equals(homeState, that.homeState) && Objects.equals(homePostalCode, that.homePostalCode) &&
		       Objects.equals(homeCountry, that.homeCountry) &&
		       Objects.equals(postAddressLine1, that.postAddressLine1) &&
		       Objects.equals(postAddressLine2, that.postAddressLine2) &&
		       Objects.equals(postAddressLine3, that.postAddressLine3) && Objects.equals(postSuburb, that.postSuburb) &&
		       Objects.equals(postState, that.postState) && Objects.equals(postPostalCode, that.postPostalCode) &&
		       Objects.equals(postCountry, that.postCountry) &&
		       Objects.equals(lastUpdatedTimestampBase, that.lastUpdatedTimestampBase) &&
		       Objects.equals(expiryDate, that.expiryDate) && Objects.equals(otherId1, that.otherId1) &&
		       Objects.equals(otherId2, that.otherId2) && Objects.equals(otherId3, that.otherId3) &&
		       Objects.equals(barcode, that.barcode) && Objects.equals(baseIsDeleted, that.baseIsDeleted) &&
		       Objects.equals(acNum, that.acNum);
	}

	@Override
	public int hashCode()
	{
		int result = Objects.hashCode(sourceSystem);
		result = 31 * result + Objects.hashCode(systemId);
		result = 31 * result + Objects.hashCode(sourceIdentityHk);
		result = 31 * result + Objects.hashCode(oneId);
		result = 31 * result + Objects.hashCode(partyId);
		result = 31 * result + Objects.hashCode(familyName);
		result = 31 * result + Objects.hashCode(givenName);
		result = 31 * result + Objects.hashCode(otherName);
		result = 31 * result + Objects.hashCode(preferredName);
		result = 31 * result + Objects.hashCode(initials);
		result = 31 * result + Objects.hashCode(title);
		result = 31 * result + Objects.hashCode(birthDate);
		result = 31 * result + Objects.hashCode(gender);
		result = 31 * result + Objects.hashCode(deceasedFlag);
		result = 31 * result + Objects.hashCode(isCurrent);
		result = 31 * result + Objects.hashCode(emailAddress1);
		result = 31 * result + Objects.hashCode(emailAddress2);
		result = 31 * result + Objects.hashCode(emailAddress3);
		result = 31 * result + Objects.hashCode(homePhone);
		result = 31 * result + Objects.hashCode(mobilePhone);
		result = 31 * result + Objects.hashCode(workPhone);
		result = 31 * result + Objects.hashCode(fax);
		result = 31 * result + Objects.hashCode(homeAddressLine1);
		result = 31 * result + Objects.hashCode(homeAddressLine2);
		result = 31 * result + Objects.hashCode(homeAddressLine3);
		result = 31 * result + Objects.hashCode(homeSuburb);
		result = 31 * result + Objects.hashCode(homeState);
		result = 31 * result + Objects.hashCode(homePostalCode);
		result = 31 * result + Objects.hashCode(homeCountry);
		result = 31 * result + Objects.hashCode(postAddressLine1);
		result = 31 * result + Objects.hashCode(postAddressLine2);
		result = 31 * result + Objects.hashCode(postAddressLine3);
		result = 31 * result + Objects.hashCode(postSuburb);
		result = 31 * result + Objects.hashCode(postState);
		result = 31 * result + Objects.hashCode(postPostalCode);
		result = 31 * result + Objects.hashCode(postCountry);
		result = 31 * result + Objects.hashCode(lastUpdatedTimestampBase);
		result = 31 * result + Objects.hashCode(expiryDate);
		result = 31 * result + Objects.hashCode(otherId1);
		result = 31 * result + Objects.hashCode(otherId2);
		result = 31 * result + Objects.hashCode(otherId3);
		result = 31 * result + Objects.hashCode(barcode);
		result = 31 * result + Objects.hashCode(baseIsDeleted);
		result = 31 * result + Objects.hashCode(acNum);
		return result;
	}

	@Override
	public String toString()
	{
		return "SnowflakePatron{" + "sourceSystem='" + sourceSystem + '\'' + ", identityBk='" + systemId + '\'' +
		       ", sourceIdentityHk='" + sourceIdentityHk + '\'' + ", oneId='" + oneId + '\'' + ", partyId=" + partyId +
		       ", familyName='" + familyName + '\'' + ", givenName='" + givenName + '\'' + ", otherName='" + otherName +
		       '\'' + ", preferredName='" + preferredName + '\'' + ", initials='" + initials + '\'' + ", title='" +
		       title + '\'' + ", birthDate=" + birthDate + ", gender='" + gender + '\'' + ", deceasedFlag='" +
		       deceasedFlag + '\'' + ", isCurrent='" + isCurrent + '\'' + ", emailAddress1='" + emailAddress1 + '\'' +
		       ", emailAddress2='" + emailAddress2 + '\'' + ", emailAddress3='" + emailAddress3 + '\'' +
		       ", homePhone='" + homePhone + '\'' + ", mobilePhone='" + mobilePhone + '\'' + ", workPhone='" +
		       workPhone + '\'' + ", fax='" + fax + '\'' + ", homeAddressLine1='" + homeAddressLine1 + '\'' +
		       ", homeAddressLine2='" + homeAddressLine2 + '\'' + ", homeAddressLine3='" + homeAddressLine3 + '\'' +
		       ", homeSuburb='" + homeSuburb + '\'' + ", homeState='" + homeState + '\'' + ", homePostalCode='" +
		       homePostalCode + '\'' + ", homeCountry='" + homeCountry + '\'' + ", postAddressLine1='" +
		       postAddressLine1 + '\'' + ", postAddressLine2='" + postAddressLine2 + '\'' + ", postAddressLine3='" +
		       postAddressLine3 + '\'' + ", postSuburb='" + postSuburb + '\'' + ", postState='" + postState + '\'' +
		       ", postPostalCode='" + postPostalCode + '\'' + ", postCountry='" + postCountry + '\'' +
		       ", lastUpdatedTimestampBase=" + lastUpdatedTimestampBase + ", expiryDate=" + expiryDate +
		       ", otherId1='" + otherId1 + '\'' + ", otherId2='" + otherId2 + '\'' + ", otherId3='" + otherId3 + '\'' +
		       ", barcode='" + barcode + '\'' + ", baseIsDeleted='" + baseIsDeleted + '\'' + ", acNum='" + acNum +
		       '\'' + '}';
	}
}
