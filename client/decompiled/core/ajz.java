/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import java.io.ByteArrayOutputStream;
import org.apache.log4j.Logger;

public abstract class ajz {
    private static final Logger a = Logger.getLogger(ajz.class);
    public static final String cAC = "png";
    public static final float cAD = 1.5f;
    protected final Entity3D cAE = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
    protected final int abn;
    protected final int abo;
    protected final float aaw;
    protected final float Gv;
    protected final float Gw;
    protected gw_2 cAF;
    protected String tF;

    protected ajz(int n2, int n3, float f, float f2, float f3) {
        this.abn = n2;
        this.abo = n3;
        this.aaw = f;
        this.Gv = f2;
        this.Gw = f3;
    }

    public final void a(gw_2 gw_22, String string, String ... stringArray) {
        this.cAF = new gw_2(gw_22);
        this.cAF.setMaterial(aPb.enf);
        this.tF = string;
        for (int j = 0; j < stringArray.length; ++j) {
            this.cAF.aA(ej_0.Z(stringArray[j]));
        }
    }

    protected boolean azh() {
        if (this.cAF.is()) {
            this.cAF.setAnimation(this.tF);
            this.cAF.a(0, this.cAE, 0);
            return true;
        }
        return false;
    }

    public abstract void a(ByteArrayOutputStream var1, String var2);

    public abstract void L(String var1, String var2);

    public abstract void a(String var1, uz_1 var2);

    public final void cleanup() {
        this.cAE.HF();
    }

    static /* synthetic */ Logger Dm() {
        return a;
    }
}

