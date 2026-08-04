/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Iterator;

/*
 * Renamed from iV
 */
public class iv_1
extends avg
implements mx_2,
Cloneable,
Comparable {
    public static final long zb = -1L;
    public static final long zc = 0L;
    protected static final int zd = iv_1.g("Resource".getBytes());
    private static final int ze = iv_1.g("null name".getBytes());
    private String name = null;
    private Boolean zf = null;
    private Long zg = null;
    private Boolean zh = null;
    private Long zi = null;

    protected static int g(byte[] byArray) {
        return new BigInteger(byArray).intValue();
    }

    public iv_1() {
    }

    public iv_1(String string) {
        this(string, false, 0L, false);
    }

    public iv_1(String string, boolean bl2, long l2) {
        this(string, bl2, l2, false);
    }

    public iv_1(String string, boolean bl2, long l2, boolean bl3) {
        this(string, bl2, l2, bl3, -1L);
    }

    public iv_1(String string, boolean bl2, long l2, boolean bl3, long l3) {
        this.name = string;
        this.setName(string);
        this.M(bl2);
        this.setLastModified(l2);
        this.N(bl3);
        this.setSize(l3);
    }

    public String getName() {
        return this.aId() ? ((iv_1)this.aIg()).getName() : this.name;
    }

    public void setName(String string) {
        this.aIl();
        this.name = string;
    }

    public boolean lI() {
        if (this.aId()) {
            return ((iv_1)this.aIg()).lI();
        }
        return this.zf == null || this.zf != false;
    }

    public void M(boolean bl2) {
        this.aIl();
        this.zf = bl2 ? Boolean.TRUE : Boolean.FALSE;
    }

    public long getLastModified() {
        if (this.aId()) {
            return ((iv_1)this.aIg()).getLastModified();
        }
        if (!this.lI() || this.zg == null) {
            return 0L;
        }
        long l2 = this.zg;
        return l2 < 0L ? 0L : l2;
    }

    public void setLastModified(long l2) {
        this.aIl();
        this.zg = new Long(l2);
    }

    public boolean isDirectory() {
        if (this.aId()) {
            return ((iv_1)this.aIg()).isDirectory();
        }
        return this.zh != null && this.zh != false;
    }

    public void N(boolean bl2) {
        this.aIl();
        this.zh = bl2 ? Boolean.TRUE : Boolean.FALSE;
    }

    public void setSize(long l2) {
        this.aIl();
        this.zi = new Long(l2 > -1L ? l2 : -1L);
    }

    public long getSize() {
        if (this.aId()) {
            return ((iv_1)this.aIg()).getSize();
        }
        return this.lI() ? (this.zi != null ? this.zi : -1L) : 0L;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new UnsupportedOperationException("CloneNotSupportedException for a Resource caught. Derived classes must support cloning.");
        }
    }

    public int compareTo(Object object) {
        if (this.aId()) {
            return ((Comparable)this.aIg()).compareTo(object);
        }
        if (!(object instanceof iv_1)) {
            throw new IllegalArgumentException("Can only be compared with Resources");
        }
        return this.toString().compareTo(object.toString());
    }

    public boolean equals(Object object) {
        if (this.aId()) {
            return this.aIg().equals(object);
        }
        return object.getClass().equals(this.getClass()) && this.compareTo(object) == 0;
    }

    public int hashCode() {
        if (this.aId()) {
            return this.aIg().hashCode();
        }
        String string = this.getName();
        return zd * (string == null ? ze : string.hashCode());
    }

    public InputStream getInputStream() {
        if (this.aId()) {
            return ((iv_1)this.aIg()).getInputStream();
        }
        throw new UnsupportedOperationException();
    }

    public OutputStream getOutputStream() {
        if (this.aId()) {
            return ((iv_1)this.aIg()).getOutputStream();
        }
        throw new UnsupportedOperationException();
    }

    public Iterator iterator() {
        return this.aId() ? ((iv_1)this.aIg()).iterator() : new fg_1(this);
    }

    public int size() {
        return this.aId() ? ((iv_1)this.aIg()).size() : 1;
    }

    public boolean dE() {
        return this.aId() && ((iv_1)this.aIg()).dE();
    }

    public String toString() {
        if (this.aId()) {
            return this.aIg().toString();
        }
        String string = this.getName();
        return string == null ? "(anonymous)" : string;
    }

    public final String lJ() {
        return this.aId() ? ((iv_1)this.aIg()).lJ() : this.aIe() + " \"" + this.toString() + '\"';
    }

    public void a(awq_0 awq_02) {
        if (this.name != null || this.zf != null || this.zg != null || this.zh != null || this.zi != null) {
            throw this.aIh();
        }
        super.a(awq_02);
    }
}

