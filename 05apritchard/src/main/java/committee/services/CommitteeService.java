package committee.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CommitteeService {

	private List<Committee> comms = null;
	
	/**
	 * Constructor initializes the array list of committee and runs initialize which adds committees to it
	 */
	public CommitteeService() {
		
		comms = new ArrayList<Committee>();
		
		initialize();
	}
	
	/**
	 * Initializes the service with a few example committees
	 */
	public void initialize() {
		
		addNewCommittee(0, "Example Appointment", 4, CommitteeType.Appointment.toString(), "VHLN", "11/12/2017", "4/3/2018");
		addNewCommittee(1, "Example Election", 3, CommitteeType.Election.toString(), "HSN", "2/4/2019", "");
		addNewCommittee(2, "Example Position", 0, CommitteeType.Position.toString(), "", "", "6/11/2010");

	}
	
	/**
	 * @return the list of committees
	 */
	public List<Committee> listCommittees(){
		
		return comms;
	}
	
	/**
	 * @param id the ID of the committee you want to 'fetch'
	 * @return the committee that you want to 'fetch'
	 */
	public Committee fetchCommittee(int id) {
		
		// searches through the committees
		for(Committee cur : comms) {
			
			// finds the committee with that id
			if(cur.getId() == id) {
				
				// returns the committee
				return cur;
			}
		}
		
		return null;
	}
	
	/**
	 * Adds a new committee to the list
	 * If a committee with that id is already in the list, it updates it
	 * This version reads in a string for type and converts it to CommitteeType
	 * @param id
	 * @param title
	 * @param memberCount
	 * @param type
	 * @param commandStruct
	 * @param start
	 * @param end
	 */
	public void addNewCommittee(int id, String title, int memberCount, String type, String commandStruct, String start, String end) {
		
		CommitteeType myType = CommitteeType.valueOf(type);
		
		// checks to see if this id is already in use
		for(Committee cur : comms) {
			if(cur.getId() == id) {
				
				// runs update committee instead if id is already in use
				updateCommittee(id, title, memberCount, myType, commandStruct, start, end);
				return;
			}
		}
		
		comms.add(new Committee(id, title, memberCount, myType, commandStruct, start, end));
	}
	
	/**
	 * Updates the committee ID with the parameters read in
	 * @param id
	 * @param title
	 * @param memberCount
	 * @param type
	 * @param commandStruct
	 * @param start
	 * @param end
	 */
	public void updateCommittee(int id, String title, int memberCount, CommitteeType type, String commandStruct, String start, String end) {
		
		// searches through comms
		for(Committee cur : comms) {
			
			// finds the id its looking for
			if(cur.getId() == id) {
				// sets all of the variables
				cur.setTitle(title);
				cur.setMembers(memberCount);
				cur.setType(type);
				cur.setCommandStruct(commandStruct);
				cur.setStart(start);
				cur.setEnd(end);
				
				break;
			}
		}
	}
	
	/**
	 * Removes the committee with the id given
	 * @param id
	 */
	public void removeCommittee(int id) {
		
		// looks through every committee
		for(Committee cur: comms) {
			if(cur.getId() == id) {
				
				// removes the current committee
				comms.remove(cur);
				
				break;
			}
		}
	}
}