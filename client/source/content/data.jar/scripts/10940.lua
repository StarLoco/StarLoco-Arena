-- [A]
-- Sort: Flèche d'immobilisation (ID: 170)
-- Classe: Cra
--


function displayEffect()
	particleId= Particle.addTweenParticleSystem(10960, startX, startY, startZ, destX, destY, destZ, 60, 1, 3)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 5)
	ScriptedAction.executeFirstAction(3, 65)
	
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	particleId3 = Particle.addParticleSystem(10940, destX, destY, destZ)
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
Mobile.setMobileAnimation(startMobileId, "AnimFlecheImmo")
particleId4 = Particle.addParticleSystem(10941, startX, startY, startZ)

-- Animation du sort
invoke(600, 1, "displayEffect");
invoke(650, 1, "executeAction")