package com.show.tree.servlets;

import com.show.tree.domain.File;
import com.show.tree.service.DataService;
import com.show.tree.util.JsonUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "fileServlet", value = "/files")
public class FileServlet extends HttpServlet {

    private final DataService dataService;

    public FileServlet() {
        dataService = new DataService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List<File> files = dataService.getAllFiles();
        String json = JsonUtil.toString(files);
        out.print(json);
        out.flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String prId = request.getParameter("parent_id");
        Integer parentId = (prId != null) ? Integer.parseInt(prId) : null;
        String rcId = request.getParameter("record_id");
        Integer recordId = (rcId != null) ? Integer.parseInt(rcId) : null;
        String name = request.getParameter("name");
        File file = new File();
        file.setId(recordId);
        file.setParentId(parentId);
        file.setName(name);
        dataService.create(file);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String rcId = request.getParameter("record_id");
        Integer recordId = (rcId != null) ? Integer.parseInt(rcId) : null;
            dataService.delete(recordId);

    }

    public void destroy() {
    }
}