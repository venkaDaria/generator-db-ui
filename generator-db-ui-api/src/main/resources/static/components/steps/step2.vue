<template lang="pug">
    div
        .form-group(v-bind:class="{ 'has-error': $v.country.$error }")
            label Country
            select.form-control(v-model.trim='country', @input='$v.country.$touch()')
                option USA
                option United Kingdom
                option France
            span.help-block(v-if='$v.country.$error && !$v.country.required') Country is required
        .form-group(v-bind:class="{ 'has-error': $v.city.$error }")
            label City
            input.form-control(v-model.trim='city', @input='$v.city.$touch()')
            span.help-block(v-if='$v.city.$error && !$v.city.required') City is required
</template>

<script>
    import { required, minLength, email } from 'vuelidate/lib/validators'

    export default {
        name: 'step2',
        data() {
            return {
                country: '',
                city: ''
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
            }
        }
    }
</script>