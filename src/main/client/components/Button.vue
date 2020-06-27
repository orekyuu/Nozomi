<template>
  <button class="bg-blue-500 hover:bg-blue-700 text-white py-1 px-2 rounded">
    <slot />
  </button>
</template>

<script>
export default {
  name: "Button"
}
</script>

<style scoped></style>
