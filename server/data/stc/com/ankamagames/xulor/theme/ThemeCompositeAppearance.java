/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ public class ThemeCompositeAppearance
/*    */   extends ThemeAppearance
/*    */ {
/*    */   public static final String TAG = "CompositeAppearance";
/* 18 */   private HashMap<String, ThemeElement> m_themeElementMap = new HashMap();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void add(IThemeElement elem)
/*    */   {
/* 25 */     if ((elem instanceof ThemeElement)) {
/* 26 */       ThemeElement app = (ThemeElement)elem;
/* 27 */       String name = app.getName();
/* 28 */       if (name != null) {
/* 29 */         this.m_themeElementMap.put(name, app);
/*    */       }
/*    */     } else {
/* 32 */       super.add(elem);
/*    */     }
/*    */   }
/*    */   
/*    */   public void addThemeElement(ThemeElement element) {
/* 37 */     if ((element != null) && (element.getName() != null)) {
/* 38 */       this.m_themeElementMap.put(element.getName(), element);
/*    */     }
/*    */   }
/*    */   
/*    */   public ThemeElement getThemeElement(String name) {
/* 43 */     return (ThemeElement)this.m_themeElementMap.get(name);
/*    */   }
/*    */   
/*    */   public void setThemeElement(String name, ThemeElement element) {
/* 47 */     if ((name != null) && (element != null)) {
/* 48 */       this.m_themeElementMap.put(name, element);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IThemeElement cloneAppearance()
/*    */   {
/* 57 */     ThemeCompositeAppearance app = new ThemeCompositeAppearance();
/*    */     
/* 59 */     copyAttributes(app);
/*    */     
/* 61 */     return app;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected void copyAttributes(ThemeCompositeAppearance app)
/*    */   {
/* 68 */     super.copyAttributes(app);
/* 69 */     for (ThemeElement element : this.m_themeElementMap.values()) {
/* 70 */       ThemeElement elem = (ThemeElement)element.cloneAppearance();
/* 71 */       app.addThemeElement(elem);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeCompositeAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */