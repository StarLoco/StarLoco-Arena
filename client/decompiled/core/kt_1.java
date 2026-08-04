/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.IsoParticleSystem;
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from kt
 */
public class kt_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(kt_1.class);

    public kt_1(LuaState luaState) {
        super(luaState);
    }

    public final String getName() {
        return "playApsSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("soundFileId", aos_1.elR, false), new LX("fightId", aos_1.elT, false), new LX("gain", aos_1.elT, false), new LX("apsId", aos_1.elT, false), new LX("duration", aos_1.elT, false), new LX("fadeOutTime", aos_1.elT, false), new LX("rollOffId", aos_1.elT, false), new LX("loop", aos_1.elV, false)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        if (!aau_0.apB().apx()) {
            return;
        }
        long l2 = this.hY(0);
        int n3 = this.hW(1);
        float f = (float)this.hW(2) / 100.0f;
        int n4 = this.hW(3);
        int n5 = this.hW(4);
        int n6 = this.hW(5);
        int n7 = this.hW(6);
        boolean bl2 = this.ic(7);
        long l3 = System.currentTimeMillis();
        long l4 = n5 != -1 ? l3 + (long)n5 : -1L;
        long l5 = n6 != 0 ? l4 - (long)n6 : -1L;
        IsoParticleSystem isoParticleSystem = qd_1.uW().cJ(n4);
        try {
            if (l2 != 0L) {
                xt_0 xt_02 = isoParticleSystem != null ? aau_0.apB().a(l2, f, bl2 ? 0 : 1, l4, l5, n3, isoParticleSystem, n7, false) : aau_0.apB().a(l2, f, bl2 ? 0 : 1, l4, l5, n3);
                if (xt_02 != null) {
                    atW.aGY().j(n4, xt_02.alE());
                }
            } else {
                a.error((Object)"Id du son nul");
            }
        }
        catch (Exception exception) {
            this.a(a, "soundExtension or soundPath not initialized " + exception);
        }
    }
}

