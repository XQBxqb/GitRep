<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	"Content-Type": "application/json",
	<base href="http://127.0.0.1:8080/crm/">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<link href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){
	$("#createActivityBtn").click(function (){
    $("#createActivityModal").modal("show");

	$("#createActivityForm").get(0).reset();

	$("#saveCreateActivityBtn").click(function (){
    var owner=$("#create-marketActivityOwner").val();
	var name=$("#create-marketActivityName").val();
	var startDate=$("#create-startTime").val();
	var endDate=$("#create-endTime").val();
    var cost=$.trim($("#create-cost").val());
    var description=$.trim($("#edit-describe").val());

	if(owner==""){
		alert("所有者不能为空");
		return;
	}
	if(name==""){
		alert("名字不能为空");
		return;
	}

	if(startDate!=""&&endDate!=""){
		if(endDate<startDate){
			alert("结束时间不能小于开始时间");
			return;
		}
	}

	var regExp=/^(([0-9]\d*)|0)$/;
	if(!regExp.test(cost)){
          alert("成本是非负整数");
		  return;
	};

	$.ajax({
		url:'workbench/activity/saveCreatActivity.do',
		data:{
			owner:owner,
			name:name,
			startDate:startDate,
			endDate:endDate,
			cost:cost,
			description:description
		},
		type:'post',
		dataType:'json',
		success:function (data){
			if(data.code=="1"){
				$("#createActivityModal").modal("hide");
				//刷新市场活动
				queryActivityByCondition(1,$("#demp_page1").bs_pagination('getOption','rowsPerPage'));
			}else{
				alert(data.message);
				$("#createActivityModal").modal("show");//可以不写
			}
		}
		}
	)
	})
	});
		$(".mydate").datetimepicker({
            language:'zh-CN',
		    format:'yyyy-mm-dd',
			minView:'month',
			autoclose:true,
			initialDate:new Date(),
			clearBtn:true,
			todayBtn:true
		});

		queryActivityByCondition(1,2);

		$("#queryActivityBtn").click(function (){
			queryActivityByCondition(1,$("#demp_page1").bs_pagination('getOption','rowsPerPage'));
		});

         $("#chckAll").click(function (){
			 $("#tBody input[type='checkbox']").prop("checked",this.checked);
		 });

		 $("#tBody").on("click","input[type='checkbox']",function(){
			 if($("#tBody input[type='checkbox']").size()==$("#tBody input[type='checkbox']:checked").size()){
				 $("#chckAll").prop("checked",true);
			 }else{
				 $("#chckAll").prop("checked",false);
			 }
		 });

		 $("#deleteActivityBtn").click(function (){
			 var checkedIds=$("#tBody input[type='checkbox']:checked");
			 if(checkedIds.size()==0){
				 alert("请选择要删除的市场活动");
				 return;
			 };
			 if(window.confirm("确定是否删除?")){
				 var ids="";
				 $.each(checkedIds,function (){
					 ids += "id=" + this.value + "&";
				 });
				 $.ajax({
					 url:'workbench/activity/deleteActivityByIds.do',
					 data:ids,
					 type:'post',
					 dataType:'json',
					 success:function (data){
						 if(data.code=="1"){
							 queryActivityByCondition(1,$("#demp_page1").bs_pagination('getOption','rowsPerPage'));
						 }else{
							 alert(data.message);
						 }
					 }
				 });
			 };
		 });

		 $("#editActivityBtn").click(function (){
			 var checkboxId=$("#tBody input[type='checkbox']:checked");
			 if(checkboxId.size()==0){
				 alert("请选择要删除的活动");
				 return;
			 };
			 if(checkboxId.size()>1){
				 alert("一次只能删除一次活动");
				 return;
			 };
			 var id=checkboxId.get(0).value;
			 $.ajax({
				 url:'workbench/activity/queryActivityById.do',
				 data:{
					 id:id
				 },
				 type:'post',
				 dataType:'json',
				 success:function (data){
					 $("#edit-id").val(data.id);
					 $("#edit-owner").val(data.owner);
					 $("#edit-name").val(data.name);
					 $("#edit-startTime").val(data.startDate);
					 $("#edit-endTime").val(data.endDate);
					 $("#edit-cost").val(data.cost);
					 $("#edit-describe").val(data.description);
					 $("#editActivityModal").modal("show");
				 }
			 })
		 })

		$("#updateActivityBtn").click(function (){
			var id=$("#edit-id").val();
			var owner=$("#edit-owner").val();
			var name=$("#edit-name").val();
			var startDate=$("#edit-startTime").val();
			var endDate=$("#edit-endTime").val();
			var cost=$("#edit-cost").val();
			var description=$("#edit-describe").val();
			$.ajax({
				url:'workbench/activity/updateActivity.do',
				data:{
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data){
					if(data.code=="0"){
				     alert(data.message);
					 $("#editActivityModal").modal("show");
					};
					if(data.code=="1"){
						$("#editActivityModal").modal("hide");
						queryActivityByCondition($("#demp_page1").bs_pagination('getOption','currentPage'),$("#demp_page1").bs_pagination('getOption','rowsPerPage'));
					}
				}
			});
			});
		$("#exportActivityAllBtn").click(function(){
			window.location.href="workbench/activity/downActivity.do";
		});
	});
	function queryActivityByCondition(pageNo,pageSize){
		var owner=$("#query-owner").val();
		var name=$("#query-name").val();
		var startDate=$("#query-startDate").val();
		var endDate=$("#query-endDate").val();

		$.ajax({
			url:'workbench/activity/queryActivityByCondition.do',
			data:{
				owner:owner,
				name:name,
				startDate:startDate,
				endDate:endDate,
				pageSize:pageSize,
				pageNo:pageNo
			},
			type:'post',
			dataType: 'json',
			success:function (data){
				//$("#totalRowsB").text(data.totalRows);
				var htmlStr="";
				$.each(data.activitiyList,function (index,obj){
					htmlStr+="<tr class=\"active\">";
					htmlStr+="<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>";
					htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/queryActivityForDetail.do?id="+obj.id+"'\">"+obj.name+"</a></td>";
					htmlStr+="<td>"+obj.owner+"</td>";
					htmlStr+="<td>"+obj.startDate+"</td>";
					htmlStr+="<td>"+obj.endDate+"</td>";
					htmlStr+="</tr>";
				});
				$("#tBody").html(htmlStr);

				var totalPages=1;
				if(data.totalRows%pageSize==0){
					totalPages=data.totalRows/pageSize;
				}else{
					totalPages=parseInt(data.totalRows/pageSize);
				};

				$("#demp_page1").bs_pagination({
					currentPage:pageNo,

					rowsPerPage:pageSize,
					totalRows:data.totalRows,
					totalPages:totalPages,

					visiblePageLinks: 5,

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo:true,

					onChangePage: function (event,pageObj){
						queryActivityByCondition(pageObj.currentPage,pageObj.rowsPerPage)
					},
				})
			}
		});
	};
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="createActivityForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${userList}" var="u">
									  <option value="${u.id}">${u.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control mydate" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveCreateActivityBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					    <input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label" >所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
									<c:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-name" class="col-sm-2 control-label" >名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label" >开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label" >结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateActivityBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="query-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="query-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="query-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="queryActivityBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary"  id="createActivityBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editActivityBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityBtn"><span class="glyphicon glyphicon-minus" ></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="chckAll" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">
						<!--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>-->
					</tbody>
				</table>
				<div id="demp_page1"></div>
			</div>
			
			<!--<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="totalRowsB">50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>-->

		</div>
		
	</div>
</body>
</html>