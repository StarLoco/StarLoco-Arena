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
/*    */ public class FighterCardSelectionCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 36 */     if ((args.size() < 3) || (args.get(2) == null)) {
/* 37 */       return;
/*    */     }
/*    */     try
/*    */     {
/* 41 */       selected = Short.valueOf((String)args.get(2)).shortValue();
/*    */     } catch (NumberFormatException e) { short selected;
/*    */       return;
/*    */     }
/*    */     short selected;
/* 46 */     Fight fight = DofusArenaGameEntity.getInstance().getFight();
/* 47 */     Fighter fighter = (Fighter)fight.getTimeline().getCurrentFighter();
/*    */     
/* 49 */     StackInventory<Spell> spells = fighter.getTeamMateSpellInventory();
/* 50 */     Spell selectedSpell = null;
/* 51 */     int i = 0;
/* 52 */     for (Spell spell : spells) {
/* 53 */       if (i == selected) {
/* 54 */         selectedSpell = spell;
/* 55 */         break;
/*    */       }
/* 57 */       i++;
/*    */     }
/*    */     
/* 60 */     if (selectedSpell != null) {
/* 61 */       UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 62 */       message.setFighter(fighter);
/* 63 */       message.setSpell(selectedSpell);
/* 64 */       message.setId(18006);
/* 65 */       Worker.getInstance().pushMessage(message);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isPassThrough()
/*    */   {
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\fight\FighterCardSelectionCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */