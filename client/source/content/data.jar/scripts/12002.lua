-- [A]
-- Sort: Morsure (ID: 105)
-- Classe: Monster
--


function displayEffect()
	Particle.addParticleSystem(12001, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 3)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()
Particle.addParticleSystem(13000, startX, startY, startZ)

invoke(1200,1,"executeAction")
displayEffect()
Sound.playSound(12001, true)