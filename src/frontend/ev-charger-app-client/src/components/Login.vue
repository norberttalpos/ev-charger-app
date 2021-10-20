<template>
    <div class="home">
        <template>
            <v-card class="mx-auto my-12" max-width="374">
                <v-card-title class="justify-center">LOGIN</v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col cols="12">
                                <v-text-field
                                    autofocus
                                    v-model="username"
                                    label="Username"
                                    outlined
                                    name="username"
                                ></v-text-field>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12">
                                <v-text-field
                                    autofocus
                                    v-model="password"
                                    :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                                    label="Password"
                                    outlined
                                    :type="show ? 'text' : 'password'"
                                    @click:append="show = !show"
                                    name="password"
                                ></v-text-field>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>
                <v-card-actions class="justify-center">
                    <v-btn
                        color="primary"
                        elevation="2"
                        raised
                        rounded
                        text
                        @click="login"
                    >
                        SIGN IN
                    </v-btn>
                </v-card-actions>
            </v-card>
        </template>
    </div>
</template>
<script>

import router from '../router'
import {serverprefix} from "@/main";

export default {
    data() {
        return {
            username:"",
            password:"",
            show:false
        }
    },
    name: 'login',
    components: {},
    methods: {
        async login() {

            try {
                const response = await this.axios.post(`${serverprefix}api/login`,{
                    password:this.password,
                    username:this.username
                });

                if(response.data?.accessToken) {
                    localStorage.setItem('accessToken', response.data.accessToken);

                    router.push('/map')
                }
            }
            catch(error) {
                console.log(error);
            }
        }
    },
};
</script>
