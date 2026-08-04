/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Renamed from Dl
 */
public class dl_2
implements Iterator {
    private Object[] aNv;
    private int aNw;
    private boolean aNx;
    private int aNy = -1;

    public dl_2(Object[] objectArray, boolean bl2) {
        this.aNv = objectArray;
        this.aNw = objectArray.length;
        this.aNx = bl2;
        this.LD();
    }

    public boolean hasNext() {
        return this.aNy < this.aNw;
    }

    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("Array end reached. Array Size : " + this.aNw);
        }
        Object object = this.aNv[this.aNy];
        this.LD();
        return object;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void LD() {
        if (this.aNx) {
            ++this.aNy;
        } else {
            ++this.aNy;
            while (this.aNy < this.aNw && this.aNv[this.aNy] == null) {
                ++this.aNy;
            }
        }
    }
}

