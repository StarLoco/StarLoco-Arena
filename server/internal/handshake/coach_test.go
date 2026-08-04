package handshake

import "testing"

// TestDecodeCoachCreationColorOrder verifies the wire order is
// [name][SKIN][HAIR][sex] -- the first color byte is skin, the second hair.
// A regression here silently swaps every created coach's colors.
func TestDecodeCoachCreationColorOrder(t *testing.T) {
	// payload: nameLen=3 "Bob", skin=0x11, hair=0x22, sex=0x01
	payload := []byte{3, 'B', 'o', 'b', 0x11, 0x22, 0x01}
	c, err := DecodeCoachCreation(payload)
	if err != nil {
		t.Fatalf("decode: %v", err)
	}
	if c.Name != "Bob" {
		t.Errorf("name = %q, want Bob", c.Name)
	}
	if c.SkinColor != 0x11 {
		t.Errorf("skin = %#x, want 0x11 (first color byte)", c.SkinColor)
	}
	if c.HairColor != 0x22 {
		t.Errorf("hair = %#x, want 0x22 (second color byte)", c.HairColor)
	}
	if c.Sex != 0x01 {
		t.Errorf("sex = %#x, want 0x01", c.Sex)
	}
}

// TestCoachInformationsLookOrder verifies the 2052 T-block emits SKIN then HAIR
// then sex, matching the client's look decoder.
func TestCoachInformationsLookOrder(t *testing.T) {
	frame, err := EncodeCoachInformations(Coach{ID: 1, Name: "X", SkinColor: 0x11, HairColor: 0x22, Sex: 1})
	if err != nil {
		t.Fatalf("encode: %v", err)
	}
	// frame = [u16 len][u16 op][payload]; payload = i64 id(8) + u8 nameLen(1) +
	// "X"(1) + skin + hair + sex ...
	p := frame[4:]
	// after id(8) + len(1) + name(1) = offset 10 is skin, 11 hair, 12 sex.
	if p[10] != 0x11 {
		t.Errorf("skin byte = %#x, want 0x11 (skin first)", p[10])
	}
	if p[11] != 0x22 {
		t.Errorf("hair byte = %#x, want 0x22 (hair second)", p[11])
	}
}
