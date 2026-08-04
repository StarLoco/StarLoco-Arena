/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from uR
 */
public class ur_1
extends on_1
implements cn_1,
ie_1 {
    private static Logger a;
    public static final String TAG = "pixmap";
    private akq_1 arn;
    private String m_name;
    private ajn_1 aro = ajn_1.dSu;
    private boolean arp = false;
    private static final acl_0 uG;
    public static final int arq;
    public static final int arr;
    public static final int ars;
    public static final int art;
    public static final int aru;
    public static final int arv;
    public static final int arw;
    public static final int arx;
    public static final int ary;
    public static final int arz;

    public static ur_1 checkOut() {
        ur_1 ur_12;
        try {
            ur_12 = (ur_1)uG.adr();
            ur_12.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            ur_12 = new ur_1();
            ur_12.b();
        }
        return ur_12;
    }

    public String getTag() {
        return TAG;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void setX(int n2) {
        if (this.arn != null) {
            this.arn.setX(n2);
            this.setNeedsToPreProcess();
        }
    }

    public int getX() {
        if (this.arn != null) {
            return this.arn.getX();
        }
        return 0;
    }

    public void setY(int n2) {
        if (this.arn != null) {
            this.arn.setY(n2);
            this.setNeedsToPreProcess();
        }
    }

    public int getY() {
        if (this.arn != null) {
            return this.arn.getY();
        }
        return 0;
    }

    public void setWidth(int n2) {
        if (this.arn == null) {
            return;
        }
        this.arn.setWidth(n2);
        this.arp = true;
        this.setNeedsToPreProcess();
        this.arn.dF(n2 == -1);
    }

    public int getWidth() {
        if (this.arn != null) {
            return this.arn.getWidth();
        }
        return 0;
    }

    public void setHeight(int n2) {
        if (this.arn != null) {
            this.arn.setHeight(n2);
            this.arp = true;
            this.setNeedsToPreProcess();
            this.arn.dF(n2 == -1);
        }
    }

    public int getHeight() {
        if (this.arn != null) {
            return this.arn.getHeight();
        }
        return 0;
    }

    public int getOrientedHeight() {
        if (this.getRotation().Ep()) {
            return this.getWidth();
        }
        return this.getHeight();
    }

    public int getOrientedWidth() {
        if (this.getRotation().Ep()) {
            return this.getHeight();
        }
        return this.getWidth();
    }

    public void setFlipHorizontaly(boolean bl2) {
        if (this.arn != null && this.arn.Gl() != bl2) {
            this.arn.setFlipHorizontaly(bl2);
            this.setNeedsToPreProcess();
        }
    }

    public void setFlipVerticaly(boolean bl2) {
        if (this.arn != null && this.arn.Gm() != bl2) {
            this.arn.setFlipVerticaly(bl2);
            this.setNeedsToPreProcess();
        }
    }

    public void setRotation(xd_1 xd_12) {
        if (this.arn != null && this.arn.getRotation() != xd_12) {
            this.arn.setRotation(xd_12);
            this.setNeedsToPreProcess();
        }
    }

    public xd_1 getRotation() {
        if (this.arn != null) {
            return this.arn.getRotation();
        }
        return null;
    }

    public void setTexture(ef_1 ef_12) {
        ef_1 ef_13;
        if (this.arn == null) {
            return;
        }
        if (this.arn.azO() && ef_12 != (ef_13 = this.arn.jI()) && (ef_12 == null || ef_13 == null || !ef_12.lC(0).a(ef_13.lC(0)))) {
            this.arp = true;
        }
        this.arn.setTexture(ef_12);
        this.setNeedsToPreProcess();
    }

    public akq_1 getPixmap() {
        return this.arn;
    }

    public void setPixmap(akq_1 akq_12) {
        this.arn = akq_12;
    }

    public void setPosition(ajn_1 ajn_12) {
        this.aro = ajn_12;
    }

    public ajn_1 getPosition() {
        return this.aro;
    }

    public void setup(and_0 and_02) {
        if (and_02 instanceof oc_0) {
            ((oc_0)and_02).setPixmap(this);
        }
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.arn != null && this.arn.azP()) {
            this.arn.azR();
        }
        if (this.arp) {
            oc_0 oc_02 = (oc_0)this.getParentOfType(oc_0.class);
            if (oc_02 instanceof azc_0) {
                oc_02.setPixmap(this);
            }
            this.arp = false;
        }
        return bl2;
    }

    public void j() {
        super.j();
        this.aro = null;
        if (this.arn != null) {
            this.arn.setTexture(null);
        }
        this.arn = null;
        this.m_name = null;
    }

    public void b() {
        assert (this.arn == null);
        super.b();
        this.arn = new akq_1();
        this.aro = ajn_1.dSu;
        this.arp = false;
    }

    public void a(air_1 air_12) {
        ur_1 ur_12 = (ur_1)air_12;
        super.a((air_1)ur_12);
        ur_12.aro = this.aro;
        ur_12.setTexture(this.arn.jI());
        if (!this.arn.azO()) {
            ur_12.setHeight(this.arn.azM());
            ur_12.setWidth(this.arn.azN());
        }
        ur_12.setX(this.arn.getX());
        ur_12.setY(this.arn.getY());
        ur_12.setFlipHorizontaly(this.arn.Gl());
        ur_12.setFlipVerticaly(this.arn.Gm());
        ur_12.setRotation(this.arn.getRotation());
        ur_12.setName(this.m_name);
    }

    public void a(akq_1 akq_12) {
        this.arp = true;
        this.setNeedsToPreProcess();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == arv) {
            this.setTexture(if_12.eO(string));
        } else if (n2 == arq) {
            this.setHeight(Gr.R(string));
        } else if (n2 == arr) {
            this.setWidth(Gr.R(string));
        } else if (n2 == ars) {
            this.setX(Gr.R(string));
        } else if (n2 == art) {
            this.setY(Gr.R(string));
        } else if (n2 == arw) {
            this.setPosition(ajn_1.lz(string));
        } else if (n2 == arx) {
            this.setRotation(xd_1.cS(string));
        } else if (n2 == ary) {
            this.setFlipHorizontaly(Gr.getBoolean(string));
        } else if (n2 == arz) {
            this.setFlipVerticaly(Gr.getBoolean(string));
        } else if (n2 == aru) {
            this.setName(string);
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == arv) {
            this.setTexture((ef_1)object);
        } else if (n2 == arq) {
            this.setHeight(Gr.R(object));
        } else if (n2 == arr) {
            this.setWidth(Gr.R(object));
        } else if (n2 == ars) {
            this.setX(Gr.R(object));
        } else if (n2 == art) {
            this.setY(Gr.R(object));
        } else if (n2 == arw) {
            this.setPosition((ajn_1)((Object)object));
        } else if (n2 == arx) {
            this.setRotation((xd_1)((Object)object));
        } else if (n2 == ary) {
            this.setFlipHorizontaly(Gr.getBoolean(object));
        } else if (n2 == arz) {
            this.setFlipVerticaly(Gr.getBoolean(object));
        } else if (n2 == aru) {
            this.setName((String)object);
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static {
        ym_0 ym_02;
        a = Logger.getLogger(ur_1.class);
        try {
            ym_02 = new ym_0(new aoy_0(), 2000);
        }
        catch (Exception exception) {
            ym_02 = new ym_0(new aOB());
        }
        uG = ym_02;
        arq = "height".hashCode();
        arr = "width".hashCode();
        ars = "x".hashCode();
        art = "y".hashCode();
        aru = "name".hashCode();
        arv = "texture".hashCode();
        arw = "position".hashCode();
        arx = "rotation".hashCode();
        ary = "flipHorizontaly".hashCode();
        arz = "flipVerticaly".hashCode();
    }
}

