/*     */ package com.ankamagames.dofusarena.client.ui.actions;
/*     */ 
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.fighter.FighterCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.event.Event;
/*     */ import com.ankamagames.dofusarena.client.core.game.fighter.Fighter;
/*     */ import com.ankamagames.dofusarena.client.core.game.spell.Spell;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.UIMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.coach.UICoachEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.fight.UIFightEventCardMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterCardMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterEquipmentMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterMessage;
/*     */ import com.ankamagames.dofusarena.client.ui.protocol.message.teamManagement.UIFighterSpellMessage;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Message;
/*     */ import com.ankamagames.framework.kernel.core.common.message.Worker;
/*     */ import com.ankamagames.xulor.event.Event;
/*     */ import com.ankamagames.xulor.event.ItemOutEvent;
/*     */ import com.ankamagames.xulor.event.ItemOverEvent;
/*     */ import com.ankamagames.xulor.event.MouseButtons;
/*     */ import com.ankamagames.xulor.event.MouseClickEvent;
/*     */ import com.ankamagames.xulor.event.MouseEnteredEvent;
/*     */ import com.ankamagames.xulor.event.MouseExitedEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FightActions
/*     */ {
/*     */   public static final String PACKAGE = "dofusarena.fight";
/*     */   
/*     */   public static void setReadyForPlacement(Event event) {
/*  44 */     UIMessage message = new UIMessage();
/*  45 */     message.setId(18009);
/*  46 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setReadyForObservation(Event event) {
/*  55 */     UIMessage message = new UIMessage();
/*  56 */     message.setId(18010);
/*  57 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setReadyForAction(Event event) {
/*  66 */     UIMessage message = new UIMessage();
/*  67 */     message.setId(18011);
/*  68 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void giveUpFight(Event event) {
/*  77 */     UIMessage message = new UIMessage();
/*  78 */     message.setId(18000);
/*  79 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseFighterInformations(Event event, Fighter fighter) {
/*  89 */     UIFighterMessage message = new UIFighterMessage();
/*  90 */     message.setFighter(fighter);
/*  91 */     message.setId(18012);
/*  92 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseEventCard(Event event, Event eventCard) {
/* 102 */     UIFightEventCardMessage message = new UIFightEventCardMessage();
/* 103 */     message.setEvent(eventCard);
/* 104 */     message.setId(18015);
/* 105 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fighterEndsTurn(Event event, Fighter fighter) {
/* 115 */     UIFighterMessage message = new UIFighterMessage();
/* 116 */     message.setFighter(fighter);
/* 117 */     message.setId(18001);
/* 118 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fighterSetSouthEastDirection(Event event, Fighter fighter) {
/* 128 */     UIFighterMessage message = new UIFighterMessage();
/* 129 */     message.setFighter(fighter);
/* 130 */     message.setId(18002);
/* 131 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fighterSetSouthWestDirection(Event event, Fighter fighter) {
/* 141 */     UIFighterMessage message = new UIFighterMessage();
/* 142 */     message.setFighter(fighter);
/* 143 */     message.setId(18003);
/* 144 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fighterSetNorthWestDirection(Event event, Fighter fighter) {
/* 154 */     UIFighterMessage message = new UIFighterMessage();
/* 155 */     message.setFighter(fighter);
/* 156 */     message.setId(18004);
/* 157 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fighterSetNorthEastDirection(Event event, Fighter fighter) {
/* 167 */     UIFighterMessage message = new UIFighterMessage();
/* 168 */     message.setFighter(fighter);
/* 169 */     message.setId(18005);
/* 170 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fighterSelectSpell(MouseClickEvent event, Fighter fighter, Spell spell) {
/* 180 */     if (event.getButton() == MouseButtons.BUTTON1) {
/* 181 */       UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 182 */       message.setFighter(fighter);
/* 183 */       message.setSpell(spell);
/* 184 */       message.setId(18006);
/* 185 */       Worker.getInstance().pushMessage((Message)message);
/*     */     } else {
/* 187 */       openCloseSpellInfos((Event)event, spell);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fighterSelectFighterCard(MouseClickEvent event, Fighter fighter, FighterCard fighterCard) {
/* 198 */     if (event.getButton() == MouseButtons.BUTTON1) {
/* 199 */       UIFighterCardMessage message = new UIFighterCardMessage();
/* 200 */       message.setFighter(fighter);
/* 201 */       message.setFighterCard(fighterCard);
/* 202 */       message.setId(18007);
/* 203 */       Worker.getInstance().pushMessage((Message)message);
/*     */     } else {
/* 205 */       openCloseFighterCardInfos((Event)event, fighterCard);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fighterSelectCloseCombat(Event event, Fighter fighter) {
/* 215 */     UIFighterMessage message = new UIFighterMessage();
/* 216 */     message.setFighter(fighter);
/* 217 */     message.setId(18008);
/* 218 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseSpellInfos(Event event, Spell spell) {
/* 228 */     UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 229 */     message.setSpell(spell);
/* 230 */     message.setId(18013);
/* 231 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openCloseFighterCardInfos(Event event, FighterCard fighterCard) {
/* 241 */     UIFighterCardMessage message = new UIFighterCardMessage();
/* 242 */     message.setFighterCard(fighterCard);
/* 243 */     message.setId(18014);
/* 244 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showEquipmentInfos(ItemOverEvent event) {
/* 253 */     Object value = event.getItemValue();
/* 254 */     if (value != null && value instanceof FighterCard) {
/* 255 */       UIFighterEquipmentMessage message = new UIFighterEquipmentMessage();
/* 256 */       message.setEquipment((FighterCard)value);
/* 257 */       message.setId(16622);
/* 258 */       Worker.getInstance().pushMessage((Message)message);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void hideEquipmentInfos(ItemOutEvent event) {
/* 268 */     UIMessage message = new UIMessage();
/* 269 */     message.setId(16623);
/* 270 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showSpellInfos(ItemOverEvent event) {
/* 279 */     Object value = event.getItemValue();
/* 280 */     if (value != null && value instanceof Spell) {
/* 281 */       UIFighterSpellMessage message = new UIFighterSpellMessage();
/* 282 */       message.setSpell((Spell)value);
/* 283 */       message.setId(16624);
/* 284 */       Worker.getInstance().pushMessage((Message)message);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void hideSpellInfos(ItemOutEvent event) {
/* 294 */     UIMessage message = new UIMessage();
/* 295 */     message.setId(16625);
/* 296 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showCoachCardInfos(ItemOverEvent event) {
/* 305 */     Object value = event.getItemValue();
/* 306 */     if (value != null && value instanceof CoachCard) {
/* 307 */       UICoachEquipmentMessage message = new UICoachEquipmentMessage();
/* 308 */       message.setEquipment((CoachCard)value);
/* 309 */       message.setId(16700);
/* 310 */       Worker.getInstance().pushMessage((Message)message);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void hideCoachCardInfos(ItemOutEvent event) {
/* 320 */     UIMessage message = new UIMessage();
/* 321 */     message.setId(16701);
/* 322 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void highlightSelectedFighter(MouseEnteredEvent event, Fighter fighter) {
/* 332 */     UIFighterMessage message = new UIFighterMessage();
/* 333 */     message.setFighter(fighter);
/* 334 */     message.setId(18016);
/* 335 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unlightSelectedFighter(MouseExitedEvent event, Fighter fighter) {
/* 344 */     UIFighterMessage message = new UIFighterMessage();
/* 345 */     message.setFighter(fighter);
/* 346 */     message.setId(18017);
/* 347 */     Worker.getInstance().pushMessage((Message)message);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\actions\FightActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */