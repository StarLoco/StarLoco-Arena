/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from CK
 */
public class ck_1
implements ge_0 {
    private static Logger a = Logger.getLogger(ck_1.class);
    private kn_1 aMK = null;
    private adg_2 aFy = null;
    private qa_1 aML;
    private kn_1 aMM;
    private boolean aFA;
    private Object dE = null;

    public ck_1(qa_1 qa_12) {
        this.aML = qa_12;
    }

    public void a(int n2, int n3, adg_2 adg_22) {
        qa_1 qa_12;
        if (!this.aFA && this.aMK.isDragEnabled() && (n2 < (qa_12 = this.aML).getDisplayX() || n2 > qa_12.getDisplayX() + qa_12.getWidth() || n3 < qa_12.getDisplayY() || n3 > qa_12.getDisplayY() + qa_12.getHeight())) {
            kn_1 kn_12 = this.aMK;
            if (kn_12 == null) {
                return;
            }
            this.aMM = (kn_1)kn_12.aah();
            this.aMM.setCopy(true);
            kn_1 kn_13 = this.aMK;
            this.aMM.setSize(kn_13.getSize());
            this.aMM.setNonBlocking(true);
            this.aMM.setLayoutData(null);
            this.aMK.W(this.dE);
            ago_2.getInstance().getLayeredContainer().a(this.aMM, 30000);
            this.aFA = true;
        }
        if (this.aFA && this.aMM != null) {
            this.aMM.setPosition(n2 - this.aMM.getWidth() / 2, n3 - this.aMM.getHeight() / 2);
            if (adg_22 != null && !(adg_22 instanceof qa_1)) {
                adg_22 = (adg_2)adg_22.getParentOfType(qa_1.class);
            }
            if (adg_22 != this.aFy) {
                if (this.aFy != null) {
                    this.aMK.b(((qa_1)this.aFy).getDragNDropable(), this.dE);
                    this.aFy = null;
                }
                if (adg_22 != null) {
                    this.aFy = adg_22;
                    this.aMK.c(((qa_1)this.aFy).getDragNDropable(), this.dE);
                }
            }
        }
    }

    public void b(int n2, int n3, adg_2 adg_22) {
        if (this.aFA) {
            Object object;
            kn_1 kn_12 = null;
            if (adg_22 != null && (kn_12 = adg_22 instanceof kn_1 ? (kn_1)adg_22 : (kn_1)adg_22.getParentOfType(alp_2.class)) == null) {
                if (!(adg_22 instanceof qa_1)) {
                    object = adg_22.getParentOfType(qa_1.class);
                    if (object instanceof qa_1) {
                        kn_12 = ((qa_1)object).getDragNDropable();
                    }
                } else {
                    kn_12 = ((qa_1)adg_22).getDragNDropable();
                }
            }
            if (kn_12 != null && kn_12.isDropValid(this.aMK, this.dE)) {
                kn_12.a(this.aMK, this.dE);
            } else if (kn_12 != this.aMK) {
                object = lt_0.a(ago_2.getInstance().getCurrentAWTMouseEvent(), this.aMK, this.dE);
                this.aML.f((ke)object);
            }
            if (this.aMM != null) {
                this.aMM.aab();
            }
        }
    }

    public void clean() {
        if (this.aMM != null) {
            if (this.aMM == this.aMK) {
                this.aMK = null;
            }
            this.aMM.aab();
            this.aMM = null;
        }
    }

    public boolean b(adg_2 adg_22, int n2, int n3) {
        if (adg_22 == null) {
            return false;
        }
        return this.aML.uO() && (adg_22 == this.aML || adg_22.l(this.aML)) && this.aML.getItem() != null;
    }

    public void select(int n2, int n3) {
        this.dE = this.aML.getItemValue();
        this.aMK = this.aML.getDragNDropable();
        this.aFA = false;
        this.aFy = null;
    }
}

