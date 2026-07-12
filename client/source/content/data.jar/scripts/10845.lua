-- [R]
-- Sort : Epée Céleste (ID : 166)
-- Classe : Iop
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10845, destX, destY, destZ)
end

function hit()
ScriptedAction.executeFirstAction(3, 3)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91);
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId);
destX, destY, destZ = Cast.getPosition();
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimMutilation");
Mobile.setMobileLookAt(startMobileId, destX, destY, false);
-- Affichage de l'effet
invoke(0, 1, "displayEffect");
invoke(2000, 1, "hit");
-- Joue un son en stéréo
Sound.playSound(801, true);

