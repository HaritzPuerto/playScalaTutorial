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

  /**
   * Retrieve all users in the users variable
   * @return
   */
  def findAll = users.toList

  /**
   * Add user to the users variable.
   * @param user
   */
  def signUp(user: User): Unit = {
    users = users + user
  }
}