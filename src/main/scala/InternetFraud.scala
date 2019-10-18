import akka.actor.{Actor, Props}

object InternetFraud{
  def props = Props(new InternetFraud)
  val name = "InternetFraud"
  case class CheckGraphInternetFraud(edge:Edge)
}
class InternetFraud extends Actor{

  import InternetFraud._

  context.actorOf(ConnectedComponentChecker.props,ConnectedComponentChecker.name)

  def receive = {
    case CheckGraphInternetFraud(edge:Edge) =>
      context.actorSelection("akka://pipeline/user/InternetFraud/ccc")
      .forward(ConnectedComponentChecker.CheckGraph(edge))
  }
}
