package com.ankamagames.baseImpl.common.clientAndServer.game.part;

import com.ankamagames.framework.kernel.core.maths.Point3;
import com.ankamagames.framework.kernel.core.maths.Vector3;
import java.util.List;

public interface PartLocalisator<P extends Part> {
  P getPartFromId(int paramInt);
  
  List<P> getPartsInSightFromPoint(Point3 paramPoint3);
  
  P getMainPartInSightFromPosition(Point3 paramPoint3);
  
  P getMainPartInSightFromVector(Vector3 paramVector3);
  
  void reset();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\common\clientAndServer\game\part\PartLocalisator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */