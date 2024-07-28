package cops.sync.ad.entity.snowflake;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ASP_UNICARD", schema = "TGT_LIBRARY_APP")
public class UnicardCard
{
	@Id
	@Column(name = "PARTY_ID")
	private BigInteger partyId;

	@Column(name = "ONE_ID1")
	private String oneId1;

	@Column(name = "ONE_ID2")
	private String oneId2;

	@Column(name = "BARCODE")
	private String barcode;

	@Column(name = "AC_NUM")
	private String acNum;

	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	public BigInteger getPartyId()
	{
		return partyId;
	}

	public void setPartyId(BigInteger partyId)
	{
		this.partyId = partyId;
	}

	public String getOneId1()
	{
		return oneId1;
	}

	public void setOneId1(String oneId1)
	{
		this.oneId1 = oneId1;
	}

	public String getOneId2()
	{
		return oneId2;
	}

	public void setOneId2(String oneId2)
	{
		this.oneId2 = oneId2;
	}

	public String getBarcode()
	{
		return barcode;
	}

	public void setBarcode(String barcode)
	{
		this.barcode = barcode;
	}

	public String getAcNum()
	{
		return acNum;
	}

	public void setAcNum(String acNum)
	{
		this.acNum = acNum;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UnicardCard that = (UnicardCard) o;
		return Objects.equals(partyId, that.partyId) && Objects.equals(oneId1, that.oneId1) &&
		       Objects.equals(oneId2, that.oneId2) && Objects.equals(barcode, that.barcode) &&
		       Objects.equals(acNum, that.acNum) && Objects.equals(updateTime, that.updateTime);
	}

	@Override
	public int hashCode()
	{
		int result = Objects.hashCode(partyId);
		result = 31 * result + Objects.hashCode(oneId1);
		result = 31 * result + Objects.hashCode(oneId2);
		result = 31 * result + Objects.hashCode(barcode);
		result = 31 * result + Objects.hashCode(acNum);
		result = 31 * result + Objects.hashCode(updateTime);
		return result;
	}

	@Override
	public String toString()
	{
		return "UnicardCard{" + "partyId=" + partyId + ", oneId1='" + oneId1 + '\'' + ", oneId2='" + oneId2 + '\'' +
		       ", barcode='" + barcode + '\'' + ", acNum='" + acNum + '\'' + ", updateTime=" + updateTime + '}';
	}
}
