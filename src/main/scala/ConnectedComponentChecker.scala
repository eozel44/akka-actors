import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor,OneForOneStrategy,Props}
import redis.clients.jedis.{Jedis,JedisPool,JedisPoolConfig}

case class Edge(sourceid:Long,targetid:Long)

object ConnectedComponentChecker {
def props = Props(new ConnectedComponentChecker)
def name ="ccc"
  case class CheckGraph(edge:Edge)
  def getJedis():Jedis={
    val config = new JedisPoolConfig
    config.setMaxIdle(200)
    config.setMaxTotal(200)
    config.setTestOnBorrow(false)
    config.setTestOnReturn(false)
    val host = "localhost"
    val pool = new JedisPool(config,host,6379,3000)
    pool.getResource
  }

  def hasRelation(jedis:Jedis,edge:Edge):Boolean={
    val cc1 = jedis.get(edge.sourceid.toString)
    val cc2 = jedis.get(edge.targetid.toString)
    cc1==cc2
  }
}

class ConnectedComponentChecker extends Actor{

  import ConnectedComponentChecker._
  var jedis:Jedis = _
  def receive ={
    case CheckGraph(edge)=>sender() ! hasRelation(jedis,edge)

  }

  override def preStart():Unit = {
    super.preStart()
    jedis = getJedis
    jedis.select(1)

  }

  override def postStop():Unit={
    super.postStop()
    jedis.close()
  }
  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false){
    case _ => Restart
  }
}