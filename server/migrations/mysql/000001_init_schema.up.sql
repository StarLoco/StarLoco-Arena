-- Initial schema for MySQL/MariaDB. See docs/03-data-model.md.

CREATE TABLE accounts (
    id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(64) NOT NULL,
    password_hash VARCHAR(60) NOT NULL,
    connected     BOOLEAN NOT NULL DEFAULT FALSE,
    coach_id      BIGINT UNSIGNED NULL,
    created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uq_accounts_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coachs (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(32) NOT NULL,
    skin       TINYINT UNSIGNED NOT NULL,
    hair       TINYINT UNSIGNED NOT NULL,
    sex        TINYINT UNSIGNED NOT NULL,
    pos_x      INT NOT NULL DEFAULT 1,
    pos_y      INT NOT NULL DEFAULT 1,
    pos_z      SMALLINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uq_coachs_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE accounts
    ADD CONSTRAINT fk_accounts_coach FOREIGN KEY (coach_id) REFERENCES coachs (id) ON DELETE SET NULL;

CREATE TABLE coach_cards (
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    coach_id    BIGINT UNSIGNED NOT NULL,
    template_id INT NOT NULL,
    quantity    SMALLINT NOT NULL DEFAULT 1,
    pos         SMALLINT NOT NULL DEFAULT 0,
    flag        SMALLINT NOT NULL DEFAULT 2,
    KEY idx_coach_cards_coach_id (coach_id),
    KEY idx_coach_cards_template_id (template_id),
    CONSTRAINT fk_coach_cards_coach FOREIGN KEY (coach_id) REFERENCES coachs (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coach_friends (
    id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    owner_id  BIGINT UNSIGNED NOT NULL,
    friend_id BIGINT UNSIGNED NOT NULL,
    notify    BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE KEY uq_coach_friends (owner_id, friend_id),
    CONSTRAINT fk_coach_friends_owner FOREIGN KEY (owner_id) REFERENCES coachs (id) ON DELETE CASCADE,
    CONSTRAINT fk_coach_friends_friend FOREIGN KEY (friend_id) REFERENCES coachs (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coach_ignored (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    owner_id   BIGINT UNSIGNED NOT NULL,
    ignored_id BIGINT UNSIGNED NOT NULL,
    UNIQUE KEY uq_coach_ignored (owner_id, ignored_id),
    CONSTRAINT fk_coach_ignored_owner FOREIGN KEY (owner_id) REFERENCES coachs (id) ON DELETE CASCADE,
    CONSTRAINT fk_coach_ignored_ignored FOREIGN KEY (ignored_id) REFERENCES coachs (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE fighters (
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    coach_id   BIGINT UNSIGNED NOT NULL,
    name       VARCHAR(32) NOT NULL,
    breed      TINYINT UNSIGNED NOT NULL,
    sex        TINYINT UNSIGNED NOT NULL,
    skin       TINYINT UNSIGNED NOT NULL,
    budget     SMALLINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_fighters_coach_id (coach_id),
    CONSTRAINT fk_fighters_coach FOREIGN KEY (coach_id) REFERENCES coachs (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE fighter_spells (
    fighter_id BIGINT UNSIGNED NOT NULL,
    spell_id   INT NOT NULL,
    PRIMARY KEY (fighter_id, spell_id),
    CONSTRAINT fk_fighter_spells_fighter FOREIGN KEY (fighter_id) REFERENCES fighters (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE fighter_objects (
    fighter_id  BIGINT UNSIGNED NOT NULL,
    template_id INT NOT NULL,
    PRIMARY KEY (fighter_id, template_id),
    CONSTRAINT fk_fighter_objects_fighter FOREIGN KEY (fighter_id) REFERENCES fighters (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE teams (
    id       BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    coach_id BIGINT UNSIGNED NOT NULL,
    slot     SMALLINT NOT NULL,
    name     VARCHAR(32) NOT NULL,
    KEY idx_teams_coach_id (coach_id),
    CONSTRAINT fk_teams_coach FOREIGN KEY (coach_id) REFERENCES coachs (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE team_fighters (
    team_id    BIGINT UNSIGNED NOT NULL,
    fighter_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (team_id, fighter_id),
    CONSTRAINT fk_team_fighters_team FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE,
    CONSTRAINT fk_team_fighters_fighter FOREIGN KEY (fighter_id) REFERENCES fighters (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
