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
 * Renamed from ael
 */
public class ael_1
extends uc_1 {
    private static final Logger a = Logger.getLogger(ael_1.class);

    public ael_1(LuaState luaState) {
        super(luaState);
    }

    public final String getName() {
        return "playRandomApsSound";
    }

    public final LX[] Q() {
        return new LX[]{new LX("fightId", aos_1.elT, false), new LX("apsId", aos_1.elT, false), new LX("duration", aos_1.elT, false), new LX("fadeOutTime", aos_1.elT, false), new LX("rollOffId", aos_1.elT, false), new LX("loop", aos_1.elV, false), new LX("soundId,gain", aos_1.elX, true)};
    }

    public final LX[] R() {
        return null;
    }

    public final void c(int n2) {
        if (!aau_0.apB().apx()) {
            return;
        }
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        int n5 = this.hW(2);
        int n6 = this.hW(3);
        int n7 = this.hW(4);
        boolean bl2 = this.ic(5);
        int n8 = ej_0.am((n2 - 6) / 2) * 2 + 6;
        long l2 = this.hY(n8);
        float f = (float)this.hW(n8 + 1) / 100.0f;
        long l3 = System.currentTimeMillis();
        long l4 = l3 + (long)n5;
        long l5 = n6 != 0 ? l4 - (long)n6 : 0L;
        IsoParticleSystem isoParticleSystem = qd_1.uW().cJ(n4);
        try {
            if (l2 != 0L) {
                xt_0 xt_02 = isoParticleSystem == null ? aau_0.apB().a(l2, f, bl2 ? 0 : 1, l4, l5, n3) : aau_0.apB().a(l2, f, bl2 ? 0 : 1, l4, l5, n3, isoParticleSystem, n7, false);
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

