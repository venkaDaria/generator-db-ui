<template lang="pug">
    #app
        header.container
            h1
                | DB UI Generator
                small &nbsp;&nbsp;generate your application now
        main.container
            form-wizard(@on-complete='onComplete', color="#a44040")
                h2(slot="title") Generate project
                tab-content(title='Metadata', icon='fa fa-user', :before-change="()=>validateStep('step1')")
                    step1(ref="step1", @on-validate="mergePartialModels")
                tab-content(title='Entities', icon='fa fa-tasks')
                    step2(ref="step2", @on-validate="mergePartialModels")
                tab-content(title='Fields', icon='fa fa-address-card')
                    step3(ref="step3", @on-validate="mergePartialModels")
                tab-content(title='Bounds', icon='fa fa-link')
                    step4(ref="step4", @on-validate="mergePartialModels")
                tab-content(title='Last step', icon='fa fa-check')
                    include fragments/step5.pug
        footer.container
            p (c) Daria Pydorenko, 2017
</template>

<script>
    import axios from 'axios'
    import step1 from './steps/step1.vue'
    import step2 from './steps/step2.vue'
    import step3 from './steps/step3.vue'
    import step4 from './steps/step4.vue'
    import store from "../js/utils/store";

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
            'step3': step3,
            'step4': step4
        },
        methods: {
            validateStep(name) {
                return this.$refs[name].validate();
            },
            mergePartialModels(model, isValid) {
                if(isValid){
                    this.finalModel = Object.assign({},this.finalModel, model);
                    this.$store.dispatch('ADD', this.finalModel.entities)
                        .catch(err => console.log(err));
                }
            },
            onComplete() {
                let a = document.createElement("a");
                document.body.appendChild(a);
                a.style = "display: none";

                let name = this.finalModel.name + ".zip";

                axios.post('http://localhost:4567/generate', this.finalModel, { responseType: 'blob'})
                    .then(function (response) {
                        console.log(response);
                        let url = window.URL.createObjectURL(response.data);
                        a.href = url;
                        a.download = name;
                        a.click();
                        window.URL.revokeObjectURL(url);
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../css/app';
    @import '../../node_modules/vue-form-wizard/dist/vue-form-wizard.min.css';
</style>