<template>
    <v-navigation-drawer
        v-model="value"
        app
        fixed
        floating
        right
        mobile-breakpoint="1264"
    >
        <v-dialog
            v-model="dialog"
            width="500"
        >
            <profile-dialog @close-dialog="dialog=false" />
        </v-dialog>
        <v-list
            nav
            dense
        >
            <v-list-item-group
                active-class="deep-purple--text text--accent-4"
            >
                <v-list-item @click="profile">
                    <v-list-item-action>
                        <v-icon class="mr-2"
                        >
                            mdi-account
                        </v-icon>
                    </v-list-item-action>
                    <v-list-item-title>Profile</v-list-item-title>

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
import ProfileDialog from "@/components/ProfileDialog";

export default {
    props: {
        value: {Boolean, default: false}
    },
    components: {ProfileDialog},
    name: "navigation-drawer",
    data(){
        return {
            dialog:false
        }
    },
    methods: {
        log_out(){
            localStorage.setItem('accessToken', null);
            router.push('/login');
        },
        profile() {
            this.dialog = true;
        }
    }
}
</script>

<style scoped>

</style>