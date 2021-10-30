<template>
    <v-layout row wrap justify-end>
        <v-icon class="mr-2">
            mdi-brightness-4
        </v-icon>
        <v-switch class="mt-5 ml-2" v-model="darkmode" dark/>
    </v-layout>
</template>

<script>
export default {
    name: "dark-mode-toggle",
    data () {
        return {
            darkmode: false
        }
    },
    watch: {
        darkmode () {
            this.handledarkmode()
        }
    },
    created () {
        if (localStorage.getItem('DarkMode')) {
            this.darkmode = localStorage.getItem('DarkMode') === 'true'
        } else {
            this.handledarkmode()
        }
    },
    methods: {
        handledarkmode () {
            if (this.darkmode === true) {
                this.$vuetify.theme.dark = true
                localStorage.setItem('DarkMode', true)
            } else if (this.darkmode === false) {
                this.$vuetify.theme.dark = false
                localStorage.setItem('DarkMode', false)
            }
        }
    }
}
</script>