import domain.resource.Desk;
import domain.resource.Device;
import domain.resource.Resource;
import domain.resource.Room;
import money.Money;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResourceTest {

    public static void main(String[] args) {
        System.out.println("--- Rozpoczynamy testy hierarchii Zasobów ---\n");

        // 1. Sala z domyślną stawką
        Room conferenceRoom = new Room(
                "Sala A",
                10,
                Set.of("projector", "whiteboard")
        );

        // 2. Sala z NIESTANDARDOWĄ stawką (np. luksusowy gabinet)
        Room vipRoom = new Room(
                "Gabinet VIP",
                Money.of("150.00"), // customHourlyRate
                4,
                Set.of("TV", "coffee machine")
        );

        // 3. Gorące biurko (brak custom stawki)
        Desk hotDesk = new Desk("Biurko H1", Desk.DeskType.HOT);

        // 4. Przypisane biurko z promocyjną (niestandardową) stawką
        Desk fixedDeskDiscount = new Desk("Biurko F1", Money.of("12.00"), Desk.DeskType.FIXED);

        // 5. Urządzenia (np. laptopy do wypożyczenia)
        Device laptops = new Device("Laptop Dell", 5);

        // --- TEST POLIMORFIZMU I METODY SZABLONOWEJ ---
        List<Resource> allResources = new ArrayList<>();
        allResources.add(conferenceRoom);
        allResources.add(vipRoom);
        allResources.add(hotDesk);
        allResources.add(fixedDeskDiscount);
        allResources.add(laptops);

        System.out.println("Katalog dostępnych zasobów:\n");
        for (Resource res : allResources) {
            System.out.println(res.describe());

            // Tutaj dzieje się "magia" metody finalnej hourlyRate():
            // Sprawdza ona, czy obiekt ma ustawiony customHourlyRate. 
            // Jeśli tak, zwraca go. Jeśli nie, polimorficznie wywołuje baseRatePerHour() z podklasy.
            System.out.println(" -> Stawka za godzinę: " + res.hourlyRate());
            System.out.println();
        }
    }
}