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
                        style="font-size: 20px;"
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
            <v-row justify="center" no-gutters dense class="mt-5">
                <v-card class="px-6 pt-6" style="max-width: 600px;" elevation="2" outlined>
                    <v-layout row wrap justify-start>
                        <v-card-title style="font-size: 20px; margin-right: 10px; margin-bottom: 10px;">charger types:</v-card-title>
                        <v-spacer/>
                        <v-btn class="mt-2" color="primary" @click="toggleAllChargerTypes">
                            toggle all
                        </v-btn>
                    </v-layout>
                    <v-col cols="12">
                        <v-row dense no-gutters>
                            <v-col cols="6" class="mb-8" v-for="chargerType in chargerTypes" :key="chargerType">
                                <v-layout justify-center align-center row wrap>
                                    <div class="image px-6" :style="chargerTypeStyle(chargerType)" @click="chargerTypeSelected(chargerType)">
                                        <v-img :class="`${darkTheme ? 'darkChargerIcon' : ''}`" style="margin-left: 10px;"
                                               :src="require(`../assets/chargerTypes/${chargerType}.png`)"
                                               max-width="70px"/>
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
    computed: {
        darkTheme() {
            return this.$vuetify.theme.dark;
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
            const base = 'display: flex; justify-content: center; align-items: center; height: 160px; border-radius: 5px; '
            return this.selectedChargerTypes.includes(chargerType) ?
                base + 'border: solid 3px green;'
                : base + 'border: solid 3px transparent;';
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