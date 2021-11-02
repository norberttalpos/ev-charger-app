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

        <v-layout class="my-5" justify-center row wrap>
            <v-btn @click="minimalDetails = false">
                details
            </v-btn>
        </v-layout>
    </v-card>

    <v-card v-else class="details pa-5 background">
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

        <v-container class="mt-4" style="width: 1000px; height: 500px;">
            <strong style="font-size: 35px;">Chargers</strong>
            <v-data-table
                :headers="headers"
                :items="chargingStation.chargers"
                fixed-header
                hide-default-footer
                class="elevation-1 mt-5"
            >
                <template #item.reserved="{ item }">
                    <v-img class="my-2 car-icon" v-if="item.currentlyChargingCar !== null" src="@/assets/carIcon.jpg" max-width="50px"
                            @click="showCarDetails"></v-img>
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
                { text: "Charger Types", value: "chargerTypes", sortable: false },
                { text: "Reserved", value: "reserved", width: '20%', sortable: false },
            ],
        }
    },
    methods: {
        showCarDetails() {
            console.log('asd');
            this.carDetailsShown = true;
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
        top: 220px;
        left: 50%;
        margin-right: -50%;
        transform: translate(-50%, -50%);
    }
    .details {
        position: fixed;
        top: 50%;
        left: 50%;
        margin-right: -50%;
        transform: translate(-50%, -50%);
    }
    .car-icon:hover {
        cursor: pointer;
    }
</style>