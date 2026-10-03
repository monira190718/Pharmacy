package com.sparktech.finalproject.category;

import com.sparktech.finalproject.medicine.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;

    private final MedicineRepository medicineRepository;


    @GetMapping("/category/add")
    public String addCategory(Model model){


        model.addAttribute(
                "category",
                new Category()
        );


        return "category-form";

    }

    
    @PostMapping("/category/save")
    public String saveCategory(
            @ModelAttribute Category category
    ){


        categoryRepository.save(category);


        return "redirect:/admin";

    }
    @GetMapping("/category/edit/{id}")
    public String editCategory(
            @PathVariable String id,
            Model model
    ){

        Category category =
                categoryRepository.findById(id)
                        .orElse(null);


        model.addAttribute(
                "category",
                category
        );


        return "category-form";

    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(
            @PathVariable String id
    ){

        categoryRepository.deleteById(id);


        return "redirect:/admin";

    }

}
