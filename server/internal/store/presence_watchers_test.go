package store

import (
	"path/filepath"
	"testing"

	"github.com/StarLoco/arena-2.70/internal/domain"
)

// TestWatchersAsFriendIgnoresNotifyFlag pins a distinction the client makes and
// the server used to collapse.
//
// `notify` is a TOAST preference. The client applies a 3148 presence
// notification unconditionally - it sets the friend online AND stores the coach
// id it carries - and only checks its own notify flag before printing "X vient
// de se connecter". Filtering the watcher query on notify therefore did more
// than silence a message: the friend never learned the other was online, and
// never got its id. Since the friend LIST only carries an id for coaches who
// were already online when it was built, that left the id permanently -1 and a
// 2v2 invitation failed depending purely on who logged in first.
func TestWatchersAsFriendIgnoresNotifyFlag(t *testing.T) {
	st, err := Open(filepath.Join(t.TempDir(), "watch.db"))
	if err != nil {
		t.Fatalf("open: %v", err)
	}
	t.Cleanup(func() { _ = st.Close() })

	// The friendship rows carry foreign keys, so the coaches must exist first.
	for i := 1; i <= 3; i++ {
		c := domain.Coach{ID: uint(i), Name: string(rune('A' + i - 1))}
		if err := st.DB().Create(&c).Error; err != nil {
			t.Fatalf("create coach %d: %v", i, err)
		}
	}

	// Two coaches both watch coach 3; only one wants the toast.
	//
	// Notify is written through a map, not the struct: the column is
	// `gorm:"default:true"`, so creating with the struct's zero value makes GORM
	// OMIT the field and the database substitutes true. A first version of this
	// test did exactly that, so it had no notify=false row at all and the
	// mutation restoring the filter passed.
	for _, f := range []map[string]any{
		{"owner_id": 1, "friend_id": 3, "notify": true},
		{"owner_id": 2, "friend_id": 3, "notify": false},
	} {
		if err := st.DB().Table("coach_friends").Create(f).Error; err != nil {
			t.Fatalf("create friendship: %v", err)
		}
	}
	// Prove the fixture is what it claims before relying on it.
	var stored []domain.CoachFriend
	if err := st.DB().Where("friend_id = ?", 3).Find(&stored).Error; err != nil {
		t.Fatalf("read back: %v", err)
	}
	notifyOff := 0
	for _, f := range stored {
		if !f.Notify {
			notifyOff++
		}
	}
	if notifyOff != 1 {
		t.Fatalf("fixture has %d notify=false rows, want exactly 1 - the test would "+
			"prove nothing", notifyOff)
	}

	ids, err := st.Coaches.WatchersAsFriend(3)
	if err != nil {
		t.Fatalf("WatchersAsFriend: %v", err)
	}
	seen := map[uint]bool{}
	for _, id := range ids {
		seen[id] = true
	}
	if !seen[1] {
		t.Error("the notify=true watcher was not returned")
	}
	if !seen[2] {
		t.Error("the notify=false watcher was dropped - it will never learn the " +
			"friend came online, nor receive its coach id")
	}
	if len(ids) != 2 {
		t.Errorf("watchers = %v, want both 1 and 2", ids)
	}
}
