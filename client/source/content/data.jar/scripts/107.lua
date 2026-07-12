-- Bonus PA

startMobileId = EffectArea.getTarget()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

Particle.addParticleSystem(13014, startX, startY, startZ)
