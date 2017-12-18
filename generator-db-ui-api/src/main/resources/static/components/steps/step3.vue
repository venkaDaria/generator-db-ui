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
                mainField: 'name'
            }
        },
        validations: {
            nameField: {
                required,
                minLength: minLength(3)
            },
            mainField: {
                required
            },
            nowEntity: {
                required
            },
            dataType: {
                required
            },
            isNull: {
            },
            form: ['mainField','nowEntity', 'nameField', 'dataType', 'isNull']
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
                        isNull: this.isNull
                    });
                } else {

                }
            }, saveMainField() {
                let isOkay = this.validate();
                if (isOkay) {
                    this.fields.forEach(field => {
                        field['isMain'] = field.parent === this.nowEntity && field.name === this.mainField;
                    })
                } else {

                }
            }
        }
    }
</script>