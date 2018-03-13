function validate() {
	var name = document.getElementById('name').value;
	var pwd = document.getElementById('pwd').value;

	var rtnVal = false;
	if (name == 'undefined' || name == null || name == "") {
		alert("Please enter UserName.");
		return false;
	} if (pwd == 'undefined' || pwd == null || pwd == "") {
		alert("Please enter Password.");
		return false;
	} else {
		return true;
	}
}