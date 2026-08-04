/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aud
 */
public class aud_0
extends uc_1 {
    public aud_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "playMusic";
    }

    public LX[] Q() {
        return new LX[]{new LX("musicFileId", aos_1.elR, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
    }
}

