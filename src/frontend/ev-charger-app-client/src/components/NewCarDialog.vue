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
            types:[],
            typeData:[]
        }
    },

    async created(){
        await this.loadTypes();
    },
    methods:{

        async loadTypes(){
            const types_response = await this.axios.get(`/api/electricCarType`);
            this.typeData=types_response.data;

            this.types = this.typeData.map(i => i.name);
        },

        async save(){
            try{
                const response = await this.axios.post(`/api/electricCar/`, {
                    licensePlate: this.licensePlate,
                    batteryPercentage: 100,
                    carType: this.typeData.filter(i => i.name === this.carType)[0]
                });
                if (response.status === 201) {
                    const newCarId = response.data.id;

                    await this.$store.dispatch("fetchId");
                    const id = this.$store.getters.getId;

                    await this.axios.put(`/api/person/${id}/setCar`, {
                        carId: newCarId,
                    });
                    this.closeDialog();
                }
            }
            catch(error){
                console.log(error);
            }
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