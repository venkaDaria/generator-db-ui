<template lang="pug">
    .container
        include ../fragments/step1.pug
        label.switcher(v-on:click='switchVersion') Switch to the full version
</template>

<script>
    export default {
        name: 'step1',
        data() {
            return {
                full: true,
                text: 'Switch to the full version'
            }
        },
        methods: {
            switchVersion: function () {
                this.text = this.full ? 'Switch to the short version' : 'Switch to the full version';
                this.full = !this.full;
                $(".full").toggleClass("hidden");
            },
            validate() {
                return true;
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../../css/step1';
</style>