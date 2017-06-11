package controllers


import javax.inject.Inject

import io.swagger.annotations._
import models.{Create_TodoItem, TodoItem}
import play.api.libs.json._
import play.api.mvc.Action
import service.TodoListServiceImpl
import play.api.libs.functional.syntax._

import scala.concurrent.ExecutionContext

/**
  * Created by kobin on 6/11/2017.
  */
@Api(value = "/todoList", description = "Operations about todo list")
class TodoListController @Inject()(todoListService: TodoListServiceImpl) extends BaseApiController  {


  /*
 * Function for find all todo item
 *
 */
  @ApiOperation(nickname = "findAllTodoItems",
    value = "List all todo item", response = classOf[models.TodoItem], httpMethod = "GET", responseContainer = "List")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Success full"),
    new ApiResponse(code = 404, message = "No Todo item found")))
  def findAllTodoItems() = Action { implicit request =>
    val output: Iterable[TodoItem] =  todoListService.findAllTodoItem()//storeData.orders.toList.asJava
    JsonResponse(output)
  }

  /*
 * Function for get todo item
 *
 */
  @ApiOperation(nickname = "findTodoItemsById",
    value = "Get todo item detail by given id", response = classOf[models.TodoItem], httpMethod = "GET")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Success full"),
    new ApiResponse(code = 400, message = "Bad request"),
    new ApiResponse(code = 404, message = "No Todo item found")))
  def findTodoItemsById(@ApiParam(value = "ID of todo item") id: Int) = Action { implicit request =>
     todoListService.findTodoItemById(id) match {
       case Some(todoItem) => JsonResponse(todoItem)
       case _ => JsonResponse(new models.ApiResponse(404, "Pet not found"),404)
     }
  }

  /*
 * Function create todo items by given parameter
 *
 */
  @ApiOperation(nickname = "createTodoItem",
    value = "Create todo item", response = classOf[models.Create_TodoItem], httpMethod = "POST")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(value = "Create new todo item", required = true, dataType = "models.Create_TodoItem", paramType = "body")))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Create todo item Success"),
    new ApiResponse(code = 400, message = "Bad request")))
    def createTodoItem() = Action { implicit request =>
      request.body.asJson match {
        case Some(e) => {


          //Read Json
          val result: JsResult[Create_TodoItem] = Json.parse(e.toString).validate[Create_TodoItem]
          result match {
            case resultSuccess: JsSuccess[Create_TodoItem] => {
              val todoItem: Create_TodoItem = resultSuccess.get
              JsonResponse(todoListService.create(todoItem.title, todoItem.detail, todoItem.status))
            }
            case e: JsError => JsonResponse(new models.ApiResponse(400, "Bad request"),400)
          }
        }
      }
  }

  /*
 * Function edit items by given parameter
 *
 */
  @ApiOperation(nickname = "editTodoItem",
    value = "Edit todo item", response = classOf[models.TodoItem], httpMethod = "PUT")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(value = "Edit todo item ", required = true, dataType = "models.TodoItem", paramType = "body")))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Edit todo item Success"),
    new ApiResponse(code = 400, message = "Bad request"),
    new ApiResponse(code = 404, message = "No Todo item found")))
  def editTodoItem() = Action { implicit request =>
    request.body.asJson match {
      case Some(e) => {

        //Read Json
        val result: JsResult[TodoItem] = Json.parse(e.toString).validate[TodoItem]
        result match {
          case resultSuccess: JsSuccess[TodoItem] => {
            val todoItem: TodoItem = resultSuccess.get
            val editItem = todoListService.edit(todoItem.id,todoItem.title, todoItem.detail, todoItem.status)
            if(!editItem.isEmpty) {
              JsonResponse(editItem.get)
            }else {
              JsonResponse(new models.ApiResponse(404, "No Todo item found"),404)
            }
          }
          case e: JsError => JsonResponse(new models.ApiResponse(400, "Bad request"),400)
        }
      }
    }
  }

  /*
 * Function edit items status by given id
 *
 */
  @ApiOperation(nickname = "updateStatus",
    value = "Upstate todo item status", response = classOf[models.TodoItem], httpMethod = "PUT")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Edit todo item Success"),
    new ApiResponse(code = 400, message = "Bad request"),
    new ApiResponse(code = 404, message = "No Todo item found")))
  def updateStatus(@ApiParam(value = "ID of todo item") id: Int,
                   @ApiParam(value = "status of todo item") status: String) = Action { implicit request =>

    //Check input string
    if (status != models.Status.DONE && status != models.Status.PENDING) {
      JsonResponse(new models.ApiResponse(400, "Bad request"), 400)
    } else {
      //Check todo item is exits
      val editItem = todoListService.updateStatus(id, status)
      if (!editItem.isEmpty) {
        JsonResponse(editItem.get)
      } else {
        JsonResponse(new models.ApiResponse(404, "No Todo item found"), 404)
      }
    }
  }

  /*
 * Function delete items status by given id
 *
 */
  @ApiOperation(nickname = "deleteTodoItem",
    value = "Delete todo item status", response = classOf[models.TodoItem], httpMethod = "DELETE")
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Detele todo item Success"),
    new ApiResponse(code = 400, message = "Bad request"),
    new ApiResponse(code = 404, message = "No Todo item found")))
  def deleteTodoItem(@ApiParam(value = "ID of todo item") id: Int) = Action { implicit request =>
    //Delete todo item
    val result = todoListService.delete(id)
    if (result == 1) {
      JsonResponse(new models.ApiResponse(200, "Detele todo item Success"), 200)
    } else {
      JsonResponse(new models.ApiResponse(404, "No Todo item found"), 404)
    }
  }

  //Create reader for Create_TodoItem
  implicit val todoItemCreateRead: Reads[Create_TodoItem] = (
    (JsPath \ "title").read[String] and
      (JsPath \ "detail").read[String].orElse(Reads.pure("")) and
      (JsPath \ "status").read[String]
    )(Create_TodoItem.apply _)

  //Create reader for Create_TodoItem
  implicit val todoItemRead: Reads[TodoItem] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "title").read[String] and
      (JsPath \ "detail").read[String].orElse(Reads.pure("")) and
      (JsPath \ "status").read[String]
    )(TodoItem.apply _)
}


