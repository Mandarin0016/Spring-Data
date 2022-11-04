package com.example.advquerying.init;

import com.example.advquerying.entities.Size;
import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.LabelService;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final Scanner scanner = new Scanner(System.in);
    private final IngredientService ingredientService;
    private final ShampooService shampooService;
    private final LabelService labelService;

    @Autowired
    public ConsoleRunner(IngredientService ingredientService, ShampooService shampooService, LabelService labelService) {
        this.ingredientService = ingredientService;
        this.shampooService = shampooService;
        this.labelService = labelService;
    }

    @Override
    public void run(String... args) throws Exception {
        getAllShampoosBySize();
        getAllShampoosBySizeOrLabelId();
        getAllShampoosGreaterThanPriceOrderedDesc();
        getAllIngredientsByNamePrefix();
        getAllIngredientsFromGiveList();
        getShampoosCountWithPriceLessThan();
        getAllShampoosThatContainsIngredients();
        getShampoosWithIngredientsCountLessThan();
        deleteIngredientByName();
        increasePriceForAllIngredients();
        increasePriceForIngredientsWithGiveNames();
    }

    private void increasePriceForIngredientsWithGiveNames() {
        List<String> names = new ArrayList<>();
        names.add("Active-Caffeine");
        ingredientService.increasePriceByTenPercentForGiven(names);
    }

    private void increasePriceForAllIngredients() {
        ingredientService.increasePriceByTenPercentForAll();
    }

    private void deleteIngredientByName() {
        ingredientService.deleteByName(scanner.nextLine());
    }

    private void getShampoosWithIngredientsCountLessThan() {
        Integer count = Integer.parseInt(scanner.nextLine());
        shampooService.getAllShampoosWithIngredientsCountLess(count).forEach(System.out::println);
    }

    private void getAllShampoosThatContainsIngredients() {
        List<String> ingredientNames = new ArrayList<>();
        ingredientNames.add("Berry");
        ingredientNames.add("Mineral-Collagen");
        shampooService.getAllShampoosThatContainsIngredientNames(ingredientNames).forEach(System.out::println);
    }

    private void getShampoosCountWithPriceLessThan() {
        System.out.println(shampooService.getShampooCountWithLowerPrice(new BigDecimal(scanner.nextLine())));
    }

    private void getAllIngredientsFromGiveList() {
        List<String> data = new ArrayList<>();
        data.add("Lavender");
        data.add("Herbs");
        data.add("Apple");
        ingredientService.getAllByGivenList(data).forEach(System.out::println);
    }

    private void getAllIngredientsByNamePrefix() {
        String prefix = scanner.nextLine();
        ingredientService.getAllByNameStartsWith(prefix).forEach(System.out::println);
    }

    private void getAllShampoosGreaterThanPriceOrderedDesc() {
        BigDecimal price = new BigDecimal(scanner.nextLine());
        shampooService.getAllShampoosGreaterThanGiverPriceDescOrdered(price).forEach(shampoo -> {
            System.out.printf("%s %s %slv%n", shampoo.getBrand(), shampoo.getSize().name(), shampoo.getPrice());
        });
    }

    private void getAllShampoosBySizeOrLabelId() {
        Size size = Size.valueOf(scanner.nextLine());
        Long id = Long.parseLong(scanner.nextLine());
        shampooService.getAllShampoosBySizeOrLabelIdOrderByDesc(size, id).forEach(shampoo -> {
            System.out.printf("%s %s %slv%n", shampoo.getBrand(), shampoo.getSize().name(), shampoo.getPrice());
        });
    }

    private void getAllShampoosBySize() {
        shampooService.getAllShampoosBySize(Size.valueOf(scanner.nextLine())).forEach(shampoo -> {
            System.out.printf("%s %s %slv%n", shampoo.getBrand(), shampoo.getSize().name(), shampoo.getPrice());
        });
    }
}
