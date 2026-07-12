package db

import (
	"context"
	"errors"
	"time"

	"github.com/rs/zerolog"
	"gorm.io/gorm"
	gormlogger "gorm.io/gorm/logger"
)

// zerologGormLogger adapts GORM's logger.Interface to zerolog so SQL
// logging follows the same structured/leveled conventions as the rest of
// the server instead of GORM's default stdlib-log output.
type zerologGormLogger struct {
	logger zerolog.Logger
}

func (l *zerologGormLogger) LogMode(gormlogger.LogLevel) gormlogger.Interface {
	return l
}

func (l *zerologGormLogger) Info(_ context.Context, msg string, args ...interface{}) {
	l.logger.Info().Msgf(msg, args...)
}

func (l *zerologGormLogger) Warn(_ context.Context, msg string, args ...interface{}) {
	l.logger.Warn().Msgf(msg, args...)
}

func (l *zerologGormLogger) Error(_ context.Context, msg string, args ...interface{}) {
	l.logger.Error().Msgf(msg, args...)
}

func (l *zerologGormLogger) Trace(_ context.Context, begin time.Time, fc func() (string, int64), err error) {
	elapsed := time.Since(begin)
	sql, rows := fc()
	evt := l.logger.Debug()
	if err != nil && !errors.Is(err, gorm.ErrRecordNotFound) {
		evt = l.logger.Error().Err(err)
	}
	evt.Dur("elapsed", elapsed).Int64("rows", rows).Str("sql", sql).Msg("gorm query")
}
