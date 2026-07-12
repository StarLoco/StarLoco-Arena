/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class ThemeAppearance
/*    */   implements IThemeElement
/*    */ {
/*    */   public static final String TAG = "Appearance";
/*    */   public static final String DEFAULT_STATE = "default";
/* 21 */   private String m_state = "default";
/*    */   
/* 23 */   private ArrayList<IThemeElement> m_decorators = new ArrayList<IThemeElement>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(IThemeElement elem) {
/* 32 */     if (elem instanceof ThemeBorder || elem instanceof ThemeBackground) {
/* 33 */       this.m_decorators.add(elem);
/*    */     }
/*    */   }
/*    */   
/*    */   public ArrayList<IThemeElement> getDecorators() {
/* 38 */     return this.m_decorators;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getState() {
/* 45 */     return this.m_state;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setState(String state) {
/* 52 */     this.m_state = state;
/*    */   }
/*    */   
/*    */   protected void copyAttributes(ThemeAppearance app) {
/* 56 */     app.setState(this.m_state);
/* 57 */     for (IThemeElement element : this.m_decorators) {
/* 58 */       app.add(element.cloneAppearance());
/*    */     }
/*    */   }
/*    */   
/*    */   public IThemeElement cloneAppearance() {
/* 63 */     ThemeAppearance app = new ThemeAppearance();
/*    */     
/* 65 */     copyAttributes(app);
/*    */     
/* 67 */     return app;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */