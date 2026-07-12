package main

import (
	"encoding/base64"
	"strconv"
)

// This file serves per-id icon/illustration PNGs straight from gui.jar (they
// are standard PNGs, so no decoding beyond base64-for-dataURL is needed). The
// frontend requests these lazily (as rows/tiles scroll into view) and shows a
// placeholder when an id has no matching art.
//
// Confirmed gui.jar layout (all <id>.png):
//   spell icon         gui/spells/icons/<id>.png
//   spell illustration gui/spells/illustrations/<id>.png
//   fighter card       gui/equipments/fighters/icons/<id>.png
//   coach card         gui/equipments/coachs/icons/<id>.png
//   event              gui/events/illustrations/<id>.png
//   breed timeline art gui/breeds/timeline/<gfxId>.png (keyed by gfx, not breedId)

// iconPathFor returns the gui.jar entry path for a (kind,id), or "" if the
// kind is unknown.
func iconPathFor(kind string, id int32) string {
	s := strconv.FormatInt(int64(id), 10)
	switch kind {
	case "spell":
		return "gui/spells/icons/" + s + ".png"
	case "spellIllustration":
		return "gui/spells/illustrations/" + s + ".png"
	case "fighterCard":
		return "gui/equipments/fighters/icons/" + s + ".png"
	case "fighterCardIllustration":
		return "gui/equipments/fighters/illustrations/" + s + ".png"
	case "coachCard":
		return "gui/equipments/coachs/icons/" + s + ".png"
	case "coachCardIllustration":
		return "gui/equipments/coachs/illustrations/" + s + ".png"
	case "event":
		return "gui/events/illustrations/" + s + ".png"
	case "breed":
		return "gui/breeds/timeline/" + s + ".png"
	case "breedBg":
		return "gui/breeds/backgrounds/" + s + ".png"
	default:
		return ""
	}
}

// IconResult is a single-value return for GetIcon. Wails does not cleanly
// serialize Go's multiple non-error returns (it collapses "(string, bool)"
// to a lone value in the generated JS bindings), so we return a struct: the
// data URL plus whether an icon actually exists (Found=false => the UI shows
// a placeholder).
type IconResult struct {
	URL   string `json:"url"`
	Found bool   `json:"found"`
}

// GetIcon returns a PNG data URL for the given (kind,id), or Found=false if no
// such art exists (the UI then shows a placeholder). Results are cached
// per-process by "kind/id".
func (a *App) GetIcon(kind string, id int32) IconResult {
	cacheKey := kind + "/" + strconv.FormatInt(int64(id), 10)

	a.iconsMu.Lock()
	if a.icons == nil {
		a.icons = map[string]string{}
	}
	if url, ok := a.icons[cacheKey]; ok {
		a.iconsMu.Unlock()
		return IconResult{URL: url, Found: url != ""}
	}
	a.iconsMu.Unlock()

	url := a.decodeIcon(kind, id)

	a.iconsMu.Lock()
	a.icons[cacheKey] = url // cache the miss ("") too, so we don't re-hit the jar
	a.iconsMu.Unlock()

	return IconResult{URL: url, Found: url != ""}
}

// decodeIcon reads the icon PNG from gui.jar and returns a data URL, or "" on
// any miss/error (missing entry, no client dir, etc.).
func (a *App) decodeIcon(kind string, id int32) string {
	entry := iconPathFor(kind, id)
	if entry == "" {
		return ""
	}
	r, err := a.openNamedJar("gui.jar")
	if err != nil {
		return ""
	}
	f := findEntry(r, entry)
	if f == nil {
		return ""
	}
	data, err := readZipEntry(f, 4<<20)
	if err != nil {
		return ""
	}
	return "data:image/png;base64," + base64.StdEncoding.EncodeToString(data)
}

// GetIcons resolves several icons of the same kind in one call (used to warm a
// table's visible rows). Returns a map id->dataURL, omitting misses.
func (a *App) GetIcons(kind string, ids []int32) map[string]string {
	out := make(map[string]string, len(ids))
	for _, id := range ids {
		if res := a.GetIcon(kind, id); res.Found {
			out[strconv.FormatInt(int64(id), 10)] = res.URL
		}
	}
	return out
}
