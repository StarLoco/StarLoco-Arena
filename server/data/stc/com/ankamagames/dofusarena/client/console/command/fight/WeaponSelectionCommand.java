/*    */ package com.ankamagames.dofusarena.client.console.command.fight;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ArrayInventory;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterCardMessage;
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
/*    */ public class WeaponSelectionCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 35 */     if ((args.size() < 3) || (args.get(2) == null)) {
/* 36 */       return;
/*    */     }
/* 38 */     short position = Short.valueOf((String)args.get(2)).shortValue();
/* 39 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 40 */     Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/* 41 */     if ((fighter != null) && (fighter.getEquipmentInventory() != null)) {
/* 42 */       UIFighterCardMessage message = new UIFighterCardMessage();
/* 43 */       message.setFighter(fighter);
/* 44 */       FighterCard card = (FighterCard)fighter.getEquipmentInventory().getFromPosition(position);
/* 45 */       message.setFighterCard(card);
/* 46 */       message.setId(18007);
/* 47 */       Worker.getInstance().pushMessage(message);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\fight\WeaponSelectionCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */