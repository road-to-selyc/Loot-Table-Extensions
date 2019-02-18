package lte.core.loottables.functions

import com.google.gson.{JsonDeserializationContext, JsonObject, JsonSerializationContext}
import lte.core.loottables.conditions.LootConditionLogicalConnective
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext
import net.minecraft.world.loot.function.{ConditionalLootFunction, LootFunction}
import play.api.libs.json._
import lte.util.json.ModJsonInstances._
import lte.util.gson.GsonUtilities._
import play.api.libs.functional.syntax._

case class Composed(functions: List[LootFunction], conds: List[LootCondition]) extends ConditionalLootFunction(conds.toArray) {
	def process(itemStack: ItemStack, lootContext: LootContext): ItemStack = {
		val processor = functions.foldLeft((itemStack: ItemStack, context: LootContext) => itemStack) { (acc, nextFunc) =>
			(itemStack, context) => nextFunc(itemStack, context)
		}
		processor(itemStack, lootContext)
	}
}

object Composed {

	val factory = new ConditionalLootFunction.Factory[Composed](new Identifier("mlc", "composed"), classOf[Composed]) {
		def fromJson(json: JsonObject, context: JsonDeserializationContext, conditions: Array[LootCondition]): Composed = {
			implicit val readsLF = readsLootFunction(json, context)
			val functions = json.read[List[LootFunction]]
			Composed(functions, conditions.toList)
		}

		def toJson(json: JsonObject, composition: Composed, context: JsonSerializationContext): Unit = {
			implicit val writesLF = writesLootFunction(context)
			implicit val writesLC = writesLootCondition(context)
			implicit val writesC = ((__ \ "functions").write[List[LootFunction]]
				and (__ \ "conditions").write[List[LootCondition]])(unlift(Composed.unapply))

			json.write(composition)
		}
	}
}
