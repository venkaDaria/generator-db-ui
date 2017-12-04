<template lang="pug">
    include ../fragments/step2.pug
</template>

<script>
    import { required, minLength, email } from 'vuelidate/lib/validators'

    export default {
        name: 'step2',
        data() {
            return {
                entities: [],
                country: '',
                city: '',
            }
        },
        validations: {
            country: {
                required
            },
            city: {
                required
            },
            form: ['country', 'city']
        },
        methods: {
            validate() {
                this.$v.form.$touch();
                let isValid = !this.$v.form.$invalid;
                this.$emit('on-validate', this.$data, isValid);
                return isValid
            },
            addEntity() {
                let isOkay = this.validate();
                if (isOkay) {
                    this.entities.push({
                        country: this.country,
                        city: this.city
                    });
                } else {

                }
            }
        }
    }
</script>