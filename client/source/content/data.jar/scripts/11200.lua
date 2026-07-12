-- [Y]
-- Sort : Flasque Explosive (ID : 128)
-- Classe : Pandawa
--
function displayEffect()
	particleId, time = Particle.addTweenParticleSystem(11200, startX, startY, startZ, destX, destY, destZ, 60, 1, 3)
	Particle.addTweenParticleSystem(11201, startX, startY, startZ, destX, destY, destZ, 60, 1, 3)

	-- Appel de l'explosion une fois la particule arrivee
	invoke(time, 1, "explode")
end

function explode()
	-- dommage et gain caster
	ScriptedAction.executeFirstAction(3, 4)
	ScriptedAction.executeFirstAction(3, 16)
	ScriptedAction.executeFirstAction(3, 20)

	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	
	-- Ajout du système de particule sur la destination 
	Particle.addParticleSystem(11202, destX, destY, destZ)
	Particle.addParticleSystem(11210, destX, destY, destZ-1)
end

--
-- Exécution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancer de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimFlasque")

-- Animation du sort
Sound.playSound(11200, true)
invoke(1200, 1, "displayEffect");

