# Bundled webfonts

Two typefaces ship with the server binary so the portal renders identically on
every machine and needs **no external requests** — no CDN, no `fonts.googleapis.com`,
nothing that fails on a LAN party with no internet. They are the only binary files
in this repository that are not game data.

| File | Family | Axes | Subset | Size |
|---|---|---|---|---|
| `sora-latin.woff2` | [Sora](https://github.com/jonathansoma/sora) | variable, weight 400–800 | latin (U+0000–00FF + typographic punctuation) | 25 KB |
| `outfit-latin.woff2` | [Outfit](https://github.com/Outfitio/Outfit-Fonts) | variable, weight 300–700 | latin (U+0000–00FF + typographic punctuation) | 32 KB |

Both are **variable** fonts: one file per family covers the whole weight range the
portal uses, which is why two files are enough for headings, body copy, semibold
navigation and the heavy display numerals.

The latin subset spans U+0000–00FF, which covers every character the game client can
send: the wire protocol is windows-1252, and coach/account names are further
restricted to `[A-Za-z0-9_-]`. Accented French text in the interface (`é`, `è`, `ç`,
`à`) renders from the same subset.

## Licence

Both families are released under the **SIL Open Font License 1.1**, which explicitly
permits redistribution — bundled, embedded in a binary, or sold with software —
provided the fonts are not sold on their own and the licence travels with them.

- Sora — Copyright © 2020 The Sora Project Authors
- Outfit — Copyright © 2021 The Outfit Project Authors

Full licence text: <https://openfontlicense.org/open-font-license-official-text/>

Neither font is renamed, so the OFL's Reserved Font Name clause is not engaged.
These files were produced by Google Fonts' subsetter and fetched from
`fonts.gstatic.com`; they are byte-identical to what that service serves.
