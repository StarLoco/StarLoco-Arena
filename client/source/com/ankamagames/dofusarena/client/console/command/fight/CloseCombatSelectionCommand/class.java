/*    */ package com.ankamagames.dofusarena.client.console.command.fight.CloseCombatSelectionCommand;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterMessage;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*    */ import com.ankamagames.framework.kernel.core.common.message.Worker;
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
/*    */ public class CloseCombatSelectionCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 34 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 35 */     Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/*    */     
/* 37 */     UIFighterMessage message = new UIFighterMessage();
/* 38 */     message.setFighter(fighter);
/* 39 */     message.setId(18008);
/* 40 */     Worker.getInstance().pushMessage((Message)message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 49 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\fight\CloseCombatSelectionCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */