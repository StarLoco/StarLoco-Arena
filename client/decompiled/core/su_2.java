/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from su
 */
public class su_2
extends uc_1 {
    public su_2(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "getTweenParticleSystemTime";
    }

    public LX[] Q() {
        return new LX[]{new LX("particleFileId", aos_1.elT, false), new LX("startX", aos_1.elT, false), new LX("startY", aos_1.elT, false), new LX("startZ", aos_1.elT, false), new LX("destX", aos_1.elT, false), new LX("destY", aos_1.elT, false), new LX("destZ", aos_1.elT, false), new LX("angle", aos_1.elT, false), new LX("type", aos_1.elT, false), new LX("timeCoef", aos_1.elU, true), new LX("level", aos_1.elT, true)};
    }

    public final LX[] R() {
        return new LX[]{new LX("movementDuration", aos_1.elT, false)};
    }

    public void c(int n2) {
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        int n5 = this.hW(2);
        int n6 = this.hW(3);
        int n7 = this.hW(4);
        int n8 = this.hW(5);
        int n9 = this.hW(6);
        int n10 = this.hW(7);
        this.hW(8);
        double d = n2 >= 9 ? this.hX(9) : -1.0;
        FreeParticleSystem freeParticleSystem = n2 >= 10 ? aiJ.ayv().bw(n3, this.hW(10)) : aiJ.ayv().kT(n3);
        freeParticleSystem.setPosition(n4, n5, n6);
        avw_0 avw_02 = d < 0.0 ? new avw_0(freeParticleSystem, n7, n8, n9, n10) : new avw_0(freeParticleSystem, n7, n8, n9, n10, d);
        this.id((int)avw_02.aJk());
        freeParticleSystem.HF();
    }
}

