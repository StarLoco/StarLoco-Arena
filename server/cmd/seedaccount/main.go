// Command seedaccount creates or updates a login account in the database,
// optionally granting admin rights. Useful for setting up test/admin logins.
//
//	go run ./cmd/seedaccount --login test --password test --admin
package main

import (
	"flag"
	"fmt"
	"os"

	"github.com/StarLoco/arena-2.70/internal/config"
	"github.com/StarLoco/arena-2.70/internal/store"
)

func main() {
	configPath := flag.String("config", "config.yaml", "path to YAML config (for db settings)")
	login := flag.String("login", "", "account login (required)")
	password := flag.String("password", "", "account password (required for new accounts)")
	admin := flag.Bool("admin", false, "grant admin rights")
	flag.Parse()

	if *login == "" {
		fmt.Fprintln(os.Stderr, "error: --login is required")
		os.Exit(2)
	}

	cfg, err := config.Load(*configPath)
	if err != nil {
		fmt.Fprintln(os.Stderr, "config:", err)
		os.Exit(1)
	}
	st, err := store.OpenConfig(store.Config{
		Driver: cfg.DB.Driver, DSN: cfg.DB.DSN,
		MaxOpenConns: cfg.DB.MaxOpenConns, MaxIdleConns: cfg.DB.MaxIdleConns,
	})
	if err != nil {
		fmt.Fprintln(os.Stderr, "open store:", err)
		os.Exit(1)
	}

	acc, err := st.Accounts.FindByName(*login)
	switch {
	case err == store.ErrNotFound:
		if *password == "" {
			fmt.Fprintln(os.Stderr, "error: --password is required to create a new account")
			os.Exit(2)
		}
		acc, err = st.Accounts.CreateAccount(*login, *password, *admin)
		if err != nil {
			fmt.Fprintln(os.Stderr, "create:", err)
			os.Exit(1)
		}
		fmt.Printf("created account %q (id=%d, admin=%v)\n", acc.Name, acc.ID, acc.IsAdmin)
	case err != nil:
		fmt.Fprintln(os.Stderr, "lookup:", err)
		os.Exit(1)
	default:
		// Existing account: update admin flag (and password if given).
		updates := map[string]any{"is_admin": *admin}
		if *password != "" {
			// Re-create hash via a temp account helper is overkill; update inline.
			if err := st.Accounts.SetPassword(acc.ID, *password); err != nil {
				fmt.Fprintln(os.Stderr, "set password:", err)
				os.Exit(1)
			}
		}
		if err := st.DB().Model(acc).Updates(updates).Error; err != nil {
			fmt.Fprintln(os.Stderr, "update:", err)
			os.Exit(1)
		}
		fmt.Printf("updated account %q (id=%d, admin=%v)\n", acc.Name, acc.ID, *admin)
	}
}
