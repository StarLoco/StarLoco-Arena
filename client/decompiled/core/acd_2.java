/*
 * Decompiled with CFR 0.152.
 */
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/*
 * Renamed from acd
 */
public class acd_2 {
    static final String DOMAIN = "ch.qos.logback.classic";

    public static String e(String string, Class clazz) {
        String string2 = "ch.qos.logback.classic:Name=" + string + ",Type=" + clazz.getName();
        return string2;
    }

    public static ObjectName c(vU vU2, Object object, String string) {
        String string2 = "Failed to convert [" + string + "] to ObjectName";
        try {
            return new ObjectName(string);
        }
        catch (MalformedObjectNameException malformedObjectNameException) {
            kw_0.a(vU2, object, string2, malformedObjectNameException);
            return null;
        }
        catch (NullPointerException nullPointerException) {
            kw_0.a(vU2, object, string2, nullPointerException);
            return null;
        }
    }

    public static boolean a(MBeanServer mBeanServer, ObjectName objectName) {
        return mBeanServer.isRegistered(objectName);
    }

    public static void a(MBeanServer mBeanServer, ahu_0 ahu_02, alX alX2, ObjectName objectName, Object object) {
        try {
            mBeanServer.registerMBean(alX2, objectName);
        }
        catch (Exception exception) {
            kw_0.a(ahu_02, object, "Failed to create mbean", exception);
        }
    }

    public static void a(ahu_0 ahu_02, MBeanServer mBeanServer, ObjectName objectName, Object object) {
        if (mBeanServer.isRegistered(objectName)) {
            try {
                kw_0.a(ahu_02, object, "Unregistering mbean [" + objectName + "]");
                mBeanServer.unregisterMBean(objectName);
            }
            catch (InstanceNotFoundException instanceNotFoundException) {
                kw_0.a(ahu_02, object, "Failed to unregister mbean" + objectName, instanceNotFoundException);
            }
            catch (MBeanRegistrationException mBeanRegistrationException) {
                kw_0.a(ahu_02, object, "Failed to unregister mbean" + objectName, mBeanRegistrationException);
            }
        } else {
            kw_0.a(ahu_02, object, "mbean [" + objectName + "] does not seem to be registered");
        }
    }
}

