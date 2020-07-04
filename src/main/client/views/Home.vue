<template>
  <div class="w-full h-full flex flex-row">
    <div
      class="w-56 bg-gray-200 h-screen flex flex-col border-r border-solid border-gray-400 fixed"
    >
      <div class="h-40 overflow-y-visible">
        <ul>
          <li
            v-for="p in projects"
            :key="p.name"
            class="flex items-center m-1 p-1 hover:bg-gray-400 text-base text-gray-800 cursor-pointer antialiased"
          >
            <img
              src="https://pbs.twimg.com/profile_images/1117404785399877632/enfsr__r_400x400.png"
              class="object-cover w-8 mr-3 rounded-full overflow-hidden"
            />
            {{ p.name }}
          </li>
        </ul>
      </div>
      <hr class="border-t border-solid border-1 border-gray-400" />
      <div class="overflow-y-visible flex-1">
        <ul>
          <li
            v-for="p in projects"
            :key="p.name"
            class="flex items-center m-1 p-1 hover:bg-gray-400 text-base text-gray-800 cursor-pointer"
          >
            <img
              src="https://pbs.twimg.com/profile_images/1117404785399877632/enfsr__r_400x400.png"
              class="object-cover w-8 mr-3 rounded-full overflow-hidden"
            />
            {{ p.name }}
          </li>
        </ul>
      </div>
    </div>
    <div class="flex-1 bg-white ml-56">
      <DeploymentView v-if="selected" :project="selected" />
      <HelloDeployment v-else />
    </div>
  </div>
</template>

<script>
import HelloDeployment from "@/components/HelloDeployment"
import DeploymentView from "@/components/DeploymentView"
import { mapActions, mapGetters } from "vuex"

export default {
  name: "Home",
  components: { DeploymentView, HelloDeployment },
  computed: {
    selected() {
      if (this.projects.length == 0) {
        return null
      }
      return this.projects[0]
    },
    ...mapGetters("deployment", ["projects"])
  },
  methods: {
    ...mapActions("deployment", ["loadProjects"])
  },
  mounted() {
    this.loadProjects()
  }
}
</script>
