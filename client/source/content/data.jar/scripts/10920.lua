-- [A]
-- Sort: Flèche chercheuse (ID: 3)
-- Classe: Cra
--


function displayEffect()
	particleId1, time = Particle.addTweenParticleSystem(10960, startX, startY, startZ, destX, destY, destZ, 60, 1, 3)
	particleId2, time = Particle.addTweenParticleSystem(10921, startX, startY, startZ, destX, destY, destZ, 50, 0, 3)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 3)
	particleId3 = Particle.addParticleSystem(10920, destX, destY, destZ)
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

-- Orientation du mobile lanceur (false = dans 4 directions uniquement)
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimFlecheChercheuse")

-- Affichage de l'effet
invoke(1700, 1, "displayEffect");
invoke(1800, 1, "executeAction")