/*    */ package com.ankamagames.dofusarena.client.console.command.debug;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
/*    */ import com.ankamagames.dofusarena.client.core.preferences.FightPreferenceStore;
/*    */ import com.ankamagames.framework.preferences.StackedPreferenceStore;
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
/*    */ public class PrefCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 30 */     if (args.size() > 2) {
/* 31 */       if (((String)args.get(2)).equals("0")) {
/* 32 */         DofusArenaClientInstance.getInstance().getPreferenceStore().removeChild(FightPreferenceStore.getInstance());
/* 33 */         manager.trace("remove FightPreferenceStore");
/*    */       } else {
/* 35 */         DofusArenaClientInstance.getInstance().getPreferenceStore().pushChild(FightPreferenceStore.getInstance());
/* 36 */         manager.trace("push FightPreferenceStore");
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
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\debug\PrefCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */