package lte.core.loottables.conditions

import com.google.gson.{JsonDeserializationContext, JsonObject, JsonSerializationContext}
import net.minecraft.util.Identifier
import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

case class Never() extends LootCondition {
	def test(context: LootContext): Boolean = false
}

object Never {
	val factory = new LootCondition.Factory[Never](new Identifier("lte", "never"), classOf[Never]) {
		def toJson(var1: JsonObject, var2: Never, var3: JsonSerializationContext): Unit = ()
		def fromJson(var1: JsonObject, var2: JsonDeserializationContext): Never = Never()
	}
}