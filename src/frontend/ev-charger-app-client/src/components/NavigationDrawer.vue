<template>
    <v-navigation-drawer
        v-model="value"
        app
        fixed
        floating
        right
        mobile-breakpoint="1264"
        :width="200"
    >
        <v-dialog
            v-model="dialog"
            max-width="400px"
        >
            <profile-dialog @close-dialog="dialog=false" />
        </v-dialog>
        <v-dialog
            v-model="new_car_dialog"
            max-width="400px"
        >

            <new-car-dialog @close-dialog="new_car_dialog=false"/>

        </v-dialog>
        <v-list
            nav
            dense
        >
            <v-list-item-group v-model="selection" class="zoom-out">
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
import NewCarDialog from "@/components/NewCarDialog";

export default {
    props: {
        value: {Boolean, default: false}
    },
    components: {NewCarDialog, ProfileDialog},
    name: "navigation-drawer",
    data(){
        return {
            dialog: false,
            new_car_dialog:false,
            selection: null,
        }
    },
    methods: {
        clear() {
            this.$nextTick(() => {
                this.selection = null
            });
        },
        log_out(){
            localStorage.setItem('accessToken', null);
            router.push('/login');

            this.clear();
        },
        profile() {
            this.dialog = true;

            this.clear();
        }
    }
}
</script>

<style scoped>
    .v-list-item--active {

    }
</style>