import { DeploymentState, RootState } from "@/lib/types"
import { ActionTree } from "vuex"
import { ApiClient } from "@/lib/api"

const actions: ActionTree<DeploymentState, RootState> = {
  loadProjects: async ({ commit }) => {
    const projects = await new ApiClient().getProjects()
    commit("setProjects", { projects })
    return true
  }
}

export default actions
