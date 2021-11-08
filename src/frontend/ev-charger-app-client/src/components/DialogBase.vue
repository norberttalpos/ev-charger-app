<template>
    <v-card>
        <v-card-title class="text-h5 darkprimary" style="color: white; ">
            {{ title }}
        </v-card-title>

        <v-card class="mx-auto my-3  elevation-0" max-width="800">
            <v-card-text>
                <v-container>
                    <slot name="default"/>
                </v-container>
            </v-card-text>
        </v-card>


        <v-divider></v-divider>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                color="secondary"
                text
                @click="cancelButtonAction"
            >
                cancel
            </v-btn>

            <v-btn
                color="primary"
                text
                @click="saveButtonAction"
            >
                save
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    props: {
        title: {String, required: true},
        cancelButtonAction: {Function, required: true},
        saveButtonAction: {Function, required: true},
    },
    name: "dialog-base"
}
</script>

<style scoped>

</style>