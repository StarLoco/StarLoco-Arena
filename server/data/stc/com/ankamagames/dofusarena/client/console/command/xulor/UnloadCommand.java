/*    */ package com.ankamagames.dofusarena.client.console.command.xulor;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.xulor.Xulor;
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
/*    */ public class UnloadCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 29 */     if (!args.isEmpty()) {
/* 30 */       String id = (String)args.get(2);
/* 31 */       if (Xulor.getInstance().isLoaded(id)) {
/* 32 */         Xulor.getInstance().unload((String)args.get(2));
/*    */       } else {
/* 34 */         manager.err(id + " n'est pas chargé !");
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 45 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\xulor\UnloadCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */