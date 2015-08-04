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

}
