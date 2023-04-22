package com.crossaisles.student;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    StudentRepository studentRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        List<Student> students = studentRepository.listAll();
        return Response.ok(students).build();
    }

    @POST
    @Transactional
    public Response saveStudent(@RequestBody Student student) {
        studentRepository.persist(student);
        if (studentRepository.isPersistent(student)) {
            return Response.created(URI.create(String.format("http://localhost:8080/students/%s", student.getId()))).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
