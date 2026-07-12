package com.ankamagames.framework.kernel.core.resource;

import com.ankamagames.framework.kernel.core.common.Poolable;

public abstract interface ManageableResource
  extends Poolable
{
  public abstract void reloadResource(ResourceContext paramResourceContext);
  
  public abstract void unloadResource(ResourceContext paramResourceContext);
  
  public abstract long estimateMemoryUsageInBytes();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\ManageableResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */