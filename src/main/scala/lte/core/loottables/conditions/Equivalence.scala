package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

case class Equivalence(operands: List[LootCondition]) extends LootCondition {
	def test(context: LootContext): Boolean = operands.forall(_.test(context)) || operands.forall(!_.test(context))
}

object Equivalence {
	implicit val eq = LootConditionLogicalConnective.connective(Equivalence.apply)(_.operands)
}
