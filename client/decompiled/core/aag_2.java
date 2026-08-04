/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Dimension;
import java.awt.Toolkit;

/*
 * Renamed from aAg
 */
public class aag_2 {
    private int cQx;
    private int oK;
    private int doW;
    private String doX;

    public aag_2(int n2, int n3, int n4, String string) {
        this.cQx = n2;
        this.oK = n3;
        this.doW = n4;
        this.doX = string;
    }

    public final int getScreenWidth() {
        return this.cQx;
    }

    public final void nc(int n2) {
        this.cQx = n2;
    }

    public final int getScreenHeight() {
        return this.oK;
    }

    public final void nd(int n2) {
        this.oK = n2;
    }

    public final int aMC() {
        return this.doW;
    }

    public final void ne(int n2) {
        this.doW = n2;
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.cQx).append("x").append(this.oK);
        if (this.doW > 0) {
            stringBuffer.append(" (").append(this.doW).append(")");
        }
        if (this.doX != null && !this.doX.equals("")) {
            stringBuffer.append(" (").append(this.doX).append(")");
        }
        return stringBuffer.toString();
    }

    public static aag_2 aMD() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int n2 = dimension.width;
        int n3 = dimension.height;
        int n4 = toolkit.getColorModel().getPixelSize();
        return new aag_2(n2, n3, n4, "courante");
    }
}

