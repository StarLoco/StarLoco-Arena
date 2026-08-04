package handshake

import "testing"

func TestDecodeStatisticUpdate(t *testing.T) {
	// statId=229 (0x00E5), flag=1, value=1 — the "coach created" counter the
	// client sends right after entering world with an existing coach.
	payload := []byte{0x00, 0xE5, 0x01, 0x00, 0x01}
	su, err := DecodeStatisticUpdate(payload)
	if err != nil {
		t.Fatalf("DecodeStatisticUpdate: %v", err)
	}
	if su.StatID != 229 {
		t.Errorf("StatID = %d, want 229", su.StatID)
	}
	if !su.Flag {
		t.Errorf("Flag = false, want true")
	}
	if su.Value != 1 {
		t.Errorf("Value = %d, want 1", su.Value)
	}
}

func TestDecodeStatisticUpdate_WrongLength(t *testing.T) {
	for _, n := range []int{0, 4, 6} {
		if _, err := DecodeStatisticUpdate(make([]byte, n)); err == nil {
			t.Errorf("DecodeStatisticUpdate(len=%d): want error, got nil", n)
		}
	}
}
