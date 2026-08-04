/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aJb
 */
public class ajb_1
extends aNZ {
    private static Logger a = Logger.getLogger(ajb_1.class);
    public static final String TAG = "TabItem";
    private dl_1 dQX = null;
    private aht_1 dQY = null;
    private ur_1 dQZ = null;
    private String IJ = null;
    private Boolean dRa = true;
    private boolean aQv = true;
    private final ArrayList dRb = new ArrayList();
    public static final int caS = "text".hashCode();
    public static final int cMb = "enabled".hashCode();
    public static final int dyD = "visible".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof aht_1) {
            this.dQY = (aht_1)na_12;
            return;
        }
        if (na_12 instanceof ur_1) {
            if (this.dQX != null) {
                this.dQX.setPixmap((ur_1)na_12);
            }
            this.dQZ = (ur_1)na_12;
        }
        super.a(na_12);
    }

    public void a(gx_1 gx_12) {
        if (!this.dRb.contains(gx_12)) {
            this.dRb.add(gx_12);
        }
    }

    public void b(gx_1 gx_12) {
        this.dRb.remove(gx_12);
    }

    public void ff(boolean bl2) {
        for (int j = this.dRb.size() - 1; j >= 0; --j) {
            ((gx_1)this.dRb.get(j)).aE(bl2);
        }
    }

    public String getTag() {
        return TAG;
    }

    public dl_1 getButton() {
        return this.dQX;
    }

    public void setButton(dl_1 dl_12) {
        this.dQX = dl_12;
        this.dQX.setEnabled(this.dRa);
        this.dQX.setVisible(this.aQv);
        this.ff(this.aQv);
        if (this.IJ != null) {
            this.dQX.setText(this.IJ);
        }
    }

    public aht_1 getData() {
        return this.dQY;
    }

    public void setData(aht_1 aht_12) {
        this.dQY = aht_12;
    }

    public String getText() {
        return this.IJ;
    }

    public void setText(String string) {
        this.IJ = string;
        if (this.dQX != null) {
            this.dQX.setText(this.IJ);
        }
    }

    private void setEnabled(boolean bl2) {
        this.dRa = bl2;
        if (this.dQX != null) {
            this.dQX.setEnabled(this.dRa);
        }
    }

    private void setVisible(boolean bl2) {
        this.aQv = bl2;
        if (this.dQX != null) {
            this.dQX.setVisible(this.aQv);
            this.ff(this.aQv);
        }
    }

    public void aVm() {
        if (this.dQX != null && this.dQZ != null) {
            this.dQX.setPixmap(this.dQZ);
        }
    }

    public void a(air_1 air_12) {
        ajb_1 ajb_12 = (ajb_1)air_12;
        super.a((air_1)ajb_12);
        ajb_12.IJ = this.IJ;
        ajb_12.dRa = this.dRa;
        ajb_12.aQv = this.aQv;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == caS) {
            this.setText(if_12.eM(string));
        } else if (n2 == cMb) {
            this.setEnabled(Gr.getBoolean(string));
        } else if (n2 == dyD) {
            this.setVisible(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == caS) {
            this.setText(String.valueOf(object));
        } else if (n2 == cMb) {
            this.setEnabled(Gr.getBoolean(object));
        } else if (n2 == dyD) {
            this.setVisible(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    public void j() {
        super.j();
        if (this.dQY != null) {
            this.dQY.release();
            this.dQY = null;
        }
        this.dRb.clear();
    }

    public void b() {
        super.b();
    }

    public boolean isVisible() {
        return this.aQv;
    }
}

