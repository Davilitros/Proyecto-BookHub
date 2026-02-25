package com.Bookhub.controller;

import com.Bookhub.entity.Book;
import com.Bookhub.entity.User;
import com.Bookhub.entity.Review;
import com.Bookhub.service.BookService;
import com.Bookhub.service.ReviewService;
import com.Bookhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cloudinary.Cloudinary;
import java.util.Map;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;



import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private Cloudinary cloudinary;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private ReviewService reviewService;

    // Listar libros
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "list"; // Thymeleaf template books/list.html
    }

    // Formulario para agregar libro (solo ADMIN)
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add"; // Thymeleaf template books/add.html
    }

    // Guardar libro (solo ADMIN)
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book,
                          @RequestParam("image") MultipartFile image,
                          Authentication authentication) throws Exception {

        if (!image.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            book.setImageUrl(uploadResult.get("secure_url").toString());
        }

        User user = userService.findByUsername(authentication.getName()).orElseThrow();
        book.setUser(user);
        book.setCreatedAt(LocalDateTime.now());

        bookService.save(book);
        return "redirect:/books";
    }
    
    // Ver detalles del libro
    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id).orElseThrow();
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviewService.findByBook(book));
        model.addAttribute("review", new Review());
        return "detail";
    }

    // Eliminar libro (solo ADMIN)
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    // Buscar libros por patrón
    @GetMapping("/search")
    public String searchBooks(@RequestParam("q") String query, Model model) {
        model.addAttribute("books", bookService.search(query));
        model.addAttribute("query", query);
        return "list";
    }
}