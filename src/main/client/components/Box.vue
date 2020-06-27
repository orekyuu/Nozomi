<template>
  <div class="rounded border border-gray-400">
    <div class="text-lg p-2 border-b border-gray-400">{{ header }}</div>
    <div class="p-2">
      <slot />
    </div>
  </div>
</template>

<script>
export default {
  name: "Box",
  props: {
    header: {
      type: String,
      required: true
    }
  }
}
</script>

<style scoped></style>
