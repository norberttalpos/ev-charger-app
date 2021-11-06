<template>
    <div>
        <v-card v-if="minimalDetails" class="minimal-details pa-5 background" max-width="500px">
            <div>
                <strong style="font-size: 40px;">
                    {{ chargingStation.ownerCompanyName }}
                </strong>
            </div>

            <div style="margin-top: -10px;">
                <br>
                <strong style="font-size: 25px;">{{ "Location: "}}</strong>
                <span style="font-size: 20px;">{{ chargingStation.address }}</span>
            </div>

            <div style="margin-top: -10px;">
                <br>
                <strong style="font-size: 25px;">{{ "Max number of chargers: "}}</strong>
                <span style="font-size: 20px;">{{ chargingStation.maxNumberOfChargers }}</span>
            </div>

            <v-layout class="mt-2" justify-start row wrap>
                <v-img style="margin-left: 10px; border-radius: 5px; border: 1px solid black;" v-for="charger in chargingStation.chargers" :key="charger.id" :src="require(`../assets/chargerTypes/${charger.chargerType.name}.png`)"
                       max-width="50px"/>
            </v-layout>

            <v-layout class="my-5 mt-5" justify-center row wrap>
                <v-btn @click="minimalDetails = false">
                    details
                </v-btn>
            </v-layout>
        </v-card>

        <v-card v-else class="details pa-12 background">
            <div>
                <strong style="font-size: 40px;">
                    {{ chargingStation.ownerCompanyName }}
                </strong>
            </div>

            <div style="margin-top: -10px;">
                <br>
                <strong style="font-size: 25px;">{{ "Location: "}}</strong>
                <span style="font-size: 20px;">{{ chargingStation.address }}</span>
            </div>

            <div style="margin-top: -10px;">
                <br>
                <strong style="font-size: 25px;">{{ "Max number of chargers: "}}</strong>
                <span style="font-size: 20px;">{{ chargingStation.maxNumberOfChargers }}</span>
            </div>

            <v-container class="mt-4" style="width: 500px;">
                <strong style="font-size: 35px;">Chargers</strong>
                <v-data-table
                    :headers="headers"
                    :items="chargingStation.chargers"
                    fixed-header
                    hide-default-footer
                    class="elevation-1 mt-5"
                >
                    <template #item.chargerType="{ item }">
                        <v-tooltip right>
                            <template #activator="{ on, attrs }">
                                <v-img :class="`my-2 image ${darkTheme ? 'darkChargerIcon' : ''}`" :src="require(`../assets/chargerTypes/${item.chargerType.name}.png`)" max-width="50px"
                                       @click="showCarDetails" style="border-radius: 5px; border: 1px solid black;" v-bind="attrs" v-on="on"/>
                            </template>
                            <div style="font-size: 16px;">
                                <p class="mb-0">{{ `name: ${item.chargerType.name}` }}</p>
                                <p class="mb-0">{{ `charging speed: ${item.chargerType.maxChargingSpeed}` }}</p>
                            </div>
                        </v-tooltip>
                    </template>
                    <template #item.reserved="{ item }">

                        <v-menu :close-on-content-click="false" offset-x right v-if="item.currentlyChargingCar !== null" nudge-right="30">
                            <template #activator="{ on }">
                                <v-img class="my-2 image" src="@/assets/reserved_carIcon.png" max-width="40px"
                                       @click="showCarDetails(item.currentlyChargingCar)" v-on="on"></v-img>
                            </template>

                            <v-card v-if="carDetails !== null">
                                <v-card-title class="mb-n4">
                                    Car Details
                                </v-card-title>
                                <v-layout column wrap class="px-4 py-2">
                                    <v-container>
                                        <v-row>
                                            <v-col cols="12">
                                                <span class="mr-3 mb-2" style="font-size: 18px;">
                                                    - battery
                                                </span>
                                                <v-progress-linear
                                                    v-model="carDetails.batteryPercentage"
                                                    color="primary"
                                                    striped
                                                    height="20"
                                                    animate
                                                >
                                                    <template v-slot:default="{ value }">
                                                        <strong>{{ Math.ceil(value) }}%</strong>
                                                    </template>
                                                </v-progress-linear>
                                            </v-col>
                                        </v-row>
                                        <v-row>
                                            <v-col cols="12">
                                                <p class="mr-3 mb-2" style="font-size: 18px;">
                                                    {{ `- license plate:  ${carDetails.licensePlate}` }}
                                                </p>
                                            </v-col>
                                        </v-row>
                                        <v-row>
                                            <v-col cols="12">
                                                <p class="mr-3 mb-4" style="font-size: 18px;">
                                                    {{ `- car type:  ${carDetails.carType.name}` }}
                                                </p>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                    <v-btn class="mr-2 mb-2" color="primary" dark @click="subscribeForLeaving(carDetails)">subscribe for leaving</v-btn>
                                </v-layout>
                            </v-card>

                        </v-menu>

                        <v-img v-else class="my-2 image" src="@/assets/checkmark_green.png" max-width="40px"></v-img>
                    </template>

                </v-data-table>
            </v-container>
        </v-card>
    </div>
</template>

<script>
import {WebsocketClient} from "@/mixins/WebsocketClient";

export default {
    name: "charging-station-details",
    props: {
        chargingStationProp: {Object, default: {}},
    },
    mixins: [WebsocketClient],
    data() {
        return {
            minimalDetails: true,
            chargingStation: {
                address: "",
                ownerCompanyName: "",
                location: {
                    coordinates: {
                        latitude: 0,
                        longitude: 0,
                    }
                },
                chargers: [],
            },
            headers: [
                { text: "ID", value: "id", width: '20%', sortable: false },
                { text: "Charger Type", value: "chargerType", sortable: false },
                { text: "Available", value: "reserved", width: '20%', sortable: false },
            ],
            carDetails: null,
        }
    },
    computed: {
        darkTheme() {
            return this.$vuetify.theme.dark;
        },
    },
    methods: {
        showCarDetails(car) {
            this.carDetails = car;

            this.subscribeForBatteryChange(car);
        },
        reserveCharger(charger) {
            console.log(charger);
        },

        subscribeForBatteryChange(car) {
            const carId = car.id;
            console.log(carId);

            //TODO feliratkozni websocket battery
        },
        subscribeForLeaving(car) {
            const carId = car.id;
            console.log(carId);
            //TODO email ertesites
        },
    },
    mounted() {
        this.chargingStation = this.chargingStationProp;
    }
}
</script>

<style>
    .minimal-details{
        position: fixed;
        top: 250px;
        left: 50%;
        margin-right: -50%;
        transform: translate(-50%, -50%);
    }
    .details {
        position: fixed;
        top: 350px;
        left: 50%;
        margin-right: -50%;
        transform: translate(-50%, -50%);
    }
    .image:hover {
        cursor: pointer;
    }
    .darkChargerIcon {
        filter: invert(100%);
    }

</style>