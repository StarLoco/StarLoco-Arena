-- [A]
-- Sort: Flèche glacée
-- Classe: Cra
--


function displayEffect()
	-- Lancement d'une particule avec une trajectoire 
	particleId, time = Particle.addTweenParticleSystem(10960, startX, startY, startZ, destX, destY, destZ, 60, 1, 3)
	particleId2, time = Particle.addTweenParticleSystem(10932, startX, startY, startZ, destX, destY, destZ, 60, 0, 3)

	-- Appel de l'explosion une fois la particule arrivee
	invoke(time-50, 1, "explode")
end

function explode()
	-- dommage et gain caster
	ScriptedAction.executeFirstAction(3, 4)
	ScriptedAction.executeFirstAction(3, 14)
	ScriptedAction.executeFirstAction(3, 18)

	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	
	-- Ajout du système de particule sur la destination 
	particleId3 = Particle.addParticleSystem(10930, destX, destY, destZ)
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

-- Affichage de l'effet
invoke(1300, 1, "displayEffect");
particleId4 = Particle.addParticleSystem(10931, startX, startY, startZ)