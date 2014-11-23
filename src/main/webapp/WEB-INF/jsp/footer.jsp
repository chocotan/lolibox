<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
  import="io.loli.box.startup.LoliBoxConfig" />
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:bundle basename="config">
  <div class="footer">
    <div class="container">
      <p>
        <a href="" data-toggle="modal" data-target="#terms">使用条款</a>&nbsp;|&nbsp;<a
          href="mailto:<%=LoliBoxConfig.getInstance().getEmail()%>" target="_blank">联系我</a> <br /> Powered by <a
          href="https://github.com/chocotan/lolibox" target="_blank"><fmt:message key="project.name" />-<fmt:message
            key="project.version" /></a>
      </p>
    </div>
  </div>
</fmt:bundle>