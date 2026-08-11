package game

import (
	"os"
	"path/filepath"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/gamedata"
)

// openRealGameData opens the real game data for a real-data test.
//
// It prefers server/data — the operator's own full client copy, which is
// git-ignored — and falls back to server/data-dist, the small curated subset
// that IS committed. That fallback matters: every real-data test here used to
// open server/data only, so all of them silently SKIPPED in CI, where the
// git-ignored tree does not exist. They now run on every build, which is the
// whole point of shipping data-dist.
//
// It still skips (never fails) when neither is present, per the repo convention.
func openRealGameData(t *testing.T) *gamedata.Store {
	t.Helper()
	for _, dir := range []string{
		filepath.Join("..", "..", "data"),
		filepath.Join("..", "..", "data-dist"),
	} {
		if _, err := os.Stat(filepath.Join(dir, "data.bdat")); err != nil {
			continue
		}
		st, err := gamedata.Open(dir)
		if err != nil {
			continue
		}
		return st
	}
	t.Skip("no game data (neither server/data nor server/data-dist); skipping")
	return nil
}
