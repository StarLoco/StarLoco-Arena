/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.event.MouseEvent;
import org.apache.log4j.Logger;

/*
 * Renamed from aBd
 */
public class abd_1
extends cq_2 {
    private static Logger a = Logger.getLogger(abd_1.class);
    private static final acl_0 uG = new ym_0(new aib_0());
    protected int oI;
    protected int oJ;
    protected int bTl;
    protected int bTm;
    protected int dqX;
    private static int dqY = 0;
    private static int dqZ = 0;

    public int p(adg_2 adg_22) {
        if (adg_22 != null) {
            return this.oI - adg_22.getDisplayX();
        }
        return 0;
    }

    public int getScreenX() {
        return this.oI;
    }

    public void ai(int n2) {
        this.oI = n2;
    }

    public int q(adg_2 adg_22) {
        if (adg_22 != null) {
            return this.oJ - adg_22.getDisplayY();
        }
        return 0;
    }

    public int getScreenY() {
        return this.oJ;
    }

    public void aj(int n2) {
        this.oJ = n2;
    }

    public int getButton() {
        return this.bTl;
    }

    public void ng(int n2) {
        this.bTl = n2;
    }

    public int getClickCount() {
        return this.bTm;
    }

    public void nh(int n2) {
        this.bTm = n2;
    }

    public int aNb() {
        return this.dqX;
    }

    public void ni(int n2) {
        this.dqX = n2;
    }

    public static abd_1 k(MouseEvent mouseEvent) {
        abd_1 abd_12 = abd_1.aNc();
        abd_12.bTl = mouseEvent.getButton();
        abd_12.jH = mouseEvent.getModifiersEx();
        abd_12.bTm = mouseEvent.getClickCount();
        return abd_12;
    }

    public static abd_1 aNc() {
        abd_1 abd_12;
        ++dqY;
        try {
            abd_12 = (abd_1)uG.adr();
            abd_12.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            abd_12 = new abd_1();
            abd_12.b();
        }
        return abd_12;
    }

    public static abd_1 f(abd_1 abd_12) {
        abd_1 abd_13 = abd_1.aNc();
        abd_13.ng(abd_12.bTl);
        abd_13.nh(abd_12.bTm);
        abd_13.setModifiers(abd_12.jH);
        abd_13.ai(abd_12.oI);
        abd_13.aj(abd_12.oJ);
        abd_13.e(abd_12.DK);
        return abd_13;
    }

    public void release() {
        super.release();
        ++dqZ;
    }

    public void j() {
        super.j();
    }
}

