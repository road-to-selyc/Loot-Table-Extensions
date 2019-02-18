package lte.util.json

import com.google.gson.{JsonDeserializationContext, JsonObject, JsonSerializationContext}
import net.minecraft.util.Identifier
import net.minecraft.world.loot.condition.{LootCondition, LootConditions}
import net.minecraft.world.loot.function.{LootFunction, LootFunctions}
import play.api.libs.json._


/*

	This object contains useful Reads/Writes instances for Minecraft types I'll be using

*/
object ModJsonInstances {

	implicit val readsId: Reads[Identifier] = __.read[String].map(new Identifier(_))
	implicit val writesId: OWrites[Identifier] = __.write[String].contramap(_.toString)

	def readsLootCondition(gson: JsonObject, context: JsonDeserializationContext): Reads[LootCondition] = {
		(__ \ "condition").read[Identifier].map(LootConditions.get(_).fromJson(gson, context))
	}

	def writesLootCondition(context: JsonSerializationContext): Writes[LootCondition] = lc => {
		Json.parse(context.serialize(lc).toString)
	}

	def readsLootFunction(gson: JsonObject, context: JsonDeserializationContext): Reads[LootFunction] = {
		(__ \ "function").read[Identifier].map(LootFunctions.get(_).fromJson(gson, context))
	}

	def writesLootFunction(context: JsonSerializationContext): Writes[LootFunction] = lf => {
		Json.parse(context.serialize(lf).toString)
	}

}
