<template lang="pug">
    include ../fragments/step3.pug
</template>

<script>
    import { required, minLength } from 'vuelidate/lib/validators'
    import ValidateMixin from "../../js/utils/mixin";

    export default {
        name: 'step3',
        data() {
            return {
                fields: [],
                nowEntity: '',
                nameField: '',
                dataType: 'String',
                isNull: true,
                isUnique: false
            }
        },
        validations: {
            nameField: {
                required,
                minLength: minLength(3)

            },
            nowEntity: {
                required
            },
            dataType: {
                required
            },
            isNull: {
            },
            isUnique: {
            },
            form: ['nowEntity', 'nameField', 'dataType', 'isUnique', 'isNull']
        },
        mixins:[ValidateMixin],
        methods: {
            entities() {
                return this.$store.getters.entities;
            },
            addField() {
                let isOkay = this.validate();
                if (isOkay) {
                    this.fields.push({
                        parent: this.nowEntity,
                        name: this.nameField,
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