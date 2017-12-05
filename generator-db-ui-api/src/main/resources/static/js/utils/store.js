import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        entities: []
    },
    actions: {
        ADD: function({ commit }, new_entities) {
            commit("ADD_MUTATION", new_entities);
        }
    },
    mutations: {
        ADD_MUTATION: function(state, new_entities) {
            state.entities = new_entities;
        }
    },
    getters: {
        entities: state => {
            return state.entities;
        }
    }
});
