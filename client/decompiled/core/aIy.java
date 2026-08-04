/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.DocumentHandler;

class aIy
extends amq_2 {
    public aIy(zv_0 zv_02, DocumentHandler documentHandler) {
        super(zv_02, documentHandler);
    }

    public void characters(char[] cArray, int n2, int n3) {
        String string = new String(cArray, n2, n3);
        String string2 = zv_0.c(this.dXM).getDescription();
        if (string2 == null) {
            zv_0.c(this.dXM).setDescription(string);
        } else {
            zv_0.c(this.dXM).setDescription(string2 + string);
        }
    }
}

