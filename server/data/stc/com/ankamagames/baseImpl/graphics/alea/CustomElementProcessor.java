package com.ankamagames.baseImpl.graphics.alea;

import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
import java.util.ArrayList;

public abstract interface CustomElementProcessor
{
  public abstract void onReadCell(WorldCell paramWorldCell, WorldElement paramWorldElement, ArrayList<WorldElement>[] paramArrayOfArrayList);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\CustomElementProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */