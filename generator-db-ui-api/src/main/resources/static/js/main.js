import Vue from 'vue'
import app from '../components/app.vue'
import VueFormWizard from 'vue-form-wizard'
import Vuelidate from 'vuelidate'
import store from "./utils/store";

Vue.use(VueFormWizard);
Vue.use(Vuelidate);

new Vue({
    el: '#app',
    store,
    components: {app: app},
    render: function (h) {
        return h('app');
    }
});