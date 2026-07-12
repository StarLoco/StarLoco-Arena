/*    */ package com.ankamagames.dofusarena.client.console.command.debug;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.framework.script.action.Action;
/*    */ import com.ankamagames.framework.script.action.ActionGroup;
/*    */ import com.ankamagames.framework.script.action.QueueActionGroupManager;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedList;
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
/*    */ public class ActionListCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 33 */     StringBuilder builder = new StringBuilder();
/* 34 */     LinkedList<ActionGroup> groups = QueueActionGroupManager.getInstance().getExecutingActionGroups();
/*    */     
/* 36 */     builder.append(groups.size()).append(" groupes d'action dans la pile.\n");
/*    */     
/* 38 */     if (groups.size() > 0) {
/* 39 */       ActionGroup group = groups.peek();
/*    */       
/* 41 */       builder.append("Actions dans le groupe en haut de la pile : \n");
/* 42 */       for (Action action : group.getActions()) {
/* 43 */         builder.append(action.getClass().getSimpleName()).append(" ").append(action.getActionId()).append(" (").append(action.getUniqueId()).append(")\n");
/*    */       }
/*    */     } 
/*    */     
/* 47 */     manager.trace(builder.toString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\debug\ActionListCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */