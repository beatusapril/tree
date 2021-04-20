package com.show.tree.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.show.tree.domain.File;
import com.show.tree.service.DataService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "fileServlet", value = "/files")
public class FileServlet extends HttpServlet {

    private final DataService dataService;

    public FileServlet() {
        dataService = new DataService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        List<File> files = dataService.getAllFiles();
      /*  ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(output);*/
        out.print(files);
        out.flush();
    }

    public void destroy() {
    }
}