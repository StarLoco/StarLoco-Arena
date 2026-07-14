package main

import (
	"errors"
	"strings"

	"github.com/dofusarena/go-server/internal/botclient"
)

// errors.go turns dynamic per-attempt error strings into low-cardinality
// "classes" so the report's distinct-failure dedup groups them meaningfully
// (e.g. a thousand "opponent-found: recv timeout" failures collapse to one
// row with count=1000 instead of a thousand near-identical rows).

// classify reduces an arbitrary error to a stable short label.
func classify(err error) string {
	if err == nil {
		return ""
	}
	if errors.Is(err, botclient.ErrClosed) {
		return "connection closed"
	}
	msg := err.Error()
	switch {
	case strings.Contains(msg, "timeout"):
		return "timeout"
	case strings.Contains(msg, "connection refused"):
		return "connection refused"
	case strings.Contains(msg, "reset by peer"), strings.Contains(msg, "forcibly closed"):
		return "connection reset"
	case strings.Contains(msg, "EOF"):
		return "eof"
	case strings.Contains(msg, "auth rejected"):
		return "auth rejected"
	default:
		return truncate(msg, 80)
	}
}

// walkErr / chat share the same simple classification.
func walkErr(err error) string { return classify(err) }

// fightErr keeps the fight sub-step prefix (search/opponent-found/create-
// fight/drive) but classifies the underlying cause, so failures are grouped
// by WHERE in the fight flow they happened.
func fightErr(err error) string {
	if err == nil {
		return ""
	}
	msg := err.Error()
	if i := strings.Index(msg, ":"); i > 0 {
		return msg[:i] + ": " + classify(errors.New(strings.TrimSpace(msg[i+1:])))
	}
	return classify(err)
}

// exchangeErr classifies exchange failures, preserving the sub-step prefix.
func exchangeErr(err error) string { return fightErr(err) }

func truncate(s string, n int) string {
	if len(s) <= n {
		return s
	}
	return s[:n] + "..."
}
