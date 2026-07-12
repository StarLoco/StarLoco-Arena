-- [A]
-- Sort : Immunité (ID : 34)
-- Classe : Feca
--

function displayEffect()
	Particle.addParticleSystem(10110, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 80)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
destX, destY, destZ = Cast.getPosition()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimImunitee")

Sound.playSound(100, true)
invoke(1600,1,"executeAction")
invoke(0, 1, "displayEffect");