/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.sun.opengl.cg.CGcontext;
import com.sun.opengl.cg.CGeffect;
import com.sun.opengl.cg.CGparameter;
import com.sun.opengl.cg.CGtechnique;
import com.sun.opengl.cg.CgGL;
import java.util.zip.CRC32;
import org.apache.log4j.Logger;

/*
 * Renamed from sZ
 */
public class sz_0
extends asr_0 {
    private static final boolean amr = true;
    private static final Logger a = Logger.getLogger(sz_0.class);
    private static final zD ams = new fd_0();
    private static CGcontext amt = null;
    protected CGeffect amu;
    private lb_0 amv = new lb_0(5);
    private static final CRC32 amw = new CRC32();

    public void l(String string, String string2) {
        byte[] byArray;
        super.l(string, string2);
        if (amt == null) {
            try {
                amt = CgGL.cgCreateContext();
            }
            catch (NoClassDefFoundError noClassDefFoundError) {
                a.error((Object)"GlEffect not loaded : Cg library not loaded");
                return;
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                a.error((Object)("GlEffect not loaded : Cg library not found : " + unsatisfiedLinkError.getMessage()));
                return;
            }
            CgGL.cgGLRegisterStates(amt);
        }
        try {
            byArray = vq_2.readFile(string2);
        }
        catch (Exception exception) {
            a.error((Object)("Unable to read file " + string2));
            return;
        }
        String string3 = new String(byArray);
        this.amu = CgGL.cgCreateEffect(amt, string3, null);
        if (this.amu == null) {
            a.error((Object)CgGL.cgGetLastErrorString(null));
            a.error((Object)CgGL.cgGetLastListing(amt));
        }
        this.initialize();
    }

    public void reload() {
        if (this.amu != null) {
            CgGL.cgDestroyEffect(this.amu);
        }
        super.reload();
    }

    public void m(String string, String string2) {
        super.m(string, string2);
        amt = CgGL.cgCreateContext();
        CgGL.cgGLRegisterStates(amt);
        this.amu = CgGL.cgCreateEffect(amt, string2, null);
        this.initialize();
    }

    public void reset() {
        super.reset();
        if (!this.amv.isEmpty()) {
            this.amv.a(ams);
        }
        vo_1 vo_12 = vo_1.aik();
        vo_12.at(1.0f);
    }

    public void a(QI qI) {
        int n2 = qI.awT();
        if (this.aFO()) {
            this.cSB.c(n2, qI);
            return;
        }
        afm_1 afm_12 = (afm_1)this.amv.get(n2);
        if (afm_12 == null) {
            return;
        }
        if (qI.bHx == mr_0.JN) {
            if (afm_12.bHy[0] != qI.bHy[0]) {
                afm_12.bHy[0] = qI.bHy[0];
                CgGL.cgSetParameter1f(afm_12.crS, afm_12.bHy[0]);
            }
        } else if (qI.bHx == mr_0.JO) {
            if (afm_12.bHy[0] != qI.bHy[0] || afm_12.bHy[1] != qI.bHy[1] || afm_12.bHy[2] != qI.bHy[2] || afm_12.bHy[3] != qI.bHy[3]) {
                afm_12.bHy[0] = qI.bHy[0];
                afm_12.bHy[1] = qI.bHy[1];
                afm_12.bHy[2] = qI.bHy[2];
                afm_12.bHy[3] = qI.bHy[3];
                CgGL.cgSetParameter4fv(afm_12.crS, afm_12.bHy, 0);
            }
        } else if (afm_12.bHy[0] != qI.bHy[0] || afm_12.bHy[1] != qI.bHy[1] || afm_12.bHy[2] != qI.bHy[2] || afm_12.bHy[3] != qI.bHy[3] || afm_12.bHy[4] != qI.bHy[4] || afm_12.bHy[5] != qI.bHy[5] || afm_12.bHy[6] != qI.bHy[6] || afm_12.bHy[7] != qI.bHy[7] || afm_12.bHy[8] != qI.bHy[8] || afm_12.bHy[9] != qI.bHy[9] || afm_12.bHy[10] != qI.bHy[10] || afm_12.bHy[11] != qI.bHy[11] || afm_12.bHy[12] != qI.bHy[12] || afm_12.bHy[13] != qI.bHy[13] || afm_12.bHy[14] != qI.bHy[14] || afm_12.bHy[15] != qI.bHy[15]) {
            afm_12.bHy[0] = qI.bHy[0];
            afm_12.bHy[1] = qI.bHy[1];
            afm_12.bHy[2] = qI.bHy[2];
            afm_12.bHy[3] = qI.bHy[3];
            afm_12.bHy[4] = qI.bHy[4];
            afm_12.bHy[5] = qI.bHy[5];
            afm_12.bHy[6] = qI.bHy[6];
            afm_12.bHy[7] = qI.bHy[7];
            afm_12.bHy[8] = qI.bHy[8];
            afm_12.bHy[9] = qI.bHy[9];
            afm_12.bHy[10] = qI.bHy[10];
            afm_12.bHy[11] = qI.bHy[11];
            afm_12.bHy[12] = qI.bHy[12];
            afm_12.bHy[13] = qI.bHy[13];
            afm_12.bHy[14] = qI.bHy[14];
            afm_12.bHy[15] = qI.bHy[15];
            CgGL.cgSetParameterValuefr(afm_12.crS, 16, afm_12.bHy, 0);
        }
    }

    public void parse() {
        CGparameter cGparameter = CgGL.cgGetFirstEffectParameter(this.amu);
        do {
            String string;
            if ((string = CgGL.cgGetParameterSemantic(cGparameter)) == null || string.length() <= 0) continue;
            amw.reset();
            amw.update(string.getBytes());
            this.amv.c((int)amw.getValue(), new afm_1(this, cGparameter));
        } while ((cGparameter = CgGL.cgGetNextParameter(cGparameter)) != null);
    }

    public final void a(db_2 db_22, Entity entity) {
        super.a(db_22, entity);
        assert (db_22.vg() == arX.cQT) : "Invalid renderer type";
        if (this.aFO()) {
            QI qI = (QI)this.cSB.get(1973737728);
            if (qI != null) {
                vo_1 vo_12 = vo_1.aik();
                vo_12.at(qI.bHy[0]);
                vo_12.n(db_22);
            } else assert (false);
            entity.d(db_22);
        } else {
            this.cSz.a(db_22, entity);
        }
    }

    public static void a(CGcontext cGcontext) {
        amt = cGcontext;
    }

    private int zj() {
        int n2 = 0;
        CGtechnique cGtechnique = CgGL.cgGetFirstTechnique(this.amu);
        CgGL.cgGetTechniqueName(cGtechnique);
        while (cGtechnique != null) {
            ++n2;
            cGtechnique = CgGL.cgGetNextTechnique(cGtechnique);
        }
        return n2;
    }

    private void initialize() {
        this.cSy = new lb_0(this.zj());
        CGtechnique cGtechnique = CgGL.cgGetFirstTechnique(this.amu);
        while (cGtechnique != null) {
            vs_1 vs_12 = new vs_1(cGtechnique);
            int n2 = vs_12.awT();
            a.info((Object)("[TECHNIQUE] " + vs_12.getName() + " (" + String.format("0x%X", n2) + ")"));
            this.cSy.c(n2, vs_12);
            cGtechnique = CgGL.cgGetNextTechnique(cGtechnique);
        }
        this.parse();
    }
}

