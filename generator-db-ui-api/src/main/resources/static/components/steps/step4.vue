<template lang="pug">
    include ../fragments/step4.pug
</template>

<script>
    import { required, minLength } from 'vuelidate/lib/validators'
    import ValidateMixin from "../../js/utils/mixin";
    import app from '../app.vue'

    export default {
        name: 'step3',
        data() {
            return {
                bounds: [],
                option1: '',
                option2: '',
                bind: '0'
            }
        },
        validations: {
            option1: {
                required,
            },
            option2: {
                required
            },
            bind: {
            },
            form: ['bind', 'option1', 'option2']
        },
        mixins:[ValidateMixin],
        methods: {
            entities() {
                return this.$store.getters.entities;
            },
            remove(index) {
                this.bounds.splice(index, 1);
            },
            addBound() {
                let isOkay = this.validate();
                if (isOkay) {
                    this.bounds.push({
                        option1: this.option1,
                        option2: this.option2,
                        bind: this.bind
                    });
                } else {

                }
            }
        }
    }
</script>