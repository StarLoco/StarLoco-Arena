/*    */ package com.ankamagames.dofusarena.client.console.command.debug.script;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*    */ import com.ankamagames.framework.fileFormat.properties.PropertyException;
/*    */ import com.ankamagames.framework.script.LuaManager;
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
/*    */ public class RunCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 32 */     if (args.size() == 3) {
/* 33 */       String fileName = args.get(2);
/* 34 */       if (fileName != null) {
/*    */         try {
/* 36 */           String scriptPath = DofusArenaConfiguration.getInstance().getString("scriptPath");
/* 37 */           int id = LuaManager.getInstance().runScript(String.valueOf(scriptPath) + fileName + ".lua");
/* 38 */           manager.trace("ID : " + id);
/* 39 */         } catch (PropertyException e) {
/* 40 */           manager.err(e.toString());
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\debug\script\RunCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */