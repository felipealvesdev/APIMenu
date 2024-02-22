package com.example.menu.controller;

import com.example.menu.food.Food;
import com.example.menu.food.FoodDTO;
import com.example.menu.food.FoodRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;


    @GetMapping()
    public ResponseEntity<List<Food>> getAllFoods() {
        return ResponseEntity.status(HttpStatus.OK).body(foodRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFoodById(@PathVariable(value = "id") Long id ) {
        Optional<Food> foodModel = foodRepository.findById(id);
        if(foodModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(foodModel.get());
    }


    @PostMapping()
    public ResponseEntity<Food> saveFood(@RequestBody FoodDTO foodDTO) {
        var foodModel = new Food();
        BeanUtils.copyProperties(foodDTO, foodModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(foodRepository.save(foodModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFoodById(@PathVariable(value = "id") Long id,
                                                 @RequestBody FoodDTO foodDTO) {
        Optional<Food> foodModel = foodRepository.findById(id);
        if(foodModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        var food = foodModel.get();
        BeanUtils.copyProperties(foodDTO, food);
        return ResponseEntity.status(HttpStatus.OK).body(foodRepository.save(food));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFoodById(@PathVariable(value = "id") Long id) {
        Optional<Food> foodModel = foodRepository.findById(id);
        if(foodModel.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        foodRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }
}
