/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from zk
 */
public class zk_0
extends le_1 {
    public static final String TAG = "tintIntensityColorPicker";
    public static final String aET = "tintColorPicker";
    public static final String aEU = "intensityColorPicker";
    private ajc aEV;
    private ajc aEW;
    private int aEX = 8;
    private float aEY = 0.3f;
    private float aEZ = 1.0f;
    private ov_1 aFa;
    public static final int HA = "colors".hashCode();
    public static final int aFb = "numVariations".hashCode();
    public static final int aFc = "minIntensity".hashCode();
    public static final int aFd = "maxIntensity".hashCode();

    public void a(String string, adg_2 adg_22) {
        super.a(string, adg_22);
        if (string.equals(aET)) {
            this.aEV = (ajc)adg_22;
            this.Hz = true;
            this.setNeedsToPreProcess();
        } else if (string.equals(aEU)) {
            this.aEW = (ajc)adg_22;
            this.Hz = true;
            this.setNeedsToPreProcess();
        }
    }

    public int getNumVariation() {
        return this.aEX;
    }

    public void setNumVariation(int n2) {
        this.aEX = n2;
    }

    public float getMinIntensity() {
        return this.aEY;
    }

    public void setMinIntensity(float f) {
        this.aEY = f;
    }

    public float getMaxIntensity() {
        return this.aEZ;
    }

    public void setMaxIntensity(float f) {
        this.aEZ = f;
    }

    private void d(vP vP2) {
        if (this.aEW == null) {
            return;
        }
        ArrayList<vP> arrayList = new ArrayList<vP>(this.aEX);
        for (int j = 0; j < this.aEX; ++j) {
            vP vP3 = new vP(vP2);
            float f = this.aEY + (float)j * (this.aEZ - this.aEY) / (float)(this.aEX - 1);
            vP3.V(f);
            arrayList.add(vP3);
        }
        this.aEW.setColors(arrayList);
    }

    protected void qt() {
        this.aEV.setColors(this.Hy);
        if (this.Hy == null || this.Hy.size() == 0) {
            return;
        }
        this.d((vP)this.Hy.get(0));
    }

    private void registerListeners() {
        this.aFa = new kx_0(this);
        this.a(qe_1.bFi, this.aFa, true);
    }

    public void j() {
        super.j();
        this.aEV = null;
        this.aEW = null;
        this.aFa = null;
    }

    public void b() {
        super.b();
        ei_1 ei_12 = (ei_1)this.getLayoutManager();
        ei_12.setHorizontal(false);
        this.aEX = 8;
        this.aEY = 0.3f;
        this.aEZ = 1.0f;
        this.registerListeners();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aFb) {
            this.setNumVariation(Gr.R(string));
        } else if (n2 == aFd) {
            this.setMaxIntensity(Gr.getFloat(string));
        } else if (n2 == aFc) {
            this.setMinIntensity(Gr.getFloat(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == HA) {
            this.setColors((ArrayList)object);
        } else if (n2 == aFb) {
            this.setNumVariation(Gr.R(object));
        } else if (n2 == aFd) {
            this.setMaxIntensity(Gr.getFloat(object));
        } else if (n2 == aFc) {
            this.setMinIntensity(Gr.getFloat(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ ajc a(zk_0 zk_02) {
        return zk_02.aEW;
    }

    static /* synthetic */ void a(zk_0 zk_02, vP vP2) {
        zk_02.d(vP2);
    }
}

