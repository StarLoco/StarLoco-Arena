/*    */ package com.ankamagames.xulor.core;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IWindow;
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
/*    */ public class WindowManager
/*    */ {
/*    */   private ArrayList<IWindow> m_windows;
/* 21 */   private static WindowManager m_instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static WindowManager getInstance() {
/* 27 */     if (m_instance == null) {
/* 28 */       m_instance = new WindowManager();
/*    */     }
/* 30 */     return m_instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(int x, int y) {
/* 37 */     IElement element = Xulor.getInstance().getScene().getElementAt(x, y);
/* 38 */     if (element == null) {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     while (element != null && !(element instanceof IWindow)) {
/* 43 */       element = element.getParent();
/*    */     }
/*    */     
/* 46 */     if (element instanceof IWindow) {
/* 47 */       ((IWindow)element).pushToTop();
/*    */     }
/*    */     
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\WindowManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */