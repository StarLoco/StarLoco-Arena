-- Add an admin flag to accounts, gating the GM chat-cheat-commands (see
-- docs/08-java-parity-roadmap.md §8.4) and the web portal's admin console
-- (account management + impersonation, see docs/10-web-portal.md).
ALTER TABLE accounts ADD COLUMN is_admin BOOLEAN NOT NULL DEFAULT FALSE;
