-- [A]
-- Sort: Empoisonnement	(ID: 173)
-- Classe: Sadida
--


function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	particleId = Particle.addParticleSystem(11015, startX, startY, startZ)
	particleIdCible = Particle.addParticleSystem(11016, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 61)
end	
	
--
-- Exécution du script
--	
	
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du lanceur
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Recup des coordonnees dela cible

destX, destY, destZ = Cast.getPosition()
-- Animation du lanceur

Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimEmpoisonnement")

-- Animation du sort
invoke(400, 1, "displayEffect")
invoke(1000,1,"executeAction")
Sound.playSound(1004, true)
Sound.playSound(1003, true)
