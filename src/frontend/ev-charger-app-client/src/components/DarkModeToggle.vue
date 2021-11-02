<template>
    <v-layout row wrap justify-end>
        <v-icon class="mr-5" @click="toggleDarkMode" dark>
            mdi-brightness-4
        </v-icon>
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
        },
        toggleDarkMode() {
            this.darkmode = !this.darkmode;
        }
    }
}
</script>