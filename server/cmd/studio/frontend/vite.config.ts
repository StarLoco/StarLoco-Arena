import { defineConfig } from "vite";

// Wails serves the built assets from frontend/dist and injects its runtime
// + generated bindings under /wailsjs at dev time. We keep the config
// minimal; relative base so the embedded assetserver resolves paths.
export default defineConfig({
  base: "./",
  build: {
    outDir: "dist",
    emptyOutDir: true,
    target: "es2021",
  },
  server: {
    port: 34115,
    strictPort: false,
  },
});
