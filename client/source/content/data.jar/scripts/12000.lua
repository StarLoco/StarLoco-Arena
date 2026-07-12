-- [A]
-- Sort: Béco du Tofu (ID: 104)
-- Classe: Monster
--


function displayEffect()
	Particle.addParticleSystem(12000, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 5)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()
Particle.addParticleSystem(13000, startX, startY, startZ)

invoke(1000,1,"executeAction")
displayEffect()
Sound.playSound(12000, true)