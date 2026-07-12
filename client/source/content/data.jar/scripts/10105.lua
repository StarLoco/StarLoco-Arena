-- [A]
-- Sort : Armure de Terre et Eau (ID : 33)
-- Classe : Feca
--

function displayEffect()
	Particle.addParticleSystem(10002, startX, startY, startZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 33)
	ScriptedAction.executeFirstAction(3, 31)
end 

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimArmureTerreEau")

Sound.playSound(0104, true)
invoke(0, 1, "displayEffect");
invoke(1800, 1, "executeAction");
