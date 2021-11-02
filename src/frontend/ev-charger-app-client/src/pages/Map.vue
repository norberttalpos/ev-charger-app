<template>
    <div id="wrapper">
        <v-progress-linear
            indeterminate
            color="green"
            v-show="progressbar"
            height="5px"
            absolute
            dark
        ></v-progress-linear>

        <gmap-map id="google-map" v-if="chargingStations" :center="userCoordinates" :mapTypeControl="false" :zoom="12"
                  ref="mapRef">
            <gmap-marker
                v-for="(c, index) in chargingStations"
                :key="index"
                :ref="`marker${index}`"
                :position="{lat: c.location.coordinates.latitude, lng: c.location.coordinates.longitude}"
                :clickable="true"
                :icon="icon"
                @click="markerClickedHandler(c)"
            />
            <gmap-marker
                :ref="`markerCurr`"
                :position="{lat: userCoordinates.lat, lng: userCoordinates.lng}"
                :icon="{url: require('../assets/carIcon.jpg'),
                        scaledSize: new google.maps.Size(50, 50),
                        origin: new google.maps.Point(0,0),
                        anchor: new google.maps.Point(30, 30)}"
            />
        </gmap-map>

        <div class="over-map" style="top: 30px; left: 30px;">
            <v-btn fab ripple :color="!$vuetify.theme.dark ? '#ffffff' : '#212121'" @click="toggleFilter" v-blur
                   :dark="$vuetify.theme.dark"
            >
                <v-icon size="30px">
                    mdi-magnify
                </v-icon>
            </v-btn>

            <v-scroll-y-reverse-transition>
                <filter-card v-show="filterShown" @filterChanged="filterChangedHandler"/>
            </v-scroll-y-reverse-transition>

        </div>
    </div>
</template>

<script>
import {serverprefix} from "@/main";
import * as VueGoogleMaps from 'vue2-google-maps';
import FilterCard from "@/components/FilterCard";

export default {
    name: 'map',
    components: {FilterCard},
    data() {
        return {
            center: {
                lat: 47.5181,
                lng: 19.0829
            },
            userCoordinates: {
                lat: 0,
                lng: 0
            },
            chargingStations: null,
            map: null,
            icon: null,
            filterShown: false,
            progressbar: false,

            chargingStationFilter: {
                chargerTypes: [
                ],
                maxNumberOfChargers: null,
                ownerCompanyName: null,
                point: {
                    latitude: null,
                    longitude: null
                },
                radius: null,
            }
        }
    },
    computed: {
        mapCoordinates() {
            if(!this.map) {
                return {
                    lat: 0,
                    lng: 0
                }
            }
            return {
                lat: this.map.getCenter().lat().toFixed(4),
                lng: this.map.getCenter().lng().toFixed(4),
            }
        },
        google: VueGoogleMaps.gmapApi
    },
    methods: {
        markerClickedHandler(chargingStation) {
            console.log(chargingStation);
        },
        toggleFilter() {
            this.filterShown = !this.filterShown;
        },
        filterChangedHandler(filter) {
            this.chargingStationFilter.ownerCompanyName = filter.ownerCompanyName;
            this.chargingStationFilter.point.latitude = this.userCoordinates.lat;
            this.chargingStationFilter.point.longitude = this.userCoordinates.lng;

            if(filter.radius !== null && filter.radius !== undefined) {
                if(filter.radius === 0) {
                    filter.radius = 1;
                }
                this.chargingStationFilter.radius = filter.radius * 1000;
            }

            this.progressbar = true;
            setTimeout(() => {this.progressbar = false}, 1000)
            this.getChargingStations()
        },
        async getChargingStations() {
            await this.axios.post(`${serverprefix}/api/chargingStation/search`, this.chargingStationFilter).then(resp => {
                this.chargingStations = resp.data;
            });
        }
    },
    created() {
        this.$getLocation({})
            .then(resp => {
                this.userCoordinates = resp;
            })
            .catch(error => {
                alert(error);
            })
    },
    async mounted() {
        await this.getChargingStations();

        await this.$store.dispatch('fetchRole');

        this.$gmapApiPromiseLazy().then(() => {
            this.icon = {
                url: require("../assets/markers/orange_marker.png"), // url
                scaledSize: new this.google.maps.Size(50, 50), // scaled size
                origin: new this.google.maps.Point(0,0), // origin
                anchor: new this.google.maps.Point(30, 30) // anchor
            }
        })
        .catch(error => {console.log(error)});

        await this.$refs.mapRef.$mapPromise.then(map => {
            this.map = map
        }).catch(error => {console.log(error)});

        this.google.maps.event.addListener(this.map, 'mousemove', () => {
            this.map.setOptions({ draggableCursor: 'default' });
        });
    }
}
</script>

<style>
    #wrapper {
        position: relative;
        width: 100%;
        height: 100%;
    }
    .over-map {
        position: absolute;
        z-index: 99;
    }
    #google-map {
        position: absolute;
        width: 100%;
        height: 100%;
    }
    .gm-style-mtc {
        display: none;
    }
    .gm-svpc {
        display: none;
    }
    .gm-fullscreen-control {
        display: none;
    }
    .slide-fade-enter-active {
        transition: all 2s ease;
    }

</style>