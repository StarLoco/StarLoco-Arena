/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.awt.Insets;
import org.apache.log4j.Logger;

/*
 * Renamed from Wx
 */
public class wx_0
extends pD {
    private static Logger a = Logger.getLogger(wx_0.class);
    public static final String TAG = "BubbleBorder";
    private aeu_0 bUg;
    private boolean bUh = true;
    private boolean bUi = false;
    private float bUj = -2.0943952f;
    private boolean bUk = false;
    public static final int bUl = "displaySpark".hashCode();
    public static final int bUm = "sparkAngle".hashCode();

    public String getTag() {
        return TAG;
    }

    public aeu_0 getMesh() {
        return this.bUg;
    }

    public void setInsets(Insets insets) {
    }

    public Entity getEntity() {
        return this.bUg.getEntity();
    }

    public void setDisplaySpark(boolean bl2) {
        this.bUh = bl2;
        this.bUi = true;
        this.bUg.setDisplaySpark(bl2);
    }

    public void setSparkAngle(float f) {
        this.bUj = f;
        this.bUk = true;
        if (this.bUh) {
            this.bUg.setSparkAngle(f);
        }
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        wx_0 wx_02 = (wx_0)air_12;
        if (this.bUi) {
            wx_02.setDisplaySpark(this.bUh);
        }
        if (this.bUk) {
            wx_02.setSparkAngle(this.bUj);
        }
    }

    public void j() {
        super.j();
        this.bUg.j();
        this.bUg = null;
    }

    public void b() {
        super.b();
        this.bUj = -2.0943952f;
        this.bUh = true;
        this.bUg = new aeu_0();
        this.bUg.b();
        super.setInsets(this.bUg.getInsets());
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == bUl) {
            this.setDisplaySpark(Gr.getBoolean(string));
        } else if (n2 == bUm) {
            this.setSparkAngle(Gr.getFloat(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == bUl) {
            this.setDisplaySpark(Gr.getBoolean(object));
        } else if (n2 == bUm) {
            this.setSparkAngle(Gr.getFloat(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

