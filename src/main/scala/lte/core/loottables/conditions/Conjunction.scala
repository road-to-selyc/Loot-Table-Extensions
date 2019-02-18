package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

case class Conjunction(conjuncts: List[LootCondition]) extends LootCondition {
	def test(context: LootContext): Boolean = conjuncts.forall(_.test(context))
}

object Conjunction {
	implicit val and = LootConditionLogicalConnective.connective(Conjunction.apply)(_.conjuncts)
}
