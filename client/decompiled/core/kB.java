/*
 * Decompiled with CFR 0.152.
 */
public abstract class kB
implements Cloneable {
    protected transient int _size;
    protected transient int Ez;
    protected static final float EA = 0.5f;
    protected static final int EB = 10;
    protected float EC;
    protected int ED;
    protected int EE;
    protected float EF;
    private boolean EG = false;

    public kB() {
        this(10, 0.5f);
    }

    public kB(int n2) {
        this(n2, 0.5f);
    }

    public kB(int n2, float f) {
        this.EC = f;
        this.EF = f;
        this.N((int)Math.ceil((float)n2 / f));
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public boolean isEmpty() {
        return 0 == this._size;
    }

    public int size() {
        return this._size;
    }

    protected abstract int capacity();

    public void ensureCapacity(int n2) {
        if (n2 > this.ED - this.size()) {
            this.rehash(it_2.gh((int)Math.ceil((float)n2 + (float)this.size() / this.EC) + 1));
            this.bN(this.capacity());
        }
    }

    public void compact() {
        this.rehash(it_2.gh((int)Math.ceil((float)this.size() / this.EC) + 1));
        this.bN(this.capacity());
        if (this.EF != 0.0f) {
            this.bO(this.size());
        }
    }

    public void t(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("Factor must be >= 0: " + f);
        }
        this.EF = f;
    }

    public float pe() {
        return this.EF;
    }

    public final void trimToSize() {
        this.compact();
    }

    protected void O(int n2) {
        --this._size;
        if (this.EF != 0.0f) {
            --this.EE;
            if (!this.EG && this.EE <= 0) {
                this.compact();
            }
        }
    }

    public void clear() {
        this._size = 0;
        this.Ez = this.capacity();
    }

    protected int N(int n2) {
        int n3 = it_2.gh(n2);
        this.bN(n3);
        this.bO(n2);
        return n3;
    }

    protected abstract void rehash(int var1);

    protected void pf() {
        this.EG = true;
    }

    protected void Y(boolean bl2) {
        this.EG = false;
        if (bl2 && this.EE <= 0 && this.EF != 0.0f) {
            this.compact();
        }
    }

    private final void bN(int n2) {
        this.ED = Math.min(n2 - 1, (int)Math.floor((float)n2 * this.EC));
        this.Ez = n2 - this._size;
    }

    private void bO(int n2) {
        if (this.EF != 0.0f) {
            this.EE = Math.round((float)n2 * this.EF);
        }
    }

    protected final void Z(boolean bl2) {
        if (bl2) {
            --this.Ez;
        }
        if (++this._size > this.ED || this.Ez == 0) {
            int n2 = this._size > this.ED ? it_2.gh(this.capacity() << 1) : this.capacity();
            this.rehash(n2);
            this.bN(this.capacity());
        }
    }

    protected int pg() {
        return this.capacity() << 1;
    }
}

