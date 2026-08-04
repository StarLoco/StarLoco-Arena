package e2e

// raceEnabled is set by the build tag below when the race detector is active.
// The heavy full-fight combat E2E test is timing-sensitive; under -race's ~10x
// slowdown its turn-timing assumptions become flaky even though the server
// logic is correct (the internal/game fight package passes -race consistently).
// We skip only that one test under -race and keep it fully active in normal runs.
var raceEnabled = false
