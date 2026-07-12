// Package migrations embeds the per-dialect SQL migration files so the
// compiled server binary is self-contained (no external migrations/
// directory needs to ship alongside it, though one still can for manual
// inspection/tooling). See go-server/docs/03-data-model.md §3.4.
package migrations

import "embed"

// FS embeds every file under sqlite/, postgres/, and mysql/.
//
//go:embed all:sqlite all:postgres all:mysql
var FS embed.FS
