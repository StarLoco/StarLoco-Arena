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

public class eT
extends abj_2 {
    private static final Logger a = Logger.getLogger(eT.class);

    public eT(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "addParticleSystemToTarget";
    }

    public LX[] Q() {
        return new LX[]{new LX("particleFileId", aos_1.elT, false), new LX("targetId", aos_1.elR, false), new LX("level", aos_1.elT, true)};
    }

    protected ahh_1 K(long l2) {
        return bd_1.Is().bb(l2);
    }

    protected void a(FreeParticleSystem freeParticleSystem, ahh_1 ahh_12, int n2) {
        freeParticleSystem.a(ahh_12);
        this.a(freeParticleSystem);
    }
}

