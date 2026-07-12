package com.ankamagames.xulor.core.renderer;

import com.ankamagames.xulor.template.IElement;

public abstract interface ResultProvider
  extends IElement
{
  public abstract Object getResult(Object paramObject);
  
  public abstract void setResultProviderParent(ResultProviderParent paramResultProviderParent);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\renderer\ResultProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */