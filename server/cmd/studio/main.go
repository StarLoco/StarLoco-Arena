// Command studio is the DofusArena 2.70 Data Studio: a cross-platform desktop
// GUI for browsing the 2.70 client's static game data (the data.bdat +
// indexes.bdat object store) as sortable tables.
//
// It is deliberately a command inside the arena-2.70 module (rather than a
// separate tool) so it can import the already-proven, byte-exact decoders in
// internal/gamedata directly. Unlike the v2.04 Studio it is READ-ONLY: the
// 2.70 data layer has no encoder, so this tool browses but never writes. The
// web frontend lives under frontend/ and is built into frontend/dist, which is
// embedded below.
package main

import (
	"embed"

	"github.com/wailsapp/wails/v2"
	"github.com/wailsapp/wails/v2/pkg/options"
	"github.com/wailsapp/wails/v2/pkg/options/assetserver"
	"github.com/wailsapp/wails/v2/pkg/options/windows"
)

//go:embed all:frontend/dist
var assets embed.FS

func main() {
	app := NewApp()

	err := wails.Run(&options.App{
		Title:            "DofusArena 2.70 Studio",
		Width:            1280,
		Height:           820,
		MinWidth:         960,
		MinHeight:        600,
		BackgroundColour: &options.RGBA{R: 15, G: 17, B: 23, A: 1},
		AssetServer: &assetserver.Options{
			Assets: assets,
		},
		OnStartup: app.startup,
		Bind: []interface{}{
			app,
		},
		Windows: &windows.Options{
			WebviewIsTransparent: false,
			WindowIsTranslucent:  false,
		},
	})
	if err != nil {
		panic(err)
	}
}
