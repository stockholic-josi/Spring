function setEmail(e){
	if(e != "") document.regForm.email2.value = e;
}

//아이디 체크
function isValidUserid(el) {
	var pattern = /^[a-zA-Z]{1}[a-zA-Z0-9_]{3,12}$/;
	return (pattern.test(el)) ? true : false;
}

//패스워드 체크
function isValidPasswd(el) {
	return (el.length < 6 || el.length > 18) ? modalAlert("패스워드는 6자이상 18자이하 <br> 이어야 합니다.") : true;
}


//닉네임 체크
function isValidNick(el) {
	var pattern = /^[a-zA-Z0-9가-힝]{2,10}$/;
	return (pattern.test(el)) ? true : false;
}

//이메일 체크
function isValidEmail(el) {
	var pattern = /^[_a-zA-Z0-9-\.]+@[\.a-zA-Z0-9-]+\.[a-zA-Z]+$/;
	return (pattern.test(el)) ? true : false;
}

