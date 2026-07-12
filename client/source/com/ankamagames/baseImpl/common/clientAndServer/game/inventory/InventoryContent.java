package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;

import java.nio.ByteBuffer;

public interface InventoryContent {
  void release();
  
  long getUniqueId();
  
  int getReferenceId();
  
  byte[] serialize();
  
  boolean unserialize(ByteBuffer paramByteBuffer);
  
  short getQuantity();
  
  void setQuantity(short paramShort);
  
  void updateQuantity(short paramShort);
  
  short getStackMaximumHeight();
  
  InventoryContent getCopy();
  
  InventoryContent getClone();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\InventoryContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */