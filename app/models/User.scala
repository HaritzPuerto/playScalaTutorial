package models

/**
 * Created by haritz on 2/08/15.
 */
case class User(username: String, password: String)


object User {
  var users = Set(
    User("Josh", "Josh"),
    User("Dave", "Dave")
  )
}