<template>
    <div class="home" style="position: absolute; height: 100%; width: 100%; background-color: #6abd80;">
        <v-card class="mx-auto" max-width="400" height="400" style="margin-top: 150px;">
            <v-card-title class="justify-center">LOGIN</v-card-title>
            <v-card-text>
                <v-container>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field
                                v-model="username"
                                label="Username"
                                outlined
                                name="username"
                                persistent-placeholder
                            ></v-text-field>
                        </v-col>
                    </v-row>
                    <v-row>
                        <v-col cols="12">
                            <v-text-field
                                v-model="password"
                                :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                                label="Password"
                                outlined
                                :type="show ? 'text' : 'password'"
                                @click:append="show = !show"
                                name="password"
                                persistent-placeholder
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
                    style="background-color: green;"
                    width="100px"
                    height="40px"
                >
                    <span style="color: white">SIGN IN</span>
                </v-btn>
            </v-card-actions>
        </v-card>
        <v-snackbar v-model="loginSnackbar" :timeout="2000" top
                    height="70px" width="450px" color="#7d181c">
            <span style="font-size: 16px;">Please enter your username and password</span>
            <template #action="{ attrs }">
                <v-btn
                    color="blue"
                    text
                    v-bind="attrs"
                    @click="loginSnackbar = false"
                >
                    Close
                </v-btn>
            </template>
        </v-snackbar>
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
            show: false,
            loginSnackbar: false,
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
                this.loginSnackbar = true;
            }
        }
    },
};
</script>
