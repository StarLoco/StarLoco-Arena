package game

import (
	"os"
	"path/filepath"
	"strconv"
	"strings"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/store"
)

// TestClanIslandDestResolvesTheGuildsOwnIsland: card 859 is the one Zaap card
// with no fixed destination, so the whole point is that two clans go to
// DIFFERENT worlds and each arrives at that world's own Zaap instance. A fixed
// fallback would satisfy "it teleports somewhere" and be wrong.
func TestClanIslandDestResolvesTheGuildsOwnIsland(t *testing.T) {
	st, err := store.Open(filepath.Join(t.TempDir(), "island.db"))
	if err != nil {
		t.Fatalf("open store: %v", err)
	}
	t.Cleanup(func() { _ = st.Close() })
	d := &Deps{Store: st}

	mk := func(login, coachName, guildName string) uint {
		t.Helper()
		acc, err := st.Accounts.CreateAccount(login, "secret", false)
		if err != nil {
			t.Fatalf("account: %v", err)
		}
		c, err := st.Coaches.Create(acc.ID, coachName, 1, 2, 0)
		if err != nil {
			t.Fatalf("coach: %v", err)
		}
		if _, err := st.Guilds.Create(guildName, c.ID, "Chef", "Membre"); err != nil {
			t.Fatalf("guild: %v", err)
		}
		return c.ID
	}
	a := mk("isl_a", "ChefA", "ClanA")
	b := mk("isl_b", "ChefB", "ClanB")

	da, ok := d.clanIslandDest(a)
	if !ok {
		t.Fatal("clan A got no island")
	}
	db, ok := d.clanIslandDest(b)
	if !ok {
		t.Fatal("clan B got no island")
	}
	if da.world == db.world {
		t.Errorf("both clans resolve to world %d", da.world)
	}
	for _, got := range []zaapDest{da, db} {
		want, ok := clanIslandZaap[got.world]
		if !ok {
			t.Fatalf("world %d is not a clan island", got.world)
		}
		if got.instanceID != want {
			t.Errorf("world %d -> instance %d, want %d (docs/OVERWORLD-MAP.md)",
				got.world, got.instanceID, want)
		}
	}
	// Stable across calls: a clan's island must not move under it.
	if again, _ := d.clanIslandDest(a); again.world != da.world {
		t.Errorf("clan A's island moved from %d to %d", da.world, again.world)
	}
}

// TestClanIslandDestRefusesAClanlessCoach: card 859 must do nothing for a coach
// in no clan rather than dropping it on some default island.
func TestClanIslandDestRefusesAClanlessCoach(t *testing.T) {
	st, err := store.Open(filepath.Join(t.TempDir(), "island2.db"))
	if err != nil {
		t.Fatalf("open store: %v", err)
	}
	t.Cleanup(func() { _ = st.Close() })
	d := &Deps{Store: st}

	acc, _ := st.Accounts.CreateAccount("isl_solo", "secret", false)
	c, _ := st.Coaches.Create(acc.ID, "Solo", 1, 2, 0)

	if dest, ok := d.clanIslandDest(c.ID); ok {
		t.Errorf("a clanless coach was sent to world %d", dest.world)
	}
}

// TestClanIslandTableMatchesTheDocumentedZaaps checks the transcription against
// its SOURCE rather than against itself.
//
// The instance ids were copied by hand out of docs/OVERWORLD-MAP.md, and an
// earlier version of this test compared them to the very map it was validating -
// which passes no matter what is in it. Parsing the document makes a typo
// visible; without that, a wrong instance id teleports a clan to a Zaap that is
// not on its island (or does not exist) and nothing catches it before a player
// does.
func TestClanIslandTableMatchesTheDocumentedZaaps(t *testing.T) {
	raw, err := os.ReadFile(filepath.Join("..", "..", "docs", "OVERWORLD-MAP.md"))
	if err != nil {
		t.Skipf("overworld map doc not available: %v", err)
	}
	// Rows carry TWO islands each - | world | inst | cell | alt | world | inst |
	// cell | alt | - and the last row has an EMPTY left half, so the halves are
	// parsed independently rather than with one all-or-nothing pattern. (A regex
	// that demanded digits in the first two cells silently dropped world 109.)
	doc := map[int16]int64{}
	for _, line := range strings.Split(string(raw), "\n") {
		line = strings.TrimSpace(line)
		if !strings.HasPrefix(line, "|") {
			continue
		}
		cells := strings.Split(strings.Trim(line, "|"), "|")
		for i := 0; i+1 < len(cells); i += 4 {
			w, err1 := strconv.Atoi(strings.TrimSpace(cells[i]))
			inst, err2 := strconv.Atoi(strings.TrimSpace(cells[i+1]))
			if err1 != nil || err2 != nil {
				continue
			}
			if int16(w) < store.GuildIslandFirst || int16(w) > store.GuildIslandLast {
				continue
			}
			// World 102 ships two Zaaps; the table lists it twice and the code
			// uses the first, so the second must not overwrite it.
			if _, seen := doc[int16(w)]; !seen {
				doc[int16(w)] = int64(inst)
			}
		}
	}
	if len(doc) != 24 {
		t.Fatalf("parsed %d islands from the doc, want 24 - the table format changed", len(doc))
	}
	if len(clanIslandZaap) != len(doc) {
		t.Errorf("code maps %d islands, doc lists %d", len(clanIslandZaap), len(doc))
	}
	for w, wantInst := range doc {
		got, ok := clanIslandZaap[w]
		if !ok {
			t.Errorf("world %d is documented but missing from the code", w)
			continue
		}
		if got != wantInst {
			t.Errorf("world %d -> instance %d, but the doc says %d", w, got, wantInst)
		}
	}
}
