/*
 * Decompiled with CFR 0.152.
 */
public class G
implements LM {
    private int aG;
    private int aH;
    private xy_0 aI;
    private String aJ;
    private boolean aK = false;

    public G(int n2, int n3, xy_0 xy_02, String string) {
        this.aG = n2;
        this.aH = n3;
        this.aI = xy_02;
        this.aJ = string;
        this.aK = true;
    }

    public G(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase("cursor") || k_02.f("path") == null) {
            return;
        }
        k_0 k_03 = k_02.f("path");
        if (k_03 != null) {
            k_0 k_04 = k_02.f("x");
            k_0 k_05 = k_02.f("y");
            k_0 k_06 = k_02.f("type");
            this.aG = k_04 == null ? 0 : k_04.getIntValue();
            this.aH = k_05 == null ? 0 : k_05.getIntValue();
            this.aI = k_06 == null ? xy_0.bYl : xy_0.valueOf(k_06.getStringValue().toUpperCase());
            this.aJ = k_03.getStringValue();
            this.aK = true;
        }
    }

    public void a(DS dS) {
        if (this.aK) {
            dS.a(this.aJ, this.aI, this.aG, this.aH);
        }
    }

    public void a(sf_1 sf_12) {
        if (!this.aK) {
            return;
        }
        sf_12.j(xy_0.class);
        String string = sf_12.yg();
        sf_12.a(new aza(null, "loadCursor", string, "\"" + this.aJ + "\"", xy_0.class.getSimpleName() + "." + this.aI.name(), String.valueOf(this.aG), String.valueOf(this.aH)));
    }

    public boolean isInitialized() {
        return this.aK;
    }
}

