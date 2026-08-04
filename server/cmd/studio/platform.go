package main

import "runtime"

// runtimeOS returns a friendly OS label for the UI footer.
func runtimeOS() string {
	switch runtime.GOOS {
	case "windows":
		return "Windows"
	case "darwin":
		return "macOS"
	case "linux":
		return "Linux"
	default:
		return runtime.GOOS
	}
}
