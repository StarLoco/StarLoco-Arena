# Changelog

## [0.6.0](https://github.com/StarLoco/StarLoco-Arena/compare/v0.5.1...v0.6.0) (2026-09-04)


### Features

* **ai:** AP efficiency, safer positioning, hazard-aware movement (item 18) ([9cd0a5f](https://github.com/StarLoco/StarLoco-Arena/commit/9cd0a5fa91767a3f2820d934c9c6b8a28804b234))
* **ai:** heal wounded allies (item 18) - the missing half of spell targeting ([eccd34e](https://github.com/StarLoco/StarLoco-Arena/commit/eccd34e38dc6dce363dc0a03ff2076357b9e4633))
* **ai:** honest knowledge model + human-like targeting (item 18) ([548767d](https://github.com/StarLoco/StarLoco-Arena/commit/548767d0f3569ab8104d49503e51335cf1728716))
* **chat:** send the real refusal errors for GM commands (3206, 3210) ([a373ff7](https://github.com/StarLoco/StarLoco-Arena/commit/a373ff7f6ddfc2f35396307754d5619a8101d513))
* **chat:** tell the player why a chat action was refused (3214), and correct my own triage ([a864236](https://github.com/StarLoco/StarLoco-Arena/commit/a864236cbc41f3b86a9610ba117ff8260a0be664))
* **config:** make the server-invented numbers configurable under rules: (item 7) ([75b7e5f](https://github.com/StarLoco/StarLoco-Arena/commit/75b7e5fe5df7caa7c0e5bf46a1cbbac44aa124d4))
* **emotes:** implement the emote system (4701/4700) (B-134) ([97e72a0](https://github.com/StarLoco/StarLoco-Arena/commit/97e72a02eeeaf41e7435bed7cc8ad543f7655d55))
* **fight:** fighters say "ouch !" on a critical hit (4902) (B-138) ([171b05c](https://github.com/StarLoco/StarLoco-Arena/commit/171b05cd2e323a9fc8cf20a642c74bea4555b161))
* **gm:** server-wide announcements via /ANNOUNCE (2070) ([092c9ff](https://github.com/StarLoco/StarLoco-Arena/commit/092c9ff51e7be3a3139a1fc07ce7a541fd37f5c5))
* **mcp:** let arena_up choose which server the client connects to ([5b286e2](https://github.com/StarLoco/StarLoco-Arena/commit/5b286e2fd9cfc998b8b4f9009f4bd7756f5e9b78))
* **overworld:** implement /resetPosition, and decode the rest of the 4xxx cluster (B-135) ([40976a5](https://github.com/StarLoco/StarLoco-Arena/commit/40976a527cda6fa5204ad439eb8dca0101b2a0a5))
* **overworld:** implement sitting - /sit, /stand, stand on move (B-139) ([ed8b32d](https://github.com/StarLoco/StarLoco-Arena/commit/ed8b32de72abb8f205ca0b7901ed526ec56fa90e))
* **overworld:** same-world teleports use 4510 instead of re-entering the instance (B-142) ([df2d02e](https://github.com/StarLoco/StarLoco-Arena/commit/df2d02e57ffd17faf2838f7cff6534abef4867a8))
* **security:** connection caps, timeouts, login throttle, and opt-out auto-admin ([087ff89](https://github.com/StarLoco/StarLoco-Arena/commit/087ff8904deebe757f976b2be3b635121f781fe0))
* **social:** cap the friend/ignore lists and refuse with 3216 (B-133) ([8722a42](https://github.com/StarLoco/StarLoco-Arena/commit/8722a42cb8846980d3f237967e87bed6d7198c5c))
* **stats:** populate the player statistics report (2401) (B-141) ([58b10a6](https://github.com/StarLoco/StarLoco-Arena/commit/58b10a6d68931b1a27bcf2e8ce6b9bb3493235b2))
* **web:** add legal, privacy and terms pages, and self-service erasure ([5ad9c49](https://github.com/StarLoco/StarLoco-Arena/commit/5ad9c491922474aa263f520680f58e5c63f653af))
* **web:** complete the legal pages against the obligations they missed ([6293b44](https://github.com/StarLoco/StarLoco-Arena/commit/6293b4443fdb8033976b8cfbcf4688b64a8d113f))
* **web:** green online dot, fighter-framed CTA, tidier footer, no grid ([21d053b](https://github.com/StarLoco/StarLoco-Arena/commit/21d053be92ae96e04baf94c9833c7f15610387d5))
* **web:** higher-quality logo, with a retina variant for the hero ([5b0ba03](https://github.com/StarLoco/StarLoco-Arena/commit/5b0ba03605aa1f62b8fed89854d83e3280479c71))
* **web:** make the portal white-label ([ad8232f](https://github.com/StarLoco/StarLoco-Arena/commit/ad8232fd18a298a0982a02c4d1e0f1edb6efae80))
* **web:** plain-http bug-report listener, because the 2012 client cannot do TLS ([01292f1](https://github.com/StarLoco/StarLoco-Arena/commit/01292f16fcb6518606f0f61ef016bb831874c0e6))
* **web:** put the locale in the URL (/en /fr /es /de) and use the logo ([90557ee](https://github.com/StarLoco/StarLoco-Arena/commit/90557ee249ea3a23681c315a443c97347143935b))
* **web:** real favicon from the AR mark, and a simpler hero headline ([973e12d](https://github.com/StarLoco/StarLoco-Arena/commit/973e12d573cee9b1fe881cf4ebfe32cb79f0dd8f))
* **web:** receive the client's own bug reports and triage them in the console ([18906b8](https://github.com/StarLoco/StarLoco-Arena/commit/18906b88a1810057f9c015bacb8c7857a80d4a2e))
* **web:** serve /discord and the login screen's account link ([9d593a3](https://github.com/StarLoco/StarLoco-Arena/commit/9d593a3fc9bb7e2d7161cd315a00c69516618a0e))
* **web:** translate the portal into FR, EN, ES and DE ([19ed900](https://github.com/StarLoco/StarLoco-Arena/commit/19ed900b1557071c9eea41a4b9c73ff26e693e33))
* **web:** trust X-Forwarded-For from configured proxies so rate limits stay per-visitor ([3a4b7b7](https://github.com/StarLoco/StarLoco-Arena/commit/3a4b7b76f5e6da354cc7cb62e4068212c762b3e5))


### Bug fixes

* **2v2:** tell the surviving partner when the other half disconnects (B-132) ([6063a85](https://github.com/StarLoco/StarLoco-Arena/commit/6063a85189148218bacbef6dee402d7c3058226f))
* **chat:** answer unknown slash-commands with the translated 3206 (B-140) ([bd32ea6](https://github.com/StarLoco/StarLoco-Arena/commit/bd32ea60abf6a3ca389a1e3a5432ed8989b9c070))
* **config:** stop publishing a client download link by default ([23e372f](https://github.com/StarLoco/StarLoco-Arena/commit/23e372f71b9fde977c0f82d88afcd2140cc257ff))
* **deps:** patch 8 reachable CVEs, and document the plaintext-transport limit ([236fdb3](https://github.com/StarLoco/StarLoco-Arena/commit/236fdb36981c5fac7069bad4b8de81b0a998214f))
* **docker:** bug-report screenshots failed with permission denied ([70df327](https://github.com/StarLoco/StarLoco-Arena/commit/70df3274ff7d800e3e5fdbae298deaa68e9159dd))
* **emotes:** address 4700 by AoI membership, not proximity (B-136) ([fe5c983](https://github.com/StarLoco/StarLoco-Arena/commit/fe5c983076e8f792499c567b1aee5286455523b3))
* **security:** close 6021 team-preset IDOR and roster duplication ([018d33b](https://github.com/StarLoco/StarLoco-Arena/commit/018d33b32ec0d869b4866d061212dd8056859786))
* **security:** close the last client-enforced-rule gaps, and prove the rest are unreachable ([3b85cf3](https://github.com/StarLoco/StarLoco-Arena/commit/3b85cf3157571d0a6771b49f5f4eb9dca87b92bb))
* **security:** close the last combat validation gaps ([3eac079](https://github.com/StarLoco/StarLoco-Arena/commit/3eac079f7c3ec39abd4e1a05224d236e51508bb5))
* **security:** close the last small items - guild orphaning, roster cap, 28649, currency overflow ([0409728](https://github.com/StarLoco/StarLoco-Arena/commit/04097283f2ef31746033393365b013fbafb5d3c9))
* **security:** close the remaining Medium and Low findings ([3b91c37](https://github.com/StarLoco/StarLoco-Arena/commit/3b91c37eabfa3580707bf9f538a15bc458f66de7))
* **security:** close two unbounded card-minting holes (22003 and 5450) ([86b234b](https://github.com/StarLoco/StarLoco-Arena/commit/86b234ba39b36015d9b801464f1f6256fe433793))
* **security:** contain panics and close two remote full-server crashes ([7682bf3](https://github.com/StarLoco/StarLoco-Arena/commit/7682bf351f21546cd643ae87b5b493b4049c4584))
* **security:** enforce the card, equipment and inventory rules the client checks ([80f2aa7](https://github.com/StarLoco/StarLoco-Arena/commit/80f2aa764bac81b464570d72b86b3a9af08a6042))
* **security:** enforce the roster and combat rules retail enforced server-side ([87c10e9](https://github.com/StarLoco/StarLoco-Arena/commit/87c10e95ad6da1efad0b7f85f0217f6e14e05840))
* **security:** final sweep - progression, tournaments, guilds, debug endpoint ([6b54650](https://github.com/StarLoco/StarLoco-Arena/commit/6b54650fac90ea8631d350fa4e917a9db0f76b92))
* **security:** reject empty/unusable fighter, team, guild and rank names ([a179b29](https://github.com/StarLoco/StarLoco-Arena/commit/a179b29ebd6554c65e4add8b50a4eaae8e8ee554))
* **security:** retire the post-fight staleness class, add packet-rate control ([c271ae9](https://github.com/StarLoco/StarLoco-Arena/commit/c271ae99dd58693c67bd4e0e06b96d457b95fe3b))
* **security:** sanitize and throttle channel chat (3140) ([d08e45b](https://github.com/StarLoco/StarLoco-Arena/commit/d08e45bddc2e8b6951fe57779f5af814c6c9b858))
* **security:** stop duplicate logins leaking coach state into every subsystem ([ec69ce7](https://github.com/StarLoco/StarLoco-Arena/commit/ec69ce7fb587cd06c86288a4d2d1e6556c695a5e))
* **security:** validate coach names server-side and stop coach-creation replay ([196073d](https://github.com/StarLoco/StarLoco-Arena/commit/196073df54d52d65e41bc81c095b1937b63ad80c))
* **security:** validate spell loadouts against the fighter's breed (6011/6001) ([6dd5663](https://github.com/StarLoco/StarLoco-Arena/commit/6dd5663ccad4c9ea06bba466a9de9c2297484151))
* **store:** coach creation failed on postgres (COLLATE NOCASE is SQLite-only) ([a65b170](https://github.com/StarLoco/StarLoco-Arena/commit/a65b170509767c93ce104e24062b650aea533cfb))
* **team:** acknowledge a deleted preset so the client drops it (B-130) ([6561c0f](https://github.com/StarLoco/StarLoco-Arena/commit/6561c0f0ab3370186d3aceb12562dc97f0db90a4))
* **team:** refuse a duplicate preset name with the client's own error (B-131) ([c15b4d8](https://github.com/StarLoco/StarLoco-Arena/commit/c15b4d82a9c6a2ace2b117cc427654938badc87e))
* **web:** add HSTS behind HTTPS, warn on secure_cookies, document the portal audit ([76dc838](https://github.com/StarLoco/StarLoco-Arena/commit/76dc838687dbb5008c0746f871ea753607ad1efd))
* **web:** keep the contact address readable behind a CDN ([5f8cadb](https://github.com/StarLoco/StarLoco-Arena/commit/5f8cadb726964d910d33ceaaad6990704e4b9cc0))
* **web:** keep the hero headline on one line ([3499199](https://github.com/StarLoco/StarLoco-Arena/commit/34991999a1625be7e5c21ea66de242fc94d43647))
* **web:** run the hero art the full width, fading into both borders ([bfc6a98](https://github.com/StarLoco/StarLoco-Arena/commit/bfc6a983fbf79156862479fa7cb50d9d460676f6))
* **web:** stop cropping the header logo, and rebuild the hero around the art ([d91049c](https://github.com/StarLoco/StarLoco-Arena/commit/d91049ccf4dc185b470674249d9b0991cb095925))
* **web:** the Home link was a dead end once signed in, and stop printing the game address ([418e8a1](https://github.com/StarLoco/StarLoco-Arena/commit/418e8a11ffd768c1008fc95055f3d7808b43ebfa))


### Performance

* **e2e:** run the wire suite in parallel - 141s to ~15s ([b6328bf](https://github.com/StarLoco/StarLoco-Arena/commit/b6328bf0d2c393f64a71ee34fd95ca17d484df77))


### Documentation

* **bugs:** B-132 - bound the 6029 claim with live-client evidence ([f6e5e62](https://github.com/StarLoco/StarLoco-Arena/commit/f6e5e62b499199cd4bea055cfac9ac78db35a47b))
* **bugs:** B-137 - ran the experiment; the duplicate is rejected, not destructive ([bafd643](https://github.com/StarLoco/StarLoco-Arena/commit/bafd643ebf7204b7cd1e803574cbbe8dacb05a55))
* **bugs:** file B-137 - same-world re-enter duplicates interactive elements (open) ([a16c21b](https://github.com/StarLoco/StarLoco-Arena/commit/a16c21b1a417c593cd37e7fa24f61e073330390d))
* **client-testing:** in-fight frames cannot be live-validated here, and why ([0468172](https://github.com/StarLoco/StarLoco-Arena/commit/0468172e520b871c943d4579caaf19e7d8de6f56))
* in-fight frames ARE live-validatable - the recorded limit was wrong ([122287b](https://github.com/StarLoco/StarLoco-Arena/commit/122287b861b3979b025d4f60dd56e2e844b4f8a6))
* **inventory:** close item 14/6 - server-assigned card uids are impossible, with proof ([cbdd9ea](https://github.com/StarLoco/StarLoco-Arena/commit/cbdd9eaa08b83b4089aa578be190efd8fba5accc))
* **opcodes:** decode 6032 as the COACH condition map, and close out the dx_2 family ([f4adf78](https://github.com/StarLoco/StarLoco-Arena/commit/f4adf78904e584ece9ecebe87a94b8e07fc7c879))
* **opcodes:** decode the 0xx connection-stage cluster, and correct the "console is dead" claim ([bf088f2](https://github.com/StarLoco/StarLoco-Arena/commit/bf088f28ede17dff4c8f5375bbab4176a385d7b8))
* **opcodes:** decode the scattered 2xxx/5xxx/8xxx/17xxx/25xxx rows ([6ebc120](https://github.com/StarLoco/StarLoco-Arena/commit/6ebc12021de42d867bd89d141ab5b942b5784d33))
* **opcodes:** document the 28xxx tournament cluster, and correct a correction ([056d909](https://github.com/StarLoco/StarLoco-Arena/commit/056d909d38f5649ed7eb298cccb05b60c2bd34cc))
* **opcodes:** finish the last vague rows; the dead-claim test caught one of them ([7409278](https://github.com/StarLoco/StarLoco-Arena/commit/740927812b02396641e8ec6d5d6fe018cf54b127))
* **opcodes:** give the fight-setup and actor-batch rows real reasons ([d6e495f](https://github.com/StarLoco/StarLoco-Arena/commit/d6e495f8a703995aefdd22b63d4d82315dcbb36d))
* **opcodes:** pin 6014 and stop short of implementing it ([fa0a606](https://github.com/StarLoco/StarLoco-Arena/commit/fa0a606d893fae41cdde5f55d8591e81a38673bf))
* **opcodes:** read the dx_2 team-preset family and record what each reply does ([45f1098](https://github.com/StarLoco/StarLoco-Arena/commit/45f10988b7f7f28cc7b3b9ab3c6b8022b024fca1))
* **opcodes:** record the negative results, starting with chat flood having no S2C frame ([cdd3dd3](https://github.com/StarLoco/StarLoco-Arena/commit/cdd3dd3a1f3cf8948d39e58a4ea159e1370c0bdf))
* **opcodes:** the channel family is a no-op consumer, not a feature gap ([4069c78](https://github.com/StarLoco/StarLoco-Arena/commit/4069c78dfe46a49c5d581d28fff7da0919230745))
* **opcodes:** triage all 100 unimplemented opcodes into work and non-work ([6745ca9](https://github.com/StarLoco/StarLoco-Arena/commit/6745ca940618d5d5747364efdef2b0bc9e861a0e))
* record B-125 (postgres COLLATE NOCASE) and B-126 (proxied rate limits) ([f120dd2](https://github.com/StarLoco/StarLoco-Arena/commit/f120dd293bfa39acabd4c70ee235fce175bd4ece))
* record B-127 and the bug-report feature; MCP points bugReportURL at the selected server ([c79475c](https://github.com/StarLoco/StarLoco-Arena/commit/c79475c5d3de2ec692a9b38d8489f834ef3796ac))
* **roadmap:** close item 18, and catch the stale claim that closing it exposed ([a6f35e7](https://github.com/StarLoco/StarLoco-Arena/commit/a6f35e7f061b19eb218bf7c1ce685472c949e005))
* **roadmap:** finish item 14 - pin the 5203 blocker in CI and repair the item ([7403fcf](https://github.com/StarLoco/StarLoco-Arena/commit/7403fcfd1156ead7d743414d9c45248f2a03fa6d))
* **security:** record the client-enforced-rule hunt and correct the budget entry ([fc35541](https://github.com/StarLoco/StarLoco-Arena/commit/fc355417209a54a0f9013ba792cd83ae0abcb041))
* **security:** record the final sweep and a fifth testing rule ([354eea7](https://github.com/StarLoco/StarLoco-Arena/commit/354eea7849c4fc90a5e5a957592a2bf02edacde7))
* **security:** record the full audit - fixed, and explicitly NOT fixed ([5be92f5](https://github.com/StarLoco/StarLoco-Arena/commit/5be92f5112f99d846c06881b17b89ad8c59caac8))
* **security:** record the remaining open items honestly ([0670823](https://github.com/StarLoco/StarLoco-Arena/commit/06708230135e83459bb50f8718721cacee78512f))
* **security:** record the second pass; audit is fully remediated ([77b260b](https://github.com/StarLoco/StarLoco-Arena/commit/77b260b17953130dbfff6019fdd3ec877d68fcd4))
* **stats:** 2401 is not Java-serialized - correct the estimate that was blocking it ([f8f4b52](https://github.com/StarLoco/StarLoco-Arena/commit/f8f4b52164147c5c4ce08a9502d038af9e6ccb40))
* **status:** record the compliance pass and correct the mirror-link claims ([28d021a](https://github.com/StarLoco/StarLoco-Arena/commit/28d021ae082cd2c5c2fa94f7098233540027ce6a))
* **web:** make the client wording true when an operator links a copy ([3c6093f](https://github.com/StarLoco/StarLoco-Arena/commit/3c6093fa69167d969df04bd749751e94262c1e32))
* **web:** state plainly that we provide a pre-configured client ([06d8525](https://github.com/StarLoco/StarLoco-Arena/commit/06d8525a6baae958b811c5dbf4eda928a71741c1))

## [0.5.1](https://github.com/StarLoco/StarLoco-Arena/compare/v0.5.0...v0.5.1) (2026-08-27)


### Documentation

* **opcodes:** explain every unimplemented C2S, and correct the 28617/28618 misfiling ([e6bece6](https://github.com/StarLoco/StarLoco-Arena/commit/e6bece6e1212c1d88a2e079ce082232bae24dbc5))

## [0.5.0](https://github.com/StarLoco/StarLoco-Arena/compare/v0.4.0...v0.5.0) (2026-08-27)


### Features

* **2v2:** a duo survives a restart - launch resolves the ally from the preset ([6528319](https://github.com/StarLoco/StarLoco-Arena/commit/65283198b39adbf731591f25da2cf02688643a40))
* **2v2:** a real 2v2 fight - four coaches, two a side (item 30) ([84a2f67](https://github.com/StarLoco/StarLoco-Arena/commit/84a2f67a0b0817fed193e666c8d917e24eaebe45))
* **2v2:** seat four coaches - pedestals, arena capacity, and the duo joining a side ([d507081](https://github.com/StarLoco/StarLoco-Arena/commit/d507081ce1c8b23274d9e403aa035e0f4f23964e))
* **2v2:** the duo becomes a real team preset the client can launch ([f95769f](https://github.com/StarLoco/StarLoco-Arena/commit/f95769f32d2f31571632feb763de71671f8a58e5))
* **2v2:** the team-up handshake - and the production 2v2 UI I said did not exist ([4392dae](https://github.com/StarLoco/StarLoco-Arena/commit/4392daecf35349744dc877e5481f73d7f5fa1fd8))
* **achievements:** decode types 800/801/802 and evaluate unlocks (B-106) ([c408dd9](https://github.com/StarLoco/StarLoco-Arena/commit/c408dd98370c627b488f00a4aa8f8b1cd413a00e))
* **achievements:** make the tome grow-only and send it to the client (B-106) ([b5d1c50](https://github.com/StarLoco/StarLoco-Arena/commit/b5d1c500a5feea11bc1593da281ec8e3b5a59b12))
* **ai:** give challenge demons a real spell repertoire ([c269db4](https://github.com/StarLoco/StarLoco-Arena/commit/c269db49c747b3b5f79bc2bd9e47c74b0df29e0b))
* **ai:** play from a spell repertoire instead of one fixed spell ([1a8d6c7](https://github.com/StarLoco/StarLoco-Arena/commit/1a8d6c714000965abcc91a438c19b58b8987d714))
* **ai:** spend leftover AP on close combat ([319510b](https://github.com/StarLoco/StarLoco-Arena/commit/319510b071894676f852979ca1344d26af714b32))
* **ai:** stop walking onto Killer tiles (B-086 follow-up) ([fd07e85](https://github.com/StarLoco/StarLoco-Arena/commit/fd07e854d9f8fbf0fd57b5f1036e633d10e67d92))
* **chat:** serve every pipe, and enforce the client's own limits (B-104) ([efab28e](https://github.com/StarLoco/StarLoco-Arena/commit/efab28e481bc4eb75dbd2c15065efde951b7644f))
* **effects:** resolve the last nine effect action ids - 100% of shipped rows ([44c315b](https://github.com/StarLoco/StarLoco-Arena/commit/44c315b9cebefccea5bbe7ed2a522076f25cd81d))
* **elements:** generate the overworld element table from the client's env layers ([4090685](https://github.com/StarLoco/StarLoco-Arena/commit/4090685c4552028d44654388bdcb843130f4b3af))
* **fight:** restore buff icons on reconnect and spectate (item 11) ([09a2056](https://github.com/StarLoco/StarLoco-Arena/commit/09a2056cc68333adbbe4375441a13e52722e5b4b))
* **fusion:** enforce the target's fusion cost and the altar's quality ([0fe2414](https://github.com/StarLoco/StarLoco-Arena/commit/0fe2414d319d24eff3a743bd04f4dcfc8f0ac5ba))
* **fusion:** pick the altar by position - the six labs are six tiers ([a219012](https://github.com/StarLoco/StarLoco-Arena/commit/a21901257c30e187cda58df9a48389ce7a9bad39))
* **game:** complete the "triggeree en zone" effect family (165-169, 177) ([32b498a](https://github.com/StarLoco/StarLoco-Arena/commit/32b498a45f9ba5282ba969615fa7f0b266a565ff))
* **gamedata:** decode equipment pools (type 251) and enforce Kanodo entitlement ([6529808](https://github.com/StarLoco/StarLoco-Arena/commit/6529808fad8d2651bace5f0a4e0f62a3a2c3148a))
* **gamedata:** decode record type 360, and correct roadmap item 24 ([5267f91](https://github.com/StarLoco/StarLoco-Arena/commit/5267f91af5b5e024cf0f0da67feb630b5c3929fc))
* **gamedata:** decode the Sphere Board (Kanodo), types 900 and 901 ([540a6ca](https://github.com/StarLoco/StarLoco-Arena/commit/540a6ca5e7be7cb63c17ad34ff6f4990cb622fe6))
* **gamedata:** decode tournament types 1000 + 1001 ([7a1f55c](https://github.com/StarLoco/StarLoco-Arena/commit/7a1f55c8996d8f88195cba4255c3d3b9712b16d5))
* **gamedata:** decode type 1100 - and there is no fusion recipe table ([db73e64](https://github.com/StarLoco/StarLoco-Arena/commit/db73e6481acf9d73a2e87187fd21137dc7874c8a))
* **game:** enforce np_1 fight-start effects and victory conditions ([224371c](https://github.com/StarLoco/StarLoco-Arena/commit/224371cd8604d8fc8f3a70234fd237a66eb2386a))
* **game:** enforce spell-level target masks ([8fad6b4](https://github.com/StarLoco/StarLoco-Arena/commit/8fad6b406b9afe78eb00c24e4b7f229b7f99504e))
* **game:** implement AoE shape 8 and the cross's 2- and 4-param arities ([b0c82c6](https://github.com/StarLoco/StarLoco-Arena/commit/b0c82c6fe181b29a451f911dd5892d49b498e3f7))
* **game:** pair coaches within a widening rating band ([9c9b144](https://github.com/StarLoco/StarLoco-Arena/commit/9c9b14449a377b8dd04386692214ebdb8198d632))
* **guilds:** a clan must be active (5 members) to hold an island ([f533aa7](https://github.com/StarLoco/StarLoco-Arena/commit/f533aa7aa50c1a2fddc2ef38ad82baa1cb14e252))
* **guilds:** clan islands on the retail rule - one per demon, held by its top clan ([f178ee3](https://github.com/StarLoco/StarLoco-Arena/commit/f178ee35630ffa79e1917c6e08e13690e2fbd5c3))
* **guilds:** clan storage, ranks/rights, and the 0x20 blob the client gates on ([b2497ec](https://github.com/StarLoco/StarLoco-Arena/commit/b2497ec48306489ba565191c740642e094340745))
* **guilds:** clan-island Zaap, and demon affiliation is not a guild feature ([0f2a665](https://github.com/StarLoco/StarLoco-Arena/commit/0f2a665c3e049eda63755f0468de2098ae8c9cdb))
* **guilds:** create, invite, accept, member list - a clan works end to end ([1b724d8](https://github.com/StarLoco/StarLoco-Arena/commit/1b724d8c14bd2cba18d8948109fab485c23491e7))
* **guilds:** fill the reserved clan tag column and the clan ladder ([02ad48c](https://github.com/StarLoco/StarLoco-Arena/commit/02ad48cc29e31f3ba3a51135ef0dc8868be266a8))
* **guilds:** kick, quit, destroy, rank CRUD and member stats ([b82a680](https://github.com/StarLoco/StarLoco-Arena/commit/b82a680dbbc48c3d2b4d729b213f6e30d3ecc080))
* **harness:** add a real double-click, and prove it is one ([7e3b81d](https://github.com/StarLoco/StarLoco-Arena/commit/7e3b81db59eb93131ea8b051575127339d43730b))
* **npc:** close item 27 - dialog trees are client-only; fix GM teleport (B-107) ([8f26aa8](https://github.com/StarLoco/StarLoco-Arena/commit/8f26aa88f7883503e7dcd50e2082da32ae471497))
* **protocol:** serve the evolution opponent search (23001-23008) (B-098) ([1ea824f](https://github.com/StarLoco/StarLoco-Arena/commit/1ea824f5255f52cbac964c0ec6df9ed95b06e752))
* **spheres:** bought Kanodo nodes now change the fighter in fight ([07fa8f0](https://github.com/StarLoco/StarLoco-Arena/commit/07fa8f0eb536635f4d83f570c5ab370ea9450a69))
* **spheres:** implement buying a Kanodo sphere (C2S 23009) ([bdd366a](https://github.com/StarLoco/StarLoco-Arena/commit/bdd366a4f04256a443b238fd72c551e936fb347f))
* **spheres:** serve each fighter's Kanodo board, cursor and owned nodes ([4850a27](https://github.com/StarLoco/StarLoco-Arena/commit/4850a278d0a02c4bcb75980191f5168b2a6eb2a3))
* **store:** versioned schema migrations, replacing the bare AutoMigrate call ([d6b68dc](https://github.com/StarLoco/StarLoco-Arena/commit/d6b68dc7958011a79f5df9742d27232f5ea5d58c))
* **tournaments:** announce the final (28620); 28622 is dead too ([e115883](https://github.com/StarLoco/StarLoco-Arena/commit/e115883bf22ac5d3986dbcefe8aa0621c56919a3))
* **tournaments:** announce the opponent-search period (28630) - the gate that makes the Tournois tab reachable ([795023f](https://github.com/StarLoco/StarLoco-Arena/commit/795023f9600687931d05fcc379e6acaa7fe56bc1))
* **tournaments:** match results advance up the bracket (item 32) ([3bfe1dc](https://github.com/StarLoco/StarLoco-Arena/commit/3bfe1dc693f235c57e11bf8659579b32228e0d09))
* **tournaments:** pair by FIXTURE, not by tournament (item 32) ([f2c3f19](https://github.com/StarLoco/StarLoco-Arena/commit/f2c3f19e6489b8c3817b488cf5bf4d8890a6b6b7))
* **tournaments:** pay the prize the client already advertises (B-123) ([c9591c4](https://github.com/StarLoco/StarLoco-Arena/commit/c9591c4a79e34425f468fe43ec1b0f8a730e3454))
* **tournaments:** persist the bracket (schema 7) ([a5ab978](https://github.com/StarLoco/StarLoco-Arena/commit/a5ab978319b8091c1f9ff6d52b938af9110082b5))
* **tournaments:** real opponent-search schedule + countdown (28644, schema 8) ([25f8326](https://github.com/StarLoco/StarLoco-Arena/commit/25f8326f58d89969f3e8b37b6e57888a2c99f3bc))
* **tournaments:** serve a real bracket instead of "tree unavailable" (item 32) ([bdfb96e](https://github.com/StarLoco/StarLoco-Arena/commit/bdfb96ec6e8ff1fc4d57e182fe9a0cf108d89aa1))
* **tournaments:** settle a closed search period by forfeit ([638209b](https://github.com/StarLoco/StarLoco-Arena/commit/638209b7b0d01b9437118e5b8c181ff385cf7f0c))
* **tournaments:** tell an unopposed entrant it has won (28648); admin family is dead code ([27deb4a](https://github.com/StarLoco/StarLoco-Arena/commit/27deb4a0aba8e4e66afc46dbb785dd42acac06c1))
* **tournaments:** tournament matches actually start (item 32) ([7cb7064](https://github.com/StarLoco/StarLoco-Arena/commit/7cb7064e92b9f45505e079eeaff34367a8384e41))
* **web:** full account portal, ported from v2.04 and restyled ([372c750](https://github.com/StarLoco/StarLoco-Arena/commit/372c750fe32bd0e4c434ff712bf49e77141c3377))
* **web:** manage tournaments from the admin console ([8d7f61a](https://github.com/StarLoco/StarLoco-Arena/commit/8d7f61ab2a41927f4d3afe577aab80ad4aeb60d9))


### Bug fixes

* **2v2:** duo presets carry a type the client renders, and refusals say why ([b795d6a](https://github.com/StarLoco/StarLoco-Arena/commit/b795d6a2966307c2bf9250732788a087ff4fc535))
* **2v2:** duos and solo coaches no longer share a matchmaking queue ([bf31517](https://github.com/StarLoco/StarLoco-Arena/commit/bf315173eba3fcc1b78e17d9f1d4fa5d71dd075e))
* **achievements:** answer 22001 so the achievements tab can open (B-105) ([c88a2b1](https://github.com/StarLoco/StarLoco-Arena/commit/c88a2b119056d1a7b62c2e101431e3aa47e95aca))
* **ai:** do not walk through or onto sudden-death cells (B-087) ([ce1a500](https://github.com/StarLoco/StarLoco-Arena/commit/ce1a5000ac01d311899353094ad9f7764cae8db3))
* **ai:** never aim a support spell at an enemy (B-084) ([9ddbb8b](https://github.com/StarLoco/StarLoco-Arena/commit/9ddbb8bcc440927528103160ebe6c51322c17b35))
* **ai:** never splash your own team with an area spell (B-085) ([cd9fe0b](https://github.com/StarLoco/StarLoco-Arena/commit/cd9fe0bc2579550e2b70c16c325c0e509e880916))
* **ai:** one predicate for "can I cast this from there" - the AI froze ([d387602](https://github.com/StarLoco/StarLoco-Arena/commit/d3876021bb934ee55bfb7e1dbc08700cf4485c97))
* **arena-mcp:** capture the client's stdout, and retract a bad conclusion ([6b4e486](https://github.com/StarLoco/StarLoco-Arena/commit/6b4e486bab3e8262c6d9523aa68a7c1293f76436))
* **chat:** deliver Trade chat, and retire the vestigial channel family (B-103) ([93cc0ee](https://github.com/StarLoco/StarLoco-Arena/commit/93cc0ee1f3f36c958e5a42b339a352b2fcfd58df))
* **combat:** Nx is turns ELAPSED, and round-card effects must land after the turn begins ([2fff8be](https://github.com/StarLoco/StarLoco-Arena/commit/2fff8bea76bdbb71a82e3dd75ca24226d2135fff))
* **combat:** send the two 8120 blob parts the client cannot do without ([de3f5b9](https://github.com/StarLoco/StarLoco-Arena/commit/de3f5b9ec4f88b9b0799ee7f94737cb764dc4535))
* **e2e:** wait for the placement phase instead of assuming a fixed drain reached it ([dbe3475](https://github.com/StarLoco/StarLoco-Arena/commit/dbe34758f48ac2fc66c056b5398510e1a26a1528))
* **elements:** interactive objects are usable - one payload fix, one operator error ([03af773](https://github.com/StarLoco/StarLoco-Arena/commit/03af7736936f86b85a27c25b9a30b6f3b8a536a8))
* **elements:** stream interactive elements per chunk (B-108) ([64aa0d7](https://github.com/StarLoco/StarLoco-Arena/commit/64aa0d7aa1e78a38bf21514702eaf8c3c4aa531e))
* **elements:** un-inert every interactive object, and put two back on their cell (B-109) ([a6c8d97](https://github.com/StarLoco/StarLoco-Arena/commit/a6c8d97578ad21ac17f6691fc70508475f1a2fd5))
* emit SPELL ids in the 8000 coach-deck blob, not card ids (B-088) ([95f2f5f](https://github.com/StarLoco/StarLoco-Arena/commit/95f2f5f6372e76450e967c493d458272952c2a6c))
* **fight:** a knockout is not a death in evolution mode (B-097) ([439b59f](https://github.com/StarLoco/StarLoco-Arena/commit/439b59f829e8a2ef0ea457fda60d1dc91b10a400))
* **fighter:** close the create path's uncapped loadout hole ([1605bab](https://github.com/StarLoco/StarLoco-Arena/commit/1605babd8a979e24eb5a1f0fff002fcd0901e0bb))
* fusion consumed the player's CHOSEN card as fuel (B-089) ([6dce1c2](https://github.com/StarLoco/StarLoco-Arena/commit/6dce1c2c3a17a500a1657b22e8006e2839ed6f68))
* **game:** actually count play time and time in fight (B-092) ([feb83b3](https://github.com/StarLoco/StarLoco-Arena/commit/feb83b3793028988d356392c7395d0bf9fab786e))
* **game:** close two anti-cheat holes in placement and spell casting ([87831bb](https://github.com/StarLoco/StarLoco-Arena/commit/87831bbf32eeb6a11b3ec1756d0323918380f183))
* **game:** dispel no longer strips innate states; persist and send Coach.Standing ([3b89f59](https://github.com/StarLoco/StarLoco-Arena/commit/3b89f59b1b9c9d46edf1ca439c0f119b916b6e58))
* **game:** forced displacement now arms traps ([a3f0aef](https://github.com/StarLoco/StarLoco-Arena/commit/a3f0aef2a385443106dc3c9edb08d01eec3b89d8))
* **game:** retire the card flag that never existed (B-094) ([0bbaf37](https://github.com/StarLoco/StarLoco-Arena/commit/0bbaf375a1833f44b8170051a46d71306a9079f1))
* **game:** send fighter conditions in CREATE_FIGHT ([1f276aa](https://github.com/StarLoco/StarLoco-Arena/commit/1f276aa8741aefaef55f00ec3cea1f1f9b32c64e))
* **guilds:** actually grant the clan-island Zaap card, and revoke it when the island moves ([4592d0f](https://github.com/StarLoco/StarLoco-Arena/commit/4592d0f3f3003fcfdebde479bc29179507ef0cac))
* **guilds:** serve the clan islands' interactive elements ([cf0f73e](https://github.com/StarLoco/StarLoco-Arena/commit/cf0f73e45a33bbdd4a3937c71c47fb2c57319253))
* **loadout:** 6011's two blobs were swapped - fighters were saving spells as equipment ([b184392](https://github.com/StarLoco/StarLoco-Arena/commit/b184392768f883804d5d078fd2957e660379af2f))
* **protocol,game:** derive effective AP/MP; enforce the StringU8 127-byte limit ([4c6f8a9](https://github.com/StarLoco/StarLoco-Arena/commit/4c6f8a938a8170d6fab4a239d31d529915bf41e7))
* **protocol:** answer the tournament opponent search, refusing honestly (B-100) ([4569025](https://github.com/StarLoco/StarLoco-Arena/commit/45690253718654fbde377f885daf13c13c0dbd8e))
* **protocol:** correct the card-exchange opcodes and payloads (B-093) ([3128d38](https://github.com/StarLoco/StarLoco-Arena/commit/3128d384678eae939880e69266bd70c5536dc52e))
* **protocol:** key END_FIGHT reports by roster id, scoped per coach (B-096) ([5442dd9](https://github.com/StarLoco/StarLoco-Arena/commit/5442dd99135cb9e5dc1b23233c650ed44ff96e32))
* **protocol:** put the fight kind in the slot the client reads (B-095) ([31ace8f](https://github.com/StarLoco/StarLoco-Arena/commit/31ace8f7c63c7a1f0787694eeedfb3020f760fce))
* **protocol:** serve the classic opponent search too, and stop double-queueing (B-099) ([bb7de26](https://github.com/StarLoco/StarLoco-Arena/commit/bb7de266b61d714805c19acdd9c603a181c2e3e5))
* **security:** stop granting admin to every account that logs in ([4395819](https://github.com/StarLoco/StarLoco-Arena/commit/4395819368ff0d6057d57428d51912d9f358e9bd))
* **social:** presence reached only friends with notify on (B-121) ([71d27e2](https://github.com/StarLoco/StarLoco-Arena/commit/71d27e2641ba2a47e72de35e5415fc360f01a03b))
* **social:** the friend list sent coach id 0 for everyone (B-120) ([cddf4b5](https://github.com/StarLoco/StarLoco-Arena/commit/cddf4b5540f9b10eb6f26e808b9db9ea046f7080))
* **testclient:** bound DrainReceived so it cannot block forever (B-128) ([99f94a9](https://github.com/StarLoco/StarLoco-Arena/commit/99f94a995e79e8b53de0dd8ca2cb6b1bbf510d86))
* **tournaments:** announce the search period AFTER the tournament list, not at login ([5870ac8](https://github.com/StarLoco/StarLoco-Arena/commit/5870ac89c1db39896dbe2d99302db4b78702dc7c))
* **tournaments:** bye an unopposed coach so a short draw can be won (B-122) ([ad2bbd5](https://github.com/StarLoco/StarLoco-Arena/commit/ad2bbd5c4e4ef10ee49e6695d076e96280d97d16))
* **tournaments:** persist registrations so a restart stops un-registering everyone (B-101) ([794fb6f](https://github.com/StarLoco/StarLoco-Arena/commit/794fb6f830035bd5e516491c8ec049ba035e85fa))
* **web:** fingerprint static asset URLs so CSS fixes actually reach browsers ([460849f](https://github.com/StarLoco/StarLoco-Arena/commit/460849f3167970e34699481996421259447a2e5e))
* **web:** kill the horizontal scrollbar and align the 01-04 feature list ([aac5951](https://github.com/StarLoco/StarLoco-Arena/commit/aac5951f3d8b0aaa4cc3d25fd1d99503c77ec1a2))
* **world:** re-send the roster and team presets on every instance change (B-124) ([b52c1e6](https://github.com/StarLoco/StarLoco-Arena/commit/b52c1e67a83d533aea397ebc85cec726d9963688))


### Documentation

* add ROADMAP.md, a feature-by-feature state of the server ([0c0dae0](https://github.com/StarLoco/StarLoco-Arena/commit/0c0dae0ede492f92a39b685c3f57ef0c9929e7e2))
* answer the MaxActive scope question that blocked enforcement ([d1a284d](https://github.com/StarLoco/StarLoco-Arena/commit/d1a284d753f8c27af780916331091fae3129885d))
* audit every ROADMAP status marker, and make the drift machine-checked ([b47270f](https://github.com/StarLoco/StarLoco-Arena/commit/b47270fdd7915c37cd6cdb7a6e9a924754724ab3))
* **bugs:** live-verify the 6011 blob order, and withdraw a claim I could not support ([a1c76b7](https://github.com/StarLoco/StarLoco-Arena/commit/a1c76b799fc33b8ce965897714c6530f1f7ac20e))
* **bugs:** record what the equipment fix actually changed, and the A/B behind it ([a7810d5](https://github.com/StarLoco/StarLoco-Arena/commit/a7810d5f9e0f4a2145cb942e5e48768bd79eacf1))
* clear the documentation-hygiene backlog ([6d007b2](https://github.com/StarLoco/StarLoco-Arena/commit/6d007b2781cc3d91f4820a70b93038ffe6aab7b1))
* close MaxActive as deliberately redundant - Tier 1 complete ([f779f7b](https://github.com/StarLoco/StarLoco-Arena/commit/f779f7b659afc054531de03eba4b79f2ef5ea6cc))
* close the coach-deck question - nothing populates it in the 2.70 build ([8059421](https://github.com/StarLoco/StarLoco-Arena/commit/8059421276d378fdcff3f3989603187c1803c90f))
* coach action cards ARE castable (8109) - and the deck blob's ids look wrong ([c83cef7](https://github.com/StarLoco/StarLoco-Arena/commit/c83cef7fb0703fe074687b8206f15bc5f104eb61))
* correct the B-123 premise and record what the live run did and did not prove ([bb3974d](https://github.com/StarLoco/StarLoco-Arena/commit/bb3974d5679673dcca4d6a73b8b09affcd526d35))
* document the web portal ([c4f2fb0](https://github.com/StarLoco/StarLoco-Arena/commit/c4f2fb06187440835c0e5fb49bf94c6ecaa7fd38))
* drive a full fight to completion and correct the stale MaxActive item ([02ecc9f](https://github.com/StarLoco/StarLoco-Arena/commit/02ecc9f16943b65304c8475a6329db27a85657a9))
* **game:** 5203 card removal is unactionable, and is not a lock ([2f0097f](https://github.com/StarLoco/StarLoco-Arena/commit/2f0097ff948af74bfa58bccd623ea2b0a0f1426c))
* **item 11:** pin the 8121/8122 wire and identify 8122 as the buff removal ([3730833](https://github.com/StarLoco/StarLoco-Arena/commit/373083320210e4b991dc303e3798d6039b7f8c08))
* item 30 (2v2) closed, with the post-fight verified for all four coaches ([00fb6a5](https://github.com/StarLoco/StarLoco-Arena/commit/00fb6a5cb995ecdd67de6cebb49e0622ba3fc036))
* item 32 done - tournament match layer live-verified end to end ([927776d](https://github.com/StarLoco/StarLoco-Arena/commit/927776d2b2b3307cf90f5aee526da0df51779e49))
* live-verify the AI spell repertoire against the retail client ([e491a80](https://github.com/StarLoco/StarLoco-Arena/commit/e491a80bbd7d0143fc8ac15d800091b0839c8d5d))
* live-verify today's CREATE_FIGHT change against the retail client ([4b26843](https://github.com/StarLoco/StarLoco-Arena/commit/4b26843b51d79fe251c4049418bcd80330d25b75))
* mark Tier 0 and Tier 1 complete, and record why five items needed no code ([d124372](https://github.com/StarLoco/StarLoco-Arena/commit/d1243728d2843300e806f62777acaaf15693e38d))
* narrow the coach-deck investigation - cards and spells are separate registries ([fb8fa99](https://github.com/StarLoco/StarLoco-Arena/commit/fb8fa996302ce83bae7580afde5dfa3a1911c833))
* note the measured effect-row coverage in the at-a-glance table ([44e0f08](https://github.com/StarLoco/StarLoco-Arena/commit/44e0f08931b23504f4f533f3652b0abc337e8f3b))
* record close combat and the B-084 guard in ROADMAP 8.16 and STATUS ([a7743ab](https://github.com/StarLoco/StarLoco-Arena/commit/a7743abf5d43b5309bbc2e26bcbb0beef9c51845))
* record the AI repertoire work in ROADMAP 8.16 and STATUS ([70fb80a](https://github.com/StarLoco/StarLoco-Arena/commit/70fb80ac4e1ad2a15aafb739758e0b82f23d0522))
* record the live verification of the corrected exchange (B-093) ([220bffd](https://github.com/StarLoco/StarLoco-Arena/commit/220bffde4a8cbab41ad1e873b27aa3c9c81f469f))
* record the two CI failures as B-126 and B-127 ([252a332](https://github.com/StarLoco/StarLoco-Arena/commit/252a332f9cf44927ec8ec5c09269dfaf4490a157))
* record what the B-074..B-083 run added to the method ([9bd13ac](https://github.com/StarLoco/StarLoco-Arena/commit/9bd13aca3ce5b6fc1ccac75a7b51c6a9f88fd469))
* resolve the np_1 operand question - those rules are a CATALOGUE ([e370ecc](https://github.com/StarLoco/StarLoco-Arena/commit/e370ecc0efeef97721f452aa7339ff4abfab0a6d))
* settle item 11's remaining channel, and correct two items I mis-listed as open ([85d0e69](https://github.com/StarLoco/StarLoco-Arena/commit/85d0e69c850334769380ad5e80e541c661187b25))
* the tournament match layer is live-verified end to end (item 32) ([abef478](https://github.com/StarLoco/StarLoco-Arena/commit/abef4789081daaa901319ba53ecb419387292209))

## [0.4.0](https://github.com/StarLoco/StarLoco-Arena/compare/v0.3.0...v0.4.0) (2026-08-05)


### Features

* **web:** link the retail client from the portal, README and release notes ([445ddc2](https://github.com/StarLoco/StarLoco-Arena/commit/445ddc24fd96ba263e0c2fdbde57d6f87587acaa))

## [0.3.0](https://github.com/StarLoco/StarLoco-Arena/compare/v0.2.0...v0.3.0) (2026-08-05)


### Features

* **release:** bundle the server data in every release and rename the archive ([39e9ae8](https://github.com/StarLoco/StarLoco-Arena/commit/39e9ae80eab15850cec099b75467f1855de2a144))

## [0.2.0](https://github.com/StarLoco/StarLoco-Arena/compare/v0.1.0...v0.2.0) (2026-08-05)


### Features

* **gamedata:** find the game data in the player's own client install ([942198e](https://github.com/StarLoco/StarLoco-Arena/commit/942198e237f0bdf872b210d864d2880a6aa5a0cc))

## 0.1.0 (2026-08-05)


### Features

* **server:** self-configuring server with a web sign-up portal ([c438a8f](https://github.com/StarLoco/StarLoco-Arena/commit/c438a8f42fcf7d0a0a44fb9099fd51cf02c53fb7))


### Documentation

* explain downloading, running and releasing the server ([127fb49](https://github.com/StarLoco/StarLoco-Arena/commit/127fb49ba051ae612b6cfb3313fee9d554e03efe))
* fix DofusArena.exe path in README (verified against a live client run) ([1fac150](https://github.com/StarLoco/StarLoco-Arena/commit/1fac15075c904b57a85ee085de4d6addd3939543))
* point cross-references at v2.04 after the main -&gt; v2.04 rename ([3b6dfaf](https://github.com/StarLoco/StarLoco-Arena/commit/3b6dfaf37d7ccf906dadf0b78bbc8c7255325f86))
