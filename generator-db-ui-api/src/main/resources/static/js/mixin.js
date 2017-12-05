const ValidateMixin = {
    methods: {
        validate() {
            this.$v.form.$touch();
            let isValid = !this.$v.form.$invalid;
            this.$emit('on-validate', this.$data, isValid);
            return isValid
        }
    }
};

export default ValidateMixin;