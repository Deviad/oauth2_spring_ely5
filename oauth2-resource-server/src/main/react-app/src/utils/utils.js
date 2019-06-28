import 'js-cookie';

const oauth2Ely5Utils = (function() {

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


	var cookieStorage = (function cookieStorage() {

		const options = {
			secure: true
		};

		var setItem = (key, value) => window.Cookies.set(key, value, options);

		var getItem = key => window.Cookies.get(key, options);

		var removeItem = key => window.Cookies.remove(key, options);

		var key = index => {
			let allKeys = Object.keys(window.Cookies.getJSON());
			return index > -1 && index <= allKeys.length
				? allKeys[index]
				: "";
		};

		return {
			setItem,
			getItem,
			removeItem,
			key
		}
	})();

	const storageAvailable = type => {
		try {
			var storage = window[type],
				x = "__storage_test__";
			storage.setItem(x, x);
			storage.removeItem(x);
			return true;
		}
		catch (e) {
			return e instanceof DOMException && (
					// everything except Firefox
				e.code === 22 ||
				// Firefox
				e.code === 1014 ||
				// test name field too, because code might not be present
				// everything except Firefox
				e.name === "QuotaExceededError" ||
				// Firefox
				e.name === "NS_ERROR_DOM_QUOTA_REACHED") &&
				// acknowledge QuotaExceededError only if there's something already stored
				storage.length !== 0;
		}
	};

	var storageFactory = (function storageFactory() {
		const getStorage = () => storageAvailable("sessionStorage")
			? sessionStorage
			: storageAvailable("localStorage")
				? localStorage
				: cookieStorage;
		return {
			getStorage
		}
	})();

	return {
		urlencode,
		getParameterByName,
		cookieStorage,
		storageFactory
	}

})();

export default oauth2Ely5Utils;
