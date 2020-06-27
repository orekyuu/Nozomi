<template>
  <div class="w-full rounded border border-gray-400">
    <div class="flex flex-row border-b border-gray-400">
      <div class="tab" v-for="tab in tabs" :key="tab.name">{{ tab.name }}</div>
      <div class="flex-1"></div>
      <div>x</div>
    </div>
    <div class="min-h-48">
      <ul class="h-48 overflow-y-visible">
        <li v-for="log in selectedTab.logs" :key="log">{{ log }}</li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "Console",
  data() {
    return {
      isOpen: true,
      tabs: [
        { name: "Run migration (Preview)", logs: ["aaaaaaaaa", "bbbbbbbbbbbb"] }
      ],
      selectedTabIndex: 0
    }
  },
  computed: {
    selectedTab() {
      return this.tabs[this.selectedTabIndex]
    }
  }
}
</script>

<style scoped></style>
