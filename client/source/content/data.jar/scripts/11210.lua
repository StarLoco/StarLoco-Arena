-- [Y]
-- Sort : Soufle Alcoolisé (ID : 131)
-- Classe : Pandawa
--
function displayEffect()
	Particle.addParticleSystem(11210, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 2)
	ScriptedAction.executeFirstAction(3, 37)
end

--
-- Exécution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancer de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimSouffle")

-- Affichage de l'effet
Sound.playSound(1106, true)
particleId = Particle.addParticleSystem(13000, startX, startY, startZ)
invoke(700, 1, "displayEffect")
invoke(750, 1, "executeAction")