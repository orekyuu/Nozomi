<template>
  <select
    class="block appearance-none w-full bg-white border border-gray-400 hover:border-gray-500 px-4 py-2 pr-8 rounded shadow leading-tight focus:outline-none focus:shadow-outline"
  >
    <option v-for="option in options" :key="option.id">{{
      option.name
    }}</option>
  </select>
</template>

<script>
export default {
  name: "Select",
  props: {
    options: {
      type: Array,
      required: true
    }
  }
}
</script>
