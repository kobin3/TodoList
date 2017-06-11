package service

import javax.inject.Inject

import models.{Status, TodoItem}
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import repository.TodoListRepository
import org.mockito.Mockito._

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext

/**
  * Created by kobin on 6/11/2017.
  */
//Create test service
class Test_TodoListService(todoListRepository: TodoListRepository) extends TodoListServiceImpl(todoListRepository)

class TodoService  extends PlaySpec with MockitoSugar {

  "TodoListService#listTodoList" should {
    "Input and output data should same" in {

      //Dummy data
      var mockData = ListBuffer[TodoItem](
        TodoItem(1, "test1", "test1", "pending"),
        TodoItem(2, "test2", "test2", "pending")
      )

      //Init
      val userRepository = mock[TodoListRepository]
      when(userRepository.list()) thenReturn mockData

      //Create mock service
      val test_TodoListService = new Test_TodoListService(userRepository)

      //Check output
      var listItem  = test_TodoListService.findAllTodoItem();
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

  "TodoListService#get" should {
    "Check select data" in {

      //Dummy data
      var mockData = ListBuffer[TodoItem](
        TodoItem(1, "test1", "test1", "pending"),
        TodoItem(2, "test2", "test2", "pending")
      )

      //Init
      val userRepository = mock[TodoListRepository]
      when(userRepository.get(1)) thenReturn Option(mockData(0))
      when(userRepository.get(2)) thenReturn Option(mockData(1))

      //Create mock service
      val test_TodoListService = new Test_TodoListService(userRepository)

      //Check output
      for ( data <- mockData) {
        var todoListItem = test_TodoListService.findTodoItemById(data.id).get
        assert(data.id == todoListItem.id)
        assert(data.title == todoListItem.title)
        assert(data.status == todoListItem.status)
        assert(data.detail == todoListItem.detail)
      }
    }
  }

  "TodoListService#create" should {
    "Created todo list should same as input" in {

      //Init
      val userRepository = mock[TodoListRepository]
      when(userRepository.create("titleA", "dataB", Status.DONE)) thenReturn TodoItem(6, "titleA", "dataB", Status.DONE)

      //Create mock service
      val test_TodoListService = new Test_TodoListService(userRepository)

      //Check output\
      var todoListItem = test_TodoListService.create("titleA", "dataB", Status.DONE)
      //assert(todoListItem.id == 6)
      assert(todoListItem.title == "titleA")
      assert(todoListItem.status == Status.DONE)
      assert(todoListItem.detail == "dataB")
    }
  }

    "TodoListService#update" should {
      "update todo list should same as input" in {

        //Init
        val userRepository = mock[TodoListRepository]
        when(userRepository.create("titleA", "dataB", Status.DONE)) thenReturn TodoItem(6, "titleA", "dataB", Status.DONE)
        when(userRepository.edit(6, "new title", "new detail", Status.PENDING)) thenReturn Option(TodoItem(6, "new title", "new detail", Status.PENDING))

        //Create mock service
        val test_TodoListService = new Test_TodoListService(userRepository)

        //Create new item
        var todoListItem = test_TodoListService.create("titleA", "dataB", Status.DONE)
        assert(todoListItem.title == "titleA")
        assert(todoListItem.status == Status.DONE)
        assert(todoListItem.detail == "dataB")

        //Predate for call edit data
        var newId = todoListItem.id
        var afterEdit = test_TodoListService.edit(6, "new title", "new detail", Status.PENDING).get

        //Check new data
        assert(afterEdit.title == "new title")
        assert(afterEdit.status == Status.PENDING)
        assert(afterEdit.detail == "new detail")
      }
    }


    "TodoListService#updateStatus" should {
      "Edit todo list status should same as input" in {

        //Init
        val userRepository = mock[TodoListRepository]
        when(userRepository.create("titleA", "dataB", Status.DONE)) thenReturn TodoItem(6, "titleA", "dataB", Status.DONE)
        when(userRepository.updateStatus(6, Status.PENDING)) thenReturn Option(TodoItem(6, "titleA", "dataB", Status.PENDING))

        //Create mock service
        val test_TodoListService = new Test_TodoListService(userRepository)

        //Create new item
        var todoListItem = test_TodoListService.create("titleA", "dataB", Status.DONE)
        assert(todoListItem.title == "titleA")
        assert(todoListItem.status == Status.DONE)
        assert(todoListItem.detail == "dataB")

        //Predate for call edit data
        var newId = todoListItem.id
        var afterEdit = test_TodoListService.updateStatus(6,  Status.PENDING).get

        //Check new data
        assert(afterEdit.title == "titleA")
        assert(afterEdit.status == Status.PENDING)
        assert(afterEdit.detail == "dataB")
      }
    }

  "TodoListService#delete" should {
    "Delete todo list status should same as input" in {

      //Init
      val userRepository = mock[TodoListRepository]
      when(userRepository.create("titleA", "dataB", Status.DONE)) thenReturn TodoItem(6, "titleA", "dataB", Status.DONE)
      when(userRepository.delete(6)) thenReturn 1
      when(userRepository.delete(8)) thenReturn 0

      //Create mock service
      val test_TodoListService = new Test_TodoListService(userRepository)

      //Create new item
      var todoListItem = test_TodoListService.create("titleA", "dataB", Status.DONE)
      assert(todoListItem.title == "titleA")
      assert(todoListItem.status == Status.DONE)
      assert(todoListItem.detail == "dataB")

      //Predate for call edit data
      var newId = todoListItem.id
      var result1 = test_TodoListService.delete(6)
      var result2 = test_TodoListService.delete(8)

      //Check new data
      assert(result1 == 1)
      assert(result2 == 0)
    }
  }
}
