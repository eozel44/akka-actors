import scala.concurrent.{ExecutionContext,Future}
object FutureHelper {
  implicit class RichFuture[T](val base:Future[T]){
    def map2[K,X](other:Future[K])(f:(T,K)=>X)(implicit executor:ExecutionContext):Future[X] = base.flatMap(t=>other.map(k=>f(t,k)))

    def map3[H,K,X](first:Future[H])(second:Future[K])(f:(T,H,K)=>X)(implicit executor:ExecutionContext):Future[X] =
      for{
        t<-base
        h<-first
        k<-second
      }yield f(t,h,k)

    def map33[H,K,X](first:Future[H])(second:Future[K])(f:(T,H,K)=>X)(implicit executor:ExecutionContext):Future[X] =
      base.flatMap{
        t=>
          first.flatMap{
            h=>second.map(k=>f(t,h,k))
          }
      }
  }

}
