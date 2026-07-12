-- arcs

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

destX, destY, destZ = Cast.getPosition()
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

Mobile.setMobileAnimation(startMobileId, "AnimEquipement-02-03-00")

invoke(900, 1, "waitBeforeNextAction")

function waitBeforeNextAction()
	--ScriptedAction.executeFirstAction(3, 1)
end
