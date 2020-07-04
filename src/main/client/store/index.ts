import Vue from "vue"
import Vuex from "vuex"
import { deployment } from "@/store/deployment/deployment"
import { RootState } from "@/lib/types"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {} as RootState,
  mutations: {},
  actions: {},
  modules: {
    deployment
  }
})
