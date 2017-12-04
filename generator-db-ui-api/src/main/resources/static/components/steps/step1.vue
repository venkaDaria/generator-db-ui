<template lang="pug">
    include ../fragments/step1.pug
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
                groupId: 'demo',
                artifactId: 'com.example',
                name: 'demo',
                packageName: 'com.example.demo',
                javaVersion: '1.8'
            }
        },
        validations: {
            groupId: {
                required
            },
            artifactId: {
                required
            },
            name: {
            },
            packageName: {
            },
            javaVersion: {
            },
            form: ['groupId', 'artifactId', 'name', 'packageName', 'javaVersion']
        },
        methods: {
            applyParams: function () {
                if (!this.full) {
                    this.name = this.groupId;
                    this.packageName = this.artifactId + '.' + this.groupId;
                }
            }, validate() {
                this.applyParams();

                this.$v.form.$touch();
                let isValid = !this.$v.form.$invalid;
                this.$emit('on-validate', this.$data, isValid);
                return isValid
            },
            switchVersion: function () {
                this.applyParams();

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