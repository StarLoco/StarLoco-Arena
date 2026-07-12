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
/*    */ import java.util.ArrayList;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class EndTurnCommand
/*    */   implements Command
/*    */ {
/* 28 */   private static Logger m_logger = Logger.getLogger(EndTurnCommand.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 37 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 38 */     if ((fight != null) && (fight.getTimeline() != null)) {
/* 39 */       Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/*    */       
/* 41 */       UIFighterMessage message = new UIFighterMessage();
/* 42 */       message.setFighter(fighter);
/* 43 */       message.setId(18001);
/* 44 */       Worker.getInstance().pushMessage(message);
/*    */     }
/*    */     else {
/* 47 */       m_logger.trace((fight == null ? "fight" : "timeline") + " null");
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


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\fight\EndTurnCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */