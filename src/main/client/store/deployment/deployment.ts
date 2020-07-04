import { DeploymentState, RootState } from "@/lib/types"
import actions from "@/store/deployment/actions"
import { Module } from "vuex"
import mutations from "@/store/deployment/mutations"
import getters from "@/store/deployment/getters"

const state: DeploymentState = {
  projects: []
}

export const deployment: Module<DeploymentState, RootState> = {
  namespaced: true,
  state,
  actions,
  mutations,
  getters
}
