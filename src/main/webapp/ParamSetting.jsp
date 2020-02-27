<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>参数设置</title>
<script src="/dhr-demo/resources/js/boot.js" type="text/javascript"></script>
<style> 
	body,html {width:100%;height:100%;overflow:hidden}
	*{
		margin:0;
		padding:0;
	}
	.excel-cell-selected{
	    background:rgb(223, 232, 246);
	}
</style>
</head>
<body>
	<div id="searchForm" class="mini-toolbar" style="padding: 5px;">
		<input id="a0100" name="a0100" class="mini-hidden" value="${user.A0100}"/>
		客户名称：<input id="d01119" name="d01119" class="mini-textbox" width="90" />
		<spanclass="separator"></span>
		项目名称：<input id="d01199" name="d01199" class="mini-textbox" width="90" />
		<span class="separator"></span>
		<a class="mini-button" iconCls="icon-search" onclick="search()">查询</a> 
		<a class="mini-button" iconCls="icon-expand" onclick="search('all')">全部</a>
	</div>
	<div class="mini-toolbar" style="padding: 5px; border-bottom: 0; border-top: 0; background: #fff">
		<a class="mini-button" iconCls="icon-save" plain="true" onclick="save()">保存</a>
	</div>
	<div class="mini-fit" style="padding: 2px; border: solid 1px #ddd;">
		<div id="D011" url="/dhr-demo/paramSetting/search" idField="d01101"
				class="mini-datagrid" style="width: 100%; height: 100%"
				fitColumns="false" 
				cellEditAction="celldblclick" 
				allowCellSelect="true"
				allowCellEdit="true" 
				editNextOnEnterKey="true" 
				editNextRowCell="true"
				enableHotTrack="false" 
				allowRowSelect="true"
		>
			<div property="columns">
				<div type="indexcolumn" width="50"></div>
				<div field="d01101" headerAlign="center" align="center" width="100">
					项目编号
				</div>
				<div field="d01199" headerAlign="center" align="center" width="100">
					项目名称
				</div>
				<div field="d01119" headerAlign="center" align="center" width="100">
					客户名称
				</div>
				<div field="d01136" type="comboboxcolumn" headerAlign="center" align="center" width="100">
					项目类型<input property="editor" class="mini-combobox" data=codes['XL'] textField="codeItemDesc" valueField="codeItemId" style="width: 100%;" />
				</div>
				<div field="d01161" headerAlign="center" align="center" width="100">
					合同金额
				</div>
				<div field="d01126name" headerAlign="center" align="center" width="100">
					所属部门
				</div>
				<div field="d01127name" headerAlign="center" align="center" width="100">
					销售经理
				</div>
				<div field="d011abname" headerAlign="center" align="center" width="100">
					项目经理
				</div>
				<div field="d01179" headerAlign="center" align="center" width="100">
					计划开始时间
				</div>
				<div field="d01178" headerAlign="center" align="center" width="100">
					计划结束时间
				</div>
				<div field="d01177" headerAlign="center" align="center" width="100">
					实际开始时间
				</div>
				<div field="d01170" headerAlign="center" align="center" width="100">
					预算人天
				</div>
				<div field="d01186" headerAlign="center" align="center" width="60">
					计算天数
					<input property="editor" class="mini-textbox" style="width: 100%;" />
				</div>
				<div field="d01187" type="comboboxcolumn" headerAlign="center" align="center" width="60">
					结算方式
					<input property="editor" class="mini-combobox" data=codes['XE'] textField="codeItemDesc" valueField="codeItemId" style="width: 100%;" />
				</div>
				<div readOnly="true" field="d01185" headerAlign="center" align="center" width="80">
					创建时间
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	mini.parse();
	var codes = ${code};
	
	var grid = mini.get("D011");
	search('all');
	
	function save(){
		var data = grid.getChanges(null,true);
		$.ajax({ 
            type:"POST", 
            url:"/dhr-demo/paramSetting/save", 
            dataType:"json",      
            contentType:"application/json",            
            data:mini.encode(data), 
            success:function(result){ 
            	mini.alert(result);                 
            } 
        }); 
	}
	
	function search(requetScopen){
		var form = new mini.Form("#searchForm");
		obj = form.getData(true, false);
		grid.load({
			conJson : mini.encode(obj)
		});
	}
</script>
</html>
 

