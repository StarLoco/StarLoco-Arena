/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Dimension;
import java.awt.Insets;
import org.apache.log4j.Logger;

/*
 * Renamed from aEI
 */
public abstract class aei_2
extends xL {
    private static Logger a = Logger.getLogger(aei_2.class);
    protected Insets dii = new Insets(0, 0, 0, 0);

    public Insets getInsets() {
        return this.dii;
    }

    public void setInsets(Insets insets) {
        this.dii.top = insets.top;
        this.dii.bottom = insets.bottom;
        this.dii.left = insets.left;
        this.dii.right = insets.right;
    }

    public abstract void a(Dimension var1, Insets var2, Insets var3, Insets var4);
}

