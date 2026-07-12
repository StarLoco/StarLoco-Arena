package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;

public abstract interface InventoryContentChecker<ContentType extends InventoryContent>
{
  public abstract int canAddItem(Inventory paramInventory, ContentType paramContentType);
  
  public abstract int canAddItem(Inventory paramInventory, ContentType paramContentType, short paramShort);
  
  public abstract int canReplaceItem(Inventory paramInventory, ContentType paramContentType1, ContentType paramContentType2);
  
  public abstract int canRemoveItem(Inventory paramInventory, ContentType paramContentType);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\InventoryContentChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */