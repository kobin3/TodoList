package controllers

import play.api.mvc.{Action, Controller}

/**
  * Created by kobin on 6/11/2017.
  */
class Application extends Controller {

  def swagger = Action {
    request =>
      Ok(views.html.swagger())
  }

}