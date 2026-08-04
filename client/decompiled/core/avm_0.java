/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/*
 * Renamed from avm
 */
public class avm_0 {
    protected static final Logger a = Logger.getLogger(avm_0.class);
    private static final avm_0 ddS = new avm_0();
    private final lb_0 ddT = new lb_0();

    private avm_0() {
    }

    public void a(int n2, XV xV) {
        if (this.ddT.contains(n2)) {
            ((List)this.ddT.get(n2)).add(xV);
        } else {
            ArrayList<XV> arrayList = new ArrayList<XV>();
            arrayList.add(xV);
            this.ddT.c(n2, arrayList);
        }
    }

    public List mv(int n2) {
        return (List)this.ddT.get(n2);
    }

    public static avm_0 aIp() {
        return ddS;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

