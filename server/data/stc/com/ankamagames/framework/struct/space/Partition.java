package com.ankamagames.framework.struct.space;

public abstract interface Partition
{
  public abstract Partition getPartitionFromPoint(float paramFloat1, float paramFloat2, float paramFloat3);
  
  public abstract void removeAllPartitions();
  
  public abstract void addPartition(Partition paramPartition);
  
  public abstract void removePartition(Partition paramPartition);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\struct\space\Partition.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */