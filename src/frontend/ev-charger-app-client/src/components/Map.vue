<template>
    <div>
        <div style="display: flex; align-items: center; justify-content: space-between">
            <div>
                <h1>Your coordinates</h1>
                <p>{{ coordinates.lat }}, {{ coordinates.lng }}</p>
            </div>
            <div>
                <h1>Map coordinates</h1>
                <p>{{ mapCoordinates.lat }}, {{ mapCoordinates.lng }}</p>
            </div>
        </div>

        <google-map
            :center="coordinates"
            :zoom="13"
            style="width: 1500px; height: 700px; margin: 32px auto;"
            ref="mapRef"
            />
    </div>
</template>

<script>
export default {
    name: 'map',

    data() {
        return {
            coordinates: {
                lat: 0,
                lng: 0
            },
            map: null
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
    mounted() {
        this.$refs.mapRef.$mapPromise.then(map => this.map = map);

        this.axios.get("http://localhost:8080/api/chargingStation").then((response) => {
            console.log(response.data)
        })

    }
}
</script>
