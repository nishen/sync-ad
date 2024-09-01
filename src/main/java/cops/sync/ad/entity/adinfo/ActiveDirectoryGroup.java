package cops.sync.ad.entity.adinfo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ads_group")
public class ActiveDirectoryGroup extends ActiveDirectoryObject
{
	@ManyToMany
	@JoinTable(name = "ads_user_group", joinColumns = @JoinColumn(name = "group_name"),
	           inverseJoinColumns = @JoinColumn(name = "user_name"))
	Set<ActiveDirectoryUser> members = new HashSet<>();

	public Set<ActiveDirectoryUser> getMembers()
	{
		return members;
	}

	public void setMembers(Set<ActiveDirectoryUser> members)
	{
		this.members = members;
	}
}
