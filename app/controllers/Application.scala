package controllers

import javax.inject.Inject

import models.User
import play.api._
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index(userForm))
  }

  def list = Action { implicit request =>
    val users = User.findAll
    Ok(views.html.users.list(users))
  }

  private val userForm: Form[User] = Form(
    mapping(
      "username" -> nonEmptyText, //fields of the formulary.
      "password" -> nonEmptyText //nonEmptyText forces the field to not to be empty
    )(User.apply)(User.unapply)
  )

  def signUp = Action { implicit request =>
    val newUser = userForm.bindFromRequest()
    newUser.fold(
      hasErrors = { form =>
        Redirect(routes.Application.index())
      },
      success = { user =>
        User.signUp(user)
        Redirect(routes.Application.list())
      }
    )
  }

  def loginPage = Action {
    Ok(views.html.users.login(userForm))
  }

  def login = Action { implicit request =>
    val newUser = userForm.bindFromRequest()
    newUser.fold(
      hasErrors = { form =>
        Redirect(routes.Application.loginPage())
      },
      success = { user =>
        if(User.login(user)){
          Redirect(routes.Application.show(user.username))
        } else {
          Redirect(routes.Application.loginPage())
        }
      }
    )
  }


  def show(username: String) = Action { implicit request =>
    User.findByUsername(username).map { user =>
      Ok(views.html.users.user(user))
    }.getOrElse(NotFound)
  }

}
