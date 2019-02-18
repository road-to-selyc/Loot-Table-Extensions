package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext
import LootConditionLogicalConnective._
import com.google.gson.{JsonDeserializationContext, JsonObject, JsonSerializationContext}
import net.minecraft.util.Identifier

case class Always() extends LootCondition {
	def test(context: LootContext): Boolean = true
}

object Always {
	val factory = new LootCondition.Factory[Always](new Identifier("lte", "always"), classOf[Always]) {
		def toJson(var1: JsonObject, var2: Always, var3: JsonSerializationContext): Unit = ()
		def fromJson(var1: JsonObject, var2: JsonDeserializationContext): Always = Always()
	}
}