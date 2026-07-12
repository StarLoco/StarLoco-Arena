-- [A]
-- Sort: maladresse de masse (ID: 149)
-- Classe: Enutrof
--


function displayEffect()
	particleId = Particle.addParticleSystem(10310, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeAllAction(3, 18)
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
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimEnuCorruption")

-- Animation du sort
invoke(1000,1,"executeAction")
invoke(500, 1, "displayEffect")
Sound.playSound(3030, true)
Sound.playSound(302, true)