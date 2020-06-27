<template>
  <div class="h-full w-full flex">
    <div
      class="w-56 h-full bg-gray-200 flex flex-col border-r border-solid border-gray-400 fixed"
    >
      <div class="p-3 flex items-center">
        <p class="font-bold flex-1">{{ project.name }}</p>
        <p class="w-2 h-2 bg-green-400 rounded-full"></p>
      </div>
      <hr class="border-t border-solid border-1 border-gray-400 my-2" />
      <div class="w-full text-center text-xl font-bold">00:00:00</div>
      <Button class="m-3">
        Lock
      </Button>
      <Button class="m-3">
        Extend
      </Button>
      <hr class="border-t border-solid border-1 border-gray-400 my-2" />
      <div class="overflow-y-visible">
        <ul>
          <li class="my-1 py-2 px-1 cursor-pointer hover:bg-gray-300">
            Deployment
          </li>
          <li class="my-1 py-2 px-1 cursor-pointer hover:bg-gray-300">
            Project Configuration
          </li>
          <li class="my-1 py-2 px-1 cursor-pointer hover:bg-gray-300">
            Deployment Log
          </li>
        </ul>
      </div>
    </div>
    <div class="flex-1 relative flex flex-col ml-56">
      <Box header="Select branch" class="m-3">
        <div class="flex">
          <TextInput class="flex-1" v-model="branch" />
          <Button>Checkout</Button>
        </div>
      </Box>
      <Box header="Select environment" class="m-3">
        <Select :options="environments" />
      </Box>
      <Box header="Action" class="m-3">
        <Button class="m-1">Run migration</Button>
        <Button class="m-1">Start deploy</Button>
      </Box>
      <div class="flex-1"></div>
      <Console class="sticky bottom-0 bg-white" />
    </div>
  </div>
</template>

<script>
import Box from "@/components/Box"
import Button from "@/components/Button"
import TextInput from "@/components/TextInput"
import Select from "@/components/Select"
import Console from "@/components/Console"
export default {
  name: "DeploymentView",
  components: { Console, Select, TextInput, Button, Box },
  props: {
    project: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      environments: [
        { id: 1, name: "Preview" },
        { id: 2, name: "Staging" },
        { id: 3, name: "Production" }
      ],
      branch: "origin/master"
    }
  }
}
</script>
