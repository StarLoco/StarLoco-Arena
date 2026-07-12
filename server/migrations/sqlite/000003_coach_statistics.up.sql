-- Persisted coach ladder + lifetime statistics backing the "Statistiques
-- du Coach" window (PLAYER_STATISTICS_REPORT, opcode 2400) and the
-- COACH_INFORMATION Level/Rank. The legacy Java server never persisted
-- these (TODO in PlayerStatisticsReport.java); this port tracks them for
-- real. See internal/domain/ladder.go and docs/opcodes/03-coach-world.md.
ALTER TABLE coachs ADD COLUMN strength INTEGER NOT NULL DEFAULT 0;
ALTER TABLE coachs ADD COLUMN stat_fights INTEGER NOT NULL DEFAULT 0;
ALTER TABLE coachs ADD COLUMN stat_wins INTEGER NOT NULL DEFAULT 0;
ALTER TABLE coachs ADD COLUMN stat_losses INTEGER NOT NULL DEFAULT 0;
ALTER TABLE coachs ADD COLUMN consecutive_wins INTEGER NOT NULL DEFAULT 0;
ALTER TABLE coachs ADD COLUMN time_in_fight_secs INTEGER NOT NULL DEFAULT 0;
ALTER TABLE coachs ADD COLUMN total_play_time_secs INTEGER NOT NULL DEFAULT 0;
