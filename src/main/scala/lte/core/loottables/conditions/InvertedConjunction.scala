package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

case class InvertedConjunction(conjuncts: List[LootCondition]) extends LootCondition {
	def test(context: LootContext): Boolean = !Conjunction(conjuncts).test(context)
}

object InvertedConjunction {
	implicit val nand = LootConditionLogicalConnective.connective(InvertedConjunction.apply)(_.conjuncts)
}
