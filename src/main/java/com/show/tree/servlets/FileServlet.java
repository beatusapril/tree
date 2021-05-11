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
import java.util.List;
import java.util.stream.Collectors;

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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = (File) JsonUtil.fromString(request.getReader().lines().collect(Collectors.joining()), File.class);
        if (file != null) {
            dataService.create(file);
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = request.getReader()
                .lines()
                .collect(Collectors.joining());
        File file = (File) JsonUtil.fromString(body, File.class);
        if (file != null) {
            dataService.delete(file.getId());
        }
    }

    public void destroy() {
    }
}