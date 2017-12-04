<template lang="pug">
    .container
        include ../fragments/step1.pug
        label.switcher(v-on:click='switchVersion') Switch to the full version
</template>

<script>
    import { required, minLength, email } from 'vuelidate/lib/validators'

    export default {
        name: 'step1',
        data() {
            return {
                full: true,
                text: 'Switch to the full version',
                // form-data
                groupId: '',
                artifactId: '',
                name: '',
                packageName: '',
                javaVersion: ''
            }
        },
        validations: {
            groupId: {
                required
            },
            artifactId: {
                required
            },
            form: ['groupId', 'artifactId', 'name', 'packageName', 'javaVersion']
        },
        methods: {
            validate() {
                this.$v.form.$touch();
                let isValid = !this.$v.form.$invalid;
                this.$emit('on-validate', this.$data, isValid);
                //TODO: return isValid
                return true
            },
            switchVersion: function () {
                this.text = this.full ? 'Switch to the short version' : 'Switch to the full version';
                this.full = !this.full;
                $(".full").toggleClass("hidden");
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../../css/step1';
</style>