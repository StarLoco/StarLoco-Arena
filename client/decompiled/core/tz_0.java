/*
 * Decompiled with CFR 0.152.
 */
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

/*
 * Renamed from TZ
 */
public class tz_0
implements Serializable {
    private static final long serialVersionUID = 3075964498087694229L;
    private static final int bOY = -1;
    private static final String bOZ = "NULL_ARGUMENT_ARRAY_ELEMENT";
    private static long startTime = System.currentTimeMillis();
    transient String bPa;
    private String bPb;
    private transient rl_2 bPc;
    private String message;
    private transient String bPd;
    private transient Object[] bPe;
    private ik_2 bPf;
    private qw_0[] bPg;
    private hK bPh;
    private axe bPi;
    private Map bPj;
    private long timeStamp;

    public tz_0() {
    }

    public tz_0(String string, arN arN2, rl_2 rl_22, String string2, Throwable throwable, Object[] objectArray) {
        this.bPa = string;
        this.bPh = arN2.agu();
        this.bPc = rl_22;
        this.message = string2;
        if (throwable != null) {
            this.bPf = new ik_2(throwable);
        }
        this.bPe = objectArray;
        this.timeStamp = System.currentTimeMillis();
        iv_0 iv_02 = (iv_0)afu_2.auZ();
        this.bPj = iv_02.Lm();
    }

    public void e(Object[] objectArray) {
        if (this.bPe != null) {
            throw new IllegalStateException("argArray has been already set");
        }
        this.bPe = objectArray;
    }

    public Object[] agq() {
        return this.bPe;
    }

    public rl_2 agr() {
        return this.bPc;
    }

    public String getThreadName() {
        if (this.bPb == null) {
            this.bPb = Thread.currentThread().getName();
        }
        return this.bPb;
    }

    public void fV(String string) {
        if (this.bPb != null) {
            throw new IllegalStateException("threadName has been already set");
        }
        this.bPb = string;
    }

    public ik_2 ags() {
        return this.bPf;
    }

    public void a(ik_2 ik_22) {
        if (this.bPf != null) {
            throw new IllegalStateException("ThrowableProxy has been already set.");
        }
        this.bPf = ik_22;
    }

    public void agt() {
        this.getThreadName();
    }

    public hK agu() {
        return this.bPh;
    }

    public void a(hK hK2) {
        this.bPh = hK2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String string) {
        if (this.message != null) {
            throw new IllegalStateException("The message for this event has been set already.");
        }
        this.message = string;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long l2) {
        this.timeStamp = l2;
    }

    public void b(rl_2 rl_22) {
        if (this.bPc != null) {
            throw new IllegalStateException("The level has been already set for this event.");
        }
        this.bPc = rl_22;
    }

    public static final long getStartTime() {
        return startTime;
    }

    public qw_0[] agv() {
        if (this.bPg == null && this.bPa != null) {
            this.bPg = qw_0.a(new Throwable(), this.bPa);
        }
        return this.bPg;
    }

    public void a(qw_0[] qw_0Array) {
        this.bPg = qw_0Array;
    }

    public axe agw() {
        return this.bPi;
    }

    public void f(axe axe2) {
        if (this.bPi != null) {
            throw new IllegalStateException("The marker has been already set for this event.");
        }
        this.bPi = axe2;
    }

    public String agx() {
        if (this.bPd != null) {
            return this.bPd;
        }
        this.bPd = this.bPe != null ? aqc.d(this.message, this.bPe) : this.message;
        return this.bPd;
    }

    public Map agy() {
        return this.bPj;
    }

    private void a(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.bPc.agf);
        if (this.bPe != null) {
            int n2 = this.bPe.length;
            objectOutputStream.writeInt(n2);
            for (int j = 0; j < this.bPe.length; ++j) {
                if (this.bPe[j] != null) {
                    objectOutputStream.writeObject(this.bPe[j].toString());
                    continue;
                }
                objectOutputStream.writeObject(bOZ);
            }
        } else {
            objectOutputStream.writeInt(-1);
        }
    }

    private void a(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        int n2 = objectInputStream.readInt();
        this.bPc = rl_2.cY(n2);
        int n3 = objectInputStream.readInt();
        if (n3 != -1) {
            this.bPe = new String[n3];
            for (int j = 0; j < n3; ++j) {
                Object object = objectInputStream.readObject();
                if (bOZ.equals(object)) continue;
                this.bPe[j] = object;
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        stringBuilder.append(this.bPc).append("] ");
        stringBuilder.append(this.agx());
        return stringBuilder.toString();
    }
}

