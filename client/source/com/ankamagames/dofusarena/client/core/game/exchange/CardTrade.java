/*     */ package com.ankamagames.dofusarena.client.core.game.exchange;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchanger;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.ItemExchangerUser;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.ContentAlreadyPresentException;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.exception.InventoryCapacityReachedException;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaGameEntity;
/*     */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*     */ import com.ankamagames.dofusarena.client.core.game.card.coach.CoachCard;
/*     */ import com.ankamagames.dofusarena.client.core.game.coach.LocalCoach;
/*     */ import com.ankamagames.dofusarena.common.game.card.AbstractCoachCard;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.messagebox.MessageBoxControler;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ import java.util.ArrayList;
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
/*     */ public class CardTrade
/*     */   extends ItemExchanger<CoachCard>
/*     */   implements FieldProvider
/*     */ {
/*     */   public static final String EXCHANGE_ID_FIELD = "exchangeId";
/*     */   public static final String LOCAL_CARDS_EXCHANGE_FIELD = "localCardExchange";
/*     */   public static final String REMOTE_CARDS_EXCHANGE_FIELD = "remoteCardExchange";
/*     */   public static final String LOCAL_CARD_COUNT_FIELD = "localCardCount";
/*     */   public static final String REMOTE_CARD_COUNT_FIELD = "remoteCardCount";
/*     */   public static final String REMOTE_USER_READY = "remoteUserReady";
/*     */   public static final String LOCAL_USER_READY = "localUserReady";
/*  39 */   public static final String[] FIELDS = new String[] {
/*  40 */       "exchangeId", 
/*     */       
/*  42 */       "localCardExchange", 
/*  43 */       "remoteCardExchange", 
/*     */       
/*  45 */       "localCardCount", 
/*  46 */       "remoteCardCount", 
/*     */       
/*  48 */       "localUserReady", 
/*  49 */       "remoteUserReady"
/*     */     };
/*     */   
/*  52 */   private MessageBoxControler m_invitationMessageBoxControler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_requesterIsLocal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CardTrade(ItemExchangerUser<CoachCard> userRequesting, ItemExchangerUser<CoachCard> userRequested, boolean requesterIsLocal) {
/*  64 */     super(userRequesting.getId());
/*  65 */     init(userRequesting, userRequested);
/*  66 */     this.m_requesterIsLocal = requesterIsLocal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInvitationMessageBoxControler(MessageBoxControler invitationMessageBoxControler) {
/*  73 */     this.m_invitationMessageBoxControler = invitationMessageBoxControler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageBoxControler getInvitationMessageBoxControler() {
/*  80 */     return this.m_invitationMessageBoxControler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRequesterLocal() {
/*  89 */     return this.m_requesterIsLocal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean needsInvitationStep() {
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean needsWaitingUsersReady() {
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isExchangeValid() {
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isCheckQuantityNeeded() {
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doExchange() {
/* 136 */     LocalCoach localCoach = DofusArenaGameEntity.getInstance().getLocalCoach();
/* 137 */     byte index = getIndexByUser((ItemExchangerUser)localCoach);
/*     */     
/* 139 */     if (this.m_usersExchangeList[0] != null) {
/* 140 */       for (CoachCard card : this.m_usersExchangeList[0].values()) {
/*     */         
/* 142 */         if (index == 0) {
/* 143 */           localCoach.getCardInventories().updateInventoryQuantity(card.getUniqueId(), (short)-card.getQuantity());
/*     */           continue;
/*     */         } 
/*     */         try {
/* 147 */           localCoach.getCardInventories().addToInventory((AbstractCoachCard)card.getClone());
/* 148 */         } catch (ContentAlreadyPresentException e) {
/* 149 */           e.printStackTrace();
/* 150 */         } catch (InventoryCapacityReachedException e) {
/* 151 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 157 */     if (this.m_usersExchangeList[1] != null) {
/* 158 */       for (CoachCard card : this.m_usersExchangeList[1].values()) {
/*     */         
/* 160 */         if (index == 1) {
/* 161 */           localCoach.getCardInventories().updateInventoryQuantity(card.getUniqueId(), (short)-card.getQuantity());
/*     */           continue;
/*     */         } 
/*     */         try {
/* 165 */           localCoach.getCardInventories().addToInventory((AbstractCoachCard)card.getClone());
/* 166 */         } catch (ContentAlreadyPresentException e) {
/* 167 */           e.printStackTrace();
/* 168 */         } catch (InventoryCapacityReachedException e) {
/* 169 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getFields() {
/* 182 */     return FIELDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldValue(String fieldName) {
/* 191 */     if (fieldName.equals("exchangeId")) {
/* 192 */       return Long.valueOf(getId());
/*     */     }
/*     */     
/* 195 */     if (fieldName.equals("localUserReady")) {
/* 196 */       int localUserIndex = this.m_requesterIsLocal ? 0 : 1;
/* 197 */       return Boolean.valueOf(this.m_usersReady[localUserIndex]);
/*     */     } 
/*     */     
/* 200 */     if (fieldName.equals("remoteUserReady")) {
/* 201 */       int remoteUserIndex = this.m_requesterIsLocal ? 1 : 0;
/* 202 */       return Boolean.valueOf(this.m_usersReady[remoteUserIndex]);
/*     */     } 
/*     */     
/* 205 */     if (fieldName.equals("localCardExchange")) {
/*     */       
/* 207 */       int localUserIndex = this.m_requesterIsLocal ? 0 : 1;
/*     */       
/* 209 */       if (this.m_usersExchangeList[localUserIndex] != null) {
/* 210 */         ArrayList<CoachCard> localList = new ArrayList<CoachCard>();
/* 211 */         for (CoachCard coachCard : this.m_usersExchangeList[localUserIndex].values()) {
/* 212 */           if (coachCard.getQuantity() > 0) {
/* 213 */             localList.add(coachCard);
/*     */           }
/*     */         } 
/* 216 */         return localList.toArray();
/*     */       } 
/*     */     } 
/* 219 */     if (fieldName.equals("remoteCardExchange")) {
/* 220 */       int remoteUserIndex = this.m_requesterIsLocal ? 1 : 0;
/*     */       
/* 222 */       if (this.m_usersExchangeList[remoteUserIndex] != null) {
/* 223 */         ArrayList<CoachCard> localList = new ArrayList<CoachCard>();
/* 224 */         for (CoachCard coachCard : this.m_usersExchangeList[remoteUserIndex].values()) {
/* 225 */           if (coachCard.getQuantity() > 0) {
/* 226 */             localList.add(coachCard);
/*     */           }
/*     */         } 
/*     */         
/* 230 */         return localList.toArray();
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     if (fieldName.equals("localCardCount")) {
/* 235 */       int localUserIndex = this.m_requesterIsLocal ? 0 : 1;
/*     */       
/* 237 */       int cardCount = 0;
/* 238 */       if (this.m_usersExchangeList[localUserIndex] != null) {
/* 239 */         for (CoachCard coachCard : this.m_usersExchangeList[localUserIndex].values()) {
/* 240 */           cardCount += coachCard.getQuantity();
/*     */         }
/*     */       }
/*     */       
/* 244 */       return DofusArenaTranslator.getInstance().getString("exchange.propositionCardCount", new Object[] { Integer.valueOf(cardCount) });
/*     */     } 
/* 246 */     if (fieldName.equals("remoteCardCount")) {
/* 247 */       int remoteUserIndex = this.m_requesterIsLocal ? 1 : 0;
/*     */       
/* 249 */       int cardCount = 0;
/* 250 */       if (this.m_usersExchangeList[remoteUserIndex] != null) {
/* 251 */         for (CoachCard coachCard : this.m_usersExchangeList[remoteUserIndex].values()) {
/* 252 */           cardCount += coachCard.getQuantity();
/*     */         }
/*     */       }
/*     */       
/* 256 */       return DofusArenaTranslator.getInstance().getString("exchange.propositionCardCount", new Object[] { Integer.valueOf(cardCount) });
/*     */     } 
/*     */     
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFieldValue(String fieldName, Object value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFieldSynchronisable(String fieldName) {
/* 298 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateLocalCardProperties() {
/* 305 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "localCardExchange");
/* 306 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "localCardCount");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRemoteCardProperties() {
/* 313 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "remoteCardExchange");
/* 314 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "remoteCardCount");
/*     */   }
/*     */   
/*     */   public void updateLocalReadyProperties() {
/* 318 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "localUserReady");
/*     */   }
/*     */   
/*     */   public void updateRemoteReadyProperties() {
/* 322 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().firePropertyValueChanged(this, "remoteUserReady");
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\exchange\CardTrade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */