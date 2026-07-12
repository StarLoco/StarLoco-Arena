package com.ankamagames.baseImpl.common.clientAndServer.game.part;

import com.ankamagames.framework.kernel.core.maths.Point3;
import com.ankamagames.framework.kernel.core.maths.Vector3;
import java.util.List;

public abstract interface PartLocalisator<P extends Part>
{
  public abstract P getPartFromId(int paramInt);
  
  public abstract List<P> getPartsInSightFromPoint(Point3 paramPoint3);
  
  public abstract P getMainPartInSightFromPosition(Point3 paramPoint3);
  
  public abstract P getMainPartInSightFromVector(Vector3 paramVector3);
  
  public abstract void reset();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\part\PartLocalisator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */