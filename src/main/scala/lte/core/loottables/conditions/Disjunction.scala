package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

case class Disjunction(disjuncts: List[LootCondition]) extends LootCondition {
	def test(context: LootContext): Boolean = disjuncts.exists(_.test(context))
}

object Disjunction {
	implicit val or = LootConditionLogicalConnective.connective(Disjunction.apply)(_.disjuncts)
}