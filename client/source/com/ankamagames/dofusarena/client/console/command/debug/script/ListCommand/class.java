/*    */ package com.ankamagames.dofusarena.client.console.command.debug.script.ListCommand;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.framework.script.LuaManager;
/*    */ import com.ankamagames.framework.script.LuaScript;
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
/*    */ public class ListCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 31 */     StringBuilder builder = new StringBuilder("Liste des scripts :");
/* 32 */     Iterable<LuaScript> scripts = LuaManager.getInstance().getScripts();
/* 33 */     for (LuaScript script : scripts) {
/* 34 */       builder.append('\n').append("- ").append(script.getId()).append(" (").append(script.getState()).append(')');
/*    */     }
/* 36 */     manager.trace(builder.toString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 45 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\debug\script\ListCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */