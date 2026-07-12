package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;

import java.nio.ByteBuffer;

public abstract interface InventoryContent
{
  public abstract void release();
  
  public abstract long getUniqueId();
  
  public abstract int getReferenceId();
  
  public abstract byte[] serialize();
  
  public abstract boolean unserialize(ByteBuffer paramByteBuffer);
  
  public abstract short getQuantity();
  
  public abstract void setQuantity(short paramShort);
  
  public abstract void updateQuantity(short paramShort);
  
  public abstract short getStackMaximumHeight();
  
  public abstract InventoryContent getCopy();
  
  public abstract InventoryContent getClone();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\InventoryContent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */