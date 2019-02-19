package lte.core.loottables.conditions

import com.google.gson.{JsonDeserializationContext, JsonObject, JsonParser, JsonSerializationContext}
import lte.util.gson.GsonUtilities._
import lte.util.json.ModJsonInstances._
import net.minecraft.util.{Identifier, JsonHelper}
import net.minecraft.world.loot.condition.{LootCondition, LootConditions}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.reflect._

object LootConditionFactory {
	implicit def writes[T <: LootCondition : LootConditionLogicalConnective](implicit lc: Writes[LootCondition]): OWrites[T] = {
		(__ \ "terms").write[List[LootCondition]].contramap(LootConditionLogicalConnective[T].terms(_))
	}

	def reader[T: ClassTag](gson: JsonObject, context: JsonDeserializationContext, key: String): T = {
		JsonHelper.deserialize[T](gson, key, context, classTag.runtimeClass.asInstanceOf[Class[T]])
	}

	def create[T <: LootCondition : ClassTag : LootConditionLogicalConnective](id: Identifier) = new LootCondition.Factory[T](id, classTag.runtimeClass.asInstanceOf[Class[T]]) {
		def toJson(json: JsonObject, connective: T, context: JsonSerializationContext): Unit = {
			implicit lazy val writesLC = writesLootCondition(context)
			json.write(connective)
		}

		def fromJson(json: JsonObject, context: JsonDeserializationContext): T = {
			implicit val j: Reads[T] = for {
				terms <- Reads.pure(reader[Array[LootCondition]](json, context, "terms"))
			} yield LootConditionLogicalConnective[T].connective(terms.toList)
			json.read[T]
		}
	}
}

