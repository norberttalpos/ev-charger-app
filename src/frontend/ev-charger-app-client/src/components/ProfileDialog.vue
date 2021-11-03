<template>
    <dialog-base :save-button-action="save" :cancel-button-action="dialog = false" title="Profile">
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
    </dialog-base>

</template>

<script>

import {serverprefix} from "@/main";
import DialogBase from "@/components/DialogBase.vue";

export default {
    name: 'profile-dialog',
    components: {DialogBase},
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
    async mounted() {
        const person_response = await this.axios.get(`${serverprefix}/api/person/current-person`);
        console.log(person_response);
        if(person_response.status===200){
            this.name=person_response.data.name;
            this.email=person_response.data.email;
            this.username=person_response.data.username;
            this.phoneNumber=person_response.data.phoneNumber;

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
        async save() {
            console.log("lofasz");
            return;
/*            if(this.emptyFields){
                this.signUpSnackBar=true;
                this.snackbarText="Please enter all your data";
                return;
            }
            if(!this.passwordsAreSame){
                this.signUpSnackBar=true;
                this.snackbarText="The passwords don't match"
            }


            try {
                const response = await this.axios.post(`${serverprefix}/api/person/register`, {
                    password: this.password,
                    username: this.username,
                    name:this.name,
                    email:this.email,
                    phoneNumber:this.phoneNumber
                });

                if(response.status===201){
                    const login_response = await this.axios.post(`${serverprefix}/api/login`,{
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
            }*/

        }
    },
};
</script>

<style>

.rounded-card {
    border-radius: 15px !important;
}

.v-input {
    font-size: 1.6em;
}
</style>