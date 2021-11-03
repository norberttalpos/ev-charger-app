import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import {serverprefix} from "@/main";

export default new Vuex.Store({
	state: {
		role: 'none',
		id:-1
	},
	actions: {
		async fetchRole({commit}) {
			const resp = await Vue.prototype.axios.get(`${serverprefix}/api/role`);

			commit('SET_ROLE', resp.data);
		},
		async fetchId({commit}){
			const resp = await Vue.prototype.axios.get(`${serverprefix}/api/person/current-person`);

			commit('SET_ID', resp.data.id);

		}
	},
	mutations: {
		SET_ROLE(state, role) {
			state.role = role;
		},
		SET_ID(state,id){
			state.id=id;
		}
	},
	getters: {
		getRole: state => {
			return state.role;
		},
		getId: state=>{
			return state.id;
		}
	}
})
