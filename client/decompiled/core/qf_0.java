/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Renamed from Qf
 */
public class qf_0
implements Iterator {
    private File bFR;
    private String[] bFS;
    private int pos = 0;

    public qf_0() {
    }

    public qf_0(File file) {
        this.bFR = file;
    }

    public qf_0(File file, String[] stringArray) {
        this(file);
        this.n(stringArray);
    }

    public void n(String[] stringArray) {
        int n2 = this.bFS == null ? 0 : this.bFS.length;
        String[] stringArray2 = new String[n2 + stringArray.length];
        if (n2 > 0) {
            System.arraycopy(this.bFS, 0, stringArray2, 0, n2);
        }
        this.bFS = stringArray2;
        System.arraycopy(stringArray, 0, this.bFS, n2, stringArray.length);
    }

    public boolean hasNext() {
        return this.pos < this.bFS.length;
    }

    public Object next() {
        return this.acU();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public ash_0 acU() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return new ash_0(this.bFR, this.bFS[this.pos++]);
    }
}

