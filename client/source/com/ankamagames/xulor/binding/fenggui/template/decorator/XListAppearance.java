/*    */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.component.List;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*    */ import com.ankamagames.xulor.theme.ThemeElement;
/*    */ import com.ankamagames.xulor.theme.ThemeListAppearance;
/*    */ import java.util.ArrayList;
/*    */ import org.fenggui.IAppearance;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XListAppearance
/*    */   extends XDecoratorAppearance
/*    */ {
/*    */   public static final String TAG = "ListAppearance";
/* 26 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList<ThemeAppearance>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAppearance getAppearance() {
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void applyAllAttributes() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildGUI() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildXML() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement cloneElementStructure() {
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 73 */     return "ListAppearance";
/*    */   }
/*    */   
/*    */   public static void setAppearance(List list, ThemeListAppearance theme) {
/* 77 */     if (list == null || theme == null) {
/*    */       return;
/*    */     }
/*    */     
/* 81 */     if (theme.getMouseOverColor() != null) {
/* 82 */       list.setMouseOverColor(theme.getMouseOverColor());
/*    */     }
/*    */     
/* 85 */     if (list.isHorizontal()) {
/* 86 */       ThemeElement scrollbarElement = theme.getThemeElement("horizontalScrollbar");
/* 87 */       XScrollBarAppearance.applyScrollBarTheme(list.getScrollBar(), scrollbarElement);
/*    */     } else {
/* 89 */       ThemeElement scrollbarElement = theme.getThemeElement("verticalScrollbar");
/* 90 */       XScrollBarAppearance.applyScrollBarTheme(list.getScrollBar(), scrollbarElement);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XListAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */