package io.loli.box.controller;

import io.loli.box.service.AbstractStorageService;
import io.loli.box.service.StorageService;
import io.loli.box.startup.LoliBoxConfig;
import io.loli.box.util.FileBean;
import io.loli.box.util.FileUtil;
import io.loli.box.util.StatusBean;

import java.io.File;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;

import org.glassfish.jersey.server.JSONP;

@Path("/admin")
public class AdminAction {
	/**
	 * Template engine to generate html
	 */
	private static JetEngine engine = JetEngine.create();

	/**
	 * Default storageService
	 */
	private static StorageService ss = AbstractStorageService
			.getDefaultInstance();

	/**
	 * File comparator to sort
	 */
	private static Comparator<File> fileComparator = new Comparator<File>() {

		@Override
		public int compare(File o1, File o2) {
			try {
				if (o1.lastModified() - o2.lastModified() == 0) {
					return 0;
				}
				return o1.lastModified() - o2.lastModified() > 0 ? -1 : 1;
			} catch (Exception e) {
				return -1;
			}
		}

	};

	@GET
	@JSONP
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<FileBean> list(@QueryParam(value = "year") String year,
			@QueryParam(value = "month") String month,
			@QueryParam(value = "day") String day,
			@Context HttpServletRequest request) {

		Object obj = request.getSession().getAttribute("login");

		// 已经登陆
		if (obj != null && ((String) obj).equals("success")) {
		} else {
			// 没有登陆的话
			return new ArrayList<FileBean>();
		}

		List<File> files = ss.getFilesByDay(year, month, day);
		Collections.sort(files, fileComparator);
		return FileUtil.toFileBean(files);
	}

	@POST
	@JSONP
	@Path("delete")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public StatusBean delete(@FormParam(value = "year") String year,
			@FormParam(value = "month") String month,
			@FormParam(value = "day") String day,
			@FormParam(value = "name") String name,
			@Context HttpServletRequest request) {

		Object obj = request.getSession().getAttribute("login");

		// 已经登陆
		if (obj != null && ((String) obj).equals("success")) {
		} else {
			// 没有登陆的话
			return new StatusBean("error", "you must login");
		}

		try {
			ss.deleteFile(year, month, day, name);
		} catch (Exception e) {
			return new StatusBean("error", e.getMessage());
		}
		return new StatusBean("success", "成功删除文件");

	}

	@GET
	@Path("/")
	public Response index(@Context HttpServletRequest request)
			throws URISyntaxException {

		Object obj = request.getSession().getAttribute("login");

		// 已经登陆
		if (obj != null && ((String) obj).equals("success")) {
			JetTemplate template = engine.getTemplate("/html/admin.html");
			Map<String, Object> context = new HashMap<String, Object>();
			StringWriter writer = new StringWriter();
			template.render(context, writer);
			return Response.ok(writer.toString(), "text/html;charset=UTF-8")
					.build();
		} else {
			// 没有登陆的话
			return Response.seeOther(new URI("/admin/login")).build();
		}

	}

	@GET
	@Path("login")
	public Response login(@Context HttpServletRequest request)
			throws MalformedURLException, URISyntaxException {
		Object obj = request.getSession().getAttribute("login");

		// 已经登陆
		if (obj != null && ((String) obj).equals("success")) {
			return Response.seeOther(new URI("/admin/")).build();
		}

		JetTemplate template = engine.getTemplate("/html/login.html");
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("email", LoliBoxConfig.getInstance().getEmail());
		StringWriter writer = new StringWriter();
		template.render(context, writer);
		return Response.ok(writer.toString(), "text/html;charset=UTF-8")
				.build();
	}

	@POST
	@Path("login")
	public Response loginSubmit(
			@FormParam(value = "password") @NotNull String password,
			@Context HttpServletRequest request

	) throws MalformedURLException, URISyntaxException {
		if (LoliBoxConfig.getInstance().getPassword().equals(password)) {
			// Login success
			request.getSession().setAttribute("login", "success");

			return Response.seeOther(new URI("/admin/")).build();
		} else {
			// Login failed
			request.getSession().removeAttribute("login");

			JetTemplate template = engine.getTemplate("/html/login.html");
			Map<String, Object> context = new HashMap<String, Object>();
			StringWriter writer = new StringWriter();
			context.put("error", "密码错误");
			template.render(context, writer);
			return Response.ok(writer.toString(), "text/html;charset=UTF-8")
					.build();
		}
	}
}
