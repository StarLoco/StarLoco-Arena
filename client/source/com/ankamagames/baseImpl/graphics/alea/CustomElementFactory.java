package com.ankamagames.baseImpl.graphics.alea;

import com.ankamagames.baseImpl.graphics.alea.element.BasicElement;
import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;

public interface CustomElementFactory {
  BasicElement createElement(int paramInt1, int paramInt2);
  
  WorldElement createWorldElement(BasicElement paramBasicElement, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\CustomElementFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */