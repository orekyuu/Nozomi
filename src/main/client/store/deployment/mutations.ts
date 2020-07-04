import { MutationTree } from "vuex"
import { DeploymentState } from "@/lib/types"

const mutations: MutationTree<DeploymentState> = {
  setProjects: (state, { projects }) => {
    state.projects = projects
  }
}

export default mutations
