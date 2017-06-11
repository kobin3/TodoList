package models

import io.swagger.annotations._
import java.util.Date

import play.api.libs.json.{JsPath, Reads}

import scala.annotation.meta.field
import play.api.libs.functional.syntax._

/**
  * Created by kobin on 6/11/2017.
  */
@ApiModel("TodoItem")
case class TodoItem(
    @(ApiModelProperty @field)(name="id",value = "Id of the item", required = false)var id: Int,
    @(ApiModelProperty @field)( name="title", value = "Title of todo item", required = true)var title: String,
    @(ApiModelProperty @field)(  name="detail",  value = "Detail of todo item")var detail: String,
    @(ApiModelProperty @field)(  name="status", value = "Status of task", required = true)var status: String
  )


