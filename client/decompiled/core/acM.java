/*
 * Decompiled with CFR 0.152.
 */
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.AbstractSequentialList;
import java.util.ListIterator;

public class acM
extends AbstractSequentialList
implements Externalizable {
    static final long serialVersionUID = 1L;
    protected Wv ckN;
    protected Wv ckO;
    protected int _size = 0;

    public ListIterator listIterator(int n2) {
        return new atJ(this, n2);
    }

    public int size() {
        return this._size;
    }

    public void a(int n2, Wv wv) {
        if (n2 < 0 || n2 > this.size()) {
            throw new IndexOutOfBoundsException("index:" + n2);
        }
        this.b(n2, wv);
    }

    public boolean c(Wv wv) {
        this.b(this._size, wv);
        return true;
    }

    public void d(Wv wv) {
        this.b(0, wv);
    }

    public void e(Wv wv) {
        this.b(this.size(), wv);
    }

    public void clear() {
        if (null != this.ckN) {
            for (Wv wv = this.ckN.uw(); wv != null; wv = wv.uw()) {
                Wv wv2 = wv.ux();
                wv2.a(null);
                wv.b(null);
            }
            this.ckO = null;
            this.ckN = null;
        }
        this._size = 0;
    }

    public Object[] toArray() {
        Object[] objectArray = new Object[this._size];
        int n2 = 0;
        for (Wv wv = this.ckN; wv != null; wv = wv.uw()) {
            objectArray[n2++] = wv;
        }
        return objectArray;
    }

    public Object[] arx() {
        Object[] objectArray = new Object[this._size];
        int n2 = 0;
        Wv wv = this.ckN;
        Wv wv2 = null;
        while (wv != null) {
            objectArray[n2] = wv;
            wv2 = wv;
            wv = wv.uw();
            wv2.a(null);
            wv2.b(null);
            ++n2;
        }
        this._size = 0;
        this.ckO = null;
        this.ckN = null;
        return objectArray;
    }

    public boolean contains(Object object) {
        for (Wv wv = this.ckN; wv != null; wv = wv.uw()) {
            if (!object.equals(wv)) continue;
            return true;
        }
        return false;
    }

    public Wv jK(int n2) {
        if (n2 < 0 || n2 >= this._size) {
            throw new IndexOutOfBoundsException("Index: " + n2 + ", Size: " + this._size);
        }
        if (n2 > this._size >> 1) {
            Wv wv = this.ckO;
            for (int j = this._size - 1; j > n2; --j) {
                wv = wv.ux();
            }
            return wv;
        }
        Wv wv = this.ckN;
        for (int j = 0; j < n2; ++j) {
            wv = wv.uw();
        }
        return wv;
    }

    public Wv ary() {
        return this.ckN;
    }

    public Wv arz() {
        return this.ckO;
    }

    public Wv f(Wv wv) {
        return wv.uw();
    }

    public Wv g(Wv wv) {
        return wv.ux();
    }

    public Wv arA() {
        Wv wv = this.ckN;
        Wv wv2 = wv.uw();
        wv.a(null);
        if (null != wv2) {
            wv2.b(null);
        }
        this.ckN = wv2;
        if (--this._size == 0) {
            this.ckO = null;
        }
        return wv;
    }

    public Wv arB() {
        Wv wv = this.ckO;
        Wv wv2 = wv.ux();
        wv.b(null);
        if (null != wv2) {
            wv2.a(null);
        }
        this.ckO = wv2;
        if (--this._size == 0) {
            this.ckN = null;
        }
        return wv;
    }

    protected void b(int n2, Wv wv) {
        Wv wv2 = wv;
        if (this._size == 0) {
            this.ckN = this.ckO = wv2;
        } else if (n2 == 0) {
            wv2.a(this.ckN);
            this.ckN.b(wv2);
            this.ckN = wv2;
        } else if (n2 == this._size) {
            this.ckO.a(wv2);
            wv2.b(this.ckO);
            this.ckO = wv2;
        } else {
            Wv wv3 = this.jK(n2);
            Wv wv4 = wv3.ux();
            if (wv4 != null) {
                wv4.a(wv);
            }
            wv.b(wv4);
            wv.a(wv3);
            wv3.b(wv);
        }
        ++this._size;
    }

    public boolean remove(Object object) {
        if (object instanceof Wv) {
            Wv wv = (Wv)object;
            Wv wv2 = wv.ux();
            Wv wv3 = wv.uw();
            if (wv3 == null && wv2 == null) {
                this.ckO = null;
                this.ckN = null;
            } else if (wv3 == null) {
                wv.b(null);
                wv2.a(null);
                this.ckO = wv2;
            } else if (wv2 == null) {
                wv.a(null);
                wv3.b(null);
                this.ckN = wv3;
            } else {
                wv2.a(wv3);
                wv3.b(wv2);
                wv.a(null);
                wv.b(null);
            }
            --this._size;
            return true;
        }
        return false;
    }

    public void a(Wv wv, Wv wv2) {
        if (wv == this.ckN) {
            this.d(wv2);
        } else if (wv == null) {
            this.e(wv2);
        } else {
            Wv wv3 = wv.ux();
            wv2.a(wv);
            wv3.a(wv2);
            wv2.b(wv3);
            wv.b(wv2);
            ++this._size;
        }
    }

    public void b(Wv wv, Wv wv2) {
        if (wv == this.ckO) {
            this.e(wv2);
        } else if (wv == null) {
            this.d(wv2);
        } else {
            Wv wv3 = wv.uw();
            wv2.b(wv);
            wv2.a(wv3);
            wv.a(wv2);
            wv3.b(wv);
            ++this._size;
        }
    }

    public boolean a(apx apx2) {
        for (Wv wv = this.ckN; wv != null; wv = wv.uw()) {
            boolean bl2 = apx2.a(wv);
            if (bl2) continue;
            return false;
        }
        return true;
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this._size);
        objectOutput.writeObject(this.ckN);
        objectOutput.writeObject(this.ckN);
    }

    public void readExternal(ObjectInput objectInput) {
        objectInput.readByte();
        this._size = objectInput.readInt();
        this.ckN = (Wv)objectInput.readObject();
        this.ckO = (Wv)objectInput.readObject();
    }
}

