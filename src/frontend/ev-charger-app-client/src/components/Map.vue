<template>
    <div id="wrapper">

        <div id="over-map">
            <div>
                <h1>Your coordinates</h1>
                <p>{{ coordinates.lat }}, {{ coordinates.lng }}</p>
            </div>
            <div>
                <h1>Map coordinates</h1>
                <p>{{ mapCoordinates.lat }}, {{ mapCoordinates.lng }}</p>
            </div>
        </div>

        <gmap-map
            id="google-map"
            v-if="chargingStations"
            :center="coordinates"
            :zoom="13"
            ref="mapRef"
        >
            <gmap-marker
                v-for="(c, index) in chargingStations"
                :key="index"
                :ref="`marker${index}`"
                :position="{lat: c.location.coordinates.latitude, lng: c.location.coordinates.longitude}"
                :clickable="true"
                :icon="icon"
                @click="markerClickedHandler(c)"
            />
        </gmap-map>
    </div>
</template>

<script>
import {serverprefix} from "@/main";
import * as VueGoogleMaps from 'vue2-google-maps';

export default {
    name: 'map',

    data() {
        return {
            coordinates: {
                lat: 0,
                lng: 0
            },
            chargingStations: null,
            map: null,
            icon: null,
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
        }
    },
    created() {
        this.$getLocation({})
            .then(resp => {
                this.coordinates = resp;
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

        await this.$refs.mapRef.$mapPromise.then(map => this.map = map);

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
    #over-map {
        position: absolute;
        top: 10px;
        right: 150px;
        width: 700px;
        background: green !important;
        z-index: 99;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    #google-map {
        position: absolute;
        width: 100%;
        height: 100%;
    }
</style>