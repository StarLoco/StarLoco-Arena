-- [R]
-- Sort : Pression (ID : 4)
-- Classe : Iop
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10815, destX, destY, destZ)
	-- Effet perte de Pv (Terre)
	ScriptedAction.executeFirstAction(3, 3)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimVitalite")
-- Affichage de l'effet
invoke(600, 1, "displayEffect");
-- Joue un son en stéréo
Sound.playSound(809, true);