-- [R]
-- Sort : Pétrification aléatoire (ID : 160)
-- Classe : Ecaflip
--
function explode()
	-- Ajout du système de particule sur la destination
	Particle.addParticleSystem(10630, destX, destY, destZ)	
	-- Appel le son d'explosion
	Sound.playSound(603, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91);
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId);
-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition();
-- Orientation du mobile lanceur (false = dans 4 directions uniquement)
Mobile.setMobileLookAt(startMobileId, destX, destY, false);
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast");
-- Affichage de l'effet
invoke(950, 1, "explode");