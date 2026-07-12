package protocol

import "testing"

func TestWriterReaderRoundTrip(t *testing.T) {
	w := NewWriter(0)
	w.PutBool(true)
	w.PutBool(false)
	w.PutByte(0xAB)
	w.PutInt8(-5)
	w.PutUint16(0xBEEF)
	w.PutInt16(-1234)
	w.PutUint32(0xDEADBEEF)
	w.PutInt32(-123456789)
	w.PutUint64(0x1122334455667788)
	w.PutInt64(-9223372036854775800)
	w.PutFloat32(3.14159)
	w.PutString("hello")
	w.PutStringShort("longer string here")
	w.PutBytes([]byte{1, 2, 3, 4})

	r := NewReader(w.Bytes())

	if got := r.Bool(); got != true {
		t.Errorf("Bool() #1 = %v, want true", got)
	}
	if got := r.Bool(); got != false {
		t.Errorf("Bool() #2 = %v, want false", got)
	}
	if got := r.Byte(); got != 0xAB {
		t.Errorf("Byte() = %#x, want 0xAB", got)
	}
	if got := r.Int8(); got != -5 {
		t.Errorf("Int8() = %d, want -5", got)
	}
	if got := r.Uint16(); got != 0xBEEF {
		t.Errorf("Uint16() = %#x, want 0xBEEF", got)
	}
	if got := r.Int16(); got != -1234 {
		t.Errorf("Int16() = %d, want -1234", got)
	}
	if got := r.Uint32(); got != 0xDEADBEEF {
		t.Errorf("Uint32() = %#x, want 0xDEADBEEF", got)
	}
	if got := r.Int32(); got != -123456789 {
		t.Errorf("Int32() = %d, want -123456789", got)
	}
	if got := r.Uint64(); got != 0x1122334455667788 {
		t.Errorf("Uint64() = %#x, want 0x1122334455667788", got)
	}
	if got := r.Int64(); got != -9223372036854775800 {
		t.Errorf("Int64() = %d, want -9223372036854775800", got)
	}
	if got := r.Float32(); got != 3.14159 {
		t.Errorf("Float32() = %v, want 3.14159", got)
	}
	if got := r.String(); got != "hello" {
		t.Errorf("String() = %q, want %q", got, "hello")
	}
	if got := r.StringShort(); got != "longer string here" {
		t.Errorf("StringShort() = %q, want %q", got, "longer string here")
	}
	if got := r.Bytes(4); string(got) != "\x01\x02\x03\x04" {
		t.Errorf("Bytes(4) = %v, want [1 2 3 4]", got)
	}

	if err := r.Err(); err != nil {
		t.Fatalf("unexpected error after full round-trip: %v", err)
	}
	if remaining := r.Remaining(); remaining != 0 {
		t.Errorf("Remaining() = %d, want 0", remaining)
	}
}

func TestReaderShortReadSetsError(t *testing.T) {
	r := NewReader([]byte{0x01, 0x02})
	_ = r.Uint32() // needs 4 bytes, only 2 available
	if r.Err() == nil {
		t.Fatal("expected error on short read, got nil")
	}

	// Once an error has occurred, subsequent reads must be no-ops
	// returning zero values, not panic or read garbage.
	if got := r.Byte(); got != 0 {
		t.Errorf("Byte() after error = %v, want 0", got)
	}
	if got := r.String(); got != "" {
		t.Errorf("String() after error = %q, want empty", got)
	}
}

func TestPutStringPanicsOnOverlongString(t *testing.T) {
	defer func() {
		if r := recover(); r == nil {
			t.Fatal("expected panic for string > 255 bytes, got none")
		}
	}()
	w := NewWriter(0)
	long := make([]byte, 256)
	w.PutString(string(long))
}

func TestNegativeIntegersRoundTripExactly(t *testing.T) {
	cases := []int32{-1, -128, -32768, -8388608, -2147483648, 2147483647}
	for _, v := range cases {
		w := NewWriter(4)
		w.PutInt32(v)
		r := NewReader(w.Bytes())
		if got := r.Int32(); got != v {
			t.Errorf("Int32 round-trip for %d got %d", v, got)
		}
	}
}
