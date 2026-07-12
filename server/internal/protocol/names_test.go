package protocol

import "testing"

func TestOpcodeNamesResolve(t *testing.T) {
	if got := RecvAuthentication.Name(); got != "AUTHENTICATION" {
		t.Errorf("RecvAuthentication.Name() = %q, want AUTHENTICATION", got)
	}
	if got := RecvTeamPresetSaveRequest.Name(); got != "TEAM_PRESET_SAVE_REQUEST" {
		t.Errorf("got %q", got)
	}
	if got := SendTeamPresetList.Name(); got != "TEAM_PRESET_LIST" {
		t.Errorf("got %q", got)
	}
	if got := SendCoachEquipmentUpdateMessage.Name(); got != "COACH_EQUIPMENT_UPDATE" {
		t.Errorf("got %q", got)
	}
}

func TestUnknownOpcodeFallsBackToNumber(t *testing.T) {
	if got := RecvOpcode(9999).Name(); got != "9999" {
		t.Errorf("unknown opcode Name() = %q, want %q", got, "9999")
	}
	if got := SendOpcode(8888).Name(); got != "8888" {
		t.Errorf("unknown opcode Name() = %q, want %q", got, "8888")
	}
}
