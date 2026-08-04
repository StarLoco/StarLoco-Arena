# Security Policy

## Scope

This project is a preservation effort and a hobby game server. It is **not
intended for production or public-facing deployment**, and it deliberately does
not implement transport encryption, rate limiting, or account bans (the original
protocol and client do not support them). Please keep that context in mind when
assessing risk.

That said, defects that could harm someone running the server locally — memory
corruption, panics reachable from untrusted client input, path traversal, SQL
injection, credential leakage, etc. — are in scope and worth reporting.

## Supported versions

Only the tip of the default branch (`v2.70`) is supported. There are no
long-term release branches. The legacy 2.04b line on `v2.04` is preserved for
reference and is not maintained.

## Reporting a vulnerability

**Please do not open a public issue for security-sensitive reports.**

Preferred: use GitHub's private vulnerability reporting —
**Security → Report a vulnerability** (Private Vulnerability Reporting) on the
repository.

Alternatively, contact the maintainer privately
(`<add-your-security-contact-here>` — replace before publishing).

When reporting, please include:

- affected component and version/commit,
- a description of the issue and its impact,
- reproduction steps or a proof of concept, if possible.

You can expect an acknowledgement within a reasonable timeframe. As an unpaid
hobby project there is no bug-bounty program, but your report will be taken
seriously and credited if you wish.

## Copyright / takedown

Requests to remove copyrighted material are handled separately — see
[`DISCLAIMER.md`](./DISCLAIMER.md).
