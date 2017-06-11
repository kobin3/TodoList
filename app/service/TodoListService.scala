package service

import javax.inject.{Inject, Singleton}

import models.TodoItem

import scala.collection.mutable.ListBuffer

/**
  * Created by kobin on 6/11/2017.
  */
trait TodoListService  {

  /*
 * Function get all items
 *
 */
  def findAllTodoItem(): Iterable[TodoItem]

  /*
  * Function get items by id
  *
  */
  def findTodoItemById(id: Int): Option[TodoItem]

  /*
   * Function create items by given parameter
   *
   */
  def create(title: String, detail: String, status: String): TodoItem

  /*
  * Function edit items by given id
  *
  */
  def edit(id: Int, title: String, detail: String, status: String): Option[TodoItem]

  /*
  * Function update items status by given id
  *
  */
  def updateStatus(id: Int, status: String): Option[TodoItem]

  /*
  * Function delete items status by given id
  *
  */
  def delete(id: Int): Int
}
