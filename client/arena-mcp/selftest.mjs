// Self-test: drive the arena MCP server through the real MCP client transport,
// exercising the full process lifecycle + agent proxy. Run: node selftest.mjs
import { Client } from "@modelcontextprotocol/sdk/client/index.js";
import { StdioClientTransport } from "@modelcontextprotocol/sdk/client/stdio.js";

const transport = new StdioClientTransport({ command: "node", args: ["server.mjs"] });
const client = new Client({ name: "selftest", version: "1" });
await client.connect(transport);

const text = (r) => (r.content || []).map((c) => c.type === "text" ? c.text : `[${c.type} ${c.data?.length || 0}b]`).join("\n");
async function call(name, args = {}) {
  process.stderr.write(`\n>>> ${name} ${JSON.stringify(args)}\n`);
  const r = await client.callTool({ name, arguments: args });
  process.stderr.write(text(r) + "\n");
  return r;
}

await call("arena_up", { rebuild: false, waitSeconds: 30 });
await call("arena_status");
await call("arena_login", { user: "locos975", pass: "azerty" });
await call("arena_roster");
await call("arena_screenshot"); // returns image; we just report its size
await call("arena_client_log", { filter: "ERROR|Exception|Erreur", lines: 5 });
await call("arena_down");

await client.close();
process.stderr.write("\nSELFTEST DONE\n");
process.exit(0);
