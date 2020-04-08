package committee.services;
import java.util.Date;
/**
 * 
 * The class committee holds all the information for the committee's table
 */
public class Committee {

	private int id;
	private String title;
	private int members;
	private CommitteeType type;
	private String commandStruct;
	private String start;
	private String end;

	/**
	 * Constructor that only takes id and title
	 * 
	 * @param myID
	 * @param myTitle
	 */
	public Committee(int myID, String myTitle) {
		setId(myID);
		setTitle(myTitle);
		setMembers(0);
		setType(CommitteeType.Election);
		setCommandStruct(null);
		setStart(null);
		setEnd(null);
	}

	/**
	 * constructor that fills in the entire committee
	 * 
	 * @param myID
	 * @param myTitle
	 * @param myMemberCount
	 * @param myType
	 * @param myCommandStruct
	 * @param myStart
	 * @param myEnd
	 */
	public Committee(int myID, String myTitle, int myMemberCount, CommitteeType myType, String myCommandStruct,
			String myStart, String myEnd) {
		setId(myID);
		setTitle(myTitle);
		setMembers(myMemberCount);
		setType(myType);
		setCommandStruct(myCommandStruct);
		setStart(myStart);
		setEnd(myEnd);
	}
	
	/**
	 * @return the three different committee types
	 */
	public CommitteeType[] getCommitteeTypes() {
		return CommitteeType.values();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(int members) {
		this.members = members;
	}

	public CommitteeType getType() {
		return type;
	}

	public void setType(CommitteeType type) {
		this.type = type;
	}

	public String getCommandStruct() {
		return commandStruct;
	}

	public void setCommandStruct(String commandStruct) {
		this.commandStruct = commandStruct;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
}

/**
 * 
 * Committee is an enumerator that holds the 3 options for committee type
 */
enum CommitteeType {
	Election, Appointment, Position;
}
