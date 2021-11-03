<template>
    <v-card class="mt-3 pa-5">
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
            <v-row justify="center">
                <v-card class="px-6 pt-6" elevation="2">
                    <v-layout row wrap justify-start>
                        <v-card-title style="font-size: 20px; margin-right: 10px; margin-bottom: 10px;">charger types:</v-card-title>
                        <v-spacer/>
                        <v-btn class="mt-2" color="primary" @click="toggleAllChargerTypes">
                            toggle all
                        </v-btn>
                    </v-layout>
                    <v-col cols="12">
                        <v-row dense no-gutters>
                            <v-col cols="6" class="mb-10" v-for="chargerType in chargerTypes" :key="chargerType">
                                <v-layout row wrap justify-center>
                                    <div :style="chargerTypeStyle(chargerType)">
                                        <v-img class="pa-2 image" style="margin-left: 10px;"
                                               :src="require(`../assets/chargerTypes/${chargerType}.png`)"
                                               max-width="70px" @click="chargerTypeSelected(chargerType)"/>
                                        <span style="margin-left: 5px;">{{ chargerType }}</span>
                                    </div>
                                </v-layout>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-card>
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
            chargerTypes: [
                "Type 2",
                "Type 3",
                "ChaDeMo",
                "Tesla"
            ],
            selectedChargerTypes: [],
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
        },
        chargerTypeSelected(chargerType) {
            if(this.selectedChargerTypes.includes(chargerType)) {
                const index = this.selectedChargerTypes.indexOf(chargerType);
                if (index !== -1) {
                    this.selectedChargerTypes.splice(index, 1);
                }
            }
            else {
                this.selectedChargerTypes.push(chargerType);
            }

            this.debounceSearch(() => this.$emit("filterChanged", {
                chargerTypes: this.selectedChargerTypes,
            }), 400);
        },
        chargerTypeStyle(chargerType) {
            return this.selectedChargerTypes.includes(chargerType) ? "border: solid 3px green; padding: 10px; display: flex; justify-content: center; align-items: center"
                : "padding: 10px; display: flex; justify-content: center; align-items: center"
        },
        toggleAllChargerTypes() {
            if(this.selectedChargerTypes.length !== this.chargerTypes.length) {
                this.selectedChargerTypes = [];
                this.chargerTypes.forEach(i => this.selectedChargerTypes.push(i));
            }
            else {
                this.selectedChargerTypes = [];
            }

            this.debounceSearch(() => this.$emit("filterChanged", {
                chargerTypes: this.selectedChargerTypes,
            }), 400);
        }
    }
}
</script>