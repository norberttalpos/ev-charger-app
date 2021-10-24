import Vue from 'vue'
import VueRouter from 'vue-router'
import {serverprefix} from "@/main";

Vue.use(VueRouter)

const routes = [
	{
		path: '/',
		name: 'Login',
		props: true,
		component: () => import('../pages/Login.vue'),
	},
	{
		path: '/map',
		name: 'Map',
		props: true,
		component: () => import('../pages/Map.vue'),
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

const hasRightForPage = async (to) => {
	const response = await Vue.prototype.axios.post(`${serverprefix}api/hasRightForPage`,{
		route: to.path
	});

	return response.data;
}

router.beforeEach(async (to, from, next) => {
	const resp = await hasRightForPage(to);

	console.log(resp);

	if (resp === 'hasRight')
		next();
	else if(resp === 'tokenExpired')
		next({
			path: '/',
		});
	else
		next(false);
});

export default router
