<template>
    <div id="wrapper">

        <gmap-map id="google-map" v-if="chargingStations" :center="center" :mapTypeControl="false" :zoom="12"
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
            <v-btn fab color="white" ripple @click="toggleFilter" v-blur
            >
                <v-icon size="30px">
                    mdi-magnify
                </v-icon>
            </v-btn>

            <v-scroll-y-reverse-transition>
                <v-card v-show="filterShown" class="mt-3" height="500px" width="300px">
                    <filter-card/>
                </v-card>
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
        await this.axios.get(`${serverprefix}api/chargingStation`).then(resp => {
            this.chargingStations = resp.data;
        });

        this.$gmapApiPromiseLazy().then(() => {
            this.icon = {
                url: require("../assets/markers/orange_marker.png"), // url
                scaledSize: new this.google.maps.Size(50, 50), // scaled size
                origin: new this.google.maps.Point(0,0), // origin
                anchor: new this.google.maps.Point(30, 30) // anchor
            }
        });

        await this.$refs.mapRef.$mapPromise.then(map => {
            this.map = map
        })

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