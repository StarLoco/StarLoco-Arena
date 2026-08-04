/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

public class ND
extends avg
implements mx_2 {
    private Vector bAc = new Vector();
    private File bAd;

    public ND() {
    }

    protected ND(ND nD) {
        this.bAd = nD.bAd;
        this.bAc = nD.bAc;
        this.l(nD.TP());
    }

    public void a(awq_0 awq_02) {
        if (this.bAd != null || this.bAc.size() != 0) {
            throw this.aIh();
        }
        super.a(awq_02);
    }

    public void x(File file) {
        this.aIl();
        this.bAd = file;
    }

    public File o(UI uI) {
        if (this.aId()) {
            return this.q(uI).o(uI);
        }
        return this.bAd;
    }

    public void fw(String string) {
        this.aIl();
        if (string != null && string.length() > 0) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, ", \t\n\r\f", false);
            while (stringTokenizer.hasMoreTokens()) {
                this.bAc.addElement(stringTokenizer.nextToken());
            }
        }
    }

    public String[] p(UI uI) {
        if (this.aId()) {
            return this.q(uI).p(uI);
        }
        if (this.bAd == null) {
            throw new eq_2("No directory specified for filelist.");
        }
        if (this.bAc.size() == 0) {
            throw new eq_2("No files specified for filelist.");
        }
        Object[] objectArray = new String[this.bAc.size()];
        this.bAc.copyInto(objectArray);
        return objectArray;
    }

    protected ND q(UI uI) {
        return (ND)this.O(uI);
    }

    public void a(gu_2 gu_22) {
        if (gu_22.getName() == null) {
            throw new eq_2("No name specified in nested file element");
        }
        this.bAc.addElement(gu_22.getName());
    }

    public Iterator iterator() {
        if (this.aId()) {
            return this.q(this.TP()).iterator();
        }
        return new qf_0(this.bAd, this.bAc.toArray(new String[this.bAc.size()]));
    }

    public int size() {
        if (this.aId()) {
            return this.q(this.TP()).size();
        }
        return this.bAc.size();
    }

    public boolean dE() {
        return true;
    }
}

