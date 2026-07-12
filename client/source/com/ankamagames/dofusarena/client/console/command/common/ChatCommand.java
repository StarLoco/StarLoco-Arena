/*    */ package com.ankamagames.dofusarena.client.console.command.common;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.ElementMap;
/*    */ import com.ankamagames.xulor.template.ITextEditor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatCommand
/*    */   implements Command
/*    */ {
/*    */   public static final String SPACE = "space";
/*    */   public static final String NULL = "none";
/*    */   
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 39 */     boolean isLoaded = Xulor.getInstance().isLoaded("chatDialog");
/* 40 */     String arg = null;
/* 41 */     if (args.size() >= 3 && args.get(2) != null) {
/* 42 */       arg = args.get(2);
/*    */     }
/*    */     
/* 45 */     if (!isLoaded) {
/* 46 */       UIMessage message = new UIMessage();
/* 47 */       message.setId(20002);
/* 48 */       Worker.getInstance().pushMessage((Message)message);
/* 49 */     } else if (arg.equalsIgnoreCase("none")) {
/* 50 */       UIMessage message = new UIMessage();
/* 51 */       message.setId(20003);
/* 52 */       Worker.getInstance().pushMessage((Message)message);
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     ITextEditor te = null;
/* 57 */     if (isLoaded) {
/* 58 */       ElementMap map = Xulor.getInstance().getEnvironment().getElementMap("chatDialog");
/* 59 */       if (map != null) {
/* 60 */         te = (ITextEditor)map.getElement("chatInput");
/*    */       }
/*    */     } 
/*    */     
/* 64 */     if ((te == null || !te.hasFocus()) && !arg.equalsIgnoreCase("none")) {
/*    */       String val;
/* 66 */       if (((String)args.get(2)).equalsIgnoreCase("space")) {
/* 67 */         val = " ";
/*    */       } else {
/* 69 */         val = args.get(2);
/*    */       } 
/*    */       
/* 72 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty("chat.dialogView").synchronizeWithLastClient();
/* 73 */       Xulor.getInstance().getEnvironment().getPropertiesProvider().appendPropertyValue("chat.dialogView", "input", val);
/*    */     } 
/*    */     
/* 76 */     if (te != null) {
/* 77 */       te.setFocused(true);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\common\ChatCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */