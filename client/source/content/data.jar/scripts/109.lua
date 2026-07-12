-- piege mortel

startMobileId = EffectArea.getTarget()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

Particle.addParticleSystem(13016, startX, startY, startZ)
