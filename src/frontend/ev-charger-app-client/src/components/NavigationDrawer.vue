<template>
    <v-navigation-drawer
        v-model="value"
        app
        fixed
        floating
        right
        mobile-breakpoint="1264"
    >
        <v-list
            nav
            dense
        >
            <v-list-item-group
                active-class="deep-purple--text text--accent-4"
            >
                <v-list-item>
                    <v-list-item-title>Foo</v-list-item-title>
                </v-list-item>

                <v-list-item>
                    <v-list-item-title>Bar</v-list-item-title>
                </v-list-item>

                <v-list-item>
                    <v-list-item-title>Fizz</v-list-item-title>
                </v-list-item>

                <v-list-item @click="log_out">
                    <v-list-item-action>
                        <v-icon class="mr-2"
                        >
                            mdi-logout
                        </v-icon>
                    </v-list-item-action>
                    <v-list-item-title>Log out</v-list-item-title>
                </v-list-item>
            </v-list-item-group>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
import router from "@/router";

export default {
    props: {
        value: {Boolean, default: false}
    },
    name: "navigation-drawer",
    methods:{
        log_out(){
            localStorage.setItem('accessToken', null);
            router.push('/login');
        },

    }

    /*data() {
        return {
            items: {

            }
        }
    }*/
}
</script>

<style scoped>

</style>