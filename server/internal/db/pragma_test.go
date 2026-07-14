package db

import (
	"strings"
	"testing"
)

func TestApplySQLitePragmaDefaults_AddsMissing(t *testing.T) {
	dsn := applySQLitePragmaDefaults("file:arena.db?_pragma=journal_mode(WAL)")
	if !strings.Contains(dsn, "synchronous(NORMAL)") {
		t.Errorf("expected synchronous default to be added: %q", dsn)
	}
	if !strings.Contains(dsn, "busy_timeout(5000)") {
		t.Errorf("expected busy_timeout default to be added: %q", dsn)
	}
	// The pre-existing pragma must be preserved.
	if !strings.Contains(dsn, "journal_mode(WAL)") {
		t.Errorf("existing pragma dropped: %q", dsn)
	}
}

func TestApplySQLitePragmaDefaults_UsesQuestionMarkWhenNoQuery(t *testing.T) {
	dsn := applySQLitePragmaDefaults("file:arena.db")
	if !strings.HasPrefix(dsn, "file:arena.db?_pragma=") {
		t.Errorf("expected a ? query separator to be introduced: %q", dsn)
	}
	if strings.Contains(dsn, "??") || strings.Contains(dsn, "&&") {
		t.Errorf("malformed separators in %q", dsn)
	}
}

func TestApplySQLitePragmaDefaults_OperatorOverrideWins(t *testing.T) {
	// If the operator already set synchronous, we must NOT append our own.
	dsn := applySQLitePragmaDefaults("file:arena.db?_pragma=synchronous(FULL)")
	if strings.Contains(dsn, "synchronous(NORMAL)") {
		t.Errorf("operator's synchronous(FULL) should not be overridden: %q", dsn)
	}
	if !strings.Contains(dsn, "synchronous(FULL)") {
		t.Errorf("operator pragma lost: %q", dsn)
	}
	// busy_timeout was not set by the operator, so it should still be added.
	if !strings.Contains(dsn, "busy_timeout(5000)") {
		t.Errorf("busy_timeout default should still be added: %q", dsn)
	}
}
