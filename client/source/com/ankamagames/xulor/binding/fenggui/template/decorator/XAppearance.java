/*    */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*    */ import com.ankamagames.xulor.template.IComponent;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*    */ import com.ankamagames.xulor.theme.ThemeElement;
/*    */ import com.ankamagames.xulor.theme.ThemeLabelAppearance;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XAppearance
/*    */   extends XDecoratorAppearance
/*    */ {
/*    */   public static final String TAG = "Appearance";
/*    */   
/*    */   public void applyAllAttributes() {
/* 33 */     if (this.m_parent instanceof XComponent) {
/* 34 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/* 35 */       if (element == null) {
/*    */         return;
/*    */       }
/* 38 */       ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*    */       
/* 40 */       if (theme == null) {
/* 41 */         ThemeLabelAppearance themeLabelAppearance = new ThemeLabelAppearance();
/* 42 */         themeLabelAppearance.setState(this.m_state);
/* 43 */         element.addThemeAppearance((ThemeAppearance)themeLabelAppearance);
/*    */       } 
/*    */       
/* 46 */       applySpacingAttributes();
/* 47 */       applyDecoratorAttributes();
/*    */     } 
/*    */     
/* 50 */     ((IComponent)this.m_parent).applyTheme();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildGUI() {
/* 57 */     IElement[] components = getChildren(); byte b; int i;
/*    */     IElement[] arrayOfIElement1;
/* 59 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/* 60 */       c.buildGUI();
/*    */       b++; }
/*    */     
/* 63 */     applyAllAttributes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildXML() {
/* 70 */     IElement[] components = getChildren(); byte b; int i;
/*    */     IElement[] arrayOfIElement1;
/* 72 */     for (i = (arrayOfIElement1 = components).length, b = 0; b < i; ) { IElement c = arrayOfIElement1[b];
/* 73 */       c.buildXML();
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAppearance getAppearance() {
/* 82 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 90 */     return "Appearance";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement cloneElementStructure() {
/* 97 */     XAppearance elem = new XAppearance();
/* 98 */     copyElementData((IElement)elem);
/* 99 */     return (IElement)elem;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */