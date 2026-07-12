-- [A]
-- Sort: Altruisme (ID: 48)
-- Classe: Eniripsa
--



function displayEffectCible()
	particleId = Particle.addParticleSystem(10730, destX, destY, destZ)	
end

function displayEffectLanceur()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
end

function playSpellSound()
	Sound.playSound(709, true)
end

function executeAction ()
	ScriptedAction.executeAllAction(3, 69)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimSortMotEni02")

-- Affichage de l'effet (dans 850 ms)
invoke(450, 1, "displayEffectCible")
invoke(400, 1, "displayEffectLanceur")
invoke(1000,1,"executeAction")
