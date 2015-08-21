import java.io._

import org.apache.http.{HttpEntity, HttpResponse}
import org.apache.http.client.{ClientProtocolException, HttpClient}
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

import spray.json._

case class WeatherResponse(
  coord: Coordinates,
  main: WeatherDetails
)

case class Coordinates(
  lon: Double,
  lat: Double
)

case class WeatherDetails(
  temp: Double,
  pressure: Int,
  humidity: Int,
  temp_min: Double,
  temp_max: Double
)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val coordinateResponseFormat = jsonFormat2(Coordinates)
  implicit val weatherDetailsResponseFormat = jsonFormat5(WeatherDetails)
  implicit val weatherResponseFormat = jsonFormat2(WeatherResponse)
}

import MyJsonProtocol._
  
object Program {
  def main(args: Array[String]) {
    // Current weather conditions for Indianapolis, IN, USA -- See http://openweathermap.org/current
    val jsonString = getRestContent("http://api.openweathermap.org/data/2.5/weather?id=4259418")

    val jsonAst = jsonString.parseJson
    val weather = jsonAst.convertTo[WeatherResponse]

    val current = kelvinToFarenheit(weather.main.temp)
    val low = kelvinToFarenheit(weather.main.temp_min)
    val high = kelvinToFarenheit(weather.main.temp_max)

    println(s"The temperature in Indianapolis is currently $current°F.")
    println(s"The high for today is $high°F. The low for today is $low°F.")
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

