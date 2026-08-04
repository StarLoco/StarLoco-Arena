/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

/*
 * Renamed from EG
 */
public class eg_1 {
    private static final int aTA = 10;
    private static final int aTB = 30;
    private ex_2 aTC;
    private HashMap aTD = new HashMap();
    private HashMap aTE = new HashMap();

    public eg_1(ex_2 ex_22) {
        this.aTC = ex_22;
    }

    public static int eL(int n2) {
        return add_1.aOG().YN().kY() + n2;
    }

    public static int eM(int n2) {
        return add_1.aOG().YN().kZ() + n2;
    }

    public void clean() {
    }

    public void d(adg_2 adg_22, String string) {
        ArrayList<adg_2> arrayList = (ArrayList<adg_2>)this.aTD.get(string);
        if (arrayList != null) {
            if (!arrayList.contains(adg_22)) {
                arrayList.add(adg_22);
            }
        } else {
            arrayList = new ArrayList<adg_2>();
            arrayList.add(adg_22);
            this.aTD.put(string, arrayList);
        }
    }

    public void e(adg_2 adg_22, String string) {
        ArrayList arrayList = (ArrayList)this.aTD.get(string);
        if (arrayList != null) {
            arrayList.remove(adg_22);
            if (arrayList.size() <= 0) {
                this.aTD.remove(string);
            }
        }
    }

    public void f(adg_2 adg_22, String string) {
        Stack<adg_2> stack = (Stack<adg_2>)this.aTE.get(string);
        if (stack != null) {
            if (stack.contains(adg_22)) {
                stack.remove(adg_22);
            }
            stack.push(adg_22);
        } else {
            stack = new Stack<adg_2>();
            stack.push(adg_22);
            this.aTE.put(string, stack);
        }
    }

    public void g(adg_2 adg_22, String string) {
        Stack stack = (Stack)this.aTE.get(string);
        if (stack != null) {
            stack.remove(adg_22);
            if (stack.size() <= 0) {
                this.aTE.remove(string);
            }
        }
    }

    public Point a(adg_2 adg_22, adg_2 adg_23) {
        int n2 = adg_22.getDisplayX();
        int n3 = adg_22.getDisplayY();
        rA rA2 = new rA(n2 + adg_22.getWidth() + 10, n3, adg_23.getWidth(), adg_23.getHeight(), null);
        boolean bl2 = rA2.x + rA2.width <= this.aTC.getWidth() ? this.a(rA2, adg_23) : false;
        if (!bl2) {
            rA2 = new rA(n2, n3 + adg_22.getHeight() + 10, adg_23.getWidth(), adg_23.getHeight(), null);
            bl2 = rA2.y + rA2.height <= this.aTC.getHeight() ? this.a(rA2, adg_23) : false;
        }
        if (!bl2) {
            rA2 = new rA(n2 - adg_23.getWidth() - 10, n3, adg_23.getWidth(), adg_23.getHeight(), null);
            bl2 = rA2.x > 0 ? this.a(rA2, adg_23) : false;
        }
        if (!bl2) {
            rA2 = new rA(n2, n3 - adg_23.getHeight() - 10, adg_23.getWidth(), adg_23.getHeight(), null);
            bl2 = rA2.y > 0 ? this.a(rA2, adg_23) : false;
        }
        if (bl2) {
            return new Point(rA2.x, rA2.y);
        }
        return null;
    }

    public Point c(adg_2 adg_22) {
        adg_2 adg_23 = this.j(adg_22);
        if (adg_23 != null) {
            int n2 = adg_23.getDisplayX();
            int n3 = adg_23.getDisplayY();
            rA rA2 = new rA(n2 + 30, n3 - (adg_22.getHeight() - adg_23.getHeight()) - 30, adg_22.getWidth(), adg_22.getHeight(), null);
            if (rA2.y <= 0) {
                rA2.y = 0;
            }
            if (rA2.x + rA2.width > this.aTC.getWidth()) {
                rA2.x = this.aTC.getWidth() - adg_22.getWidth();
            }
            return new Point(rA2.x, rA2.y);
        }
        return null;
    }

    public boolean a(rA rA2, adg_2 adg_22) {
        aht_1 aht_12 = this.aTC.getLayeredContainer().getContainerFromWidget(adg_22);
        if (aht_12 == null) {
            return false;
        }
        ArrayList arrayList = aht_12.getWidgetChildren();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            adg_2 adg_23 = (adg_2)arrayList.get(j);
            if (adg_23 == adg_22 || !this.d(adg_23) || adg_23.getWidth() > 1000 && adg_23.getHeight() > 700 && adg_23.getX() == 0 && adg_23.getY() == 0 || !this.d(adg_23.getX(), adg_23.getY(), adg_23.getWidth(), adg_23.getHeight(), (int)rA2.getX(), (int)rA2.getY(), (int)rA2.getWidth(), (int)rA2.getHeight())) continue;
            return false;
        }
        return true;
    }

    private boolean d(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        if (n8 <= 0 || n9 <= 0 || n4 <= 0 || n5 <= 0) {
            return false;
        }
        n9 += n7;
        n4 += n2;
        n5 += n3;
        return !((n8 += n6) >= n6 && n8 <= n2 || n9 >= n7 && n9 <= n3 || n4 >= n2 && n4 <= n6 || n5 >= n3 && n5 <= n7);
    }

    public boolean d(adg_2 adg_22) {
        if (adg_22.getElementMap() != null) {
            String string = adg_22.getElementMap().getId();
            for (String string2 : this.aTD.keySet()) {
                if (!string.startsWith(string2)) continue;
                return ((ArrayList)this.aTD.get(string2)).contains(adg_22);
            }
        }
        return false;
    }

    public adg_2 j(na_1 na_12) {
        if (na_12.getElementMap() != null) {
            String string = na_12.getElementMap().getId();
            for (String string2 : this.aTE.keySet()) {
                if (!string.startsWith(string2) || ((Stack)this.aTE.get(string2)).size() <= 1) continue;
                Stack stack = (Stack)this.aTE.get(string2);
                for (adg_2 adg_22 : stack) {
                    if (adg_22 != na_12) continue;
                    int n2 = stack.indexOf(adg_22) - 1;
                    return n2 < 0 ? null : (adg_2)stack.get(n2);
                }
            }
        }
        return null;
    }

    public void b(String string, adg_2 adg_22) {
        ArrayList arrayList = (ArrayList)this.aTD.get(string);
        if (arrayList == null) {
            return;
        }
        if (adg_22 != null) {
            this.a(arrayList, adg_22);
        }
    }

    private void a(ArrayList arrayList, adg_2 adg_22) {
        int n2 = adg_22.getHeight();
        int n3 = adg_22.getWidth();
        if (n2 == 0) {
            n2 = adg_22.getPrefSize().height;
        }
        if (n3 == 0) {
            n3 = adg_22.getPrefSize().width;
        }
        rA[] rAArray = this.c(adg_22.getX(), adg_22.getY(), n3, n2, this.aTC.getWidth(), this.aTC.getHeight());
        int n4 = 0;
        int n5 = arrayList.size();
        for (rA rA2 : rAArray) {
            if (n4 >= n5) break;
            n4 += this.a(rA2, arrayList, n4);
        }
        if (n4 < n5) {
            for (int j = n4; j < n5; ++j) {
                adg_2 adg_23 = (adg_2)arrayList.get(j);
                if (adg_23 == adg_22) continue;
                adg_23.setPosition(this.c(adg_23));
            }
        }
    }

    private int a(rA rA2, ArrayList arrayList, int n2) {
        int n3;
        if (arrayList.size() == 0 || n2 < 0 || n2 >= arrayList.size()) {
            return 0;
        }
        int n4 = 0;
        adg_2 adg_22 = null;
        int n5 = arrayList.size();
        for (n3 = n2; n3 < n5; ++n3) {
            adg_2 adg_23 = (adg_2)arrayList.get(n3);
            if (adg_23.getWidth() != 0 && adg_23.getHeight() != 0 && adg_23.getVisible()) {
                adg_22 = adg_23;
                break;
            }
            ++n4;
            ++n2;
        }
        if (adg_22 == null) {
            return n4;
        }
        n3 = rA2.dg(adg_22.getWidth());
        n5 = rA2.dh(adg_22.getHeight());
        while (rA2.di(n3)) {
            while (rA2.dj(n5) && this.e((int)rA2.getX(), (int)rA2.getY(), (int)rA2.getWidth(), (int)rA2.getHeight(), n3, n5, adg_22.getWidth(), adg_22.getHeight())) {
                adg_22.setPosition(n3, n5);
                ++n4;
                n5 += rA2.dl(adg_22.getHeight());
                int n6 = arrayList.size();
                for (int j = ++n2; j < n6; ++j) {
                    adg_2 adg_24 = (adg_2)arrayList.get(j);
                    if (adg_24.getWidth() != 0 && adg_24.getHeight() != 0 && adg_24.getVisible()) {
                        adg_22 = adg_24;
                        break;
                    }
                    ++n4;
                    ++n2;
                }
                if (n2 != arrayList.size()) continue;
                return n4;
            }
            n5 = rA2.dh(adg_22.getHeight());
            n3 += rA2.dk(adg_22.getWidth());
        }
        return n4;
    }

    private boolean e(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        return this.c(n2, n3, n4, n5, n6, n7, n8, n9) && this.t(n6, n7, n8, n9);
    }

    private boolean c(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        return this.s(n2, n4, n6, n8) && this.r(n3, n5, n7, n9);
    }

    private boolean r(int n2, int n3, int n4, int n5) {
        return n4 < n2 + n3 && n4 + n5 > n2;
    }

    private boolean s(int n2, int n3, int n4, int n5) {
        return n4 < n2 + n3 && n4 + n5 > n2;
    }

    private boolean t(int n2, int n3, int n4, int n5) {
        return n2 >= 0 && n3 >= 0 && n2 + n4 < this.aTC.getWidth() && n3 + n5 < this.aTC.getHeight();
    }

    private rA[] c(int n2, int n3, int n4, int n5, int n6, int n7) {
        rA[] rAArray = new rA[]{new rA(n2, n3 + n5, n4, n7 - n3 - n5, bo_0.aJt), new rA(n2, 0, n4, n3, bo_0.aJs), new rA(0, n3, n2, n5, bo_0.aJu), new rA(n2 + n4, n3, n6 - n2 - n4, n5, bo_0.aJv)};
        Arrays.sort(rAArray, ak_1.aGS);
        return rAArray;
    }
}

