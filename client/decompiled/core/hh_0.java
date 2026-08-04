/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.BufferUtil;
import java.nio.Buffer;

/*
 * Renamed from hH
 */
public class hh_0
implements rn_0 {
    public static final int wh = 1;
    public static final int wi = 2;
    public static final int wj = 3;
    public static final int wk = 4;
    public static final int wl = 5;
    private static final Object no = new Object();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object r(Object object) {
        Integer n2 = (Integer)object;
        Object object2 = no;
        synchronized (object2) {
            Buffer buffer = null;
            int n3 = n2 >> 24 & 0xFF;
            int n4 = n2 & 0xFFFFFF;
            switch (n3) {
                case 1: {
                    buffer = BufferUtil.newByteBuffer(n4);
                    break;
                }
                case 2: {
                    buffer = BufferUtil.newShortBuffer(n4);
                    break;
                }
                case 3: {
                    buffer = BufferUtil.newIntBuffer(n4);
                    break;
                }
                case 4: {
                    buffer = BufferUtil.newFloatBuffer(n4);
                    break;
                }
                case 5: {
                    buffer = BufferUtil.newDoubleBuffer(n4);
                }
            }
            return buffer;
        }
    }

    public void e(Object object, Object object2) {
    }

    public boolean f(Object object, Object object2) {
        return true;
    }

    public void g(Object object, Object object2) {
    }

    public void h(Object object, Object object2) {
    }
}

