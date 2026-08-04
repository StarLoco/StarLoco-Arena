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
 * Renamed from abj
 */
public abstract class abj_2
extends uc_1 {
    private static final Logger a = Logger.getLogger(abj_2.class);

    protected abj_2(LuaState luaState) {
        super(luaState);
    }

    public LX[] R() {
        return new LX[]{new LX("systemId", aos_1.elT, false)};
    }

    protected abstract ahh_1 K(long var1);

    protected abstract void a(FreeParticleSystem var1, ahh_1 var2, int var3);

    protected final void a(FreeParticleSystem freeParticleSystem) {
        int n2 = this.agC().Gf();
        if (n2 != -1) {
            freeParticleSystem.eC(n2);
        }
    }

    protected void c(int n2) {
        int n3 = this.hW(0);
        long l2 = this.hY(1);
        FreeParticleSystem freeParticleSystem = n2 >= 3 ? aiJ.ayv().bw(n3, this.hW(2)) : aiJ.ayv().kT(n3);
        ahh_1 ahh_12 = this.K(l2);
        if (ahh_12 != null) {
            this.a(freeParticleSystem, ahh_12, n2);
            qd_1.uW().b(freeParticleSystem);
            this.id(freeParticleSystem.getId());
        } else {
            this.a(a, "pas d'\u00e9l\u00e9ment interactif trouv\u00e9 " + l2);
            this.agB();
        }
    }
}

