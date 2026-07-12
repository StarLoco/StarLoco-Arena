// Command dbdump is a local-development diagnostic that prints the
// contents of the coach/fighter/team tables from a SQLite database, so we
// can see exactly what persisted (bypassing the wire protocol entirely).
//
// Usage:
//
//	go run ./cmd/dbdump --dsn "file:arena.dev.db?_pragma=foreign_keys(1)"
package main

import (
	"flag"
	"fmt"
	"os"

	"github.com/glebarez/sqlite"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

func main() {
	dsn := flag.String("dsn", "file:arena.dev.db?_pragma=foreign_keys(1)", "sqlite DSN")
	flag.Parse()

	db, err := gorm.Open(sqlite.Open(*dsn), &gorm.Config{Logger: logger.Default.LogMode(logger.Silent)})
	if err != nil {
		fmt.Fprintln(os.Stderr, "open:", err)
		os.Exit(1)
	}

	dumpTable(db, "accounts", "SELECT id, name, connected, coach_id FROM accounts")
	dumpTable(db, "coachs", "SELECT id, name FROM coachs")
	dumpTable(db, "fighters", "SELECT id, coach_id, name, breed FROM fighters")
	dumpTable(db, "teams", "SELECT id, coach_id, slot, name FROM teams")
	dumpTable(db, "team_fighters", "SELECT team_id, fighter_id FROM team_fighters")
	dumpTable(db, "fighter_spells", "SELECT fighter_id, spell_id FROM fighter_spells")
	dumpTable(db, "fighter_objects", "SELECT fighter_id, template_id FROM fighter_objects")
}

func dumpTable(db *gorm.DB, name, query string) {
	fmt.Printf("\n=== %s ===\n", name)
	rows, err := db.Raw(query).Rows()
	if err != nil {
		fmt.Printf("  error: %v\n", err)
		return
	}
	defer rows.Close()

	cols, _ := rows.Columns()
	fmt.Printf("  %v\n", cols)
	count := 0
	for rows.Next() {
		vals := make([]any, len(cols))
		ptrs := make([]any, len(cols))
		for i := range vals {
			ptrs[i] = &vals[i]
		}
		if err := rows.Scan(ptrs...); err != nil {
			fmt.Printf("  scan error: %v\n", err)
			continue
		}
		fmt.Printf("  %v\n", vals)
		count++
	}
	fmt.Printf("  (%d rows)\n", count)
}
