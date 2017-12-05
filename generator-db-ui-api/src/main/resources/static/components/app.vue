<template lang="pug">
    #app
        header.container
            h1
                | DB UI Generator
                small &nbsp;&nbsp;generate your application now
        main.container
            form-wizard(@on-complete='onComplete', color="#a44040")
                h2(slot="title") Generate project
                tab-content(title='Personal details', icon='fa fa-user', :before-change="()=>validateStep('step1')")
                    step1(ref="step1", @on-validate="mergePartialModels")
                tab-content(title='Additional Info', icon='fa fa-tasks')
                    step2(ref="step2", @on-validate="mergePartialModels")
                tab-content(title='Additional Info', icon='fa fa-link')
                    step3(ref="step3", @on-validate="mergePartialModels")
                tab-content(title='Last step', icon='fa fa-check')
                    include fragments/step4.pug
        footer.container
            p (c) Daria Pydorenko, 2017
</template>

<script>
    import axios from 'axios'
    import step1 from '../components/steps/step1.vue'
    import step2 from '../components/steps/step2.vue'
    import step3 from '../components/steps/step3.vue'

    export default {
        name: 'app',
        data() {
            return {
                finalModel: {}
            }
        },
        components: {
            'step1': step1,
            'step2': step2,
            'step3': step3
        },
        methods: {
            getEntities() {
                return this.finalModel.entities;
            },
            validateStep(name) {
                return this.$refs[name].validate();
            },
            mergePartialModels(model, isValid) {
                if(isValid){
                    this.finalModel = Object.assign({},this.finalModel, model)
                }
            },
            onComplete() {
                console.log(this.finalModel);
                axios.post('localhost:4567/generate', this.finalModel)
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../css/app';
    @import '../../node_modules/vue-form-wizard/dist/vue-form-wizard.min.css';
</style>