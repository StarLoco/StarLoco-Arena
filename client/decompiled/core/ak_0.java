/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from AK
 */
public class ak_0
extends aht_1
implements oc_0 {
    public static final String TAG = "RepeatableImage";
    private ArrayList aHS = new ArrayList();
    private ur_1 aHT = null;
    private vP AC = null;
    private int aHU = 0;
    private boolean aHV = false;
    private boolean aHW = false;
    public static final int ej = "horizontal".hashCode();
    public static final int aHX = "modulationColor".hashCode();
    public static final int aHY = "repeatNumber".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.setPixmap((ur_1)na_12);
        }
        super.a(na_12);
    }

    public void a(amx_1 amx_12) {
        if (amx_12 instanceof aiD && (this.dMe != null || !(amx_12 instanceof ei_1))) {
            amx_12.release();
            return;
        }
        super.a(amx_12);
    }

    public String getTag() {
        return TAG;
    }

    public void setHorizontal(boolean bl2) {
        if (this.dMe instanceof ei_1) {
            ((ei_1)this.dMe).setHorizontal(bl2);
        }
    }

    public boolean getHorizontal() {
        if (this.dMe instanceof ei_1) {
            return ((ei_1)this.dMe).isHorizontal();
        }
        return false;
    }

    public void setRepeatNumber(int n2) {
        if (n2 != this.aHU) {
            this.aHU = n2;
            this.aHV = true;
            this.setNeedsToPreProcess();
        }
    }

    public int getRepeatNumber() {
        return this.aHU;
    }

    public void setPixmap(ur_1 ur_12) {
        if (ur_12 != this.aHT) {
            this.aHT = ur_12;
            this.aHW = true;
            this.setNeedsToPreProcess();
        }
    }

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
        for (int j = this.aHS.size() - 1; j >= 0; --j) {
            ((azc_0)this.aHS.get(j)).setModulationColor(vP2);
        }
    }

    public vP getModulationColor() {
        return this.AC;
    }

    public void a(air_1 air_12) {
        ak_0 ak_02 = (ak_0)air_12;
        super.a((air_1)ak_02);
        while (ak_02.dMc.size() > 0) {
            ((adg_2)ak_02.dMc.get(0)).aab();
        }
        ak_02.setHorizontal(this.getHorizontal());
        ak_02.setRepeatNumber(this.aHU);
        ak_02.setModulationColor(this.AC);
    }

    public void Hs() {
        if (this.aHV) {
            while (this.aHU < this.aHS.size()) {
                ((azc_0)this.aHS.remove(this.aHU)).aab();
            }
            if (this.aHU > this.aHS.size()) {
                azc_0 azc_02;
                if (this.aHS.size() == 0) {
                    azc_02 = new azc_0();
                    azc_02.b();
                    azc_02.setNonBlocking(true);
                    azc_02.setModulationColor(this.AC);
                    this.a(azc_02);
                    azc_02.a(this.aHT.aah());
                    this.aHS.add(azc_02);
                }
                while (this.aHU > this.aHS.size()) {
                    azc_02 = (azc_0)((azc_0)this.aHS.get(0)).aah();
                    this.a(azc_02);
                    this.aHS.add(azc_02);
                }
            }
            this.aHV = false;
        }
        if (this.aHW) {
            for (int j = this.aHS.size() - 1; j >= 0; --j) {
                ((azc_0)this.aHS.get(j)).a(this.aHT.aah());
            }
            this.aHW = false;
        }
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.aHW || this.aHV) {
            this.Hs();
            this.Am();
        }
        return bl2;
    }

    public void j() {
        super.j();
        this.aHS.clear();
        this.aHU = 0;
        this.aHT = null;
        this.AC = null;
        this.aHV = false;
        this.aHW = false;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(string));
        } else if (n2 == aHX) {
            this.setModulationColor(if_12.eK(string));
        } else if (n2 == aHY) {
            this.setRepeatNumber(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == ej) {
            this.setHorizontal(Gr.getBoolean(object));
        } else if (n2 == aHX) {
            this.setModulationColor((vP)object);
        } else if (n2 == aHY) {
            this.setRepeatNumber(Gr.R(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

