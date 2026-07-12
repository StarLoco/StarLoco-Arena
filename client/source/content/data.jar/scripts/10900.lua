-- [A]--> pas de son
-- Sort: Oeil de lynx (ID: 20)
-- Classe: Cra
--


function displayEffect()
	particleId = Particle.addParticleSystem(10900, startX, startY, startZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 72)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimOeilDeLynx")

-- Affichage de l'effet
invoke(150, 1, "displayEffect")
invoke(1200,1,"executeAction")