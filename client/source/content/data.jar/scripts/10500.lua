-- [Y]
-- Sort : Ralentissement (ID : 42)
-- Classe : Xelor
--

function executeAction()
	ScriptedAction.executeFirstAction(3, 14)
end

function displayEffect()
	Particle.addParticleSystem(10535, destX, destY, destZ)
end

function playSpellSound()
	Sound.playSound(511, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimRalentissement")

-- Animation du sort
displayEffect()
invoke(1000, 1, "playSpellSound")
invoke(1300, 1, "executeAction")

