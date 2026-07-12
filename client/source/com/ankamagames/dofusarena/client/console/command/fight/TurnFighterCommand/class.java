/*    */ package com.ankamagames.dofusarena.client.console.command.fight.TurnFighterCommand;
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
/*    */ import com.ankamagames.framework.kernel.core.maths.Direction8;
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
/*    */ public class TurnFighterCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/*    */     short id;
/* 35 */     if (args.size() < 3 || args.get(2) == null) {
/*    */       return;
/*    */     }
/* 38 */     Direction8 direction = Direction8.getDirectionFromIndex(Integer.valueOf(args.get(2)).intValue());
/*    */     
/* 40 */     switch (direction) {
/*    */       case NORTH_EAST:
/* 42 */         id = 18005;
/*    */         break;
/*    */       case NORTH_WEST:
/* 45 */         id = 18004;
/*    */         break;
/*    */       case SOUTH_EAST:
/* 48 */         id = 18002;
/*    */         break;
/*    */       case SOUTH_WEST:
/* 51 */         id = 18003;
/*    */         break;
/*    */       
/*    */       default:
/*    */         return;
/*    */     } 
/* 57 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 58 */     Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/*    */     
/* 60 */     UIFighterMessage message = new UIFighterMessage();
/* 61 */     message.setFighter(fighter);
/* 62 */     message.setId(id);
/* 63 */     Worker.getInstance().pushMessage((Message)message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\fight\TurnFighterCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */