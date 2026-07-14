// Package log configures the process-wide structured logger (zerolog) and
// exposes small helpers for creating contextual sub-loggers, e.g. per
// connection or per fight, so log lines carry stable correlation fields
// instead of being built via ad-hoc string concatenation.
package log

import (
	"fmt"
	"io"
	"os"
	"path/filepath"
	"strings"
	"time"

	"github.com/rs/zerolog"
)

// Options configures the root logger.
type Options struct {
	// Level is one of "debug", "info", "warn", "error". Defaults to "info"
	// for unrecognized values.
	Level string
	// Format is "console" (human-readable, colorized) or "json"
	// (structured, recommended for production log aggregation).
	Format string
	// Dir, when non-empty, additionally writes every log line (in
	// human-readable console format) to a NEW timestamped file inside that
	// directory (e.g. logs/server-2006-01-02_15-04-05.log). The newest file
	// is therefore always the latest run's log, so packet traces can be
	// inspected/diffed from disk instead of scraping the console. Failures
	// to open the file are non-fatal (logged to stderr, file output
	// skipped).
	Dir string
}

// New builds the root zerolog.Logger according to opts and also installs it
// as zerolog's global logger so package-level helpers (zerolog/log) work if
// used incidentally by a dependency.
func New(opts Options) zerolog.Logger {
	zerolog.TimeFieldFormat = time.RFC3339

	var console io.Writer = os.Stdout
	if strings.EqualFold(opts.Format, "console") {
		console = zerolog.ConsoleWriter{Out: os.Stdout, TimeFormat: time.Kitchen}
	}

	out := console
	if opts.Dir != "" {
		if f, path, err := openRunLogFile(opts.Dir); err != nil {
			fmt.Fprintf(os.Stderr, "log: could not open log file in %q, continuing with console only: %v\n", opts.Dir, err)
		} else {
			fmt.Fprintf(os.Stderr, "log: writing this run's log to %s\n", path)
			fileWriter := zerolog.ConsoleWriter{Out: f, TimeFormat: time.Kitchen, NoColor: true}
			out = zerolog.MultiLevelWriter(console, fileWriter)
		}
	}

	logger := zerolog.New(out).With().Timestamp().Logger().Level(parseLevel(opts.Level))
	return logger
}

// openRunLogFile creates dir (if needed) and opens a fresh, timestamped log
// file for this run, returning the file and its path.
func openRunLogFile(dir string) (*os.File, string, error) {
	if err := os.MkdirAll(dir, 0o755); err != nil {
		return nil, "", err
	}
	name := fmt.Sprintf("server-%s.log", time.Now().Format("2006-01-02_15-04-05"))
	path := filepath.Join(dir, name)
	f, err := os.OpenFile(path, os.O_CREATE|os.O_WRONLY|os.O_TRUNC, 0o644)
	if err != nil {
		return nil, "", err
	}
	return f, path, nil
}

func parseLevel(level string) zerolog.Level {
	// Friendly aliases for "log nothing" (zerolog's own token is
	// "disabled"); makes `logging.level: off` in a YAML config work.
	switch strings.ToLower(strings.TrimSpace(level)) {
	case "off", "none", "silent", "quiet":
		return zerolog.Disabled
	}
	lvl, err := zerolog.ParseLevel(strings.ToLower(level))
	if err != nil {
		return zerolog.InfoLevel
	}
	return lvl
}
