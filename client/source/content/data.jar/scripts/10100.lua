-- [A]
-- Sort : Faiblesse (ID : 35)
-- Classe : Feca
--

function displayEffect()
	particleId = Particle.addParticleSystem(10100, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 83)
end 

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimFaiblesse")

Sound.playSound(101, true)
invoke(600, 1, "displayEffect");
invoke(700, 1, "executeAction");