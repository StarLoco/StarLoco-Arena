-- [Y]
-- Sort : Punition (ID : 63)
-- Classe : Sacrieur
--

function displayEffect()
	Particle.addParticleSystem(11135, destX, destY, destZ)
end

function executeAction(actionId)
	ScriptedAction.executeFirstAction(3, actionId)
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
Mobile.setMobileAnimation(startMobileId, "AnimSacriPunition01")

-- Animation du sort
Sound.playSound(1106, true)
invoke(1000, 1, "displayEffect")
invoke(1000, 1, "executeAction", 4)
invoke(1250, 1, "executeAction", 5)



