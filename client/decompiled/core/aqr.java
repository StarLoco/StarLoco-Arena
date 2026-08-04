/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.keplerproject.luajava.LuaState
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;
import org.apache.log4j.Logger;
import org.keplerproject.luajava.LuaState;

public class aqr
extends uc_1 {
    private static final Logger a = Logger.getLogger(aqr.class);

    public aqr(LuaState luaState) {
        super(luaState);
    }

    public String getName() {
        return "setPartColor";
    }

    public LX[] Q() {
        return new LX[]{new LX("mobileId", aos_1.elR, false), new LX("partName", aos_1.elS, false), new LX("red", aos_1.elU, false), new LX("green", aos_1.elU, false), new LX("blue", aos_1.elU, false), new LX("alpha", aos_1.elU, false)};
    }

    public final LX[] R() {
        return null;
    }

    public void c(int n2) {
        long l2 = this.hY(0);
        String string = this.hZ(1);
        float f = (float)this.hX(2);
        float f2 = (float)this.hX(3);
        float f3 = (float)this.hX(4);
        float f4 = (float)this.hX(5);
        mT mT2 = bd_1.Is().bb(l2);
        if (mT2 != null) {
            int n3 = Anm.ah(string);
            if (n3 == 0) {
                this.a(a, "partName invalid " + string);
                return;
            }
            mT2.b(n3, new float[]{f, f2, f3, f4});
        } else {
            this.a(a, "le mobile " + l2 + " n'existe pas ");
        }
    }
}

