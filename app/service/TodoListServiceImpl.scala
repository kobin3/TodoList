package service

import javax.inject.Inject

import models.TodoItem
import repository.TodoListRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by kobin on 6/11/2017.
  */
class TodoListServiceImpl  @Inject()(todoListRepository: TodoListRepository)extends TodoListService  {

  /*
 * Function get all items
 *
 */
  override def findAllTodoItem(): Iterable[TodoItem] = {
      todoListRepository.list
  }

  /*
  * Function get items by id
  *
  */
  override def findTodoItemById(id: Int): Option[TodoItem] = {
    todoListRepository.get(id)
  }

  /*
   * Function create items by given parameter
   *
   */
  override def create(title: String, detail: String, status: String): TodoItem = {
    todoListRepository.create(title, detail, status)
  }

  /*
  * Function edit items by given id
  *
  */
  override def edit(id: Int, title: String, detail: String, status: String): Option[TodoItem] = {
    todoListRepository.edit(id, title, detail, status)
  }

  /*
  * Function update items status by given id
  *
  */
  override def updateStatus(id: Int, status: String): Option[TodoItem] = {
    todoListRepository.updateStatus(id, status)
  }

  /*
  * Function delete items status by given id
  *
  */
  override def delete(id: Int): Int = {
    todoListRepository.delete(id)
  }


}
