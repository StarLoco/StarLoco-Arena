-- [Y]
-- Sort : Poussière temporelle (ID : 155)
-- Classe : Xelor
--

function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	if startMobileDirection == 1 then
	 particleFileId = 10513
	elseif startMobileDirection == 3 then
	 particleFileId = 10510
	elseif startMobileDirection == 5 then
	 particleFileId = 10511
	elseif startMobileDirection == 7 then
	 particleFileId = 10512
	end	 

	Particle.addParticleSystem(particleFileId, startX, startY, startZ)
end

function applyEffect()
	ScriptedAction.executeAllAction(3, 2)
end

function displayHit()
	particleId = Particle.addParticleSystem(10514, destX, destY, destZ)
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
Mobile.setMobileAnimation(startMobileId, "AnimPoussiereTemp")

-- Animation du sort
displayEffect()
Sound.playSound(507, true)
invoke(1400, 1, "displayHit")
invoke(1520, 1, "applyEffect")
