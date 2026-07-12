package webadmin

import (
	"context"
	"errors"
	"net/http"
	"time"

	"github.com/rs/zerolog"
)

// Serve runs the portal HTTP server on addr until ctx is canceled, then
// gracefully shuts it down. It's shared by both the in-process launch (from
// internal/app when web.enabled) and the standalone cmd/web binary, so the
// two entry points exercise identical server setup.
func Serve(ctx context.Context, addr string, h *Handler, logger zerolog.Logger) error {
	srv := &http.Server{
		Addr:              addr,
		Handler:           h,
		ReadHeaderTimeout: 10 * time.Second,
		ReadTimeout:       20 * time.Second,
		WriteTimeout:      30 * time.Second,
		IdleTimeout:       120 * time.Second,
	}

	errCh := make(chan error, 1)
	go func() {
		logger.Info().Str("addr", addr).Msg("web portal listening")
		if err := srv.ListenAndServe(); err != nil && !errors.Is(err, http.ErrServerClosed) {
			errCh <- err
			return
		}
		errCh <- nil
	}()

	select {
	case <-ctx.Done():
		shutdownCtx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
		defer cancel()
		if err := srv.Shutdown(shutdownCtx); err != nil {
			logger.Warn().Err(err).Msg("web portal shutdown error")
		}
		logger.Info().Msg("web portal stopped")
		return nil
	case err := <-errCh:
		return err
	}
}
