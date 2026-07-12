package com.ankamagames.framework.kernel.core.resource;

import com.ankamagames.framework.kernel.core.common.Poolable;

public interface ManageableResource extends Poolable {
  void reloadResource(ResourceContext paramResourceContext);
  
  void unloadResource(ResourceContext paramResourceContext);
  
  long estimateMemoryUsageInBytes();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\ManageableResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */