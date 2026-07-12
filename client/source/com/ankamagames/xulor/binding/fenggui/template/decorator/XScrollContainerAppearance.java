/*    */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*    */ import com.ankamagames.xulor.theme.ThemeElement;
/*    */ import com.ankamagames.xulor.theme.ThemeScrollContainerAppearance;
/*    */ import java.util.ArrayList;
/*    */ import org.fenggui.IAppearance;
/*    */ import org.fenggui.ScrollBar;
/*    */ import org.fenggui.ScrollContainer;
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
/*    */ 
/*    */ 
/*    */ public class XScrollContainerAppearance
/*    */   extends XDecoratorAppearance
/*    */ {
/*    */   public static final String TAG = "ScrollContainerAppearance";
/* 29 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList<ThemeAppearance>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAppearance getAppearance() {
/* 36 */     return null;
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
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 76 */     return "ScrollContainerAppearance";
/*    */   }
/*    */   
/*    */   public static void setAppearance(ScrollContainer container, ThemeScrollContainerAppearance theme) {
/* 80 */     if (container == null || theme == null) {
/*    */       return;
/*    */     }
/*    */     
/* 84 */     ThemeElement scrollBarApp = theme.getThemeElement("horizontalScrollbar");
/* 85 */     ScrollBar scrollBar = container.getHorizontalScrollBar();
/* 86 */     XScrollBarAppearance.applyScrollBarTheme(scrollBar, scrollBarApp);
/*    */     
/* 88 */     scrollBarApp = theme.getThemeElement("verticalScrollbar");
/* 89 */     scrollBar = container.getVerticalScrollBar();
/* 90 */     XScrollBarAppearance.applyScrollBarTheme(scrollBar, scrollBarApp);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XScrollContainerAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */