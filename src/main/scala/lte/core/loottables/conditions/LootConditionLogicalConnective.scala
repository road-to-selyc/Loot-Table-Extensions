package lte.core.loottables.conditions

import net.minecraft.world.loot.condition.LootCondition
import net.minecraft.world.loot.context.LootContext

import play.api.libs.json._
import play.api.libs.functional.syntax._

trait LootConditionLogicalConnective[T <: LootCondition] {
	def connective(terms: List[LootCondition]): T
	def terms(connective: T): List[LootCondition]
}

object LootConditionLogicalConnective {

	def apply[T <: LootCondition: LootConditionLogicalConnective] = implicitly[LootConditionLogicalConnective[T]]

	def connective[T <: LootCondition](lift: List[LootCondition] => T)(unlift: T => List[LootCondition]): LootConditionLogicalConnective[T] = new LootConditionLogicalConnective[T] {
		def connective(terms: List[LootCondition]): T = lift(terms)
		def terms(connective: T): List[LootCondition] = unlift(connective)
	}
}