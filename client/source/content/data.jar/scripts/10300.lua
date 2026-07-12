-- [A]
-- Sort: Maladresse (ID: 37)
-- Classe: Enutrof
--


function displayEffect()
	particleId = Particle.addParticleSystem(10300, destX, destY, destZ)
end

function playSpellSound()
	Sound.playSound(302, true)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 18)
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
Mobile.setMobileAnimation(startMobileId, "AnimEnuMaladresse")

-- Animation du sort
invoke(2000,1,"executeAction")
invoke(2000, 1, "displayEffect");
invoke(1600, 1, "playSpellSound")
Sound.playSound(3036, true) 