-- Initial schema for SQLite (local dev / tests). See docs/03-data-model.md.
-- SQLite resolves forward-referenced foreign keys at runtime, not at
-- CREATE TABLE time, so accounts.coach_id can reference coachs before that
-- table is defined below.

PRAGMA foreign_keys = ON;

CREATE TABLE accounts (
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    name          TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    connected     INTEGER NOT NULL DEFAULT 0,
    coach_id      INTEGER REFERENCES coachs (id) ON DELETE SET NULL,
    created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE coachs (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    name       TEXT NOT NULL UNIQUE,
    skin       INTEGER NOT NULL,
    hair       INTEGER NOT NULL,
    sex        INTEGER NOT NULL,
    pos_x      INTEGER NOT NULL DEFAULT 1,
    pos_y      INTEGER NOT NULL DEFAULT 1,
    pos_z      INTEGER NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE coach_cards (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    coach_id    INTEGER NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    template_id INTEGER NOT NULL,
    quantity    INTEGER NOT NULL DEFAULT 1,
    pos         INTEGER NOT NULL DEFAULT 0,
    flag        INTEGER NOT NULL DEFAULT 2
);
CREATE INDEX idx_coach_cards_coach_id ON coach_cards (coach_id);
CREATE INDEX idx_coach_cards_template_id ON coach_cards (template_id);

CREATE TABLE coach_friends (
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    owner_id  INTEGER NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    friend_id INTEGER NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    notify    INTEGER NOT NULL DEFAULT 1,
    UNIQUE (owner_id, friend_id)
);

CREATE TABLE coach_ignored (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    owner_id   INTEGER NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    ignored_id INTEGER NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    UNIQUE (owner_id, ignored_id)
);

CREATE TABLE fighters (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    coach_id   INTEGER NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    name       TEXT NOT NULL,
    breed      INTEGER NOT NULL,
    sex        INTEGER NOT NULL,
    skin       INTEGER NOT NULL,
    budget     INTEGER NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_fighters_coach_id ON fighters (coach_id);

CREATE TABLE fighter_spells (
    fighter_id INTEGER NOT NULL REFERENCES fighters (id) ON DELETE CASCADE,
    spell_id   INTEGER NOT NULL,
    PRIMARY KEY (fighter_id, spell_id)
);

CREATE TABLE fighter_objects (
    fighter_id  INTEGER NOT NULL REFERENCES fighters (id) ON DELETE CASCADE,
    template_id INTEGER NOT NULL,
    PRIMARY KEY (fighter_id, template_id)
);

CREATE TABLE teams (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    coach_id INTEGER NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    slot     INTEGER NOT NULL,
    name     TEXT NOT NULL
);
CREATE INDEX idx_teams_coach_id ON teams (coach_id);

CREATE TABLE team_fighters (
    team_id    INTEGER NOT NULL REFERENCES teams (id) ON DELETE CASCADE,
    fighter_id INTEGER NOT NULL REFERENCES fighters (id) ON DELETE CASCADE,
    PRIMARY KEY (team_id, fighter_id)
);
