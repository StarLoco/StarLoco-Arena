-- [A]
-- Sort : Bulle (ID : 137)
-- Classe : Feca
--

function displayEffect()
	particleIdLanceur = Particle.addParticleSystem(10125, startX, startY, startZ)
end

function displayEffectBulle()
	-- Lancement d'une particule avec une trajectoire
	invoke(1, 1, "removeParticle", particleIdLanceur)
	particleIdBulle, time = Particle.addTweenParticleSystem(10150, startX, startY, startZ+3, destX, destY, destZ, 20, 0, 2)

	-- Appel de l'explosion une fois la particule arrivee
	invoke(time, 1, "explode")
	invoke(time+100, 1, "removeParticle", particleIdBulle);
end

function explode()

	ScriptedAction.executeFirstAction(3, 4)
	startMobileDirection = Mobile.getMobileDirection(startMobileId)

	if startMobileDirection == 1 then
	 particleFileId = 10127
	elseif startMobileDirection == 3 then
	 particleFileId = 10126
	elseif startMobileDirection == 5 then
	 particleFileId = 10128
	elseif startMobileDirection == 7 then
	 particleFileId = 10129
	end
	
	-- Ajout du système de particule sur la destination
	particleId2 = Particle.addParticleSystem(particleFileId, destX, destY, destZ)
end

function removeParticle(particleId)
	Particle.removeParticleSystem(particleId)	
end


--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimBulle")

Sound.playSound(102)
displayEffect()
invoke (1800, 1, "displayEffectBulle")


