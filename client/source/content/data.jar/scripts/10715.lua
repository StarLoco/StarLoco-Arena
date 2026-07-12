-- [A]
-- Sort: Mot Stimulant (ID: 23)
-- Classe: Eniripsa
--


function displayEffect()
	particleId = Particle.addParticleSystem(10715, destX, destY, destZ+2)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 13)
end

function playSpellSound()
	Sound.playSound(701, true)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimSortMotEni01")

-- Animation du sort
invoke(400, 1, "displayEffect")
invoke(1500, 1, "executeAction")
invoke(200, 1, "playSpellSound")