--- [Y]
-- Sort : Momification (ID : 47)
-- Classe : Xelor
--

function displayEffectCible()
	Particle.addParticleSystem(10525, startX, startY, startZ)
end

function executeAction(actionId)
	ScriptedAction.executeFirstAction(3, actionId)
end

function playSpellSound()
	Sound.playSound(510, true)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancer de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimContre")

-- Animation du sort
invoke(10, 1, "displayEffectCible")
invoke(800, 1, "playSpellSound")
invoke(500, 1, "executeAction" , 60)
invoke(750, 1, "executeAction" , 18)
invoke(900, 1, "executeAction" , 86)
invoke(1100, 1, "executeAction", 87)