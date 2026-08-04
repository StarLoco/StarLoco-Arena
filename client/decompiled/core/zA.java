/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import org.apache.log4j.Logger;

public class zA
implements ge_0 {
    private static Logger a = Logger.getLogger(zA.class);
    private alp_2 aFx;
    private adg_2 aFy = null;
    private azc_0 aFz;
    private boolean aFA;
    private aaj aFB = null;
    private EntitySprite aFC = null;
    private int aFD;
    private int aFE;

    public zA(alp_2 alp_22) {
        this.aFx = alp_22;
    }

    public void a(int n2, int n3, adg_2 adg_22) {
        if (!(this.aFA || n2 >= this.aFD - 20 && n2 <= this.aFD + 20 && n3 >= this.aFE - 20 && n3 <= this.aFE + 20)) {
            this.aFz = new azc_0();
            this.aFz.b();
            this.aFz.setPixmap(new akq_1(this.aFC.jI()));
            this.aFz.setSize(this.aFC.getWidth(), this.aFC.getHeight());
            this.aFz.setNonBlocking(true);
            this.aFz.setLayoutData(null);
            this.aFx.W(this.aFB);
            ago_2.getInstance().getLayeredContainer().a(this.aFz, 30000);
            this.aFA = true;
        }
        if (this.aFA && this.aFz != null) {
            this.aFz.setPosition(n2 - this.aFz.getWidth() / 2, n3 - this.aFz.getHeight() / 2);
            if (adg_22 != this.aFy) {
                if (this.aFy != null) {
                    this.aFx.b(null, this.aFB);
                    this.aFy = null;
                }
                if (adg_22 != null) {
                    this.aFy = adg_22;
                    this.aFx.c(null, this.aFB);
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
            if (kn_12 != null && kn_12.isDropValid(this.aFx, this.aFB)) {
                kn_12.a(kn_12, this.aFB);
            } else {
                object = lt_0.a(ago_2.getInstance().getCurrentAWTMouseEvent(), (kn_1)this.aFx, (Object)this.aFB);
                this.aFx.f((ke)object);
            }
            if (this.aFz != null) {
                this.aFz.aab();
            }
        }
    }

    public void clean() {
        if (this.aFz != null) {
            this.aFz.aab();
            this.aFz = null;
        }
    }

    public boolean b(adg_2 adg_22, int n2, int n3) {
        if (adg_22 == null) {
            return false;
        }
        if (adg_22 == this.aFx || adg_22.l(this.aFx)) {
            aaj aaj2 = this.aFx.getOverItem();
            return this.aFx.isDragEnabled() && aaj2 != null && aaj2.aoO() && (!aaj2.isEditable() || !aaj2.aoP());
        }
        return false;
    }

    public void select(int n2, int n3) {
        this.aFD = n2;
        this.aFE = n3;
        this.aFB = this.aFx.getOverItem();
        this.aFC = this.aFx.getOverMesh();
        this.aFA = false;
        this.aFy = null;
    }
}

