/*    */ package com.ankamagames.xulor.actions;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IPopup;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XulorActions
/*    */ {
/*    */   public static final String PACKAGE = "xulor";
/*    */   
/*    */   public static void unloadDialog(Event event) {
/* 33 */     IElement element = event.getElement();
/* 34 */     if (element != null) {
/* 35 */       String id = element.getElementMap().getId();
/* 36 */       Xulor.getInstance().unload(id);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void popup(Event event, IPopup popup) {
/* 46 */     popup.show();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void openClosePopup(Event event, IPopup popup) {
/* 56 */     if (popup.isShow()) {
/* 57 */       popup.hide();
/*    */     } else {
/* 59 */       popup.show();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\actions\XulorActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */