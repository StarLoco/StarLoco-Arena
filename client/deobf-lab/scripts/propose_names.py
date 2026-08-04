#!/usr/bin/env python3
r"""
propose_names.py -- AI-naming pass over the still-auto classes.

Reads ../mappings/naming_worklist.csv and assigns each class a readable name by
reasoning over signals that are FACTS or already-confirmed:

  * DOMAIN  <- keyword vote over (a) references to already-named *Message classes
              (confirmed 2007 names) and (b) plaintext i18n strings / log text.
  * ROLE    <- structure:
                - has an opcode (getId)            -> a network Message
                - implements the handler iface atG -> a MessageHandler
                - references >=3 known *Message     -> a Manager/Handler
  * DIRECTION <- the (already-readable) base class: OutputOnlyProxyMessage=C2S,
                InputOnlyProxyMessage / FightServerToClientMessage = S2C.

Naming scheme (opcode is a FACT, so it is embedded for messages -> cross-refs
analysis/opcodes.md directly):

  opcode + domain      -> <Domain>Message<opcode>     (high)
  opcode, no domain    -> <Dir>Message<opcode>        (medium)
  handler + domain     -> <Domain>MessageHandler      (high)
  handler, no domain   -> ProxyMessageHandler         (medium)
  domain + >=3 msg refs-> <Domain>Manager             (medium)
  domain from strings  -> <Domain>Screen              (low, NOT applied)

Output ../mappings/ai_names.csv: obf_true, ai_name, confidence, apply, role,
domain, opcode, rationale. build_mapping.py applies rows where apply==1
(high+medium); low rows are kept for human review only.

These are AI GUESSES (except the opcode). class_names.csv marks them source=ai.
"""
import csv
import os

LAB   = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
WORK  = os.path.join(LAB, "mappings", "naming_worklist.csv")
OUT   = os.path.join(LAB, "mappings", "ai_names.csv")

# domain -> keywords (checked against ref tokens + string tokens, lowercased).
# Order matters: earlier domains win ties (more specific first).
DOMAINS = [
    ("Chat",        ["channel", "chat", "friend", "ignore", "private", "vicinity", "ami", "mute"]),
    ("Team",        ["teampreset", "teammanagement", "fightercreation", "fighterequipment",
                     "bench", "editablefighter", "fighteredition", "roster", "fighterinformation"]),
    ("Tournament",  ["tournament"]),
    ("Ladder",      ["ladder", "ranking"]),
    ("Guild",       ["guild"]),
    ("Exchange",    ["exchange", "trade", "addcard"]),
    ("Coach",       ["coach"]),
    ("Matchmaking", ["opponentsearch", "opponent", "matchmaking", "matchconfirm"]),
    ("Property",    ["property"]),
    ("Connection",  ["login", "logon", "reconnection", "disconnection", "clientversion", "authentication"]),
    ("Combat",      ["fighter", "combat", "spell", "effect", "turn", "tackle", "summon",
                     "buff", "runningeffect", "cast", "closecombat"]),
    ("Actor",       ["actorspawn", "movement", "direction", "actor"]),
    ("Shop",        ["shop", "market", "auction", "boutique"]),
    ("Achievement", ["achievement", "trophy"]),
    ("Calendar",    ["calendar"]),
    ("Party",       ["party", "invitation"]),
    ("World",       ["topology", "worldmap", "mapcomplementary"]),
]

DIR = {
    "OutputOnlyProxyMessage": "C2S",
    "InputOnlyProxyMessage": "S2C",
    "FightServerToClientMessage": "S2C",
    "AdminMessageBase": "Admin",
}


def domain_of(ref_tokens, str_tokens):
    blob_refs = [t.lower() for t in ref_tokens]
    blob_strs = [t.lower() for t in str_tokens]
    best, best_hits = "", 0
    for dom, kws in DOMAINS:
        hits = 0
        for t in blob_refs:
            if any(k in t for k in kws):
                hits += 2          # confirmed message refs weigh more
        for t in blob_strs:
            if any(k in t for k in kws):
                hits += 1
        if hits > best_hits:
            best, best_hits = dom, hits
    return best, best_hits


def main():
    rows = []
    with open(WORK, encoding="utf-8") as f:
        for r in csv.DictReader(f):
            refs = [t for t in (r.get("refs_known") or "").split() if t]
            strs = [t.strip() for t in (r.get("strings") or "").split("|") if t.strip()]
            opcode = (r.get("opcode") or "").strip()
            extends = (r.get("extends") or "").strip()
            implements = (r.get("implements") or "").strip()
            msg_refs = sum(1 for t in refs if t.endswith("Message") and t != "NetworkMessage")

            dom, hits = domain_of(refs, strs)
            is_handler = ("atG" in implements) or ("MessageHandler" in implements) \
                or (not opcode and msg_refs >= 3)
            direction = DIR.get(extends, "")

            name = conf = role = ""
            if opcode:
                role = "Message"
                if dom:
                    name, conf = f"{dom}Message{opcode}", "high"
                else:
                    name, conf = f"{(direction or 'Proxy')}Message{opcode}", "medium"
            elif is_handler:
                role = "Handler"
                if dom:
                    name, conf = f"{dom}MessageHandler", "high"
                else:
                    name, conf = "ProxyMessageHandler", "medium"
            elif dom and msg_refs >= 3:
                role, name, conf = "Manager", f"{dom}Manager", "medium"
            elif dom and len(strs) >= 2 and hits >= 2:
                role, name, conf = "Screen", f"{dom}Screen", "low"
            else:
                continue  # not enough signal -> leave on auto name

            rationale = f"refs=[{' '.join(refs[:4])}] strs=[{' | '.join(strs[:3])}]"
            rows.append({
                "obf_true": r["obf_true"], "ai_name": name, "confidence": conf,
                "apply": 1 if conf in ("high", "medium") else 0,
                "role": role, "domain": dom, "opcode": opcode,
                "rationale": rationale[:200],
            })

    rows.sort(key=lambda x: (x["confidence"] != "high", x["confidence"] != "medium", x["ai_name"]))
    cols = ["obf_true", "ai_name", "confidence", "apply", "role", "domain", "opcode", "rationale"]
    with open(OUT, "w", encoding="utf-8", newline="") as f:
        w = csv.DictWriter(f, fieldnames=cols)
        w.writeheader()
        w.writerows(rows)

    hi = sum(1 for r in rows if r["confidence"] == "high")
    me = sum(1 for r in rows if r["confidence"] == "medium")
    lo = sum(1 for r in rows if r["confidence"] == "low")
    print(f"proposals: {len(rows)}  (high={hi} medium={me} low={lo})")
    print(f"  applied (high+medium): {hi + me}")
    from collections import Counter
    dc = Counter(r["domain"] for r in rows if r["domain"])
    print("  by domain:", dict(dc.most_common()))
    print(f"written: {OUT}")


if __name__ == "__main__":
    main()
