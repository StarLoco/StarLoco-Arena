package game

// practiceArenaSceneryCells lists every solid-scenery cell of the fallback arena
// (real altitude, no floor). Test helper: the arena no longer keeps an explicit
// obstacle set — scenery is derived from the cell grid.
func practiceArenaSceneryCells() [][2]int32 {
	var out [][2]int32
	for y := int32(0); y < practiceArena.height; y++ {
		for x := int32(0); x < practiceArena.width; x++ {
			if practiceArena.scenery(x, y) {
				out = append(out, [2]int32{x, y})
			}
		}
	}
	return out
}
