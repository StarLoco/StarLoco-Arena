-- [A]
-- Carte évènement: Eclair (ID: 187)
--
--


function displayEffect()
	Particle.addParticleSystem(12100, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeAllAction(3, 5)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()
Mobile.setMobileAnimation(startMobileId, "AnimCarte")
Particle.addParticleSystem(12200, startX, startY, startZ)

invoke(700,1,"displayEffect")
invoke(1200,1,"executeAction")
Sound.playSound(12100, true)