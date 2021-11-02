<template>
    <v-app>
        <v-app-bar
            app
            color="#00008b"
            dark
        >
            <div class="d-flex align-center" style="margin-left: 15px;">
                <v-img
                    alt="Woozy"
                    class="shrink mr-2"
                    contain
                    src="../src/assets/woozy.png"
                    transition="scale-transition"
                    max-width="50"
                />

                <span style="margin-left: 10px; font-size: 40px; font-family: 'Georama', sans-serif !important;">Töltősch</span>
            </div>
            <v-spacer/>
            <dark-mode-toggle></dark-mode-toggle>
            <v-app-bar-nav-icon v-if="adminNavDrawer" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
            <logout-menu v-else></logout-menu>

        </v-app-bar>

        <navigation-drawer v-if="adminNavDrawer" :value="drawer"/>

        <v-main>
            <router-view/>
        </v-main>
    </v-app>
</template>

<script>

import DarkModeToggle from "@/components/DarkModeToggle";
import NavigationDrawer from "@/components/NavigationDrawer";
import LogoutMenu from "@/components/LogoutMenu";
import {LogoutAvailable} from "@/mixins/LogoutAvailable";
export default {
    name: 'App',
    components: {LogoutMenu, NavigationDrawer, DarkModeToggle},
    mixins: [LogoutAvailable],
    data() {
        return {
            drawer: false,
        }
    },
    computed: {
        adminNavDrawer() {
            return this.$store.getters.getRole === 'admin' && this.logOutAvailable;
        },
    },
    mounted() {
        const options = {
            enableHighAccuracy: true,
            timeout: 5000,
            maximumAge: 0
        };

        function success(pos) {
            const crd = pos.coords;

            console.log('Your current position is:');
            console.log(`Latitude : ${crd.latitude}`);
            console.log(`Longitude: ${crd.longitude}`);
            console.log(`More or less ${crd.accuracy} meters.`);
        }

        function error(err) {
            console.warn(`ERROR(${err.code}): ${err.message}`);
        }

        navigator.geolocation.getCurrentPosition(success, error, options);
    }
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Georama:ital,wght@1,800&display=swap');

::-webkit-scrollbar {
    display: none;
}

.background {
    background: rgb(106,189,128);
    background: linear-gradient(155deg, rgba(106,189,128,1) 0%, rgba(58,239,255,1) 100%);
}
</style>
