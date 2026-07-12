ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimAttaque")

-- Affichage de l'effet (dans 850 ms)
invoke(400, 1, "displayEffectCible");
invoke(800, 1, "displayEffectCible");
invoke(1200, 1, "displayEffectCible");

-- Fonctions

function displayEffectCible()
	-- Ajout du système de particule sur la destination (id de fichier = 10000)
	particleId = Particle.addParticleSystem(12001, destX, destY, destZ)
end

function playSpellSound()
	-- Joue un son en stéréo (id de fichier = 2)
	Sound.playSound(12001, true)
end
playSpellSound()