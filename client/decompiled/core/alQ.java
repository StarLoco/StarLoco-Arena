/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import com.ankamagames.xulor2.graphics.XulorParticleSystem;
import java.util.ArrayList;

public class alQ
extends adg_2
implements SF,
aHq {
    private static final alQ cFC = new alQ();
    public static final String TAG = "WorldPositionMarker";
    public static int cFD;
    private static String cFE;
    private float cFF;
    private float cFG;
    private EntityGroup cFH;
    private ArrayList cFI = new ArrayList();
    private final ArrayList cFJ = new ArrayList();
    private static final eu_2 cFK;
    private static final agu_0 cFL;

    private alQ() {
    }

    public static alQ getInstance() {
        return cFC;
    }

    public static void setParticlePath(String string) {
        cFE = string;
    }

    protected void pX() {
        super.pX();
        this.arC.i(this.cFH);
    }

    public void a(Io io) {
        this.cFI.add(io);
        XulorParticleSystem xulorParticleSystem = mq_1.rk().j(String.format(cFE, io.Uh()), 0);
        xulorParticleSystem.aUM().a(new avz());
        this.cFJ.add(xulorParticleSystem);
    }

    public void b(Io io) {
        if (this.cFI.remove(io)) {
            XulorParticleSystem xulorParticleSystem = (XulorParticleSystem)this.cFJ.remove(this.cFJ.size() - 1);
            xulorParticleSystem.alQ();
            xulorParticleSystem.HF();
        }
    }

    public String getTag() {
        return TAG;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    private int getOnScreenX(float f, float f2, int n2, int n3) {
        int n4 = this.getAppearance().getContentHeight() - n3;
        int n5 = this.getAppearance().getContentWidth() - n2;
        float f3 = (float)n4 / (float)n5;
        float f4 = f2 - (float)(n4 / 2);
        float f5 = f - (float)(n5 / 2);
        float f6 = f4 / f5;
        if (Math.abs(f6) < f3) {
            return f5 > 0.0f ? n5 : 0;
        }
        return (int)(Math.signum(f5) * (float)n4 / 2.0f / Math.abs(f6)) + n5 / 2;
    }

    private int getOnScreenY(float f, float f2, int n2, int n3) {
        int n4 = this.getAppearance().getContentHeight() - n3;
        int n5 = this.getAppearance().getContentWidth() - n2;
        float f3 = (float)n4 / (float)n5;
        float f4 = f2 - (float)(n4 / 2);
        float f5 = f - (float)(n5 / 2);
        float f6 = f4 / f5;
        if (Math.abs(f6) > f3) {
            return f4 > 0.0f ? n4 : 0;
        }
        return (int)(Math.signum(f4) * (float)n5 / 2.0f * Math.abs(f6)) + n4 / 2;
    }

    private void a(Io io, XulorParticleSystem xulorParticleSystem, float f, float f2) {
        float f3 = (float)io.getScreenX() - this.cFF;
        float f4 = (float)io.getScreenY() - this.cFG;
        int n2 = this.getOnScreenX((float)((int)f3) + f, (float)((int)f4) + f2, 50, 50);
        int n3 = this.getOnScreenY((float)((int)f3) + f, (float)((int)f4) + f2, 50, 50);
        avz avz2 = (avz)xulorParticleSystem.aUM().aI(0);
        float f5 = (float)n2 - f;
        float f6 = (float)n3 - f2;
        float f7 = this.N(f5, f6) - (float)Math.PI;
        cFK.a(cFL, f7);
        avz2.e(n2 + 25, n3 + 25, 0.0f);
        avz2.f(cFK);
        xulorParticleSystem.aUM().b(0, avz2);
        this.cFH.i(xulorParticleSystem);
    }

    private float N(float f, float f2) {
        if (f == 0.0f && f2 < 0.0f) {
            f = -1.0E-4f;
        }
        float f3 = (float)Math.sqrt(f * f + f2 * f2);
        float f4 = f2 / f3;
        float f5 = -f / f3;
        double d = Math.acos(f4);
        return (float)((double)Math.signum(f5) * d);
    }

    public void clear() {
        this.cFI.clear();
        for (int j = this.cFJ.size() - 1; j >= 0; --j) {
            XulorParticleSystem xulorParticleSystem = (XulorParticleSystem)this.cFJ.remove(0);
            xulorParticleSystem.alQ();
            xulorParticleSystem.HF();
        }
    }

    public void a(aba_2 aba_22, int n2) {
        float f = (float)this.cLZ.getContentWidth() / 2.0f;
        float f2 = (float)this.cLZ.getContentHeight() / 2.0f;
        this.cFH.removeAllChildren();
        for (int j = this.cFI.size() - 1; j >= 0; --j) {
            Io io = (Io)this.cFI.get(j);
            XulorParticleSystem xulorParticleSystem = (XulorParticleSystem)this.cFJ.get(j);
            this.a(io, xulorParticleSystem, f, f2);
            xulorParticleSystem.a((float)n2 / 1000.0f);
            xulorParticleSystem.b(this.cFH);
        }
    }

    public void a(aba_2 aba_22, float f, float f2) {
        this.cFF = (float)aba_22.i(f, f2);
        this.cFG = (float)aba_22.j(f, f2);
    }

    public void j() {
        super.j();
        this.cFH.HF();
        this.cFH = null;
        this.clear();
    }

    public void b() {
        super.b();
        Zb zb = Zb.checkOut();
        zb.setWidget(this);
        this.a(zb);
        auW auW2 = new auW();
        auW2.b();
        auW2.setSize(new agj_1(100.0f, 100.0f));
        this.a(auW2);
        this.dyc = true;
        this.cFH = (EntityGroup)yW.FL().a(EntityGroup.it(), EntityGroup.class);
        this.cFH.aUM().a(new avz());
    }

    static {
        cFK = new eu_2();
        cFL = new agu_0(0.0f, 0.0f, 1.0f);
    }
}

