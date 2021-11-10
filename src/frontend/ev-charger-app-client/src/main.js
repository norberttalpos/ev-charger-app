import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import VueGeolocation from "vue-browser-geolocation/src";
import axios from 'axios'
import VueAxios from 'vue-axios'
import * as VueGoogleMaps from 'vue2-google-maps';
import {googleMapsApiKey} from "../key";

Vue.config.productionTip = false

export const serverprefix = window.location.protocol + '//' + window.location.hostname + ":8080/"

Vue.directive('blur', {
	inserted: function (el) {
		el.onfocus = (ev) => ev.target.blur()
	}
});

Vue.use(VueAxios, axios)

axios.interceptors.request.use(config => {
	config.baseURL = serverprefix;
	config.headers.Authorization =  "Bearer " + localStorage.getItem('accessToken');
	return config;
});

Vue.use(VueGeolocation);

Vue.use(VueGoogleMaps, {
	load: {
		key: googleMapsApiKey
	},
})

new Vue({
	router,
	store,
	vuetify,
	render: h => h(App)
}).$mount('#app')