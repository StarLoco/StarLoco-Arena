package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;

public interface InventoryContentChecker<ContentType extends InventoryContent> {
  int canAddItem(Inventory paramInventory, ContentType paramContentType);
  
  int canAddItem(Inventory paramInventory, ContentType paramContentType, short paramShort);
  
  int canReplaceItem(Inventory paramInventory, ContentType paramContentType1, ContentType paramContentType2);
  
  int canRemoveItem(Inventory paramInventory, ContentType paramContentType);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\InventoryContentChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */