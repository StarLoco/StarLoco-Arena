--- [Y]
-- Sort : Aiguille (ID : 43)
-- Classe : Xelor
--

function executeAction(actionId)
	ScriptedAction.executeFirstAction(3, actionId)
end

function displayMainAiguille(offsetZ, angle, speed)
	particleId, time = Particle.addTweenParticleSystem(10546, startX, startY, startZ+offsetZ, destX, destY, destZ+offsetZ, angle, 1, speed)	
	invoke(time, 1, "removeAiguille", particleId);	
	
	invoke(time+50, 1, "explode")
	invoke(time+50, 1, "executeAction", 4)
	invoke(time+200, 1, "executeAction", 16)
	invoke(time+350, 1, "executeAction", 20)	
end

function displayAiguille(offsetZ, angle, speed)
	particleId, time = Particle.addTweenParticleSystem(10546, startX, startY, startZ+offsetZ, destX, destY, destZ+offsetZ, angle, 1, speed)	
	invoke(time, 1, "removeAiguille", particleId);	
end

function removeAiguille(particleId)
	Particle.removeParticleSystem(particleId)	
end

function explode()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	particleId3 = Particle.addParticleSystem(10547, destX, destY, destZ)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancer de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimAiguille")

-- Animation du sort
Particle.addParticleSystem(10545, startX, startY, startZ)

invoke(1800, 1, "displayAiguille", 1, 35, 3)
invoke(1800, 1, "displayAiguille", 3, 30, 3)
invoke(1800, 1, "displayAiguille", 1, 40, 3)
invoke(1800, 1, "displayAiguille", 3, 45, 3)
invoke(1800, 1, "displayAiguille", 3, 50, 3)
invoke(1800, 1, "displayAiguille", 3, 55, 3)
invoke(1800, 1, "displayMainAiguille", 3, 60, 3)
invoke(1800, 1, "displayAiguille", 1, 65, 4)
invoke(1800, 1, "displayAiguille", 1, 70, 4)


