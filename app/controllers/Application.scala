package controllers

import models.User
import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def list = Action { implicit request =>
    val users = User.findAll
    Ok(views.html.users.list(users))
  }

}
