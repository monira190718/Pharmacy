package com.sparktech.finalproject.manufacturer;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ManufacturerController {


    private final ManufacturerRepository manufacturerRepository;



    @GetMapping("/manufacturer/add")
    public String addManufacturer(Model model){


        model.addAttribute(
                "manufacturer",
                new Manufacturer()
        );


        return "manufacturer-form";

    }



    @PostMapping("/manufacturer/save")
    public String saveManufacturer(
            @ModelAttribute Manufacturer manufacturer
    ){

        if(manufacturer.getId() != null
                && manufacturer.getId().isEmpty()){

            manufacturer.setId(null);
        }


        manufacturerRepository.save(manufacturer);


        return "redirect:/admin";

    }


    @GetMapping("/manufacturer/edit/{id}")
    public String editManufacturer(
            @PathVariable String id,
            Model model
    ){

        Manufacturer manufacturer =
                manufacturerRepository.findById(id)
                        .orElse(null);


        model.addAttribute(
                "manufacturer",
                manufacturer
        );


        return "manufacturer-form";

    }

    @GetMapping("/manufacturer/delete/{id}")
    public String deleteManufacturer(
            @PathVariable String id
    ){

        manufacturerRepository.deleteById(id);


        return "redirect:/admin";

    }


}