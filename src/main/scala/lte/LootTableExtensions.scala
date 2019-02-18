package lte

import com.google.gson.{JsonDeserializationContext, JsonObject, JsonSerializationContext}
import lte.core.loottables.LootConditionFactory
import lte.core.loottables.conditions._
import lte.core.loottables.functions.Composed
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import net.minecraft.world.loot.condition.{LootCondition, LootConditions}
import net.minecraft.world.loot.context.LootContext
import net.minecraft.world.loot.function.LootFunctions
//import LootConditionFactory.formats

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

		case class True() extends LootCondition {
			def test(context: LootContext): Boolean = true
		}

		case class False() extends LootCondition {
			def test(Context: LootContext): Boolean = false
		}

		val trueFac = new LootCondition.Factory[True](new Identifier("lte", "always"), classOf[True]) {
			def toJson(var1: JsonObject, var2: True, var3: JsonSerializationContext): Unit = ()
			def fromJson(var1: JsonObject, var2: JsonDeserializationContext): True = True()
		}

		val falseFac = new LootCondition.Factory[False](new Identifier("lte","never"), classOf[False]) {
			def toJson(var1: JsonObject, var2: False, var3: JsonSerializationContext): Unit = ()
			def fromJson(var1: JsonObject, var2: JsonDeserializationContext): False = False()
		}

		LootConditions.register(trueFac)
		LootConditions.register(falseFac)

		LootFunctions.register(Composed.factory)

	}
}