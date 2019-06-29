import React, {useEffect} from 'react';

import {useRoutes} from "hookrouter";

import oauth2Ely5Utils from '../utils';
import endpointFactory from "../endpointFactory";



const Authorization = () => {
	console.log('NODE_ENV', process.env.NODE_ENV);
	const urlencodedParams = oauth2Ely5Utils.urlencode({
		"response_type": "code",
		"client_id": "fooClientIdPassword",
		"scope": "read",
		"redirect_uri": endpointFactory.tokenRedirect
		// "state": oauth2Ely5Utils.cookieStorage.getItem("XSRF-TOKEN")
	});

	const url = `http://localhost:5050/oauth/authorize/?${urlencodedParams}`;

	useEffect(() => {
		window.location.href = url;
	}, []);
	return (<div>Authorization</div>);
};


const handleLocalAuth = () => {
	const authorizeUrl = "http://localhost:5050/oauth/token";
	fetch(authorizeUrl, {
		// credentials: "include",
		method: "post",
		headers: {
			"Content-type": "application/json;charset=UTF-8",
			// "Authorization": `Basic '${+btoa("fooClientIdPassword:secret")}'`
		},
		body: JSON.stringify({
			"grant_type": "authorization_code",
			"client_id": "fooClientIdPassword",
			"client_secret": "secret",
			"code": oauth2Ely5Utils.getParameterByName("code"),
			"redirect_uri": endpointFactory.tokenRedirect
		})
	})
	.then(res => ((res.status / 100) | 0) === 2 && res)
	.then(res => res.json())
	.then(function (data) {
		console.log('Request succeeded with JSON response', data);
		oauth2Ely5Utils.storage.setItem("user", JSON.stringify(data));



		window.location.href = endpointFactory.resource
	})
	.catch(function (error) {
		console.log('Request failed', error);
	});
};

const Token = () => {


	useEffect(handleLocalAuth, []);

	return (<div>Token</div>);

};

const oauth2Routes = {
	'/authorization': () => <Authorization/>,
	'/token': () => <Token/>
};

const OAuth2 = () => {
	const routeResult = useRoutes(oauth2Routes);

	return (
		<div>
			{routeResult}
		</div>
	);
};

export default OAuth2;
