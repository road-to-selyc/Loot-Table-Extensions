package lte.util.gson

import com.google.gson._
import play.api.libs.json._
import collection.JavaConverters._
import scala.util.Try


/*
  I really don't like Gson, and playJSON at least gives us some type safety.
  We'll always be working with objects, but we will prefer errors being thrown so
  that we have an idea of what went wrong.

  Primarily contains helper syntax to make working with the Factories as simple
  as defining OFormat instances
*/

object GsonUtilities {

	/*

		Json AST forms a tree, so we'll just recurse over it when we're
		not dealing with a primitive.  JsValue's convert quite nicely to
		JsonElements.

	*/
	private def convertToGson(json: JsValue): JsonElement = json match {
		case JsNull => JsonNull.INSTANCE
		case JsNumber(num) => new JsonPrimitive(num)
		case JsString(s) => new JsonPrimitive(s)
		case JsBoolean(b) => new JsonPrimitive(b)
		case JsArray(arr) => arr.foldLeft(new JsonArray())((acc, value) => {
			acc.add(convertToGson(value))
			acc
		})
		case JsObject(map) => map.foldLeft(new JsonObject)({case (acc, (key, value)) => {
			acc.add(key,convertToGson(value))
			acc
		}})
	}


	/* read and write methods that let us succintly work with JsonObject's */
	implicit class gsonObjectOps(jsonObj: JsonObject) {

		/* Reads will never mutate the object */
		def read[T: Reads]: T = {
			Json.parse(jsonObj.toString).as[T]
		}

		/* This WILL mutate the object that calls it, but the json structure should
				be sound, and playJson is well tested so this should be fine.
		 */
		def write[T: OWrites](data: T): Unit = {
			// once we have Gson object in play's format, we can simply merge the objects together
			val json = Json.toJsObject(data)
				.toGson
				.getAsJsonObject
			for {
				entry <- json.entrySet().asScala
			} jsonObj.add(entry.getKey, entry.getValue)
		}
	}

	/* useful for when we just want to convert JsValue to JsonElement */
	implicit class playToGsonOps(play: JsValue) {
		def toGson: JsonElement = convertToGson(play)
	}
}
