export const LogoutAvailable = {
	computed: {
		logOutAvailable() {
			const path=this.$route.name;

			return !( path==="Login" || path==="Sign Up");
		}
	},
}