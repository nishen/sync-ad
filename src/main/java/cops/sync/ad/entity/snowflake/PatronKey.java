package cops.sync.ad.entity.snowflake;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class PatronKey implements Serializable
{
	@Serial
	private static final long serialVersionUID = -6049500787443916409L;

	String sourceSystem;

	String systemId;

	public PatronKey()
	{
		super();
	}

	public PatronKey(String sourceSystem, String systemId)
	{
		this.sourceSystem = sourceSystem;
		this.systemId = systemId;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PatronKey patronKey = (PatronKey) o;
		return Objects.equals(sourceSystem, patronKey.sourceSystem) && Objects.equals(systemId, patronKey.systemId);
	}

	@Override
	public int hashCode()
	{
		int result = Objects.hashCode(sourceSystem);
		result = 31 * result + Objects.hashCode(systemId);
		return result;
	}

	@Override
	public String toString()
	{
		return "PatronKey{" + "sourceSystem='" + sourceSystem + '\'' + ", identityBk='" + systemId + '\'' + '}';
	}
}
