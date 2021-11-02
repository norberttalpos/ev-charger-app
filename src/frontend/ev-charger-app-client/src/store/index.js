import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import {serverprefix} from "@/main";

export default new Vuex.Store({
	state: {
		role: 'none',
	},
	actions: {
		async fetchRole({commit}) {
			const resp = await Vue.prototype.axios.get(`${serverprefix}/api/role`);

			commit('SET_ROLE', resp.data);
		}
	},
	mutations: {
		SET_ROLE(state, role) {
			state.role = role;
		}
	},
	getters: {
		getRole: state => {
			return state.role;
		}
	}
})
