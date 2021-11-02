<template>
    <v-card v-if="minimalDetails" class="minimal-details pa-5 background" max-width="500px">
        <div>
            <strong style="font-size: 40px;">
                {{ chargingStation.ownerCompanyName }}
            </strong>
        </div>

        <div style="margin-top: -10px;">
            <br>
            <strong style="font-size: 25px;">{{ "Location: "}}</strong>
            <span style="font-size: 20px;">{{ chargingStation.location.coordinates.latitude + ", " + chargingStation.location.coordinates.longitude }}</span>
        </div>

        <div style="margin-top: -10px;">
            <br>
            <strong style="font-size: 25px;">{{ "Max number of chargers: "}}</strong>
            <span style="font-size: 20px;">{{ chargingStation.maxNumberOfChargers }}</span>
        </div>

        <v-layout class="mt-2" justify-start row wrap>
            <v-img style="margin-left: 10px;" v-for="charger in chargingStation.chargers" :key="charger" :src="require(`../assets/chargerTypes/${charger.chargerType.name}.png`)"
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
            <span style="font-size: 20px;">{{ chargingStation.location.coordinates.latitude + ", " + chargingStation.location.coordinates.longitude }}</span>
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
                    <v-img class="my-2 image" :src="require(`../assets/chargerTypes/${item.chargerType.name}.png`)" max-width="50px"
                         @click="showCarDetails"/>
                </template>
                <template #item.reserved="{ item }">
                    <v-img class="my-2 image" v-if="item.currentlyChargingCar !== null" src="@/assets/reserved_carIcon.png" max-width="40px"
                            @click="showCarDetails"></v-img>

                    <v-menu offset-x right v-else nudge-right="15">
                        <template v-slot:activator="{ on }">
                            <v-img class="my-2 image" src="@/assets/checkmark_green.png" max-width="40px"
                                   v-on="on"></v-img>
                        </template>
                        <v-list>
                            <v-list-item class="image" @click="reserveCharger(item)">
                                <v-list-item-title>Reserve</v-list-item-title>
                            </v-list-item>
                        </v-list>

                    </v-menu>
                </template>
            </v-data-table>

        </v-container>
    </v-card>
</template>

<script>
export default {
    name: "charging-station-details",
    props: {
        chargingStationProp: {Object, default: {}},
    },
    data() {
        return {
            minimalDetails: true,
            chargingStation: {
                ownerCompanyName: "",
                location: {
                    coordinates: {
                        latitude: 0,
                        longitude: 0,
                    }
                },
                chargers: [],
                carDetailsShown: false,
            },
            headers: [
                { text: "ID", value: "id", width: '20%', sortable: false },
                { text: "Charger Type", value: "chargerType", sortable: false },
                { text: "Available", value: "reserved", width: '20%', sortable: false },
            ],
        }
    },
    methods: {
        showCarDetails() {
            this.carDetailsShown = true;
        },
        reserveCharger(charger) {
            console.log(charger);
        }
    },
    mounted() {
        console.log(this.chargingStationProp)
        this.chargingStation = this.chargingStationProp;
    }
}
</script>

<style scoped>
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
</style>