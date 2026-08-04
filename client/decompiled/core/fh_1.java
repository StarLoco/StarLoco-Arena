/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.zip.CRC32;

/*
 * Renamed from fH
 */
public abstract class fh_1
extends aFe {
    private static final String rJ = ".xps";
    private static final CRC32 qM = new CRC32();
    public static int rK = 0;
    public final FilenameFilter rL = new aul_0(this);

    protected fh_1() {
        super(0x500000L, true);
    }

    public abstract ParticleSystem ai(String var1);

    public String getExtension() {
        return rJ;
    }

    public final FilenameFilter getFilenameFilter() {
        return this.rL;
    }

    protected String az(int n2) {
        return this.getPath() + n2 + this.getExtension();
    }

    protected void a(int n2, int n3, ParticleSystem particleSystem) {
        String string = this.az(n2);
        this.a(string, n3, particleSystem);
    }

    protected void a(String string, int n2, ParticleSystem particleSystem) {
        long l2;
        this.a(particleSystem, string);
        this.b(string, n2, particleSystem);
        particleSystem.alT();
        try {
            l2 = 0xDD00DD0000000000L | particleSystem.bZD;
        }
        catch (Exception exception) {
            a.error((Object)("The name of a particle system must be a number" + exception));
            qM.reset();
            qM.update(vq_2.gs(string).getBytes());
            l2 = 0xDD00DD0000000000L | qM.getValue();
        }
        String string2 = vq_2.gt(string) + "/" + particleSystem.bZD + ".tga";
        cx_0 cx_02 = cx_0.JY();
        ef_1 ef_12 = cx_02.bt(l2);
        if (ef_12 == null) {
            aon_2 aon_22 = new aon_2(string2);
            ef_12 = cx_02.a(arX.cQT.iE(), l2, aon_22, false);
        }
        particleSystem.e(ef_12);
    }

    protected void b(String string, int n2, ParticleSystem particleSystem) {
        particleSystem.eA = string;
        particleSystem.bZE = Gr.d((Object)vq_2.gs(string), 0);
        alo_2.a(string, particleSystem, n2);
    }

    protected void a(ParticleSystem particleSystem, String string) {
        long l2;
        int n2;
        int n3 = string.lastIndexOf(47);
        int n4 = n3 > (n2 = string.lastIndexOf(92)) ? n3 : n2;
        int n5 = string.indexOf(46, n4);
        String string2 = string.substring(n4 + 1, n5);
        try {
            l2 = 0xDD00DD0000000000L | (long)Integer.parseInt(string2);
        }
        catch (Exception exception) {
            qM.reset();
            qM.update(string2.getBytes());
            l2 = 0xDD00DD0000000000L | qM.getValue();
        }
        particleSystem.dj(l2);
    }

    public final void a(int n2, ParticleSystem particleSystem) {
        try {
            particleSystem.alS().clear();
            ArrayList arrayList = particleSystem.alY();
            alo_2.a(particleSystem.eA, particleSystem, n2);
            for (int j = 0; j < arrayList.size(); ++j) {
                mp_1 mp_12 = (mp_1)particleSystem.alS().get(j);
                Emitter emitter = (Emitter)arrayList.get(j);
                emitter.c(mp_12);
            }
            particleSystem.alV();
        }
        catch (Exception exception) {
            a.error((Object)"", (Throwable)exception);
        }
    }
}

