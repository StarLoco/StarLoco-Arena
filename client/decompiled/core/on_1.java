/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from ON
 */
public abstract class on_1
extends bv_1
implements ve_2 {
    private static Logger a = Logger.getLogger(on_1.class);
    private boolean bCx;
    public static final int bCy = "decoratorSwitch".hashCode();

    public boolean isDecoratorSwitch() {
        return this.bCx;
    }

    public void setDecoratorSwitch(boolean bl2) {
        this.bCx = bl2;
    }

    public abstract void setup(and_0 var1);

    public void b() {
        super.b();
        this.bCx = false;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        on_1 on_12 = (on_1)air_12;
        on_12.setDecoratorSwitch(this.bCx);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != bCy) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setDecoratorSwitch(Gr.getBoolean(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != bCy) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setDecoratorSwitch(Gr.getBoolean(object));
        return true;
    }
}

