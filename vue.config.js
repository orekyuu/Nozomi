// eslint-disable-next-line @typescript-eslint/no-var-requires
const path = require("path")

module.exports = {
  configureWebpack: {
    resolve: {
      alias: {
        "@": path.join(__dirname, "/src/main/client")
      }
    }
  },
  outputDir: "src/main/resources/static",
  pages: {
    index: {
      entry: "src/main/client/main.ts",
      template: "src/main/client/public/index.html",
      filename: "index.html"
    }
  },
  devServer: {
    port: 3000
  }
}
