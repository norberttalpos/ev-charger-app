
<template>
    <v-layout row wrap justify-end v-if="logOutAvailable">
        <v-icon class="mr-2"
        @click="log_out">
            mdi-logout
        </v-icon>
    </v-layout>
</template>

<script>
import router from "@/router";

export default {
    name: "logout",
    data () {
        return {
        }
    },
    computed:{
        logOutAvailable() {
            console.log("lofasz");
            const path=this.$route.name;
            console.log(path);
            return !( path==="Login" || path==="Sign Up");
        }
    },
    methods:{
        log_out(){
            localStorage.setItem('accessToken', null);
            router.push('/login');
        }
    }
}
</script>
