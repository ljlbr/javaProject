package by.psu;

import by.psu.db.JdbcHelper;
import by.psu.model.Excursion;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        JdbcHelper db = new JdbcHelper();

        // 1. CREATE
        Excursion e = new Excursion(
                0,
                "City Tour",
                new BigDecimal("49.99"),
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                "Ivan Petrov",
                "WALKING",
                true
        );

        db.create(e);
        System.out.println("Создана экскурсия с ID: " + e.getId());

        // 2. FIND BY ID
        Excursion found = db.getById(e.getId());
        System.out.println("Найдена экскурсия: " + found);

        // 3. UPDATE
        found.setName("Updated City Tour");
        found.setPrice(new BigDecimal("59.99"));
        db.update(found);
        System.out.println("После обновления: " + db.getById(found.getId()));

        // 4. DELETE
        //db.delete(found.getId());
        System.out.println("После удаления: " + db.getById(found.getId())); // будет null
    }
}