/**
  * @author weil
  */
object WebServer {

    def main(args: Array[String]): Unit = {
        implicit val system = ActorSystem("my-system")
        implicit val materializer = ActorMaterializer()
        // needed for the future flatMap/onComplete in the end
        implicit val executionContext = system.dispatcher

    }
}
