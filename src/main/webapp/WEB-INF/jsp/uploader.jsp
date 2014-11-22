<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="uploader">
  <div class="container">
    <div class="uploader-div">
      <form class="uploader-form" method="post" action="${pageContext.request.contextPath}/image/upload"
        enctype="multipart/form-data">
        <div>
          <h3>拖动图片到页面上或者</h3>
          <a class="btn btn-primary uploader-browse-button">选择图片</a>&nbsp; <input type="file" class="uploader-form-file"
            name="image" multiple />
        </div>
        <div id="fileList"></div>
      </form>
    </div>

  </div>

  <div class="modal" id="terms">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">
            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
          </button>
          <h4 class="modal-title">使用条款</h4>
        </div>
        <div class="modal-body">
          <jsp:include page="terms.jsp"></jsp:include>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        </div>
      </div>
      <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
  </div>
  <!-- /.modal -->
</div>



<div class="uploader-links-container hide">
  <div class="container ">

    <div class="panel panel-default uploader-links-panel">
      <div class="panel-heading">
        您选择了<span class="count-result"></span>张图片
        <button class="btn btn-default btn-xs select-all-btn">全选</button>
        <button class="btn btn-default btn-xs unselect-all-btn">取消</button>
        <button class="btn btn-default btn-xs copy-all-btn">复制</button>
      </div>
      <div class="panel-body">
        <div class="links-left">
          <div class="list-group">
            <a href="javascript:void(0)" class="list-group-item active origin-btn">原始链接</a> <a href="javascript:void(0)"
              class="list-group-item html-btn">HTML代码</a> <a href="javascript:void(0)" class="list-group-item bb-btn">BB
              CODE</a>
          </div>
        </div>
        <div class="links-right">
          <textarea id="links-result" readonly></textarea>
        </div>
      </div>
    </div>


  </div>
</div>