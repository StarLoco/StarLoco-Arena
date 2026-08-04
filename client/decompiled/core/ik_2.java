/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;
import java.util.Arrays;

/*
 * Renamed from IK
 */
public class ik_2
implements Serializable {
    private static final long serialVersionUID = 6307784764626694851L;
    private un_1[] bhV;
    private final transient Throwable bhW;
    private transient uv_1 bhX;
    private boolean bhY = false;

    public ik_2(Throwable throwable) {
        this.bhW = throwable;
        this.bhV = amE.g(throwable);
    }

    public Throwable getThrowable() {
        return this.bhW;
    }

    public uv_1 UJ() {
        if (this.bhW != null && this.bhX == null) {
            this.bhX = new uv_1();
        }
        return this.bhX;
    }

    public void UK() {
        if (this.bhY) {
            return;
        }
        uv_1 uv_12 = this.UJ();
        if (uv_12 != null) {
            this.bhY = true;
            uv_12.a(this.bhV);
        }
    }

    public un_1[] UL() {
        return this.bhV;
    }

    public int hashCode() {
        int n2 = 31;
        int n3 = 1;
        n3 = 31 * n3 + Arrays.hashCode(this.bhV);
        return n3;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        ik_2 ik_22 = (ik_2)object;
        return Arrays.equals(this.bhV, ik_22.bhV);
    }

    public void UM() {
        StringBuilder stringBuilder = new StringBuilder();
        for (un_1 un_12 : this.UL()) {
            String string = un_12.toString();
            stringBuilder.append(string);
            this.a(stringBuilder, un_12);
            stringBuilder.append(kJ.sy);
        }
        System.out.println(stringBuilder.toString());
    }

    protected void a(StringBuilder stringBuilder, un_1 un_12) {
        abl_0 abl_02;
        pj_1 pj_12 = un_12.AT();
        if (pj_12 != null && (abl_02 = pj_12.up()) != null) {
            if (!abl_02.aNl()) {
                stringBuilder.append(" ~[");
            } else {
                stringBuilder.append(" [");
            }
            stringBuilder.append(abl_02.aNk()).append(':').append(abl_02.getVersion()).append(']');
        }
    }
}

