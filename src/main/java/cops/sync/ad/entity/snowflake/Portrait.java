package cops.sync.ad.entity.snowflake;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ASP_UNICARD", schema = "TGT_LIBRARY_APP")
public class Portrait
{
	@Id
	@Column(name = "ASP_UNICARD_SK")
	private BigDecimal id;

	@Column(name = "PARTY_ID")
	private BigDecimal partyId;

	@Column(name = "BARCODE")
	private String barcode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "PHOTO_IMAGE")
	private byte[] portrait;

	public BigDecimal getId()
	{
		return id;
	}

	public void setId(BigDecimal id)
	{
		this.id = id;
	}

	public BigDecimal getPartyId()
	{
		return partyId;
	}

	public void setPartyId(BigDecimal partyId)
	{
		this.partyId = partyId;
	}

	public String getBarcode()
	{
		return barcode;
	}

	public void setBarcode(String barcode)
	{
		this.barcode = barcode;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public byte[] getPortrait()
	{
		return portrait;
	}

	public void setPortrait(byte[] portrait)
	{
		this.portrait = portrait;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Portrait portrait1 = (Portrait) o;
		return Objects.equals(id, portrait1.id) && Objects.equals(partyId, portrait1.partyId) &&
		       Objects.equals(barcode, portrait1.barcode) && Objects.equals(updateTime, portrait1.updateTime) &&
		       Arrays.equals(portrait, portrait1.portrait);
	}

	@Override
	public int hashCode()
	{
		int result = Objects.hashCode(id);
		result = 31 * result + Objects.hashCode(partyId);
		result = 31 * result + Objects.hashCode(barcode);
		result = 31 * result + Objects.hashCode(updateTime);
		result = 31 * result + Arrays.hashCode(portrait);
		return result;
	}

	@Override
	public String toString()
	{
		return "Portrait{" + "id=" + id + ", partyId=" + partyId + ", barcode='" + barcode + '\'' + ", updateTime=" +
		       updateTime + ", portrait=" + Arrays.toString(portrait) + '}';
	}
}
