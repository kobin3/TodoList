package repository

import javax.inject.{ Inject, Singleton }

import models.{Status, TodoItem}

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

/**
  * Created by kobin on 6/11/2017.
  */
@Singleton
class TodoListRepository {
    //Local repository
    private var gen_id: Int = 1;
    private val todoList = new ListBuffer[TodoItem]();

    //Init repository
    /*todoList += TodoItem(1, "title 1", "data 1", Status.PENDING);
    todoList += TodoItem(2, "title 2", "data 2", Status.PENDING);
    todoList += TodoItem(3, "title 3", "data 3", Status.PENDING);
    todoList += TodoItem(4, "title 4", "data 4", Status.PENDING);
    todoList += TodoItem(5, "title 5", "data 5", Status.PENDING);*/


  /*
   * Function get all items
   *
   */
  def list(): Iterable[TodoItem] = {
      todoList.toList
  }

  /*
   * Function get items by id
   *
   */
  def get(id: Int): Option[TodoItem] = {
      todoList.find(todo => todo.id == id)
  }

  /*
   * Function create items by given parameter
   *
   */
  def create(title: String, detail: String, status: String): TodoItem = {
    var data = TodoItem(gen_id, title, detail, status)
    todoList += data
    gen_id += 1;
    data
  }

  /*
   * Function edit items by given id
   *
   */
  def edit(id: Int, title: String, detail: String, status: String): Option[TodoItem] = {
    //Find item from list
    var item = todoList.find(todo => todo.id == id)

    //Check item exits
    if(!item.isEmpty) {
      var todoItem = item.get
      todoItem.title = title;
      todoItem.detail = detail;
      todoItem.status = status;
      Option(todoItem)
    }else {
      //Not found item
      Option(null)
    }
  }

  /*
   * Function update items status by given id
   *
   */
  def updateStatus(id: Int, status: String): Option[TodoItem] = {
    //Find item from list
    var item = todoList.find(todo => todo.id == id)

    //Check item exits
    if(!item.isEmpty) {
      var todoItem = item.get
      todoItem.status = status;
      Option(todoItem)
    }else {
      //Not found item
      Option(null)
    }
  }

  /*
   * Function delete items status by given id
   *
   */
  def delete(id: Int): Int = {
    //Find item from list
    var index = todoList.indexWhere(todo => todo.id == id)

    //Check item exits
    if(index != -1) {
      todoList.remove(index)
      1
    }else {
      //Not found item
      0
    }
  }
}
