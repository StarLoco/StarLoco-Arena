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
 * Renamed from adJ
 */
public class adj_1
extends eT {
    private static final Logger a = Logger.getLogger(adj_1.class);

    public adj_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "addParticleSystemToTargetWithOffset";
    }

    public LX[] Q() {
        return new LX[]{new LX("particleFileId", aos_1.elT, false), new LX("targetId", aos_1.elR, false), new LX("level", aos_1.elT, false), new LX("height", aos_1.elT, true)};
    }

    protected void a(FreeParticleSystem freeParticleSystem, ahh_1 ahh_12, int n2) {
        int n3 = n2 == 4 ? this.hW(3) : ahh_12.ge();
        freeParticleSystem.a(ahh_12, n3);
        this.a(freeParticleSystem);
    }
}

