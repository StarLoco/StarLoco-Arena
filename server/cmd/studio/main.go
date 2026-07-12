// Command studio is the DofusArena2-06 Data & Asset Studio: a cross-platform
// (Linux/macOS/Windows) desktop GUI for browsing, visualizing, editing, and
// exporting the game's data files and client assets.
//
// It is deliberately a command inside the go-server module (rather than a
// separate module under tools/) so it can import the already-proven,
// byte-exact parsers in internal/gamedata directly -- see tools/README.md
// section 1 for the rationale. The web frontend lives under
// tools/studio-frontend and is built into cmd/studio/frontend/dist, which is
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
		Title:            "DofusArena2-06 Studio",
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
