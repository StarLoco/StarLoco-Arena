-- [A]
-- Sort: Renvoi de sort (ID: 138)
-- Classe: Feca
--


function displayEffect()
	-- Ajout du système de particule sur la destination (id de fichier = 10000)
	particleId = Particle.addParticleSystem(10145, startX, startY, startZ)
	
	-- Appel le son d'explosion dans 900 ms
	invoke(0, 1, "playSpellSound")
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 88)
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimRenvoiDeSort")

-- Affichage de l'effet (dans 850 ms)
invoke(1600,1,"executeAction")
invoke(0, 1, "displayEffect");