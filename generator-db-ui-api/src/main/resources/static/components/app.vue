<template lang="pug">
    #app
        header.container
            h1
                | DB UI Generator
                small &nbsp;&nbsp;generate your application now
        main.container
            form-wizard(@on-complete='onComplete', color="#a44040")
                h2(slot="title") This will replace my whole title
                tab-content(title='Personal details', icon='fa fa-user', :before-change="()=>validateStep('step1')")
                    step1(ref="step1", @on-validate="mergePartialModels")
                tab-content(title='Additional Info', icon='fa fa-tasks', :before-change="()=>validateStep('step2')")
                    step2(ref="step2", @on-validate="mergePartialModels")
                tab-content(title='Last step', icon='fa fa-check')
                    include fragments/step3.pug
        footer.container
            p (c) Daria Pydorenko, 2017
</template>

<script>
    import axios from 'axios'
    import step1 from '../components/steps/step1.vue'
    import step2 from '../components/steps/step2.vue'

    export default {
        name: 'app',
        data() {
            return {
                finalModel: {}
            }
        },
        components: {
            'step1': step1,
            'step2': step2
        },
        methods: {
            validateStep(name) {
                return this.$refs[name].validate();
            },
            mergePartialModels(model, isValid){
                if(isValid){
                    this.finalModel = Object.assign({},this.finalModel, model)
                }
            },
            onComplete() {
                console.log(this.finalModel);
                // axios
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../css/app';
    @import '../../node_modules/vue-form-wizard/dist/vue-form-wizard.min.css';
</style>