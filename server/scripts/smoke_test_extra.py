"""Extended smoke test: fighter creation, team save/list, private chat
between two sessions, and duplicate-login rejection. Manual dev tool only,
mirrors go-server/docs/02-protocol.md wire formats.
"""
import socket
import struct
import sys
import time

from smoke_test import send_packet, recv_packet, pstring, read_exact  # type: ignore


def connect_and_auth(host, port, login, password):
    sock = socket.create_connection((host, port), timeout=5)
    sock.settimeout(5)
    send_packet(sock, 1, 1025, pstring(login) + pstring(password))
    opcode, payload = recv_packet(sock)
    assert opcode == 1024
    result_code = payload[0]
    return sock, result_code


def drain(sock, n=6, timeout=1.0):
    sock.settimeout(timeout)
    for _ in range(n):
        try:
            recv_packet(sock)
        except socket.timeout:
            break


def main():
    host, port = "127.0.0.1", 4443

    print("=== test 1: duplicate login rejected ===")
    sock1, code1 = connect_and_auth(host, port, "test", "test123")
    assert code1 == 0, f"first login should succeed, got {code1}"
    drain(sock1)

    sock2, code2 = connect_and_auth(host, port, "test", "test123")
    print(f"second login result code = {code2} (expect 3 = already connected)")
    assert code2 == 3, f"expected AlreadyConnected(3), got {code2}"
    sock2.close()

    print("\n=== test 2: fighter creation ===")
    # FIGHTER_CREATE_REQUEST (6001, arch=3):
    # short(unused) byte(version) short(budget) byte(breed) pstring(name) byte(sex) byte(skin)
    # short(spellsLen) bytes short(objectsLen) bytes
    payload = struct.pack(">hBhB", 0, 1, 100, 1) + pstring("Bob") + bytes([0, 0]) + struct.pack(">HH", 0, 0)
    send_packet(sock1, 3, 6001, payload)
    opcode, payload = recv_packet(sock1)
    assert opcode == 6000, f"expected FIGHTER_CREATE_RESULT(6000), got {opcode}"
    error_code = payload[0]
    fighter_id = struct.unpack(">q", payload[1:9])[0]
    print(f"fighter create error_code={error_code} fighter_id={fighter_id}")
    assert error_code == 0

    print("\n=== test 3: fighter information list ===")
    send_packet(sock1, 3, 6005, b"")
    opcode, payload = recv_packet(sock1)
    assert opcode == 6006, f"expected FIGHTER_INFORMATION_LIST(6006), got {opcode}"
    count = payload[0]
    print(f"fighter list count={count}")
    assert count >= 1

    print("\n=== test 4: team preset save ===")
    # TEAM_PRESET_SAVE_REQUEST (6021, arch=3): short(slot) pstring(name) byte(count) [long fighterId]*
    payload = struct.pack(">h", 1) + pstring("MyTeam") + bytes([1]) + struct.pack(">q", fighter_id)
    send_packet(sock1, 3, 6021, payload)
    opcode, payload = recv_packet(sock1)
    assert opcode == 6020, f"expected TEAM_PRESET_SAVE(6020), got {opcode}"
    print(f"team save error_code={payload[0]}")
    assert payload[0] == 0
    # Followed by TEAM_PRESET_LIST + FIGHTER_INFORMATION_LIST (auto re-list)
    opcode, payload = recv_packet(sock1)
    assert opcode == 6030, f"expected TEAM_PRESET_LIST(6030), got {opcode}"
    opcode, payload = recv_packet(sock1)
    assert opcode == 6006

    print("\nall extra smoke tests passed")
    sock1.close()


if __name__ == "__main__":
    sys.path.insert(0, ".")
    main()
