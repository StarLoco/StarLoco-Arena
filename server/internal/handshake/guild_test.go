package handshake

import (
	"bytes"
	"testing"
)

// TestGuildBlobIsEmptyWithoutAClan is the compatibility guarantee: a coach in no
// guild must produce exactly the two zero bytes the server sent before guilds
// existed. Every coach that has no clan - which is all of them until one is
// created - keeps its previous wire image.
func TestGuildBlobIsEmptyWithoutAClan(t *testing.T) {
	got := buildGuildBlob(nil)
	if !bytes.Equal(got, []byte{0, 0}) {
		t.Errorf("no-clan blob = % x, want 00 00", got)
	}
}

// TestGuildBlobLayout pins `uu_2.f` field for field. The client reads this blob
// positionally with no discriminator, so a single misplaced field shifts every
// later one and the clan silently comes out wrong rather than failing.
func TestGuildBlobLayout(t *testing.T) {
	out := buildGuildBlob(&GuildMembership{
		GuildID:   0x1122334455667788,
		GuildName: "Les Bouftous",
		Rights:    1,
		RankLevel: 1,
		RankName:  "Chef",
		DemonID:   7,
	})
	if len(out) < 2 {
		t.Fatal("blob too short")
	}
	blobLen := int(uint16(out[0])<<8 | uint16(out[1]))
	blob := out[2:]
	if len(blob) != blobLen {
		t.Fatalf("length prefix says %d, %d bytes follow", blobLen, len(blob))
	}
	// Part table: one part, index 2.
	if blob[0] != 1 {
		t.Fatalf("partCount = %d, want 1", blob[0])
	}
	if blob[1] != 2 {
		t.Fatalf("part index = %d, want 2 (uu_2 = my own membership)", blob[1])
	}
	off := int(uint32(blob[2])<<24 | uint32(blob[3])<<16 | uint32(blob[4])<<8 | uint32(blob[5]))
	if off != 6 {
		t.Fatalf("part offset = %d, want 6 (1 count byte + one 5-byte entry)", off)
	}
	if blob[off] != 2 {
		t.Fatalf("the byte at the offset is %d, want the part index 2", blob[off])
	}
	p := blob[off+1:]

	be64 := func(b []byte) uint64 {
		var v uint64
		for _, x := range b[:8] {
			v = v<<8 | uint64(x)
		}
		return v
	}
	if id := be64(p); id != 0x1122334455667788 {
		t.Errorf("guildId = %#x, want 0x1122334455667788", id)
	}
	p = p[8:]
	if rights := uint32(p[0])<<24 | uint32(p[1])<<16 | uint32(p[2])<<8 | uint32(p[3]); rights != 1 {
		t.Errorf("rights = %d, want 1", rights)
	}
	p = p[4:]
	if lvl := uint16(p[0])<<8 | uint16(p[1]); lvl != 1 {
		t.Errorf("rankLevel = %d, want 1", lvl)
	}
	p = p[2:]
	if int(p[0]) != len("Chef") || string(p[1:1+p[0]]) != "Chef" {
		t.Fatalf("rankName = %q", string(p[1:1+p[0]]))
	}
	p = p[1+int(p[0]):]
	p = p[2:] // unused i16
	if int(p[0]) != len("Les Bouftous") || string(p[1:1+p[0]]) != "Les Bouftous" {
		t.Fatalf("guildName = %q", string(p[1:1+p[0]]))
	}
	p = p[1+int(p[0]):]
	p = p[2+4+4:] // unused i16, i32, i32
	if demon := uint16(p[0])<<8 | uint16(p[1]); demon != 7 {
		t.Errorf("demonId = %d, want 7", demon)
	}
}

// TestGuildBlobUsesUTF8 is the difference that would otherwise survive every
// ASCII test: guild strings go through `aey_0`, which names UTF-8 explicitly,
// while coach and fighter names use the cp1252 wire charset. An accented clan
// name is where the two disagree.
func TestGuildBlobUsesUTF8(t *testing.T) {
	const name = "Épée" // É is 2 bytes in UTF-8, 1 in cp1252
	out := buildGuildBlob(&GuildMembership{GuildID: 1, GuildName: name, RankName: "a"})
	if !bytes.Contains(out, []byte(name)) {
		t.Errorf("blob does not contain the UTF-8 bytes of %q: % x", name, out)
	}
}
