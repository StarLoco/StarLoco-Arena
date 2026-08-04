/*
 * Decompiled with CFR 0.152.
 */
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.xml.sax.Attributes;

/*
 * Renamed from ayI
 */
public class ayi_0
extends ka_0 {
    static final String dmC = "objectName";
    static final String dmD = "contextName";
    static final char dmE = ',';

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        String string2;
        String string3;
        ObjectName objectName;
        this.ee("begin");
        String string4 = this.Pb.getName();
        String string5 = attributes.getValue(dmD);
        if (!dh_2.isEmpty(string5)) {
            string4 = string5;
        }
        if ((objectName = acd_2.c(this.Pb, this, string3 = dh_2.isEmpty(string2 = attributes.getValue(dmC)) ? acd_2.e(string4, alX.class) : string2)) == null) {
            this.eg("Failed to for ObjectName for [" + string3 + "]");
            return;
        }
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        if (!acd_2.a(mBeanServer, objectName)) {
            alX alX2 = new alX((ahu_0)this.Pb, mBeanServer, objectName);
            try {
                mBeanServer.registerMBean(alX2, objectName);
            }
            catch (Exception exception) {
                this.e("Failed to create mbean", exception);
            }
        }
    }

    public void a(qq_0 qq_02, String string) {
    }
}

