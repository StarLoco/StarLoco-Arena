-- [R]
-- Sort : Couper (ID : 164)
-- Classe : Iop
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	 particleId = Particle.addParticleSystem(10830, destX, destY, destZ)
	
	-- Appel le son d'explosion dans 900 ms
	invoke(200, 1, "playSpellSound")
end

function playSpellSound()
	-- Joue un son en stéréo (id de fichier = 2)
	Sound.playSound(802, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()
-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimCouper")
-- Affichage de l'effet
invoke(1000, 1, "displayEffect")