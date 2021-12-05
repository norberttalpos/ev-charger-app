<template>
    <dialog-base :save-button-action="save" :cancel-button-action="cancelAction" title="Add new car" >


        <v-row class="mt-n1">
            <v-col cols="12">
                <v-text-field
                    v-model="licensePlate"
                    label="License plate"
                    outlined
                    name="licensePlate"
                    persistent-placeholder
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row class="mt-n1">
            <v-col cols="12">
                <v-select
                    v-model="carType"
                    label="Type"
                    :items="types"
                    outlined
                    name="carType"
                    persistent-placeholder
                    return-object

                ></v-select>
            </v-col>
        </v-row>


    </dialog-base>

</template>

<script>
import DialogBase from "@/components/DialogBase.vue";

export default {
    name: "NewCarDialog.vue",
    components: {DialogBase},
    data() {
        return {
            carType:"",
            licensePlate: "",
            types:[]
        }
    },


    methods:{
        async created(){
            console.log("alma");
            await this.loadTypes();
        },
        async loadTypes(){
            console.log("alma");
            const types_response = await this.axios.get(`/api/electricCarType`);
            console.log(types_response);
            for (let i = 0; i < types_response.data.length(); i++) {
                console.log(types_response.data[i].name);
                this.types.append(types_response.data[i].name);
            }
        },

        save(){

        },
        closeDialog() {
            this.$emit('close-dialog');
        },
        cancelAction(){
            this.closeDialog();

        }
    }
}
</script>

<style scoped>

</style>