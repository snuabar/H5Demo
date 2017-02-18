window.onresize = function(){
	doResize();
};

var ImageScale = function() {
	return "98%";
}
var SubDivSize = function() {
	var screenScale = 1.0;
	if (screen.hasOwnProperty("scale")) {
		screenScale = screen.scale;
	}
	var divSize = (window.innerWidth / 2.0 - 26) * screenScale + "px";
	return divSize;
}
function loadImageList() {
	var divImgList = document.getElementById("img_list");
//	var images = ["http://hiphotos.baidu.com/image/pic/item/d53f8794a4c27d1edbf3cee012d5ad6eddc43838.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa4bf151cabd8f8c5494ee7b71.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/faf2b2119313b07e582dae2c05d7912397dd8c06.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/37d3d539b6003af3bdab25443c2ac65c1038b685.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/a9d3fd1f4134970ad293ef0b9ccad1c8a7865d36.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/bd3eb13533fa828b7a8263c7f41f4134970a5a75.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/bd315c6034a85edf445e000740540923dc5475c0.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/5882b2b7d0a20cf4ac0b00fb7f094b36acaf997f.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/c8177f3e6709c93d9af8f0d3963df8dcd1005488.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/1c950a7b02087bf444efaecdfbd3572c11dfcf97.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/8694a4c27d1ed21b0f1ed2c1a46eddc451da3f3a.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/2f738bd4b31c8701e1904dc22e7f9e2f0708fff4.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/91529822720e0cf34488dfdf0346f21fbe09aa93.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa4bf151cabd8f8c5494ee7b71.jpg",
//		"http://hiphotos.baidu.com/image/pic/item/d53f8794a4c27d1edbf3cee012d5ad6eddc43838.jpg"
//	];
	var images;
	try {
		var imgsString = window.android.getImages();
		images = imgsString.split(",");
	} catch(e) {
		var spanEle = document.createElement("span");
		spanEle.innerText = e.description;
		divImgList.appendChild(spanEle);
		return;
	}
	
	if(images.length == 0) {
		divImgList.innerHTML = "";
		var spanEle = document.createElement("span");
		spanEle.innerText = "List is empty.";
		divImgList.appendChild(spanEle);
	} else {
		for(var i = 0; i < images.length; i++) {
			//add subdiv
			var divSub = Creater.subDiv();
			divImgList.appendChild(divSub);

			//add img to subdiv
			var imgurl = images[i];
			var imgElem = Creater.imageWithURL(imgurl);
			divSub.appendChild(imgElem);
		}

		doResize();
		var a = setTimeout("doResize()", 100);
	}
//	document.writeln(screen.hasOwnProperty("scale") + "");
}
var UIChanger = {
	resetSubDivCSS: function(div) {
		div.style.background = "gray";
		div.style.display = "flex";
		div.style.float = "left";
		div.style.width = SubDivSize();
		div.style.height = SubDivSize();
		div.style.margin = "4px";
		div.style.alignItems = "center";
	},
	resetImgViewCSS: function(img) {
		if(img.width >= img.height) {
			img.style.width = ImageScale();
			img.style.height = "auto";
		} else {
			img.style.width = "auto";
			img.style.height = ImageScale();
		}
		
		img.style.margin = "auto";
	}
}
function doResize() {
	var divs = document.getElementsByClassName("sub_div");
	for(var i = 0; i < divs.length; i++) {
		var div = divs[i];
		UIChanger.resetSubDivCSS(div);
	}
	
	var imgs = document.getElementsByClassName("imgcontent");
	for(var i = 0; i < imgs.length; i++) {
		var img = imgs[i];
		UIChanger.resetImgViewCSS(img);
	}
}
var Creater = {
	imageWithURL: function(imgurl, idx) {
		var imgElem = document.createElement("img");
		imgElem.setAttribute("class", "imgcontent")
		imgElem.setAttribute("src", imgurl);
		imgElem.setAttribute("title", imgurl);
		imgElem.setAttribute("alt", imgurl);
		imgElem.setAttribute("onclick","window.location=" + "'" + imgurl + "'")
		return imgElem;
	},
	subDiv: function() {
		var divSub = document.createElement("div");
		divSub.setAttribute("class", "sub_div");
		return divSub;
	},
	loadCSS: function(cssfile) {
		try {
			var linkEle = document.createElement("link");
			linkEle.rel = 'stylesheet';
			linkEle.type = 'text/css';
			linkEle.href = cssfile;
			var head = document.getElementsByTagName("body")[0];
			head.appendChild(linkEle);
		} catch(e) {
			document.writeln(e.description);
		}
	}
}
