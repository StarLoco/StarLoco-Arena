package com.ankamagames.baseImpl.graphics.alea;

import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;

public abstract interface CustomElementFactory
{
  public abstract BasicElement createElement(int paramInt1, int paramInt2);
  
  public abstract WorldElement createWorldElement(BasicElement paramBasicElement, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\CustomElementFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */