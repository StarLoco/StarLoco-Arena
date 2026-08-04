/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aeR
 */
public class aer_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(aer_2.class);

    public aer_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "stopMusic";
    }

    public LX[] Q() {
        return null;
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        this.a(a, "stop music not yet implemented");
    }
}

