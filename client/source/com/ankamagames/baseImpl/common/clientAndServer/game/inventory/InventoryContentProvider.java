package com.ankamagames.baseImpl.common.clientAndServer.game.inventory;

import com.ankamagames.framework.annotations.Nullable;
import java.nio.ByteBuffer;

public interface InventoryContentProvider<ContentType extends InventoryContent> {
  @Nullable
  ContentType unserializeContent(ByteBuffer paramByteBuffer);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\inventory\InventoryContentProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */