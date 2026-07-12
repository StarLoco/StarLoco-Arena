-- [Y]
-- Sort : Folie sanguinaire (ID : 29)
-- Classe : Sacrieur
--
function displayEffect()
	Particle.addParticleSystem(11100, startX, startY, startZ)
	Particle.addParticleSystem(11100, destX, destY, destZ)
end

function hit()
	ScriptedAction.executeFirstAction(3, 10)
end

function playSpellSound()
	Sound.playSound(1102, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimSacriFoliesangui")

-- Animation du sort
playSpellSound()
invoke(220, 1, "displayEffect")
invoke(250, 1, "hit")


