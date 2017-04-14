import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

case class Item(name: String, id: Long)

/**
  * @author weil
  */
object WebServer {

    def main(args: Array[String]): Unit = {
        implicit val system = ActorSystem("my-system")
        implicit val materializer = ActorMaterializer()
        // needed for the future flatMap/onComplete in the end
        implicit val executionContext = system.dispatcher


      // formats for unmarshalling and marshalling
      implicit val itemFormat = jsonFormat2(Item)
      
        val route = get {
          pathPrefix("dict" /  Segment) {
            word => complete(new Item(word, 0L))
          }
        }

        val bindingFuture = Http().bindAndHandle(route, "127.0.0.1", 8020)

        println(s"Server online at http://127.0.0.1:8020/\nPress RETURN to stop...")
        StdIn.readLine() // let it run until user presses return
        bindingFuture
          .flatMap(_.unbind()) // trigger unbinding from the port
          .onComplete(_ => system.terminate()) // and shutdown when done
    }
}
