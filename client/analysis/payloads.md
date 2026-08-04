# DofusArena 2.70 — Message Payload Layouts

Auto-extracted from message `a(byte[])` (S2C decode) / `encode()` (C2S encode) bodies. **All fields are BIG-ENDIAN** (network byte order). Header already stripped; S2C fight messages (base `ue_0`) begin with an 8-byte header `apt:i32, apu:i32`.

Types: i8/i16/i32/i64 signed, u8/u16 unsigned, f32/f64 float, blob = `[u8 len][len bytes]`. Rows with a loop note are variable-length — read the source for the element layout.

> Best-effort static extraction. `declared_len` for S2C is the class's own length assertion; for C2S it's the ByteBuffer.allocate expression.


### 1 — `aqb` — DisconnectionNotificationMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 1 — `ll_2` — LoginMessage  [C2S]
declared length: `1 + byArray.length + 1 + byArray2.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |

### 2 — `apg_1` — ReconnectionTicketMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 2 — `tI` — ReconnectionTicketMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 3 — `po_1` — ReconnectionTicketRequestMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 3 — `tL` — ReconnectionTicketRequestMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 4 — `nu_2` — ReconnectionTicketRequestResultMessage  [S2C]
declared length: `1 (min)`
_(no simple fields detected — empty body or complex; see source)_

### 6 — `asu` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 7 — `na` — ClientVersionMessage  [C2S]
declared length: `4 + kS.BUILD_VERSION.length(`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |

### 8 — `oq_1` — InvalidClientVersionMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 9 — `avT` — (unnamed)  [S2C]
declared length: `2 (exact)`
_(no simple fields detected — empty body or complex; see source)_

### 10 — `ms_0` — PropertyListQueryMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 11 — `afy_2` — PropertyItemMessage  [C2S]
declared length: `1 + byArray.length + 1 + byArray2.length + 4 + 4 + n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i8/bytes | ? |  |
| 4 | — | i8/bytes | ? |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i8/bytes | ? |  |
| 7 | — | i16 | 2 |  |
| 8 | — | i32 | 4 |  |
| 9 | — | i64 | 8 |  |
| 10 | — | f64 | 8 |  |
| 11 | — | f32 | 4 |  |
| 12 | — | i8/bytes | ? |  |
| 13 | — | i8/bytes | ? |  |

### 12 — `pn_2` — PropertyQueryMessage  [C2S]
declared length: `1 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |

### 20 — `rx_0` — (unnamed)  [C2S]
declared length: `4 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i8/bytes | ? |  |

### 100 — `es_0` — (unnamed)  [S2C]
declared length: `9 (exact)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i32 | 4 |  |

### 101 — `aFC` — (unnamed)  [C2S]
declared length: `byArray.length + 2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i8/bytes | ? |  |

### 102 — `ja_0` — (unnamed)  [S2C]
declared length: `3 (min)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i16 | 2 |  |

### 103 — `dm_0` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 103 — `qr_2` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 105 — `Ve` — (unnamed)  [S2C]
declared length: `3 (min)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i16 | 2 |  |

### 106 — `lo` — (unnamed)  [S2C]
declared length: `1 (min)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 107 — `asg_0` — (unnamed)  [C2S]
declared length: `13`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i64 | 8 |  |

### 108 — `abj_0` — (unnamed)  [S2C]
declared length: `29 (exact)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i64 | 8 |  |

### 200 — `rz_2` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |

### 201 — `bd_2` — (unnamed)  [C2S]
declared length: `12`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 202 — `tt_2` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 204 — `il_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i64 | 8 |  |

### 206 — `acc_2` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i64 | 8 |  |

### 501 — `uq_2` — (unnamed)  [C2S]
declared length: `3 + byArray.length + 8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |
| 4 | — | i64 | 8 |  |
| 5 | — | i8/bytes | ? |  |
| 6 | — | i8/bytes | ? |  |
| 7 | — | i64 | 8 |  |
| 8 | — | i64 | 8 |  |

### 502 — `auf_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i8 | 1 |  |

### 504 — `mD` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i32 | 4 |  |

### 509 — `atM` — (unnamed)  [C2S]
declared length: `2 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |

### 510 — `arl_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |

### 511 — `awR` — (unnamed)  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 513 — `wt_1` — (unnamed)  [C2S]
declared length: `9 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |

### 517 — `auZ` — (unnamed)  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 519 — `add_2` — (unnamed)  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 539 — `F` — (unnamed)  [C2S]
declared length: `this.ay.nj(`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |

### 551 — `mx_1` — (unnamed)  [C2S]
declared length: `18`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |

### 553 — `abo_0` — (unnamed)  [C2S]
declared length: `13 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |

### 555 — `Nr` — (unnamed)  [C2S]
declared length: `17 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i8/bytes | ? |  |
| 5 | — | i8/bytes | ? |  |

### 556 — `h_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 557 — `Ko` — (unnamed)  [C2S]
declared length: `10`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 558 — `ahU` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |

### 560 — `ry_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |

### 1024 — `Uk` — ResultMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i32 | 4 |  |

### 1025 — `bu_1` — ClientAuthenticationMessage  [C2S]
declared length: `by + by2 + 2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |

### 1026 — `fm_1` — WorldServerUnavailableMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 2048 — `amz_0` — CoachCreationRequestMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 2049 — `alq_0` — CoachCreationMessage  [C2S]
declared length: `4 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |
| 4 | — | i8/bytes | ? |  |

### 2050 — `az_0` — CoachCreationResultMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 2052 — `aoh_2` — CoachInformationsMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 2070 — `nj` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 2260 — `py_0` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 2261 — `wv_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 2300 — `bk_1` — OpponentFoundMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 2301 — `agp_1` — OpponentSearchRequestMessage  [C2S]
declared length: `n3`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i64 | 8 |  |

### 2302 — `apa_0` — OpponentSearchErrorMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 2303 — `adj_0` — OpponentSearchCancelMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 2304 — `Hf` — OpponentSearchInProgressMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 2306 — `tj_2` — OpponentSearchCancelResultMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 2307 — `bx_1` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i64 | 8 |  |

### 2308 — `Pg` — (unnamed)  [C2S]
declared length: `n3`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i8/bytes | ? |  |

### 2309 — `cJ` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 2400 — `pl_1` — PlayerStatisticsReportMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u16 | 2 |  |

### 2401 — `uf_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u16 | 2 |  |

### 2411 — `HJ` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i32 | 4 |  |

### 2600 — `mL` — (unnamed)  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 2601 — `kq_2` — (unnamed)  [S2C]
declared length: `1 (min)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |

### 3128 — `by_1` — ChannelFlagsMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |

### 3129 — `QZ` — AddFriendMessage  [C2S]
declared length: `1 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |

### 3130 — `cz_0` — ChannelJoinMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i8 | 1 |  |

### 3131 — `MP` — AddIgnoreMessage  [C2S]
declared length: `1 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |

### 3132 — `Bs` — ChannelLeaveMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |

### 3133 — `aym_0` — RemoveFriendMessage  [C2S]
declared length: `1 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |

### 3134 — `Uy` — ChannelMemberFlagsMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i8 | 1 |  |

### 3135 — `aer_0` — RemoveIgnoreMessage  [C2S]
declared length: `1 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |

### 3136 — `ato_0` — ChannelMemberKickMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i8 | 1 |  |

### 3138 — `ax_2` — ChannelMembersMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |

### 3140 — `xb_1` — ChannelContentMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i8 | 1 |  |

### 3142 — `wh_0` — ChatUserFlagsMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |

### 3144 — `aaf_1` — FriendListMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | i16 | 2 |  |

### 3146 — `abh_0` — IgnoreListMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | u8 | 1 |  |

### 3148 — `dh_0` — NotificationFriendOnlineMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | u8 | 1 |  |
| 2 | — | u8 | 1 |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i16 | 2 |  |
| 5 | — | i8 | 1 |  |
| 6 | — | i64 | 8 |  |

### 3150 — `pv_0` — NotificationFriendOfflineMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | u8 | 1 |  |

### 3151 — `acS` — UserChannelContentMessage  [C2S]
declared length: `1 + byArray.length + 1 + byArray2.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |

### 3152 — `ck_0` — VicinityContentMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |

### 3153 — `bb_0` — UserVicinityContentMessage  [C2S]
declared length: `2 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i8/bytes | ? |  |

### 3154 — `ais_2` — PrivateContentMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | u8 | 1 |  |

### 3155 — `Xk` — UserPrivateContentMessage  [C2S]
declared length: `1 + byArray2.length + 1 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |

### 3156 — `kz_1` — FriendAddedMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | u8 | 1 |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i8 | 1 |  |
| 5 | — | i16 | 2 |  |

### 3158 — `ft_0` — IgnoreAddedMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | u8 | 1 |  |

### 3159 — `afq_0` — (unnamed)  [C2S]
declared length: `2 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i8/bytes | ? |  |

### 3160 — `adw_1` — FriendRemovedMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |

### 3161 — `aux_` — (unnamed)  [C2S]
declared length: `10 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i8/bytes | ? |  |

### 3162 — `ahm_0` — IgnoreRemovedMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |

### 3164 — `jH` — NotificationIgnoreOnlineMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | i64 | 8 |  |

### 3166 — `jf_0` — NotificationIgnoreOfflineMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |

### 3168 — `ayy` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |

### 3170 — `aik_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |

### 3198 — `ano_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | u8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |

### 3199 — `ak` — (unnamed)  [C2S]
declared length: `2 + byArray.length + 8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i64 | 8 |  |

### 3202 — `qv_0` — ChannelNotFoundMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 3204 — `ve_1` — UserNotFoundMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 3206 — `amd_1` — MalformedCommandMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 3208 — `ez_2` — MemberNotFoundMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 3210 — `lx_0` — NotEnoughPrivilegesMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 3212 — `adm_0` — NotYetImplementedMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 3214 — `as_0` — TargetIsYourselfMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 3216 — `avs` — OperationNotPermitedMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 4000 — `tg_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 4001 — `tc_2` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i32 | 4 |  |

### 4096 — `xe_2` — ActorSpawnMessage  [S2C]
declared length: `1 (min)`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i8 | 1 |  |

### 4098 — `th_1` — ActorDespawnMessage  [S2C]
declared length: `1 (min)`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i64 | 8 |  |

### 4102 — `aEV` — ActorAppearMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i16 | 2 |  |
| 5 | — | i8 | 1 |  |

### 4104 — `aya_0` — ActorDisapearMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i64 | 8 |  |

### 4106 — `aqb_0` — ActorRepositionMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i16 | 2 |  |

### 4309 — `n` — FightInvitationErrorMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 4311 — `aff_2` — FightCreationCancelMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 4500 — `avf_0` — ActorMovementMessage  [S2C]
declared length: `8 (min)`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |

### 4501 — `aLY` — CoachActorMovementRequestMessage  [C2S]
declared length: `n3`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i16 | 2 |  |

### 4503 — `md_1` — FighterActorMovementRequestMessage  [C2S]
declared length: `8 + n3`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |

### 4506 — `acg` — FighterTackledMessage  [S2C]
declared length: `24 (exact)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |
| 3 | — | i64 | 8 |  |

### 4510 — `xp_0` — ActorTeleportsMessage  [S2C]
declared length: `18 (exact)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |

### 4512 — `Gs` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 4514 — `aII` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 4516 — `yu_1` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 4517 — `aae_2` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 4518 — `Ab` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 4519 — `aOx` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 4520 — `cd_2` — FighterDiesMessage  [S2C]
declared length: `16 (exact)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |

### 4521 — `lr_2` — FighterActorDirectionChangeRequestMessage  [C2S]
declared length: `9`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |

### 4522 — `u_0` — FighterChangeDirectionMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |
| 3 | — | i8 | 1 |  |

### 4523 — `anv_0` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 4524 — `yr_1` — FighterMoveMessage  [S2C]
declared length: `16 (min)`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i16 | 2 |  |

### 4600 — `aec_2` — EnterInstanceMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | f32 | 4 |  |
| 1 | — | f32 | 4 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i8 | 1 |  |

### 4601 — `afV` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i64 | 8 |  |

### 4607 — `aik_0` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i32 | 4 |  |

### 4700 — `azt_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |

### 4701 — `JY` — (unnamed)  [C2S]
declared length: `1 + byArray.length + 4`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i32 | 4 |  |

### 4800 — `yd` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i64 | 8 |  |

### 4900 — `xk_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i32 | 4 |  |

### 4901 — `xy_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |

### 4902 — `aiz_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |

### 5000 — `CU` — NoInstanceServerAvailableMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 5101 — `fw_1` — ItemExchangeInvitationRequestMessage  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 5102 — `uo_1` — ItemExchangeInvitationMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | u8 | 1 |  |

### 5103 — `tw_0` — ItemExchangeInvitationAnswerMessage  [C2S]
declared length: `9`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |

### 5104 — `Ul` — ItemExchangeInvitationConfirmationMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i64 | 8 |  |

### 5109 — `ahJ` — ItemExchangeCardAddedMessage  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 5110 — `asH` — ItemExchangeCardRemovedMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i16 | 2 |  |

### 5111 — `any` — ItemExchangeEndMessage  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 5112 — `aaz_1` — ItemExchangeUserReadyMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i16 | 2 |  |

### 5113 — `Or` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |

### 5114 — `aqX` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |

### 5116 — `dl_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |

### 5200 — `air_2` — CoachInventoryUpdateMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i16 | 2 |  |
| 5 | — | i16 | 2 |  |
| 6 | — | i16 | 2 |  |
| 7 | — | i16 | 2 |  |
| 8 | — | i32 | 4 |  |

### 5201 — `aEl` — CoachEquipmentUpdateRequestMessage  [C2S]
declared length: `56`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 5202 — `yz_2` — CoachEquipmentUpdateMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 5203 — `fh_0` — CoachInventoryUpdateRequestMessage  [C2S]
declared length: `2 + 8 * this.aVl.size(`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |

### 5204 — `ajm_2` — (unnamed)  [C2S]
declared length: `4`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 5300 — `yg` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 5301 — `axZ` — (unnamed)  [?]
_(no simple fields detected — empty body or complex; see source)_

### 5400 — `aOo` — (unnamed)  [C2S]
declared length: `6 + this.bts.size(`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i16 | 2 |  |

### 5401 — `NN` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i16 | 2 |  |

### 5403 — `mj_1` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i8 | 1 |  |
| 3 | — | i32 | 4 |  |

### 5450 — `mo_2` — (unnamed)  [C2S]
declared length: `6 + this.bts.size(`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |

### 5470 — `Zu` — (unnamed)  [C2S]
declared length: `6 + this.cdf.length * 6`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |

### 5490 — `ahg_0` — (unnamed)  [C2S]
declared length: `n3`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |

### 5491 — `agr_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |

### 6000 — `aiy_2` — CreationFighterInformationMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i8 | 1 |  |
| 5 | — | i16 | 2 |  |

### 6001 — `aNb` — CreateFighterInformationRequestMessage  [C2S]
declared length: `3 + byArray.length + 2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i8/bytes | ? |  |

### 6002 — `DQ` — DeletionFighterInformationMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i64 | 8 |  |

### 6003 — `ot_2` — DeleteFighterInformationRequestMessage  [C2S]
declared length: `10`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 6006 — `jt_2` — FighterInformationListMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i16 | 2 |  |

### 6010 — `nl_1` — UpdatedFighterInformationInventoryMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i16 | 2 |  |

### 6011 — `bp_1` — UpdateFighterInventoryRequestMessage  [C2S]
declared length: `12 + byArray.length + 2 + byArray2.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i8/bytes | ? |  |
| 4 | — | i16 | 2 |  |
| 5 | — | i8/bytes | ? |  |

### 6013 — `qp_1` — (unnamed)  [C2S]
declared length: `20`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i64 | 8 |  |

### 6014 — `aoi` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i64 | 8 |  |
| 5 | — | i64 | 8 |  |

### 6020 — `aic_0` — SaveTeamPresetMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i8 | 1 |  |

### 6021 — `aqH` — SaveTeamPresetRequestMessage  [C2S]
declared length: `byArray.length + 1`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |

### 6022 — `agH` — DeletionTeamPresetMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i16 | 2 |  |

### 6023 — `aad_1` — DeleteTeamPresetRequestMessage  [C2S]
declared length: `12`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |

### 6024 — `ir_0` — (unnamed)  [C2S]
declared length: `1 + byArray.length + 8 + 8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i64 | 8 |  |

### 6025 — `dy_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i64 | 8 |  |

### 6026 — `abB` — (unnamed)  [C2S]
declared length: `2 + byArray.length + 8 + 8 + 2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i64 | 8 |  |
| 5 | — | i16 | 2 |  |

### 6027 — `lk_0` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 6028 — `ahh_2` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 6029 — `OJ` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 6030 — `ar_0` — TeamPresetListMessage  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i8 | 1 |  |

### 6031 — `ys_1` — TeamPresetListRequestMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 6032 — `gd_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i8 | 1 |  |

### 6200 — `jD` — EffectAreaActionMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i8 | 1 |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i64 | 8 |  |
| 5 | — | i64 | 8 |  |

### 8000 — `aat_2` — FightCreationMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 8010 — `akx_1` — StartPresentationMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 8011 — `au_2` — TeamMateSetReadyForPlacementRequestMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 8012 — `aio_0` — TeamMateSetReadyForPlacementMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 8018 — `akl_1` — EndPresentationMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 8020 — `ut` — StartPlacementMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 8021 — `adn_0` — MoveToFreePlacementRequestMessage  [C2S]
declared length: `18`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |

### 8022 — `lk_2` — MoveToFreePlacementMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |

### 8023 — `auq_0` — TeamMateSetReadyForObservationRequestMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 8024 — `dw_1` — TeamMateSetReadyForObservationMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 8028 — `aaw_0` — EndPlacementMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 8030 — `agp_2` — StartObservationMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 8031 — `nS` — TeamMateSetReadyForActionRequestMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 8032 — `aMC` — TeamMateSetReadyForActionMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 8038 — `aPo` — EndObservationMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 8040 — `tt_0` — StartActionMessage  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 8100 — `jg_1` — NewTableTurnBeginMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i8 | 1 |  |
| 3 | — | i32 | 4 |  |

### 8104 — `kw_2` — FighterTurnBeginMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |

### 8105 — `rC` — FighterEndTurnRequestMessage  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 8106 — `TJ` — FighterTurnEndMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |

### 8107 — `sg_2` — FighterCardUseRequestMessage  [C2S]
declared length: `22`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i16 | 2 |  |

### 8108 — `arn_0` — FighterCardUseMessage  [S2C]
declared length: `21 (min)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i8 | 1 |  |
| 5 | — | i8 | 1 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i32 | 4 |  |
| 8 | — | i16 | 2 |  |

### 8109 — `mc_2` — SpellCastRequestMessage  [C2S]
declared length: `22`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i16 | 2 |  |

### 8110 — `axn_0` — SpellCastMessage  [S2C]
declared length: `21 (min)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i8 | 1 |  |
| 5 | — | i8 | 1 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i32 | 4 |  |
| 8 | — | i16 | 2 |  |

### 8111 — `aso_0` — CloseCombatRequestMessage  [C2S]
declared length: `18`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |

### 8112 — `aAD` — CloseCombatMessage  [S2C]
declared length: `17 (min)`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i64 | 8 |  |
| 3 | — | i8 | 1 |  |
| 4 | — | i8 | 1 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i16 | 2 |  |

### 8120 — `amb_0` — RunningEffectActionMessage  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i8 | 1 |  |
| 3 | — | i8 | 1 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i16 | 2 |  |

### 8121 — `rq_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i16 | 2 |  |

### 8122 — `zq_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |

### 8151 — `as_1` — GiveUpFightRequestMessage  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 8200 — `ayj_0` — FightActionSequenceExecute  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 8250 — `wc_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |

### 8300 — `YP` — EndFightMessage  [S2C]
declared length: `9 (min)`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | apt | i32 | 4 | header:o |
| 1 | apu | i32 | 4 | header:o |
| 2 | — | i8 | 1 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i64 | 8 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i32 | 4 |  |
| 8 | — | i64 | 8 |  |
| 9 | — | i32 | 4 |  |
| 10 | — | i8 | 1 |  |
| 11 | — | i64 | 8 |  |
| 12 | — | i16 | 2 |  |
| 13 | — | i16 | 2 |  |
| 14 | — | i8 | 1 |  |
| 15 | — | i64 | 8 |  |
| 16 | — | i16 | 2 |  |
| 17 | — | i16 | 2 |  |
| 18 | — | i16 | 2 |  |
| 19 | — | i16 | 2 |  |
| 20 | — | i8 | 1 |  |
| 21 | — | i64 | 8 |  |
| 22 | — | i16 | 2 |  |
| 23 | — | i8 | 1 |  |
| 24 | — | i8 | 1 |  |
| 25 | — | i32 | 4 |  |

### 8400 — `aBZ` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 15000 — `ajs_0` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 15001 — `ayV` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |

### 15003 — `Eh` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 15004 — `ads_0` — (unnamed)  [C2S]
declared length: `1 + this.dxo.length * 8`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i64 | 8 |  |

### 15005 — `wt_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 15006 — `akk_1` — (unnamed)  [C2S]
declared length: `9`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 15007 — `cb` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i8 | 1 |  |

### 15506 — `rr_1` — (unnamed)  [C2S]
declared length: `1 + byArray.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |

### 15507 — `afj_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 17002 — `yq_1` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 17003 — `awa_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i32 | 4 |  |

### 17004 — `fu_2` — (unnamed)  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 17005 — `aef_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 17006 — `agh_2` — (unnamed)  [C2S]
declared length: `8 + this.dzD.nj(`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |

### 17008 — `ald_2` — (unnamed)  [C2S]
declared length: `1 + n2`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |

### 17010 — `aFu` — (unnamed)  [C2S]
declared length: `6 + this.dHa.length * 4 + 8 + 8 + 8 + 1 + this.dHe.size(`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i64 | 8 |  |
| 5 | — | i64 | 8 |  |
| 6 | — | i8/bytes | ? |  |
| 7 | — | i64 | 8 |  |
| 8 | — | i8/bytes | ? |  |
| 9 | — | i8/bytes | ? |  |
| 10 | — | i16 | 2 |  |
| 11 | — | i8/bytes | ? |  |
| 12 | — | i8/bytes | ? |  |
| 13 | — | i64 | 8 |  |
| 14 | — | i32 | 4 |  |
| 15 | — | i8/bytes | ? |  |
| 16 | — | i32 | 4 |  |

### 22000 — `ade_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |

### 22001 — `anp_0` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 22002 — `ls_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i16 | 2 |  |

### 22003 — `nq` — (unnamed)  [C2S]
declared length: `5`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i16 | 2 |  |

### 22004 — `axH` — (unnamed)  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 22092 — `axA` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 22093 — `Tx` — (unnamed)  [C2S]
declared length: `4`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 22094 — `la_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i64 | 8 |  |

### 22095 — `axf_0` — (unnamed)  [C2S]
declared length: `20`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i64 | 8 |  |

### 22097 — `OB` — (unnamed)  [C2S]
declared length: `4`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 22099 — `bw` — (unnamed)  [C2S]
declared length: `12`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |

### 23000 — `Jc` — (unnamed)  [C2S]
declared length: `9`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |

### 23001 — `abn_0` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 23002 — `wf_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 23003 — `ajw_0` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 23004 — `amh_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i8 | 1 |  |

### 23006 — `azl_0` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 23008 — `KL` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 23009 — `aow_2` — (unnamed)  [C2S]
declared length: `16`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |

### 23101 — `bm_1` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 23102 — `ada_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 23103 — `atj_0` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 23104 — `aLi` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i8 | 1 |  |

### 23106 — `ads_2` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 23108 — `M` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 23110 — `tb_2` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i16 | 2 |  |
| 5 | — | i16 | 2 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i64 | 8 |  |

### 23112 — `aku_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 23114 — `acz_2` — (unnamed)  [C2S]
declared length: `n3`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i64 | 8 |  |
| 6 | — | i8/bytes | ? |  |

### 23116 — `aex_0` — (unnamed)  [C2S]
declared length: `n3`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i64 | 8 |  |

### 25000 — `az` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 26300 — `wu_2` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i8 | 1 |  |
| 3 | — | i8 | 1 |  |
| 4 | — | i32 | 4 |  |

### 26301 — `hk_1` — (unnamed)  [C2S]
declared length: `9`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |

### 26302 — `pu_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |

### 26303 — `bl_1` — (unnamed)  [C2S]
declared length: `10`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |

### 26304 — `gz_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 26305 — `vT` — (unnamed)  [C2S]
declared length: `9`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |

### 26307 — `mz_0` — (unnamed)  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 26310 — `nx_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |

### 26312 — `axr_0` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 26313 — `aju_1` — (unnamed)  [C2S]
declared length: `10 + 8 * this.dRv.size(`
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i64 | 8 |  |

### 26314 — `ahV` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i8 | 1 |  |
| 3 | — | i64 | 8 |  |

### 26321 — `nv_0` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 26330 — `alv_1` — (unnamed)  [C2S]
declared length: `6`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i16 | 2 |  |

### 26331 — `x_0` — (unnamed)  [C2S]
declared length: `8`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 26332 — `azb_0` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 26333 — `uz_0` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 26334 — `aiw_1` — (unnamed)  [C2S]
declared length: `1`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |

### 27500 — `dp_0` — (unnamed)  [C2S]
declared length: `n3`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 27501 — `azd_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i16 | 2 |  |
| 7 | — | i32 | 4 |  |
| 8 | — | i32 | 4 |  |
| 9 | — | i32 | 4 |  |
| 10 | — | i32 | 4 |  |
| 11 | — | i32 | 4 |  |
| 12 | — | i32 | 4 |  |
| 13 | — | i8 | 1 |  |

### 27502 — `pc_1` — (unnamed)  [C2S]
declared length: `n3`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i32 | 4 |  |

### 27503 — `ij_1` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |

### 27504 — `vg_1` — (unnamed)  [C2S]
declared length: `n3`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 27505 — `aka_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i32 | 4 |  |
| 8 | — | i16 | 2 |  |
| 9 | — | i32 | 4 |  |
| 10 | — | i32 | 4 |  |
| 11 | — | i32 | 4 |  |
| 12 | — | i32 | 4 |  |
| 13 | — | i32 | 4 |  |
| 14 | — | i32 | 4 |  |
| 15 | — | i32 | 4 |  |

### 27506 — `qk_2` — (unnamed)  [C2S]
declared length: `n5 += 4`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i8/bytes | ? |  |
| 4 | — | i8/bytes | ? |  |
| 5 | — | i16 | 2 |  |

### 27507 — `uj_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i32 | 4 |  |
| 8 | — | i32 | 4 |  |
| 9 | — | i32 | 4 |  |
| 10 | — | i32 | 4 |  |
| 11 | — | i32 | 4 |  |
| 12 | — | i8 | 1 |  |
| 13 | — | i32 | 4 |  |
| 14 | — | i32 | 4 |  |
| 15 | — | i32 | 4 |  |
| 16 | — | i32 | 4 |  |
| 17 | — | i32 | 4 |  |
| 18 | — | i32 | 4 |  |
| 19 | — | i8 | 1 |  |
| 20 | — | i32 | 4 |  |
| 21 | — | i32 | 4 |  |
| 22 | — | i32 | 4 |  |
| 23 | — | i32 | 4 |  |
| 24 | — | i32 | 4 |  |
| 25 | — | i32 | 4 |  |
| 26 | — | i8 | 1 |  |

### 27508 — `aa_2` — (unnamed)  [C2S]
declared length: `n3`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 27509 — `jw_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i32 | 4 |  |
| 8 | — | i32 | 4 |  |
| 9 | — | i32 | 4 |  |
| 10 | — | i16 | 2 |  |
| 11 | — | i8 | 1 |  |

### 27510 — `aid_1` — (unnamed)  [C2S]
declared length: `n3`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |

### 27511 — `anc_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i64 | 8 |  |
| 6 | — | i64 | 8 |  |
| 7 | — | i64 | 8 |  |
| 8 | — | i64 | 8 |  |

### 27512 — `ow_2` — (unnamed)  [C2S]
declared length: `n3`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i32 | 4 |  |

### 27513 — `xn_2` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i16 | 2 |  |
| 4 | — | i64 | 8 |  |
| 5 | — | i32 | 4 |  |

### 27514 — `ck_2` — (unnamed)  [C2S]
declared length: `n5`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |

### 27515 — `amu_0` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i16 | 2 |  |
| 8 | — | i8 | 1 |  |

### 27525 — `zz_0` — (unnamed)  [C2S]
declared length: `1 + this.zs.length + 1 + 1 + 1 + 2 + this.zw.length`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i8/bytes | ? |  |
| 4 | — | i8/bytes | ? |  |
| 5 | — | i16 | 2 |  |
| 6 | — | i8/bytes | ? |  |

### 27526 — `jg_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |
| 2 | — | i8 | 1 |  |
| 3 | — | i8 | 1 |  |
| 4 | — | i16 | 2 |  |
| 5 | — | i64 | 8 |  |

### 27527 — `gc_0` — (unnamed)  [C2S]
declared length: `1 + this.zs.length + 8 + 1`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8/bytes | ? |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i64 | 8 |  |
| 3 | — | i8/bytes | ? |  |

### 27528 — `eq_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 27529 — `bl` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 27551 — `aib_1` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 27552 — `amk_0` — (unnamed)  [S2C]
_(no simple fields detected — empty body or complex; see source)_

### 28601 — `wa_2` — (unnamed)  [C2S]
_(no simple fields detected — empty body or complex; see source)_

### 28602 — `ng_2` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i8 | 1 |  |
| 3 | — | i8 | 1 |  |
| 4 | — | i16 | 2 |  |
| 5 | — | i8 | 1 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i32 | 4 |  |
| 8 | — | i32 | 4 |  |
| 9 | — | i32 | 4 |  |
| 10 | — | i32 | 4 |  |
| 11 | — | i8 | 1 |  |

### 28603 — `ayQ` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i16 | 2 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i8/bytes | ? |  |

### 28604 — `auE` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 28605 — `bi_2` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 28606 — `aik` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 28607 — `ago_0` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |
| 3 | — | i32 | 4 |  |

### 28608 — `dy_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |

### 28609 — `bt_0` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |

### 28610 — `de_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 28611 — `ly_1` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i16 | 2 |  |

### 28612 — `DR` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i16 | 2 |  |
| 2 | — | i8 | 1 |  |

### 28614 — `azj_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 28616 — `kw_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i8 | 1 |  |

### 28617 — `afg_2` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 28618 — `ahd_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |

### 28620 — `Yq` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |
| 1 | — | i64 | 8 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i64 | 8 |  |
| 4 | — | i32 | 4 |  |
| 5 | — | i32 | 4 |  |
| 6 | — | i32 | 4 |  |
| 7 | — | i64 | 8 |  |

### 28622 — `uw_2` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |

### 28630 — `dg_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |

### 28633 — `kx_2` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |

### 28634 — `acn_2` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 28635 — `aeC` — (unnamed)  [C2S]
declared length: `n2`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8/bytes | ? |  |
| 2 | — | i8/bytes | ? |  |
| 3 | — | i64 | 8 |  |

### 28636 — `aig_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i8 | 1 |  |

### 28644 — `aaj_0` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |

### 28646 — `aNq` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i64 | 8 |  |

### 28648 — `df_1` — (unnamed)  [S2C]
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i8 | 1 |  |

### 28649 — `alf_0` — (unnamed)  [C2S]
declared length: `n3`
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i64 | 8 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i8/bytes | ? |  |

### 28650 — `IL` — (unnamed)  [S2C]
note: _contains loop/collection - variable-length, verify source_
| # | field | type | bytes | note |
|--:|-------|------|------:|------|
| 0 | — | i32 | 4 |  |
| 1 | — | i32 | 4 |  |
| 2 | — | i32 | 4 |  |
| 3 | — | i32 | 4 |  |
| 4 | — | i32 | 4 |  |
