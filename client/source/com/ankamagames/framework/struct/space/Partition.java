package com.ankamagames.framework.struct.space;

public interface Partition {
  Partition getPartitionFromPoint(float paramFloat1, float paramFloat2, float paramFloat3);
  
  void removeAllPartitions();
  
  void addPartition(Partition paramPartition);
  
  void removePartition(Partition paramPartition);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\struct\space\Partition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */