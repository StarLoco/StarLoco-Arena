/*    */ package com.ankamagames.xulor.binding.fenggui.template;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.template.IMessageBox;
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
/*    */ public class XMessageBox
/*    */   extends XWindow
/*    */   implements IMessageBox
/*    */ {
/*    */   public static final String TAG = "MessageBox";
/*    */   
/*    */   public String getTag()
/*    */   {
/* 28 */     return "MessageBox";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 35 */     XMessageBox msgbox = new XMessageBox();
/* 36 */     copyElementData(msgbox);
/* 37 */     return msgbox;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XMessageBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */