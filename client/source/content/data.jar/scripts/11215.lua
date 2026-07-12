-- [Y]
-- Sort : Stabilisation (ID : 130)
-- Classe : Pandawa
--
function displayEffect()
	Particle.addParticleSystem(11215, startX, startY, startZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 94)
end

--
-- Exécution du script
--
ScriptedAction.executeFirstAction(3, 91)

startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimStabilisation")

-- Affichage de l'effet
Sound.playSound(1106, true)
particleId = Particle.addParticleSystem(13000, startX, startY, startZ)
invoke(1200, 1, "displayEffect")
invoke(1500, 1, "executeAction")
