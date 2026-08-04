/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * Renamed from Zl
 */
public class zl_0
implements od_1 {
    private final List ccK = new ArrayList();
    private final ReadWriteLock ccL = new ReentrantReadWriteLock();
    private final Lock ccM = this.ccL.readLock();
    private final Lock ccN = this.ccL.writeLock();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(adr_0 adr_02) {
        if (adr_02 == null) {
            throw new IllegalArgumentException("Null argument disallowed");
        }
        try {
            this.ccN.lock();
            if (!this.ccK.contains(adr_02)) {
                this.ccK.add(adr_02);
            }
        }
        finally {
            this.ccN.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int au(Object object) {
        int n2 = 0;
        try {
            this.ccM.lock();
            for (adr_0 adr_02 : this.ccK) {
                adr_02.T(object);
                ++n2;
            }
        }
        finally {
            this.ccM.unlock();
        }
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Iterator tN() {
        ArrayList arrayList;
        try {
            this.ccM.lock();
            arrayList = new ArrayList(this.ccK);
        }
        finally {
            this.ccM.unlock();
        }
        return arrayList.iterator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public adr_0 bs(String string) {
        if (string == null) {
            return null;
        }
        adr_0 adr_02 = null;
        try {
            this.ccM.lock();
            for (adr_0 adr_03 : this.ccK) {
                if (!string.equals(adr_03.getName())) continue;
                adr_02 = adr_03;
                break;
            }
        }
        finally {
            this.ccM.unlock();
        }
        return adr_02;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(adr_0 adr_02) {
        if (adr_02 == null) {
            return false;
        }
        boolean bl2 = false;
        try {
            this.ccM.lock();
            for (adr_0 adr_03 : this.ccK) {
                if (adr_03 != adr_02) continue;
                bl2 = true;
                break;
            }
        }
        finally {
            this.ccM.unlock();
        }
        return bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void tO() {
        try {
            this.ccN.lock();
            for (adr_0 adr_02 : this.ccK) {
                adr_02.stop();
            }
            this.ccK.clear();
        }
        finally {
            this.ccN.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean c(adr_0 adr_02) {
        boolean bl2;
        if (adr_02 == null) {
            return false;
        }
        try {
            this.ccN.lock();
            bl2 = this.ccK.remove(adr_02);
        }
        finally {
            this.ccN.unlock();
        }
        return bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean bt(String string) {
        if (string == null) {
            return false;
        }
        boolean bl2 = false;
        try {
            this.ccN.lock();
            for (adr_0 adr_02 : this.ccK) {
                if (!string.equals(adr_02.getName())) continue;
                bl2 = this.ccK.remove(adr_02);
                break;
            }
        }
        finally {
            this.ccN.unlock();
        }
        return bl2;
    }
}

