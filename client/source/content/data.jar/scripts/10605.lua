-- [R]
-- Sort : Trèfle (ID : 14)
-- Classe : Ecaflip
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10605, startX, startY, startZ)
	-- Appel le son d'explosion
	invoke(100, 1, "playSpell")
end

function playSpell()
	-- gain de CC
	ScriptedAction.executeFirstAction(3, 70);
	-- Joue un son en stéréo
	Sound.playSound(600, true);
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91);
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId);
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast");
-- Affichage de l'effet
invoke(750, 1, "displayEffect");

