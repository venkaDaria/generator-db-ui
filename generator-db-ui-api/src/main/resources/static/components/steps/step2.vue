<template lang="pug">
    include ../fragments/step2.pug
</template>

<script>
    import { required, minLength } from 'vuelidate/lib/validators'
    import ValidateMixin from "../../js/utils/mixin";

    function firstNotNumber(value) {
        return /^[a-zA-Z].*$/.test(value)
    }

    export default {
        name: 'step2',
        data() {
            return {
                entities: [],
                nameEntity: ''
            }
        },
        validations: {
            nameEntity: {
                required,
                minLength: minLength(3),
                firstNotNumber
            },
            form: ['nameEntity']
        },
        mixins:[ValidateMixin],
        methods: {
            addEntity() {
                let isOkay = this.validate();
                if (isOkay) {
                    this.entities.push({
                        name: this.nameEntity,
                    });
                } else {

                }
            },
            remove(index) {
                this.entities.splice(index, 1);
            }
        }
    }
</script>