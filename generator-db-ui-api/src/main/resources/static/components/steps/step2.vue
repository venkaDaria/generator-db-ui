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
                name: '',
                dataType: 'String',
                isNull: true,
                isUnique: false
            }
        },
        validations: {
            name: {
                required,
                minLength: minLength(3)

            },
            dataType: {
                required
            },
            isNull: {
            },
            isUnique: {
            },
            form: ['name', 'dataType', 'isUnique', 'isNull']
        },
        mixins:[ValidateMixin],
        methods: {
            addEntity() {
                let isOkay = this.validate();
                if (isOkay) {
                    this.entities.push({
                        name: this.name,
                        dataType: this.dataType,
                        isUnique: this.isUnique,
                        isNull: this.isNull
                    });
                } else {

                }
            }
        }
    }
</script>