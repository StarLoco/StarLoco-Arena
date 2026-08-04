/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from PW
 */
public class pw_1 {
    private static pw_1 bEF = new pw_1();
    private final cp_2 bEG = new cp_2();

    public static pw_1 acG() {
        return bEF;
    }

    public void a(long l2, Iterable iterable, boolean bl2, boolean bl3) {
        Object object2;
        abi_0 abi_02 = new abi_0(l2);
        this.bEG.a(l2, abi_02);
        int n2 = bl2 ? 4 : 24;
        ArrayList<String> arrayList = new ArrayList<String>();
        String string = "";
        boolean bl4 = true;
        for (Object object2 : iterable) {
            string = string + (String)object2;
            arrayList.add(string);
            if (bl2) continue;
            bl4 &= !apN.aDK().Ln().yP() && !mc_1.qM().qO().containsKey(((String)object2).toLowerCase());
        }
        Object object3 = "";
        object3 = bl2 ? aon_0.aYc().getString("fightInvitation.messageOut", arrayList.toArray()) : (!bl3 ? aon_0.aYc().getString("fightInvitation.messageIn", arrayList.toArray()) : aon_0.aYc().getString("fightInvitation.messageInEvolution", arrayList.toArray()));
        if (bl4) {
            object2 = add_1.aOG().a((String)object3, n2 | 0x80 | 0x400, 102, 1);
            abi_02.d((r_0)object2);
            ((r_0)object2).a(new akA(this, bl3, l2));
        } else {
            object2 = new mz_0();
            ((mz_0)object2).d(l2);
            apN.aDK().vJ().b((pr_0)object2);
        }
    }

    public void cr(long l2) {
        abi_0 abi_02 = (abi_0)this.bEG.t(l2);
        this.a(abi_02);
        this.bEG.u(l2);
    }

    public abi_0 cs(long l2) {
        return (abi_0)this.bEG.t(l2);
    }

    public void a(abi_0 abi_02) {
        if (abi_02 != null && abi_02.aIq() != null) {
            abi_02.aIq().D();
        }
    }

    public boolean isEmpty() {
        return this.bEG.isEmpty();
    }

    public void clear() {
        this.bEG.a(new akB(this));
        this.bEG.clear();
    }
}

