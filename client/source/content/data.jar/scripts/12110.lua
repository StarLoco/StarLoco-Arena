-- [A]
-- Carte évènement: Vent repoussant (ID: 194)
--
--



function displayEffect()
	particleId = Particle.addParticleSystem(12110, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 37)
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimCarte")
particleId = Particle.addParticleSystem(12200, startX, startY, startZ)

invoke(700,1,"displayEffect")
invoke(1100, 1, "executeAction")
Sound.playSound(12110, true)