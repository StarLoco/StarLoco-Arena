// Package gamedata reads the 2.70 client's static data store (data.bdat +
// indexes.bdat) so the server can resolve card/spell templates.
//
// Storage format (see client/analysis/DATA-FORMAT.md):
//   - indexes.bdat: raw, big-endian, sequence of entries until EOF:
//     [i32 type][u16 nameLen + name][u16 valLen + val][i64 position]
//   - data.bdat: concatenation of independent zlib streams, each addressed by
//     the absolute `position` from an index entry. Inflating one stream yields
//     one record block: [i32 id][i16 version][i32 dataLen][payload].
//
// All integers are big-endian.
package gamedata

import (
	"bufio"
	"compress/zlib"
	"encoding/binary"
	"fmt"
	"io"
	"os"
	"path/filepath"
)

// Record type ids (enum atr_0) present in the store.
const (
	TypeCoachCard    = 100
	TypeEffect       = 200
	TypeStaticEffect = 210
	TypeSpell        = 220
	TypeEvent        = 230
	TypeFighterCard  = 250
	TypeSummoning    = 300
)

// IndexEntry maps (type, key) to a byte offset in data.bdat.
type IndexEntry struct {
	Type     int32
	Name     string // index name, always "id"
	Value    string // the key as a string
	Position int64
}

// Record is one decoded data block.
type Record struct {
	ID      int32
	Version int16
	Data    []byte
}

// Store holds the parsed index and the open data file.
type Store struct {
	dataPath string
	index    map[int32][]IndexEntry // by type
}

// Open reads indexes.bdat and prepares to read records from data.bdat. dir is
// the directory containing both files (…/contents/bdata).
func Open(dir string) (*Store, error) {
	idxPath := filepath.Join(dir, "indexes.bdat")
	dataPath := filepath.Join(dir, "data.bdat")

	f, err := os.Open(idxPath)
	if err != nil {
		return nil, fmt.Errorf("gamedata: open index: %w", err)
	}
	defer f.Close()

	index, err := readIndex(bufio.NewReader(f))
	if err != nil {
		return nil, err
	}
	return &Store{dataPath: dataPath, index: index}, nil
}

// readIndex parses all index entries until EOF, grouped by type.
func readIndex(r io.Reader) (map[int32][]IndexEntry, error) {
	index := make(map[int32][]IndexEntry)
	for {
		var typ int32
		if err := binary.Read(r, binary.BigEndian, &typ); err != nil {
			if err == io.EOF {
				break
			}
			return nil, fmt.Errorf("gamedata: index type: %w", err)
		}
		name, err := readJavaUTF(r)
		if err != nil {
			return nil, err
		}
		value, err := readJavaUTF(r)
		if err != nil {
			return nil, err
		}
		var pos int64
		if err := binary.Read(r, binary.BigEndian, &pos); err != nil {
			return nil, fmt.Errorf("gamedata: index pos: %w", err)
		}
		index[typ] = append(index[typ], IndexEntry{
			Type: typ, Name: name, Value: value, Position: pos,
		})
	}
	return index, nil
}

// readJavaUTF reads a Java writeUTF value: [u16 len][modified-utf8 bytes].
// For ASCII ids this is plain ASCII.
func readJavaUTF(r io.Reader) (string, error) {
	var n uint16
	if err := binary.Read(r, binary.BigEndian, &n); err != nil {
		return "", err
	}
	b := make([]byte, n)
	if _, err := io.ReadFull(r, b); err != nil {
		return "", err
	}
	return string(b), nil
}

// EntriesOf returns the index entries for a record type.
func (s *Store) EntriesOf(typ int32) []IndexEntry { return s.index[typ] }

// ReadRecord seeks to a position in data.bdat, inflates the single zlib stream
// there and returns the decoded record block.
func (s *Store) ReadRecord(pos int64) (*Record, error) {
	f, err := os.Open(s.dataPath)
	if err != nil {
		return nil, err
	}
	defer f.Close()

	if _, err := f.Seek(pos, io.SeekStart); err != nil {
		return nil, err
	}
	zr, err := zlib.NewReader(f)
	if err != nil {
		return nil, fmt.Errorf("gamedata: zlib at %d: %w", pos, err)
	}
	defer zr.Close()

	var rec Record
	if err := binary.Read(zr, binary.BigEndian, &rec.ID); err != nil {
		return nil, err
	}
	if err := binary.Read(zr, binary.BigEndian, &rec.Version); err != nil {
		return nil, err
	}
	var dataLen int32
	if err := binary.Read(zr, binary.BigEndian, &dataLen); err != nil {
		return nil, err
	}
	rec.Data = make([]byte, dataLen)
	if _, err := io.ReadFull(zr, rec.Data); err != nil {
		return nil, err
	}
	return &rec, nil
}
