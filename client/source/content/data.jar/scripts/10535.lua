--- [Y]
-- Sort : Vol du temps (ID : 156)
-- Classe : Xelor
--
function displayEffectCible()
	Particle.addParticleSystem(10535, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 85)
end

function playSpellSound()
	Sound.playSound(509, true)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancer de sort
startMobileId = Cast.getCaster()
destX, destY, destZ = Cast.getPosition()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimCadran")

-- Animation du sort
invoke(280, 1, "displayEffectCible")
invoke(280, 1, "playSpellSound")
invoke(2310, 1, "executeAction")