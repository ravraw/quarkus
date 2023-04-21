package com.crossaisles.student;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentRepository {
    @Inject
    StudentRepository studentRepository;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        List<Student> students = studentRepository
      return Response.ok(students).build();
    }
}
