/*    */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.template.XObservableLabelComponent;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*    */ import com.ankamagames.xulor.theme.ThemeElement;
/*    */ import com.ankamagames.xulor.theme.ThemeTabbedContainerAppearance;
/*    */ import java.util.ArrayList;
/*    */ import org.fenggui.IAppearance;
/*    */ import org.fenggui.ObservableLabelWidget;
/*    */ import org.fenggui.TabContainer;
/*    */ import org.fenggui.TabItemLabel;
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
/*    */ public class XTabbedContainerAppearance
/*    */   extends XDecoratorAppearance
/*    */ {
/*    */   public static final String TAG = "TabbedContainerAppearance";
/* 31 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList<ThemeAppearance>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAppearance getAppearance() {
/* 38 */     return null;
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
/* 71 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 78 */     return "TabbedContainerAppearance";
/*    */   }
/*    */   
/*    */   public static void setAppearance(TabContainer container, ThemeTabbedContainerAppearance theme) {
/* 82 */     if (container == null || theme == null) {
/*    */       return;
/*    */     }
/*    */     
/* 86 */     ThemeElement tabApp = theme.getThemeElement("tabitem");
/* 87 */     for (int i = 0; i < container.getChildrenCount(); i++) {
/* 88 */       container.selectTab(i);
/* 89 */       TabItemLabel label = container.getSelectedTabLabel();
/* 90 */       XObservableLabelComponent.applyObservableLabelTheme((ObservableLabelWidget)label, tabApp);
/*    */     } 
/*    */     
/* 93 */     if (container.getChildrenCount() != 0)
/*    */     {
/* 95 */       container.selectTab(0);
/*    */     }
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XTabbedContainerAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */