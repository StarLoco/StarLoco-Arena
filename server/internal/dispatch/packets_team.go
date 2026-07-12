package dispatch

import (
	"github.com/dofusarena/go-server/internal/domain"
	"github.com/dofusarena/go-server/internal/protocol"
)

// buildTeamPresetSaveResult serializes TEAM_PRESET_SAVE, see
// TeamPresetSaveRequest.java:62-64.
func buildTeamPresetSaveResult(slot int16, name string, fighterIDs []uint) protocol.OutboundFrame {
	w := protocol.NewWriter(5 + len(name) + 8*len(fighterIDs))
	w.PutByte(0).PutInt16(slot).PutString(name).PutByte(byte(len(fighterIDs)))
	for _, id := range fighterIDs {
		w.PutInt64(int64(id))
	}
	return protocol.OutboundFrame{Opcode: protocol.SendTeamPresetSave, Payload: w.Bytes()}
}

func buildTeamPresetSaveError() protocol.OutboundFrame {
	w := protocol.NewWriter(1)
	w.PutByte(1)
	return protocol.OutboundFrame{Opcode: protocol.SendTeamPresetSave, Payload: w.Bytes()}
}

// buildTeamPresetDeletionResult serializes TEAM_PRESET_DELETION, see
// TeamPresetDeleteRequest.java:27.
func buildTeamPresetDeletionResult(slot int16) protocol.OutboundFrame {
	w := protocol.NewWriter(3)
	w.PutByte(0).PutInt16(slot)
	return protocol.OutboundFrame{Opcode: protocol.SendTeamPresetDeletion, Payload: w.Bytes()}
}

// buildTeamPresetList serializes TEAM_PRESET_LIST.
//
// Each preset is written in the exact layout the client's
// TeamPreset.unserialize expects (client's common/game/team/TeamPreset.java):
//
//	short id
//	byte  nameLen; byte[] name
//	byte  fighterCount
//	long[fighterCount] fighterIds   <-- REQUIRED
//
// NOTE: the legacy Java TeamPresetListRequest wrote only the fighter
// *count* and omitted the fighter IDs, so reloaded teams appeared empty on
// the client (it reads `count` longs regardless). This port writes the
// fighter IDs so team rosters survive a reconnect. The team's Fighters
// association must be preloaded by the caller (TeamService.ListTeams does
// this).
func buildTeamPresetList(teams []domain.Team) protocol.OutboundFrame {
	size := 1
	for _, t := range teams {
		size += 3 + len(t.Name) + 8*len(t.Fighters)
	}

	w := protocol.NewWriter(size)
	w.PutByte(byte(len(teams)))
	for _, t := range teams {
		w.PutInt16(t.Slot).PutString(t.Name).PutByte(byte(len(t.Fighters)))
		for _, f := range t.Fighters {
			w.PutInt64(int64(f.ID))
		}
	}
	return protocol.OutboundFrame{Opcode: protocol.SendTeamPresetList, Payload: w.Bytes()}
}
