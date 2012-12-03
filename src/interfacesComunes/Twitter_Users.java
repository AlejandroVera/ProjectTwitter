package interfacesComunes;

import java.io.Serializable;
import java.util.List;

public interface Twitter_Users extends Serializable{

	List<Long> getFollowerIDs();

}
