/*    */ package com.ankamagames.dofusarena.client.console.command.fight;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.StackInventory;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Fight;
/*    */ import com.ankamagames.dofusarena.client.core.game.fight.Timeline;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*    */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*    */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSpellMessage;
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
/*    */ public class SpellSelectionCommand
/*    */   implements Command
/*    */ {
/* 30 */   protected static final Logger m_logger = Logger.getLogger(SpellSelectionCommand.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 39 */     if ((args.size() < 3) || (args.get(2) == null)) {
/* 40 */       return;
/*    */     }
/*    */     try
/*    */     {
/* 44 */       selected = Integer.valueOf((String)args.get(2)).intValue();
/*    */     } catch (NumberFormatException e) { int selected;
/*    */       return;
/*    */     }
/*    */     int selected;
/* 49 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 50 */     if ((fight != null) && (fight.getTimeline() != null)) {
/* 51 */       Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/* 52 */       if (fighter != null) {
/* 53 */         StackInventory<Spell> spellInventory = fighter.getSpellInventory();
/* 54 */         Spell selectedSpell = null;
/* 55 */         int i = 0;
/* 56 */         for (Spell spell : spellInventory) {
/* 57 */           if (i == selected) {
/* 58 */             selectedSpell = spell;
/* 59 */             break;
/*    */           }
/* 61 */           i++;
/*    */         }
/* 63 */         if (selectedSpell != null) {
/* 64 */           UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 65 */           message.setFighter(fighter);
/* 66 */           message.setSpell(selectedSpell);
/* 67 */           message.setId(18006);
/* 68 */           Worker.getInstance().pushMessage(message);
/*    */         }
/*    */       } else {
/* 71 */         m_logger.error("Pas de fighter");
/*    */       }
/*    */     }
/* 74 */     else if (fight == null) {
/* 75 */       m_logger.error("Pas de fight");
/*    */     } else {
/* 77 */       m_logger.error("Pas de timeLine");
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\fight\SpellSelectionCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */