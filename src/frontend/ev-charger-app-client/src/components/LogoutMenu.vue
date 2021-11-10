<template>
    <div class="text-center">
        <v-menu
            offset-y
            bottom
            :close-on-content-click="false"
            v-if="logOutAvailable"
        >
            <template v-slot:activator="{ on, attrs }">
                <v-app-bar-nav-icon class="zoom-out" v-bind="attrs" v-on="on"></v-app-bar-nav-icon>
            </template>
            <v-dialog
                v-model="dialog"
                max-width="400px"
            >
                <profile-dialog @close-dialog="dialog=false" />
            </v-dialog>

            <v-card class="zoom-out">
                <v-list>
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


                </v-list>

            </v-card>
        </v-menu>
    </div>
</template>


<script>
import router from "@/router";
import {LogoutAvailable} from "@/mixins/LogoutAvailable";
import ProfileDialog from "@/components/ProfileDialog";

export default {
    name: "logout-menu",
    components: {ProfileDialog},
    mixins: [LogoutAvailable],
    data(){
        return {
            dialog:false
        }
    },
    methods:{
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
