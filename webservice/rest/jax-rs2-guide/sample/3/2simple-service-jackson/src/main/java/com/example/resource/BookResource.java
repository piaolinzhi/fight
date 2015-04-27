package com.example.resource;

import com.example.domain.Book;
import com.example.domain.Books;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.Map.Entry;

@Path("books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    private static final Logger LOGGER = Logger.getLogger(BookResource.class);
    private static final HashMap<Long, Book> memoryBase;

    static {
        memoryBase = com.google.common.collect.Maps.newHashMap();
        BookResource.memoryBase.put(1L, new Book(1L, "Java Restful Web Services使用指南"));
        BookResource.memoryBase.put(2L, new Book(2L, "Java EE 7 精髓"));
    }

    @Path("/emptybook")
    @GET
    public JsonBook getEmptyArrayBook() {
        final JsonBook book = new JsonBook();
        BookResource.LOGGER.debug(book);
        return book;
    }

    @Path("/hybirdbook")
    @GET
    public JsonHybridBook getHybirdBook() {
        final JsonHybridBook book = new JsonHybridBook();
        BookResource.LOGGER.debug(book);
        return book;
    }

    @Path("/nojaxbbook")
    @GET
    public JsonNoJaxbBook getNoJaxbBook() {
        final JsonNoJaxbBook book = new JsonNoJaxbBook();
        BookResource.LOGGER.debug(book);
        return book;
    }

    @GET
    public Books getBooks() {
        final List<Book> bookList = new ArrayList<>();
        final Set<Map.Entry<Long, Book>> entries = BookResource.memoryBase.entrySet();
        final Iterator<Entry<Long, Book>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            final Entry<Long, Book> cursor = iterator.next();
            BookResource.LOGGER.debug(cursor.getKey());
            bookList.add(cursor.getValue());
        }
        final Books books = new Books(bookList);
        BookResource.LOGGER.debug(books);
        return books;
    }

    @POST
    public Book saveBook(final Book book) {
        book.setBookId(System.nanoTime());
        BookResource.memoryBase.put(book.getBookId(), book);
        return book;
    }
}
