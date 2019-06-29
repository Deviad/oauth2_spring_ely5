export default {


	get resource() {
		if(process.env.NODE_ENV === 'production') {
			return 'http://localhost:5051/react/user-profile'
		} else {
			return 'http://localhost:3000/user-profile'
		}
	},

	get tokenRedirect() {
		if(process.env.NODE_ENV === 'production') {
			return 'http://localhost:5051/react/oauth/token'
		} else {
			return 'http://localhost:3000/oauth/token'
		}
	}


}
