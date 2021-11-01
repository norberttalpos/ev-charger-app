
<template>
    <v-layout row wrap justify-end>
        <v-icon class="mr-2" v-if="logOutAvailable()"
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
    methods:{
        log_out(){
            localStorage.setItem('accessToken', null);
            router.push('/login');
        },
        logOutAvailable(){
            const path=this.$router.history.current["path"];
            console.log(path);
            return !( path==="/login" || path==="/sign-up");
        }
    }
}
</script>
