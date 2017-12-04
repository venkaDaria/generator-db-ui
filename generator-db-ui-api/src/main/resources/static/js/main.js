import Vue from 'vue'
import app from '../components/app.vue'
import VueFormWizard from 'vue-form-wizard'
import Vuelidate from 'vuelidate'

Vue.use(VueFormWizard);
Vue.use(Vuelidate);

new Vue({
    el: '#app',
    components: {app: app},
    render: function (h) {
        return h('app');
    }
});