<template lang="pug">
    include ../fragments/step2.pug
</template>

<script>
    import { required, minLength } from 'vuelidate/lib/validators'
    import ValidateMixin from "../../js/utils/mixin";

    export default {
        name: 'step2',
        data() {
            return {
                entities: [],
                name: ''
            }
        },
        validations: {
            name: {
                required,
                minLength: minLength(3)

            },
            form: ['name']
        },
        mixins:[ValidateMixin],
        methods: {
            addEntity() {
                let isOkay = this.validate();
                if (isOkay) {
                    this.entities.push({
                        name: this.name,
                    });
                } else {

                }
            }
        }
    }
</script>