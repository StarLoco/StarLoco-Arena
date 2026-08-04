/*
 * Decompiled with CFR 0.152.
 */
import java.util.Stack;

/*
 * Renamed from zL
 */
public class zl_2
implements aGm {
    private Stack ajc = new Stack();
    private afq_1 ajd;

    public air_1 a(afq_1 afq_12, aji_1 aji_12) {
        this.ajd = afq_12;
        this.ajc.push(aji_12);
        aji_1 aji_13 = (aji_1)this.ajc.peek();
        aab_2 aab_22 = new aab_2();
        aab_22.b();
        aab_22.setElementMap(aji_13);
        aab_22.setMinSize(new agj_1(200, 150));
        aab_22.setNonBlocking(true);
        aab_22.setStyle("replay");
        aab_22.Ak();
        auW auW2 = new auW();
        auW2.b();
        auW2.setElementMap(aji_13);
        auW2.setAlign(ajn_1.dSy);
        aab_22.j(auW2);
        auW2.Ak();
        auW2.Aj();
        ei_1 ei_12 = ei_1.checkOut();
        aab_22.j(ei_12);
        ei_12.Ak();
        aqq_0 aqq_02 = new aqq_0();
        aqq_02.b();
        aqq_02.setElementMap(aji_13);
        apc apc2 = new apc();
        apc2.fS("dofusarena.replay:playPause");
        aqq_02.setOnClick(apc2);
        aqq_02.setStyle("playReplay");
        ei_12.j(aqq_02);
        aqq_02.Ak();
        afz_1 afz_12 = afz_1.checkOut();
        afz_12.setElementMap(aji_13);
        afz_12.setAttribute("style");
        afz_12.setName("replayPaused");
        aqq_02.j(afz_12);
        afz_12.Ak();
        av_1 av_12 = new av_1();
        av_12.b();
        av_12.setElementMap(aji_13);
        av_12.setElseValue("pausereplay");
        av_12.setValue("playReplay");
        afz_12.j(av_12);
        av_12.Ak();
        du_0 du_02 = new du_0();
        du_02.b();
        du_02.setElementMap(aji_13);
        av_12.j(du_02);
        du_02.Ak();
        du_02.Aj();
        av_12.Aj();
        afz_12.Aj();
        afz_1 afz_13 = afz_1.checkOut();
        afz_13.setElementMap(aji_13);
        afz_13.setAttribute("visible");
        afz_13.setName("tutorialMode");
        aqq_02.j(afz_13);
        afz_13.Ak();
        av_1 av_13 = new av_1();
        av_13.b();
        av_13.setElementMap(aji_13);
        afz_13.j(av_13);
        av_13.Ak();
        aDe aDe2 = new aDe();
        aDe2.b();
        aDe2.setElementMap(aji_13);
        av_13.j(aDe2);
        aDe2.Ak();
        aDe2.Aj();
        av_13.Aj();
        afz_13.Aj();
        aqq_02.Aj();
        aqq_0 aqq_03 = new aqq_0();
        aqq_03.b();
        aqq_03.setElementMap(aji_13);
        apc apc3 = new apc();
        apc3.fS("dofusarena.replay:stop");
        aqq_03.setOnClick(apc3);
        aqq_03.setStyle("stopReplay");
        ei_12.j(aqq_03);
        aqq_03.Ak();
        aqq_03.Aj();
        ei_12.Aj();
        aab_22.Aj();
        return aab_22;
    }
}

