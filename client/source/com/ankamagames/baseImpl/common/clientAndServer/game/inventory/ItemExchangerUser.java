package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;

import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEvent;

public interface ItemExchangerUser<ContentType extends InventoryContent> {
  long getId();
  
  String getName();
  
  void onItemExchangerEvent(ItemExchangerEvent paramItemExchangerEvent);
  
  void setCurrentItemExchanger(ItemExchanger<ContentType> paramItemExchanger);
  
  boolean canStartNewExchange();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\ItemExchangerUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */