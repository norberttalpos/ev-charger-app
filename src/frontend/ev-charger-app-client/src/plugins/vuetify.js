import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

import colors from 'vuetify/lib/util/colors'

Vue.use(Vuetify);

export default new Vuetify({
	theme: {
		dark: false,

		themes: {
			light: {
				primary: colors.green.accent4,
				darkprimary: colors.green.darken3,
				secondary: colors.deepPurple.accent4,
				error: colors.red.accent4,
				appbar: colors.green.darken4,
			},
			dark: {
				primary: colors.purple.accent4,
				darkprimary: colors.purple.darken3,
				secondary: colors.deepPurple.accent4,
				error: colors.red.accent4,
				appbar: colors.purple.darken4,
			}
		},
	},
});
