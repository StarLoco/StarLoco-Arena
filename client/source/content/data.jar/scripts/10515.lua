-- [Y]
-- Sort : Devouement (ID : 46)
-- Classe : Xelor
--

function displayEffect()
	Particle.addParticleSystem(10515, destX, destY, destZ)
end

function executeAction(actionId)
	ScriptedAction.executeFirstAction(3, 13)
end

function playSpellSound()
	Sound.playSound(504, true)
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
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimContre")

-- Animation du sort
playSpellSound()
invoke(150, 1, "displayEffect")
invoke(900, 1, "executeAction")