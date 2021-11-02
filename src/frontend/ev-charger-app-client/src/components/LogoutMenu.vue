<template>
    <div class="text-center">
        <v-menu
            offset-y
            bottom
            :close-on-content-click="false"
            v-if="logOutAvailable"
        >
            <template v-slot:activator="{ on, attrs }">
                <v-app-bar-nav-icon v-bind="attrs" v-on="on"></v-app-bar-nav-icon>
            </template>

            <v-card>
                <v-list>
                    <v-list-item @click="profile">
                    <v-list-item-action>
                        <v-icon
                        >
                            mdi-account
                        </v-icon>
                    </v-list-item-action>
                    <v-list-item-title>Profile page</v-list-item-title>
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

export default {
    name: "logout-menu",
    mixins: [LogoutAvailable],
    data: () => ({
    }),
    methods:{
        log_out(){
            localStorage.setItem('accessToken', null);
            router.push('/login');
        },
        profile(){
            router.push('/profile');
        }
    }
}
</script>
