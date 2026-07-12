/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.binding.fenggui.component.WindowMovePoint;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import org.fenggui.Widget;
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
/*    */   
/*    */   protected void displayNonBlockingAvailability() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void buildGUI() {
/* 36 */     if (this.m_wmp == null) {
/* 37 */       this.m_wmp = new WindowMovePoint();
/*    */       
/* 39 */       applyAllAttributes();
/*    */       
/* 41 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/* 42 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_wmp, (IElement)this);
/*    */     }  byte b; int i;
/*    */     IElement[] arrayOfIElement;
/* 45 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement component = arrayOfIElement[b];
/* 46 */       component.buildGUI(); b++; }
/*    */     
/* 48 */     applyTheme();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WindowMovePoint getWidget() {
/* 58 */     return this.m_wmp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTag() {
/* 67 */     return "windowMovePoint";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IElement cloneElementStructure() {
/* 77 */     XWindowMovePoint elem = new XWindowMovePoint();
/* 78 */     copyElementData(elem);
/* 79 */     return (IElement)elem;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XWindowMovePoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */