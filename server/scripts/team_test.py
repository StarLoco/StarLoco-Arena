"""Live test: create fighters, save a team with them, verify the team list
comes back with fighter IDs. Manual dev tool. Assumes the server is already
running on 127.0.0.1:4443 and account testuser/test123 exists (or pass
--login/--password).
"""
import argparse
import socket
import struct
import sys

from smoke_test import send_packet, recv_packet, pstring, read_exact  # type: ignore


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--host", default="127.0.0.1")
    ap.add_argument("--port", type=int, default=4443)
    ap.add_argument("--login", default="testuser")
    ap.add_argument("--password", default="test123")
    ap.add_argument("--coach", default="Coach")
    args = ap.parse_args()

    sock = socket.create_connection((args.host, args.port), timeout=5)
    sock.settimeout(5)

    # AUTH
    send_packet(sock, 1, 1025, pstring(args.login) + pstring(args.password))
    op, pl = recv_packet(sock)
    assert op == 1024, f"auth result opcode {op}"
    if pl[0] != 0:
        print(f"auth failed code={pl[0]}")
        return 1
    recv_packet(sock)  # QUEUE_NOTIFICATION
    op, pl = recv_packet(sock)
    if op == 2048:  # coach creation request
        send_packet(sock, 2, 2049, pstring(args.coach) + bytes([0, 0, 0]))
        op, pl = recv_packet(sock)
        assert op == 2050
        op, pl = recv_packet(sock)  # COACH_INFORMATION
    # drain until ENTER_WORLD_INSTANCE (4600)
    for _ in range(10):
        if op == 4600:
            break
        sock.settimeout(1.5)
        try:
            op, pl = recv_packet(sock)
        except socket.timeout:
            break
    sock.settimeout(5)

    # Create two fighters
    def create_fighter(name):
        payload = struct.pack(">hBhB", 0, 1, 100, 1) + pstring(name) + bytes([0, 0]) + struct.pack(">HH", 0, 0)
        send_packet(sock, 3, 6001, payload)
        op, pl = recv_packet(sock)
        assert op == 6000, f"fighter create opcode {op}"
        fid = struct.unpack(">q", pl[1:9])[0]
        print(f"created fighter {name} id={fid}")
        return fid

    f1 = create_fighter("PyA")
    f2 = create_fighter("PyB")

    # Save team with slot -1 (new)
    payload = struct.pack(">h", -1) + pstring("PyTeam") + bytes([2]) + struct.pack(">q", f1) + struct.pack(">q", f2)
    send_packet(sock, 3, 6021, payload)
    op, pl = recv_packet(sock)
    assert op == 6020, f"team save opcode {op}"
    print(f"team save result code={pl[0]}")
    # TEAM_PRESET_LIST
    op, pl = recv_packet(sock)
    assert op == 6030, f"team list opcode {op}"
    parse_team_list(pl)
    op, pl = recv_packet(sock)  # FIGHTER_INFORMATION_LIST

    # Now re-request the team list to simulate reconnect view
    print("\n--- re-requesting TEAM_PRESET_LIST ---")
    send_packet(sock, 3, 6031, b"")
    op, pl = recv_packet(sock)
    assert op == 6030
    parse_team_list(pl)

    sock.close()
    return 0


def parse_team_list(pl):
    pos = 0
    count = pl[pos]; pos += 1
    print(f"TEAM_PRESET_LIST: {count} team(s)")
    for _ in range(count):
        slot = struct.unpack(">h", pl[pos:pos+2])[0]; pos += 2
        namelen = pl[pos]; pos += 1
        name = pl[pos:pos+namelen].decode(); pos += namelen
        fcount = pl[pos]; pos += 1
        fids = []
        for _ in range(fcount):
            fids.append(struct.unpack(">q", pl[pos:pos+8])[0]); pos += 8
        print(f"  slot={slot} name={name!r} fighterCount={fcount} fighterIds={fids}")


if __name__ == "__main__":
    sys.path.insert(0, ".")
    sys.exit(main())
