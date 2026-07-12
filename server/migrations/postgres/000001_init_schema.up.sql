-- Initial schema for PostgreSQL. See docs/03-data-model.md for the
-- rationale behind each design choice vs. the legacy OrmLite/MySQL schema.

CREATE TABLE accounts (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(64) NOT NULL,
    password_hash VARCHAR(60) NOT NULL,
    connected     BOOLEAN NOT NULL DEFAULT FALSE,
    coach_id      BIGINT,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_accounts_name UNIQUE (name)
);

CREATE TABLE coachs (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(32) NOT NULL,
    skin       SMALLINT NOT NULL,
    hair       SMALLINT NOT NULL,
    sex        SMALLINT NOT NULL,
    pos_x      INTEGER NOT NULL DEFAULT 1,
    pos_y      INTEGER NOT NULL DEFAULT 1,
    pos_z      SMALLINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uq_coachs_name UNIQUE (name)
);

ALTER TABLE accounts
    ADD CONSTRAINT fk_accounts_coach FOREIGN KEY (coach_id) REFERENCES coachs (id) ON DELETE SET NULL;

CREATE TABLE coach_cards (
    id          BIGSERIAL PRIMARY KEY,
    coach_id    BIGINT NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    template_id INTEGER NOT NULL,
    quantity    SMALLINT NOT NULL DEFAULT 1,
    pos         SMALLINT NOT NULL DEFAULT 0,
    flag        SMALLINT NOT NULL DEFAULT 2
);
CREATE INDEX idx_coach_cards_coach_id ON coach_cards (coach_id);
CREATE INDEX idx_coach_cards_template_id ON coach_cards (template_id);

CREATE TABLE coach_friends (
    id        BIGSERIAL PRIMARY KEY,
    owner_id  BIGINT NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    friend_id BIGINT NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    notify    BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_coach_friends UNIQUE (owner_id, friend_id)
);

CREATE TABLE coach_ignored (
    id         BIGSERIAL PRIMARY KEY,
    owner_id   BIGINT NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    ignored_id BIGINT NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    CONSTRAINT uq_coach_ignored UNIQUE (owner_id, ignored_id)
);

CREATE TABLE fighters (
    id         BIGSERIAL PRIMARY KEY,
    coach_id   BIGINT NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    name       VARCHAR(32) NOT NULL,
    breed      SMALLINT NOT NULL,
    sex        SMALLINT NOT NULL,
    skin       SMALLINT NOT NULL,
    budget     SMALLINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX idx_fighters_coach_id ON fighters (coach_id);

CREATE TABLE fighter_spells (
    fighter_id BIGINT NOT NULL REFERENCES fighters (id) ON DELETE CASCADE,
    spell_id   INTEGER NOT NULL,
    PRIMARY KEY (fighter_id, spell_id)
);

CREATE TABLE fighter_objects (
    fighter_id  BIGINT NOT NULL REFERENCES fighters (id) ON DELETE CASCADE,
    template_id INTEGER NOT NULL,
    PRIMARY KEY (fighter_id, template_id)
);

CREATE TABLE teams (
    id       BIGSERIAL PRIMARY KEY,
    coach_id BIGINT NOT NULL REFERENCES coachs (id) ON DELETE CASCADE,
    slot     SMALLINT NOT NULL,
    name     VARCHAR(32) NOT NULL
);
CREATE INDEX idx_teams_coach_id ON teams (coach_id);

CREATE TABLE team_fighters (
    team_id    BIGINT NOT NULL REFERENCES teams (id) ON DELETE CASCADE,
    fighter_id BIGINT NOT NULL REFERENCES fighters (id) ON DELETE CASCADE,
    PRIMARY KEY (team_id, fighter_id)
);
