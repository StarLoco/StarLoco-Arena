"""Ad-hoc protocol smoke test client for local development.

Connects to the running Go server, authenticates, creates a coach (if
needed), and prints every packet received. Not part of the build; a manual
debugging tool only. See go-server/docs/02-protocol.md for the wire format
this implements.

Usage:
    python scripts/smoke_test.py --host 127.0.0.1 --port 4443 --login test --password test123
"""
import argparse
import socket
import struct
import sys
import time


def send_packet(sock: socket.socket, arch_target: int, opcode: int, payload: bytes) -> None:
    total_size = 5 + len(payload)
    header = struct.pack(">HBH", total_size, arch_target, opcode)
    sock.sendall(header + payload)
    print(f"--> sent opcode={opcode} arch={arch_target} payload_len={len(payload)} payload={payload!r}")


def read_exact(sock: socket.socket, n: int) -> bytes:
    buf = b""
    while len(buf) < n:
        chunk = sock.recv(n - len(buf))
        if not chunk:
            raise ConnectionError("connection closed while reading")
        buf += chunk
    return buf


def recv_packet(sock: socket.socket):
    header = read_exact(sock, 4)
    total_size, opcode = struct.unpack(">HH", header)
    payload_len = total_size - 4
    payload = read_exact(sock, payload_len) if payload_len > 0 else b""
    print(f"<-- recv opcode={opcode} payload_len={payload_len} payload={payload!r}")
    return opcode, payload


def pstring(s: str) -> bytes:
    b = s.encode("utf-8")
    return bytes([len(b)]) + b


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--host", default="127.0.0.1")
    ap.add_argument("--port", type=int, default=4443)
    ap.add_argument("--login", default="test")
    ap.add_argument("--password", default="test123")
    ap.add_argument("--coach-name", default="Smoketest")
    args = ap.parse_args()

    sock = socket.create_connection((args.host, args.port), timeout=5)
    sock.settimeout(5)

    try:
        # AUTHENTICATION (opcode 1025, arch_target=1)
        payload = pstring(args.login) + pstring(args.password)
        send_packet(sock, 1, 1025, payload)

        opcode, payload = recv_packet(sock)
        assert opcode == 1024, f"expected AUTHENTICATION_RESULT(1024), got {opcode}"
        result_code = payload[0]
        print(f"auth result code = {result_code}")
        if result_code != 0:
            print("auth failed, aborting")
            return 1

        opcode, payload = recv_packet(sock)  # QUEUE_NOTIFICATION
        assert opcode == 8192, f"expected QUEUE_NOTIFICATION(8192), got {opcode}"

        opcode, payload = recv_packet(sock)
        if opcode == 2048:
            print("server requested coach creation")
            # COACH_CREATION (opcode 2049, arch_target=2)
            creation_payload = pstring(args.coach_name) + bytes([0, 0, 0])  # skin, hair, sex
            send_packet(sock, 2, 2049, creation_payload)

            opcode, payload = recv_packet(sock)
            assert opcode == 2050, f"expected COACH_CREATION_RESULT(2050), got {opcode}"
            print(f"coach creation result code = {payload[0]}")
            if payload[0] != 0:
                print("coach creation failed, aborting")
                return 1
        else:
            print("coach already exists, opcode was", opcode)

        # Drain a handful of post-login packets (COACH_INFORMATION, FRIEND_LIST,
        # IGNORE_LIST, PLAYER_STATISTICS_REPORT, ENTER_WORLD_INSTANCE, ACTOR_SPAWN...).
        for _ in range(8):
            try:
                sock.settimeout(1.5)
                recv_packet(sock)
            except socket.timeout:
                break

        print("smoke test completed successfully")
        return 0
    finally:
        sock.close()


if __name__ == "__main__":
    sys.exit(main())
