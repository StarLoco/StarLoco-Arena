/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from aB
 */
public class ab_0 {
    Object[] cw;
    int cx;
    int cy;
    int cz;
    int cA;

    public ab_0(int n2) {
        if (n2 < 1) {
            throw new IllegalArgumentException("The maxSize argument (" + n2 + ") is not a positive integer.");
        }
        this.init(n2);
    }

    private void init(int n2) {
        this.cA = n2;
        this.cw = new Object[n2];
        this.cx = 0;
        this.cy = 0;
        this.cz = 0;
    }

    public void clear() {
        this.init(this.cA);
    }

    public void add(Object object) {
        this.cw[this.cy] = object;
        if (++this.cy == this.cA) {
            this.cy = 0;
        }
        if (this.cz < this.cA) {
            ++this.cz;
        } else if (++this.cx == this.cA) {
            this.cx = 0;
        }
    }

    public Object get(int n2) {
        if (n2 < 0 || n2 >= this.cz) {
            return null;
        }
        return this.cw[(this.cx + n2) % this.cA];
    }

    public int getMaxSize() {
        return this.cA;
    }

    public Object get() {
        Object object = null;
        if (this.cz > 0) {
            --this.cz;
            object = this.cw[this.cx];
            this.cw[this.cx] = null;
            if (++this.cx == this.cA) {
                this.cx = 0;
            }
        }
        return object;
    }

    public List asList() {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int j = 0; j < this.length(); ++j) {
            arrayList.add(this.get(j));
        }
        return arrayList;
    }

    public int length() {
        return this.cz;
    }

    public void resize(int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("Negative array size [" + n2 + "] not allowed.");
        }
        if (n2 == this.cz) {
            return;
        }
        Object[] objectArray = new Object[n2];
        int n3 = n2 < this.cz ? n2 : this.cz;
        for (int j = 0; j < n3; ++j) {
            objectArray[j] = this.cw[this.cx];
            this.cw[this.cx] = null;
            if (++this.cx != this.cz) continue;
            this.cx = 0;
        }
        this.cw = objectArray;
        this.cx = 0;
        this.cz = n3;
        this.cA = n2;
        this.cy = n3 == n2 ? 0 : n3;
    }
}

