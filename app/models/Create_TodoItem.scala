package models

import io.swagger.annotations.{ApiModel, ApiModelProperty}

import scala.annotation.meta.field

/**
  * Created by kobin on 6/11/2017.
  */
@ApiModel("Create_TodoItem")
case class Create_TodoItem (
 @(ApiModelProperty @field)(  name="title", value = "Title of todo item", required = true)var title: String,
 @(ApiModelProperty @field)(  name="detail",  value = "Detail of todo item")var detail: String,
 @(ApiModelProperty @field)(  name="status", value = "Status of task", required = true)var status: String
  )