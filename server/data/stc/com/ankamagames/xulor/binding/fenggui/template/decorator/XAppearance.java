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
/*    */ public class XAppearance
/*    */   extends XDecoratorAppearance
/*    */ {
/*    */   public static final String TAG = "Appearance";
/*    */   
/*    */   public void applyAllAttributes()
/*    */   {
/* 33 */     if ((this.m_parent instanceof XComponent)) {
/* 34 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/* 35 */       if (element == null) {
/* 36 */         return;
/*    */       }
/* 38 */       ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*    */       
/* 40 */       if (theme == null) {
/* 41 */         theme = new ThemeLabelAppearance();
/* 42 */         theme.setState(this.m_state);
/* 43 */         element.addThemeAppearance(theme);
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
/*    */   public void buildGUI()
/*    */   {
/* 57 */     IElement[] components = getChildren();
/*    */     IElement[] arrayOfIElement1;
/* 59 */     int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/* 60 */       c.buildGUI();
/*    */     }
/*    */     
/* 63 */     applyAllAttributes();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void buildXML()
/*    */   {
/* 70 */     IElement[] components = getChildren();
/*    */     IElement[] arrayOfIElement1;
/* 72 */     int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/* 73 */       c.buildXML();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IAppearance getAppearance()
/*    */   {
/* 82 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 90 */     return "Appearance";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 97 */     XAppearance elem = new XAppearance();
/* 98 */     copyElementData(elem);
/* 99 */     return elem;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */