-- [A]
-- Sort: Fouet (ID: 57)
-- Classe: Osamoda
--

function displayEffect()
	Sound.playSound(207)
	particleId = Particle.addParticleSystem(10215, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 3)
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
particleId = Particle.addParticleSystem(13000, startX, startY, startZ)
Mobile.setMobileAnimation(startMobileId, "AnimFouet")

-- Affichage de l'effet
invoke(1200, 1, "displayEffect")
invoke(1200, 1, "executeAction")