import React, {useEffect, useState, useContext} from 'react';
import './App.css';
import {getWorkingPath, navigate, useRoutes} from "hookrouter";
import OAuth2 from "./Oauth2";
import UserContext, {UserProvider} from "./UserContext";
import oauth2Ely5Utils from './utils'
const routes = {
	'/': () => <HomePage/>,
	'/oauth*': () => <OAuth2/>,
	'/user-profile': () => <UserProfile/>
};


const NotFoundPage = () => <div>Page not Found</div>;


const UserProfile = () => {

	const user = useContext(UserContext);

	console.log("User", user);

	return (
		<div>
			User is <br />
			{JSON.stringify(user)}
		</div>
	);

};


const HomePage = () => {

	useEffect(() => {
		setTimeout(() => {
			navigate('/oauth/authorization')
		}, 1000)
	}, []);

	return <div>This is a showcase of OAuth2 integration in React</div>

};


const ContextInjector = () => {
	const match = useRoutes(routes);

	const [user, setUser] = useState({});

	useEffect(() => {
				setUser(oauth2Ely5Utils.storage.getItem("user"))
		}
	);

	return (
		<UserProvider value={user}>

			{match || <NotFoundPage/>}
		</UserProvider>
	)

};

const App = (props) => {


	return (
		<div className="App" style={{marginTop: '100px'}}>
			{console.log('getWorkingPath', getWorkingPath())}

			<ContextInjector/>

		</div>
	);
};

export default App;
