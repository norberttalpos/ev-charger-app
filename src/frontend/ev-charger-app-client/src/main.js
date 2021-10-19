import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import VueGeolocation from "vue-browser-geolocation/src";
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.config.productionTip = false

Vue.use(VueGeolocation);

import * as VueGoogleMaps from 'vue2-google-maps';
import {googleMapsApiKey} from "../key";
Vue.use(VueGoogleMaps, {
	load: {
		key: googleMapsApiKey
	},
})

Vue.use(VueAxios, axios)

export const serverprefix = "http://localhost:8080/";

new Vue({
	router,
	store,
	vuetify,
	render: h => h(App)
}).$mount('#app')