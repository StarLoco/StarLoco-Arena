/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from tg
 */
class tg_0
extends uc_1 {
    final /* synthetic */ apM oR;

    private tg_0(apM apM2, LuaState luaState) {
        this.oR = apM2;
        super(luaState);
    }

    public String getName() {
        return "characterDisplayerOpen";
    }

    public LX[] Q() {
        return new LX[]{new LX("fileName", aos_1.elS, false), new LX("linkageName", aos_1.elS, false), new LX("align", aos_1.elS, false), new LX("screenXoffset", aos_1.elT, false), new LX("screenYoffset", aos_1.elT, false)};
    }

    public LX[] R() {
        return new LX[]{new LX("id", aos_1.elT, false)};
    }

    protected void c(int n2) {
        int n3 = apM.aDI();
        String string = apM.a(this.oR, n3);
        adg_2 adg_22 = (adg_2)add_1.aOG().a(string, oh_2.bq("emissaryTutoDialog"), 2L, (short)30000);
        String string2 = this.hZ(1);
        String string3 = add_1.aOG().yh().Mr();
        String string4 = this.hZ(0) + ".anm";
        String string5 = string2.substring(2);
        int n4 = Integer.parseInt(string2.substring(0, 1));
        azs_0.aLV().a("filePath", (Object)string4, string);
        azs_0.aLV().a("animName", (Object)string5, string);
        azs_0.aLV().a("direction", (Object)n4, string);
        ajn_1 ajn_12 = ajn_1.valueOf(this.hZ(2));
        int n5 = this.hW(3);
        int n6 = this.hW(4);
        auW auW2 = new auW();
        auW2.b();
        auW2.setAlign(ajn_12);
        auW2.setXOffset(n5);
        auW2.setYOffset(n6);
        adg_22.a(auW2);
        this.id(n3);
    }

    /* synthetic */ tg_0(apM apM2, LuaState luaState, apd_1 apd_12) {
        this(apM2, luaState);
    }
}

