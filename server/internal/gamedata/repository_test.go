package gamedata

import (
	"errors"
	"sync/atomic"
	"testing"
)

type fakeTemplate struct {
	id int32
}

func (f fakeTemplate) TemplateID() int32 { return f.id }

func TestRepositoryLazyLoadsOnlyOnce(t *testing.T) {
	var loadCount atomic.Int32
	repo := NewRepository(func() (map[int32]fakeTemplate, error) {
		loadCount.Add(1)
		return map[int32]fakeTemplate{
			1: {id: 1},
			2: {id: 2},
		}, nil
	})

	if loadCount.Load() != 0 {
		t.Fatalf("loader should not run before first access, ran %d times", loadCount.Load())
	}

	v, ok := repo.Get(1)
	if !ok || v.id != 1 {
		t.Errorf("Get(1) = %+v, %v", v, ok)
	}
	if loadCount.Load() != 1 {
		t.Fatalf("loader should have run exactly once after first Get, ran %d times", loadCount.Load())
	}

	// Further calls must not re-trigger the loader.
	_, _ = repo.Get(2)
	_ = repo.All()
	_ = repo.Len()
	if loadCount.Load() != 1 {
		t.Fatalf("loader ran %d times, want exactly 1 (lazy + cached)", loadCount.Load())
	}
}

func TestRepositoryGetMissingID(t *testing.T) {
	repo := NewRepository(func() (map[int32]fakeTemplate, error) {
		return map[int32]fakeTemplate{1: {id: 1}}, nil
	})

	v, ok := repo.Get(999)
	if ok {
		t.Errorf("Get(999) ok = true, want false")
	}
	if v.id != 0 {
		t.Errorf("Get(999) should return zero value, got %+v", v)
	}
}

func TestRepositoryPropagatesLoadError(t *testing.T) {
	wantErr := errors.New("boom")
	repo := NewRepository(func() (map[int32]fakeTemplate, error) {
		return nil, wantErr
	})

	_, ok := repo.Get(1)
	if ok {
		t.Error("Get should return false when load failed")
	}
	if err := repo.Err(); !errors.Is(err, wantErr) {
		t.Errorf("Err() = %v, want %v", err, wantErr)
	}
}

func TestRepositoryWarmUpTriggersLoad(t *testing.T) {
	var loaded atomic.Bool
	repo := NewRepository(func() (map[int32]fakeTemplate, error) {
		loaded.Store(true)
		return map[int32]fakeTemplate{}, nil
	})

	if err := repo.WarmUp(); err != nil {
		t.Fatalf("WarmUp: %v", err)
	}
	if !loaded.Load() {
		t.Error("WarmUp should have triggered the loader immediately")
	}
}

func TestRepositoryConcurrentAccessLoadsOnce(t *testing.T) {
	var loadCount atomic.Int32
	repo := NewRepository(func() (map[int32]fakeTemplate, error) {
		loadCount.Add(1)
		return map[int32]fakeTemplate{1: {id: 1}}, nil
	})

	const goroutines = 50
	done := make(chan struct{})
	for i := 0; i < goroutines; i++ {
		go func() {
			defer func() { done <- struct{}{} }()
			_, _ = repo.Get(1)
		}()
	}
	for i := 0; i < goroutines; i++ {
		<-done
	}

	if loadCount.Load() != 1 {
		t.Errorf("loader ran %d times under concurrent access, want exactly 1", loadCount.Load())
	}
}
