-- [Y]
-- Sort : Assaut (ID : 26)
-- Classe : Sacrieur
--

function displayEffect()
	particleId = Particle.addParticleSystem(11115, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 5)
end

function playSpellSound()
	Sound.playSound(1104, true)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimSacriAssaut01")

-- Affichage de l'effet
playSpellSound()
invoke(260, 1, "displayEffect")
invoke(280, 1, "executeAction")
