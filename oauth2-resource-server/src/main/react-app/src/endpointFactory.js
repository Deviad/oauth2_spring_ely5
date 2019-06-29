export default {


	get resource() {
		if(process.env.NODE_ENV === 'production') {
			return 'http://localhost:5051/react/fakeuser'
		} else {
			return 'http://localhost:3000/react/fakeuser'
		}
	},

	get tokenRedirect() {
		if(process.env.NODE_ENV === 'production') {
			return 'http://localhost:5051/react/oauth/token'
		} else {
			return 'http://localhost:3000/react/oauth/token'
		}
	}


}
