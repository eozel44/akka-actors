import akka.actor.{ActorRef,ActorSystem}
import akka.util.Timeout
import akka.pattern._
import scala.concurrent.{ExecutionContext,Future}

class Fraud(system: ActorSystem,timeout:Timeout) extends InternetFraudApi {
  implicit val requestTimeout = timeout
  implicit def executionContext = system.dispatcher
  def createInternetFraud = system.actorOf(InternetFraud.props,InternetFraud.name)

  def dummyFunction(s:String)=Future{
    Thread.sleep(100)
    s.length
  }

  def dummyFunction2(s:String)=Future{
    Thread.sleep(100)
    s
  }
  /*alpakka ile kafka consumer yazılmalı*/
  val resultGraph =checkGraph(Edge(1,2))
  val resultDummy = dummyFunction("hello")
  val resultDummy2 = dummyFunction2("hello")

  import FutureHelper._
  val result2 = resultGraph.map3(resultDummy)(resultDummy2){
    case(graph,dummy,dummy3) =>
      s"graph result $graph dummy result $dummy result $dummy3"
  }
}

trait InternetFraudApi{
  import InternetFraud._
  def createInternetFraud():ActorRef
  implicit def executionContext:ExecutionContext
  implicit def requestTimeout:Timeout

  lazy val internetFraud = createInternetFraud()

  def checkGraph(edge:Edge)={
    internetFraud.ask(CheckGraphInternetFraud(edge)).mapTo[Boolean]
  }
}