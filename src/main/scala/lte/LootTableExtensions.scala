package lte

import lte.core.loottables.conditions._
import lte.core.loottables.functions.Composed
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import net.minecraft.world.loot.condition.LootConditions
import net.minecraft.world.loot.function.LootFunctions

class LootTableExtensions extends ModInitializer {
	def onInitialize(): Unit = {
		println("Initializing LootTableExtensions")

		LootConditions.register(LootConditionFactory.create[Conjunction](new Identifier("lte","all_of")))
		LootConditions.register(LootConditionFactory.create[Disjunction](new Identifier("lte", "any_of")))
		LootConditions.register(LootConditionFactory.create[ExclusiveDisjunction](new Identifier("lte", "one_of")))
		LootConditions.register(LootConditionFactory.create[Equivalence](new Identifier("lte", "equivalence")))
		LootConditions.register(LootConditionFactory.create[Implication](new Identifier("lte", "implies")))
		LootConditions.register(LootConditionFactory.create[InvertedConjunction](new Identifier("lte", "not_all")))
		LootConditions.register(LootConditionFactory.create[InvertedDisjunction](new Identifier("lte", "none_of")))
		LootConditions.register(Always.factory)
		LootConditions.register(Never.factory)
		LootFunctions.register(Composed.factory)
	}
}