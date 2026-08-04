/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;

/*
 * Renamed from XG
 */
public class xg_2 {
    public static final float[] bYU = null;
    private static final float[] bYV = new float[]{1.0f, 0.0f, 0.0f, 1.0f};
    private static final float[] bYW = new float[]{1.0f, 0.4f, 0.0f, 1.0f};
    private static final float[] bYX = new float[]{0.0f, 0.6f, 0.0f, 1.0f};
    private static final float bYY = 0.0f;
    private static final float bYZ = 2.66f;
    private static final float bZa = 2.166f;
    private static final float bZb = 1.66f;
    private static final int bZc = 0;
    private static final int bZd = 0;
    private static final int bZe = 0;
    private static final int bZf = 0;
    private static final do_0 bZg = do_0.aNC;
    private static final aiJ bZh = aiJ.ayv();
    private static final qd_1 bZi = qd_1.uW();
    private static final cp_2 bZj = new cp_2();
    private static final cp_2 bZk = new cp_2();

    public static float[] c(aez_0 aez_02) {
        int n2;
        float[] fArray = aez_02 == null ? bYU : (!aet_0.nC(n2 = aez_02.aQc()) ? bYU : (aet_0.nD(n2) ? bYV : (aet_0.nE(n2) ? bYW : (aet_0.nF(n2) ? bYX : bYU))));
        return fArray;
    }

    public static int d(aez_0 aez_02) {
        int n2;
        int n3 = aez_02 == null ? 0 : (!aet_0.nC(n2 = aez_02.aQc()) ? 0 : (aet_0.nD(n2) ? 0 : (aet_0.nE(n2) ? 0 : (aet_0.nF(n2) ? 0 : 0))));
        return n3;
    }

    public static float e(aez_0 aez_02) {
        int n2;
        float f = aez_02 == null ? 0.0f : (!aet_0.nC(n2 = aez_02.aQc()) ? 0.0f : (aet_0.nD(n2) ? 2.66f : (aet_0.nE(n2) ? 2.166f : (aet_0.nF(n2) ? 1.66f : 0.0f))));
        return f;
    }

    public static void l(mT mT2) {
        aez_0 aez_02;
        float[] fArray;
        if (mT2 != null && mT2 instanceof aez_0 && (fArray = xg_2.c(aez_02 = (aez_0)mT2)) != bYU) {
            FreeParticleSystem freeParticleSystem;
            int n2;
            float f;
            long l2 = aez_02.getId();
            lP lP2 = (lP)bZg.P();
            if (lP2 != null && (f = xg_2.e(aez_02)) != 0.0f) {
                bZg.a(lP2);
                lP2.s(fArray[0], fArray[1], fArray[2]);
                lP2.a(aez_02);
                lP2.u(f);
                bZj.a(l2, lP2);
            }
            if ((n2 = xg_2.d(aez_02)) != 0 && (freeParticleSystem = bZh.kT(n2)) != null) {
                bZi.b(freeParticleSystem);
                freeParticleSystem.a(aez_02);
                freeParticleSystem.setDuration(Integer.MAX_VALUE);
                bZk.a(l2, freeParticleSystem);
            }
        }
    }

    public static void m(mT mT2) {
        if (mT2 != null && mT2 instanceof aez_0) {
            FreeParticleSystem freeParticleSystem;
            aez_0 aez_02 = (aez_0)mT2;
            lP lP2 = (lP)bZj.t(aez_02.getId());
            if (lP2 != null) {
                bZg.b(lP2);
                lP2.a((Du)null);
            }
            if ((freeParticleSystem = (FreeParticleSystem)bZk.t(aez_02.getId())) != null) {
                bZi.cK(freeParticleSystem.getId());
                freeParticleSystem.a((Du)null);
            }
        }
    }
}

