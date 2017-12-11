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
            form: ['nowEntity', 'name', 'dataType', 'isUnique', 'isNull']
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