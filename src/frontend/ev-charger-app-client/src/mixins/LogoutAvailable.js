export const LogoutAvailable = {
	computed: {
		logOutAvailable() {
			const path=this.$route.name;
			console.log(path);
			return !( path==="Login" || path==="Sign Up");
		}
	},
}