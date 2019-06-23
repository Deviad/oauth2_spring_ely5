var oauth2Ely5Utils = (function() {

	function urlencode(obj) {
		var str = [];
		for (var p in obj)
			str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		return str.join("&");
	}

	function getParameterByName(name, url) {
		if (!url) url = window.location.href;
		name = name.replace(/[\[\]]/g, '\\$&');
		var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
			results = regex.exec(url);
		if (!results) return null;
		if (!results[2]) return '';
		return decodeURIComponent(results[2].replace(/\+/g, ' '));
	}

	return {
		urlencode,
		getParameterByName
	}

})();
