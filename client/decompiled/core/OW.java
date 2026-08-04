/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class OW
extends adl_0
implements aho_0 {
    public static final String bCO = "initialXpGained";
    public static final String bCP = "moraleBonus";
    public static final String bCQ = "goodRestBonus";
    public static final String bCR = "itemBonus";
    public static final String bCS = "totalXpGained";
    public static final String aSZ = "totalXp";
    public static final String bCT = "morale";
    public static final String bCU = "moraleModificationForProgressBar";
    public static final String bCV = "moraleModification";
    public static final String bCW = "tiredness";
    public static final String bCX = "tirednessModificationForProgressBar";
    public static final String bCY = "tirednessModification";
    public static final String bCZ = "hasBeenHurt";
    public static final String[] ce = new String[]{"initialXpGained", "moraleBonus", "goodRestBonus", "itemBonus", "totalXpGained", "totalXp", "morale", "moraleModificationForProgressBar", "moraleModification", "tiredness", "tirednessModificationForProgressBar", "tirednessModification", "hasBeenHurt"};

    public OW(byte[] byArray) {
        this.b(byArray);
    }

    public String[] getFields() {
        return ce;
    }

    public void b(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.cng = byteBuffer.get() == 1;
        this.cnh = byteBuffer.get() == 1;
        this.cni = byteBuffer.getInt();
        this.cnj = byteBuffer.get() == 1;
        this.cnk = byteBuffer.get() == 1;
        this.cnl = byteBuffer.getInt();
        this.cnm = byteBuffer.getInt();
        this.cnn = byteBuffer.get() == 1;
        this.baT = byteBuffer.get() == 1;
        this.aRx = byteBuffer.get();
        this.cno = byteBuffer.get();
        this.aRy = byteBuffer.get();
        this.cnp = byteBuffer.get();
        this.cnq = byteBuffer.getInt();
        this.cnr = byteBuffer.getInt();
        this.cns = byteBuffer.get();
        this.cnt = byteBuffer.get() == 1;
        this.cnu = byteBuffer.getInt();
        this.cnv = byteBuffer.getInt();
    }

    public Object getFieldValue(String string) {
        if (string.equals(bCO)) {
            return this.cnr;
        }
        if (string.equals(bCP)) {
            return this.cns + "%";
        }
        if (string.equals(bCQ)) {
            if (this.cnt) {
                return "50%";
            }
            return "0%";
        }
        if (string.equals(bCR)) {
            return this.cnv - this.cnu;
        }
        if (string.equals(bCS)) {
            return this.cnv;
        }
        if (string.equals(aSZ)) {
            return this.cnq;
        }
        if (string.equals(bCT)) {
            return 100 - this.aRy;
        }
        if (string.equals(bCU)) {
            return 100 - this.aRy + this.cnp;
        }
        if (string.equals(bCV)) {
            return this.cnp > 0 ? "+" + this.cnp : Byte.valueOf(this.cnp);
        }
        if (string.equals(bCW)) {
            return 100 - this.aRx;
        }
        if (string.equals(bCX)) {
            return 100 - this.aRx + this.cno;
        }
        if (string.equals(bCY)) {
            return this.cno > 0 ? "+" + this.cno : Byte.valueOf(this.cno);
        }
        if (string.equals(bCZ)) {
            return this.cnl != 0;
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

