package com.ankamagames.baseImpl.graphicalClient.core.contentLoader;

import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;

public abstract interface ContentInitializer
{
  public abstract void init(AbstractGameClientInstance paramAbstractGameClientInstance)
    throws Exception;
  
  public abstract String getName();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\core\contentLoader\ContentInitializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */