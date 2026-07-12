/*     */ package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;
/*     */ 
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEndEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEndEvent.Reason;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEvent.Action;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerModifiedEvent;
/*     */ import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerModifiedEvent.Modification;
/*     */ import java.util.HashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ItemExchanger<ContentType extends InventoryContent>
/*     */ {
/*  21 */   protected static final Logger m_logger = Logger.getLogger(ItemExchanger.class);
/*     */   
/*     */ 
/*  24 */   protected final ItemExchangerUser<ContentType>[] m_users = new ItemExchangerUser[2];
/*  25 */   protected final long[] m_usersId = new long[2];
/*  26 */   protected final boolean[] m_usersReady = new boolean[2];
/*     */   
/*     */ 
/*  29 */   protected final HashMap<Long, ContentType>[] m_usersExchangeList = new HashMap[2];
/*     */   
/*     */   protected ItemExchangeState m_currentState;
/*     */   protected final long m_id;
/*     */   
/*     */   protected ItemExchanger(long id)
/*     */   {
/*  36 */     this.m_id = id;
/*     */   }
/*     */   
/*     */   public long getId() {
/*  40 */     return this.m_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void init(ItemExchangerUser<ContentType> user1, ItemExchangerUser<ContentType> user2)
/*     */   {
/*  49 */     if (user1 == null)
/*  50 */       throw new NullPointerException("First user of an ItemExchanger can't be null");
/*  51 */     if (user2 == null)
/*  52 */       throw new NullPointerException("Second user of an ItemExchanger can't be null");
/*  53 */     this.m_currentState = ItemExchangeState.INITIALIZING;
/*     */     
/*  55 */     this.m_users[0] = user1;
/*  56 */     this.m_usersId[0] = user1.getId();
/*  57 */     this.m_usersReady[0] = false;
/*     */     
/*  59 */     this.m_users[1] = user2;
/*  60 */     this.m_usersId[1] = user2.getId();
/*  61 */     this.m_usersReady[1] = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/*  68 */     if (this.m_currentState != ItemExchangeState.INITIALIZING) {
/*  69 */       throw new IllegalStateException("Only an Initializing exchanger can be started. Current State: " + this.m_currentState);
/*     */     }
/*  71 */     if (!this.m_users[1].canStartNewExchange()) {
/*  72 */       notifyUser(0, ItemExchangerEndEvent.checkOut(this, ItemExchangerEndEvent.Reason.INVITATION_IMPOSSIBLE_USER_BUSY));
/*  73 */       finishExchange();
/*  74 */       return;
/*     */     }
/*     */     
/*  77 */     if (!ItemExchangerManager.getInstance().addExchanger(this)) {
/*  78 */       notifyUser(0, ItemExchangerEndEvent.checkOut(this, ItemExchangerEndEvent.Reason.INVITATION_IMPOSSIBLE_USER_BUSY));
/*  79 */       finishExchange();
/*  80 */       return;
/*     */     }
/*     */     
/*  83 */     this.m_users[0].setCurrentItemExchanger(this);
/*     */     
/*  85 */     if (needsInvitationStep()) {
/*  86 */       doInvitationStep();
/*     */     } else {
/*  88 */       doPropositionStep();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isUserConcerned(ItemExchangerUser user)
/*     */   {
/*  98 */     return (user == this.m_users[0]) || (user == this.m_users[1]);
/*     */   }
/*     */   
/*     */   protected int getUserInnerIndex(ItemExchangerUser user) {
/* 102 */     return user == this.m_users[0] ? 0 : 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void doInvitationStep()
/*     */   {
/* 110 */     this.m_users[0].setCurrentItemExchanger(this);
/* 111 */     this.m_currentState = ItemExchangeState.AWAITING_INVITATION_ANSWER;
/* 112 */     notifyUser(this.m_users[0], ItemExchangerEvent.checkOut(this, ItemExchangerEvent.Action.EXCHANGE_PROPOSED));
/* 113 */     notifyUser(this.m_users[1], ItemExchangerEvent.checkOut(this, ItemExchangerEvent.Action.EXCHANGE_REQUESTED));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void doPropositionStep()
/*     */   {
/* 120 */     this.m_users[1].setCurrentItemExchanger(this);
/* 121 */     this.m_currentState = ItemExchangeState.PROPOSING;
/* 122 */     notifyUsers(ItemExchangerEvent.checkOut(this, ItemExchangerEvent.Action.EXCHANGE_STARTED));
/*     */   }
/*     */   
/*     */ 
/*     */   protected void finishExchange()
/*     */   {
/* 128 */     if (this.m_currentState != ItemExchangeState.INITIALIZING) {
/* 129 */       this.m_users[0].setCurrentItemExchanger(null);
/*     */     }
/* 131 */     if (this.m_currentState != ItemExchangeState.INITIALIZING) {
/* 132 */       this.m_users[1].setCurrentItemExchanger(null);
/*     */     }
/*     */     
/* 135 */     this.m_currentState = ItemExchangeState.FINISHED;
/*     */     
/*     */ 
/* 138 */     releaseExchangeList(0);
/* 139 */     releaseExchangeList(1);
/*     */     
/*     */ 
/* 142 */     ItemExchangerManager.getInstance().removeExchanger(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void notifyUsers(ItemExchangerEvent event)
/*     */   {
/* 150 */     notifyUsers(event, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void notifyUsers(ItemExchangerEvent event, boolean releaseEvent)
/*     */   {
/* 160 */     if (this.m_users[0] != null)
/* 161 */       this.m_users[0].onItemExchangerEvent(event);
/* 162 */     if (this.m_users[1] != null)
/* 163 */       this.m_users[1].onItemExchangerEvent(event);
/* 164 */     if (releaseEvent) {
/*     */       try {
/* 166 */         event.release();
/*     */       } catch (Exception e) {
/* 168 */         m_logger.error("Exception lors de la notification d'un évènement aux utilisateurs d'un ItemExchanger: ", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void notifyUser(int userNumber, ItemExchangerEvent event)
/*     */   {
/* 176 */     notifyUser(userNumber, event, true);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void notifyUser(ItemExchangerUser user, ItemExchangerEvent event)
/*     */   {
/* 182 */     notifyUser(user, event, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void notifyUser(int userNumber, ItemExchangerEvent event, boolean releaseEvent)
/*     */   {
/* 193 */     if ((userNumber != 0) && (userNumber != 1)) {
/* 194 */       m_logger.error("Impossible d'envoyer un évènement à l'utilisateur numéro " + userNumber);
/* 195 */       return;
/*     */     }
/* 197 */     notifyUser(this.m_users[userNumber], event, releaseEvent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void notifyUser(ItemExchangerUser user, ItemExchangerEvent event, boolean releaseEvent)
/*     */   {
/* 208 */     if (user != null)
/* 209 */       user.onItemExchangerEvent(event);
/* 210 */     if (releaseEvent) {
/*     */       try {
/* 212 */         event.release();
/*     */       } catch (Exception e) {
/* 214 */         m_logger.error("Exception lors de la notification d'un évènement à un user d'un ItemExchanger: ", e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemExchangeState getCurrentState()
/*     */   {
/* 224 */     return this.m_currentState;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract boolean needsInvitationStep();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract boolean needsWaitingUsersReady();
/*     */   
/*     */ 
/*     */ 
/*     */   public void acceptInvitation(ItemExchangerUser user)
/*     */   {
/* 241 */     if (!isUserConcerned(user))
/* 242 */       return;
/* 243 */     doPropositionStep();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void declineInvitation(ItemExchangerUser user, byte reason)
/*     */   {
/* 252 */     if (!isUserConcerned(user)) {
/* 253 */       return;
/*     */     }
/* 255 */     notifyUser(0, ItemExchangerEndEvent.checkOut(this, ItemExchangerEndEvent.Reason.INVITATION_REMOTELY_CANCELED));
/* 256 */     notifyUser(1, ItemExchangerEndEvent.checkOut(this, ItemExchangerEndEvent.Reason.INVITATION_LOCALLY_CANCELED));
/*     */     
/* 258 */     finishExchange();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void cancelExchange(ItemExchangerUser user)
/*     */   {
/* 266 */     if (!isUserConcerned(user)) {
/* 267 */       m_logger.error("Impossible de retirer l'utilisateur " + user + " de l'ItemExchangerUser : il n'est pas concerné par cet échange");
/* 268 */       return;
/*     */     }
/* 270 */     int index = getUserInnerIndex(user);
/* 271 */     notifyUser(index, ItemExchangerEndEvent.checkOut(this, ItemExchangerEndEvent.Reason.LOCALLY_CANCELED));
/* 272 */     notifyUser(1 - index, ItemExchangerEndEvent.checkOut(this, ItemExchangerEndEvent.Reason.REMOTELY_CANCELED));
/*     */     
/* 274 */     finishExchange();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void acceptExchange(ItemExchangerUser user)
/*     */   {
/* 282 */     if (!isUserConcerned(user)) {
/* 283 */       m_logger.error("Impossible de finir l'échange " + user + " de l'ItemExchangerUser n'est pas concerné par cet échange");
/* 284 */       return;
/*     */     }
/*     */     
/*     */ 
/* 288 */     if (isExchangeValid()) {
/* 289 */       doExchange();
/*     */     }
/*     */     
/*     */ 
/* 293 */     notifyUsers(ItemExchangerEndEvent.checkOut(this, ItemExchangerEndEvent.Reason.EXCHANGE_DONE));
/*     */     
/* 295 */     finishExchange();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setUserReady(ItemExchangerUser user)
/*     */   {
/* 302 */     if (!isUserConcerned(user)) {
/* 303 */       m_logger.error("Impossible de continuer l'échange " + user + " de l'ItemExchangerUser n'est pas concerné par cet échange");
/* 304 */       return;
/*     */     }
/*     */     
/* 307 */     if (needsWaitingUsersReady())
/*     */     {
/* 309 */       byte userIndex = getIndexByUser(user);
/* 310 */       this.m_usersReady[userIndex] = (this.m_usersReady[userIndex] != 0 ? 0 : true);
/*     */       
/*     */ 
/* 313 */       if ((this.m_usersReady[0] != 0) && (this.m_usersReady[1] != 0)) {
/* 314 */         acceptExchange(user);
/*     */       } else {
/* 316 */         ItemExchangerEvent event = ItemExchangerEvent.checkOut(this, ItemExchangerEvent.Action.EXCHANGE_USER_READY);
/* 317 */         event.setUserIndex(userIndex);
/*     */         
/* 319 */         notifyUsers(event);
/*     */       }
/*     */     }
/*     */     else {
/* 323 */       acceptExchange(user);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemExchangerUser<ContentType> getUser(int userIndex)
/*     */   {
/* 334 */     if ((userIndex < 0) || (userIndex > 1))
/* 335 */       return null;
/* 336 */     return this.m_users[userIndex];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte getIndexByUser(ItemExchangerUser user)
/*     */   {
/* 345 */     if (this.m_users[0] == user)
/* 346 */       return 0;
/* 347 */     if (this.m_users[1] == user) {
/* 348 */       return 1;
/*     */     }
/* 350 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addItemToExchange(byte userIndex, ContentType referenceContent, short quantity)
/*     */   {
/* 359 */     if (quantity < 1) {
/* 360 */       m_logger.error("On essaye d'ajouter une quantité négative ou nulle d'objets à l'échange");
/* 361 */       return;
/*     */     }
/*     */     
/*     */ 
/* 365 */     this.m_usersReady[0] = false;
/* 366 */     this.m_usersReady[1] = false;
/*     */     
/*     */ 
/* 369 */     if (this.m_usersExchangeList[userIndex] == null) {
/* 370 */       this.m_usersExchangeList[userIndex] = new HashMap();
/*     */     }
/*     */     
/* 373 */     if (this.m_usersExchangeList[userIndex].containsKey(Long.valueOf(referenceContent.getUniqueId())))
/*     */     {
/* 375 */       ContentType exchangeContent = (InventoryContent)this.m_usersExchangeList[userIndex].get(Long.valueOf(referenceContent.getUniqueId()));
/*     */       
/* 377 */       if ((isCheckQuantityNeeded()) && (exchangeContent.getQuantity() + quantity > referenceContent.getQuantity())) {
/* 378 */         m_logger.error("On essaye d'ajouter plus de carte qu'il n'en a de disponible dans un échange");
/* 379 */         return;
/*     */       }
/* 381 */       exchangeContent.updateQuantity(quantity);
/*     */     }
/*     */     else {
/* 384 */       if ((isCheckQuantityNeeded()) && (quantity > referenceContent.getQuantity())) {
/* 385 */         m_logger.error("On essaye d'échanger plus de carte qu'il n'en a de disponible");
/* 386 */         return;
/*     */       }
/*     */       
/* 389 */       ContentType exchangeContent = referenceContent.getClone();
/* 390 */       exchangeContent.setQuantity(quantity);
/*     */       
/* 392 */       this.m_usersExchangeList[userIndex].put(Long.valueOf(referenceContent.getUniqueId()), exchangeContent);
/*     */     }
/*     */     
/* 395 */     notifyUsers(ItemExchangerModifiedEvent.checkOut(this, ItemExchangerModifiedEvent.Modification.CONTENT_ADDED, userIndex, referenceContent, quantity));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isCheckQuantityNeeded()
/*     */   {
/* 403 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeItemToExchange(byte userIndex, ContentType referenceContent, short quantity)
/*     */   {
/* 413 */     if (quantity < 1) {
/* 414 */       m_logger.error("On essaye de retirer une quantité négative ou nulle d'objets à l'échange");
/* 415 */       return;
/*     */     }
/*     */     
/*     */ 
/* 419 */     this.m_usersReady[0] = false;
/* 420 */     this.m_usersReady[1] = false;
/*     */     
/* 422 */     if ((this.m_usersExchangeList[userIndex] == null) || (!this.m_usersExchangeList[userIndex].containsKey(Long.valueOf(referenceContent.getUniqueId())))) {
/* 423 */       m_logger.error("On essaye de retirer un objet de l'échange qui n'existe pas");
/* 424 */       return;
/*     */     }
/*     */     
/* 427 */     ContentType exchangeContent = (InventoryContent)this.m_usersExchangeList[userIndex].get(Long.valueOf(referenceContent.getUniqueId()));
/* 428 */     exchangeContent.updateQuantity((short)-quantity);
/*     */     
/*     */ 
/* 431 */     if (exchangeContent.getQuantity() <= 0) {
/* 432 */       exchangeContent.release();
/* 433 */       this.m_usersExchangeList[userIndex].remove(Long.valueOf(referenceContent.getUniqueId()));
/*     */     }
/*     */     
/* 436 */     notifyUsers(ItemExchangerModifiedEvent.checkOut(this, ItemExchangerModifiedEvent.Modification.CONTENT_REMOVED, userIndex, referenceContent, quantity));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void releaseExchangeList(int index)
/*     */   {
/* 447 */     if (this.m_usersExchangeList[index] != null)
/*     */     {
/* 449 */       for (ContentType content : this.m_usersExchangeList[index].values())
/*     */       {
/* 451 */         content.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract boolean isExchangeValid();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void doExchange();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void cancel(ItemExchangerUser user)
/*     */   {
/* 473 */     switch (getCurrentState())
/*     */     {
/*     */     case INITIALIZING: 
/* 476 */       cancelExchange(user);
/* 477 */       break;
/*     */     case FINISHED: 
/* 479 */       declineInvitation(user, (byte)0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\ItemExchanger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */