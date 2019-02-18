package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

case class Implication(operands: List[LootCondition]) extends LootCondition {
	def test(context: LootContext): Boolean = {
		val initial = operands.init.forall(_.test(context))
		val last = operands.last.test(context)

		!initial || last
	}
}

object Implication {
	implicit val implication = LootConditionLogicalConnective.connective(Implication.apply)(_.operands)
}