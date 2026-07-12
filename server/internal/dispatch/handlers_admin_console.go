package dispatch

import (
	"fmt"
	"strings"

	"github.com/dofusarena/go-server/internal/netio"
	"github.com/dofusarena/go-server/internal/protocol"
)

// This file wires the client's in-game admin console protocol:
// CONSOLE_ADMIN_COMMAND (8193, client->server), CONSOLE_ADMIN_COMMAND_RESULT
// (8194, server->client) and DEFAULT_RESULT (8195, server->client).
//
// The client sends a free-text command string over the *regular game
// socket* (client ConsoleAdminCommandMessage is an OutputOnlyProxyMessage
// dispatched via DofusArenaGameEntity.getNetworkEntity().sendMessage, NOT
// the separate SSL AdminServerInstance), and renders each result line in
// its console via ConsoleManager.trace/log/err keyed on the result's
// leading type byte. We reply with one or more CONSOLE_ADMIN_COMMAND_RESULT
// lines and a trailing DEFAULT_RESULT completion code.
//
// Note this corrects handlers_gm_commands.go's earlier "out of scope"
// framing: the ConsoleAdminCommand* opcodes ride the normal game protocol
// and are wirable here. The legacy proxy/worldmanager/connection/game/chat
// live-property subsystem queries (which used the encrypted
// AdminServerInstance listener) remain a distinct, out-of-scope subsystem.
//
// Access is gated by sessionIsAdmin (the same Account.IsAdmin flag used by
// the GM chat commands and the web portal); non-admins get an ERROR line
// and no command runs. Every attempt is logged for auditing.

// adminCommand describes one console verb.
type adminCommand struct {
	usage string
	help  string
	run   func(session *netio.Session, args []string, deps *Deps)
}

// adminCommands is the console command table, keyed by uppercased verb.
// Populated in init() rather than a composite literal to avoid a static
// initialization cycle (runAdminHelp reads this same table).
var adminCommands map[string]adminCommand

// adminCommandOrder gives HELP a deterministic listing order (Go map
// iteration is randomized) and defines which verbs exist.
var adminCommandOrder = []string{"HELP", "STATUS", "PING"}

func init() {
	adminCommands = map[string]adminCommand{
		"HELP": {
			usage: "HELP",
			help:  "list available commands",
			run:   runAdminHelp,
		},
		"STATUS": {
			usage: "STATUS",
			help:  "show server version and online coach count",
			run:   runAdminStatus,
		},
		"PING": {
			usage: "PING",
			help:  "check the console round-trip (replies pong)",
			run:   runAdminPing,
		},
	}
}

// RegisterAdminConsoleHandlers wires the CONSOLE_ADMIN_COMMAND opcode.
func RegisterAdminConsoleHandlers(r *Router, deps *Deps) {
	r.Register(protocol.RecvConsoleAdminCommand, func(session *netio.Session, payload *protocol.Reader) {
		handleConsoleAdminCommand(session, payload, deps)
	})
}

// handleConsoleAdminCommand decodes a console command (a single 1-byte
// length-prefixed string), enforces the admin gate, dispatches to the
// command table, and sends a DEFAULT_RESULT completion code.
func handleConsoleAdminCommand(session *netio.Session, payload *protocol.Reader, deps *Deps) {
	command := payload.String()
	if payload.Err() != nil {
		return
	}

	if !sessionIsAdmin(session) {
		var accountID uint
		if ref := session.Account(); ref != nil {
			accountID = ref.AccountID
		}
		deps.Logger.Warn().
			Uint("account_id", accountID).
			Str("command", command).
			Msg("rejected admin console command from non-admin session")
		sendAdminError(session, "Permission denied.")
		session.Send(buildDefaultResult(1))
		return
	}

	fields := strings.Fields(command)
	if len(fields) == 0 {
		sendAdminError(session, "Empty command. Type HELP for the command list.")
		session.Send(buildDefaultResult(1))
		return
	}

	verb := strings.ToUpper(fields[0])
	args := fields[1:]

	var accountID uint
	if ref := session.Account(); ref != nil {
		accountID = ref.AccountID
	}
	deps.Logger.Info().
		Uint("account_id", accountID).
		Str("command", command).
		Msg("admin console command")

	cmd, ok := adminCommands[verb]
	if !ok {
		sendAdminError(session, "Unknown command: "+fields[0]+". Type HELP for the command list.")
		session.Send(buildDefaultResult(1))
		return
	}

	cmd.run(session, args, deps)
	// A single generic success completion code (0). Individual commands
	// have already streamed their own TRACE/LOG/ERROR output above.
	session.Send(buildDefaultResult(0))
}

// runAdminHelp lists every registered command with its usage and summary.
func runAdminHelp(session *netio.Session, _ []string, _ *Deps) {
	sendAdminLog(session, "Available commands:")
	// Emit in a stable order for predictable output.
	for _, verb := range adminCommandOrder {
		cmd := adminCommands[verb]
		sendAdminLog(session, fmt.Sprintf("  %-8s - %s", cmd.usage, cmd.help))
	}
}

// runAdminStatus reports the running server version and how many coaches
// are currently online.
func runAdminStatus(session *netio.Session, _ []string, deps *Deps) {
	v := deps.Server.Version
	sendAdminLog(session, fmt.Sprintf("Server version %d.%d build %s", v.Major, v.Revision, v.Build))
	online := 0
	if deps.World != nil {
		online = deps.World.Len()
	}
	sendAdminLog(session, fmt.Sprintf("Online coaches: %d", online))
}

// runAdminPing replies with a trace line, useful to confirm the console
// round-trip is alive.
func runAdminPing(session *netio.Session, _ []string, _ *Deps) {
	sendAdminTrace(session, "pong")
}
