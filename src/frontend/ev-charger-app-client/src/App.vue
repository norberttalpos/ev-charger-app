<template>
    <v-app>
        <v-app-bar
            app
            color="appbar"
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
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Georama:ital,wght@1,800&display=swap');

::-webkit-scrollbar {
    display: none;
}

.background-green {
    background: rgb(106,189,128);
    background: linear-gradient(155deg, rgba(106,189,128,1) 0%, rgba(58,239,255,1) 100%);
}
.background-purple {
    background: rgb(98,0,234);
    background: linear-gradient(155deg, rgba(98,0,234,1) 0%, rgba(179,136,255,1) 100%);
}
</style>
