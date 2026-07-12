"""Live test: create a fighter, set spells+equipment via UPDATE_FIGHTER_INVENTORY
(6011), verify the response and a fresh fighter list both carry the loadout.
Uses the real ArrayInventory wire format: [short pos][int32 id] pairs.
Assumes server on 127.0.0.1:443 and account exists.
"""
import socket
import struct
import sys

from smoke_test import send_packet, recv_packet, pstring  # type: ignore

SPELLS = [6, 32, 39]
OBJECTS = [9, 133]


def inv_blob(ids):
    b = b""
    for i, x in enumerate(ids):
        b += struct.pack(">hi", i, x)
    return b


def parse_blob(blob):
    ids = []
    while len(blob) >= 6:
        ids.append(struct.unpack(">i", blob[2:6])[0])
        blob = blob[6:]
    return ids


def main():
    host, port, login, password, coach = "127.0.0.1", 443, "testuser", "test123", "Coach"
    sock = socket.create_connection((host, port), timeout=5)
    sock.settimeout(5)

    send_packet(sock, 1, 1025, pstring(login) + pstring(password))
    op, pl = recv_packet(sock)
    assert op == 1024 and pl[0] == 0, f"auth failed {op}/{pl[:1]}"
    recv_packet(sock)  # queue
    op, pl = recv_packet(sock)
    if op == 2048:
        send_packet(sock, 2, 2049, pstring(coach) + bytes([0, 0, 0]))
        assert recv_packet(sock)[0] == 2050
        op, pl = recv_packet(sock)
    # drain to ENTER_WORLD_INSTANCE
    for _ in range(10):
        if op == 4600:
            break
        sock.settimeout(1.5)
        try:
            op, pl = recv_packet(sock)
        except socket.timeout:
            break
    sock.settimeout(5)

    # Create a fighter
    payload = struct.pack(">hBhB", 0, 1, 100, 1) + pstring("PyKit") + bytes([0, 0]) + struct.pack(">HH", 0, 0)
    send_packet(sock, 3, 6001, payload)
    op, pl = recv_packet(sock)
    assert op == 6000, f"create opcode {op}"
    fid = struct.unpack(">q", pl[1:9])[0]
    print(f"created fighter id={fid}")

    # Update inventory: long(id) short(spellLen) spellBlob short(objLen) objBlob
    sb = inv_blob(SPELLS)
    ob = inv_blob(OBJECTS)
    payload = struct.pack(">q", fid) + struct.pack(">H", len(sb)) + sb + struct.pack(">H", len(ob)) + ob
    send_packet(sock, 3, 6011, payload)
    op, pl = recv_packet(sock)
    assert op == 6010, f"update response opcode {op}"
    # long id, byte err, short spellLen, spellBlob, short objLen, objBlob
    pos = 8
    err = pl[pos]; pos += 1
    slen = struct.unpack(">H", pl[pos:pos+2])[0]; pos += 2
    sblob = pl[pos:pos+slen]; pos += slen
    olen = struct.unpack(">H", pl[pos:pos+2])[0]; pos += 2
    oblob = pl[pos:pos+olen]; pos += olen
    print(f"update response err={err} spells={parse_blob(sblob)} objects={parse_blob(oblob)}")
    assert parse_blob(sblob) == SPELLS, "spells mismatch in response"
    assert parse_blob(oblob) == OBJECTS, "objects mismatch in response"

    # Request fighter list and decode first fighter's loadout
    send_packet(sock, 3, 6005, b"")
    op, pl = recv_packet(sock)
    assert op == 6006
    spells, objects = parse_first_fighter(pl)
    print(f"fighter list loadout spells={spells} objects={objects}")
    assert spells == SPELLS, "spells lost in fighter list"
    assert objects == OBJECTS, "objects lost in fighter list"

    print("\nSUCCESS: fighter equipment + spells round-trip correctly")
    sock.close()
    return 0


def parse_first_fighter(pl):
    pos = 0
    count = pl[pos]; pos += 1
    assert count >= 1
    pos += 8  # fighter id
    pos += 2  # serialized length
    pos += 1  # version marker
    pos += 2  # budget
    pos += 1  # breed
    namelen = pl[pos]; pos += 1
    pos += namelen  # name
    pos += 1  # sex
    pos += 1  # skin
    slen = struct.unpack(">H", pl[pos:pos+2])[0]; pos += 2
    sblob = pl[pos:pos+slen]; pos += slen
    olen = struct.unpack(">H", pl[pos:pos+2])[0]; pos += 2
    oblob = pl[pos:pos+olen]; pos += olen
    return parse_blob(sblob), parse_blob(oblob)


if __name__ == "__main__":
    sys.path.insert(0, ".")
    sys.exit(main())
