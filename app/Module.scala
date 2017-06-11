import javax.inject._

import com.fasterxml.jackson.module.scala.ScalaModule
import com.google.inject.AbstractModule
import repository.TodoListRepository
import play.api.{Configuration, Environment}
import service.{TodoListService, TodoListServiceImpl}

/**
  * Created by kobin on 6/11/2017.
  */
class Module(environment: Environment, configuration: Configuration)
  extends AbstractModule {

  def configure() = {
    bind(classOf[TodoListService]).to(classOf[TodoListServiceImpl])
  }
}