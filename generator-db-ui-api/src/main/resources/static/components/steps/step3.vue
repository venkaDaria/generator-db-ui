<template lang="pug">
    include ../fragments/step3.pug
</template>

<script>
    import { required, minLength } from 'vuelidate/lib/validators'
    import ValidateMixin from "../../js/mixin";
    import app from '../app.vue'

    export default {
        name: 'step3',
        data() {
            return {
                options: [],
                option1: '',
                option2: '',
                bind: '',
                entities: this.$root.finalModel.entities
            }
        },
        validations: {
            option1: {
                required,
            },
            option2: {
                required
            },
            form: ['option1', 'option2']
        },
        mixins:[ValidateMixin],
        methods: {
            addBound(idx) {
                let isOkay = this.validate();
                if (isOkay) {
                    this.options.push({
                        option1: this.option1,
                        option2: this.option2,
                        bind: idx
                    });
                } else {

                }
            }
        }
    }
</script>

<style>
    .flex {
        display: flex;
        flex-direction: row;
    }

    .flex-div {
        flex: 1;
        margin: 15px;
    }
</style>