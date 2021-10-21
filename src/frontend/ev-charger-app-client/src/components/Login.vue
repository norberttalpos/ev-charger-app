<template>
    <div class="login" style="position: absolute; height: 100%; width: 100%;">
        <v-card class="mx-auto rounded-card" max-width="400" height="420" style="margin-top: 150px;">
            <v-card-title class="justify-center py-8">
                <span style="font-size: 35px;">Login</span>
            </v-card-title>
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
                    height="70px" width="450px" color="error">
            <span style="font-size: 16px;">{{ snackbarText }}</span>
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
    name: 'login',
    components: {},
    props: {
        redirectionReason: {String, default: null}
    },
    data() {
        return {
            username:"",
            password:"",
            show: false,
            loginSnackbar: false,
            snackbarText: "",
        }
    },
    computed: {
        emptyFields() {
            return this.username === "" || this.username === null || this.password === "" || this.password === null;
        }
    },
    methods: {
        async login() {
            if(this.emptyFields)
                return;
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
                this.snackbarText = 'Please enter your valid username and password';
            }
        }
    },
};
</script>

<style>
    .login {
        background: rgb(106,189,128);
        background: linear-gradient(155deg, rgba(106,189,128,1) 0%, rgba(58,239,255,1) 100%);
    }
    .rounded-card{
        border-radius: 15px !important;
    }
    .v-input {
        font-size: 1.6em;
    }
</style>
