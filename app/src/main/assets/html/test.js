function getValueFromAndroid(){
    var v = window.android.testStrings();
    document.getElementById("value_from_androids").innerHTML = v;
}

function showToast(){
    var component = document.getElementById("toast_string");
    var string = component.value;
    window.android.toast(string);
}

function selectImage(){
	window.android.showSelImage();
}

function setURLToTextField(strURL){
	var component = document.getElementById("image_url");
	component.value = strURL;
}

function loadImage(){
	var component = document.getElementById("image_url");
	var strURL = component.value;
	var imgCom = document.getElementById("imageview");
	imgCom.src = strURL;
}
