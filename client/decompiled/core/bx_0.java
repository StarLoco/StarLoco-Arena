/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import org.apache.log4j.Logger;

/*
 * Renamed from Bx
 */
public class bx_0
extends aGJ {
    private static final Logger a = Logger.getLogger(bx_0.class);
    private int aIS;
    private int aIT;
    private int aIU;
    private int aIV;
    private static final acl_0 uG = new ym_0(new aGQ());

    public static bx_0 a(abd_1 abd_12, na_1 na_12, qe_1 qe_12, aaj aaj2, EntitySprite entitySprite) {
        bx_0 bx_02;
        try {
            bx_02 = (bx_0)uG.adr();
            bx_02.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            bx_02 = new bx_0();
            bx_02.b();
        }
        bx_02.ng(abd_12.bTl);
        bx_02.nh(abd_12.bTm);
        bx_02.setModifiers(abd_12.jH);
        bx_02.ai(abd_12.oI);
        bx_02.aj(abd_12.oJ);
        bx_02.X(abd_12.oH());
        bx_02.e(na_12);
        bx_02.a(qe_12);
        bx_02.setItemValue(aaj2);
        bx_02.eH((int)entitySprite.HC());
        bx_02.eI((int)entitySprite.HD());
        bx_02.eJ(entitySprite.getWidth());
        bx_02.eK(entitySprite.getHeight());
        return bx_02;
    }

    public int Il() {
        return this.aIS;
    }

    public void eH(int n2) {
        this.aIS = n2;
    }

    public int Im() {
        return this.aIT;
    }

    public void eI(int n2) {
        this.aIT = n2;
    }

    public int In() {
        return this.aIU;
    }

    public void eJ(int n2) {
        this.aIU = n2;
    }

    public int Io() {
        return this.aIV;
    }

    public void eK(int n2) {
        this.aIV = n2;
    }

    public aaj Ip() {
        return (aaj)this.getItemValue();
    }
}

