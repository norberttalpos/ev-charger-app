<template>
    <div :class="`${this.$vuetify.theme.dark ? 'background-purple' : 'background-green'}`" style="position: absolute; height: 100%; width: 100%;">
        <v-container style="overflow-y: scroll; height: 100%;">
            <v-row no-gutters justify="center">
                <v-col cols="8" >
                    <v-card class="mx-auto my-3 rounded-card" max-width="800">
                        <v-card-title class="justify-center py-8">
                            <span style="font-size: 35px;">Sign up</span>
                        </v-card-title>
                        <v-card-title class="justify-center py-8">
                            <span style="font-size: 20px;">Create your account for Töltősch</span>
                        </v-card-title>
                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-col cols="12">
                                        <v-text-field
                                            v-model="name"
                                            label="Name"
                                            :rules="notEmptyRule"
                                            outlined
                                            name="confirm_password"
                                            persistent-placeholder
                                        ></v-text-field>
                                    </v-col>
                                </v-row>

                                <v-row>

                                    <v-col cols="12">
                                        <v-text-field
                                            v-model="email"
                                            :rules="emailRules"
                                            label="Email address"
                                            outlined
                                            name="email"
                                            persistent-placeholder
                                        ></v-text-field>
                                    </v-col>

                                </v-row>
                                <v-row>
                                    <v-col cols="6">
                                        <v-text-field
                                            v-model="username"
                                            label="Username"
                                            :rules="notEmptyRule"
                                            outlined
                                            name="username"
                                            persistent-placeholder
                                        ></v-text-field>
                                    </v-col> <v-col cols="6">
                                    <v-text-field
                                        v-model="phoneNumber"
                                        label="Phone Number"
                                        :rules="notEmptyRule"
                                        outlined
                                        name="phoneNumber"
                                        persistent-placeholder
                                    ></v-text-field>
                                </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="6">
                                        <v-text-field
                                            v-model="password"
                                            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                                            label="Password"
                                            outlined
                                            :type="show ? 'text' : 'password'"
                                            :rules="passwordRule"
                                            @click:append="show = !show"
                                            name="password"
                                            persistent-placeholder
                                        ></v-text-field>
                                    </v-col>
                                    <v-col cols="6">
                                        <v-text-field
                                            v-model="confirm_password"
                                            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                                            label="Confirm password"
                                            outlined
                                            :rules="confirmPasswordRule"
                                            :type="show ? 'text' : 'password'"
                                            @click:append="show = !show"
                                            name="confirm_password"
                                            persistent-placeholder
                                        ></v-text-field>
                                    </v-col>

                                </v-row>
                                <v-row>
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
                                @click="sign_up"
                                style="background-color: green;"
                                width="240px"
                                height="40px"
                            >
                                <span style="color: white">CREATE YOUR ACCOUNT</span>
                            </v-btn>
                        </v-card-actions>
                        <v-card-text>
                            <v-card-title class="justify-center py-8">
                                <span style="font-size: 16px; margin-right: 20px;">Already have an account?</span>
                                <a href="login"> <span style="font-size: 20px;">Log in</span></a>
                            </v-card-title>

                        </v-card-text>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>

        <v-snackbar v-model="signUpSnackBar" :timeout="2000" top
                    height="70px" width="450px" color="error">
            <span style="font-size: 16px;">{{ snackbarText }}</span>
            <template #action="{ attrs }">
                <v-btn
                    color="blue"
                    text
                    v-bind="attrs"
                    @click="signUpSnackBar = false"
                >
                    Close
                </v-btn>
            </template>
        </v-snackbar>

    </div>
</template>
<script>

import router from '../router'

export default {
    name: 'sign-up',
    components: {},
    props: {
        redirectionReason: {String, default: null}
    },
    data() {
        return {
            username: "",
            password: "",
            confirm_password: "",
            email: "",
            phoneNumber:"",
            name:"",
            show: false,
            signUpSnackBar: false,
            snackbarText: "",
            emailRules: [
                v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
            ],
            passwordRule:[
                pass => !pass||pass.length>=6 || "Your password is too short"
            ],
            confirmPasswordRule:[
                pass => !pass||pass.length>=6 || "Your password is too short"
            ],
            notEmptyRule:[
                t=> !(t===null || t==="")
            ]
        }
    },
    computed: {
        emptyFields() {
            return this.username === "" || this.username === null || this.password === "" || this.password === null ||
                this.confirm_password === "" || this.confirm_password === null || this.email === "" || this.email === null ||
                this.name === "" || this.name === null || this.phoneNumber==="" || this.phoneNumber===null;
        },
        passwordsAreSame(){
            return this.password===this.confirm_password;
        }
    },
    methods: {
        async sign_up() {
            if(this.emptyFields){
                this.signUpSnackBar=true;
                this.snackbarText="Please enter all your data";
                return;
            }
            if(!this.passwordsAreSame){
                this.signUpSnackBar=true;
                this.snackbarText="The passwords don't match"
            }


            try {
                const response = await this.axios.post(`/api/person/register`, {
                    password: this.password,
                    username: this.username,
                    name:this.name,
                    email:this.email,
                    phoneNumber:this.phoneNumber
                });

                if(response.status===201){
                    const login_response = await this.axios.post(`/api/login`,{
                        password:this.password,
                        username:this.username
                    });

                    if(login_response.data?.accessToken) {

                        localStorage.setItem('accessToken', login_response.data.accessToken);

                        router.push('/map');
                    }
                }

                else{
                    this.loginSnackbar = true;
                    this.snackbarText = 'Not valid data!';
                }

            } catch (error) {
                this.loginSnackbar = true;
                this.snackbarText = 'Not valid data!';
            }

        }
    },
};
</script>

<style>
.v-input {
    font-size: 1.6em;
}
</style>
