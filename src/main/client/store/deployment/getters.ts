import { GetterTree } from "vuex"
import { DeploymentState, RootState } from "@/lib/types"

const getters: GetterTree<DeploymentState, RootState> = {
  projects: state => {
    return state.projects
  }
}

export default getters
