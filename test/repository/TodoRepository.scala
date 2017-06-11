package repository

import javax.inject.Inject

import models.{Status, TodoItem}
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext

/**
  * Created by kobin on 6/11/2017.
  */
class TodoRepository  extends PlaySpec with MockitoSugar {
  "TodoRepository#listTodoList" should {
    "Input and output data should same" in {
      //Create input
      var mockData = new ListBuffer[TodoItem]
      val repository = new TodoListRepository()
      mockData += repository.create("title 1","detail 1",Status.PENDING)
      mockData += repository.create("title 2","detail 2",Status.PENDING)

      //Check output
      val listItem  = repository.list();
      var i =0
      for ( item <- listItem ) {
        assert(mockData(i).id == item.id)
        assert(mockData(i).title == item.title)
        assert(mockData(i).status == item.status)
        assert(mockData(i).detail == item.detail)
        i += 1
      }
    }
  }

  "TodoRepository#get" should {
    "Check select data" in {
      //Create input
      var mockData = new ListBuffer[TodoItem]
      var repository = new TodoListRepository()
      mockData += repository.create("title 1","detail 1",Status.PENDING)
      mockData += repository.create("title 2","detail 2",Status.PENDING)

      //Check output
      for ( data <- mockData ) {
        var item = repository.get(data.id).get
        assert(data.id == item.id)
        assert(data.title == item.title)
        assert(data.status == item.status)
        assert(data.detail == item.detail)
      }
    }
  }

  "TodoRepository#create" should {
    "Created todo list should same as input" in {
      //Create input
      var mockData = new ListBuffer[TodoItem]
      var repository = new TodoListRepository()
      mockData += repository.create("title 1","detail 1",Status.PENDING)
      mockData += repository.create("title 2","detail 2",Status.PENDING)

      //Check output
      val listItem  = repository.list();
      var i =0
      for ( item <- listItem ) {
        assert(mockData(i).id == item.id)
        assert(mockData(i).title == item.title)
        assert(mockData(i).status == item.status)
        assert(mockData(i).detail == item.detail)
        i += 1
      }

      //Check count of items
      assert(mockData.length == i)
    }
  }

  "TodoRepository#update" should {
    "update todo list should same as input" in {
      //Create input
      val repository = new TodoListRepository()
      val mockData = repository.create("title 1","detail 1",Status.PENDING)

      //Edit item
      val editData = repository.edit(mockData.id, "title 2", "detail 2", Status.DONE).get

      //Check output
      assert(editData.id == mockData.id)
      assert(editData.title == "title 2")
      assert(editData.detail == "detail 2")
      assert(editData.status == Status.DONE)
    }
  }

  "TodoRepository#delete" should {
    "Delete todo list status should same as input" in {
      //Create input
      val repository = new TodoListRepository()
      val mockData = repository.create("title 1","detail 1",Status.PENDING)

      //Predate for call delete data
      var result1 = repository.delete(mockData.id)
      var result2 = repository.delete(mockData.id)

      //Check new data
      assert(result1 == 1)
      assert(result2 == 0)
    }
  }
}