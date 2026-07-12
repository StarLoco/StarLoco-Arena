// Command seedaccount is a local-development helper that inserts a test
// account (with a properly bcrypt-hashed password) directly into the
// configured database. Useful for manual protocol testing without needing
// a full account-registration flow (which this server doesn't expose over
// the wire, matching the legacy client/server, where accounts are
// presumably provisioned out-of-band, e.g. via a website).
//
// Usage:
//
//	go run ./cmd/seedaccount --config configs/config.dev.yaml --login test --password test123
package main

import (
	"flag"
	"fmt"
	"os"

	"github.com/dofusarena/go-server/internal/config"
	"github.com/dofusarena/go-server/internal/db"
	"github.com/dofusarena/go-server/internal/domain"
	golog "github.com/dofusarena/go-server/internal/log"
	"github.com/dofusarena/go-server/internal/service"
)

func main() {
	configPath := flag.String("config", "", "path to config YAML file")
	login := flag.String("login", "test", "account login/name")
	password := flag.String("password", "test", "account password (plaintext, will be bcrypt-hashed)")
	admin := flag.Bool("admin", false, "flag the account as an administrator (unlocks GM commands + web admin console)")
	flag.Parse()

	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintln(os.Stderr, "load config:", err)
		os.Exit(1)
	}

	logger := golog.New(golog.Options{Level: "warn", Format: "console"})
	gdb, err := db.Open(cfg.Database, logger)
	if err != nil {
		fmt.Fprintln(os.Stderr, "open db:", err)
		os.Exit(1)
	}

	hash, err := service.HashPassword(*password)
	if err != nil {
		fmt.Fprintln(os.Stderr, "hash password:", err)
		os.Exit(1)
	}

	account := domain.Account{Name: *login, PasswordHash: hash, IsAdmin: *admin}
	if err := gdb.Create(&account).Error; err != nil {
		fmt.Fprintln(os.Stderr, "create account:", err)
		os.Exit(1)
	}

	fmt.Printf("created account id=%d login=%q admin=%t\n", account.ID, *login, *admin)
}
