package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

case class ExclusiveDisjunction(disjuncts: List[LootCondition]) extends LootCondition {
	def test(context: LootContext): Boolean = disjuncts.foldLeft(false)((p, next) => {
		val q = next.test(context)
		(!p && q) || (p && !q)
	})
}

object ExclusiveDisjunction {
	implicit val xor = LootConditionLogicalConnective.connective(ExclusiveDisjunction.apply)(_.disjuncts)
}