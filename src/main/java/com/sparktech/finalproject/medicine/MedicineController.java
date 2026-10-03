package com.sparktech.finalproject.medicine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineRepository medicineRepository;



    @GetMapping("/medicine")
    public String medicineList(Model model){

        model.addAttribute(
                "medicines",
                medicineRepository.findAll()
        );

        return "admin";

    }




    @GetMapping("/medicine/add")
    public String addMedicine(Model model){

        model.addAttribute(
                "medicine",
                new Medicine()
        );

        return "medicine-form";

    }




    @PostMapping("/medicine/save")
    public String saveMedicine(
            @ModelAttribute Medicine medicine
    ){

        if (medicine.getId() != null &&
                medicine.getId().isBlank()) {

            medicine.setId(null);
        }
        medicineRepository.save(medicine);


        return "redirect:/admin";

    }

    @GetMapping("/medicine/delete/{id}")
    public String deleteMedicine(@PathVariable String id){

        medicineRepository.deleteById(id);

        return "redirect:/admin";
    }

    @GetMapping("/medicine/edit/{id}")
    public String editMedicine(
            @PathVariable String id,
            Model model){

        Medicine medicine =
                medicineRepository.findById(id)
                        .orElseThrow();

       
        model.addAttribute(
                "medicine",
                medicine
        );


        return "medicine-form";
    }
}
