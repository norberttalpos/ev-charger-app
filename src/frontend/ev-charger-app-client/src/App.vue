<template>
    <v-app>
        <v-app-bar
            app
            color="primary"
            dark
        >
            <div class="d-flex align-center" style="margin-left: 15px;">
                <v-img
                    alt="Vuetify Logo"
                    class="shrink mr-2"
                    contain
                    src="../src/assets/woozy.png"
                    transition="scale-transition"
                    width="40"
                />

                <span style="margin-left: 15px; font-size: 40px; font-family: 'Georama', sans-serif !important;">Töltősch</span>
            </div>

        </v-app-bar>

        <v-main>
            <router-view/>
        </v-main>
    </v-app>
</template>

<script>

export default {
    name: 'App',

    data: () => ({
        //
    }),
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Georama:ital,wght@1,800&display=swap');

::-webkit-scrollbar {
    display: none;
}

</style>
