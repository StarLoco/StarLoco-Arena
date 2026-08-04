/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from Tk
 */
public class tk_0
extends abj_2 {
    private static final Logger a = Logger.getLogger(tk_0.class);

    public tk_0(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "addParticleSystemToIe";
    }

    public LX[] Q() {
        return new LX[]{new LX("particleFileId", aos_1.elT, false), new LX("targetId", aos_1.elR, false), new LX("level", aos_1.elT, true)};
    }

    protected ahh_1 K(long l2) {
        return GY.Ss().bF(l2);
    }

    protected void a(FreeParticleSystem freeParticleSystem, ahh_1 ahh_12, int n2) {
        freeParticleSystem.a(ahh_12);
    }
}

