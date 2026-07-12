--batons

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

destX, destY, destZ = Cast.getPosition()
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

Mobile.setMobileAnimation(startMobileId, "AnimEquipement-02-08-00")

invoke(1600, 1, "waitBeforeNextAction")

function waitBeforeNextAction()
	--ScriptedAction.executeFirstAction(3, 1)
end
