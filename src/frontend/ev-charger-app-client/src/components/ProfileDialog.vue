<template>
    <dialog-base :save-button-action="save" :cancel-button-action="cancelAction" title="Profile"
                  :tab-items="['profile', 'car']" @tabidxChanged="tabidxChanged">
        <v-tabs-items v-model="tabsidx">
            <v-tab-item>
                <v-row class="mt-n1">
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
                    </v-col>
                    <v-col cols="6">
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
            </v-tab-item>
            <v-tab-item>
                <v-row>
                    <v-col cols="12">
                        <v-card>
                            <v-card-title class="mainTitle">
                                MY CAR
                            </v-card-title>
                            <v-divider></v-divider>
                            <v-card-title class="carName">
                                {{ carType.name }}
                                <v-img :src="require(`../assets/cars/${carTypeFilename}.png`)" max-width="50px"
                                       style="border-radius: 5px; margin-left: 10px"/>
                            </v-card-title>
                            <v-card-text class="carDetails">
                                License plate: {{ car.licensePlate }}<br>
                                Current battery:
                                <v-col cols="4" justify-center>
                                    <v-progress-linear
                                        :value="car.batteryPercentage"
                                        color="primary"
                                        height="20"
                                        striped
                                        justify-center

                                    >
                                        <template v-slot:default="{ value }">
                                            <strong>{{ Math.ceil(value) }}%</strong>
                                        </template>
                                    </v-progress-linear>

                                </v-col>
                                Compatible charger types:
                                <v-layout class="mt-2" justify-start row wrap>
                                    <div v-for="chargerType in carType.compatibleChargerTypes"
                                         :key="chargerType.id" class="ml-3">
                                        <v-col lass="sm12 md12 lg6 xl6 mb-1" >
                                            <div>
                                                <v-layout column>
                                                    <v-layout row wrap justify-center>
                                                        <v-img :class="`${darkTheme ? 'darkChargerIcon' : ''}`"
                                                               style="border-radius: 5px; border: 1px solid black;margin-left: 10px;"
                                                               :src="require(`../assets/chargerTypes/${chargerType.name}.png`)"
                                                               max-width="70px"/>
                                                    </v-layout>
                                                    <v-layout class="mt-5" row wrap justify-center>
                                                        <span style="margin-left: 5px;">{{ chargerType.name }}</span>
                                                    </v-layout>
                                                </v-layout>
                                            </div>
                                        </v-col>

                                    </div>
                                </v-layout>
                            </v-card-text>

                        </v-card>
                    </v-col>

                </v-row>
            </v-tab-item>
        </v-tabs-items>

        <v-snackbar v-model="snackBar" :timeout="2000" top
                    height="70px" width="450px" color="error">
            <span style="font-size: 16px;">{{ snackbarText }}</span>
            <template #action="{ attrs }">
                <v-btn
                    color="blue"
                    text
                    v-bind="attrs"
                    @click="snackBar = false"
                >
                    Close
                </v-btn>
            </template>
        </v-snackbar>

    </dialog-base>


</template>

<script>

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
            carType: {
                id: null,
                name: "",
            },
            car: {
                licensePlate: "",
            },
            confirm_password: "",
            email: "",
            phoneNumber: "",
            name: "",
            show: false,
            snackBar: false,
            snackbarText: "",
            tabsidx: 0,
            emailRules: [
                v => !v || /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
            ],
            passwordRule: [
                pass => !pass || pass.length >= 6 || "Your password is too short"
            ],
            confirmPasswordRule: [
                pass => !pass || pass.length >= 6 || "Your password is too short"
            ],
            notEmptyRule: [
                t => !(t === null || t === "")
            ]
        }
    },
    computed: {
        darkTheme() {
            return this.$vuetify.theme.dark;
        },
        emptyFields() {
            return this.username === "" || this.username === null || this.password === "" || this.password === null ||
                this.confirm_password === "" || this.confirm_password === null || this.email === "" || this.email === null ||
                this.name === "" || this.name === null || this.phoneNumber === "" || this.phoneNumber === null;
        },
        passwordsAreSame() {
            return this.password === this.confirm_password;
        },
        carTypeFilename() {
            return this.carType.id === null ? "car" : this.carType.name.split(' ')[0].toLowerCase();
        },
    },
    methods: {
        closeDialog() {
            this.$emit('close-dialog');
        },
        cancelAction() {
            this.closeDialog();
        },
        tabidxChanged(idx) {
            this.tabsidx = idx;
        },
        async load_data() {
            const person_response = await this.axios.get(`/api/person/current-person`);
            console.log(person_response);
            if (person_response.status === 200) {
                this.name = person_response.data.name;
                this.email = person_response.data.email;
                this.username = person_response.data.username;
                this.phoneNumber = person_response.data.phoneNumber;
                this.carType = person_response.data.car.carType;
                this.car = person_response.data.car;
            }
        },
        async save() {
            if (!this.passwordsAreSame) {
                this.snackBar = true;
                this.snackbarText = "The passwords don't match";
                return;
            }

            try {
                await this.$store.dispatch("fetchId");

                const id = this.$store.getters.getId;
                console.log(id);

                const response = await this.axios.put(`/api/person/${id}`, {
                    password: this.password,
                    username: this.username,
                    name: this.name,
                    email: this.email,
                    phoneNumber: this.phoneNumber,
                    id: id
                });
                this.password = "";
                this.confirm_password = "";
                console.log(response);

                if (response.status === 200) {

                    this.snackBar = true;
                    this.snackbarText = "Kész";

                    this.closeDialog();
                } else {
                    this.snackBar = true;
                    this.snackbarText = 'Not valid data!';
                }

            } catch (error) {
                this.snackBar = true;
                this.snackbarText = 'Not valid data!';
                console.log("catch error")
                console.log(error)
            }

        }
    },
    async created() {
        await this.load_data();
    },
};
</script>

<style>
.v-input {
    font-size: 1.6em;
}

.mainTitle {
    font-family: 'Roboto', sans-serif;
    font-size: 2.5em;
    letter-spacing: -1px;
    line-height: 1;
    text-align: center;
}

.carName {
    font-family: 'Roboto';
}

.carDetails {
    font-family: 'Roboto';
}

</style>
