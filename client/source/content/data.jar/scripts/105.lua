-- Bonus au soin

startMobileId = EffectArea.getTarget()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

Particle.addParticleSystem(13013, startX, startY, startZ)
