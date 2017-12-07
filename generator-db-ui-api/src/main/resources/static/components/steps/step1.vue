<template lang="pug">
    include ../fragments/step1.pug
</template>

<script>
    import { required } from 'vuelidate/lib/validators'

    function patternGroup(value) {
        return /^[a-zA-Z].*$/.test(value)
    }

    export default {
        name: 'step1',
        data() {
            return {
                full: true,
                text: 'Switch to the full version',
                // form-data
                groupId: 'com.example',
                artifactId: 'demo',
                name: 'demo',
                packageName: 'com.example.demo',
                javaVersion: '1.8',
                userName: 'root',
                password: 'root',
                dataBase: 'MySQL'
            }
        },
        validations: {
            groupId: {
                required,
                patternGroup
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
            userName: {
            },
            password: {
            },
            dataBase: {
            },
            form: ['dataBase','groupId', 'artifactId', 'name', 'packageName', 'javaVersion', 'userName', 'password', 'dataBase']
        },
        methods: {
            applyParams: function () {
                if (!this.full) {
                    this.name = this.artifactId;
                    this.packageName = this.groupId  + '.' + this.artifactId;
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