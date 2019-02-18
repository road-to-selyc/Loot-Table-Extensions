package lte.core.loottables

import com.google.gson.{JsonDeserializationContext, JsonObject, JsonSerializationContext}
import lte.core.loottables.conditions.LootConditionLogicalConnective
import net.minecraft.util.Identifier
import net.minecraft.world.loot.condition.LootCondition

import scala.reflect._
import lte.util.json.ModJsonInstances._
import lte.util.gson.GsonUtilities._
import net.minecraft.world.loot.function.{ConditionalLootFunction, LootFunction}
import play.api.libs.json._

object LootConditionFactory {
	implicit def reads[T <: LootCondition : LootConditionLogicalConnective](implicit lc: Reads[LootCondition]): Reads[T] = {
		(__ \ "terms").read[List[LootCondition]].map(LootConditionLogicalConnective[T].connective(_))
	}

	implicit def writes[T <: LootCondition : LootConditionLogicalConnective](implicit lc: Writes[LootCondition]): OWrites[T] = {
		(__ \ "terms").write[List[LootCondition]].contramap(LootConditionLogicalConnective[T].terms(_))
	}

	def create[T <: LootCondition : ClassTag : LootConditionLogicalConnective](id: Identifier) = new LootCondition.Factory[T](id, classTag.runtimeClass.asInstanceOf[Class[T]]) {
		def toJson(json: JsonObject, connective: T, context: JsonSerializationContext): Unit = {
			implicit val writesLC = writesLootCondition(context)
			json.write(connective)
		}

		def fromJson(json: JsonObject, context: JsonDeserializationContext): T = {
			implicit val readsLC = readsLootCondition(json, context)
			json.read[T]
		}
	}
}

