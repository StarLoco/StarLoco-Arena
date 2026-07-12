package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;

import com.ankamagames.baseImpl.common.clientAndServer.game.inventory.event.ItemExchangerEvent;

public abstract interface ItemExchangerUser<ContentType extends InventoryContent>
{
  public abstract long getId();
  
  public abstract String getName();
  
  public abstract void onItemExchangerEvent(ItemExchangerEvent paramItemExchangerEvent);
  
  public abstract void setCurrentItemExchanger(ItemExchanger<ContentType> paramItemExchanger);
  
  public abstract boolean canStartNewExchange();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\ItemExchangerUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */