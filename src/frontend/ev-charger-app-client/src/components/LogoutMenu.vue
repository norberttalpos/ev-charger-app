<template>
    <div class="text-center ml-5">
        <v-menu
            top
            offset-y
            :close-on-content-click="false"
            v-if="logOutAvailable"
        >
            <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="primary"
                    dark
                    v-bind="attrs"
                    v-on="on"
                >
                    <v-icon class="mr-2">
                        mdi-menu
                    </v-icon>
                </v-btn>
            </template>

            <v-card>



                <v-list>



                    <v-list-item @click="profile">
                    <v-list-item-action>
                        <v-icon class="mr-2"
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

export default {
    name: "logout-menu",
    data: () => ({
    }),
    computed:{
        logOutAvailable() {
            const path=this.$route.name;
            console.log(path);
            return !( path==="Login" || path==="Sign Up");
        }
    },
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
