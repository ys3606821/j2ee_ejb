package phasebook.friendship;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-17T17:30:30.119+0100")
@StaticMetamodel(Friendship.class)
public class Friendship_ {
	public static volatile SingularAttribute<Friendship, Integer> inviterUserID;
	public static volatile SingularAttribute<Friendship, Integer> inveteeUserID;
	public static volatile SingularAttribute<Friendship, Date> creationDate;
	public static volatile SingularAttribute<Friendship, Date> deletionDate;
	public static volatile SingularAttribute<Friendship, Boolean> accepted;
	public static volatile SingularAttribute<Friendship, Integer> ID;
}
