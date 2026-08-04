/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from adC
 */
public class adc_1
extends ke {
    private static Logger a = Logger.getLogger(adc_1.class);
    private static final acl_0 uG = new ym_0(new azZ());

    public static adc_1 ata() {
        adc_1 adc_12;
        try {
            adc_12 = (adc_1)uG.adr();
            adc_12.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            adc_12 = new adc_1();
            adc_12.b();
        }
        return adc_12;
    }
}

