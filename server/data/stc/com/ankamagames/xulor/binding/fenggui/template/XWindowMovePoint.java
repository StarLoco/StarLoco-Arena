/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.binding.fenggui.component.WindowMovePoint;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XWindowMovePoint
/*    */   extends XContainer
/*    */ {
/*    */   public static final String TAG = "windowMovePoint";
/* 20 */   private WindowMovePoint m_wmp = null;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void displayNonBlockingAvailability() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildGUI()
/*    */   {
/* 36 */     if (this.m_wmp == null) {
/* 37 */       this.m_wmp = new WindowMovePoint();
/*    */       
/* 39 */       applyAllAttributes();
/*    */       
/* 41 */       if (this.m_parent != null) this.m_parent.addWidget(this);
/* 42 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_wmp, this);
/*    */     }
/*    */     IElement[] arrayOfIElement;
/* 45 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement component = arrayOfIElement[i];
/* 46 */       component.buildGUI();
/*    */     }
/* 48 */     applyTheme();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public WindowMovePoint getWidget()
/*    */   {
/* 58 */     return this.m_wmp;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 67 */     return "windowMovePoint";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 77 */     XWindowMovePoint elem = new XWindowMovePoint();
/* 78 */     copyElementData(elem);
/* 79 */     return elem;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XWindowMovePoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */