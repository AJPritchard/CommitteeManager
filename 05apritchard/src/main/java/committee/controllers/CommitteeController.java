/**
 * @author AJ Pritchard
 */
package committee.controllers;

import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import committee.services.CommitteeService;

@Controller
public class CommitteeController {

	@Autowired
	private CommitteeService comService;

	/**
	 * The base page
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/CommitteeManager")
	public ModelAndView base(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("base");

		return mav;
	}

	/**
	 * returns the list of committees to the AJAX and which allows it to give it to
	 * the ajax_table.html which can build the table
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/ajax/buildTable")
	public ModelAndView table(HttpServletRequest request, HttpServletResponse response) {

		try {

			response.setContentType("text/html"); // ajax responses will be html snippets

			ModelAndView mav = new ModelAndView("ajax_table");

			// adds the committee list as an object
			mav.addObject("committees", comService.listCommittees());

			return mav;

		} catch (Exception e) {

			ModelAndView mav = new ModelAndView("ajax_error");

			return mav;
		}
	}

	/**
	 * Add committee retrieves all the data from the AJAX form, and creates a new
	 * committee in comService with it. Then it returns the list of committee
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/ajax/addCommittee")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("AJAX ADD");
		try {

			ModelAndView mav = new ModelAndView("ajax_table");

			// reads in every piece of data
			int id = Integer.parseInt(request.getParameter("id")); // Holds the id parameter
			String title = (request.getParameter("title")); // Holds the text parameter
			int members = Integer.parseInt(request.getParameter("members")); // Holds the members parameter
			String type = (request.getParameter("type")); // Holds the text parameter
			String command = (request.getParameter("command")); // Holds the text parameter
			String startTemp = (request.getParameter("start")); // Holds the text parameter
			String endTemp = (request.getParameter("end")); // Holds the text parameter

			String start = ""; // sets the dates to empty
			String end = "";

			// tries to parse the Date object from them
			try {
				start = new SimpleDateFormat("dd/MM/yyyy").parse(startTemp).toString();
				end = new SimpleDateFormat("dd/MM/yyyy").parse(endTemp).toString();
			} catch (Exception e) {
				System.err.println("Could not parse date from date given");
			}

			// adds the new committee
			comService.addNewCommittee(id, title, members, type, command, start, end);

			mav.addObject("committees", comService.listCommittees());

			return mav;

		} catch (Exception e) {

			ModelAndView mav = new ModelAndView("ajax_error");

			return mav;
		}
	}

	/**
	 * Remove committee retrieves the id of the committee to be removed from its
	 * AJAX and then removes it. Finally it returns the list of committees
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/ajax/removeCommittee")
	public ModelAndView remove(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("AJAX REMOVE");
		try {

			ModelAndView mav = new ModelAndView("ajax_table");

			int id = Integer.parseInt(request.getParameter("id")); // Holds the id parameter

			// double checks that the committee exists and then removes it
			if (comService.fetchCommittee(id) != null) {
				comService.removeCommittee(id);
			} else {
				// if a committee with that id does not exist it throws this error
				System.err.println(id + " cannot be removed because it doesn't exist!!");
			}

			// adds the list of committees to the modelandview
			mav.addObject("committees", comService.listCommittees());

			// returns the model and view
			return mav;

		} catch (Exception e) {

			ModelAndView mav = new ModelAndView("ajax_error");

			return mav;
		}
	}
}
