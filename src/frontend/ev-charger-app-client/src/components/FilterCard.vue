<template>
    <v-card class="mt-3 pa-5" height="500px" max-width="400px;">
        <v-container>
            <v-row>
                <v-col cols="12" class="pb-0">
                    <v-text-field
                        v-model="searchField"
                        solo
                        persistent-placeholder
                        placeholder="company name"
                        style="font-size: 20px; max-width: 280px;"
                        clearable
                        prepend-inner-icon="mdi-magnify"
                        @input="searchFieldHandler"
                    ></v-text-field>
                </v-col>
                <v-col cols="12" class="py-0">
                    <v-layout>
                        <span style="font-size: 20px; margin-right: 10px;">range:</span>
                        <v-slider
                            v-model="slider"
                            max="20"
                            thumb-color="green"
                            color="green"
                            track-color="green"
                            :thumb-label="true"
                            @input="rangeSliderHandler"
                        ></v-slider>
                    </v-layout>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
</template>

<script>
import {DebounceSearch} from "@/mixins/DebounceSearch";

export default {
    name: "filter-card",
    mixins: [DebounceSearch],
    data() {
        return {
            debounce: null,
            searchField: null,
            slider: 10,
        }
    },
    methods: {
        searchFieldHandler() {
            this.debounceSearch(() => this.$emit("filterChanged", {
                ownerCompanyName: this.searchField
            }), 400);
        },
        rangeSliderHandler() {
            this.debounceSearch(() => this.$emit("filterChanged", {
                radius: this.slider
            }), 400);
        }
    }
}
</script>

<style scoped>

</style>