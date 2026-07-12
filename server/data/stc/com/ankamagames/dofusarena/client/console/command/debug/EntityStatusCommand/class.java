/*    */ package com.ankamagames.dofusarena.client.console.command.debug;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.NetworkEntity;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.framework.kernel.core.common.message.MessageHandler;
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
/*    */ public class EntityStatusCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 30 */     StringBuilder builder = new StringBuilder("Status de DofusArenaGameEntity :");
/*    */     try {
/* 32 */       builder.append('\n').append("Connecté : ").append(DofusArenaGameEntity.getInstance().getNetworkEntity().isConnected());
/*    */     }
/*    */     catch (Exception localException) {}
/* 35 */     builder.append('\n').append("Frames : ");
/* 36 */     for (MessageHandler frame : DofusArenaGameEntity.getInstance().getFrames()) {
/* 37 */       builder.append('\n').append(" - ").append(frame.getClass().getSimpleName());
/*    */     }
/* 39 */     manager.trace(builder.toString());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\debug\EntityStatusCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */