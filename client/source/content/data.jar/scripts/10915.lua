-- [A]
-- Sort: Flèche absorbante (ID: 169)
-- Classe: Cra
--


function displayEffect()
	particleId, time = Particle.addTweenParticleSystem(10960, startX, startY, startZ, destX, destY, destZ, 50, 0, 3)
	particleId2, time = Particle.addTweenParticleSystem(10917, startX, startY, startZ, destX, destY, destZ, 50, 0, 3)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 10)
	particleId3 = Particle.addParticleSystem(10915, destX, destY, destZ)
	particleId4 = Particle.addParticleSystem(10916, startX, startY, startZ)
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
Mobile.setMobileAnimation(startMobileId, "AnimFlecheAbsorbante")

-- Affichage de l'effet (dans 850 ms)
invoke(1300, 1, "displayEffect")
invoke(1400, 1, "executeAction")