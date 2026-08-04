/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaObject
 *  org.keplerproject.luajava.LuaState
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aJm
 */
public class ajm_1
extends uc_1 {
    public ajm_1(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "addParticleSystem";
    }

    public LX[] Q() {
        return new LX[]{new LX("particleFileId", aos_1.elT, false), new LX("x", aos_1.elT, false), new LX("y", aos_1.elT, false), new LX("z", aos_1.elT, false), new LX("level", aos_1.elT, true), new LX("fightId", aos_1.elT, true)};
    }

    public final LX[] R() {
        return new LX[]{new LX("systemId", aos_1.elT, false)};
    }

    public void c(int n2) {
        int n3 = this.hW(0);
        int n4 = this.hW(1);
        int n5 = this.hW(2);
        int n6 = this.hW(3);
        if (n3 == 0) {
            this.id(-1);
            return;
        }
        FreeParticleSystem freeParticleSystem = n2 >= 5 ? aiJ.ayv().bw(n3, this.hW(4)) : aiJ.ayv().kT(n3);
        int n7 = n2 >= 6 ? this.hW(5) : -1;
        freeParticleSystem.setPosition(n4, n5, n6);
        LuaObject luaObject = this.agC().getLuaState().getLuaObject("fightId");
        if (luaObject.isNumber()) {
            freeParticleSystem.eC((int)luaObject.getNumber());
        } else if (n7 != -1) {
            freeParticleSystem.eC(n7);
        }
        qd_1.uW().b(freeParticleSystem);
        this.id(freeParticleSystem.getId());
    }
}

