-- [A]
-- Sort : Armure d'Air et de feu (ID : 32)
-- Classe : Feca
--

function displayEffect()
	Particle.addParticleSystem(10115, startX, startY, startZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 29)
	ScriptedAction.executeFirstAction(3, 35)
end
	
--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimArmureFeuVEnt")

Sound.playSound(106, true)
invoke(1200,1,"executeAction")
invoke(200, 1, "displayEffect");

