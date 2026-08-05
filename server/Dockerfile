# DofusArena 2.70 server — multi-stage build.
# Pure-Go (CGO disabled), so the final image is a tiny static binary on alpine.

# ---- build stage ----------------------------------------------------------
FROM golang:1.26-alpine AS build
WORKDIR /src

# Cache module downloads.
COPY go.mod go.sum ./
RUN go mod download

# Build the server + seed tool.
# VERSION is stamped into the binary so `arena-server --version` and the update
# check work in the image too:
#   docker build --build-arg VERSION=v0.1.0 .
ARG VERSION=dev
COPY . .
ENV CGO_ENABLED=0 GOOS=linux
RUN go build -trimpath \
      -ldflags="-s -w -X github.com/StarLoco/arena-2.70/internal/version.Version=${VERSION}" \
      -o /out/arena-server ./cmd/server && \
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

# 5555 = game client, 8080 = the account-registration web portal. The portal is
# pinned to a fixed port here rather than left on "choose one for me", so the
# EXPOSE/publish mapping is predictable.
ENV ARENA_WEB_ADDR=0.0.0.0:8080
EXPOSE 5555 8080

# Default: read config from env (ARENA_*) or a mounted config file.
ENTRYPOINT ["/app/arena-server"]
CMD ["--config", "/app/configs/config.sqlite.yaml"]
