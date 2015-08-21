import java.io._

import org.apache.http.{HttpEntity, HttpResponse}
import org.apache.http.client.{ClientProtocolException, HttpClient}
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

import spray.json._

case class Weather(
  coord: Coordinates,
  weather: List[WeatherCondition],
  main: WeatherDetails
)

case class Coordinates(
  lon: Double,
  lat: Double
)

case class WeatherCondition(
  id: Int,
  main: String,
  description: String,
  icon: String
)

case class WeatherDetails(
  temp: Double,
  pressure: Int,
  humidity: Int,
  temp_min: Double,
  temp_max: Double
)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val coordinatesFormat = jsonFormat2(Coordinates)
  implicit val weatherConditionFormat = jsonFormat4(WeatherCondition)
  implicit val weatherDetailsFormat = jsonFormat5(WeatherDetails)
  implicit val weatherFormat = jsonFormat3(Weather)
}

import MyJsonProtocol._
  
object Program {
  def main(args: Array[String]) {
    // Current weather conditions for Indianapolis, IN, USA -- See http://openweathermap.org/current
    val jsonString = getRestContent("http://api.openweathermap.org/data/2.5/weather?id=4259418")

    val jsonAst = jsonString.parseJson
    val weather = jsonAst.convertTo[Weather]

    val currentTemp = kelvinToFarenheit(weather.main.temp)
    val currentConditions = weather.weather.map(_.description).mkString("\n")

    println(f"The temperature in Indianapolis is currently $currentTemp%.1f°F.")
    println(s"The current conditions are:\n$currentConditions")
  }

  def getRestContent(url: String): String = {
    val httpClient = new DefaultHttpClient()
    val httpResponse = httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()
    return content
  }

  def kelvinToFarenheit(temp_k: Double): Double = ((temp_k - 273.15)*1.8)+32
}

