import React, {useEffect} from 'react';
import './App.css';
import {getWorkingPath, navigate, setBasepath, useControlledInterceptor, useRoutes} from "hookrouter";
import OAuth2 from "./Oauth2";

const routes = {
	'/': () => <HomePage/>,
	'/oauth*': () => <OAuth2/>,
	'/fakeuser': () => <FakeUser />
};


const NotFoundPage = () => <div>Page not Found</div>;


const FakeUser = () => <div>Fake User Component</div>


const HomePage = () => {

	useEffect(()=> {
		setTimeout(()=>{
			navigate('/oauth/authorization')
		}, 1000)
	}, []);

	return <div>This is a showcase of OAuth2 integration in React</div>

};




const App = (props) => {

	const match = useRoutes(routes);
	return (
		<div className="App" style={{marginTop:'100px'}}>
			{console.log('getWorkingPath', getWorkingPath())}

			{match || <NotFoundPage/>}
		</div>
	);
};

export default App;
