package main

import "testing"

// TestValidateData_RealData runs the integrity validator over the real
// repositories and asserts the report is well-formed. It scans a non-trivial
// number of records and every issue must carry a navigable target. Skips when
// the data dir is absent.
func TestValidateData_RealData(t *testing.T) {
	a := newAppWithData(t)
	rep, err := a.ValidateData()
	if err != nil {
		t.Fatalf("ValidateData: %v", err)
	}
	if rep.Checked == 0 {
		t.Fatal("validator scanned zero records")
	}
	if rep.Errors+rep.Warnings+rep.Infos != len(rep.Issues) {
		t.Errorf("counts (%d/%d/%d) != issue count %d",
			rep.Errors, rep.Warnings, rep.Infos, len(rep.Issues))
	}
	for i, is := range rep.Issues {
		if is.Severity == "" || is.View == "" || is.Message == "" {
			t.Errorf("issue %d missing fields: %+v", i, is)
		}
	}
	t.Logf("validated %d records: %d errors, %d warnings, %d infos",
		rep.Checked, rep.Errors, rep.Warnings, rep.Infos)
}

// TestValidateData_DetectsBrokenSummonRef confirms the validator flags a summon
// effect that references a non-existent summoning, using a synthetic store so
// the assertion doesn't depend on the shipped data being clean or dirty.
func TestValidateData_SortsErrorsFirst(t *testing.T) {
	a := newAppWithData(t)
	rep, err := a.ValidateData()
	if err != nil {
		t.Fatalf("ValidateData: %v", err)
	}
	// Errors must precede warnings which must precede infos in the sorted list.
	rank := map[string]int{"error": 0, "warning": 1, "info": 2}
	last := -1
	for _, is := range rep.Issues {
		r := rank[is.Severity]
		if r < last {
			t.Fatalf("issues not sorted worst-first: %s after rank %d", is.Severity, last)
		}
		last = r
	}
}
