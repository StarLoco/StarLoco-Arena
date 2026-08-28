package store

import (
	"go/ast"
	"go/parser"
	"go/token"
	"strings"
	"testing"
)

// TestNoSQLiteOnlySQLInStringLiterals pins a portability rule the test suite
// cannot otherwise see: these tests run on SQLite, so SQLite-only SQL passes
// everything here and then fails in production on postgres/mysql.
//
// It cost a live outage to learn. `COLLATE NOCASE` sat in CoachRepo.GetByName
// and CoachRepo.Create; on SQLite it works, on postgres coach creation died
// with `collation "nocase" for encoding "UTF8" does not exist` and the player
// was disconnected the instant they confirmed their name. Use
// `LOWER(col) = LOWER(?)` instead, which every driver understands.
//
// Only string literals are inspected, so prose in comments (including the
// paragraph above) is free to name these constructs.
func TestNoSQLiteOnlySQLInStringLiterals(t *testing.T) {
	// Constructs that only SQLite understands, and the portable replacement.
	banned := map[string]string{
		"collate nocase": "LOWER(col) = LOWER(?)",
		"autoincrement":  "let the driver pick the identity type",
		"insert or replace": "ON CONFLICT ... DO UPDATE, or GORM's " +
			"clause.OnConflict",
		"insert or ignore": "ON CONFLICT ... DO NOTHING",
		"ifnull(":          "COALESCE(",
		"group_concat(":    "STRING_AGG / GROUP_CONCAT per driver",
		"strftime(":        "pass Go time.Time values as parameters",
		"julianday(":       "pass Go time.Time values as parameters",
	}

	fset := token.NewFileSet()
	pkgs, err := parser.ParseDir(fset, ".", nil, parser.ParseComments)
	if err != nil {
		t.Fatalf("parse package: %v", err)
	}

	for _, pkg := range pkgs {
		for path, file := range pkg.Files {
			// PRAGMAs are legitimate where they are guarded by isSQLite, and
			// store.go is the only place that does that.
			if strings.HasSuffix(path, "portability_test.go") {
				continue
			}
			ast.Inspect(file, func(n ast.Node) bool {
				lit, ok := n.(*ast.BasicLit)
				if !ok || lit.Kind != token.STRING {
					return true
				}
				lowered := strings.ToLower(lit.Value)
				for bad, fix := range banned {
					if strings.Contains(lowered, bad) {
						t.Errorf("%s: SQL string contains SQLite-only %q\n"+
							"  literal: %s\n"+
							"  use instead: %s\n"+
							"  (the test suite runs on SQLite, so this would "+
							"only fail once a real player hit it on postgres)",
							fset.Position(lit.Pos()), bad, lit.Value, fix)
					}
				}
				return true
			})
		}
	}
}
