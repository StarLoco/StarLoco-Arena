--- [Y]
-- Sort : Contre (ID : 150)
-- Classe : Xelor
--
function displayEffect()
	Particle.addParticleSystem(10530, startX, startY, startZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 89)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancer de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimContre")

-- Animation du sort
displayEffect()
Sound.playSound(503, true)
invoke(800, 0, "executeAction")

