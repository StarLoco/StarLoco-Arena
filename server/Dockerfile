# DofusArena 2.70 server — multi-stage build.
# Pure-Go (CGO disabled), so the final image is a tiny static binary on alpine.

# ---- build stage ----------------------------------------------------------
FROM golang:1.26-alpine AS build
WORKDIR /src

# Cache module downloads.
COPY go.mod go.sum ./
RUN go mod download

# Build the server + seed tool.
COPY . .
ENV CGO_ENABLED=0 GOOS=linux
RUN go build -trimpath -ldflags="-s -w" -o /out/arena-server ./cmd/server && \
    go build -trimpath -ldflags="-s -w" -o /out/seedaccount ./cmd/seedaccount

# ---- runtime stage --------------------------------------------------------
FROM alpine:3.20
RUN adduser -D -u 10001 arena
WORKDIR /app

COPY --from=build /out/arena-server /app/arena-server
COPY --from=build /out/seedaccount /app/seedaccount
COPY configs/ /app/configs/

# Game data (data.bdat/indexes.bdat) is mounted at /data at runtime.
# The DB (sqlite) or connection (postgres/mysql) is configured via config/env.
USER arena
EXPOSE 5555

# Default: read config from env (ARENA_*) or a mounted config file.
ENTRYPOINT ["/app/arena-server"]
CMD ["--config", "/app/configs/config.sqlite.yaml"]
