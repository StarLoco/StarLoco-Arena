package com.ankamagames.xulor.core.renderer;

import com.ankamagames.xulor.template.IElement;

public interface ResultProvider extends IElement {
  Object getResult(Object paramObject);
  
  void setResultProviderParent(ResultProviderParent paramResultProviderParent);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\ResultProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */