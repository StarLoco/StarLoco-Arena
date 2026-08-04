/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

public interface amb {
    public static final int INFO = 0;
    public static final int cGb = 1;
    public static final int ERROR = 2;

    public int getLevel();

    public int aBf();

    public Object aBg();

    public String getMessage();

    public Throwable getThrowable();

    public Long aBh();

    public boolean hasChildren();

    public void c(amb var1);

    public boolean d(amb var1);

    public Iterator iterator();
}

