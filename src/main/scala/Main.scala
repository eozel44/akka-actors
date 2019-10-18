import akka.actor.ActorSystem
import scala.concurrent.duration._

object Main extends App{
  /**
   * @author eren özel
   */

  implicit val system = ActorSystem("pipeline")
  implicit val ec = system.dispatcher
  val api = new Fraud(system,1000 millis)

}
