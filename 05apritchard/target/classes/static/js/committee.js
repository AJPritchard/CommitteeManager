/*
 * Runs the build table function in CommitteeController
 * Then it adds it in to the base.html #table snippet
 * 
 * Most important thing about this function is that it places "ajax_table.html" into #table
 */
function buildTable() {

	// uses AJAX to get the table from the backend Java
	$.ajax({
		method : "GET",
		url : "/committee/ajax/buildTable",
		cache : false
	}).done(function(txt) {
		// then places the snippet of the table into the base.html
		// the table is built in ajax_table.html and is placed in base.html
		$("#table").html(txt);
	}).fail(function(jqXHR, textStatus) {
		alert("Request failed: " + textStatus + " : " + jqXHR.responseText);

	});
}

/*
 * Runs through every form on the popup to make sure they are all not only in
 * use, but also the correct type, as in id is a number
 */
function validateFields(id, title, members, type, command, start, end) {

	var valid = true;

	// removes the class is invalid from all fields
	$(id).removeClass("is-invalid");
	$(title).removeClass("is-invalid");
	$(members).removeClass("is-invalid");
	$(type).removeClass("is-invalid");
	$(command).removeClass("is-invalid");
	$(start).removeClass("is-invalid");
	$(end).removeClass("is-invalid");

	// finds the value for all fields
	var idStr = $(id).val();
	var titleStr = $(title).val();
	var memberStr = $(members).val();
	var typeStr = $(type).val();
	var commandStr = $(command).val();
	var startStr = $(start).val();
	var endStr = $(end).val();

	// Goes through each variable making sure that its either valid through
	// being filled, or that its a number when it needs to be a number
	if (!($.isNumeric(idStr) && idStr <= 99999 && idStr >= 0)) {
		$(id).addClass("is-invalid");
		valid = false;
	}
	if (titleStr.length < 1) {
		$(title).addClass("is-invalid");
		valid = false;
	}
	if ((!$.isNumeric(memberStr) && idStr < 99999 && idStr >= 0)) {
		$(members).addClass("is-invalid");
		valid = false;
	}
	return valid;
}

// reads in each of the variables after its already been validated and uses ajax
// to send the data to the backend
function addCommittee(myId, myTitle, myMembers, myType, myCommand, myStart,
		myEnd) {
	// the strings of all the values so that they can be sent to the backend
	var idStr = $(myId).val();
	var titleStr = $(myTitle).val();
	var memberStr = $(myMembers).val();
	var typeStr = $(myType).val();
	var commandStr = $(myCommand).val();
	var startStr = $(myStart).val();
	var endStr = $(myEnd).val();

	// alert("VALUES READ");
	// does AJAX to get the data to the java method that can add to the list of
	// committees
	$.ajax({
		method : "GET",
		url : "/committee/ajax/addCommittee",
		cache : false,
		data : {
			id : idStr,
			title : titleStr,
			members : memberStr,
			type : typeStr,
			command : commandStr,
			start : startStr,
			end : endStr,
		}
	}).done(function(txt) {
		// rebuilds the table when its done
		$("#table").html(txt);
		// alert("AJAX CALLED: TABLE PLACED");

	}).fail(function(jqXHR, textStatus) {
		alert("Request failed: ");// + textStatus + " : " +
		// jqXHR.responseText);

	});
}

function openAddDialogue() {
	// open dialog when user clicks the right button
	// alert("ADD PRESSED");
	$("#dlgAdd").dialog("open");
}

// open dialog with data filled in when user clicks the right button
function openEditDialogue(committeeId, committeeTitle, committeeMembers,
		committeeType, committeeCommand, committeeStart, committeeEnd) {
	// alert("Edit pressed");

	$("#dlgEdit").dialog("open");

	// alert("Opened Dialog");
	// alert(committeeId);

	// puts the committee data into the fields to be editted/read
	document.getElementById("dlg_id_edit").value = committeeId;
	document.getElementById("dlg_title_edit").value = committeeTitle;
	document.getElementById("dlg_members_edit").value = committeeMembers;
	document.getElementById("dlg_type_edit").value = committeeType;
	document.getElementById("dlg_command_edit").value = committeeCommand;
	document.getElementById("dlg_start_edit").value = committeeStart;
	document.getElementById("dlg_end_edit").value = committeeEnd;

	// alert("ID Filled");
}

// on click it opens a dialogue, with the option to delete the committee
function deleteCommittee(committeeId) {
	$("#dlgDelete").dialog("open");

	// grabs just the committee id
	document.getElementById("dlg_id_del").value = committeeId;

}

// When the webpage is opened
$(document).ready(
		function() {

			// when the webpage opens, I want it to immediately run buildTable()
			buildTable();

			// register and hide dialog for the add popup
			dialog = $("#dlgAdd").dialog(
					{
						autoOpen : false,
						height : 700,
						width : 350,
						modal : true,
						buttons : {
							"Add New Committee" : function() {

								// runs validate fields to make sure we can use
								// addcommittee without it breaking
								if (validateFields("#dlg_id", "#dlg_title",
										"#dlg_members", "#dlg_type",
										"#dlg_command", "#dlg_start",
										"#dlg_end")) {

									// runs add committee
									addCommittee("#dlg_id", "#dlg_title",
											"#dlg_members", "#dlg_type",
											"#dlg_command", "#dlg_start",
											"#dlg_end");
								}

								// leaves popup open in case you want to add
								// more than one
							},
							Cancel : function() {
								dialog.dialog("close");
							}
						},
						close : function() {
							// do something on dialog close...if needed.
						}
					});

			// dialog for edit committee
			dialog_e = $("#dlgEdit")
					.dialog(
							{
								autoOpen : false,
								height : 700,
								width : 350,
								modal : true,
								buttons : {
									"Edit Committee" : function() {

										// Does the exact same thing as add
										// committee in terms of AJAX
										// but in the editCommittee() function
										// it changes all of the fields
										// of the popup to the ones of the
										// committee that we want

										// runs validate fields to make sure we
										// can use
										// addcommittee without it breaking
										if (validateFields("#dlg_id_edit",
												"#dlg_title_edit",
												"#dlg_members_edit",
												"#dlg_type_edit",
												"#dlg_command_edit",
												"#dlg_start_edit",
												"#dlg_end_edit")) {

											// runs add committee
											addCommittee("#dlg_id_edit",
													"#dlg_title_edit",
													"#dlg_members_edit",
													"#dlg_type_edit",
													"#dlg_command_edit",
													"#dlg_start_edit",
													"#dlg_end_edit");

											dialog_e.dialog("close");

										}

									},
									Cancel : function() {
										dialog_e.dialog("close");
									}
								},
								close : function() {
									// do something on dialog close...if
									// needed.
								}
							});

			// script for the delete dialog popup
			dialog_d = $("#dlgDelete").dialog(
					{
						autoOpen : false,
						height : 225,
						width : 350,
						modal : true,
						buttons : {
							"Delete Committee" : function() {

								var idStr = document
										.getElementById("dlg_id_del").value;

								$.ajax({
									method : "GET",
									url : "/committee/ajax/removeCommittee",
									cache : false,
									data : {
										id : idStr,
									}
								}).done(function(txt) {
									// rebuilds the table when its done
									$("#table").html(txt);

								}).fail(function(jqXHR, textStatus) {
									alert("Request failed: ");// + textStatus
									// + " : " +
									// jqXHR.responseText);

								});
								dialog_d.dialog("close");

							},
							Cancel : function() {
								dialog_d.dialog("close");
							}
						},
						close : function() {
							// do something on dialog close...if needed.
						}
					});
		});
