/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import java.util.HashMap;
import java.util.Map;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aMd
 */
public class amd_0 {
    private final String m_name;
    private Map dXp = new HashMap();

    public amd_0() {
        this.m_name = null;
    }

    public amd_0(String string) {
        this.m_name = string;
    }

    public final void u(String string, Object object) {
        this.dXp.put(string, object);
    }

    public final String getName() {
        return this.m_name;
    }

    public final void c(LuaState luaState) {
        assert (this.m_name != null);
        if (this.m_name.length() == 0) {
            for (Map.Entry entry : this.dXp.entrySet()) {
                new jJ(entry.getValue()).c(luaState);
                luaState.setGlobal((String)entry.getKey());
            }
        } else {
            luaState.newTable();
            for (Map.Entry entry : this.dXp.entrySet()) {
                luaState.pushString((String)entry.getKey());
                new jJ(entry.getValue()).c(luaState);
                luaState.setTable(-3);
            }
            luaState.setGlobal(this.m_name);
        }
    }
}

