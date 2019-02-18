package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

case class InvertedDisjunction(disjuncts: List[LootCondition]) extends LootCondition {
	def test(context: LootContext): Boolean = !Disjunction(disjuncts).test(context)
}

object InvertedDisjunction {
	implicit val nor = LootConditionLogicalConnective.connective(InvertedDisjunction.apply)(_.disjuncts)
}
