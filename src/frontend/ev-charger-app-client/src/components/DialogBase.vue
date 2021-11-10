<template>
    <v-card max-width="400">
        <v-card-title class="text-h5 darkprimary" style="color: white; ">
            {{ title }}
        </v-card-title>

        <v-tabs
            fixed-tabs
            v-if="tabs"
            v-model="tabidx"
            @change="tabidxChanged"
        >
            <v-tab v-for="i in tabItems" :key="i">
                {{ i }}
            </v-tab>
        </v-tabs>

        <v-card class="mx-auto my-3  elevation-0">
            <v-card-text>
                <v-container class="zoom-out">
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
    name: "dialog-base",
    props: {
        title: {String, required: true},
        cancelButtonAction: {Function, required: true},
        saveButtonAction: {Function, required: true},
        tabItems: {Array}
    },
    data() {
        return {
            tabidx: 0,
        }
    },
    computed: {
        tabs() {
            return this.tabItems?.size !== 0;
        }
    },
    methods: {
        tabidxChanged() {
            this.$emit("tabidxChanged", this.tabidx);
        }
    },
}
</script>

<style scoped>

</style>