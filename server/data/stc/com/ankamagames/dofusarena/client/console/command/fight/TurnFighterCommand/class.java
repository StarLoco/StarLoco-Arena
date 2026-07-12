/*    */ package com.ankamagames.dofusarena.client.console.command.fight;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterMessage;
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
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 35 */     if ((args.size() < 3) || (args.get(2) == null)) {
/* 36 */       return;
/*    */     }
/* 38 */     Direction8 direction = Direction8.getDirectionFromIndex(Integer.valueOf((String)args.get(2)).intValue());
/*    */     short id;
/* 40 */     short id; short id; short id; switch (direction) {
/*    */     case SOUTH_EAST: 
/* 42 */       id = 18005;
/* 43 */       break;
/*    */     case NORTH_WEST: 
/* 45 */       id = 18004;
/* 46 */       break;
/*    */     case EAST: 
/* 48 */       id = 18002;
/* 49 */       break;
/*    */     case NORTH: 
/* 51 */       id = 18003;
/* 52 */       break;
/*    */     case NONE: case NORTH_EAST: case SOUTH: default: 
/*    */       return;
/*    */     }
/*    */     short id;
/* 57 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 58 */     Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/*    */     
/* 60 */     UIFighterMessage message = new UIFighterMessage();
/* 61 */     message.setFighter(fighter);
/* 62 */     message.setId(id);
/* 63 */     Worker.getInstance().pushMessage(message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\fight\TurnFighterCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */