
function copyToClipboard() {
  /* Get the text field */
  var copyText = document.getElementById("ipLink");

  /* Select the text field */
  copyText.select();
  copyText.setSelectionRange(0, 99999); /* For mobile devices */

  /* Copy the text inside the text field */
  navigator.clipboard.writeText(copyText.value);
  
  /* Alert the copied text */
  alert("Copied the text: " + copyText.value);
}


/*window.onbeforeunload = function() { 
	
	PF('validStat').renderMessage({
			"summary": "Your work will be lost",
			"severity": "Back"
		})
		
	return "Your work will be lost.";  
	
	};*/


function onLogin() {
	localStorage.setItem('logged-in', true);
	sessionStorage.setItem('logged-in', true);
}

function checkLoginStatus() {

	window.onbeforeunload = (event) => {
		localStorage.removeItem('logged-in');
	}

	let loggedIn = localStorage.getItem('logged-in');
	let sessionLoggedIn = sessionStorage.getItem('logged-in');
	if (!loggedIn) {
		if (sessionLoggedIn) {
			localStorage.setItem('logged-in', JSON.parse(sessionLoggedIn));
			//go to authenticated space
			window.location.href = '/authenticated';
		} else {
			//go to login
			window.location.href = '/login';
		}
	} else {
		//go to authenticated space
		window.location.href = '/authenticated';
	}

	window.addEventListener('storage', (event) => {
		if (event.key == 'logout' && event.newValue) {
			sessionStorage.removeItem('logged-in');
			localStorage.removeItem('logout');
			window.location.href = '/login';
		}
	});

}

function onLogout() {
	localStorage.setItem('logout', true)
}



// The following function captures scroll location of datatable
// and the later sets it back after every ajax update to retain last scroll postion. 
var hsLoc;
function captureHScrollPosition() {
	hsLoc = $(".ui-datatable .ui-datatable-scrollable-body").scrollLeft();
}

// is supposed to be called within every deactivator method 
function setHScrollPosition() {
	$(".ui-datatable .ui-datatable-scrollable-body").scrollLeft(hsLoc);
}

function scrollToBottom() {
	//alert("Hello! I am an alert box!");  
	$('.ui-datatable-scrollable-body').scrollTop(100000);

}


// The following function captures scroll location of modules menu list
// and the later sets it back after every ajax update to retain last scroll postion. 
var vtLoc;
function captureVScrollPosition() {
	vtLoc = $("ui-sidebar-left").scrollBottom();
}

// is supposed to be called within every deactivator method 
function setVScrollPosition() {
	$("ui-sidebar-left").scrollBottom(vtLoc);
}





function getCook(cookiename) {
	// Get name followed by anything except a semicolon
	var cookiestring = RegExp("" + cookiename + "[^;]+").exec(document.cookie);
	// Return everything after the equal sign, or an empty string if the cookie name not found
	return decodeURIComponent(!!cookiestring ? cookiestring.toString().replace(
		/^[^=]+./, "") : "null");
}

function dateValidation(event) {
	
	
	
	if (event.type == "click") {
		var gettarget = $(event.target);//The click event can come from button or anchor tag.
		if (gettarget.is("a")) {//if the click coming from anchor tag
			id = event.path[0]['id'];
		} else {//else the click is from button
			id = $("[id='" + event.path[1]['id'] + "']").siblings('input')[0]['id'];
		}
	} else {
		if (event.path == null) { 
			id = event.currentTarget['id'];
		} else {
			id = event.path[0]['id'];
		}
	}


	var id = event.path[0]['id'];

	var componentDate = document.getElementById(id).value;

	if (moment(componentDate, "DD/MM/YYYY", true).isValid() == false) {

		PF('validStat').renderMessage({
			"summary": "Invalid date format",
			"severity": "warn"
		})

		var clipDate = document.getElementById("headerForm:clipBoardElement").value;

		//var responseDate = moment(clipDate).format('DD/MM/YYYY');
		document.getElementById(id).value = clipDate;

		return false;

	}

	return true;

}

function checkForWarehouse() {
	var extractid = event.path[1]['id'].split(":");

	if (document.getElementById(extractid[0] + ":wh").value.trim() == "") {
		PF('validStat').renderMessage({
			"summary": "Warehouse is emplty",
			"severity": "warn"
		})
		return false;
	} else {
		return true;
	}
}

function inspectNulls() {
	var extractid = event.path[1]['id'].split(":");
	/*if (checkDateValidation(extractid[0] + ":docCreateDate_input") == false)
		return false;*/

	proceed = validateRequiredFields(".master-part ");
	if (proceed == true) {
		proceed = validateDetailsPartRequiredFields();
		return proceed;
	} else {
		return proceed;
	}
}

function validateDetailsPartRequiredFields() {
	if ($('.details-part').find('input[aria-required=true]').length > 0) {
		proceed = validateRequiredFields(".details-part ");
		if (proceed == true) {
			proceed = validateSiblingPartRequiredFields();
			return proceed;
		} else {
			return proceed;
		}
	} else {
		/*PF('validStat').renderMessage({
			"summary" : "Item details can not be empty",
			"severity" : "warn"
		})
		proceed = false;*/
	}
	return proceed;
}

function validateSiblingPartRequiredFields() {
	proceed = validateRequiredFields(".sibling-part ");
	return proceed;
}

function validateRequiredFields(id) {

	var proceed = true;

	$
		.each(
			$(id + 'input[aria-required=true]'),
			function(sKey, sValue) {
				if (document.getElementById(sValue.id).value == ""
					|| parseFloat(document
						.getElementById(sValue.id).value) == "0") {

					var rownum = parseInt(sValue.id.match(/\d+/)) + 1;
					console.log(rownum);

					PF("validStat").renderMessage(
						{
							"summary": sValue.alt
								+ " not defined "
								+ (rownum >= 0 ? "at " + rownum
									: ""),
							"severity": "warn"
						})

					document.getElementById(sValue.id).classList
						.add("reqFieldZ");
					proceed = false;
					return proceed;

				} else {
					document.getElementById(sValue.id).classList
						.remove("reqFieldZ");
				}
			});
	return proceed;

}
function onBeforeAjaxCall(event, setevttype, allowzero) {
	if (event.path == null) {
		id = event.currentTarget['id'];
	} else {
		id = event.path[0]['id'];
	}

	captureHScrollPosition();

	if (setevttype == 1) {
		//console.log("onbefore ajax event");

		/*if (document.getElementById(id).hasAttribute("previouselementid")) {
			document.getElementById(id).removeAttribute("previouselementid");
		}*/

		if (typeof id === 'undefined'
			|| document.getElementById(id).value === null) {
			console.log("invalid case");
			document.getElementById(id).value = null;
		} else {

			if (allowzero != "Y") {
				if (parseFloat(document.getElementById(id).value.trim()) === "0") {
					document.getElementById(id).value = "null";
				}
			}
			//negative number checking[below]
			if (Math.sign(parseFloat(document.getElementById(id).value.trim())) === -1
				|| Math.sign(parseFloat(document.getElementById(id).value
					.trim())) == -0) {
				document.getElementById(id).value = "null";
			}
			//negative number checking[above] 

			if (isNaN(document.getElementById(id).value.trim())) {
				//console.log("not a number");
				document.getElementById(id).value = document.getElementById("headerForm:clipBoardElement").value;
				return false;
			} else {
				return true;
			}
		}
	} else {
		//console.log("onfocus event");
		/*document.getElementById(id).setAttribute("previousvalue",
				document.getElementById(id).value.trim());
		window.previousvalueid = id;
		setRownumberandpreviousval(id);*/
	}

}

function disableWarehouse(id) {
	PF('whMasterBtn').disable();
	$("[id='" + id + ":wh']").prop("readonly", true);
}
