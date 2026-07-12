package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;

import com.ankamagames.framework.annotations.Nullable;
import java.nio.ByteBuffer;

public abstract interface InventoryContentProvider<ContentType extends InventoryContent>
{
  @Nullable
  public abstract ContentType unserializeContent(ByteBuffer paramByteBuffer);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\InventoryContentProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */